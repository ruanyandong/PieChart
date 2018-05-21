package com.example.ai.piechart;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class PieFragment extends Fragment implements OnChartValueSelectedListener {

    private static String DATA_KEY = "pie_fragment_data_key";

    private MonthBean mData;

    private PieChart mChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            mData = arguments.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pie, null);

        mChart = view.findViewById(R.id.pc_chart);

        initView();
        return view;
    }

    private void initView() {
        setData();
        /**
         * 隐藏底部图例,legend：图例,说明
         */
        //mChart.getLegend().setEnabled(false);

        //设置图例字体大小
        mChart.getLegend().setTextSize(14);

        //设置右下角描述
        Description description = new Description();
        description.setText("每月消费开支比重");
        description.setTextSize(14);
        mChart.setDescription(description);

        //禁止饼图旋转false,true为开启旋转
        mChart.setRotationEnabled(true);

        //设置饼图选中的监听事件
        // 在这里实现选中的部分被置于底部
        mChart.setOnChartValueSelectedListener(this);


    }

    private void setData() {


        List<PieEntry> entrys = new ArrayList<>();

        for (int i = 0; i < mData.obj.size(); i++) {

            MonthBean.PieBean pieBean = mData.obj.get(i);

            entrys.add(new PieEntry(pieBean.value, pieBean.title));

        }

        PieDataSet dataSet = new PieDataSet(entrys, "图例");

        dataSet.setColors(new int[]{
                Color.rgb(216, 77, 219),
                Color.rgb(183, 56, 63),
                Color.rgb(247, 85, 47)
        });

        PieData pieData = new PieData(dataSet);

        pieData.setValueTextSize(22);

        mChart.setData(pieData);

    }

    public static PieFragment newInstance(MonthBean data) {

        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY, data);
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //饼图往外凸的时候调用
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        //在这里实现选中的部分被置于底部
        /**
         * 代码有问题
         */

        //proportion:比例，占比
        float proportion = 360f / mData.getSum();

        float angle = 90 - mData.obj.get((int) e.getX()).value * proportion / 2 - mData.getSum((int) e.getX()) * proportion;

        mChart.setRotationAngle(angle);
    }

    //饼图往里缩的时候调用
    @Override
    public void onNothingSelected() {

    }
}
