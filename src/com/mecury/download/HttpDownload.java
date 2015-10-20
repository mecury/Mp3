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
	
	/*
	 * service���õ������ط���,�÷������ص�ֵ-1���������0���������سɹ���1�������ļ��Ѿ�����
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
	 * ����Url�õ�������
	 */
	public InputStream getInputStreamFromUrl(String urlstr) throws MalformedURLException,IOException{
		URL url = new URL(urlstr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream inputStream = connection.getInputStream();
		return inputStream;
	}
}
