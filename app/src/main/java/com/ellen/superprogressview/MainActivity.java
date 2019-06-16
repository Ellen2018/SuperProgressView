package com.ellen.superprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ellen.progressview.ProgressView;

public class MainActivity extends AppCompatActivity {

   private ProgressView progressView;
   private int p = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = findViewById(R.id.button_progress_green);
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
               progressView.setCurrentProgress(p);
               //progressView.setText(progressView.getCurrentProgress()+"\n"+"dsadasd");
            }
        });

    }
}
