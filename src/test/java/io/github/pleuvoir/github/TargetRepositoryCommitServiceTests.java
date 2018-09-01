package io.github.pleuvoir.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.pleuvoir.github.CommitService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TargetRepositoryCommitServiceTests {

	@Autowired
	private CommitService commitService;
	
	

	@Test
	public void contextLoads()  { 
		commitService.commit(null, null);
	}
	
	
}
