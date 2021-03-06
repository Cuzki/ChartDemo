/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import android.graphics.Color;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class ChartDataProvider2 implements IChartDataProvider {
    float[][]floats={{30,200},{60,350},{27,650},{50,135},{90,251},{11,121},{80,352},{45,122},{64,200},{24,410},{44,180},{15,205}};
    String[] stings={"2016.1","2016.2","2016.3","2016.4","2016.5","2016.6","2016.7","2016.8","2016.9","2016.10","2016.11","2016.12"};
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
        return getValue(indexX,indexY)+"";
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
        }
        return color;
    }

    @Override
    public int getDateCount(int indexY) {
        return 0;
    }
}

