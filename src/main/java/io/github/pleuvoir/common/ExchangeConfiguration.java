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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.pleuvoir.github.GithubProperties;
import lombok.SneakyThrows;

@Configuration
@EnableConfigurationProperties(GithubProperties.class)
public class ExchangeConfiguration {
	
	@Autowired
	private GithubProperties githubProperties;
	
	@PostConstruct
	@SneakyThrows
	@Bean
	public GHRepository initGHRepository() {
		GitHub github = GitHub.connect(githubProperties.getUsername(), githubProperties.getOauthAccessToken());
		GHMyself myself = github.getMyself();
		GHRepository repository = myself.getRepository(githubProperties.getTargetRepository());
		return repository;
	}
	
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(9000);
		return factory;
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
