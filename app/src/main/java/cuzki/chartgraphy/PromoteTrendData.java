/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

import android.graphics.Color;

import java.util.List;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class PromoteTrendData implements IChartDataProvider {
    List<PromoteTrendItem> promoteTrendItems;

    @Override
    public int getDateCount() {
        return promoteTrendItems==null?0:promoteTrendItems.size();
    }

    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return getDateCount()<indexX?0:(promoteTrendItems.get(indexX)==null?0:(indexY==0?promoteTrendItems.get(indexX).promotedRate:promoteTrendItems.get(indexX).promotedNum));
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        return getDateCount()<indexX?"":(promoteTrendItems.get(indexX)==null?"":promoteTrendItems.get(indexX).statDate);
    }

    @Override
    public String getValueLabel(int indexX, int indexY) {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return promoteTrendItems==null||promoteTrendItems.size()==0;
    }

    @Override
    public int getChildColor(int indexX, int indexY) {
        int color=0;
        switch (indexY){
            case 0:
                color= Color.parseColor("#f8a755");
                break;
            case 1:
                color= Color.parseColor("#90c8ff");
                break;
            case 2:
                color= Color.parseColor("#ffce7f");
                break;
            default:
                color= Color.parseColor("#ffce7f");
                break;
        }
        return color;
    }

    @Override
    public int getDateCount(int indexY) {
        return 0;
    }
}
