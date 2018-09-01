package io.github.pleuvoir.kit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GithubKit {

	public static void checkPathExist(GHRepository repository, String path) {
		String decodedPath = decodeUTF8(path);
		log.info("检查仓库【{}】路径【{}】是否存在", repository.getName(), decodedPath);
		try {
			repository.getDirectoryContent(path);
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				log.warn("仓库【{}】路径【{}】不存在，开始本地创建路径", repository.getName(), decodedPath);
				mkdir("D://".concat(decodedPath));
			}
		}

	}

	public static String encodeUTF8(String encode){
		try {
			return URLEncoder.encode(encode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("encodeUTF8 error，{}",e);
		}
		return null;
	}
	
	public static String decodeUTF8(String decode) {
		try {
			return URLDecoder.decode(decode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("decodeUTF8 error，{}",e);
		}
		return null;
	}
	
	
	private static void createFile(String pathname) {
		File file = new File(pathname);
		try {
			file.createNewFile();
			log.info("文件创建成功，pathname：{}", pathname);
		} catch (IOException e) {
			log.error("创建文件失败，pathname：{}", pathname);
		}
	}
	
	
	
	private static void mkdir(String pathname) {
		File file = new File(pathname);
		boolean mkdir = file.mkdirs();
		if (mkdir) {
			log.info("文件夹创建成功，pathname：{}", pathname);
		} else {
			log.error("文件夹创建失败，pathname：{}", pathname);
			System.exit(0);
		}
	}
	
	
	public static void main(String[] args) {
		File file = new File("D://摘录/2018/09");
		boolean mkdir = file.mkdirs();
		System.out.println(mkdir);
	}
	
}
