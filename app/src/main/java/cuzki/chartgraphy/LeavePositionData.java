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
public class LeavePositionData implements IChartDataProvider {
    List<LeavePositionItem> leavePositionItems;//rows

    @Override
    public int getDateCount() {
        return leavePositionItems==null?0:leavePositionItems.size();
    }

    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return getDateCount()<indexX?0:(leavePositionItems.get(indexX)==null?0:(indexY==0?leavePositionItems.get(indexX).quitRate:leavePositionItems.get(indexX).quitNum));
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        return getDateCount()<indexX?"":(leavePositionItems.get(indexX)==null?"":leavePositionItems.get(indexX).position);
    }

    @Override
    public String getValueLabel(int indexX, int indexY) {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return leavePositionItems==null||leavePositionItems.size()==0;
    }

    @Override
    public int getChildColor(int indexX, int indexY) {
        int color=0;
        switch (indexY){
            case 0:
                color= Color.parseColor("#90c8ff");
                break;
            case 1:
                color= Color.parseColor("#f8a755");
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
