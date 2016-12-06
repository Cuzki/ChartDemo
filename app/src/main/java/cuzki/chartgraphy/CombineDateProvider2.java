/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class CombineDateProvider2  implements ICombineDateProvider {
    float[] floats={80,55,21,120,57,55,55,528,85,532,75,78};
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

