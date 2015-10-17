package com.mecury.download;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownload {

	/*
	 * ���ز��ִ���
	 */
	public String download(String urlstr){
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
		try {
			//����һ��URL����
			URL url = new URL(urlstr);
			//����һ��http����
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
}
