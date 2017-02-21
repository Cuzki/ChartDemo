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
public interface IChartDataProvider extends Serializable {

    /**
     * 获取横坐标数目
     * @return 横坐标
     */
    int getDateCount();

    /**
     * 获取每个横坐标对应的纵坐标数目 1对多
     * @return 每个横坐标对应的纵坐标数目
     */
    int getChildCount();

    /**
     * 获取横坐标indexX对应的第indexY个纵坐标值
     * @return 横坐标indexX对应的第indexY个纵坐标值
     */
    float getValue(int indexX, int indexY);

    /**
     * 获取横坐标indexX对应的label,一般用于显示在横坐标轴上
     * @param indexX
     * @return
     */
    String getCoordinateLabel(int indexX);

    /**
     * 获取indexX对应Y值的label,一般用于显示在Y方向的图形上
     * @param indexX
     * @return
     */
    String getValueLabel(int indexX,int indexY);

    /**
     * 该数据是否为空或无效,通常主要区分indexY
     * @return
     */
    boolean isEmpty();

    /**
     * 获取第indexY个Y组的色值
     * @return 横坐标 color值
     */
    int getChildColor(int indexX,int indexY);

    /**
     * 获取横坐标数目
     * @return 横坐标
     */
    int getDateCount(int indexY);

}
