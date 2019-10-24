package com.aaron.baselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.aaron.baselibrary.R;

import java.util.regex.Pattern;

/*
<!-- 默认文本框样式 -->
<style name="EditTextStyle">
    <item name="android:background">@null</item>
    <item name="android:textSize">15sp</item>
    <item name="android:textColorHint">#A4A4A4</item>
    <item name="android:textColor">#333333</item>
    <item name="android:paddingTop">10dp</item>
    <item name="android:paddingBottom">10dp</item>
    <item name="android:paddingLeft">10dp</item>
    <item name="android:paddingRight">10dp</item>
</style>

<com.aaron.widgetlibrary.view.RegexEditText
    style="@style/EditTextStyle"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginLeft="40dp"
    android:layout_marginTop="20dp"
    android:layout_marginRight="40dp"
    android:hint="这里只能输入手机号（只能以 1 开头）"
    app:regexType="mobile" />

<!-- 正则输入限制 -->
<attr name="inputRegex" format="string" />
<!-- 常用正则类型 -->
<attr name="regexType" >
    <!-- 手机号（只能以 1 开头） -->
    <enum name="mobile" value="0x01" />
    <!-- 中文（普通的中文字符） -->
    <enum name="chinese" value="0x02" />
    <!-- 英文（大写和小写的英文） -->
    <enum name="english" value="0x03" />
    <!-- 整数（非 0 开头的数字） -->
    <enum name="count" value="0x04" />
    <!-- 用户名（中文、英文、数字） -->
    <enum name="name" value="0x05" />
    <!-- 非空格字符 -->
    <enum name="nonnull" value="0x06" />
</attr>
 */
/**
 *  正则输入限制编辑框
 */
public class RegexEditText extends AppCompatEditText implements InputFilter {

    /** 手机号（只能以 1 开头） */
    public static final String REGEX_MOBILE = "[1]\\d{0,10}";
    /** 中文（普通的中文字符） */
    public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]*";
    /** 英文（大写和小写的英文） */
    public static final String REGEX_ENGLISH = "[a-zA-Z]*";
    /** 计数（非 0 开头的数字） */
    public static final String REGEX_COUNT = "[1-9]\\d*";
    /** 用户名（中文、英文、数字） */
    public static final String REGEX_NAME = "[" + REGEX_CHINESE + "|" + REGEX_ENGLISH + "|" + "\\d*" + "]*";
    /** 非空格的字符（不能输入空格） */
    public static final String REGEX_NONNULL = "\\S+";

    /** 正则表达式规则 */
    private Pattern mPattern;

    public RegexEditText(Context context) {
        super(context);
        initialize(context, null);
    }

    public RegexEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public RegexEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    /**
     * 初始化属性
     */
    protected void initialize(Context context, AttributeSet attrs) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RegexEditText);

        if (array.hasValue(R.styleable.RegexEditText_inputRegex)) {
            setInputRegex(array.getString(R.styleable.RegexEditText_inputRegex));
        } else {
            if (array.hasValue(R.styleable.RegexEditText_regexType)) {
                int regexType = array.getInt(R.styleable.RegexEditText_regexType, 0);
                switch (regexType) {
                    case 0x01:
                        setInputRegex(REGEX_MOBILE);
                        break;
                    case 0x02:
                        setInputRegex(REGEX_CHINESE);
                        break;
                    case 0x03:
                        setInputRegex(REGEX_ENGLISH);
                        break;
                    case 0x04:
                        setInputRegex(REGEX_COUNT);
                        break;
                    case 0x05:
                        setInputRegex(REGEX_NAME);
                        break;
                    case 0x06:
                        setInputRegex(REGEX_NONNULL);
                        break;
                    default:
                        break;
                }
            }
        }

        array.recycle();
    }

    /**
     * 是否有这个输入标记
     */
    public boolean hasInputType(int type) {
        return (getInputType() & type) != 0;
    }

    /**
     * 添加一个输入标记
     */
    public void addInputType(int type) {
        setInputType(getInputType() | type);
    }

    /**
     * 移除一个输入标记
     */
    public void removeInputType(int type) {
        setInputType(getInputType() & ~type);
    }

    /**
     * 设置输入正则
     */
    public void setInputRegex(String regex) {
        if (regex == null || "".equals(regex)) {
            return;
        }

        mPattern = Pattern.compile(regex);
        addFilters(this);
    }

    /**
     * 获取输入正则
     */
    public String getInputRegex() {
        if (mPattern == null) {
            return null;
        }
        return mPattern.pattern();
    }

    /**
     * 添加筛选规则
     */
    public void addFilters(InputFilter filter) {
        if (filter == null) {
            return;
        }

        final InputFilter[] newFilters;
        final InputFilter[] oldFilters = getFilters();
        if (oldFilters != null && oldFilters.length > 0) {
            newFilters = new InputFilter[oldFilters.length + 1];
            // 复制旧数组的元素到新数组中
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
            newFilters[oldFilters.length] = filter;
        } else {
            newFilters = new InputFilter[1];
            newFilters[0] = filter;
        }
        super.setFilters(newFilters);
    }

    /**
     * {@link InputFilter}
     *
     * @param source        新输入的字符串
     * @param start         新输入的字符串起始下标，一般为0
     * @param end           新输入的字符串终点下标，一般为source长度-1
     * @param dest          输入之前文本框内容
     * @param dstart        原内容起始坐标，一般为0
     * @param dend          原内容终点坐标，一般为dest长度-1
     * @return              返回字符串将会加入到内容中
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (mPattern == null) {
            return source;
        }

        // 拼接出最终的字符串
        String begin = dest.toString().substring(0, dstart);
        String over = dest.toString().substring(dstart + (dend - dstart), dstart + (dest.toString().length() - begin.length()));
        String result = begin + source + over;

        // 判断是插入还是删除
        if (dstart > dend - 1) {
            if (mPattern.matcher(result).matches()) {
                // 如果匹配就允许这个文本通过
                return source;
            }
        } else {
            if (!mPattern.matcher(result).matches()) {
                // 如果不匹配则不让删除（删空操作除外）
                if (!"".equals(result)) {
                    return dest.toString().substring(dstart, dend);
                }
            }
        }

        // 注意这里不能返回 null，否则会和 return source 效果一致
        return "";
    }
}