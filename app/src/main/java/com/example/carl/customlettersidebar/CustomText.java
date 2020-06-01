package com.example.carl.customlettersidebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class CustomText extends TextView {
    private Paint mTextPain;
    private Paint mBackgroundPain;
    private int mTextColor;
    private int mBackgroundColor;
    private int mRadius;
    public CustomText(Context context) {
        //super(context);
        this(context,null);
    }

    public CustomText(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }

    public CustomText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CustomText);
        mBackgroundColor = array.getColor(R.styleable.CustomText_BackgroundColor,0);
        mTextColor = array.getColor(R.styleable.CustomText_TextColor,0);
        mRadius = array.getInt(R.styleable.CustomText_BackgroundRadius,10);
        setBackgroundPain();
        setTextPain();

    }

    private void setBackgroundPain(){
        mBackgroundPain = new Paint();
        mBackgroundPain.setColor(mBackgroundColor);
        //抗锯齿
        mBackgroundPain.setAntiAlias(true);
        //防抖动
        mBackgroundPain.setDither(true);
    }
    private void setTextPain(){
        mTextPain = new Paint();
        mTextPain.setColor(mTextColor);
        //抗锯齿
        mTextPain.setAntiAlias(true);
        //防抖动
        mTextPain.setDither(true);
        mTextPain.setTextSize(getTextSize());
    }

    private void drawBackground(Canvas canvas){
        int x = getWidth()/2;
        int y = getHeight()/2;
        canvas.drawCircle(x,y,mRadius,mBackgroundPain);
    }
    private void drawText(Canvas canvas){
        String text = getText().toString();
        Rect bounds = new Rect();
        mTextPain.getTextBounds(text,0,text.length(),bounds);
        Paint.FontMetricsInt fontMetricsInt = mTextPain.getFontMetricsInt();
        //top 是一个负值 bottom是一给正值 top bottom是baseLine到文字底部的距离(正值)
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2+dy;
        int x = getWidth()/2 - bounds.width()/2;
        canvas.drawText(text,x,baseLine,mTextPain);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        drawBackground(canvas);
        drawText(canvas);
    }
}
