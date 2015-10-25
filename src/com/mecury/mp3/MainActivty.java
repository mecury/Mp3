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
		
		//�õ�Tabhost��������TabAcitvity�Ĳ���ͨ�����д˶�������
		TabHost tabHost = getTabHost();
		//����һ��intent���󣬸ö�������һ��acitvity��
		Intent remoteintent = new Intent();
		remoteintent.setClass(this, Mp3ListActivity.class);
		//����һ��TabSpec����ÿ���������һ��ҳ
		TabHost.TabSpec remoteSpec = tabHost.newTabSpec("remote");
		Resources resources = getResources(); //����resources��Դ
		//����ҳ��indicator���������ƺ�ͼ��
		remoteSpec.setIndicator("remote",resources.getDrawable(R.drawable.libra));
		remoteSpec.setContent(remoteintent);
		tabHost.addTab(remoteSpec);
		
		//������һ��ҳ
		Intent localIntent = new Intent();
		localIntent.setClass(this, LocalMp3ListActivity.class);
		TabHost.TabSpec localSpec = tabHost.newTabSpec("local");
		localSpec.setIndicator("local",resources.getDrawable(R.drawable.sagittarius));
		localSpec.setContent(localIntent);
		tabHost.addTab(localSpec);
	}
}
