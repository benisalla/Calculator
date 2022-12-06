package com.exemple.calculator;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Graphic extends Fragment implements View.OnClickListener {
    private XYPlot xyPlot;
    final private Number[] xVal = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    final private List<Number> yVal = new ArrayList<Number>();
    private XYSeries xySeries;
    private LineAndPointFormatter lineAndPointFormatter;
    private PanZoom panZoom;
    private String xExpression;

    public Graphic() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PixelUtils.init(getContext());
        xExpression = "log(x)";
        yVal.addAll(Arrays.asList(xVal));
        xySeries = new SimpleXYSeries(yVal, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,xExpression);
        lineAndPointFormatter = new LineAndPointFormatter(Color.RED,Color.GREEN,null,null);
        lineAndPointFormatter.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graphic, container, false);
        xyPlot = (XYPlot) rootView.findViewById(R.id.graph_ploter);
        xyPlot.addSeries(xySeries,lineAndPointFormatter);
        xyPlot.setDomainBoundaries(-1000, 1000, BoundaryMode.AUTO);
        xyPlot.setPlotPadding(10,36,10,36);
        xyPlot.setScrollbarFadingEnabled(true);
        xyPlot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);

        //xyPlot.getGraph().setLineLabelEdges(XYGraphWidget.Edge.BOTTOM, XYGraphWidget.Edge.LEFT, XYGraphWidget.Edge.RIGHT);
        xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.RIGHT).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(i);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        //panZoom.attach(xyPlot);
        panZoom.attach(xyPlot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH);
        xyPlot.getOuterLimits().set(-1000, 1000, -1000, 1000);

        Button btn = (Button) rootView.findViewById(R.id.plot_invoker);
        btn.setOnClickListener(this);



        return rootView;
    }

//    @Override
//    public void onSaveInstanceState(Bundle bundle) {
//        bundle.putSerializable("LAST_STATE", panZoom.getState());
//    }
//
//    @Override
//    public void onViewStateRestored(Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        PanZoom.State state = (PanZoom.State) savedInstanceState.getSerializable("LAST_STATE");
//        if(isStateSaved()){
//            panZoom.setState(state);
//            xyPlot.redraw();
//        }
//    }

    //------------------------------------------------------------------------------
    public void PlotClickHandler(){
        PixelUtils.init(getContext());
        xVal[0]=5;xVal[1]=2;xVal[2]=5;xVal[3]=0;xVal[4]=8;xVal[5]=3;xVal[6]=6;xVal[7]=8;
        xExpression = "sin(x)";
        String exp = xExpression;
        yVal.clear();
        double number = 0;

        for(Number num : xVal){
            number = num.doubleValue();
            exp = xExpression.replace("x",number+"");
            yVal.add(Math_Exp_Evaluator.CalculateFromExpression(exp));
        }
        xySeries = new SimpleXYSeries(yVal, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,xExpression);
        lineAndPointFormatter = new LineAndPointFormatter(Color.RED,Color.GREEN,null,null);
        //lineAndPointFormatter.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform));
        xyPlot.addSeries(xySeries,lineAndPointFormatter);

        xyPlot.redraw();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.plot_invoker){
            PlotClickHandler();
        }
    }
}