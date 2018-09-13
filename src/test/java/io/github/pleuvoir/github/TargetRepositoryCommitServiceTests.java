package io.github.pleuvoir.github;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.pleuvoir.github.thread.GithubCommitTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TargetRepositoryCommitServiceTests {
	
	@Resource(name="singleExecutorService")
	private ExecutorService	executorService;

	@Test
	public void contextLoads() throws InterruptedException {
		executorService.execute(new GithubCommitTask("today is my lucky day "
				+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())));
		
		TimeUnit.SECONDS.sleep(5);
	}
	
}