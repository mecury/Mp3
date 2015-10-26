package com.mecury.sercive;

import java.io.File;

import com.mecury.modle.Mp3Info;
import com.mecury.mp3.AppConstants;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

public class PlayerService extends Service{
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isPlaying = false;
	private boolean isPause = false;
	private boolean isStop = false;
	
	private MediaPlayer mediaPlayer = null;
	private Mp3Info mp3Info;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
		int MSG = intent.getIntExtra("MSG", 0);
		if (MSG == AppConstants.PlayerMsg.PLAY_MSG) {
			play(mp3Info);
		}else if (MSG == AppConstants.PlayerMsg.PAUSE_MSG) {
			pause();
		}else if (MSG == AppConstants.PlayerMsg.STOP_MSG) {
			stop();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	private String getMp3Path(Mp3Info mp3Info) {
		String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = SDCardRoot + File.separator + "mp3" + File.separator + mp3Info.getMp3name();
		return path;
	}
	
	public void play(Mp3Info mp3Info) {
		if (!isPlaying) {
			
			String path = getMp3Path(mp3Info);
			mediaPlayer = MediaPlayer.create(this, Uri.parse("file://"+path));
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
			isPlaying = true;
			isStop = false;
		}
	}
	
	
	public void pause() {
		if (mediaPlayer!=null) {
			if (!isStop) {
				if (!isPause) {
					mediaPlayer.pause();
					isPause = true;
					isPlaying = false;
				}else {
					mediaPlayer.start();
					isPause = false;
				}
			}
		}
	}
	
	public void stop(){
		if (mediaPlayer != null) {
			if (isPlaying) {
				if (!isStop) {
				mediaPlayer.stop();
				mediaPlayer.release();
				isStop = true;
				}
				isPlaying = false;
			}
			
		}
	}
}
