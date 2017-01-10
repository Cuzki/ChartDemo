/**
 * Created on 2017/1/10
 */
package cuzki.chartgraphy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class HBarChartView  extends View {

    /**
     *  列表的数据源
     */
    private List<HBarChartData> mData;

    IChartDataProvider mDataProvider;

    Paint paintLabel;
    Paint paintBar;

    /**
     * 屏幕的宽
     *
     */
    private float scrW;

    public HBarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HBarChartView(Context context) {
        super(context);
        init();
    }

    private void init(){
        paintLabel = new Paint();
        paintBar = new Paint();

        paintLabel.setAntiAlias(true);
        paintBar.setAntiAlias(true);

        paintBar.setStyle(Paint.Style.FILL_AND_STROKE);
        paintLabel.setTextSize(Utils.sp2px(getResources(), 10));
        paintLabel.setTextAlign(Paint.Align.RIGHT);
        paintLabel.setColor(Color.parseColor("#000000"));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width=getWidth()-getPaddingLeft()-getPaddingRight();
        float height=getHeight()-getPaddingTop()-getPaddingBottom();
        float barWidth=width;
        float unitDistance=0;
        if(mDataProvider==null||mDataProvider.isEmpty()){
            return;
        }
        final int count=mDataProvider.getDateCount();
        float maxValue=0;
        float maxLabelWidth=0;
        for(int i=0;i<count;i++){
            float totleValue=0;
            for(int j=0;j<mDataProvider.getChildCount();j++){
                totleValue+=mDataProvider.getValue(i,j);
            }
            if(maxValue<totleValue){
                maxValue=totleValue;
            }
            int[] res=getTextWidth(mDataProvider.getCoordinateLabel(i));
            int labelWidth=res[0];
            if(maxLabelWidth<labelWidth){
                maxLabelWidth=labelWidth;
            }
        }
        float labelMarginRight=Utils.dp2px(getResources(),4);//label右间距
        maxLabelWidth+=labelMarginRight;
        if(maxLabelWidth>width/8){//最大不超过宽度的1/8
            maxLabelWidth=width/8;
        }
        if(maxValue!=0){
            unitDistance=barWidth/maxValue;
        }
        float offset=Utils.dp2px(getResources(),4);
        float barHeight=(height-offset*(count+1))/count;
        float lineBarStartX=maxLabelWidth;
        float lineBarStartY=offset;

        Paint.FontMetricsInt fmi = paintLabel.getFontMetricsInt();
        for(int i=0;i<count;i++){
            //画bar
            float startX=lineBarStartX;
            for(int j=0;j<mDataProvider.getChildCount();j++){
                paintBar.setColor(mDataProvider.getChildColor(i,j));
                float diatance=mDataProvider.getValue(i,j)*unitDistance;
                canvas.drawRect(startX,lineBarStartY,startX+diatance,lineBarStartY+barHeight,paintBar);
                startX+=+diatance;
            }
            //画label
            canvas.drawText(mDataProvider.getCoordinateLabel(i), lineBarStartX-labelMarginRight, lineBarStartY+barHeight/2+(fmi.bottom-fmi.top)/2-fmi.bottom, paintLabel);
            lineBarStartY=(lineBarStartY+barHeight+offset);
        }

    }

    /**
     * 获取单个字符的高和宽
     */
    private int[] getTextWidth(String str) {
        int[] wh = new int[2];
        if(TextUtils.isEmpty(str)){
            return wh;
        }
        // 一个矩形
        Rect rect = new Rect();
        // 设置文字大小
        paintLabel.getTextBounds(str, 0, str.length(), rect);
        wh[0] = rect.width();
        wh[1] = rect.height();
        return wh;
    }



    public void setBarData(IChartDataProvider data) {
        mDataProvider=data;
        invalidate();
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
