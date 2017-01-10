/**
 * Created on 2017/1/10
 */
package cuzki.chartgraphy;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class HBarChartData {
    private float recover_complete;
    private String name;
    private float recover_uncomplete;

    public float getRecover_complete() {
        return recover_complete;
    }

    public void setRecover_complete(float recover_complete) {
        this.recover_complete = recover_complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRecover_uncomplete() {
        return recover_uncomplete;
    }

    public void setRecover_uncomplete(float recover_uncomplete) {
        this.recover_uncomplete = recover_uncomplete;
    }
}
