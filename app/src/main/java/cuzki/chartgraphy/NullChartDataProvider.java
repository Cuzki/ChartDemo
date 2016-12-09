/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class NullChartDataProvider implements IChartData,Serializable {
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
}
