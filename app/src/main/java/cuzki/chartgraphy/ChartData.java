/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

import java.util.List;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class ChartData implements IChartDataProvider{
    List<ChartDataItem> chartDataItems;//rows

    @Override
    public int getDateCount() {
        return chartDataItems==null?0:chartDataItems.size();
    }

    @Override
    public int getChildCount() {
        return 1;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return chartDataItems==null?0:(chartDataItems.size()>indexX?chartDataItems.get(indexX).num:0);
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        return chartDataItems==null?"":(chartDataItems.size()>indexX?chartDataItems.get(indexX).name:"");
    }

    @Override
    public boolean isEmpty() {
        return chartDataItems==null||chartDataItems.size()==0;
    }

    @Override
    public int getChildColor(int indexX, int indexY) {
        return 0;
    }

    @Override
    public int getDateCount(int indexY) {
        return 0;
    }

    @Override
    public String getValueLabel(int indexX, int indexY) {
        return getValue(indexX,0)+"";
    }
}
