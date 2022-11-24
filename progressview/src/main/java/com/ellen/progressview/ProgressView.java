package com.ellen.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
    private String textContent = "";
    private float textSize = 10f;
    private Boolean isWidthWrap = null;
    private Boolean isHeightWrap = null;
    private int textViewPadding;
    private int direction = 1;

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
            }else if(attr == R.styleable.ProgressView_text_padding){
                float padding = typedArray.getDimension(attr,this.textViewPadding);
                this.textViewPadding = (int) padding;
            }else if(attr == R.styleable.ProgressView_progress_direction){
                direction = typedArray.getInt(attr, 0);
            }
        }
        typedArray.recycle();
        initView();
    }

    private void initView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.progress_layout, this);
        viewBackground = view.findViewById(R.id.view_background);
        progressBar = view.findViewById(R.id.progress_horizontal);
        viewProgressColor = view.findViewById(R.id.view_progress_color);
        textView = view.findViewById(R.id.text);
        GradientDrawable gradientDrawable1 = (GradientDrawable) viewBackground.getBackground();
        gradientDrawable1.setColor(bgColor);
        gradientDrawable1.setCornerRadius(radius);
        progressBar.setMax(this.maxProgress);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setText(textContent);
        textView.setPadding(textViewPadding,textViewPadding,textViewPadding,textViewPadding);
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

    public void setBgColor(int bgColor){
        this.bgColor = bgColor;
        GradientDrawable gradientDrawable1 = (GradientDrawable) viewBackground.getBackground();
        gradientDrawable1.setColor(bgColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //view根据xml中layout_width和layout_height测量出对应的宽度和高度值，
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if(isWidthWrap == null){
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            if(layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT){
                isWidthWrap = false;
            }else if(layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT){
                isWidthWrap = true;
            }else {
                isWidthWrap = false;
            }
        }

        if(isHeightWrap == null){
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            if(layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT){
                isHeightWrap = false;
            }else if(layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                isHeightWrap = true;
            }else {
                isHeightWrap = false;
            }
        }

        //没有进行父宽高适配，padding适配
        switch (widthSpecMode){
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
        }

        switch (heightSpecMode){
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
        }
        if(isWidthWrap && isHeightWrap) {
            setMeasuredDimension(textView.getMeasuredWidth(), textView.getMeasuredHeight());
        }else if(isWidthWrap && !isHeightWrap){
            setMeasuredDimension(textView.getMeasuredWidth(),this.getMeasuredHeight());
            textView.setHeight(this.getMeasuredHeight());
        }else if(!isWidthWrap && isHeightWrap){
            setMeasuredDimension(this.getMeasuredWidth(),textView.getMeasuredHeight());
            textView.setWidth(this.getMeasuredWidth());
        }else {
            setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
            textView.setWidth(this.getMeasuredWidth());
            textView.setHeight(this.getMeasuredHeight());
        }
    }

    public float getTextWidth(){
        TextPaint paint = new TextPaint();
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(textContent);
    }

    public float getTextHeight (){
        TextPaint paint = new TextPaint();
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        Rect rect = new Rect();
        paint.measureText(textContent);
        paint.getTextBounds(textContent, 0, textContent.length(), rect);
        int h = rect.height();
        return h;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void setProgressDrawable() {
        GradientDrawable gradientDrawable1 = (GradientDrawable) viewProgressColor.getBackground();
        gradientDrawable1.setColor(progressColor);
        gradientDrawable1.setCornerRadius(radius);
        int d = Gravity.LEFT;
        int v = ClipDrawable.HORIZONTAL;
        if(direction == 1){
            d = Gravity.LEFT;
        }else if(direction == 2){
            d = Gravity.TOP;
            v = ClipDrawable.VERTICAL;
        }else if(direction == 3){
            d = Gravity.RIGHT;
        }else{
            d = Gravity.BOTTOM;
            v = ClipDrawable.VERTICAL;
        }
        ClipDrawable clipDrawable = new ClipDrawable(gradientDrawable1, d,v);
        progressBar.setProgressDrawable(clipDrawable);
        progressBar.setProgress(currentProgress);
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = Math.min(currentProgress, maxProgress);
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

    public TextView getTextView(){
        return textView;
    }
}