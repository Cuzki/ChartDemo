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
public class NullChartDataProvider implements ICombineDateProvider ,Serializable {
    @Override
    public int getDateCount() {
        return 0;
    }

    @Override
    public float getY(int indexX,int indexY) {
        return 0;
    }

    @Override
    public String getLabel(int indexX) {
        return "";
    }

    @Override
    public int getChildCount() {
        return 0;
    }
}
