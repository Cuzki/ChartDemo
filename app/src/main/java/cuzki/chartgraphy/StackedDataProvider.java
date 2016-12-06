/**
 * Created on 2016/12/6
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class StackedDataProvider implements ICombineDateProvider {
    float[] floats={10,2,50,20,30,60,40,25,16,50,12,5};
    String[] stings={"10-20岁","10-20岁","10-20岁","10-20岁","10-20岁","10-20岁"};
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

