package cuzki.chartgraphy;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class RoseData implements IChartData {
    float[][]floats={{1,200},{0.8f,350},{0.6f,250},{0.4f,115},{0.2f,221}};
    String[] stings={"2016.4","2016.2","2016.3的法师法师打发实得分","2016.4","2016.5000000000000000"};
    String[] stingv={"200人","350人","250人","115人","221人"};
    @Override
    public int getDateCount() {
        return floats.length;
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
        return ""+ getValue(indexX,indexY);
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

