package com.aaron.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/*
<!-- 获取验证码样式 -->
<style name="CountdownViewStyle">
    <item name="android:layout_width">wrap_content</item>
    <item name="android:layout_height">wrap_content</item>
    <item name="android:background">@drawable/selector_countdown</item>
    <item name="android:layout_marginLeft">15dp</item>
    <item name="android:layout_marginRight">15dp</item>
    <item name="android:paddingTop">10dp</item>
    <item name="android:paddingBottom">10dp</item>
    <item name="android:minWidth">90dp</item>
    <item name="android:gravity">center</item>
    <item name="android:text">@string/common_code_send</item>
    <item name="android:textColor">@drawable/selector_countdown_color</item>
    <item name="android:textSize">12sp</item>
</style>

<com.aaron.baselibrary.view.CountdownView
    android:id="@+id/cv_phone_reset_countdown"
    style="@style/CountdownViewStyle" />
 */
/**
 *  验证码倒计时
 */
public final class CountdownView extends AppCompatTextView implements Runnable {

    /** 倒计时秒数 */
    private int mTotalSecond = 60;
    /** 秒数单位文本 */
    private static final String TIME_UNIT = "S";

    /** 当前秒数 */
    private int mCurrentSecond;
    /** 记录原有的文本 */
    private CharSequence mRecordText;
    /** 标记是否重置了倒计控件 */
    private boolean mFlag;

    public CountdownView(Context context) {
        super(context);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置倒计时总秒数
     */
    public void setTotalTime(int totalTime) {
        this.mTotalSecond = totalTime;
    }

    /**
     * 重置倒计时控件
     */
    public void resetState() {
        mFlag = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 设置点击的属性
        setClickable(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        // 移除延迟任务，避免内存泄露
        removeCallbacks(this);
        super.onDetachedFromWindow();
    }

    @Override
    public boolean performClick() {
        boolean click = super.performClick();
        mRecordText = getText();
        setEnabled(false);
        mCurrentSecond = mTotalSecond;
        post(this);
        return click;
    }

    /**
     * {@link Runnable}
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void run() {
        if (mCurrentSecond == 0 || mFlag) {
            setText(mRecordText);
            setEnabled(true);
            mFlag = false;
        } else {
            mCurrentSecond--;
            setText(mCurrentSecond + " " + TIME_UNIT);
            postDelayed(this, 1000);
        }
    }
}