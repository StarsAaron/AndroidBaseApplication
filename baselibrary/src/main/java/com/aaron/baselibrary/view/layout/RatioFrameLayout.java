package com.aaron.baselibrary.view.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.aaron.baselibrary.R;

/*
<com.aaron.baselibrary.view.layout.RatioFrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/colorButtonPressed"
    app:sizeRatio="2">

    <androidx.appcompat.widget.AppCompatTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="这是一个宽高比 2:1 的FrameLayout"
        android:textColor="@color/white" />
</com.aaron.baselibrary.view.layout.RatioFrameLayout>
 */
/**
 *  按照比例显示的 FrameLayout
 *
 *  xml文件只需设置sizeRatio比例属性
 */
public final class RatioFrameLayout extends FrameLayout {

    /** 宽高比 */
    private float mSizeRatio;

    public RatioFrameLayout(Context context) {
        this(context, null);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout);
        mSizeRatio = array.getFloat(R.styleable.RatioFrameLayout_sizeRatio, 0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mSizeRatio != 0) {
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode != MeasureSpec.EXACTLY) {
                // 如果当前宽度是写死的，但是高度不写死
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSpecSize / mSizeRatio), MeasureSpec.EXACTLY);
            } else if (heightSpecMode == MeasureSpec.EXACTLY && widthSpecMode != MeasureSpec.EXACTLY) {
                // 如果当前高度是写死的，但是宽度不写死
                widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (heightSpecSize * mSizeRatio), MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
