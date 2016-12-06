/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class MemberChangeAnalyzedFragment extends Fragment {



    ViewPager mNewestViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cloudoffice_member_analyze_fragment,null);
        mNewestViewPager = (ViewPager) view.findViewById(R.id.vp_newest_statistics);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
        mNewestViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        getChildFragmentManager().beginTransaction().add(R.id.fl_container, new ViewPagerChartsFragment(), "viewpage").commitAllowingStateLoss();
        return view;
    }
}
