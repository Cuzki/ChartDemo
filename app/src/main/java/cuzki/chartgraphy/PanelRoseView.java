/**
 * Created on 2016/12/6
 */
package cuzki.chartgraphy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * 南丁格尔玫瑰图
 *
 * @author Cuzki
 */
public class PanelRoseView extends View {
    IChartDataProvider mDataProvider;

    private float mCirX = -1;
    private float mCirY = -1;
    private float mRadius = -1;
    private float mCenterRadius = -1;
    private int mSelectedRoseIndex = -1;
    private float mDividerAngel=0;
    private float mDrawStartAngel=0;

    private boolean mDrawCenter = false;
    private boolean  mEnableSelected=false;
    private boolean  mEnableRotate=false;
    private boolean  mDrawEmpty=false;
    private onRosePanelSelectedListener mSelectedListener;

    Paint paintArc;
    Paint paintLabel;
    Paint paintCenter;
    Paint paintValue;
    XChartCalc xcalc;
    public PanelRoseView(Context context) {
        super(context);
        init();
    }

    public PanelRoseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelRoseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PanelRoseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public boolean isDrawCenter() {
        return mDrawCenter;
    }

    public PanelRoseView setDrawCenter(boolean drawCenter) {
        this.mDrawCenter = drawCenter;
        return this;
    }

    public PanelRoseView setOnSelectedListener(onRosePanelSelectedListener selectedListener) {
        this.mSelectedListener = selectedListener;
        return this;
    }

    public PanelRoseView setSelectPanel(int index){
        mSelectedRoseIndex=index;
        invalidate();
        return this;
    }

