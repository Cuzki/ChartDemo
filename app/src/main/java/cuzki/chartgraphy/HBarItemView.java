/**
 * Created on 2017/1/10
 */
package cuzki.chartgraphy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class HBarItemView extends View {

    private HBarChartData mData;
    private float mTextW, mChartH, mMaxV;

    private Paint arcPaint = null;

    public HBarItemView(Context context) {
        super(context);
    }

    public HBarItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HBarItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null) {
            return;
        }
        // 画文字
        drawText(canvas, mData.getName());

        //画图形
        drawLine(canvas);

    }

    /**绘制图形
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        double chart_length = (getWidth() - mTextW) / (double) mMaxV;
        float start_complete_left = mTextW + 10,
                start_complete_top = 4,
                start_complete_right = start_complete_left + (int) (chart_length * mData.getRecover_complete())- dip2px(getContext(), 6),
                start_uncomplete_right = start_complete_left + (int) (chart_length * (mData.getRecover_complete() + mData.getRecover_uncomplete()))- dip2px(getContext(), 6);

        Log.e("TAG", start_complete_left + "..." + start_complete_top + ",,," + start_complete_right + "ds"
                + start_uncomplete_right);
        this.arcPaint = new Paint();
        this.arcPaint.setColor(getResources().getColor(R.color.line_chart_uncomplete));
        this.arcPaint.setAntiAlias(true);// 去除锯齿
        // 绘制未完成的，
        canvas.drawRect(start_complete_left, start_complete_top, start_uncomplete_right, mChartH, arcPaint);

        // 绘制完成的
        this.arcPaint.setColor(getResources().getColor(R.color.line_chart_complete));
        canvas.drawRect(start_complete_left, start_complete_top, start_complete_right, mChartH, arcPaint);
    }

    /**
     *绘制文字说明  右对齐
     * @param canvas
     * @param text
     */
    private void drawText(Canvas canvas, String text) {
        int x = getWidth();
        int y = getHeight();

        Paint textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.text_line_chart));
        textPaint.setTextSize(dip2px(getContext(), 16));
        // 设置文字右对齐
        textPaint.setTextAlign(Paint.Align.RIGHT);
        float tX = (x - getFontlength(textPaint, text)) / 2;
        float tY = (y - getFontHeight(textPaint)) / 2 + getFontLeading(textPaint);
        // 注意第二个参数，右对齐，文字是从右开始写的，那么  x 就是对齐处的X坐标
        canvas.drawText(text, mTextW, tY, textPaint);
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    public void setData(float textW, float chartW, float max_valur, HBarChartData data) {
        Log.e("TAG", max_valur + "...max");
        this.mTextW = textW;
        this.mChartH = chartW;
        this.mMaxV = max_valur;
        this.mData = data;
        this.postInvalidate();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
