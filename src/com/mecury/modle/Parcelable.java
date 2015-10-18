package com.mecury.modle;

import android.os.Parcel;

public interface Parcelable {
	
	public int describeContents();
	
	public void writeToParcel(Parcel dest, int flags);
	
	public interface Creator<T>{
		public T createFromParcel(Parcel source);
		public T[] newArray(int size);
	}
}
