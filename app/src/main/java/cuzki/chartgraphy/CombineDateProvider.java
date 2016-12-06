/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class CombineDateProvider implements ICombineDateProvider {
    float[][] floats={{60,50,20},{32,10,150},{82,77,52},{20,14,50},{80,30,21},{50,44,232},{52,15,30},{72,21,10},{60,150,11},{60,151,89},{44,15,80},{45,60,10}};
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
