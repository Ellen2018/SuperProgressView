package com.ellen.superprogressview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ellen.progressview.ProgressView;

public class MainActivity extends AppCompatActivity {

   private ProgressView progressView;
   private int p = 0;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = findViewById(R.id.upload_data);
        progressView.setText("更新数据");
        progressView.setOnClickListener(new ProgressView.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = p + 10;
                if(p>100)
                    p = 10;
               progressView.setCurrentProgress(p);
               progressView.setText("进度："+p);
            }
        });
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p+=10;
                if(p>100){
                    p = 10;
                }
                progressView.setCurrentProgress(p);
                progressView.setText("进度："+p);
                mainHandler.postDelayed(this,1000);
            }
        },1000);
    }
}
