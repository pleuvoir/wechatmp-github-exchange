package io.github.pleuvoir.github;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.fastjson.JSON;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "github")
public class GithubProperties {

	/**
	 * github 用户名
	 */
	private String username;
	
	/**
	 * 访问令牌
	 */
	private String oauthAccessToken;
	
	/**
	 * 待提交仓库
	 */
	private String targetRepository;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
