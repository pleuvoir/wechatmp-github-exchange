package io.github.pleuvoir.wechatmp;

import io.github.pleuvoir.github.GithubCommitService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;


@Slf4j
@Component
public class MsgHandler implements WxMpMessageHandler{
	
	
	@Autowired
	private GithubCommitService githubCommitService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
    	
        // 提交到摘录项目
		new GithubCommitAsyncThread(githubCommitService, wxMessage.getContent());
        
        // 组装回复消息
        String content = "收到信息内容：" + JSON.toJSONString(wxMessage);
        return new TextBuilder().build(content, wxMessage, weixinService);
    }
    
    
    
	/**
	 *  github 提交异步处理线程
	 * 	<p> github 提交后页面显示内容已变，但实际上程序必须去另外的 url 下载文件内容，而这个是有延迟的，所以所有提交排队等待 5 分钟 后再去更新
	 */
	private static class GithubCommitAsyncThread extends Thread {
		private GithubCommitService githubCommitService;
		private String content;

		public GithubCommitAsyncThread(GithubCommitService githubCommitService,
				String content) {
			this.githubCommitService = githubCommitService;
			this.content = content;
			start();
		}

		@Override
		public void run() {
			try {
				synchronized (githubCommitService) {
					TimeUnit.MINUTES.sleep(5);
					githubCommitService.commit(content);
				}
			} catch (InterruptedException e) {
				log.error("{} error，{}", this.getName(), e);
			}
		}
	}
    
    
    
    public class TextBuilder  {
        public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage,
                                       WxMpService service) {
            WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(content)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
            return m;
        }
    }
}
