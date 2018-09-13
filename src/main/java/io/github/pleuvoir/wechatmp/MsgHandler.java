package io.github.pleuvoir.wechatmp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.github.thread.GithubCommitTask;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;


@Slf4j
@Component
public class MsgHandler implements WxMpMessageHandler{
	
	@Resource(name="singleExecutorService")
	private ExecutorService	executorService;
	

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
    	
    	// 创建单个线程，需要顺序保证执行任务，不会有多个线程活动，使用了无界队列
    	executorService.execute(new GithubCommitTask(wxMessage.getContent()));

        // 组装回复消息
		log.debug("收到信息内容：{}", JSON.toJSONString(wxMessage));
		return new TextBuilder().build(
				"today is my lucky day " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),
				wxMessage, weixinService);
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
