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
public class ChartDataDefine implements IChartDataDefine {
    float[][] floats={{60,50,20},{32,10,150},{82,77,52},{20,14,50},{80,30,21},{50,44,232},{52,15,30},{72,21,10},{60,150,11},{60,151,89},{44,15,80},{45,60,10}};
    String[] stings={"2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1"};
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
                color= Color.parseColor("#9933FF");
                break;
            case 1:
                color= Color.parseColor("#66FF66");
                break;
            case 2:
                color= Color.parseColor("#33CCFF");
                break;
        }
        return color;
    }
}
