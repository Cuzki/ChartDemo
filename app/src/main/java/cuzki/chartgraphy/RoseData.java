package cuzki.chartgraphy;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class RoseData implements ICombineDateProvider {
    float[][]floats={{30,200},{40,350},{55,250},{60,115},{78,221}};
    String[] stings={"2016.1","2016.2","2016.3","2016.4","2016.5"};
    @Override
    public int getDateCount() {
        return floats.length;
    }

    @Override
    public float getY(int indexX,int indexY) {
        return floats[indexX][indexY];
    }

    @Override
    public String getLabel(int indexX) {
        if(indexX>=stings.length||stings[indexX]==null){
            return "";
        }
        return stings[indexX];
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

