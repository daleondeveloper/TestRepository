package org.daleon.Test1;


import org.daleon.framework.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MyMathTest {

    //find matrix solutin
    public static List<Double> matrixFind(double[][] mainMat,double[] seconMat){
        double [][] mainMatOri = new double[mainMat.length][mainMat[0].length];
        for(int i = 0; i < mainMatOri.length; i ++){
            mainMatOri[i] = mainMat[i].clone();
        }
        double[] returnMat = new double[mainMat.length];
        double delta = 0;
        for(int j = 0; j < mainMat.length+1; j++){
            for(int i = 0; i < mainMatOri.length; i++){
                mainMat[i] = mainMatOri[i].clone();
            }
            for(int m = 0; m < seconMat.length; m ++){
               if(j>0)
                mainMat[j-1][m] = seconMat[m];
            }
            double timeDelta = 1;
            double sumTimeDelta = 0;
            double secondTimeDelta = 1;
            double secondSumTimeDelta = 0;
                for(int m = 0; m < mainMat.length; m++){
                    timeDelta = 1;
                    secondTimeDelta = 1;
                    for(int n = 0; n < mainMat.length; n++){
                        int k = n+m;
                        if(k >= mainMat.length){
                            k = k - (mainMat.length);
                        }
                        timeDelta *= mainMat[n][k];
                        secondTimeDelta *= mainMat[n][(mainMat[0].length-1) - k];
                    }
                    sumTimeDelta += timeDelta;
                    secondSumTimeDelta += secondTimeDelta;
                }
                if(j == 0){
                    delta = sumTimeDelta - secondSumTimeDelta;
                }else{
                    returnMat[j-1] = sumTimeDelta - secondSumTimeDelta;
                }

            }
        List<Double> list = new ArrayList<>();
        for(int i = 0; i < returnMat.length; i++){
            returnMat[i] = returnMat[i]/delta;
            list.add(returnMat[i]);
        }

        return list;
    }

    //find Y cordinate in 0.05 X step by square method
    public static List<float[]> metodKvadrativ(Point[] points){
        List<float[]> returnList = new ArrayList();
        double[] x = new double[7];
        double[] y = new double[4];
        x[0] = points.length + 1;
        //take sum of all x
        for(int j = 1; j < x.length; j++) {
            for (int i = 0; i < points.length; i++) {

                x[j] += Math.pow(points[i].position.x,j);
            }
        }
        //take matrix
        double[][] matX = new double[4][4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                if(j<4) {
                    matX[i][j] = x[(j + i)];
                }
                if(i == 0){
                    y[i] += points[j].position.y;
                }else{
                    y[i] += points[j].position.y * Math.pow(points[j].position.x,i);
                }
            }
        }
        // find matrix solution
        List<Double> list = MyMathTest.matrixFind(matX,y);
        double[] cordinat = new double[2];
        //find b1,b2...bn;
        for(double i = 0; i < 6; i += 0.05 ){
            cordinat[0] = i;
            cordinat[1] = list.get(0);
            for(int j = 1; j < list.size(); j++){
                cordinat[1] += list.get(j) * Math.pow(cordinat[0],j);
            }
            //draw graf
            float[] f = new float[2];
            f[0] = (float)cordinat[0];
            f[1] = (float)cordinat[1];
            returnList.add(f);
        }
        return returnList;
    }

    //find Y cordinate by 0.01 X step by lagrant method
    public static List<float[]> buildLagran(Point[] points){
        List<float[]>list = new ArrayList<>();

        for(float i =0;  i < points.length+1; i += 0.01){
            float[] d = new float[2];

            d[0] = i;
        d[1] = helpLagrang(i,points);

        list.add(d);
        }
        return list;
    }

    //find Y cordinate by X cordinate method Lagrant
    public static float helpLagrang(float x,Point[] points){
        float ljx ;
        float Lx = 0;
        for(int i = 0; i < points.length; i++) {
            ljx = 0;
            for (int j = 0; j < points.length; j++) {
                if(j != i ) {
                    if (ljx != 0) {
                        ljx *= (x - points[j].position.x) / (points[i].position.x - points[j].position.x);

                    } else {
                        ljx = (x - points[j].position.x) / (points[i].position.x - points[j].position.x);
                    }
                }

            }
            ljx *= points[i].position.y;
            Lx += ljx;
        }
        return Lx;
    }

    //find the angle between the given vector and 0 degree vector
    public static float takeAngelRectangles(float x1, float y1,float x2, float y2){

        Vector2 v1 = new Vector2(x2-x1,y2-y1);
        float v1d = (float)Math.sqrt(v1.x*v1.x+v1.y*v1.y);
        Vector2 v2 = new Vector2(x1+0.5f,y1-y1);
        float v2d = (float)Math.sqrt(v2.x*v2.x+v2.y*v2.y);
        float cosAngle = (v1.x*v2.x+v1.y*v2.y)/(v1d*v2d);
        float angle = (float)Math.toDegrees(Math.acos(cosAngle));
        if(y1 > y2){
            angle = 360 - angle;
        }
        return angle;
    }
}
