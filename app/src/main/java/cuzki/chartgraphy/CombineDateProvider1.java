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
    float[] floats={10,2,50,20,30,60,40,25,16,50,12,5};
    String[] stings={"2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1","2016.1"};
    @Override
    public int getDateCount() {
        return floats.length;
    }

    @Override
    public float getY(int indexX) {
        return floats[indexX];
    }

    @Override
    public String getLabel(int indexX) {
        if(indexX>=stings.length||stings[indexX]==null){
            return "";
        }
        return stings[indexX];
    }
}
