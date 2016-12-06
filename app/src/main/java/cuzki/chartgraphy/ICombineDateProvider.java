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
public interface ICombineDateProvider extends Serializable {

    int getDateCount();

    int getChildCount();

    float getY(int indexX,int indexY);

    String getLabel(int indexX);

    boolean isEmpty();
}
