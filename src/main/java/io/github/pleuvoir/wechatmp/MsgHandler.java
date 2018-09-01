package io.github.pleuvoir.wechatmp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.github.GithubCommitService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;


@Component
public class MsgHandler implements WxMpMessageHandler{
	
	
	@Autowired
	private GithubCommitService githubCommitService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
    	
        // 提交到摘录项目
        githubCommitService.commit(wxMessage.getContent());
        // 组装回复消息
        String content = "收到信息内容：" + JSON.toJSONString(wxMessage);
        return new TextBuilder().build(content, wxMessage, weixinService);
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
