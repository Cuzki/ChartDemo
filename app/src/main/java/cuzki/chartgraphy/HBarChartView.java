/**
 * Created on 2017/1/10
 */
package cuzki.chartgraphy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
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
    Paint paintCoor;
    Paint paintValue;

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
        paintCoor=new Paint();
        paintValue=new Paint();
        paintLabel.setAntiAlias(true);
        paintBar.setAntiAlias(true);
        paintCoor.setAntiAlias(true);
        paintCoor.setStyle(Paint.Style.STROKE);
        paintBar.setStyle(Paint.Style.FILL_AND_STROKE);
        paintLabel.setTextSize(Utils.sp2px(getResources(), 10));
        paintValue.setTextSize(Utils.sp2px(getResources(), 10));
        paintLabel.setTextAlign(Paint.Align.RIGHT);
        paintLabel.setColor(Color.parseColor("#000000"));
        paintValue.setTextAlign(Paint.Align.RIGHT);
        paintValue.setColor(Color.parseColor("#ffffff"));
        paintValue.setStrokeWidth(2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBarRecorderList.clear();
        float width=getWidth()-getPaddingLeft()-getPaddingRight();
        float height=getHeight()-getPaddingTop()-getPaddingBottom();
        float barWidth=width;
        float unitDistance=0;
        if(mDataProvider==null||mDataProvider.isEmpty()){
            return;//画空
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
        }else {
            return;//画空
        }
        float offset=Utils.dp2px(getResources(),4);
        float barHeight=(height-offset*(count+1))/count;
        float lineBarStartX=maxLabelWidth;
        float lineBarStartY=offset;
        if(mIsDrawXCorordinate){
            canvas.drawLine(lineBarStartX,0,lineBarStartX,height,paintCoor);
        }
        if(mIsDrawYCorordinate){
            canvas.drawLine(lineBarStartX,height,width,height,paintCoor);
        }
        Paint.FontMetricsInt LabelFmi = paintLabel.getFontMetricsInt();
        Paint.FontMetricsInt valueFmi = paintValue.getFontMetricsInt();
        for(int i=0;i<count;i++){
            //画bar
            float startX=lineBarStartX;
            for(int j=0;j<mDataProvider.getChildCount();j++){
                float diatance=mDataProvider.getValue(i,j)*unitDistance;
                BarRecorder recorder=new BarRecorder();//记录该bar
                recorder.indexY=j;
                recorder.indexX=i;
                float top=lineBarStartY;
                float bottom=lineBarStartY+barHeight;
                float right=startX+diatance;
                float left=startX;
                paintBar.setColor(mDataProvider.getChildColor(i,j));
                if(i==mSelectedXIndex&&j== mSelectedYIndex){
                    top-=offset/2;
                    bottom+=offset/2;
                    paintBar.setColor( Utils.darkenColor(mDataProvider.getChildColor(i,j)));
                }
                RectF bar=new RectF(left,top,right,bottom);
                recorder.rectF=bar;
                mBarRecorderList.add(recorder);
                canvas.drawRect(bar,paintBar);
                if(i==mSelectedXIndex&&j== mSelectedYIndex){
                    canvas.drawText(mDataProvider.getValueLabel(i,j), right-labelMarginRight, lineBarStartY-offset/2+(offset+barHeight)/2+(valueFmi.bottom-valueFmi.top)/2-valueFmi.bottom, paintValue);
                }
                startX+=+diatance;
            }
            //画label x轴
            canvas.drawText(mDataProvider.getCoordinateLabel(i), lineBarStartX-labelMarginRight, lineBarStartY+barHeight/2+(LabelFmi.bottom-LabelFmi.top)/2-LabelFmi.bottom, paintLabel);
            lineBarStartY=(lineBarStartY+barHeight+offset);
        }
    }

    /**
     * 获取字符的高和宽
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



    public HBarChartView setBarData(IChartDataProvider data) {
        mDataProvider=data;
        invalidate();
        return this;
    }

    public HBarChartView setXCoordinateDrawable(boolean isDraw) {
        mIsDrawXCorordinate=isDraw;
        return this;
    }

    public HBarChartView setYCoordinateDrawable(boolean isDraw) {
        mIsDrawYCorordinate=isDraw;
        return this;
    }

    public HBarChartView setLabelTxtColor(int  color) {
        if(paintLabel==null){
            init();
        }
        paintLabel.setColor(color);
        return this;
    }

    public HBarChartView setLabelTxtSize(float  size) {
        if(paintLabel==null){
            init();
        }
        paintLabel.setTextSize(size);
        return this;
    }


    /**
     * 水平轴及坐标线是否绘制
     */
    private boolean mIsDrawXCorordinate=true;

    /**
     * 竖直坐标轴及坐标线是否绘制
     */
    private boolean mIsDrawYCorordinate=true;

    private int mSelectedXIndex=-1;
    private int mSelectedYIndex=-1;
    private List<BarRecorder> mBarRecorderList=new ArrayList<>();
    private boolean  mEnableSelected=true;
    private boolean  mDrawEmpty=false;
    class BarRecorder{
        int indexX;
        int indexY;
        RectF rectF;
        public boolean isSelected(float x,float y){
            return rectF==null?false:rectF.contains(x,y);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mEnableSelected&&!mDrawEmpty){
            handleTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private void handleTouchEvent(MotionEvent event) {
        float posX = event.getX();
        float posY = event.getY();
        boolean result=false;
        for(BarRecorder recorder:mBarRecorderList){
            if(recorder.isSelected(posX,posY)){
                mSelectedXIndex=recorder.indexX;
                mSelectedYIndex=recorder.indexY;
                result=true;
                break;
            }
        }
        if(!result){
            mSelectedXIndex=-1;
            mSelectedYIndex=-1;
        }
        invalidate();
    }

}
