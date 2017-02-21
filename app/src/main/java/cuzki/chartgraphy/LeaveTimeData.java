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
public class LeaveTimeData implements IChartDataProvider{

    List<LeaveTimeItem> leaveTimeItems;

    @Override
    public int getDateCount(int indexY) {
        return (leaveTimeItems==null||leaveTimeItems.size()<indexY||leaveTimeItems.get(indexY).quitList==null)?0:leaveTimeItems.get(indexY).quitList.size();
    }

    @Override
    public int getDateCount() {
        return 12;
    }

    @Override
    public int getChildCount() {
        return leaveTimeItems==null?0:leaveTimeItems.size();
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return (leaveTimeItems==null||leaveTimeItems.size()<indexY||leaveTimeItems.get(indexY).quitList==null||leaveTimeItems.get(indexY).quitList.size()<indexX)?0:leaveTimeItems.get(indexY).quitList.get(indexX).num;
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        return  (leaveTimeItems==null||leaveTimeItems.size()==0||leaveTimeItems.get(0).quitList==null)?"":leaveTimeItems.get(0).quitList.get(indexX).name;
    }

    @Override
    public String getValueLabel(int indexX, int indexY) {
        return ((int)getValue(indexX,indexY))+"";
    }

    @Override
    public boolean isEmpty() {
        return getDateCount()==0;
    }

    @Override
    public int getChildColor(int indexX, int indexY) {
        int color=0;
        switch (indexY){
            case 0:
                color= Color.parseColor("#3dab14");
                break;
            case 1:
                color= Color.parseColor("#a7d3ff");
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
}
