package com.exemple.calculator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.NormedXYSeries;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.ZoomEstimator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Graphic extends Fragment implements View.OnClickListener {
    private XYPlot xyPlot;
    private List<Number> xVal = new ArrayList<Number>();
    private List<Number> yVal = new ArrayList<Number>();
    private XYSeries xySeries;
    private LineAndPointFormatter lineAndPointFormatter;
    private PanZoom panZoom;
    private String xExpression;
    EditText newFunctionExp;
    public Graphic() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PixelUtils.init(getContext());
        xExpression = "sin(x)";
        xVal = Generate_xVal(-200,200);
        for(Number num : xVal){
            yVal.add(Math_Exp_Evaluator.CalculateFromExpression(xExpression.replace("x",num.toString())));
        }
        xySeries = new SimpleXYSeries(xVal, yVal,xExpression);
        lineAndPointFormatter = new LineAndPointFormatter(Color.RED,Color.GREEN,null,null);
        lineAndPointFormatter.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graphic, container, false);
        xyPlot = (XYPlot) rootView.findViewById(R.id.graph_ploter);
        newFunctionExp = (EditText) rootView.findViewById(R.id.NewFunction);
        xyPlot.addSeries(xySeries, lineAndPointFormatter);
        //------------------------[call styler]-----------------------//
        GraphStyler();
        //------------------------------------------------------------//
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
        xExpression = newFunctionExp.getText().toString().trim();
        if(xExpression.contains("log") || xExpression.contains("ln")){
            xVal = Generate_xVal(1,100);
        }
        else if(xExpression.contains("exp")){
            Toast.makeText(getContext(), "exp exist", Toast.LENGTH_SHORT).show();
            xVal = Generate_xVal(-10,6);
        }else {
            xVal = Generate_xVal(-200, 200);
        }

        String graphName = xExpression;
        xExpression = xExpression.replace("ex","vv");
        xExpression = xExpression.replace("x","k");
        xExpression = xExpression.replace("vv","ex");

        yVal.clear();
        for(Number num : xVal){
            yVal.add(Math_Exp_Evaluator.CalculateFromExpression(xExpression.replace("k",num.toString())));
        }
        Toast.makeText(getContext(), "finished calculating", Toast.LENGTH_SHORT).show();
        xyPlot.removeSeries(xySeries);
        xyPlot.clear();
        xySeries = new SimpleXYSeries(xVal,yVal,graphName);
        lineAndPointFormatter = new LineAndPointFormatter(Color.RED,Color.GREEN,null,null);
        lineAndPointFormatter.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform));
        xyPlot.addSeries(xySeries,lineAndPointFormatter);
        xyPlot.redraw();
        GraphStyler();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.plot_invoker){
            PlotClickHandler();
        }
    }
    //--------------------------------------------------------------------
    public List<Number> Generate_xVal(int minVal, int maxVal){
        List<Number> Xes = new ArrayList<Number>();
        for(int i=minVal; i<maxVal; i+=1){ Xes.add(i);}
        return Xes;
    }
    //--------------------------------------------------------------------
    public void GraphStyler(){
        //===> setting the x and y steps and their boundaries.
//        xyPlot.setDomainBoundaries(xyPlot.getBounds().getMinY(),xyPlot.getBounds().getMaxY(), BoundaryMode.FIXED);
        xyPlot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);
//        xyPlot.setRangeBoundaries(xyPlot.getBounds().getMinX(),xyPlot.getBounds().getMaxX(),BoundaryMode.FIXED);
        xyPlot.setRangeStep(StepMode.INCREMENT_BY_VAL,1);
        //===> setting the graph padding.
        xyPlot.setPlotPadding(0,-24,0,40);

        xyPlot.setScrollbarFadingEnabled(true);
        //===> styling the labels of the graph
        xyPlot.getGraph().setLineLabelEdges(XYGraphWidget.Edge.BOTTOM, XYGraphWidget.Edge.LEFT);
        xyPlot.setLinesPerRangeLabel(1);
        xyPlot.setLinesPerDomainLabel(1);
        //===> last state of the graph could be saved now.
        xyPlot.setSaveEnabled(true);
        xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).getPaint().setTextSize(30);
        xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).getPaint().setColor(Color.rgb(0,255,255));
        xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).getPaint().setTextSize(30);
        xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).getPaint().setColor(Color.rgb(0,255,255));
        xyPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.RIGHT).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                return toAppendTo.append(Math.round(((Number) obj).floatValue()));
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) { return null; }
        });

        //===> enable zooming and panning
        //panZoom.attach(xyPlot);
        panZoom.attach(xyPlot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH);

        //===> estimating zoom calculations
        xyPlot.getRegistry().setEstimator(new ZoomEstimator());

        //===> outer limits of the graph
        xyPlot.getOuterLimits().set(-1000, 1000, -1000, 1000);

        xyPlot.setDomainBoundaries(-200,200,BoundaryMode.FIXED);
        xyPlot.setRangeBoundaries(-200,200,BoundaryMode.FIXED);

        //===> centring the origin of the graph
        xyPlot.centerOnDomainOrigin(0);
        xyPlot.centerOnRangeOrigin(0);
//        xyPlot.setDomainStepValue(1);
//        xyPlot.setRangeStepValue(1);

        //===> set color of x-axis and y-axis to rgb(0,255,255)
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0,255,255));
        xyPlot.getGraph().setDomainOriginLinePaint(paint);
        xyPlot.getGraph().setRangeOriginLinePaint(paint);

        xyPlot.getGraph().setLineLabelRenderer(XYGraphWidget.Edge.BOTTOM, new CustomLineLabelRenderer());
        xyPlot.getGraph().setLineLabelRenderer(XYGraphWidget.Edge.LEFT, new CustomLineLabelRenderer());
    }
}


class CustomLineLabelRenderer extends XYGraphWidget.LineLabelRenderer {
    @Override
    protected void drawLabel(Canvas canvas, String text, Paint paint,
                             float x, float y, boolean isOrigin) {
        if(isOrigin) {
            final Paint originPaint = new Paint(paint);
            originPaint.setColor(Color.RED);
            super.drawLabel(canvas, text, originPaint, x, y , isOrigin);
        } else {
            super.drawLabel(canvas, text, paint, x, y , isOrigin);
        }
    }
}