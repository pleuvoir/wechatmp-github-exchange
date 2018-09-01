package io.github.pleuvoir.common;

import javax.annotation.PostConstruct;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.pleuvoir.kit.StatefullRestTemplate;
import lombok.SneakyThrows;

@Configuration
public class ExchangeConfiguration {

	@Value("${username}")
	private String username;
	@Value("${oauthAccessToken}")
	private String oauthAccessToken;
	@Value("${targetRepository}")
	private String targetRepository;
	
	@PostConstruct
	@SneakyThrows
	@Bean
	public GHRepository initGHRepository() {
		GitHub github = GitHub.connect(username, oauthAccessToken);
		GHMyself myself = github.getMyself();
		GHRepository repository = myself.getRepository(targetRepository);
		return repository;
	}
	
	
	@Bean
    public RestTemplate restTemplate() {
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        httpContext.setAttribute(HttpClientContext.REQUEST_CONFIG, RequestConfig.custom().setRedirectsEnabled(false).build());
        return new StatefullRestTemplate(httpContext);
    }
	
}
