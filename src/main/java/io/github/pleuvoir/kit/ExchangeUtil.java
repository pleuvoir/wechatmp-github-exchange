package io.github.pleuvoir.kit;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExchangeUtil {

	
	/**
	 * 创建目录
	 * @param path 可以是 /a/b/c
	 */
	public static void mkdir(String decodedPath) {
		File file = new File(decodedPath);
		boolean mkdir = file.mkdirs();
		if (mkdir) {
			log.info("文件夹创建成功，decodedPath：{}", decodedPath);
		} else {
			log.error("文件夹创建失败，decodedPath：{}", decodedPath);
		}
	}
	
	
	/**
	 * 在当前项目目录下创建文件夹
	 * @param pathname 可以是 /a/b/c
	 * @return 	文件夹的绝对路径
	 */
	public static String mkdirInClassPath(String path) {
		String folder = decodeUTF8(getClasspath().concat(path));
		mkdir(folder);
		return folder;
	}
	
	/**
	 * 生成文件
	 * @param fileName	文件名称
	 */
	public static void createFile(String fileName) {
		File file = new File(fileName);
		try {
			file.createNewFile();
			log.info("文件创建成功，fileName：{}", fileName);
		} catch (IOException e) {
			log.error("创建文件失败，fileName：{}", fileName);
		}
	}
	
	public static String getClasspath() {
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../")
				.replaceAll("file:/", "").replaceAll("%20", " ").trim();
		try {
			path = URLDecoder.decode(path, "utf-8");
			if (path.indexOf(":") != 1) {
				path = File.separator + path;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
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
	
}
