/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class ChartDataDefine1 implements IChartDataDefine {
    float[][]floats={{20,80},{50,50},{60,40},{38,62},{70,30},{22,77},{55,45},{41,59},{20,80},{90,10},{11,89},{0,100}};
    String[] stings={"100岁以上","90岁以上","80岁以上","60-50岁","50-40岁","40-30岁","30-25岁","25-22岁","22-20岁","20-18岁","18-17岁","17-15岁"};
    @Override
    public int getDateCount() {
        return floats.length;
    }

    @Override
    public int getChildCount() {
        return floats[0].length;
    }

    @Override
    public float getValue(int indexX, int indexY) {
        return floats[indexX][indexY];
    }

    @Override
    public String getCoordinateLabel(int indexX) {
        if(indexX>=stings.length||stings[indexX]==null){
            return "";
        }
        return stings[indexX];
    }

    @Override
    public String getValueLabel(int indexX,int indexY) {
        return floats[indexX][indexY]+"";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
