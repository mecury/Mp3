package com.mecury.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.R.integer;
import android.os.Environment;

public class FileUtils {

	private String SDCardRoot;
	
	public FileUtils(){
		//得到SDcard的根目录，File.separator为\\
		SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	/*
	 * 在SDCard上创建文件
	 */
	public File creatFileInSDCard(String fileName, String dir) throws IOException{
		//
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}
	
	/*
	 * 在SDCard上创建目录
	 */
	public File creatSDDir(String dir) {
		File file = new File(SDCardRoot  + dir +File.separator);
		System.out.println(file.mkdir());
		return file;
	}
	
	/*
	 * 判断文件夹是否存在
	 */
	public boolean isFileExist(String fileName, String dir) {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		return file.exists();
	}
	
	/*
	 * 将inputStream中的数据写入SD中
	 */
	public File writeSDFromInput(String path, String fileName, InputStream inputStream) {
		File file = null;
		OutputStream outputStream = null;
		try {
			creatSDDir(path);
			file = creatFileInSDCard(fileName, path);
			outputStream = new FileOutputStream(file);
			byte buffer[] =  new byte[4 * 1024];
			int temp;
			while((temp = inputStream.read(buffer)) != -1){
				outputStream.write(buffer, 0, temp);
			}
			outputStream.flush();
		} catch (Exception e) {
		e.printStackTrace();
		}finally{
			try {
				outputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return file;
	}
}
















