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
public class AgeSpreadData extends ChartData {

    long max;
    public int getChildColor(int indexX, int indexY) {
        return Color.parseColor(indexY==1?"#dce4ec":"#91c8ff");
    }

    @Override
    public int getChildCount() {
        return 2;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        if(chartDataItems==null){
            return 0;
        }
        if(max==0){
           for(ChartDataItem item:chartDataItems){
               if(max<item.num){
                   max= (long) item.num;
               }
           }
        }
        if(indexY==1){
            float result=max-getValue(indexX,0);
            return result>0?result:0 ;
        }
        return super.getValue(indexX, indexY);
    }
}
