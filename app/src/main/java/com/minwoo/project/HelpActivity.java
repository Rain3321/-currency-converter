package com.minwoo.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

public class HelpActivity extends AppCompatActivity {
    String[] items = {"은행선택", "국민은행", "신한은행", "기업은행", "하나은행"};
    Spinner spinner;
    int checkable;
    Button action_btn;
    String[] pakage = {"","com.kbstar.kbbank", "com.shinhan.sbanking", "com.ibk.android.ionebank", "com.hanabank.enk.channel.android.hananbank"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        spinner = findViewById(R.id.spinner);
        action_btn = findViewById(R.id.url_action_btn);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    checkable = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        action_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkable != 0)
                    checkApp(checkable);

            }
        });
    }
    public boolean getPackageList(int position){
        boolean isExist = false;
        PackageManager pkgManager = getPackageManager();
        List<ResolveInfo> mApps;
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = pkgManager.queryIntentActivities(mainIntent, 0);
        try{
            for(int i =0; i< mApps.size(); i++){
                if(mApps.get(i).activityInfo.packageName.startsWith(pakage[position])){
                    isExist = true;
                    break;
                }
            }
        }catch (Exception e ){
            isExist = false;
        }

        return isExist;
    }
    public void checkApp(int position){
        if(getPackageList(position)){
            Intent intent = getPackageManager().getLaunchIntentForPackage(pakage[position]);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else{
            String url = "market://details?id="+pakage[position];
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }
}