    public PanelRoseView setPanelRoseData(IChartDataProvider panelRoseData) {
        mDataProvider = panelRoseData;
        if (mDataProvider == null || mDataProvider.isEmpty()) {
            mDataProvider = new PanelRoseEmptyDataProviderProvider();
        }
        invalidate();
        return this;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setStartDrawAngel(mDrawStartAngel+=2);
            invalidate();
        }
    };
    public PanelRoseView setSelectedMode(boolean enableSelected){
        mEnableSelected=enableSelected;
        return this;
    }

    public PanelRoseView setTouchMode(boolean enableRotate){
        mEnableRotate=enableRotate;
        return this;
    }

    public PanelRoseView setDividerAngel(float dividerAngel){
        mDividerAngel=dividerAngel;
        while (mDividerAngel<0||mDividerAngel>=360){
            mDividerAngel=0;
        }
        return this;
    }

    public PanelRoseView setStartDrawAngel(float startDrawAngel){
        handleDrawStartAngel(startDrawAngel);
        return this;
    }

    private void handleDrawStartAngel(float startDrawAngel){
        mDrawStartAngel=startDrawAngel;
        while (mDrawStartAngel<0){
            mDrawStartAngel+=360;
        }
        mDrawStartAngel=mDrawStartAngel%360;
    }


    private void init() {
        this.setClickable(true);
        paintArc = new Paint();
        paintLabel = new Paint();
        paintCenter = new Paint();
        paintValue = new Paint();

        paintLabel.setAntiAlias(true);
        paintArc.setAntiAlias(true);
        paintValue.setAntiAlias(true);
        paintCenter.setAntiAlias(true);

        paintCenter.setColor(Color.WHITE);
        paintCenter.setStyle(Paint.Style.FILL_AND_STROKE);
        paintValue.setTextSize(Utils.sp2px(getResources(), 10));
        //位置计算类
        xcalc = new XChartCalc();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataProvider == null || mDataProvider.isEmpty()) {
            mDataProvider = new PanelRoseEmptyDataProviderProvider();
            mDrawEmpty=true;
        }else {
            mDrawEmpty=false;
        }
        if (xcalc==null||paintArc == null || paintLabel == null || paintCenter == null || paintValue == null) {
            init();
        }
        mHistroyList.clear();
        if (mCirX < 0 || mCirY < 0 || mRadius < 0) {
            int hor = getWidth() - getPaddingLeft() - getPaddingRight();
            int vir = getHeight() - getPaddingTop() - getPaddingBottom();
            mCirX = hor / 2 + getPaddingLeft();//绘制圆心
            mCirY = vir / 2 + getPaddingTop();
            mRadius = Math.min(hor, vir) * 2 / 5;//本图所能取到的最大半径,画外环用
        }

        float labelRadius = (float) (mRadius * 1.1);
        Resources res = getResources();

        float currPer = mDrawStartAngel;

        float baseRaidus = (float) (mRadius * 0.9);//南丁格尔玫瑰图花瓣最大半径值

        float maxRadia = 0;
        float minRadia = Float.MAX_VALUE;
        float totle = 0;
        for (int i = 0; i < mDataProvider.getDateCount(); i++) {
            totle += mDataProvider.getValue(i, 1);
            float ra = mDataProvider.getValue(i, 0);
            if (maxRadia < ra) {
                maxRadia = ra;
            }
            if (minRadia > ra && ra > 0) {
                minRadia = ra;
            }
        }
        int emptyColor = -1;
        if (mDataProvider.isEmpty()) {
            emptyColor = Color.parseColor("#e4e4e4");
        }

        if (maxRadia == 0) {//无效数据，无法计算(被除数),被除数不能为0,（认为每个部分都为0）
            maxRadia = 1;
        }
        if (totle == 0) {//无效数据，无法计算(被除数),被除数不能为0,（认为每个部分都为0）
            totle = 1;
        }
        for (int i = 0; i < mDataProvider.getDateCount(); i++) {
            if (mDataProvider.getValue(i, 1) <= 0 || mDataProvider.getValue(i, 0) <= 0) {
                continue;
            }
            int color = emptyColor == -1 ? mDataProvider.getChildColor(1,i) : emptyColor;
            float thisRadius = baseRaidus * mDataProvider.getValue(i, 0) / maxRadia;
            float newarcLeft = mCirX - thisRadius;
            float newarcTop = mCirY - thisRadius;
            float newarcRight = mCirX + thisRadius;
            float newarcBottom = mCirY + thisRadius;
            float percentage = 360 * mDataProvider.getValue(i, 1) / totle;
            mHistroyList.add(new RoseHistroy(currPer, currPer + percentage, thisRadius));
            if (mSelectedRoseIndex == i) {
                float offset = Utils.dp2px(res, 3);
                newarcLeft -= offset;
                newarcTop -= offset;
                newarcRight += offset;
                newarcBottom += offset;
                thisRadius += offset;
            }
            RectF newarcRF = new RectF(newarcLeft, newarcTop, newarcRight, newarcBottom);
            paintArc.setColor(color);
            paintArc.setStyle(Paint.Style.FILL_AND_STROKE);
            float drawAngel=percentage-mDividerAngel;
            if(drawAngel<=0){
                drawAngel+=mDividerAngel;
            }
            canvas.drawArc(newarcRF, currPer, drawAngel, true, paintArc);
           //画扇形
            if (TextUtils.isEmpty(mDataProvider.getCoordinateLabel(i))) {
                currPer += percentage;
                continue;
            }
            paintLabel.setColor(color);
            paintLabel.setStrokeWidth(Utils.sp2px(res, mSelectedRoseIndex == i ? 1 : 0.5f));
            paintLabel.setTextSize(Utils.sp2px(res, mSelectedRoseIndex == i ? 12 : 8));
            //计算百分比标签
            float lineAngel = currPer + (drawAngel) / 2;

            xcalc.CalcArcEndPointXY(mCirX, mCirY, thisRadius, lineAngel);
            float lineStartX1 = xcalc.getPosX();
            float lineStartY1 = xcalc.getPosY();

            xcalc.CalcArcEndPointXY(mCirX, mCirY, labelRadius, lineAngel);
            float lineEndX1 = xcalc.getPosX();
            float lineEndY1 = xcalc.getPosY();
            canvas.drawLine(lineStartX1, lineStartY1, lineEndX1, lineEndY1, paintLabel);//斜线

            float lineStartX2 = lineEndX1;
            float lineStartY2 = lineEndY1;
            float lineEndY2 = lineEndY1;
            float realAngel=lineAngel%360;
            final boolean isRight = (realAngel >= 0 && realAngel <= 90) || (realAngel >= 270 && realAngel <= 360);
            float distance = Utils.dp2px(res, 10);
            float lineEndX2 = isRight ? lineEndX1 + distance : lineEndX1 - distance;
            canvas.drawLine(lineStartX2, lineStartY2, lineEndX2, lineEndY2, paintLabel);//水平线

            Rect textbounds = new Rect();
            paintLabel.getTextBounds(mDataProvider.getCoordinateLabel(i), 0, mDataProvider.getCoordinateLabel(i).length(), textbounds);
            //画标识文本

            float margin = Utils.dp2px(res, 1.5f);
            Paint.FontMetricsInt fmi = paintLabel.getFontMetricsInt();
            canvas.drawText(mDataProvider.getCoordinateLabel(i), isRight ? lineEndX2 + margin : lineEndX2 - textbounds.right - textbounds.left - margin, lineEndY2 + (fmi.bottom - textbounds.top) / 2 - fmi.bottom, paintLabel);
            //下次的起始角度
            currPer += percentage;
        }

        //画最外环
        paintLabel.setStyle(Paint.Style.STROKE);
        paintLabel.setStrokeWidth(Utils.dp2px(res, 1));
        paintLabel.setColor(Color.parseColor("#333333"));
        canvas.drawCircle(mCirX, mCirY, mRadius, paintLabel);

        if (mDrawCenter) {//画中心白色圆及内环
            paintCenter.setColor(Color.WHITE);
            paintCenter.setStyle(Paint.Style.FILL_AND_STROKE);
            paintCenter.setAntiAlias(true);
            float centerRadius = 2 * baseRaidus * (minRadia / maxRadia) / 5;
            canvas.drawCircle(mCirX, mCirY, centerRadius, paintCenter);
            canvas.drawCircle(mCirX, mCirY, centerRadius, paintLabel);
            canvas.drawCircle(mCirX, mCirY, 3 * centerRadius / 4, paintLabel);
            mCenterRadius = centerRadius;
        }

        if (mSelectedRoseIndex >= 0) {//画选中花瓣的数值及其标签
            final String valueLabelString = mDataProvider.getValueLabel(mSelectedRoseIndex,1);
            RoseHistroy histroy = null;
            if (mHistroyList == null || mSelectedRoseIndex >= mHistroyList.size() || (histroy = mHistroyList.get(mSelectedRoseIndex)) == null) {
                return;
            }
            xcalc.CalcArcEndPointXY(mCirX, mCirY, histroy.radiaus * 3 / 5, (histroy.startAngel + histroy.endAngel) / 2);
            paintValue.setTextSize(Utils.sp2px(res, 9));
            Paint.FontMetricsInt fmi = paintValue.getFontMetricsInt();
            Rect textNum = new Rect();
            paintValue.getTextBounds(valueLabelString, 0, valueLabelString.length(), textNum);
            float textWidth = textNum.right - textNum.left;
            float textBaseLineX = xcalc.getPosX() - textWidth / 2;
            float textBaseLineY = xcalc.getPosY();
            paintValue.setColor(darkenColor(mDataProvider.getChildColor(1,mSelectedRoseIndex)));
            float offset = Utils.dp2px(res, 3);
            canvas.drawRect(new RectF(textBaseLineX - offset, textBaseLineY - offset / 2 + fmi.top, textBaseLineX + textWidth + offset, textBaseLineY + offset / 2 + fmi.bottom), paintValue);
            paintValue.setColor(Color.WHITE);
            canvas.drawText(valueLabelString, textBaseLineX, textBaseLineY, paintValue);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mEnableSelected&&!mDrawEmpty){
            handleTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private float caculateAngel(float touchX,float touchY){
        // 将屏幕中的点转换成以屏幕中心为原点的坐标点
        float mx = touchX - mCirX;
        float my = touchY - mCirY;
        float piAngel = (float) Math.atan2((float) my, (float) mx);
        if (piAngel < 0) {
            piAngel = (float) (piAngel + Math.PI * 2);
        }
        return  (float) ((float) piAngel * 180 / Math.PI);
    }

    private void handleTouchEvent(MotionEvent event) {
        final int selectedIndex = mSelectedRoseIndex;
        final float drawStartAngel=mDrawStartAngel;
        Log.i("cxy","Action=" +event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float posX = event.getX();
                float posY = event.getY();
                // 将屏幕中的点转换成以屏幕中心为原点的坐标点
                float mx = posX - mCirX;
                float my = posY - mCirY;
                float distance = mx * mx + my * my;
                float touchDownAngel = caculateAngel(posX,posY);//计算角度
                mFlipDownAngel=touchDownAngel;
                for (int i = 0; i < mHistroyList.size(); i++) {
                    RoseHistroy histroy = mHistroyList.get(i);
                    float startAngel = histroy.startAngel;
                    float endAngel = histroy.endAngel;
                    float radiaus = histroy.radiaus;
                    if ((touchDownAngel >= startAngel && touchDownAngel <= endAngel)||(touchDownAngel +360>= startAngel && touchDownAngel+360 <= endAngel)) {
                        if (distance >= (mCenterRadius * mCenterRadius) && distance <= (radiaus * radiaus)) {
                            mSelectedRoseIndex = i;//记录并重新绘制选中的部分
                        } else {//外围部分，取消选中状态
                            mSelectedRoseIndex = -1;
                        }
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mEnableRotate){
                    float flipEndAngel=caculateAngel(event.getX(),event.getY());
                    mDrawStartAngel+=(flipEndAngel-mFlipDownAngel);
                    handleDrawStartAngel(mDrawStartAngel);
                    mFlipDownAngel=flipEndAngel;
                }
                break;
            case MotionEvent.ACTION_UP:
                mFlipDownAngel=0;
                break;
        }

        if (mSelectedRoseIndex != selectedIndex||mDrawStartAngel!=drawStartAngel) {//有变化，需要重绘
            invalidate();
            if (mSelectedRoseIndex != selectedIndex&&mSelectedListener != null) {
                mSelectedListener.onRosePanelSelected(mSelectedRoseIndex);
            }
        }
    }

    private float mFlipDownAngel ;

    public class XChartCalc {
        //Position位置
        private float posX = 0.0f;
        private float posY = 0.0f;

        public XChartCalc() {
        }

        //依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
        public void CalcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle) {

            //将角度转换为弧度
            float arcAngle = (float) (Math.PI * cirAngle / 180.0);
            if (cirAngle < 90) {
                posX = cirX + (float) (Math.cos(arcAngle)) * radius;
                posY = cirY + (float) (Math.sin(arcAngle)) * radius;
            } else if (cirAngle == 90) {
                posX = cirX;
                posY = cirY + radius;
            } else if (cirAngle > 90 && cirAngle < 180) {
                arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
                posX = cirX - (float) (Math.cos(arcAngle)) * radius;
                posY = cirY + (float) (Math.sin(arcAngle)) * radius;
            } else if (cirAngle == 180) {
                posX = cirX - radius;
                posY = cirY;
            } else if (cirAngle > 180 && cirAngle < 270) {
                arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
                posX = cirX - (float) (Math.cos(arcAngle)) * radius;
                posY = cirY - (float) (Math.sin(arcAngle)) * radius;
            } else if (cirAngle == 270) {
                posX = cirX;
                posY = cirY - radius;
            } else {
                arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
                posX = cirX + (float) (Math.cos(arcAngle)) * radius;
                posY = cirY - (float) (Math.sin(arcAngle)) * radius;
            }

        }


        public float getPosX() {
            return posX;
        }


        public float getPosY() {
            return posY;
        }
    }

    public class PanelRoseEmptyDataProviderProvider implements IChartDataProvider, Serializable {
        float[][] floats = {{4, 1}, {5, 1}, {6, 1}, {8, 1}};

        @Override
        public int getDateCount() {
            return floats.length;
        }

        @Override
        public float getValue(int indexX, int indexY) {
            return floats[indexX][indexY];
        }

        @Override
        public String getCoordinateLabel(int indexX) {
            return "";
        }

        @Override
        public String getValueLabel(int indexX,int indexY) {
            return "";
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int getChildCount() {
            return floats[0].length;
        }


        @Override
        public int getChildColor(final int indexX,final int indexY) {
            return Color.parseColor("#999999");
        }
    }

    private class RoseHistroy implements Parcelable{

        public RoseHistroy(float startAngel, float endAngel, float radiaus) {
            this.startAngel = startAngel;
            this.endAngel = endAngel;
            this.radiaus = radiaus;
        }

        float startAngel;
        float endAngel;
        float radiaus;

        @Override
        public int describeContents() {
            return 0;
        }

        public RoseHistroy(Parcel dest) {
            dest.writeFloat(startAngel);
            dest.writeFloat(endAngel);
            dest.writeFloat(radiaus);
        }


        @Override
        public void writeToParcel(Parcel parcel, int i) {
            startAngel = parcel.readFloat();
            endAngel = parcel.readFloat();
            radiaus = parcel.readFloat();
        }

        public  Creator<RoseHistroy> CREATOR = new Creator<RoseHistroy>() {
            @Override
            public RoseHistroy createFromParcel(Parcel in) {
                return new RoseHistroy(in);
            }

            @Override
            public RoseHistroy[] newArray(int size) {
                return new RoseHistroy[size];
            }
        };
    }

    private final List<RoseHistroy> mHistroyList = new ArrayList<RoseHistroy>();

    interface onRosePanelSelectedListener {
        void onRosePanelSelected(int index);
    }

    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        int alpha = Color.alpha(color);
        Color.colorToHSV(color, hsv);
        hsv[1] = Math.min(hsv[1] * DARKEN_SATURATION, 1.0f);
        hsv[2] = hsv[2] * DARKEN_INTENSITY;
        int tempColor = Color.HSVToColor(hsv);
        return Color.argb(alpha, Color.red(tempColor), Color.green(tempColor), Color.blue(tempColor));
    }

    private static final float DARKEN_SATURATION = 1.1f;
    private static final float DARKEN_INTENSITY = 0.9f;

}
