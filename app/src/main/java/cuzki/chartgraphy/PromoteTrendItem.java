/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class PromoteTrendItem {
    String statDate;
    long promotedNum;
    long totalNum;
    float promotedRate;

    public PromoteTrendItem(String statDate, long promotedNum, long totalNum, float promotedRate) {
        this.statDate = statDate;
        this.promotedNum = promotedNum;
        this.totalNum = totalNum;
        this.promotedRate = promotedRate;
    }
}
