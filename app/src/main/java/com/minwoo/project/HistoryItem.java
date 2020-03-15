package com.minwoo.project;

public class HistoryItem {
    private int iconId1, iconId2;
    private String money1, money2;
    private String currency1, currency2;

    public HistoryItem(int i1, int i2, String m1, String m2, String c1, String c2){
        iconId1 = i1;
        iconId2 = i2;
        money1 = m1;
        money2 = m2;
        currency1 = c1;
        currency2 = c2;
    }

    public int getIconId1() {
        return iconId1;
    }
    public int getIconId2() {
        return iconId2;
    }
    public String getCurrency1() {
        return currency1;
    }
    public String getCurrency2() {
        return currency2;
    }
    public String getMoney1() {
        return money1;
    }
    public String getMoney2() {
        return money2;
    }

    public void setIconId1(int id){this.iconId1 = id;}
    public void setIconId2(int id){this.iconId2 = id;}
    public void setMoney1(String m){this.money1 = m;}
    public void setMoney2(String m){this.money2 = m;}
    public void setCurrency1(String c){this.currency1 = c;}
    public void setCurrency2(String c){this.currency2 = c;}

}
