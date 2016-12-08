package cuzki.chartgraphy;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;

public class DataAnalyzedActivity extends FragmentActivity implements View.OnClickListener {


    OrientationEventListener mOrientationListener;//重力感应器
    private TabLayout mTab;
    private int  mCurrentFragmentPosition=0;
    boolean mIsAbleJump = true;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mIsAbleJump = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloudoffice_data_analyze_activity);
        initView();
        initEvent();

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                orientation = orientation % 360;
                if (orientation > 350 || orientation < 10) { //0度
                    orientation = 0;
                } else if (orientation > 80 && orientation < 100) { //90度
                    orientation = 90;
                } else if (orientation > 170 && orientation < 190) { //180度
                    orientation = 180;
                } else if (orientation > 260 && orientation < 280) { //270度
                    orientation = 270;
                } else {
                    return;
                }
                Log.i("cxys",
                        "Orientation changed to " + orientation + "  " + mIsAbleJump);
                if ((orientation == 270 || orientation == 90) && mIsAbleJump) {
                    mIsAbleJump = false;
                    Intent intent=new Intent(DataAnalyzedActivity.this,LandChartActivity.class);
                    intent.putExtra(LandChartActivity.KEY_PAGE_TYPE,mCurrentFragmentPosition);
                    DataAnalyzedActivity.this.startActivityForResult(intent, 10);
                }
            }
        };

    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.tab_fragment_title);
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTab.addTab(mTab.newTab().setText(this.getResources().getString(R.string.member_statistic)),true);
        mTab.addTab(mTab.newTab().setText(this.getResources().getString(R.string.member_change_analyze)));
    }

    private void initEvent() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHandler.sendEmptyMessageDelayed(1, 1500);
        Log.i("cxys",
                "onActivityResult ");
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
        mIsAbleJump = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    private void showFragment(final int index) {
        mCurrentFragmentPosition=index;
        final boolean isMember = (index == 0);
        final String showTag = isMember ? MemberStatisticFragment.class.getSimpleName() : MemberChangeAnalyzedFragment.class.getSimpleName();
        final String hideTag = !isMember ? MemberStatisticFragment.class.getSimpleName() : MemberChangeAnalyzedFragment.class.getSimpleName();
        Fragment showFragment = getSupportFragmentManager().findFragmentByTag(showTag);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (showFragment == null) {
            showFragment = isMember ? new MemberStatisticFragment() : new MemberChangeAnalyzedFragment();
            trans.add(R.id.container, showFragment, showTag);
        } else {
            trans.show(showFragment);
        }
        Fragment hideFragment = getSupportFragmentManager().findFragmentByTag(hideTag);
        if (hideFragment != null && !hideFragment.isHidden()) {
            trans.hide(hideFragment);
        }
        trans.commitAllowingStateLoss();

    }

    @Override
    public void onClick(View view) {
    }

}