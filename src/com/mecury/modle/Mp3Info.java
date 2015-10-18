package com.mecury.modle;

import android.os.Parcel;


public class Mp3Info implements Parcelable{

	private String id;
	private String mp3name;
	private String mp3size;
	private String lrcname;
	private String lrcsize;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMp3name() {
		return mp3name;
	}
	public void setMp3name(String mp3name) {
		this.mp3name = mp3name;
	}
	public String getMp3size() {
		return mp3size;
	}
	public void setMp3size(String mp3size) {
		this.mp3size = mp3size;
	}
	public String getLrcname() {
		return lrcname;
	}
	public void setLrcname(String lrcname) {
		this.lrcname = lrcname;
	}
	public String getLrcsize() {
		return lrcsize;
	}
	public void setLrcsize(String lrcsize) {
		this.lrcsize = lrcsize;
	}
	public Mp3Info(String id, String mp3name, String mp3size, String lrcname,
			String lrcsize) {
		super();
		this.id = id;
		this.mp3name = mp3name;
		this.mp3size = mp3size;
		this.lrcname = lrcname;
		this.lrcsize = lrcsize;
	}
	public String toString() {
		return "Mp3Info [id=" + id + ", mp3name=" + mp3name + ", mp3size="
				+ mp3size + ", lrcname=" + lrcname + ", lrcsize=" + lrcsize
				+ "]";
	}
	public Mp3Info() {
		super();
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(mp3name);
		dest.writeString(mp3size);
		dest.writeString(lrcname);
		dest.writeString(lrcsize);
		
	}
	public static final Parcelable.Creator<Mp3Info> CREATOR  = new Parcelable.Creator<Mp3Info>() {

		@Override
		public Mp3Info createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Mp3Info(source);
		}

		@Override
		public Mp3Info[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Mp3Info[size];
		}
	};
	
	public Mp3Info(Parcel source){
		id = source.readString();
		mp3name = source.readString();
		mp3size = source.readString();
		lrcname = source.readString();
		lrcsize = source.readString();
	}
}
