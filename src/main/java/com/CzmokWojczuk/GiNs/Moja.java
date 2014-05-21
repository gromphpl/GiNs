package com.CzmokWojczuk.GiNs;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import com.androidplot.Plot;
import com.androidplot.series.XYSeries;
import com.androidplot.ui.widget.DomainLabelWidget;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

import java.lang.reflect.Array;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Formen on 2014-05-20.
 */
public class Moja extends Activity {
    private XYPlot glucosePlot;
    private XYPlot insulinePlot;

    ODE licz = new ODE();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bla);
        glucosePlot = (XYPlot) findViewById(R.id.glucose);
        insulinePlot = (XYPlot) findViewById(R.id.insuline);
        Double glucoseInsulin [][] =  licz.rungeKutyy();
        Double glucose[] = new Double[glucoseInsulin[0].length];
        Double insulin[] = new Double[glucoseInsulin[1].length];
        Double time[] = new Double[glucoseInsulin[2].length];
        double max=0.;
        double max2=0.;
        for (int i=0; i<=glucoseInsulin[0].length-1;i++){
            if(glucoseInsulin[0][i]>max) max=glucoseInsulin[0][i];
            if(glucoseInsulin[1][i]>max2) max2=glucoseInsulin[1][i];
            glucose[i]=(glucoseInsulin[0][i]);
             insulin[i]=glucoseInsulin[1][i] ;
             time[i]=glucoseInsulin[2][i];
        }




        //tworzymy serie
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(time),
                Arrays.asList(glucose),
                "glucose");
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(time),
                Arrays.asList(insulin),"insuline");
        //kolor tla siatki wykresu (czyli pole miedzy osia X a Y
        glucosePlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.BLACK);

        insulinePlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.BLACK);
        //mozemy takze ustawic przezroczystosc na tym polu
        glucosePlot.getGraphWidget().getGridBackgroundPaint().setAlpha(0);
        insulinePlot.getGraphWidget().getGridBackgroundPaint().setAlpha(0);
        //tlo wykresu i przelegajacych etykiet (opis osi)
        glucosePlot.getGraphWidget().getBackgroundPaint().setAlpha(0);
        insulinePlot.getGraphWidget().getBackgroundPaint().setAlpha(0);
        //ustawiam przezroczystosc tla formy.
        //jest obszar poza wykresem i polami opisu osi
        glucosePlot.getBackgroundPaint().setAlpha(0);
        insulinePlot.getBackgroundPaint().setAlpha(0);
        //kolor osi X
        glucosePlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        insulinePlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        //kolor osi Y
        glucosePlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        insulinePlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        //ustawiam polozenie legendy
        //mySimpleXYPlot.getLegendWidget().setSize(new SizeMetrics(-20, SizeLayoutType.ABSOLUTE, 160, SizeLayoutType.ABSOLUTE));
        //choc jednak decyduje sie aby ja usunac
        glucosePlot.getLayoutManager().remove(glucosePlot.getLegendWidget());
        insulinePlot.getLayoutManager().remove(insulinePlot.getLegendWidget());
        //odsuniecie napisow od wykresu
        glucosePlot.getGraphWidget().setPadding(5, 5, 5, 5);
        insulinePlot.getGraphWidget().setPadding(5, 5, 5, 5);
        //Margines od brzegow zewnetrznych
        glucosePlot.setPlotMargins(10, 10, 10, 10);
        glucosePlot.setPlotPadding(10, 10, 10, 10);
        insulinePlot.setPlotMargins(10, 10, 10, 10);
        insulinePlot.setPlotPadding(10, 10, 10, 10);
        //typ obramowanie calej formy
        glucosePlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        insulinePlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        //kolor powyzszej
        glucosePlot.getBorderPaint().setColor(Color.BLACK);
        insulinePlot.getBorderPaint().setColor(Color.BLACK);
        //antyaliassing obramowania
        glucosePlot.getBorderPaint().setAntiAlias(true);
        insulinePlot.getBorderPaint().setAntiAlias(true);

        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.RED, Shader.TileMode.MIRROR));

        Paint lineFill2 = new Paint();
        lineFill2.setAlpha(200);
        lineFill2.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.CYAN, Shader.TileMode.MIRROR));

        LineAndPointFormatter formatter = new LineAndPointFormatter(Color.rgb(0, 0,0), null, Color.RED);
        formatter.setFillPaint(lineFill);

        LineAndPointFormatter formatter2 = new LineAndPointFormatter(Color.rgb(0, 0,0), null, Color.CYAN);
        formatter2.setFillPaint(lineFill2);

        //dodajemy nasza serie + format linii
        glucosePlot.addSeries(series1, formatter);
        insulinePlot.addSeries(series2, formatter2);
        //okresla skok/krok skali domeny, w tym przypadku nastepuje co rok
        glucosePlot.setDomainStep(XYStepMode.SUBDIVIDE, 6);     // okresla co ile ma pokazywac wykres
        insulinePlot.setDomainStep(XYStepMode.SUBDIVIDE, 6);
        // opisy osi

        glucosePlot.setDomainLabel("t[s]");
        glucosePlot.setRangeLabel("glukoza [mg/dl]");
        insulinePlot.setDomainLabel("t[s]");
        insulinePlot.setRangeLabel("insulina [mU/ml]");


        //precyzja zakresu, w tym przypadku - liczby calkowite      
        glucosePlot.setRangeValueFormat(new DecimalFormat("#"));
        glucosePlot.setDomainValueFormat(new DecimalFormat("#"));
        glucosePlot.setRangeBottomMax(90);

        insulinePlot.setRangeValueFormat(new DecimalFormat("#"));
        insulinePlot.setDomainValueFormat(new DecimalFormat("#"));
        insulinePlot.setRangeBottomMax(1);
        //wlacz a przekonasz sie ;)
        glucosePlot.disableAllMarkup();
        insulinePlot.disableAllMarkup();

    }
}