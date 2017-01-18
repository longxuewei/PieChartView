package cn.lxw.piechartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 源代码: Lxw
 * 伊妹儿: China2021@126.com
 * 时间轴: 2017 年 01 月 18 日 15 : 55
 */

public class PieChartView extends View {

    private int mWidth, mHeight;
    private int defColor = Color.parseColor("#0094FF");
    private Paint paint;
    private float currentAngle = 0;
    private static final int defWidth = 300;
    private static final int defHeight = 300;
    private List<Float> ratio = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();

    public PieChartView(Context context) {
        super(context);
        initPaint();
    }


    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (colors == null || colors.size() == 0)
            colors.add(defColor);
        if (ratio == null || ratio.size() == 0) {
            ratio.add(100f);
        }
        canvas.translate(mWidth / 2, mHeight / 2);
        for (int i = 0; i < ratio.size(); i++) {
            paint.setColor(colors.get(i));
            canvas.drawArc(getRectF(), currentAngle, 360 * (ratio.get(i) / 100), true, paint);
            currentAngle += 360 * (ratio.get(i) / 100);
        }
    }

    private RectF getRectF() {
        return new RectF(-(mWidth / 2), -(mHeight / 2), mWidth / 2, mHeight / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    public void setColorAndRatio(Map<Integer, Float> colorAndRatio) {
        if (colorAndRatio == null || colorAndRatio.size() <= 0)
            return;
        ratio.clear();
        colors.clear();
        for (Map.Entry<Integer, Float> data : colorAndRatio.entrySet()) {
            ratio.add(data.getValue());
            colors.add(data.getKey());
        }
        refresh();
    }


    private void refresh() {
        currentAngle = 0;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = getMeasureWidth(widthMeasureSpec);
        int heightSize = getMeasureHeight(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getMeasureWidth(int width) {
        int widthMode = MeasureSpec.getMode(width);
        int widthSize = MeasureSpec.getSize(width);
        int result;
        if (widthMode == MeasureSpec.EXACTLY) {
            result = widthSize;
        } else {
            result = defWidth;
            if (widthMode == MeasureSpec.AT_MOST) {
                result = Math.min(widthSize, result);
            }
        }
        return result;
    }

    private int getMeasureHeight(int height) {
        int heightMode = MeasureSpec.getMode(height);
        int heightSize = MeasureSpec.getSize(height);
        int result;
        if (heightMode == MeasureSpec.EXACTLY) {
            result = heightSize;
        } else {
            result = defHeight;
            if (heightMode == MeasureSpec.AT_MOST) {
                result = Math.min(heightSize, result);
            }
        }
        return result;
    }
}
