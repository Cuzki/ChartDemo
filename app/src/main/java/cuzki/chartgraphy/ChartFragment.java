/**
 * Created on 2016/12/1
 */
package cuzki.chartgraphy;

import android.graphics.Color;
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

import static cuzki.chartgraphy.ViewPagerChartsFragment.SERVER_COMPANY_LIFE_DISTRIBUTE;
import static cuzki.chartgraphy.ViewPagerChartsFragment.SERVER_CURRENT_PROMOTE_DISTRIBUTE;

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
    private IChartDataProvider mData;
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
    public static ChartFragment newInstance(int chartType, int serviceType, String title) {
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
            case SERVER_COMPANY_LIFE_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_RANK_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_TIME_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_REASON_DISTRIBUTE:

                break;
            case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_POSITION_DISTRIBUTE:

                break;
            case SERVER_CURRENT_PROMOTE_DISTRIBUTE:

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
                panelRoseView.setDrawCenter(true).setSelectedMode(true).setDividerAngel(1).setTouchMode(true)
                        .setOnSelectedListener(new PanelRoseView.onRosePanelSelectedListener() {
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
            case 5://水平柱状图
                HBarChartView landChart = new HBarChartView(getActivity());
                /** Note: Chart is within ViewPager so enable container scroll mode. **/
                mRlChartContainer.addView(landChart);
                mChart = landChart;
                break;
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

    private ComboLineColumnChartData generateComBineData(IChartDataProvider provider) {
        if (provider == null) {
            provider = new NullChartDataProviderProvider();
        }
        //为了最优化显示，获取折线图最大值,折线图index 为0，柱状图为1
        float lineMax = 0;
        for (int i = 0; i < provider.getDateCount(); ++i) {
            if (provider.getValue(i, 0) > lineMax) {
                lineMax = provider.getValue(i, 0);
            }
        }
        final float tempoRange = lineMax;

        float columeMax = 0;
        float columeMin = 0;
        for (int i = 0; i < provider.getDateCount(); ++i) {
            if (provider.getValue(i, 1) > columeMax) {
                columeMax = provider.getValue(i, 1);
            }
            if (provider.getValue(i, 1) < columeMin) {
                columeMin = provider.getValue(i, 1);
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
        Axis axisLeftY = new Axis().setHasLines(true).setFormatter(new HeightValueFormatter(scale, sub, 0)).setTextColor(Color.parseColor("#000000"));
        Axis axisX = new Axis().setValues(axvalues).setTextColor(Color.parseColor("#000000")).setHasTiltedLabels(true);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisLeftY);

        Axis axisRightY = new Axis().setFormatter(new SimpleAxisValueFormatter().setAppendedText("%".toCharArray())).setTextColor(Color.parseColor("#000000")).setTextSize(9);
        data.setAxisYRight(axisRightY);
        return data;
    }

    private LineChartData generateLineData(IChartDataProvider linePrivider) {
        if (linePrivider == null) {
            linePrivider = new NullChartDataProviderProvider();
        }
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < linePrivider.getDateCount(); ++j) {
            values.add(new PointValue(j, linePrivider.getValue(j, 0)).setLabel(linePrivider.getValueLabel(j,0)));
        }
        Line line = new Line(values);
        line.setColor(linePrivider.getChildColor(0,0));
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true).setHasLabelsOnlyForSelected(true).setShape(getShape(2));
        lines.add(line);

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    private ColumnChartData generateColumnData(IChartDataProvider columeProvider, final float scale, final float sub) {
        if (columeProvider == null) {
            columeProvider = new NullChartDataProviderProvider();
        }
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < columeProvider.getDateCount(); ++i) {
            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(columeProvider.getValue(i, 1) * scale - sub, columeProvider.getChildColor(i,1)).setLabel(columeProvider.getValueLabel(i,1)));
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private LineChartData generateLineChartData(IChartDataProvider providers) {
        if (providers == null) {
            providers = new NullChartDataProviderProvider();
        }
        List<Line> lines = new ArrayList<Line>();
        for (int j = 0; j < providers.getChildCount(); j++) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 1; i <= providers.getDateCount(j); ++i) {
                values.add(new PointValue(i, providers.getValue(i - 1, j)).setLabel(providers.getValueLabel(i-1,j)));
            }
            Line line = new Line(values);
            line.setColor(providers.getChildColor(0,j));
            line.setFilled(true);
            line.setCubic(true);
            line.setShape(getShape(j));
            line.setStrokeWidth(1).setHasLabelsOnlyForSelected(true);
            lines.add(line);
        }
        LineChartData data = new LineChartData(lines);

        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int i = 1; i <= providers.getDateCount(); ++i) {
            AxisValue v = new AxisValue(i, providers.getCoordinateLabel(i-1).toCharArray());
            axvalues.add(v);
        }

        data.setAxisXBottom(new Axis().setValues(axvalues).setTextColor(Color.parseColor("#000000")));
        data.setAxisYLeft(new Axis().setHasLines(true).setTextColor(Color.parseColor("#000000")));
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        return data;

    }

    private ValueShape getShape(int index) {
        int i = index % 3;
        if (i == 0) {
            return ValueShape.SQUARE;
        }
        if (i == 1) {
            return ValueShape.DIAMOND;
        }
        if (i == 2) {
            return ValueShape.CIRCLE;
        }
        return ValueShape.CIRCLE;
    }

    private ColumnChartData generateColumnChartData(IChartDataProvider provider) {
        if (provider == null) {
            provider = new NullChartDataProviderProvider();
        }
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axvalues = new ArrayList<AxisValue>();
        for (int j = 0; j < provider.getDateCount(); j++) {
            values = new ArrayList<SubcolumnValue>();
            for (int i = 0; i < provider.getChildCount(); ++i) {
                values.add(new SubcolumnValue(provider.getValue(j, i), provider.getChildColor(j,i)).setLabel(provider.getValueLabel(j,i)));
            }
            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(true);
            columns.add(column);
            AxisValue v = new AxisValue(j, provider.getCoordinateLabel(j).toCharArray());
            axvalues.add(v);
        }

        ColumnChartData data = new ColumnChartData(columns);
        if (mServiceType == ViewPagerChartsFragment.SERVER_AGE_DISTRIBUTE || mServiceType == ViewPagerChartsFragment.SERVER_RANK_P_DISTRIBUTE || mServiceType == ViewPagerChartsFragment.SERVER_RANK_M_DISTRIBUTE) {
            data.setStacked(true);
        }
        data.setAxisXBottom(new Axis().setValues(axvalues).setHasLines(true).setMaxLabelChars(4).setHasTiltedLabels(true).setTextSize(8));
        data.setAxisYLeft(new Axis().setHasLines(true));
        return data;
    }


    private PieChartData generatePieChartData(IChartDataProvider provider) {
        if (provider == null) {
            provider = new NullChartDataProviderProvider();
        }

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < provider.getDateCount(); ++i) {
            values.add(new SliceValue(provider.getValue(i, 0), ChartUtils.pickColor()).setLabel(provider.getCoordinateLabel(i)));
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

            switch (mServiceType) {//造假数据
                case ViewPagerChartsFragment.SERVER_MEMBER_DISTRIBUTE://部门人员分布
                    MemberSpreadData memberSpreadData=new MemberSpreadData();
                    List<ChartDataItem> chartDataItems=new ArrayList<>();
                    chartDataItems.add(new ChartDataItem("财务部",150));
                    chartDataItems.add(new ChartDataItem("人事部",50));
                    chartDataItems.add(new ChartDataItem("行政部",20));
                    chartDataItems.add(new ChartDataItem("产品部",120));
                    chartDataItems.add(new ChartDataItem("运维部",70));
                    chartDataItems.add(new ChartDataItem("市场部",30));
                    chartDataItems.add(new ChartDataItem("开发部",80));
                    chartDataItems.add(new ChartDataItem("设计部",65));
                    memberSpreadData.chartDataItems=chartDataItems;
                    mData = memberSpreadData;
                    break;
                case ViewPagerChartsFragment.SERVER_SEX_DISTRIBUTE://性别分布
                    SexScaleData sexScaleData=new SexScaleData();
                    List<ChartDataItem> sex=new ArrayList<>();
                    sex.add(new ChartDataItem("女310",51));
                    sex.add(new ChartDataItem("男310",162));
                    sexScaleData.chartDataItems=sex;
                    mData = sexScaleData;
                    break;
                case  ViewPagerChartsFragment.SERVER_RANK_DISTRIBUTE://职级分布
                    PositionSpreadData positionSpreadData=new PositionSpreadData();
                    List<ChartDataItem> position=new ArrayList<>();
                    position.add(new ChartDataItem("P岗",51));
                    position.add(new ChartDataItem("M岗",162));
                    positionSpreadData.chartDataItems=position;
                    mData = positionSpreadData;
                    break;
                case  ViewPagerChartsFragment.SERVER_QUALIFICATION_DISTRIBUTE://学历分布
                    StudyHistroyData histroyData = new StudyHistroyData();
                    List<ChartDataItem> study=new ArrayList<>();
                    study.add(new ChartDataItem("高中及以下",51));
                    study.add(new ChartDataItem("大专",162));
                    study.add(new ChartDataItem("本科",231));
                    study.add(new ChartDataItem("研究生及以上",60));
                    histroyData.chartDataItems=study;
                    mData =histroyData;
                    break;
                case SERVER_COMPANY_LIFE_DISTRIBUTE://司龄分布
                    WorkTimeData workTimeData= new WorkTimeData();
                    List<ChartDataItem> work=new ArrayList<>();
                    work.add(new ChartDataItem("0-1年",51));
                    work.add(new ChartDataItem("1-3年",100));
                    work.add(new ChartDataItem("3-5年",60));
                    work.add(new ChartDataItem("5-10年",75));
                    work.add(new ChartDataItem("10以上年",20));
                    workTimeData.chartDataItems=work;
                    mData =workTimeData;
                    break;

                case ViewPagerChartsFragment.SERVER_AGE_DISTRIBUTE://年龄分布
                    AgeSpreadData ageSpreadData=new AgeSpreadData();
                    List<ChartDataItem> age=new ArrayList<>();
                    age.add(new ChartDataItem("18-30岁",51));
                    age.add(new ChartDataItem("31-40岁",62));
                    age.add(new ChartDataItem("41-50岁",90));
                    age.add(new ChartDataItem("51-64岁",12));
                    age.add(new ChartDataItem("65岁以上",72));
                    ageSpreadData.chartDataItems=age;
                    mData = ageSpreadData;
                    break;
                case ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_TIME_DISTRIBUTE://近3年离职时间分析

                    List<ChartDataItem> year2014s=new ArrayList<>();
                    year2014s.add(new ChartDataItem("1月",50));
                    year2014s.add(new ChartDataItem("2月",150));
                    year2014s.add(new ChartDataItem("3月",250));
                    year2014s.add(new ChartDataItem("4月",120));
                    year2014s.add(new ChartDataItem("5月",70));
                    year2014s.add(new ChartDataItem("6月",90));
                    year2014s.add(new ChartDataItem("7月",100));
                    year2014s.add(new ChartDataItem("8月",20));
                    year2014s.add(new ChartDataItem("9月",88));
                    year2014s.add(new ChartDataItem("10月",26));
                    year2014s.add(new ChartDataItem("11月",60));
                    year2014s.add(new ChartDataItem("12月",70));
                    LeaveTimeItem year2014=new LeaveTimeItem("2014",year2014s);

                    List<ChartDataItem> year2015s=new ArrayList<>();
                    year2015s.add(new ChartDataItem("1月",150));
                    year2015s.add(new ChartDataItem("2月",200));
                    year2015s.add(new ChartDataItem("3月",150));
                    year2015s.add(new ChartDataItem("4月",120));
                    year2015s.add(new ChartDataItem("5月",160));
                    year2015s.add(new ChartDataItem("6月",70));
                    year2015s.add(new ChartDataItem("7月",120));
                    year2015s.add(new ChartDataItem("8月",70));
                    year2015s.add(new ChartDataItem("9月",40));
                    year2015s.add(new ChartDataItem("10月",80));
                    year2015s.add(new ChartDataItem("11月",50));
                    year2015s.add(new ChartDataItem("12月",10));
                    LeaveTimeItem year2015=new LeaveTimeItem("2015",year2015s);

                    List<ChartDataItem> year2016s=new ArrayList<>();
                    year2016s.add(new ChartDataItem("1月",250));
                    year2016s.add(new ChartDataItem("2月",10));
                    year2016s.add(new ChartDataItem("3月",150));
                    year2016s.add(new ChartDataItem("4月",150));
                    year2016s.add(new ChartDataItem("5月",79));
                    year2016s.add(new ChartDataItem("6月",20));
                    year2016s.add(new ChartDataItem("7月",10));
                    year2016s.add(new ChartDataItem("8月",40));
//                    year2016s.add(new ChartDataItem("9月",62));
//                    year2016s.add(new ChartDataItem("10月",50));
//                    year2016s.add(new ChartDataItem("11月",40));
//                    year2016s.add(new ChartDataItem("12月",120));
                    LeaveTimeItem year2016=new LeaveTimeItem("2016",year2016s);

                    LeaveTimeData leaveTimeData=new LeaveTimeData();
                    List<LeaveTimeItem> itms=new ArrayList<>();
                    itms.add(year2014);
                    itms.add(year2015);
                    itms.add(year2016);
                    leaveTimeData.leaveTimeItems=itms;
                    mData=leaveTimeData;
                    break;
                case  ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_REASON_DISTRIBUTE://近3年离职原因分析
                    LeaveReasonData reasonData=new LeaveReasonData();
                    List<ChartDataItem> reasonDatas=new ArrayList<>();
                    reasonDatas.add(new ChartDataItem("个人原因",51));
                    reasonDatas.add(new ChartDataItem("其他",62));
                    reasonDatas.add(new ChartDataItem("不适应企业文化",90));
                    reasonDatas.add(new ChartDataItem("缺少发展空间",12));
                    reasonDatas.add(new ChartDataItem("薪资福利低",72));
                    reasonData.chartDataItems=reasonDatas;
                    mData = reasonData;
                    break;
                case  ViewPagerChartsFragment.SERVER_CURRENT_LEAVE_POSITION_DISTRIBUTE://近3年离职岗位分析
                    LeavePositionData leavePositionData=new LeavePositionData();
                    List<LeavePositionItem> leaves=new ArrayList<>();
                    leaves.add(new LeavePositionItem("设计部",51,100,20));
                    leaves.add(new LeavePositionItem("产品部",100,100,60));
                    leaves.add(new LeavePositionItem("开发部",70,100,10));
                    leaves.add(new LeavePositionItem("法政部",11,100,45));
                    leaves.add(new LeavePositionItem("总办",21,100,51));
                    leaves.add(new LeavePositionItem("商务部",70,100,10));
                    leaves.add(new LeavePositionItem("公关部",11,100,45));
                    leaves.add(new LeavePositionItem("执行部",21,100,51));
                    leavePositionData.leavePositionItems=leaves;
                    mData = leavePositionData;
                    break;
                case SERVER_CURRENT_PROMOTE_DISTRIBUTE://近3年晋升趋势分析
                    PromoteTrendData promoteTrendData=new PromoteTrendData();
                    List<PromoteTrendItem> promoteTrendItems=new ArrayList<>();
                    promoteTrendItems.add(new PromoteTrendItem("2016.1",51,100,20));
                    promoteTrendItems.add(new PromoteTrendItem("2016.2",100,100,60));
                    promoteTrendItems.add(new PromoteTrendItem("2016.3",70,100,10));
                    promoteTrendItems.add(new PromoteTrendItem("2016.4",11,100,45));
                    promoteTrendItems.add(new PromoteTrendItem("2016.5",21,100,51));
                    promoteTrendItems.add(new PromoteTrendItem("2016.6",70,100,10));
                    promoteTrendItems.add(new PromoteTrendItem("2016.7",11,100,45));
                    promoteTrendItems.add(new PromoteTrendItem("2016.8",21,100,51));
                    promoteTrendItems.add(new PromoteTrendItem("2016.9",21,100,51));
                    promoteTrendItems.add(new PromoteTrendItem("2016.10",70,100,10));
                    promoteTrendItems.add(new PromoteTrendItem("2016.11",11,100,45));
                    promoteTrendItems.add(new PromoteTrendItem("2016.12",21,100,51));
                    promoteTrendData.promoteTrendItems=promoteTrendItems;
                    mData = promoteTrendData;
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
                PanelRoseView panelRoseView = (PanelRoseView) mChart;
                if(mServiceType==ViewPagerChartsFragment.SERVER_SEX_DISTRIBUTE||mServiceType==ViewPagerChartsFragment.SERVER_RANK_DISTRIBUTE){
                    panelRoseView.setDrawCenter(false);
                }
                panelRoseView.setStartDrawAngel(-90).setPanelRoseData(mData);
                break;
            case 4://hellochart 折线柱状混合图
                ComboLineColumnChartView comboLineColumnChartView = (ComboLineColumnChartView) mChart;
                comboLineColumnChartView.setComboLineColumnChartData(generateComBineData(mData));
                break;
            case 5://水平柱状图
                HBarChartView hBarChartView = (HBarChartView) mChart;
                if(mServiceType==ViewPagerChartsFragment.SERVER_MEMBER_DISTRIBUTE){
                    hBarChartView.setXCoordinateDrawable(true).setYCoordinateDrawable(true).setCoordinateColor(Color.parseColor("#91c8ff"));
                }
                hBarChartView.setLabelTxtSize(Utils.sp2px(getResources(),15.0f));
                hBarChartView.setBarData(mData);
                break;
        }
        if (mChart instanceof AbstractChartView) {
            ((AbstractChartView) mChart).startDataAnimation();
        }
    }

    private List<HBarChartData> generateLandBarData(IChartDataProvider data) {
        List<HBarChartData> list=new ArrayList<>();
        if(data!=null){
            for(int i=0;i<data.getDateCount();i++){
                HBarChartData date=new HBarChartData();
                date.setName(data.getCoordinateLabel(i));
                date.setRecover_complete(data.getValue(i,0));
                date.setRecover_uncomplete(data.getValue(i,1));
                list.add(date);
            }
        }
        return list;
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
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Log.i("cxy", "savedInstanceState  null" + mChartType);
            return;
        }
        Serializable serializable = savedInstanceState.getSerializable(KEY_DATA);
        if (serializable != null && serializable instanceof IChartDataProvider) {
            mData = (IChartDataProvider) savedInstanceState.getSerializable(KEY_DATA);
            Log.i("cxy", "fragment" + mChartType + "调用onCreate获取到存储信息:  " + mData.getChildCount() + "条数");
        }
    }

}
