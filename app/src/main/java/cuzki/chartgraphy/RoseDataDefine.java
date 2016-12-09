package cuzki.chartgraphy;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class RoseDataDefine implements IChartDataDefine {
    float[][]floats={{1,200},{0.8f,350},{0.6f,250},{0.4f,115},{0.2f,221}};
    String[] stings={"2016.4","2016.2","2016.3的法师法师打发实得分","2016.4","2016.5000000000000000"};
    String[] stingv={"200人","350人","250人","115人","221人"};
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
        if(indexX>=stings.length||stings[indexX]==null){
            return "";
        }
        return stings[indexX];
    }

    @Override
    public String getValueLabel(int indexX,int indexY) {
        return ""+ getValue(indexX,indexY);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getChildCount() {
        return floats[0].length;
    }

    @Override
    public int getChildColor(final int indexX,final int indexY) {
        int color=0;
        switch (indexY){
            case 0:
                color= Color.parseColor("#9933FF");
                break;
            case 1:
                color= Color.parseColor("#66FF66");
                break;
            case 2:
                color= Color.parseColor("#33CCFF");
                break;
            case 3:
                color= Color.parseColor("#FF3300");
                break;
            case 4:
                color= Color.parseColor("#FF66FF");
                break;
            default:
                color= Color.parseColor("#999999");
                break;
        }
        return color;
    }
}

