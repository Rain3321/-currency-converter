package com.minwoo.project;


import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("Serial")
public class ListItem implements Parcelable {
    private int iconId;
    private String countryStr;


    public ListItem(){}
    public ListItem(Parcel in){
        readFromParcel(in);
    }
    public ListItem(int icon, String text1){
        iconId = icon;
        countryStr = text1;
    }

    public int getIconId(){
        return this.iconId;
    }
    public String getCountryStr(){
        return this.countryStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(iconId);
        dest.writeString(countryStr);
    }
    private void readFromParcel(Parcel in){

        iconId = in.readInt();
        countryStr = in.readString();
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}
