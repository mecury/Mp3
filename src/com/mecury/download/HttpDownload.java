package com.mecury.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.mecury.Utils.FileUtils;

import android.R.integer;
import android.R.string;

public class HttpDownload {

	/*
	 * 下载部分代码
	 */
	public String download(String urlstr){
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
		try {
			//创建一个URL对象
			URL url = new URL(urlstr);
			//创建一个http连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = buffer.readLine())!=null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				buffer.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/*
	 * service中用到的下载方法,该方法返回的值-1：代表出错，0：代表下载成功，1：代表文件已经存在
	 */
	public int downloadFile(String urlstr, String path, String fileName){
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.isFileExist(fileName, path)) {
				return 1;
			}else {
				inputStream = getInputStreamFromUrl(urlstr);
				File result = fileUtils.writeSDFromInput(path, fileName, inputStream);
				if (result == null) {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally{
			try {
				inputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return 0;
	}
	
	/*
	 * 根据Url得到输入流
	 */
	public InputStream getInputStreamFromUrl(String urlstr) throws MalformedURLException,IOException{
		URL url = new URL(urlstr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream inputStream = connection.getInputStream();
		return inputStream;
	}
}
