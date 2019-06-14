package com.ellen.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProgressView extends RelativeLayout {

    private View viewBackground;
    private View viewProgressColor;
    private ProgressBar progressBar;
    private TextView textView;
    private float radius = 0f;
    private int bgColor = 0xffffff;
    private int progressColor = 0x000000;
    private int maxProgress = 100;
    private int currentProgress = 70;
    private int textColor = 0xffffff;
    private OnClickListener onClickListener;

    public ProgressView(Context context) {
        super(context);
        initView();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ProgressView_bg_color) {
                int color = typedArray.getColor(attr, this.bgColor);
                this.bgColor = color;
            } else if (attr == R.styleable.ProgressView_radius) {
                float radius = typedArray.getDimension(attr, this.radius);
                this.radius = radius;
            } else if (attr == R.styleable.ProgressView_progress_color) {
                int color = typedArray.getColor(attr, this.progressColor);
                this.progressColor = color;
            } else if (attr == R.styleable.ProgressView_maxProgress) {
                int maxProgress = typedArray.getInt(attr, this.maxProgress);
                this.maxProgress = maxProgress;
            } else if (attr == R.styleable.ProgressView_progress) {
                int currentProgress = typedArray.getInt(attr, this.currentProgress);
                this.currentProgress = currentProgress;
            }else if (attr == R.styleable.ProgressView_text_color) {
                int color = typedArray.getColor(attr, this.textColor);
                this.textColor = color;
            }
        }
        typedArray.recycle();
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.progress_layout, this);
        viewBackground = view.findViewById(R.id.view_background);
        progressBar = view.findViewById(R.id.progress_horizontal);
        viewProgressColor = view.findViewById(R.id.view_progress_color);
        textView = view.findViewById(R.id.text);
        GradientDrawable gradientDrawable1 = (GradientDrawable) viewBackground.getBackground();
        gradientDrawable1.setColor(bgColor);
        gradientDrawable1.setCornerRadius(radius);
        progressBar.setMax(this.maxProgress);
        textView.setTextColor(textColor);
        setProgressDrawable();
        progressBar.setProgress(this.currentProgress);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onClick(v);
                }
            }
        });
    }

    private void setProgressDrawable() {
        GradientDrawable gradientDrawable1 = (GradientDrawable) viewProgressColor.getBackground();
        gradientDrawable1.setColor(progressColor);
        gradientDrawable1.setCornerRadius(radius);
        ClipDrawable clipDrawable = new ClipDrawable(gradientDrawable1, Gravity.LEFT,ClipDrawable.HORIZONTAL);
        progressBar.setProgressDrawable(clipDrawable);
        progressBar.setProgress(currentProgress);
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress > maxProgress ? maxProgress : currentProgress;
        progressBar.setProgress(currentProgress);
    }

    public interface OnClickListener{
        void onClick(View view);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        progressBar.setMax(maxProgress);
    }

    public void setProgressColor(int color){
        this.progressColor = color;
        setProgressDrawable();
        progressBar.setProgress(currentProgress);
    }
}