package com.example.carl.customlettersidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterSideBar extends View {
    private String TAG = "chen";
    private String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private Paint mNormalPaint;
    private Paint mFocusPaint;
    private int mInterval=0;
    private int mCurrentLetter =0 ;
    private int mLetterHeight;
    private int mNormalBaseLine;
    private int mFocusBaseLine;
    private int mFocusLetterHeight;
    public LetterSideBar(Context context) {
        //super(context);
        this(context,null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array =context.obtainStyledAttributes(attrs,R.styleable.LetterSideBar);
        mInterval = array.getInt(R.styleable.LetterSideBar_interval,0);
        int normalColor = array.getColor(R.styleable.LetterSideBar_normalColor,0);
        int focusColor = array.getColor(R.styleable.LetterSideBar_focusColor,0);
        Log.i(TAG,"mInterval:"+mInterval);
        initNormalPain(normalColor);
        initFocusPain(focusColor);


        Rect bounds = new Rect();
        mNormalPaint.getTextBounds("A",0,1,bounds);
        int dy = (bounds.bottom-bounds.top)/2-bounds.bottom;
        mNormalBaseLine = bounds.height()/2+dy;
        Rect f_bounds = new Rect();
        mFocusPaint.getTextBounds("A",0,1,f_bounds);
        int f_dy = (f_bounds.bottom-f_bounds.top)/2-f_bounds.bottom;
        mFocusBaseLine = f_bounds.height()/2 +f_dy;
        mFocusLetterHeight = f_bounds.bottom-f_bounds.top;
        mLetterHeight = bounds.bottom-bounds.top;
    }

    private void initNormalPain(int color){
        mNormalPaint = new Paint();
        mNormalPaint.setColor(color);
        //抗锯齿
        mNormalPaint.setAntiAlias(true);
        //防抖动
        mNormalPaint.setDither(true);
        mNormalPaint.setTextSize(30);
    }

    private void initFocusPain(int color){
        mFocusPaint = new Paint();
        mFocusPaint.setColor(color);
        //抗锯齿
        mFocusPaint.setAntiAlias(true);
        //防抖动
        mFocusPaint.setDither(true);
        mFocusPaint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<letters.length;i++){
            Rect focus_char_bounds = new Rect();
            mFocusPaint.getTextBounds(letters[i],0,1,focus_char_bounds);
            Rect char_bounds = new Rect();
            mNormalPaint.getTextBounds(letters[i],0,1,char_bounds);
            if (i==mCurrentLetter) {
                int x = getWidth()-getPaddingRight()-focus_char_bounds.width()/2;
                canvas.drawText(letters[i], x, getPaddingTop() + (mNormalBaseLine + mInterval) * (i-1)+mFocusBaseLine+mInterval, mFocusPaint);
            }else if (i<mCurrentLetter){
                int x = getWidth()-getPaddingRight()-char_bounds.width()/2;
                canvas.drawText(letters[i], x, getPaddingTop() + (mNormalBaseLine + mInterval) * i, mNormalPaint);
            }else {
                int x = getWidth()-getPaddingRight()-char_bounds.width()/2;
                canvas.drawText(letters[i], x, getPaddingTop() + (mNormalBaseLine + mInterval) * (i-1)+2*mInterval+mFocusLetterHeight, mNormalPaint);
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度 一张图片的高度 ，自己实现 padding +间隔
        int height =(mLetterHeight+mInterval)*letters.length+getPaddingTop()+getPaddingBottom();
        Rect foucus_char_bounds = new Rect();
        mFocusPaint.getTextBounds("A",0,1,foucus_char_bounds);
        int width = foucus_char_bounds.width()+getPaddingLeft()+getPaddingRight();
        setMeasuredDimension(width,height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                mCurrentLetter = (int) moveY/(mLetterHeight +mInterval);
                if (mCurrentLetter<0){
                    mCurrentLetter = 0;
                }
                if (mCurrentLetter>=letters.length){
                    mCurrentLetter = letters.length-1;
                }
                Log.i(TAG,"moveY:"+moveY);
                Log.i(TAG,"moveY:"+moveY+",mLetterHeight+mInterval:"+(mLetterHeight +mInterval));
                Log.i(TAG,"index:"+mCurrentLetter+","+letters[mCurrentLetter]);
                mLetterTouchListener.touch(letters[mCurrentLetter]);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mLetterTouchListener.touchUp();
                break;
        }
        return true;
    }
    private LetterTouchListener mLetterTouchListener;
    public void setListener(LetterTouchListener listent){
        mLetterTouchListener = listent;
    };
    public interface LetterTouchListener{
        void touch(CharSequence letter);
        void touchUp();

    }

}
