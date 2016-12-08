/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Cuzki
 */
public interface IChartData extends Serializable {

    int getDateCount();

    int getChildCount();

    float getY(int indexX,int indexY);

    String getCoordinateLabel(int indexX);

    String getValueLabel(int indexX);

    boolean isEmpty();
}
