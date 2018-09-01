package io.github.pleuvoir.kit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public abstract class Test {

	public static void main(String[] args) throws IOException {

		GitHub github = GitHub.connect("pleuvoir", "68280c0c766b1b33e7aa49f1d6c6f02c9f89c930");
		GHMyself myself = github.getMyself();
		GHRepository repository = myself.getRepository("reading-and-writing");
//		System.out.println(repository.getFullName());
		
		LocalDate now = LocalDate.now();
		// 	今年
		String year = String.valueOf(now.getYear());
		//	当月
		String month = format(now.getMonthValue());
		//	当天			
		String day = format(now.getDayOfMonth());
		
		String targetFolder = GithubKit.encodeUTF8("摘录");
		// 	今年的目录
		String yearFolder = targetFolder.concat("/").concat(year);
		//	当月的目录
		String monthFolder = yearFolder.concat("/").concat(month);
		
		// 今年的目录有没有，没有则创建
		GithubKit.checkPathExist(repository, yearFolder);
		
		// 当月的目录有没有，没有则创建
		GithubKit.checkPathExist(repository, monthFolder);
		
		
//		List<GHContent> yearFolderContent = repository.getDirectoryContent(yearFolder);
//		
//		if (yearFolderContent.size() > 0) {
//			List<GHContent> monthFolderContent = repository.getDirectoryContent(monthFolder);
//			monthFolderContent.forEach(folder -> {
//				System.out.println(folder.getPath());
//			});
//		}
	}
	
	
	private void printDirectory(GHRepository repository, String path) throws UnsupportedEncodingException, IOException {
		List<GHContent> directoryContent = repository.getDirectoryContent(URLEncoder.encode(path, "UTF-8"));
		directoryContent.forEach(folder -> {
			System.out.println(folder.getPath());
		});
	}
	

	private static String format(Integer time) {
		if (time > 10) {
			return String.valueOf(time);
		}
		return "0".concat(String.valueOf(time));
	}
}
