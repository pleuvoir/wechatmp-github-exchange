package io.github.pleuvoir.github;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TargetRepositoryCommitServiceTests {

	@Autowired
	private GithubCommitService commitService;
	

	@Test
	public void contextLoads() throws URISyntaxException, IOException  { 
		commitService.commit("today is my lucky day " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
	}
	
}
