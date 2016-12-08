package cuzki.chartgraphy;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class RoseData implements IChartData {
    float[][]floats={{1,200},{1,350},{1,250},{1,115},{1,221}};
    String[] stings={"2016.1","2016.2","2016.3","2016.4","2016.5"};
    String[] stingv={"200人","350人","250人","115人","221人"};
    @Override
    public int getDateCount() {
        return floats.length;
    }

    @Override
    public float getY(int indexX,int indexY) {
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
    public String getValueLabel(int indexX) {
        if(indexX>=stingv.length||stingv[indexX]==null){
            return "";
        }
        return stingv[indexX];
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getChildCount() {
        return floats[0].length;
    }
}

