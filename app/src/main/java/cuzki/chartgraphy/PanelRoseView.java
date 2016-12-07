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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static cuzki.chartgraphy.R.color.tab_selected_color;

/**
 * <p/>
 * 南丁格尔玫瑰图
 *
 * @author Cuzki
 */
public class PanelRoseView extends View {
    ICombineDateProvider mDataProvider;

    private float mCirX=-1;
    private float mCirY=-1;
    private float mRadius=-1;
    private float mCenterRadius=-1;
    private int mSelectedRoseIndex=-1;

    private onRosePanelSelectedListener mSelectedListener;

    private final int COLOR_GROUP[]={R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary,tab_selected_color,R.color.tab_color};

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
    }
    private void init() {
        init();
    }

    public void setOnSelectedListener(onRosePanelSelectedListener selectedListener) {
        this.mSelectedListener = selectedListener;
    }

    public void setPanelRoseData(ICombineDateProvider panelRoseData){
        mDataProvider=panelRoseData;
        if(mDataProvider==null||mDataProvider.isEmpty()){
            mDataProvider=new PanelRoseEmptyDataProvider();
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mDataProvider==null||mDataProvider.isEmpty()){
            mDataProvider=new PanelRoseEmptyDataProvider();
        }
        mHistroyList.clear();
        if(mCirX<0||mCirY<0||mRadius<0){
            int hor = getWidth() - getPaddingLeft() - getPaddingRight();
            int vir = getHeight() - getPaddingTop() - getPaddingBottom();
            mCirX = hor / 2 + getPaddingLeft();//绘制圆心
            mCirY = vir / 2 + getPaddingTop();
            mRadius = Math.min(hor, vir) *2/ 5;//本图所能取到的最大半径,画外环用
        }


        float labelRadius= (float) (mRadius*1.1);
        Resources res=getResources();

        //画笔初始化
        Paint paintArc = new Paint();
        Paint paintLabel = new Paint();
        paintLabel.setAntiAlias(true);
        paintArc.setAntiAlias(true);
        //位置计算类
        XChartCalc xcalc = new XChartCalc();

        float currPer = 0.0f;

        float baseRaidus = mRadius - Utils.dp2px(res,10);//南丁格尔玫瑰图花瓣最大半径值

        float maxRadia = 0;
        float minRadia=Float.MAX_VALUE;
        float totle = 0;
        for (int i=0; i<mDataProvider.getDateCount(); i++) {
            totle += mDataProvider.getY(i,1);
            float ra=mDataProvider.getY(i,0);
            if (maxRadia < ra) {
                maxRadia = ra;
            }
            if (minRadia > ra&&ra>0) {
                minRadia = ra;
            }
        }
        int emptyColor=-1;
        if(mDataProvider.isEmpty() ){
            emptyColor=Color.parseColor("#e4e4e4");
        }

        if(maxRadia==0||totle==0){//无效数据，无法计算(被除数)，故设置默认显示样式
            mDataProvider=new PanelRoseEmptyDataProvider();
            invalidate();
            return;
        }
        for (int i = 0; i < mDataProvider.getDateCount(); i++) {
            if(mDataProvider.getY(i,1)==0||mDataProvider.getY(i,0)==0){
                continue;
            }
            int color=emptyColor==-1?res.getColor(COLOR_GROUP[i%COLOR_GROUP.length]):emptyColor;
            float thisRadius = baseRaidus * mDataProvider.getY(i,0) / maxRadia;
            float newarcLeft = mCirX - thisRadius;
            float newarcTop = mCirY - thisRadius;
            float newarcRight = mCirX + thisRadius;
            float newarcBottom = mCirY + thisRadius;
            RectF newarcRF = new RectF(newarcLeft, newarcTop, newarcRight, newarcBottom);
            float percentage = 360 * mDataProvider.getY(i,1)/ totle;
            if(mSelectedRoseIndex==i){
                float offset=Utils.dp2px(res,3);
                paintArc.setStyle(Paint.Style.FILL_AND_STROKE);
                RectF selectedcRF = new RectF(newarcLeft-offset, newarcTop-offset, newarcRight+offset, newarcBottom+offset);
                paintArc.setColor(Color.RED);
                canvas.drawArc(selectedcRF, currPer, percentage, true, paintArc);
            }
            paintArc.setColor(color);
            paintArc.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawArc(newarcRF, currPer, percentage, true, paintArc);

            mHistroyList.add(new RoseHistroy(currPer,currPer+percentage,thisRadius));

            if(TextUtils.isEmpty(mDataProvider.getLabel(i))){
                currPer += percentage;
                continue;
            }
            paintLabel.setColor(color);
            paintLabel.setStrokeWidth(Utils.sp2px(res,mSelectedRoseIndex==i?1:0.5f));
            paintLabel.setTextSize(Utils.sp2px(res,mSelectedRoseIndex==i?12:8));
            //计算百分比标签
            float lineAngel=currPer + percentage / 2;

            xcalc.CalcArcEndPointXY(mCirX, mCirY, thisRadius, lineAngel);
            float lineStartX1=xcalc.getPosX();
            float lineStartY1= xcalc.getPosY();

            xcalc.CalcArcEndPointXY(mCirX, mCirY, labelRadius, lineAngel);
            float lineEndX1=xcalc.getPosX();
            float lineEndY1= xcalc.getPosY();
            canvas.drawLine(lineStartX1, lineStartY1, lineEndX1,lineEndY1,paintLabel);//斜线

            float lineStartX2=lineEndX1;
            float lineStartY2= lineEndY1;
            float lineEndY2= lineEndY1;
            final boolean isRight=(lineAngel>=0&&lineAngel<=90)||(lineAngel>=270&&lineAngel<=360);
            float distance=Utils.dp2px(res,10);
            float lineEndX2=isRight?lineEndX1+distance:lineEndX1-distance;
            canvas.drawLine(lineStartX2, lineStartY2, lineEndX2,lineEndY2,paintLabel);//水平线

            Rect textbounds = new Rect();
            paintLabel.getTextBounds(mDataProvider.getLabel(i), 0, mDataProvider.getLabel(i).length(), textbounds);
            //标识

            float margin=Utils.dp2px(res,1.5f);
            canvas.drawText(mDataProvider.getLabel(i), isRight?lineEndX2+margin:lineEndX2-textbounds.right-textbounds.left-margin, lineEndY2+(textbounds.bottom-textbounds.top)/3, paintLabel);
            //下次的起始角度
            currPer += percentage;
        }

        Paint paintCenter = new Paint();
        paintCenter.setColor(Color.WHITE);
        paintCenter.setStyle(Paint.Style.FILL_AND_STROKE);
        paintCenter.setAntiAlias(true);
        float centerRadius=2*baseRaidus*(minRadia/maxRadia)/5;
        canvas.drawCircle(mCirX, mCirY, centerRadius, paintCenter);

        //外环
        paintLabel.setStyle(Paint.Style.STROKE);
        paintLabel.setStrokeWidth(Utils.dp2px(res,1));
        paintLabel.setColor(Color.parseColor("#333333"));
        canvas.drawCircle(mCirX, mCirY, mRadius, paintLabel);
        canvas.drawCircle(mCirX, mCirY, centerRadius, paintLabel);
        canvas.drawCircle(mCirX, mCirY, 3*centerRadius/4, paintLabel);
        mCenterRadius=centerRadius;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        handleTouchEvent(x,y);
        return super.onTouchEvent(event);
    }

    private void handleTouchEvent(float x, float y) {
        // 将屏幕中的点转换成以屏幕中心为原点的坐标点
        float mx = x - mCirX;
        float my = y - mCirY;
        float distance = mx * mx + my * my;
        float piAngel= (float) Math.atan2((float)my,(float)mx);
        if(piAngel<0){
            piAngel= (float) (piAngel+Math.PI*2);
        }
        final float angel= (float) ((float) piAngel*180/Math.PI);//计算角度

        final int selectedIndex=mSelectedRoseIndex;
//        Log.i("cxy","piAngel="+piAngel+"  angel="+angel+"-mx="+mx+"-  my="+my);
        for(int i=0;i<mHistroyList.size();i++){
            RoseHistroy histroy=mHistroyList.get(i);
            float startAngel=histroy.startAngel;
            float endAngel=histroy.endAngel;
            float radiaus=histroy.radiaus;
            if(angel>=startAngel&&angel<=endAngel){
                if(distance<=(radiaus*radiaus)){
                    mSelectedRoseIndex=i;//记录并重新绘制选中的部分
                }else{//外围部分，取消选中状态
                    mSelectedRoseIndex=-1;
                }
                break;
            }
        }
        if(mSelectedRoseIndex!=selectedIndex){//有变化，需要重绘
            Log.i("cxy","选中 mSelectedRoseIndex="+mSelectedRoseIndex);
            invalidate();
            if(mSelectedListener!=null){
                mSelectedListener.onRosePanelSelected(mSelectedRoseIndex);
            }
        }
    }

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

    public class PanelRoseEmptyDataProvider implements ICombineDateProvider ,Serializable {
        float[][] floats={{4,1},{5,1},{6,1},{8,1}};
        @Override
        public int getDateCount() {
            return floats.length;
        }

        @Override
        public float getY(int indexX,int indexY) {
            return floats[indexX][indexY];
        }

        @Override
        public String getLabel(int indexX) {
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
    }

    private class RoseHistroy{

        public RoseHistroy(float startAngel, float endAngel, float radiaus) {
            this.startAngel = startAngel;
            this.endAngel = endAngel;
            this.radiaus = radiaus;
        }

        float startAngel;
        float endAngel;
        float radiaus;
    }

    private final  List<RoseHistroy> mHistroyList=new ArrayList<RoseHistroy>();

    interface onRosePanelSelectedListener{
        void onRosePanelSelected(int index);

    }

}
