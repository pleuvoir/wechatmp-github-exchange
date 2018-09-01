package io.github.pleuvoir.wechatmp;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;


@Configuration
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatMpConfiguration {

    private MsgHandler msgHandler;
 
    private WechatMpProperties WechatMpProperties;

    private static Map<String, WxMpMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMpService> mpServices = Maps.newHashMap();

	@Autowired
	public WechatMpConfiguration(MsgHandler msgHandler, WechatMpProperties properties) {
		this.msgHandler = msgHandler;
		this.WechatMpProperties = properties;
	}

    public static Map<String, WxMpMessageRouter> getRouters() {
        return routers;
    }

    public static Map<String, WxMpService> getMpServices() {
        return mpServices;
    }

    @Bean
    public Object services() {
        mpServices = this.WechatMpProperties.getConfigs()
            .stream()
            .map(s -> {
                WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
                configStorage.setAppId(s.getAppId());
                configStorage.setSecret(s.getSecret());
                configStorage.setToken(s.getToken());
                configStorage.setAesKey(s.getAesKey());
                WxMpService service = new WxMpServiceImpl();
                service.setWxMpConfigStorage(configStorage);
                routers.put(s.getAppId(), this.newRouter(service));
                return service;
            }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));

        return Boolean.TRUE;
    }

    private WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();
        return newRouter;
    }

}
