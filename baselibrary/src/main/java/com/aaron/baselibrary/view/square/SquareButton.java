package com.aaron.baselibrary.view.square;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 *  正方形的 Button
 */
public final class SquareButton extends AppCompatButton {

    public SquareButton(Context context) {
        super(context);
    }

    public SquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(SquareDelegate.measureWidth(widthMeasureSpec, heightMeasureSpec),
                SquareDelegate.measureHeight(widthMeasureSpec, heightMeasureSpec));
    }
}