/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class CombineDateProvider1 implements ICombineDateProvider {
    float[][]floats={{300},{360},{650},{350},{521},{221},{350},{112},{120},{572},{280},{745}};
    String[] stings={"2016.1","2016.2","2016.3","2016.4","2016.5","2016.6","2016.7","2016.8","2016.9","2016.10","2016.11","2016.12"};
    @Override
    public int getDateCount() {
        return floats.length;
    }

    @Override
    public int getChildCount() {
        return floats[0].length;
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
}
