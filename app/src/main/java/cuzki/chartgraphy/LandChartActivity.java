/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.OrientationEventListener;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class LandChartActivity extends FragmentActivity {
    public static final String KEY_PAGE_TYPE="KEY_PAGE_TYPE";
    private int mPageType;
    OrientationEventListener mOrientationListener;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(LandChartActivity.this!=null&&!LandChartActivity.this.isFinishing()){
                        LandChartActivity.this.setResult(101);
                        LandChartActivity.this.finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mPageType=savedInstanceState.getInt(KEY_PAGE_TYPE,0);
        }else {
            mPageType=getIntent().getIntExtra(KEY_PAGE_TYPE,0);
        }
        setContentView(R.layout.cloudoffice_data_chart_activity);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,ViewPagerChartsFragment.newInstance(mPageType), "viewpage").commitAllowingStateLoss();
        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                if(orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                orientation=orientation%360;
                if( orientation > 350 || orientation< 10 ) { //0度
                    orientation = 0;
                }
                else if( orientation > 80 &&orientation < 100 ) { //90度
                    orientation= 90;
                }
                else if( orientation > 170 &&orientation < 190 ) { //180度
                    orientation= 180;
                }
                else if( orientation > 260 &&orientation < 280  ) { //270度
                    orientation= 270;
                }
                else {
                    return;
                }
                if((orientation==180||orientation==0)){
                    mHandler.sendEmptyMessageDelayed(1,1500);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationListener.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationListener.disable();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if(outState!=null){
            outState.putInt(KEY_PAGE_TYPE, mPageType);
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }


}
