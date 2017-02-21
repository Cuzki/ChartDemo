/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

import android.graphics.Color;
import android.text.Html;

import java.text.DecimalFormat;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class GradeSpreadData extends ChartData {

    @Override
    public int getChildColor(int indexX, int indexY) {
        int color=0;
        switch (indexX){
            case 0:
                color= Color.parseColor("#ff5052");
                break;
            case 1:
                color= Color.parseColor("#51ccf3");
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
        String name=chartDataItems.size()>indexX?chartDataItems.get(indexX).name:"";
        long value= chartDataItems.size()>indexX? (long) chartDataItems.get(indexX).num :0;
        long totle=0;
        for(ChartDataItem item:chartDataItems){
            totle+=item.num;
        }
        String percent=(totle==0?"0":new DecimalFormat("0.##").format(value*100/totle));
        return  Html.fromHtml(("<font color=\"#000000\">"+name+"</font><br>"+"("+percent+"%)")).toString();
    }
}
