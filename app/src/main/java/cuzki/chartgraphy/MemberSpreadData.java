/**
 * Created on 2017/2/20
 */
package cuzki.chartgraphy;

import android.graphics.Color;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class MemberSpreadData extends ChartData {
    @Override
    public int getChildColor(int indexX, int indexY) {
        return Color.parseColor(indexY==1?"#dce4ec":"#91c8ff");
    }

    @Override
    public String getValueLabel(int indexX, int indexY) {
        return getValue(indexX,0)+"";
    }
}
