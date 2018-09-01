package io.github.pleuvoir.github;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.kit.GithubKit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TargetRepositoryCommitService implements CommitService {

	@Autowired
	private GHRepository repository;

	@Override
	public void commit(String contents, String path) {
		LocalDate now = LocalDate.now();
		// 	今年
		String year = String.valueOf(now.getYear());
		//	当月
		String month = format(now.getMonthValue());
		//	当天			
		String day = format(now.getDayOfMonth());
		
		String targetFolder = encodeUTF8("摘录");
		// 	今年的目录
		String yearFolder = targetFolder.concat("/").concat(year);
		//	当月的目录
		String monthFolder = yearFolder.concat("/").concat(month);
		
		GithubKit.checkPathExist(repository, yearFolder);
		
	//	List<GHContent> yearFolderContent = repository.getDirectoryContent(yearFolder);
		
//		if (yearFolderContent.size() > 0) {
//			List<GHContent> monthFolderContent = repository.getDirectoryContent(monthFolder);
//			monthFolderContent.forEach(folder -> {
//				log.info(folder.getPath());
//			});
//		}
	}

	
	private static String encodeUTF8(String encode){
		try {
			return URLEncoder.encode(encode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String format(Integer time) {
		if (time > 10) {
			return String.valueOf(time);
		}
		return "0".concat(String.valueOf(time));
	}
}
