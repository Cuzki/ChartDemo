package cuzki.chartgraphy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerChartsFragment extends Fragment {
    public static final String ARG_COMBINE_TYPE = "ARG_COMBINE_TYPE";
    private static final int TYPE_MEMBER_STATISTIC = 0;
    private static final int TYPE_CHANGE_ANALYZE =1;
    private int mServiceType;//区分业务类型

    /**
     * 业务类型
     */
    public static final int SERVER_MEMBER_DISTRIBUTE=1;//部门人员分布
    public static final int SERVER_SEX_DISTRIBUTE=2;//性别分布
    public static final int SERVER_AGE_DISTRIBUTE=3;//年龄分布
    public static final int SERVER_QUALIFICATION_DISTRIBUTE=4;//学历分布
    public static final int SERVER_COMPANY_LIFE_DISTRIBUTE=5;//司龄分布
    public static final int SERVER_RANK_DISTRIBUTE=6;//职级分布
    public static final int SERVER_RANK_P_DISTRIBUTE=7;//P职级分布
    public static final int SERVER_RANK_M_DISTRIBUTE=8;//M职级分布
    public static final int SERVER_CURRENT_LEAVE_TIME_DISTRIBUTE=9;//近3年离职时间分析
    public static final int SERVER_CURRENT_LEAVE_REASON_DISTRIBUTE=10;//近3年离职原因分析
    public static final int SERVER_CURRENT_LEAVE_POSITION_DISTRIBUTE=11;//近3年离职岗位分析
    public static final int SERVER_CURRENT_PROMOTE_DISTRIBUTE=11;//近3年晋升趋势分析



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this becomes too
     * memory intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    NavigationView mNvChart;


    public static ViewPagerChartsFragment newInstance(int serviceType) {
        ViewPagerChartsFragment fragment = new ViewPagerChartsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COMBINE_TYPE, serviceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view_pager_charts,null);
        mServiceType=getArguments().getInt(ARG_COMBINE_TYPE,TYPE_MEMBER_STATISTIC);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mNvChart= (NavigationView) view.findViewById(R.id.nv_chart);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mNvChart.setSelect(position);
            }
        });
        initChartNavigation();
        return view;
    }

    private void initChartNavigation() {
        mNvChart.refreshCount(mSectionsPagerAdapter.getCount());
        mNvChart.setSelect(0);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ChartFragment.newInstance(getChartType(position),getServerType(position), getPageTitle(position).toString());
        }

        @Override
        public int getCount() {
            return mServiceType==TYPE_MEMBER_STATISTIC?6:4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            final boolean isMemberStatisticUse=(mServiceType==TYPE_MEMBER_STATISTIC);
            switch (position) {
                case 0:
                    return isMemberStatisticUse?"部门员工分布":"近3年离职时间分析";
                case 1:
                    return isMemberStatisticUse?"性别比例":"近3年离职原因分析";
                case 2:
                    return isMemberStatisticUse?"年龄层分布":"近3年离职岗位分析";
                case 3:
                    return isMemberStatisticUse?"学历分布":"近3年晋升趋势分析";
                case 4:
                    return isMemberStatisticUse?"司龄分布":"";
                case 5:
                    return isMemberStatisticUse?"职级分布":"";
            }
            return "";
        }

        public int getChartType(int position){// 1:线形图 2:柱状图 3:自定义南丁格尔玫瑰图（兼自定义饼图）4:折线柱状混合图
            int type=1;
            final boolean isMemberStatisticUse=(mServiceType==TYPE_MEMBER_STATISTIC);
            switch (position) {
                case 0:
                    type= (isMemberStatisticUse?2:1);
                    break;
                case 1:
                    type=(isMemberStatisticUse?3:3);
                    break;
                case 2:
                    type= (isMemberStatisticUse?2:4);
                    break;
                case 3:
                    type= (isMemberStatisticUse?3:4);
                    break;
                case 4:
                    type= 3;
                    break;
                case 5:
                    type= 3;
                    break;
            }
            return type;
        }

        public int getServerType(int position){// 1:线形图 2:柱状图 3:自定义南丁格尔玫瑰图（兼自定义饼图）4:折线柱状混合图
            int type=1;
            final boolean isMemberStatisticUse=(mServiceType==TYPE_MEMBER_STATISTIC);
            switch (position) {
                case 0:
                    type= (isMemberStatisticUse?SERVER_MEMBER_DISTRIBUTE:SERVER_CURRENT_LEAVE_TIME_DISTRIBUTE);
                    break;
                case 1:
                    type=(isMemberStatisticUse?SERVER_SEX_DISTRIBUTE:SERVER_CURRENT_LEAVE_REASON_DISTRIBUTE);
                    break;
                case 2:
                    type= (isMemberStatisticUse?SERVER_AGE_DISTRIBUTE:SERVER_CURRENT_LEAVE_POSITION_DISTRIBUTE);
                    break;
                case 3:
                    type= (isMemberStatisticUse?SERVER_QUALIFICATION_DISTRIBUTE:SERVER_CURRENT_PROMOTE_DISTRIBUTE);
                    break;
                case 4:
                    type= SERVER_COMPANY_LIFE_DISTRIBUTE;
                    break;
                case 5:
                    type= SERVER_RANK_DISTRIBUTE;
                    break;
            }
            return type;
        }
    }


}
