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
public class ChartDataItem {

    String name;

    float num;

    List<ChartDataItem> detail;

    public ChartDataItem(String name, float num) {
        this.name = name;
        this.num = num;
    }

    public ChartDataItem(String name, float num, List<ChartDataItem> detail) {
        this.name = name;
        this.num = num;
        this.detail = detail;
    }
}
