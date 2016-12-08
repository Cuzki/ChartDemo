/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * <p/>
 * 图标类
 *
 * @author Cuzki
 */
public class ChartFragment extends Fragment {
    RelativeLayout mRlChartContainer;
    TextView mTvTitle;
    int mChartType;//区分图标类型
    int mServiceType;//区分业务类型
    View mChart;
    private IChartData mData;
    private String mChartName;

    private static final String ARG_CHART_TYPE = "ARG_CHART_TYPE";
    private static final String ARG_SERVICE_TYPE = "ARG_SERVICE_TYPE";
    private static final String ARG_CHART_NAME_TYPE = "ARG_CHART_NAME_TYPE";

    private static final String KEY_DATA = "KEY_DATA";

    /**
     * 控件是否初始化完成
     */
    private boolean mIsViewCreated = false;
    /**
     * 数据是否已加载完毕
     */
    private boolean mIsLoadDataCompleted = false;

    Bundle bundle;

    public ChartFragment() {

    }

    /**
     * @param chartType   图标类型
     * @param serviceType 服务类型
     * @return
     */
    public static ChartFragment newInstance(int chartType, int serviceType,String title) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHART_TYPE, chartType);
        args.putInt(ARG_SERVICE_TYPE, serviceType);
        args.putString(ARG_CHART_NAME_TYPE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle = savedInstanceState;
        View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
        RelativeLayout layout = (RelativeLayout) rootView;
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_chart_title);
        mRlChartContainer = (RelativeLayout) rootView.findViewById(R.id.rl_chart_container);

        mChartType = getArguments().getInt(ARG_CHART_TYPE);
        mServiceType = getArguments().getInt(ARG_SERVICE_TYPE);
        mChartName = getArguments().getString(ARG_CHART_NAME_TYPE);
        Log.i("cxy", "调用onCreateView chart= " + mChartType);
        addChart();
        setService();
        mIsViewCreated = true;
        return rootView;
    }

    private void setService() {
        switch (mServiceType) {
            case ViewPagerChartsFragment.SERVER_MEMBER_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_SEX_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_AGE_DISTRIBUTE:
                break;
            case ViewPagerChartsFragment.SERVER_QUALIFICATION_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_COMPANY_LIFE_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_RANK_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_TIME_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_REASON_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_POSITION_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_PROMOTE_DISTRIBUTE:

                break;


        }
    }

    private void addChart() {
        switch (mChartType) {
            case 1://线形图
                LineChartView lineChartView = new LineChartView(getActivity());
                lineChartView.setZoomType(ZoomType.HORIZONTAL);
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                lineChartView.setValueSelectionEnabled(true);
                mRlChartContainer.addView(lineChartView);
                mChart = lineChartView;
                break;
            case 2://柱状图
                ColumnChartView columnChartView = new ColumnChartView(getActivity());
                columnChartView.setValueSelectionEnabled(true);
                columnChartView.setZoomType(ZoomType.HORIZONTAL);
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                columnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                mRlChartContainer.addView(columnChartView);
                mChart = columnChartView;
                break;
            case 3://自定义南丁格尔玫瑰图（兼自定义饼图）
                PanelRoseView panelRoseView = new PanelRoseView(getActivity());
                panelRoseView.setDrawCenter(true);
                panelRoseView.setOnSelectedListener(new PanelRoseView.onRosePanelSelectedListener() {
                    @Override
                    public void onRosePanelSelected(int index) {
//                        FragmentTransaction transaction=ChartFragment.this.getChildFragmentManager().beginTransaction();
//                        transaction.replace(R.id.rl_container,new TestFragment());
//                        transaction.commitAllowingStateLoss();
                    }
                });
                mRlChartContainer.addView(panelRoseView);
                mChart = panelRoseView;
                break;
            case 4://折线柱状混合图
                ComboLineColumnChartView comboLineColumnChartView = new ComboLineColumnChartView(getActivity());
                comboLineColumnChartView.setValueSelectionEnabled(true);
                comboLineColumnChartView.setOnValueTouchListener(new ComboLineColumnChartOnValueSelectListener() {
                    @Override
                    public void onValueDeselected() {

                    }

                    @Override
                    public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

                    }

                    @Override
                    public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {

                    }
                });

                comboLineColumnChartView.setZoomType(ZoomType.HORIZONTAL);
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                comboLineColumnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                mRlChartContainer.addView(comboLineColumnChartView);
                mChart = comboLineColumnChartView;
                break;
//            case 5://框架饼图
//                PieChartView pieChartView = new PieChartView(getActivity());
//                pieChartView.setCircleFillRatio(0.8f);
//                pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
//                    @Override
//                    public void onValueDeselected() {
//
//                    }
//
//                    @Override
//                    public void onValueSelected(int arcIndex, SliceValue value) {
//
//                    }
//                });
//                /** Note: Chart is within ViewPager so enable container scroll mode. **/
//                pieChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//                pieChartView.setValueSelectionEnabled(true);
//                mRlChartContainer.addView(pieChartView);
//                mChart = pieChartView;
//                break;
        }
        mTvTitle.setText(mChartName);
    }

    private static class HeightValueFormatter extends SimpleAxisValueFormatter {

        private float scale;
        private float sub;
        private int decimalDigits;

        public HeightValueFormatter(float scale, float sub, int decimalDigits) {
            this.scale = scale;
            this.sub = sub;
            this.decimalDigits = decimalDigits;
        }


        @Override
        public int formatValueForAutoGeneratedAxis(char[] formattedValue, float value, int autoDecimalDigits) {
            float scaledValue = (value + sub) / scale;
            return super.formatValueForAutoGeneratedAxis(formattedValue, scaledValue, this.decimalDigits);
        }
    }

    private ComboLineColumnChartData generateComBineData(IChartData provider) {
        if (provider == null) {
            provider = new NullChartDataProvider();
        }
        //为了最优化显示，获取折线图最大值,折线图index 为0，柱状图为1
        float lineMax = 0;
        for (int i = 0; i < provider.getDateCount(); ++i) {
            if (provider.getY(i, 0) > lineMax) {
                lineMax = provider.getY(i, 0);
            }
        }
        final float tempoRange = lineMax;

        float columeMax = 0;
        float columeMin = 0;
        for (int i = 0; i < provider.getDateCount(); ++i) {
            if (provider.getY(i, 1) > columeMax) {
                columeMax = provider.getY(i, 1);
            }
            if (provider.getY(i, 1) < columeMin) {
                columeMin = provider.getY(i, 1);
            }
        }
        final float scale = tempoRange / columeMax;
        final float sub = (columeMin * scale) / 2;

        ComboLineColumnChartData data = new ComboLineColumnChartData(generateColumnData(provider, scale, sub), generateLineData(provider));
        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int i = 0; i < provider.getDateCount(); i++) {
            AxisValue a = new AxisValue(i, provider.getCoordinateLabel(i).toCharArray());
            axvalues.add(a);
        }
        Axis axisLeftY = new Axis().setHasLines(true).setFormatter(new HeightValueFormatter(scale, sub, 0));
        Axis axisX = new Axis().setValues(axvalues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisLeftY);

        Axis axisRightY = new Axis().setHasLines(true).setFormatter(new SimpleAxisValueFormatter().setAppendedText("%".toCharArray()));
        data.setAxisYRight(axisRightY);
        return data;
    }

    private LineChartData generateLineData(IChartData linePrivider) {
        if (linePrivider == null) {
            linePrivider = new NullChartDataProvider();
        }
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < linePrivider.getDateCount(); ++j) {
            values.add(new PointValue(j, linePrivider.getY(j, 0)).setLabel("" + (int) (linePrivider.getY(j, 0))));
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.pickColor());
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(false).setHasLabelsOnlyForSelected(true);
        lines.add(line);

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    private ColumnChartData generateColumnData(IChartData columeProvider, final float scale, final float sub) {
        if (columeProvider == null) {
            columeProvider = new NullChartDataProvider();
        }
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < columeProvider.getDateCount(); ++i) {
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(columeProvider.getY(i, 1) * scale - sub, ChartUtils.COLOR_GREEN).setLabel((int) (columeProvider.getY(i, 1)) + ""));
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private LineChartData generateLineChartData(IChartData providers) {
        if (providers == null) {
            providers = new NullChartDataProvider();
        }
        List<Line> lines = new ArrayList<Line>();
        for (int j = 0; j < providers.getChildCount(); j++) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 1; i <= providers.getDateCount(); ++i) {
                values.add(new PointValue(i, providers.getY(i - 1, j)).setLabel("" + (int) (providers.getY(i - 1, j))));
            }
            Line line = new Line(values);
            line.setColor(ChartUtils.pickColor());
            line.setFilled(true);
            line.setShape(getShape(j));
            line.setStrokeWidth(1).setHasLabelsOnlyForSelected(true);
            lines.add(line);
        }
        LineChartData data = new LineChartData(lines);

        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int i = 1; i <= providers.getDateCount(); ++i) {
            AxisValue v = new AxisValue(i, (i + "月").toCharArray());
            axvalues.add(v);
        }

        data.setAxisXBottom(new Axis().setValues(axvalues).setHasLines(true).setTextColor(ChartUtils.COLOR_ORANGE).setFormatter(new SimpleAxisValueFormatter().setAppendedText("月".toCharArray())).setHasTiltedLabels(true));
        data.setAxisYLeft(new Axis().setHasLines(true));
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        return data;

    }

    private ValueShape getShape(int index) {
        int i = index % 3;
        if (i == 0) {
            return ValueShape.CIRCLE;
        }
        if (i == 1) {
            return ValueShape.SQUARE;
        }
        if (i == 2) {
            return ValueShape.DIAMOND;
        }
        return ValueShape.CIRCLE;
    }

    private ColumnChartData generateColumnChartData(IChartData provider) {
        if (provider == null) {
            provider = new NullChartDataProvider();
        }
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int j = 0; j < provider.getDateCount(); j++) {
            values = new ArrayList<SubcolumnValue>();
            for (int i = 0; i < provider.getChildCount(); ++i) {
                values.add(new SubcolumnValue(provider.getY(j, i), ChartUtils.pickColor()).setLabel("" + (int) (provider.getY(j, i))));
            }
            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(true);
            columns.add(column);
            AxisValue v = new AxisValue(j, provider.getCoordinateLabel(j).toCharArray());
            axvalues.add(v);
        }

        ColumnChartData data = new ColumnChartData(columns);
        if(mServiceType==ViewPagerChartsFragment.SERVER_AGE_DISTRIBUTE||mServiceType==ViewPagerChartsFragment.SERVER_RANK_P_DISTRIBUTE||mServiceType==ViewPagerChartsFragment.SERVER_RANK_M_DISTRIBUTE){
            data.setStacked(true);
        }
        data.setAxisXBottom(new Axis().setValues(axvalues).setHasLines(true).setMaxLabelChars(4).setHasTiltedLabels(true));
        data.setAxisYLeft(new Axis().setHasLines(true));
        return data;
    }


    private PieChartData generatePieChartData(IChartData provider) {
        if (provider == null) {
            provider = new NullChartDataProvider();
        }

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < provider.getDateCount(); ++i) {
            values.add(new SliceValue(provider.getY(i, 0), ChartUtils.pickColor()).setLabel(provider.getCoordinateLabel(i)));
        }

        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        return data;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("cxy", "fragment " + (isVisibleToUser ? "可见" : "不可见") + mChartType + "mIsViewCreated -mIsLoadDataCompleted" + mIsViewCreated + "-" + mIsLoadDataCompleted);
        if (isVisibleToUser && mIsViewCreated && !mIsLoadDataCompleted) {
            requestDate();
        }

    }

    private void requestDate() {
        if (mData != null && mData.getDateCount() > 0) {
            Log.i("cxy", "fragment" + mChartType + "设置导入数据" + mData.getChildCount() + "条数");
            mIsLoadDataCompleted = true;
            setChartData();
        } else {
            mIsLoadDataCompleted = false;
            Log.i("cxy", "fragment" + mChartType + "请求数据");
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIsLoadDataCompleted = true;
            mData = null;
            switch (mChartType) {//造假数据
                case 1://线形图
                    mData = new ChartData();
                    break;
                case 2://柱状图
                    mData = (mServiceType==ViewPagerChartsFragment.SERVER_AGE_DISTRIBUTE||mServiceType==ViewPagerChartsFragment.SERVER_RANK_P_DISTRIBUTE||mServiceType==ViewPagerChartsFragment.SERVER_RANK_M_DISTRIBUTE)?new ChartData1():new ChartData0();
                    break;
                case 3://南丁格尔玫瑰图
                    mData=new RoseData();
                    break;
                case 4://折线柱状混合图
                    mData = new ChartData2();
                    break;
                case 5:
                    mData = new ChartData1();
                    break;
            }
            setChartData();
        }
    };

    private void setChartData() {
        switch (mChartType) {
            case 1://hellochart 线形图
                LineChartView lineChartView = (LineChartView) mChart;
                lineChartView.setLineChartData(generateLineChartData(mData));
                break;
            case 2://hellochart 柱状图
                ColumnChartView columnChartView = (ColumnChartView) mChart;
                columnChartView.setColumnChartData(generateColumnChartData(mData));
                break;
            case 3://南丁格尔玫瑰图（兼自定义饼图）
                PanelRoseView panelRoseView= (PanelRoseView) mChart;
                panelRoseView.setPanelRoseData(mData);
                break;
            case 4://hellochart 折线柱状混合图
                ComboLineColumnChartView comboLineColumnChartView = (ComboLineColumnChartView) mChart;
                comboLineColumnChartView.setComboLineColumnChartData(generateComBineData(mData));
                break;
//            case 5://hellochart 饼图
//                PieChartView pieChartView = (PieChartView) mChart;
//                pieChartView.setPieChartData(generatePieChartData(mData));
//                break;
        }
        if (mChart instanceof AbstractChartView) {
            ((AbstractChartView) mChart).startDataAnimation();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("cxy", "调用onActivityCreated ");
        if (getUserVisibleHint()) {
            requestDate();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null) {
            Log.i("cxy", "调用onSaveInstanceState  存入信息" + mData.getChildCount() + "条数");
            outState.putSerializable(KEY_DATA, (Serializable) mData);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        getView();
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Log.i("cxy", "savedInstanceState  null" + mChartType);
            return;
        }
        Serializable serializable = savedInstanceState.getSerializable(KEY_DATA);
        if (serializable != null && serializable instanceof IChartData) {
            mData = (IChartData) savedInstanceState.getSerializable(KEY_DATA);
            Log.i("cxy", "fragment" + mChartType + "调用onCreate获取到存储信息:  " + mData.getChildCount() + "条数");
        }
    }

}
