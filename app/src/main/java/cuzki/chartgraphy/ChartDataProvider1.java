/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

import android.graphics.Color;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class ChartDataProvider1 implements IChartDataProvider {
    float[][]floats={{20,80},{50,50},{60,40},{38,62},{70,30},{23,77},{55,45},{41,59},{20,80},{100,0},{11,89},{0,100}};
    String[] stings={"100岁以上","90岁以上","80岁以上","60-50岁","50-40岁","40-30岁","30-25岁","25-22岁","22-20岁","20-18岁","18-17岁","17-15岁"};
    @Override
    public int getDateCount() {
        return floats.length;
    }

    @Override
    public int getChildCount() {
        return floats[0].length;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return floats[indexX][indexY];
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        if(indexX>=stings.length||stings[indexX]==null){
            return "";
        }
        return stings[indexX];
    }

    @Override
    public String getValueLabel(int indexX,int indexY) {
        return floats[indexX][indexY]+"";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getChildColor(final int indexX,final int indexY) {
        int color=0;
        switch (indexY){
            case 0:
                color= Color.parseColor("#91c8ff");
                break;
            case 1:
                color= Color.parseColor("#dce4ec");
                break;
            case 2:
                color= Color.parseColor("#33CCFF");
                break;
        }
        return color;
    }

    @Override
    public int getDateCount(int indexY) {
        return 0;
    }
}
