package com.samsoft.xpendify.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsoft.xpendify.R;
import com.samsoft.xpendify.graph.hzgrapherlib.animation.GraphAnimation;
import com.samsoft.xpendify.graph.hzgrapherlib.graphview.CircleGraphView;
import com.samsoft.xpendify.graph.hzgrapherlib.vo.GraphNameBox;
import com.samsoft.xpendify.graph.hzgrapherlib.vo.circlegraph.CircleGraph;
import com.samsoft.xpendify.graph.hzgrapherlib.vo.circlegraph.CircleGraphVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred on 05-Jan-16.
 */
public class PieChartFragment extends Fragment {

    private ViewGroup viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pie_chart_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewGroup = (ViewGroup) view.findViewById(R.id.graphView);

    }

    private void setCircleGraph() {

        CircleGraphVO vo = makeLineGraphAllSetting();

        viewGroup.addView(new CircleGraphView(getActivity(), vo));
    }

    private CircleGraphVO makeLineGraphAllSetting() {
        int paddingBottom = CircleGraphVO.DEFAULT_PADDING;
        int paddingTop = CircleGraphVO.DEFAULT_PADDING;
        int paddingLeft = CircleGraphVO.DEFAULT_PADDING;
        int paddingRight = CircleGraphVO.DEFAULT_PADDING;

        int marginTop = CircleGraphVO.DEFAULT_MARGIN_TOP;
        int marginRight = CircleGraphVO.DEFAULT_MARGIN_RIGHT;

        int radius = 130;

        List<CircleGraph> arrGraph = new ArrayList<CircleGraph>();

        arrGraph.add(new CircleGraph("android", Color.parseColor("#3366CC"), 1));
        arrGraph.add(new CircleGraph("ios", Color.parseColor("#DC3912"), 1));
        arrGraph.add(new CircleGraph("Java", Color.parseColor("#FF9900"), 1));
        arrGraph.add(new CircleGraph("HTML", Color.parseColor("#109618"), 1));
        arrGraph.add(new CircleGraph("C", Color.parseColor("#990099"), 3));

        CircleGraphVO vo = new CircleGraphVO(paddingBottom, paddingTop, paddingLeft, paddingRight, marginTop, marginRight, radius, arrGraph);

        vo.setLineColor(Color.WHITE);

        vo.setTextColor(Color.WHITE);
        vo.setTextSize(20);

        vo.setCenterX(0);
        vo.setCenterY(0);

        vo.setAnimation(new GraphAnimation(GraphAnimation.LINEAR_ANIMATION, 2000));

        vo.setPieChart(true);

        GraphNameBox graphNameBox = new GraphNameBox();

        graphNameBox.setNameboxMarginTop(25);
        graphNameBox.setNameboxMarginRight(25);

        vo.setGraphNameBox(graphNameBox);

        return vo;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCircleGraph();
    }
}
