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
public class LeaveTimeItem {

    public LeaveTimeItem(String year, List<ChartDataItem> quitList) {
        this.year = year;
        this.quitList = quitList;
    }

    String year;
    List<ChartDataItem> quitList;
}
