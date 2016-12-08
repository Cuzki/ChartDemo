/**
 * Created on 2016/12/2
 */
package cuzki.chartgraphy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p/>
 *
 * @author Cuzki
 */
public class MemberChangeAnalyzedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cloudoffice_change_analyze_fragment,null);

        getChildFragmentManager().beginTransaction().add(R.id.fl_container, ViewPagerChartsFragment.newInstance(1), "viewpage").commitAllowingStateLoss();
        return view;
    }
}
