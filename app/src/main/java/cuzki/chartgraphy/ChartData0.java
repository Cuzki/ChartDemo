/**
 * Created on 2016/12/8
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class ChartData0 implements IChartData {
    float[][] floats={{60},{150},{82},{50},{80},{232},{30},{72},{50},{89},{80},{10}};
    String[] stings={"2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1"};
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
        return getValue(indexX,indexY)+"";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
