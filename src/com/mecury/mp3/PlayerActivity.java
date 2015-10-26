package com.mecury.mp3;

import java.io.File;

import com.mecury.modle.Mp3Info;
import com.mecury.sercive.PlayerService;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PlayerActivity extends Activity{
	
	private ImageButton beginButton ;
	private ImageButton pauseButton ;
	private ImageButton stopButton;
	
	Mp3Info mp3Info;
	private int MSG;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		Intent intent = getIntent();
		mp3Info = (Mp3Info) intent.getSerializableExtra("mp3info");
		
		
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
			System.out.println("MP3info++++++++??????> " + mp3Info);
			System.out.println("1");
			MSG = AppConstants.PlayerMsg.PLAY_MSG;
			sendIntent(mp3Info,MSG);
	}
}
	//ÔÝÍ£pause
	class pauseButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			MSG = AppConstants.PlayerMsg.PAUSE_MSG;
			sendIntent(mp3Info,MSG);
		}
		
	}
	//Í£Ö¹stop
	class stopButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			MSG = AppConstants.PlayerMsg.STOP_MSG;
			sendIntent(mp3Info,MSG);
		}
		
	}
	
	private void sendIntent(Mp3Info mp3Info, int MSG){
		System.out.println("11");
		Intent intent = new Intent();
		System.out.println("111");
		intent.putExtra("MSG", MSG);
		System.out.println("1111");
		intent.putExtra("mp3Info", mp3Info);
		System.out.print("mp3info++++++++++++>"+mp3Info);
		intent.setClass(this, PlayerService.class);
		startService(intent);
	}
	

}
