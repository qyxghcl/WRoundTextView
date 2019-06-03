package com.a50647.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.WRoundTextView.R;

/**
 * 具有圆角功能的textView
 *
 * @author wm
 * @date 2019/6/3
 */
public class RoundTextView extends AppCompatTextView {
    private Context mContext;
    private Bitmap mTextBitmap;
    private Canvas mTextCanvas;
    private Paint mTextPaint;
    private int mBgColorNormal, mBgColorSelect,
            mStartColor, mEndColor,
            mWidth, mHeight, mAngle,
            mStrokeColorNormal, mStrokeColorSelect;
    private float mRadius, mLeftTopRadius, mLeftBottomRadius, mRightTopRadius, mRightBottomRadius, mStrokeWidth;
    private boolean mHalfRadius, mSelected;

    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        initPaintAndRectF();
    }

    @SuppressLint("CustomViewStyleable")
    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.WidgetRoundTextView);
            mBgColorNormal = arr.getColor(R.styleable.WidgetRoundTextView_bgColorNormal, 0);
            mBgColorSelect = arr.getColor(R.styleable.WidgetRoundTextView_bgColorSelect, 0);
            mStrokeColorNormal = arr.getColor(R.styleable.WidgetRoundTextView_strokeColorNormal, 0);
            mStrokeColorSelect = arr.getColor(R.styleable.WidgetRoundTextView_strokeColorSelect, 0);
            mStartColor = arr.getColor(R.styleable.WidgetRoundTextView_startColor, 0);
            mEndColor = arr.getColor(R.styleable.WidgetRoundTextView_endColor, 0);
            mRadius = arr.getDimension(R.styleable.WidgetRoundTextView_radius, 0);
            mLeftTopRadius = arr.getDimension(R.styleable.WidgetRoundTextView_leftTopRadius, 0);
            mLeftBottomRadius = arr.getDimension(R.styleable.WidgetRoundTextView_leftBottomRadius, 0);
            mRightTopRadius = arr.getDimension(R.styleable.WidgetRoundTextView_rightTopRadius, 0);
            mRightBottomRadius = arr.getDimension(R.styleable.WidgetRoundTextView_rightBottomRadius, 0);
            mStrokeWidth = arr.getDimension(R.styleable.WidgetRoundTextView_strokeWidth, 0);
            mAngle = arr.getInt(R.styleable.WidgetRoundTextView_angle, 0);
            mHalfRadius = arr.getBoolean(R.styleable.WidgetRoundTextView_halfRadius, false);
            arr.recycle();
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaintAndRectF() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createTextCanvas(w, h);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 创建一个canvas,用于替换原本的画布
     *
     * @param w 宽
     * @param h 高
     */
    private void createTextCanvas(int w, int h) {
        mTextBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mTextCanvas = new Canvas(mTextBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(mTextCanvas);
        drawBackground(canvas);
        canvas.drawBitmap(mTextBitmap, 0, 0, mTextPaint);
    }

    /**
     * 绘制背景
     *
     * @param canvas textView的原始画布
     */
    private void drawBackground(Canvas canvas) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        //定义角度 优先级half>radius>leftRadius
        float radius;
        if (mHalfRadius || mRadius > 0) {
            if (mHalfRadius) {
                radius = Math.max(mWidth, mHeight);
            } else {
                radius = mRadius;
            }
            gradientDrawable.setCornerRadius(radius);
        } else if (mLeftTopRadius > 0
                || mLeftBottomRadius > 0
                || mRightBottomRadius > 0
                || mRightTopRadius > 0) {
            float[] radii = {mLeftTopRadius, mLeftTopRadius,
                    mRightTopRadius, mRightTopRadius,
                    mRightBottomRadius, mRightBottomRadius,
                    mLeftBottomRadius, mLeftBottomRadius};
            gradientDrawable.setCornerRadii(radii);
        }
        //设置stroke
        if (mStrokeWidth != 0) {
            if (mSelected) {
                if (mStrokeColorSelect != 0) {
                    gradientDrawable.setStroke((int) mStrokeWidth, mStrokeColorSelect);
                }
            } else {
                if (mStrokeColorNormal != 0) {
                    gradientDrawable.setStroke((int) mStrokeWidth, mStrokeColorNormal);
                }
            }
        }
        //设置颜色
        if (mBgColorNormal != 0) {
            if (mSelected) {
                if (mBgColorSelect != 0) {
                    gradientDrawable.setColor(mBgColorSelect);
                }
            } else {
                gradientDrawable.setColor(mBgColorNormal);
            }
        } else if (mStartColor != 0 && mEndColor != 0) {
            int[] colors = {mStartColor, mEndColor};
            gradientDrawable.setColors(colors);
            gradientDrawable.setOrientation(getOrientation(mAngle));
        }
        gradientDrawable.setBounds(0, 0, mWidth, mHeight);
        gradientDrawable.draw(canvas);
    }

    /**
     * 设置选中状态
     *
     * @param select true:选中 false:未选中
     */
    public void setRoundTextViewSelect(boolean select) {
        setSelected(select);
        mSelected = select;
        postInvalidate();
    }

    /**
     * 获取GradientDrawable.Orientation 0-315逆时针旋转
     *
     * @param gradientOrientation 参数
     * @return GradientDrawable.Orientation
     */
    private GradientDrawable.Orientation getOrientation(int gradientOrientation) {
        GradientDrawable.Orientation orientation;
        switch (gradientOrientation) {
            case 0:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 45:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 90:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 135:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 180:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 225:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 270:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 315:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
            default:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
        }
        return orientation;
    }
}
