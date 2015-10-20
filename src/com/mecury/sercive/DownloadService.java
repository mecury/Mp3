package com.mecury.sercive;

import com.mecury.download.HttpDownload;
import com.mecury.modle.Mp3Info;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
	/*����ʼ�ķ���
	 * (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Mp3Info mp3Info = (Mp3Info)(intent.getSerializableExtra("mp3Info"));
		System.out.println("intent ------->" + mp3Info);
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * �����߳�
	 */
	class DownloadThread extends Thread{
		private Mp3Info mp3Info;
		public DownloadThread(Mp3Info mp3Info){
			this.mp3Info = mp3Info;
		}
		@Override
		public void run() {
			/*
			 * �������ص����ƽ������ص�ַ
			 * �������ص��ļ�д��sd����
			 */
			String mp3Url = "http://10.0.2.2:8088/mp3/" + mp3Info.getMp3name();
			HttpDownload httpDownload = new HttpDownload();
			int result = httpDownload.downloadFile(mp3Url, "mp3/", mp3Info.getMp3name());
			String resultMessage = null;
			if (result == -1) {
				resultMessage = "����ʧ��";
			}else if(result == 0){
				resultMessage = "�ļ��Ѵ���";
			}else if (result == 1) {
				resultMessage = "���سɹ� ";
			}
		}
	}
}






