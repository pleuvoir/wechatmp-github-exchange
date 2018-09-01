package io.github.pleuvoir.common;

import javax.annotation.PostConstruct;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.SneakyThrows;

@Configuration
public class GithubConfiguration {

	@Value("${username}")
	private String username;
	@Value("${oauthAccessToken}")
	private String oauthAccessToken;
	@Value("${targetRepository}")
	private String targetRepository;
	
	@PostConstruct
	@SneakyThrows
	@Bean
	public GHRepository inita() {
		GitHub github = GitHub.connect(username, oauthAccessToken);
		GHMyself myself = github.getMyself();
		GHRepository repository = myself.getRepository(targetRepository);
		return repository;
	}
	
}
