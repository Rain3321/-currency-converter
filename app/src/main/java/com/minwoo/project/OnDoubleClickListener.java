package com.minwoo.project;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewConfiguration;


public abstract class OnDoubleClickListener implements View.OnClickListener {
    private final int doubleClickTimeOut;
    private Handler handler;
    private long firstClickTime;
    public OnDoubleClickListener() {
        this.doubleClickTimeOut = ViewConfiguration.getDoubleTapTimeout();
        firstClickTime = 0L;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onClick(final View v) {
        long now = System.currentTimeMillis();

        if(now - firstClickTime < doubleClickTimeOut){
            handler.removeCallbacksAndMessages(null);
            firstClickTime = 0L;
            onDoubleClick(v);
        }else {
            firstClickTime = now;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onSingClick(v);
                    firstClickTime = 0L;
                }
            }, doubleClickTimeOut);
        }

    }

    public abstract void onDoubleClick(View v);
    public abstract void onSingClick(View v);

    public void reset(){
        handler.removeCallbacksAndMessages(null);
    }

}

