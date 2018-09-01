package io.github.pleuvoir.github;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.github.pleuvoir.common.ExchangeConfiguration;
import lombok.SneakyThrows;

public class GithubKit {
	
	/**
	 * 今日目录
	 */
	public static String todayCatalogue() {
		String now = DateTimeFormatter.ofPattern("yyyy/MM").format(LocalDateTime.now());
		String todayCatalogue = "摘录/".concat(now);
		return todayCatalogue;
	}
	
	/**
	 * 今日文件名称
	 */
	public static String todayFileName() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()).concat(".md");
	}
	
	/**
	 * 从文件地址获取字节数组
	 * @param downloadUrl	文件地址
	 * @return
	 */
	@SneakyThrows
	public static byte[] fetchFileInputStream(String downloadUrl) {
		RestTemplate restTemplate = new ExchangeConfiguration().restTemplate();
		URI uri = new URI(downloadUrl);
		ResponseEntity<byte[]> responseEntity = restTemplate.exchange(RequestEntity.get(uri).build(), byte[].class);  
		return responseEntity.getBody();
	}
	
	
	/**
	 * 将文本内容转为字节数组
	 * @param content	文本内容
	 * @return
	 */
	public static byte[] addContent(String content) {
		String origin = new String("").concat("\r\n\r\n").concat(content);
		return origin.getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	 * 向字节数组中尾部增加文本内容，并以字节数组形式返回
	 * @param bytes		原始文件字节数组
	 * @param content	向尾部追加的文本内容
	 * @return
	 */
	public static byte[] addContent(byte[] bytes, String content) {
		String origin = utfToString(bytes).concat("\r\n\r\n").concat(content);
		return origin.getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	 * 从文件地址获取字节数组并向尾部增加文本内容，并以字节数组形式返回
	 * @param downloadUrl	文件地址
	 * @param content		向尾部追加的文本内容
	 * @return
	 */
	public static byte[] addContent(String downloadUrl, String content) {
		String origin = utfToString(fetchFileInputStream(downloadUrl)).concat("\r\n\r\n").concat(content);
		return origin.getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	 * 加码成人类不可读的文本
	 * @param text	原始文本
	 */
	public static String encodeUTF8(String text){
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	/**
	 * 解码成人类可读的文本
	 * @param text	原始文本
	 */
	public static String decodeUTF8(String text) {
		try {
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	
	/**
	 * 安全的转化为字节数组，防止乱码
	 * @param data
	 * @return
	 */
	public static String utfToString(byte[] data) {
		String str = null;
		try {
			str = new String(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}
	
}
