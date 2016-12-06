/**
 * Created on 2016/12/5
 */
package cuzki.chartgraphy;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class NavigationView extends LinearLayout {

    private Context mContext;
    public NavigationView(Context context) {
        super(context);
        init( context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init( context);
    }

    private void init(Context context){
        mContext=context;
        this.setOrientation(LinearLayout.HORIZONTAL);
    }

    public void refreshCount(int count){
        this.removeAllViews();
        if(count<=0){
            this.setVisibility(INVISIBLE);
            return;
        }else {
            this.setVisibility(VISIBLE);
        }
        for(int i=0;i<count;i++){
            ImageView point=new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    10, 10);
            layoutParams.setMargins(5, 0, 5, 0);
            point.setLayoutParams(layoutParams);
            point.setImageResource(R.drawable.bg_navigion_shape_selector);
            addView(point);
        }
    }

    public void setSelect(int position){
        int count = getChildCount();
        if(position>=0&&position<count){
            for(int i=0;i<count;i++){
                ImageView point= (ImageView) getChildAt(i);
                point.setImageLevel(i==position?1:0);
            }
        }
    }

}
