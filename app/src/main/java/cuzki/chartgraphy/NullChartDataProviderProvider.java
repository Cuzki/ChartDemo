/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import android.graphics.Color;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class NullChartDataProviderProvider implements IChartDataProvider,Serializable {
    @Override
    public int getDateCount() {
        return 0;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return 0;
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        return "";
    }

    @Override
    public String getValueLabel(int indexX,int indexY) {
        return getValue(indexX,indexY)+"";
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int getChildCount() {
        return 0;
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
