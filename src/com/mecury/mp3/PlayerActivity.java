package com.mecury.mp3;

import java.io.File;

import com.mecury.modle.Mp3Info;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PlayerActivity extends Activity{
	
	private ImageButton beginButton ;
	private ImageButton pauseButton ;
	private ImageButton stopButton;
	
	private boolean isPlaying = false;
	private boolean isPause = false;
	private boolean isStop = false;
	
	private MediaPlayer mediaPlayer = null;
	private Mp3Info mp3Info = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		Intent intent = getIntent();
		mp3Info = (Mp3Info) intent.getSerializableExtra("mp3info");
		System.out.print("mp3info3------->" + mp3Info);
		
		beginButton = (ImageButton) findViewById(R.id.playing);
		pauseButton = (ImageButton) findViewById(R.id.pause);
		stopButton = (ImageButton) findViewById(R.id.stop);
		beginButton.setOnClickListener(new BeginButtonListener());
		pauseButton.setOnClickListener(new pauseButtonListener());
		stopButton.setOnClickListener(new stopButtonListener());
	}
	
	
	//²¥·Åbegin
	class BeginButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if (!isPlaying) {
				
				String path = getMp3Path(mp3Info);
				mediaPlayer = MediaPlayer.create(PlayerActivity.this, Uri.parse("file://"+path));
				mediaPlayer.setLooping(false);
				mediaPlayer.start();
				isPlaying = true;
				isStop = false;
			}
		}
	}
	//ÔÝÍ£pause
	class pauseButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
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
		
	}
	//Í£Ö¹stop
	class stopButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
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
	
	private String getMp3Path(Mp3Info mp3Info){
		String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = SDCardRoot  + File.separator + "mp3" + File.separator + mp3Info.getMp3name();
		System.out.println(path);
		return path;
	}
}
