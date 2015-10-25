package com.mecury.mp3;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivty extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//得到Tabhost对象，正对TabAcitvity的操作通常都有此对象的完成
		TabHost tabHost = getTabHost();
		//生成一个intent对象，该对象与另一个acitvity绑定
		Intent remoteintent = new Intent();
		remoteintent.setClass(this, Mp3ListActivity.class);
		//生成一个TabSpec对象，每个对象代表一个页
		TabHost.TabSpec remoteSpec = tabHost.newTabSpec("remote");
		Resources resources = getResources(); //调用resources资源
		//设置页的indicator，包括名称和图标
		remoteSpec.setIndicator("remote",resources.getDrawable(R.drawable.libra));
		remoteSpec.setContent(remoteintent);
		tabHost.addTab(remoteSpec);
		
		//创建另一个页
		Intent localIntent = new Intent();
		localIntent.setClass(this, LocalMp3ListActivity.class);
		TabHost.TabSpec localSpec = tabHost.newTabSpec("local");
		localSpec.setIndicator("local",resources.getDrawable(R.drawable.sagittarius));
		localSpec.setContent(localIntent);
		tabHost.addTab(localSpec);
	}
}
