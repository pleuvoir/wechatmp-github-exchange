package io.github.pleuvoir.wechatmp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.fastjson.JSON;

import lombok.Data;


@Data
@ConfigurationProperties(prefix = "wechatmp")
public class WechatMpProperties {
	
    private List<MpConfig> configs;

    @Data
    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
