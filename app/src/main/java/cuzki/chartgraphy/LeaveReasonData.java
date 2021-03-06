/**
 * Created on 2017/2/21
 */
package cuzki.chartgraphy;

import android.graphics.Color;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class LeaveReasonData extends ChartData {
    @Override
    public int getChildColor(int indexX, int indexY) {
        int color=0;
        switch (indexX){
            case 0:
                color= Color.parseColor("#44bcb2");
                break;
            case 1:
                color= Color.parseColor("#59b21c");
                break;
            case 2:
                color= Color.parseColor("#97a3bb");
                break;
            case 3:
                color= Color.parseColor("#fee16b");
                break;
            case 4:
                color= Color.parseColor("#ee5255");
                break;

        }
        return color;
    }

    @Override
    public String getValueLabel(int indexX, int indexY) {
        return getValue(indexX,0)+"";
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        if(chartDataItems==null){
            return "";
        }
        return  chartDataItems.size()>indexX?chartDataItems.get(indexX).name:"";
    }

    @Override
    public float getValue(int indexX, int indexY) {
        if(indexY==0){
            return 1;
        }
        return super.getValue(indexX, indexY);
    }
}

