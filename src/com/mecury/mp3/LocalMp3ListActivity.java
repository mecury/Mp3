package com.mecury.mp3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mecury.Utils.FileUtils;
import com.mecury.modle.Mp3Info;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemSelectedListener;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LocalMp3ListActivity extends ListActivity{

	private List<Mp3Info> mp3Infos = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_mp3_list);
	}
	@Override
	protected void onResume() {
		FileUtils fileUtils = new FileUtils();
		 mp3Infos = fileUtils.getMp3Files("mp3/");
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Iterator<Mp3Info> iterator = mp3Infos.iterator(); iterator.hasNext();) {
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3name());
			map.put("mp3_size", mp3Info.getMp3size());
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.mp3info_item, 
				new String[]{"mp3_name", "mp3_size"},new int[]{R.id.mp3_name, R.id.mp3_size} );
		setListAdapter(simpleAdapter);
		super.onResume();
	}
	
	/*
	 * 当点击已下载界面的list时，播放此音乐
	 * (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (mp3Infos!=null) {
			Mp3Info mp3Info = mp3Infos.get(position);
		Intent intent = new Intent();
		intent.putExtra("mp3info", mp3Info);
		intent.setClass(this, PlayerActivity.class);
		startActivity(intent);
		}
		
	}
}
