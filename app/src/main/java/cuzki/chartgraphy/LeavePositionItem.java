/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class LeavePositionItem {
    /**
     * position : 开发
     * quitNum : 1
     * totalNum : 1
     * quitRate : 1
     */

     String position;
    long quitNum;
    long totalNum;

    public LeavePositionItem(String position, long quitNum, long totalNum, long quitRate) {
        this.position = position;
        this.quitNum = quitNum;
        this.totalNum = totalNum;
        this.quitRate = quitRate;
    }

    long quitRate;


}
