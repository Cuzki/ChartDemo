/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

import android.graphics.Color;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class PGradeSpreadData extends ChartData {
    public int getChildColor(int indexX, int indexY) {
        return Color.parseColor("#91c8ff");
    }

    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        if(indexY==0){
            return chartDataItems==null?0:(chartDataItems.size()>indexX?chartDataItems.get(indexX).num:0);
        }
        long max=0;
        for(ChartDataItem item:chartDataItems){
            if(max<item.num){
                max= (long) item.num;
            }
        }
        long i= (long) (max-(chartDataItems==null?0:(chartDataItems.size()>indexX?chartDataItems.get(indexX).num:0)));
        if(i<0){
            i=0;
        }
        return i;
    }

}
