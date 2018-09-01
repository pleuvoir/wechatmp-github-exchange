package io.github.pleuvoir.github;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHContentBuilder;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReadingAndWritingCommitter implements GithubCommitService {

	@Autowired
	private GHRepository repository;

	@SneakyThrows
	@Override
	public void commit(String content) {
		String todayCatalogue = GithubKit.todayCatalogue();  						//	摘录/2018/09
		String todayFileName = GithubKit.todayFileName();							//	2018-09-01.md
		String todayFilePath = todayCatalogue.concat("/").concat(todayFileName);	//	摘录/2018/09/2018-09-01.md
		log.info("检查仓库【{}】今日【{}】文件是否存在", repository.getName(), todayFilePath);
		
		// 检查文件是否存在
		try {
			GHContent fileContent = repository.getFileContent(GithubKit.encodeUTF8(todayFilePath));
			String downloadUrl = fileContent.getDownloadUrl();
			log.info("今日文件已存在，获取文件下载地址：{}", downloadUrl);
			
			// 获取已存在文件流 并且将文本内容追加到末尾
			byte[] addedContentBytes = GithubKit.addContent(downloadUrl, content);
			log.info("更新后内容：{}", new String(addedContentBytes, "UTF-8"));
			// 提交文件
			commitFile(addedContentBytes, todayFilePath, fileContent.getSha());
			log.info("仓库【{}】文件【{}】已提交", repository.getName(), todayFilePath);
			
		} catch (IOException e) {
			
			// 如果文件不存在则直接生成  今日文件 byte[]
			if (e instanceof FileNotFoundException) {
				log.warn("仓库【{}】文件【{}】不存在，开始创建流文件", repository.getName(), todayFilePath);
				byte[] addedContentBytes = GithubKit.addContent(content);
				log.info("创建文件内容：{}", new String(addedContentBytes, "UTF-8"));
				// 提交文件
				commitFile(addedContentBytes, todayFilePath);
				log.info("仓库【{}】文件【{}】已提交", repository.getName(), todayFilePath);
			}
		}
	}
	
	
	@SneakyThrows
	private void commitFile(byte[] bytes, String path, String sha) {
		GHContentBuilder contentBuilder = repository.createContent();
		contentBuilder.sha(sha).content(bytes)
				.message(" :boom: auto commit ")
				.path(GithubKit.encodeUTF8(path)).commit();
	}
	
	@SneakyThrows
	private void commitFile(byte[] bytes, String path) {
		GHContentBuilder contentBuilder = repository.createContent();
		contentBuilder.content(bytes)
				.message(" :blush: auto commit ")
				.path(GithubKit.encodeUTF8(path)).commit();
	}
	
}
