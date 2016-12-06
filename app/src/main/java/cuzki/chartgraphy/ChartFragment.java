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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
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
import lecho.lib.hellocharts.view.PieChartView;

/**
 * <p/>
 * 图标类
 *
 * @author Cuzki
 */
public class ChartFragment extends Fragment {
    RelativeLayout mRlTitleContainer;
    TextView mTvTitle;
    int mChartType;//区分图标类型
    int mServiceType;//区分业务类型
    View mChart;
    private final List<ICombineDateProvider> mData=new ArrayList<ICombineDateProvider>();
    private static final String ARG_CHART_TYPE = "ARG_CHART_TYPE";
    private static final String ARG_SERVICE_TYPE = "ARG_SERVICE_TYPE";
    private static final String KEY_DATA = "KEY_DATA";

    /**
     * 控件是否初始化完成
     */
    private boolean mIsViewCreated=false;
    /**
     * 数据是否已加载完毕
     */
    private boolean mIsLoadDataCompleted=false;

    Bundle bundle;

    public ChartFragment() {

    }

    /**
     *
     * @param chartType 图标类型
     * @param serviceType 服务类型
     * @return
     */
    public static ChartFragment newInstance(int chartType, int serviceType) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHART_TYPE, chartType);
        args.putInt(ARG_SERVICE_TYPE, serviceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle=savedInstanceState;
        View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
        LinearLayout layout = (LinearLayout) rootView;
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_chart_title);
        mRlTitleContainer = (RelativeLayout) rootView.findViewById(R.id.rl_title_container);

        mChartType = getArguments().getInt(ARG_CHART_TYPE);
        mServiceType=getArguments().getInt(ARG_SERVICE_TYPE);
        Log.i("cxy","调用onCreateView chart= "+ mChartType );
        addChart(layout);
        setService();
        mIsViewCreated=true;
        return rootView;
    }

    private void setService() {
        switch (mServiceType) {

        }
    }

    private void addChart(LinearLayout layout){
        String title = "";
        switch (mChartType) {
            case 1://线形图
                LineChartView lineChartView = new LineChartView(getActivity());
                lineChartView.setZoomType(ZoomType.HORIZONTAL);
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                lineChartView.setValueSelectionEnabled(true);
                layout.addView(lineChartView);
                title = "线形图";
                mChart=lineChartView;
                break;
            case 2://柱状图
                ColumnChartView columnChartView = new ColumnChartView(getActivity());
                columnChartView.setValueSelectionEnabled(true);
                columnChartView.setZoomType(ZoomType.HORIZONTAL);
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                columnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                layout.addView(columnChartView);
                title = "柱状图";
                mChart=columnChartView;
                break;
            case 3://折线柱状混合图
                PanelRoseView panelRoseView=new PanelRoseView(getActivity());
                layout.addView(panelRoseView);
                mChart=panelRoseView;
                break;
            case 4:
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
                layout.addView(comboLineColumnChartView);
                mChart=comboLineColumnChartView;
                break;
            case 5:
                PieChartView pieChartView = new PieChartView(getActivity());
                pieChartView.setCircleFillRatio(0.8f);
                pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
                    @Override
                    public void onValueDeselected() {

                    }

                    @Override
                    public void onValueSelected(int arcIndex, SliceValue value) {

                    }
                });
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                pieChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
                pieChartView.setValueSelectionEnabled(true);
                layout.addView(pieChartView);
                title = "饼状图";
                mChart=pieChartView;
                break;
        }
        mTvTitle.setText(title);
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

    private ComboLineColumnChartData generateComBineData(ICombineDateProvider columeProvider,ICombineDateProvider linePrivider) {
        if(columeProvider==null){
            columeProvider=new NullChartDataProvider();
        }
        if(linePrivider==null){
            linePrivider=new NullChartDataProvider();
        }
        //为了最优化显示，获取折线图最大值
        float lineMax = 0;
        for (int i = 0; i < linePrivider.getDateCount(); ++i) {
            if (linePrivider.getY(i) > lineMax) {
                lineMax = linePrivider.getY(i);
            }
        }
        final float tempoRange = lineMax;

        float columeMax = 0;
        float columeMin = 0;
        for (int i = 0; i < columeProvider.getDateCount(); ++i) {
            if (columeProvider.getY(i) > columeMax) {
                columeMax = columeProvider.getY(i);
            }
            if (columeProvider.getY(i) < columeMin) {
                columeMin = columeProvider.getY(i);
            }
        }
        final float scale = tempoRange / columeMax;
        final float sub = (columeMin * scale) / 2;

        ComboLineColumnChartData data = new ComboLineColumnChartData(generateColumnData(columeProvider,scale, sub), generateLineData(linePrivider));
        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int i = 0; i < columeProvider.getDateCount(); i++) {
            AxisValue a = new AxisValue(i, columeProvider.getLabel(i).toCharArray());
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

    private LineChartData generateLineData(ICombineDateProvider linePrivider) {
        if(linePrivider==null){
            linePrivider=new NullChartDataProvider();
        }
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < linePrivider.getDateCount(); ++j) {
            values.add(new PointValue(j, linePrivider.getY(j)).setLabel("" + (int) (linePrivider.getY(j))));
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

    private ColumnChartData generateColumnData(ICombineDateProvider columeProvider, final float scale, final float sub) {
        if(columeProvider==null){
            columeProvider=new NullChartDataProvider();
        }
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < columeProvider.getDateCount(); ++i) {
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(columeProvider.getY(i) * scale - sub, ChartUtils.COLOR_GREEN).setLabel((int) (columeProvider.getY(i)) + ""));
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private LineChartData generateLineChartData(List<ICombineDateProvider> providers) {
        if (providers == null || providers.size() == 0) {
            providers = new ArrayList<ICombineDateProvider>();
        }
        List<Line> lines = new ArrayList<Line>();
        for (int j = 0; j < providers.size(); j++) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 1; i <= providers.get(j).getDateCount(); ++i) {
                values.add(new PointValue(i,  providers.get(j).getY(i - 1)).setLabel("" + (int) (providers.get(j).getY(i - 1))));
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
        for (int i = 1; i <= providers.get(0).getDateCount(); ++i) {
            AxisValue v = new AxisValue(i, (i + "月").toCharArray());
            axvalues.add(v);
        }

        data.setAxisXBottom(new Axis().setValues(axvalues).setHasLines(true).setTextColor(ChartUtils.COLOR_ORANGE).setFormatter(new SimpleAxisValueFormatter().setAppendedText("月".toCharArray())).setHasTiltedLabels(true));
        data.setAxisYLeft(new Axis().setHasLines(true));
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        return data;

    }

    private ValueShape getShape(int index){
        int i=index%3;
        if(i==0){
            return ValueShape.CIRCLE;
        }
        if(i==1){
            return ValueShape.SQUARE;
        }
        if(i==2){
            return ValueShape.DIAMOND;
        }
        return ValueShape.CIRCLE;
    }

    private ColumnChartData generateColumnChartData(ICombineDateProvider provider) {
        if (provider == null) {
            provider=new NullChartDataProvider();
        }
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int i = 0; i < provider.getDateCount(); ++i) {
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(provider.getY(i), ChartUtils.pickColor()).setLabel("" + (int) (provider.getY(i))));
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            AxisValue v = new AxisValue(i, provider.getLabel(i).toCharArray());
            axvalues.add(v);
        }

        ColumnChartData data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis().setValues(axvalues).setHasLines(true).setMaxLabelChars(4).setHasTiltedLabels(true));
        data.setAxisYLeft(new Axis().setHasLines(true));
        return data;
    }


    private PieChartData generatePieChartData(ICombineDateProvider provider) {
        if (provider == null) {
            provider=new NullChartDataProvider();
        }

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < provider.getDateCount(); ++i) {
            values.add(new SliceValue(provider.getY(i), ChartUtils.pickColor()).setLabel(provider.getLabel(i)));
        }

        PieChartData data = new PieChartData(values);
        data.setHasLabels(true);
        return data;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("cxy","fragment "+(isVisibleToUser?"可见":"不可见")+ mChartType +"mIsViewCreated -mIsLoadDataCompleted"+mIsViewCreated+"-"+mIsLoadDataCompleted);
        if (isVisibleToUser && mIsViewCreated && !mIsLoadDataCompleted) {
            requestDate();
        }

    }

    private void requestDate() {
        if(mData.size()>0){
            Log.i("cxy","fragment"+mChartType+"设置保存数据"+mData.size()+"条数" );
            mIsLoadDataCompleted=true;
            setChartData();
        }else {
            mIsLoadDataCompleted=false;
            Log.i("cxy","fragment"+mChartType+"请求数据" );
            mHandler.sendEmptyMessageDelayed(0,2000);
        }

    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIsLoadDataCompleted=true;
            mData.clear();
            switch (mChartType) {//造假数据
                case 1://线形图
                    List<ICombineDateProvider> providers=new ArrayList<>();
                    providers.add(new CombineDateProvider());
                    providers.add(new CombineDateProvider1());
                    providers.add(new CombineDateProvider2());
                    mData.addAll(providers);
                    break;
                case 2://柱状图
                    mData.add(new CombineDateProvider());
                    break;
                case 3://折线柱状混合图
                case 4:
                    mData.add(new CombineDateProvider());
                    mData.add(new CombineDateProvider1());
                    break;
                case 5:
                    PieChartView pieChartView = (PieChartView) mChart;
                    mData.add(new CombineDateProvider());
                    break;
            }
            setChartData();
        }
    };

    private void setChartData(){
        switch (mChartType) {
            case 1://线形图
                LineChartView lineChartView = (LineChartView) mChart;
                lineChartView.setLineChartData(generateLineChartData(mData));
                break;
            case 2://柱状图
                ColumnChartView columnChartView = (ColumnChartView) mChart;
                columnChartView.setColumnChartData(generateColumnChartData(mData.get(0)));
                break;
            case 3:

                break;
            case 4://折线柱状混合图
                ComboLineColumnChartView comboLineColumnChartView = (ComboLineColumnChartView) mChart;
                comboLineColumnChartView.setComboLineColumnChartData(generateComBineData(mData.get(0),mData.get(1)));
                break;
            case 5:
                PieChartView pieChartView = (PieChartView) mChart;
                pieChartView.setPieChartData(generatePieChartData(mData.get(0)));
                break;
        }
        if(mChart instanceof AbstractChartView){
            ((AbstractChartView)mChart).startDataAnimation();
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("cxy","调用onActivityCreated " );
        if (getUserVisibleHint()) {
            requestDate();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("cxy","调用onSaveInstanceState  存入信息"+mData.size()+"条数");
        outState.putSerializable(KEY_DATA, (Serializable) mData);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        getView();
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            Log.i("cxy","savedInstanceState  null"+ mChartType);
            return;
        }
        Serializable serializable=savedInstanceState.getSerializable(KEY_DATA);
        if(serializable!=null){
            mData.addAll((List<ICombineDateProvider>) savedInstanceState.getSerializable(KEY_DATA));
            Log.i("cxy","fragment"+mChartType+"调用onCreate获取到存储信息:  " +mData.size()+"条数");
        }
    }

}
