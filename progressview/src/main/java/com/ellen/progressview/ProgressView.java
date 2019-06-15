package com.ellen.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
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
    private String textContent;
    private float textSize = 10f;
    private RelativeLayout relativeLayout;
    private boolean isInit = false;

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
            }else if(attr == R.styleable.ProgressView_text){
                String text = typedArray.getString(attr);
                this.textContent = text;
            }else if(attr == R.styleable.ProgressView_text_size){
                float textSize = typedArray.getDimension(attr, this.textSize);
                this.textSize = textSize;
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
        relativeLayout = view.findViewById(R.id.relative_layout);
        textView = view.findViewById(R.id.text);
        GradientDrawable gradientDrawable1 = (GradientDrawable) viewBackground.getBackground();
        gradientDrawable1.setColor(bgColor);
        gradientDrawable1.setCornerRadius(radius);
        progressBar.setMax(this.maxProgress);
        textView.setTextColor(textColor);
        textView.setText(textContent);
        textView.setTextSize(textSize);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //view根据xml中layout_width和layout_height测量出对应的宽度和高度值，
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthSpecMode){
            case MeasureSpec.UNSPECIFIED:
                isInit = false;
                break;
            case MeasureSpec.AT_MOST://wrap_content时候
                isInit = true;
                break;
            case MeasureSpec.EXACTLY:
                isInit = false;
                break;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(isInit){
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) relativeLayout.getLayoutParams();
            layoutParams.width = textView.getWidth();
            layoutParams.height = textView.getHeight();
            relativeLayout.setLayoutParams(layoutParams);
        }else {
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
            layoutParams.height = getHeight();
            layoutParams.width = getWidth();
            textView.setLayoutParams(layoutParams);
        }
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

    public void setText(String text){
        this.textContent = text;
        textView.setText(text);
    }

    public int getCurrentProgress(){return currentProgress;}
}