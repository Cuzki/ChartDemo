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
import android.util.AttributeSet;
import android.view.View;

import static cuzki.chartgraphy.R.color.tab_selected_color;

/**
 * <p/>
 * 南丁格尔玫瑰图
 *
 * @author Cuzki
 */
public class PanelRoseView extends View {

    //演示用的百分比例,实际使用中，即为外部传入的比例参数
    private final float arrRadias[] = new float[]{40f, 50f, 60f, 35f, 70f, 80f, 90f};

    private final float arrAngel[] = new float[]{20f, 80f, 10f, 55f, 60f, 32f, 40f};

    private final int COLOR_GROUP[]={R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary,tab_selected_color,R.color.tab_color};
    //演示用标签
    private final String arrPerLabel[] = new String[]{"PostgreSQL", "Sybase", "DB2", "国产及其它", "MySQL", "Ms Sql", "Oracle"};
    //RGB颜色数组
    private final int arrColorRgb[][] = {{77, 83, 97},
            {148, 159, 181},
            {253, 180, 90},
            {52, 194, 188},
            {39, 51, 72},
            {255, 135, 195},
            {215, 124, 124}};

    public PanelRoseView(Context context) {
        super(context);
        init();
    }

    public PanelRoseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelRoseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PanelRoseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int hor = getWidth() - getPaddingLeft() - getPaddingRight();
        int vir = getHeight() - getPaddingTop() - getPaddingBottom();
        float cirX = hor / 2 + getPaddingLeft();//绘制圆心
        float cirY = vir / 2 + getPaddingTop();

        float radius = Math.min(hor, vir) *2/ 5;//本图最大半径,画外环用

        float labelRadius= (float) (radius*1.2);

        float arcLeft = cirX - radius;
        float arcTop = cirY - radius;
        float arcRight = cirX + radius;
        float arcBottom = cirY + radius;
        RectF arcRF0 = new RectF(arcLeft, arcTop, arcRight, arcBottom);//本图绘画区域

        //画笔初始化
        Paint paintArc = new Paint();
        Paint paintLabel = new Paint();
        paintLabel.setTextSize(16);

        paintLabel.setAntiAlias(true);
        paintArc.setAntiAlias(true);
        //位置计算类
        XChartCalc xcalc = new XChartCalc();

        float currPer = 0.0f;

        float baseRaidus = radius - 20;//南丁格尔玫瑰图最大半径值

        float maxRadia = 0;
        for (float ra : arrRadias) {
            if (maxRadia < ra) {
                maxRadia = ra;
            }
        }
        float totle = 0;
        for (float ra : arrAngel) {
            totle += ra;
        }
        Resources res=getResources();

        for (int i = 0; i < arrRadias.length; i++) {
            //将百分比转换为新扇区的半径
            int color=res.getColor(COLOR_GROUP[i%5]);

            float thisRadius = baseRaidus * arrRadias[i] / maxRadia;
            float NewarcLeft = cirX - thisRadius;
            float NewarcTop = cirY - thisRadius;
            float NewarcRight = cirX + thisRadius;
            float NewarcBottom = cirY + thisRadius;
            RectF NewarcRF = new RectF(NewarcLeft, NewarcTop, NewarcRight, NewarcBottom);

            paintArc.setColor(color);
            //在饼图中显示所占比例
            float percentage = 360 * arrAngel[i] / totle;
            canvas.drawArc(NewarcRF, currPer, percentage, true, paintArc);


            paintLabel.setColor(color);
            //计算百分比标签
            float lineAngel=currPer + percentage / 2;

            xcalc.CalcArcEndPointXY(cirX, cirY, thisRadius, lineAngel);
            float lineStartX1=xcalc.getPosX();
            float lineStartY1= xcalc.getPosY();

            xcalc.CalcArcEndPointXY(cirX, cirY, labelRadius, lineAngel);
            float lineEndX1=xcalc.getPosX();
            float lineEndY1= xcalc.getPosY();
            canvas.drawLine(lineStartX1, lineStartY1, lineEndX1,lineEndY1,paintLabel);//斜线

            float lineStartX2=lineEndX1;
            float lineStartY2= lineEndY1;
            float lineEndY2= lineEndY1;
            final boolean isRight=(lineAngel>=0&&lineAngel<=90)||(lineAngel>=270&&lineAngel<=360);
            float lineEndX2=isRight?lineEndX1+20:lineEndX1-20;
            canvas.drawLine(lineStartX2, lineStartY2, lineEndX2,lineEndY2,paintLabel);//水平线

            Rect textbounds = new Rect();
            paintLabel.getTextBounds(arrPerLabel[i], 0, arrPerLabel[i].length(), textbounds);
            //标识

            float margin=3;
            canvas.drawText(arrPerLabel[i], isRight?lineEndX2+margin:lineEndX2-textbounds.right-textbounds.left-margin, lineEndY2+(textbounds.bottom-textbounds.top)/3, paintLabel);
            //下次的起始角度
            currPer += percentage;
        }
        //外环
        paintLabel.setStyle(Paint.Style.STROKE);
        paintLabel.setColor(Color.BLACK);
        canvas.drawCircle(cirX, cirY, radius, paintLabel);

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

}
