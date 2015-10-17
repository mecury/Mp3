package com.mecury.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mecury.modle.Mp3Info;

public class Mp3ListContentHandler extends DefaultHandler{

	private List<Mp3Info> infos = null;
	private Mp3Info mp3Info = null;
	private String TagName = null;
	
	//infosµÄ¹¹ÔìÆ÷
	public Mp3ListContentHandler(List<Mp3Info> infos) {
		super();
		this.infos = infos;
	}
	
	public List<Mp3Info> getInfos() {
		return infos;
	}
	public void setInfos(List<Mp3Info> infos) {
		this.infos = infos;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.TagName = localName;
		if (TagName.equals("resource")) {
			mp3Info =new Mp3Info();
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp = new String(ch, start, length);
		if (TagName.equals("id")) {
			mp3Info.setId(temp);
			System.out.println("XXXXXzzzzzz");
		}else if (TagName.equals("mp3.name")) {
			mp3Info.setMp3name(temp);
			System.out.println("XXXXX");
		}else if (TagName.equals("mp3.size")) {
			mp3Info.setMp3size(temp);
		}else if (TagName.equals("lrc.name")) {
			mp3Info.setLrcname(temp);
		}else if (TagName.equals("lrc.size")) {
			mp3Info.setLrcsize(temp);
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (qName.equals("resource")) {
			infos.add(mp3Info);
			System.out.println("aaaaaa");
		}
		TagName = "";
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
	
}
