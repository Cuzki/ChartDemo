/**
 * Created on 2016/12/9
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public abstract class AbstractChartData implements IChartDataDefine {
    public String getValueLabel(int indexX,int indexY){
        return getValueLabel(indexX,indexY)+"";
    }
}
