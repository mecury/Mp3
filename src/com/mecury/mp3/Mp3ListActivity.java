package com.mecury.mp3;

import java.io.StringReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.mecury.download.HttpDownload;
import com.mecury.modle.Mp3Info;
import com.mecury.sercive.DownloadService;
import com.mecury.xml.Mp3ListContentHandler;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/*
 * 
 */
public class Mp3ListActivity extends ListActivity {

	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	String xml = null;
	private Handler handler;
	List<Mp3Info> mp3infos = new ArrayList<Mp3Info>();
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, UPDATE, 1, R.string.mp3list_update);
		menu.add(0, ABOUT, 2, R.string.mp3list_about);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_mp3_list);
		updateListView();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("item-------->" + item);
		
		if (item.getItemId() == UPDATE) {
			
			new Thread(downloadXMLRun).start();
			
			updateListView();
			
		}else if (item.getItemId() == ABOUT) {
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	private List<Mp3Info> parse(String xmlStr){
		List<Mp3Info> infos = new ArrayList<Mp3Info>();
		//获取一个SAXParseFactory的实例
		SAXParserFactory saxParserFactory= SAXParserFactory.newInstance();
		try {
			//通过上面的实例来xmlReader对象
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(infos);
			xmlReader.setContentHandler(mp3ListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator<Mp3Info> iterator = infos.iterator(); iterator.hasNext();) {
				 Mp3Info mp3Info = (Mp3Info) iterator.next();
				System.out.println(mp3Info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	/*		
	 * 下载线程
	 */
	Runnable downloadXMLRun = new Runnable() {
		
		@Override
		public void run() {
			xml = downloadXML("http://10.0.2.2:8088/mp3/resources.xml"); 
			//对xml文件进行解析，将解析出来的数据放入mp3infos中，最后将这些放入到list中
			 mp3infos= parse(xml);
			 System.out.println("xml-------->" + xml);
			 System.out.println(mp3infos);
			 //利用handle传递数据
			 Looper.prepare();
			 try {
			
			 Message message = new Message();
			 Bundle bundle = new Bundle();
			 bundle.putParcelableArrayList("list",(ArrayList<? extends Parcelable>) mp3infos);
			 message.setData(bundle);
			 handler.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 Looper.loop();
		}
	}; 
	public String downloadXML(String urlstr){
		HttpDownload httpDownload = new HttpDownload();
		String result = httpDownload.download(urlstr);
		return result ;
	}
	
	/*
	 * 更新Listview的重构方法
	 */
	public void updateListView(){
		new Thread(downloadXMLRun).start();
		//处理得到的message
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle bundle = msg.getData();
				List list2 = bundle.getParcelableArrayList("list");
				 mp3infos  =  new ArrayList<Mp3Info>();
				mp3infos = (List<Mp3Info>)list2;
				System.out.println(mp3infos);
			buildSimpleAdapter(mp3infos);
			}
		};
	}
	
	public SimpleAdapter buildSimpleAdapter (List<Mp3Info> mp3Infos){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
			System.out.println("1");
			//迭代器
			for (Iterator<Mp3Info> iterator = mp3Infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Infoo = (Mp3Info) iterator.next();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("mp3_name", mp3Infoo.getMp3name());
				map.put("mp3_size", mp3Infoo.getMp3size());
				list.add(map);
			}
			SimpleAdapter simpleAdapter = new SimpleAdapter(Mp3ListActivity.this, list, R.layout.mp3info_item, new String[]{"mp3_name","mp3_size"}, 
					new int[]{R.id.mp3_name,R.id.mp3_size});
			setListAdapter(simpleAdapter);
		return simpleAdapter;
	}
	
	/*监听list的点击事件
	 * (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Mp3Info mp3Info = mp3infos.get(position);
		System.out.println("mp3info-------->"+ mp3Info);
		Intent intent = new Intent();
		intent.putExtra("mp3Info", mp3Info);
		intent.setClass(this, DownloadService.class);
		startService(intent);
		super.onListItemClick(l, v, position, id);
	}
}
