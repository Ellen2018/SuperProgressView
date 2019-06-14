package com.ellen.superprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ellen.progressview.ProgressView;

public class MainActivity extends AppCompatActivity {

   private ProgressView progressView;
   private int p = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = findViewById(R.id.button_progress_green);
        progressView.setOnClickListener(new ProgressView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击了",Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = p + 10;
               progressView.setCurrentProgress(p);
            }
        });
    }
}
