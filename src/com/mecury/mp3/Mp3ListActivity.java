package com.mecury.mp3;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.mecury.download.HttpDownload;
import com.mecury.modle.Mp3Info;
import com.mecury.xml.Mp3ListContentHandler;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Mp3ListActivity extends ListActivity {

	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	String xml = null;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, UPDATE, 1, R.string.mp3list_update);
		menu.add(0, ABOUT, 2, R.string.mp3list_about);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("item-------->" + item);
		if (item.getItemId() == UPDATE) {
			new Thread(downloadXMLRun).start();
		}else if (item.getItemId() == ABOUT) {
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	private List<Mp3Info> parse(String xmlStr){
		System.out.println("4");
		//获取一个SAXParseFactory的实例
		SAXParserFactory saxParserFactory= SAXParserFactory.newInstance();
		try {
			//通过上面的实例来xmlReader对象
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			List<Mp3Info> infos = new ArrayList<Mp3Info>();
			System.out.println("1");
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(infos);
			System.out.println("5");
			xmlReader.setContentHandler(mp3ListContentHandler);
			System.out.println("2");
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			System.out.println("3");
			for (Iterator<Mp3Info> iterator = infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Info = (Mp3Info) iterator.next();
				System.out.println(mp3Info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * 下载线程
	 */
	Runnable downloadXMLRun = new Runnable() {
		
		@Override
		public void run() {
			xml = downloadXML("http://10.0.2.2:8088/mp3/resources.xml");
			
			parse(xml);
			System.out.println("xml-------->" + xml);
		}
	}; 
	public String downloadXML(String urlstr){
		HttpDownload httpDownload = new HttpDownload();
		String result = httpDownload.download(urlstr);
		return result ;
	}
}
