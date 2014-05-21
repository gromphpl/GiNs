package com.CzmokWojczuk.GiNs;

import java.math.BigDecimal;
import java.util.PriorityQueue;

/**
 * Created by Formen on 22.03.14.
 */
public class ODE {
    ODE(){

    }
    int N=100;
    private Double h=0.1;
    private Double x0=0.0;
    private Double M1 =100.0;


    private Double[][] glucoseInsulin = new Double[3] [N];


    private Double detx1=0.0;
    private Double detx2= 0.0;
    //wspolczynniki
    private Double a1=0.05;
    private Double a2=1.0;
    private Double a3=0.5;
    private Double a4=2.0;
    private Double b1=1.01;
    private Double b2= 1.01;
    private Double R1=50.0;
    private Double R2=24.0;
    private Double tauu1 =0.6;
    private Double tauu2 =0.5;
    private Double t0 = 0.5;
    private Double in=0.; //wsp insuliny
    //

    Double u1(double t)
    {
        return R1*Math.exp(-(t-t0)/tauu1);


    }
    Double u2(double t)
    {
        return   in*R2*Math.exp(-(t-t0)/tauu2);


    }
    Double glucose(double t, double x1, double x2){

        if(x1<M1)     return   -a1*x1*x2+a2*(M1-x1)+b1*u1(t);

        else      return -a1*x1*x2+b1*u1(t);


    }

    Double insulin( double t, double x1, double x2){

        if(x1>M1) return a3*(x1-M1)-a4*x2+b2*u2(t) ;

        else return  -a4*x2+b2*u2(t);


    }


    // rungego-kutty
                   public Double[] [] rungeKutyy(){
                       Double k1x1=0.;
                       Double k2x1=0.;
                       Double k1x2=0.;
                       Double k2x2=0.;
                       Double k1x3=0.;
                       Double k2x3=0.;
                       Double k1x4=0.;
                       Double k2x4=0.;

                       glucoseInsulin[0][0]=M1;        //wartosc poczatkowa glukozy
                       glucoseInsulin[1][0]=0.;     //wartosc poczatkowa insuliny
                       glucoseInsulin[2][0]=0.0;    //czas poczatkowy



                       k1x1 = glucose(glucoseInsulin[2][0],glucoseInsulin[0][0], glucoseInsulin[1][0]);
                       k2x1 = insulin(glucoseInsulin[2][0],glucoseInsulin[0][0], glucoseInsulin[1][0]);

                       k1x2=glucose(glucoseInsulin[2][0]+(h/2),glucoseInsulin[0][0]+(1/2*k1x1*h), glucoseInsulin[1][0]+(1/2*k2x1*h));
                       k2x2=insulin(glucoseInsulin[2][0]+(h/2),glucoseInsulin[0][0]+(1/2*k1x1*h), glucoseInsulin[1][0]+(1/2*k2x1*h));

                       k1x3=glucose(glucoseInsulin[2][0]+(h/2),glucoseInsulin[0][0]+(1/2*k1x2*h), glucoseInsulin[1][0]+(1/2*k2x2*h));
                       k2x3=insulin(glucoseInsulin[2][0]+(h/2),glucoseInsulin[0][0]+(1/2*k1x2*h), glucoseInsulin[1][0]+(1/2*k2x2*h));

                       k1x4=glucose((glucoseInsulin[2][0]+h),glucoseInsulin[0][0]+k1x3*h, glucoseInsulin[1][0]+(k2x3*h));
                       k2x4=insulin((glucoseInsulin[2][0]+h),glucoseInsulin[0][0]+k1x3*h, glucoseInsulin[1][0]+(k2x3*h));

                       detx1=((1/6.)*(k1x1+(2*k1x2)+(2*k1x3)+k1x4)*h);
                       detx2=((1/6.)*(k2x1+(2*k2x2)+(2*k2x3)+k2x4)*h);
                       glucoseInsulin[0][1]=glucoseInsulin[0][0]+detx1;
                       glucoseInsulin[1][1]= glucoseInsulin[1][0]+detx2;
                       glucoseInsulin[2][1]=glucoseInsulin[2][0]+h;


                       for(int i=1;i<=(N-2);i++)
                       {

                           Double T1=Math.abs(k1x2-k1x3)/(k1x1-k1x2);
                           Double T2=Math.abs((k2x2-k2x3)/(k2x1-k2x2));
                           if(T1>0.05 || T2 >0.05)          h=h/2;




                           k1x1=glucose(glucoseInsulin[2][i],glucoseInsulin[0][i], glucoseInsulin[1][i]);
                           k2x1=insulin(glucoseInsulin[2][i],glucoseInsulin[0][i], glucoseInsulin[1][i]);

                           k1x2=glucose(glucoseInsulin[2][i]+(h/2),glucoseInsulin[0][i]+(1/2*k1x1*h), glucoseInsulin[1][i]+(1/2*k2x1*h));
                           k2x2=insulin(glucoseInsulin[2][i]+(h/2),glucoseInsulin[0][i]+(1/2*k1x1*h), glucoseInsulin[1][i]+(1/2*k2x1*h));

                           k1x3=glucose(glucoseInsulin[2][i]+(h/2),glucoseInsulin[0][i]+(1/2*k1x1*h), glucoseInsulin[1][i]+(1/2*k2x1*h));
                           k2x3=insulin(glucoseInsulin[2][i]+(h/2),glucoseInsulin[0][i]+(1/2*k1x1*h), glucoseInsulin[1][i]+(1/2*k2x1*h));

                           k1x4=glucose((glucoseInsulin[2][i]+h),glucoseInsulin[0][i]+k1x3*h, glucoseInsulin[1][i]+(k2x3*h));
                           k2x4=insulin((glucoseInsulin[2][i]+h),glucoseInsulin[0][i]+k1x3*h, glucoseInsulin[1][i]+(k2x3*h));

                           detx1=((1/6.)*(k1x1+(2*k1x2)+(2*k1x3)+k1x4)*h);
                           detx2=((1/6.)*(k2x1+(2*k2x2)+(2*k2x3)+k2x4)*h);
                           glucoseInsulin[0][i+1]=glucoseInsulin[0][i]+detx1;
                           glucoseInsulin[1][i+1]=glucoseInsulin[1][i]+detx2;
                           glucoseInsulin[2][i+1]=glucoseInsulin[2][i]+h;

                       }

                       return   glucoseInsulin;
    }

}


