/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class MemberStatisticFragment extends Fragment {
    final List<StatisticData> mData=new ArrayList<StatisticData>();
    ViewPager mVpNewest;
    NavigationView mNvNewest;
    List<View> mListViews = new ArrayList<View>();
    StasticPageAdapter mPageAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cloudoffice_member_analyze_fragment, null);
        mVpNewest = (ViewPager) view.findViewById(R.id.vp_newest_statistics);
        mNvNewest= (NavigationView) view.findViewById(R.id.nv_newest_statistics);

        List<StatisticData> datas=new ArrayList<StatisticData>();
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        datas.add(new StatisticData("总人数","70"));
        initStatisticCardData(datas);

        getChildFragmentManager().beginTransaction().add(R.id.fl_container, new ViewPagerChartsFragment(), "viewpage").commitAllowingStateLoss();

        mVpNewest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNvNewest.setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;
    }

    private void initStatisticCardData(List<StatisticData> dataList){
        mData.clear();
        mListViews.clear();
        if(dataList==null||dataList.isEmpty()){
            return;
        }
        mData.addAll(dataList);
        mPageAdapter=new StasticPageAdapter();
        LayoutInflater lf = LayoutInflater.from(getActivity());
        for(int i=0;i<mPageAdapter.getCount();i++){
            mListViews.add(lf.inflate(R.layout.stastic_item_layout, null));
        }
        mVpNewest.setAdapter(mPageAdapter);
        mNvNewest.refreshCount(mPageAdapter.getCount());
        mNvNewest.setSelect(0);
    }
    class StasticPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mData == null) {
                return 0;
            }
            int count = 0;
            if (mData.size() % 4 == 0) {
                count = mData.size() / 4;
            } else {
                count = (int) (mData.size() / 4) + 1;
            }
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView(mListViews.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position));
            int start=position*4;
            int end=position*4+3;
            View root=mListViews.get(position);
            for(int i=start;i<=end&&i<mData.size();i++){
                View view=null;
                if(i==start){
                    view= root.findViewById(R.id.content_one);
                }
                if(i==start+1){
                    view= root.findViewById(R.id.content_two);
                }
                if(i==start+2){
                    view= root.findViewById(R.id.content_three);
                }
                if(i==start+3){
                    view= root.findViewById(R.id.content_four);
                }
                TextView count= (TextView) view.findViewById(R.id.tv_count);
                TextView title= (TextView) view.findViewById(R.id.tv_title);
                count.setText(mData.get(i).count+"");
                title.setText(mData.get(i).title+"");
            }
            return mListViews.get(position);
        }

    }


}

