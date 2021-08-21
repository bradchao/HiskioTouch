package com.bradchao.hiskiotouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView status;
    private Button btn;
    private boolean isClicking;
    private Timer timer = new Timer();
    private ClickTask clickTask = new ClickTask();
    private int i;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        btn = findViewById(R.id.btn);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    status.setText("按下");
                    isClicking = true;
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    isClicking = false;
                    status.setText("放開");
                    i = 0;
                }
                return true;
            }
        });

        view = findViewById(R.id.view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();
                int index = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.v("bradlog", "第1:按下");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v("bradlog", "最後1:放開");
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.v("bradlog", "第"+ (index+1) + ":按下");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        Log.v("bradlog", "第"+ (index+1) + ":放開");
                        break;
                }


                return true;
            }
        });


        timer.schedule(clickTask, 0, 10);
    }

    private class ClickTask extends TimerTask {
        @Override
        public void run() {
            if (isClicking) {
                i++;
                uiHandler.sendEmptyMessage(0);
            }
        }
    }

    private UIHandler uiHandler = new UIHandler();
    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            status.setText("一直按..." + i/10);
        }
    }

    public void clickMe(View view) {
        status.setText("Click");
    }
}