package cuzki.chartgraphy;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

public class DataAnalyzedActivity extends FragmentActivity implements View.OnClickListener {

    private TabLayout mTab;
    private int  mCurrentFragmentPosition=0;
    ViewPagerChartsFragment mLandFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("land","onCreate orientation");
        Log.i("info","portrait"); // 竖屏
        setContentView(R.layout.cloudoffice_data_analyze_activity);
        initView();
        initEvent();

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("land","onConfigurationChanged orientation="+(newConfig.orientation == 1?"竖屏":"横屏"));
        if(newConfig.orientation == 2){//land
            int position=0;
            final String showTag = mCurrentFragmentPosition==0 ? MemberStatisticFragment.class.getSimpleName() : MemberChangeAnalyzedFragment.class.getSimpleName();
            Fragment showFragment = getSupportFragmentManager().findFragmentByTag(showTag);
//            if(showFragment!=null){
//                if(showFragment instanceof MemberStatisticFragment){
//                    position=((MemberStatisticFragment)showFragment).getCurrentFramentIndex();
//                }else{
//                    position=((MemberChangeAnalyzedFragment)showFragment).getCurrentFramentIndex();
//                }
//            }
            getSupportFragmentManager().beginTransaction().add(R.id.landContainer,mLandFragment=ViewPagerChartsFragment.newInstance(mCurrentFragmentPosition,true,position), "viewpage").commitAllowingStateLoss();
        }else{
            if(mLandFragment!=null){
//                int position=mLandFragment.getCurrentFramentIndex();
//                getSupportFragmentManager().beginTransaction().remove(mLandFragment).commitAllowingStateLoss();
//                setCurrentViewPagePosition(position);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void setCurrentViewPagePosition(int currentFragmentPosition) {
        final String showTag = mCurrentFragmentPosition==0 ? MemberStatisticFragment.class.getSimpleName() : MemberChangeAnalyzedFragment.class.getSimpleName();
        Fragment showFragment = getSupportFragmentManager().findFragmentByTag(showTag);
        if(showFragment!=null){
            if(showFragment instanceof MemberStatisticFragment){
                ((MemberStatisticFragment)showFragment).setViewPageIndex(currentFragmentPosition);
            }else{
                ((MemberChangeAnalyzedFragment)showFragment).setViewPageIndex(currentFragmentPosition);
            }
        }
    }
}