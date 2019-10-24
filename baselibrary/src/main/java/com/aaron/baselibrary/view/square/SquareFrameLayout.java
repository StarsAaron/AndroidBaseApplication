package com.aaron.baselibrary.view.square;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/*
<com.aaron.baselibrary.view.square.SquareFrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    android:background="@color/colorButtonPressed">

    <androidx.appcompat.widget.AppCompatTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="这是一个正方形的FrameLayout"
        android:textColor="@color/white" />

</com.aaron.baselibrary.view.square.SquareFrameLayout>
 */
/**
 *  正方形的 FrameLayout
 */
public final class SquareFrameLayout extends FrameLayout {

    public SquareFrameLayout(Context context) {
        super(context);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(SquareDelegate.measureWidth(widthMeasureSpec, heightMeasureSpec),
                SquareDelegate.measureHeight(widthMeasureSpec, heightMeasureSpec));
    }
}