package com.minwoo.project;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Exchange_task extends AsyncTask<String, String, String> {
    String urlStr = "https://api.manana.kr/exchange/rate/";

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        String line;
        try {
            urlStr = urlStr + strings[1] + "/" + strings[0] + ".json";
            URL url = new URL(urlStr);
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = bf.readLine()) != null) {
                result = result.concat(line);
                // System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public double jsonParsing(String json) {
        double money = 0.;
        try {

            JSONArray priceArray = new JSONArray(json);
            for (int i = 0; i < priceArray.length(); i++) {
                JSONObject getObject = priceArray.getJSONObject(i);
                // str = getObject.getString("");
                money = getObject.getDouble("rate");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return money;
    }

    public String jsonTime(String json) {
        String time = "";

        try {
            JSONArray priceArray = new JSONArray(json);
            for (int i = 0; i < priceArray.length(); i++) {
                JSONObject getObject = priceArray.getJSONObject(i);
                // str = getObject.getString("");
                time = getObject.getString("date");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "환율 기준 시간 : " + time;
    }
}
