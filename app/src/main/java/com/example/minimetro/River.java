package com.example.minimetro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

class River {
    private ArrayList<int[]> m_points  = new ArrayList<>();

    River (int screenWidth, int screenHeight, int nbPoints) {
        Random r = new Random();
        int po[] = {0, screenHeight/2};
        m_points.add(po);
        for (int i=0; i<nbPoints; i++) {
            int x = r.nextInt(Math.abs(screenWidth/nbPoints))+i*Math.abs(screenWidth)/nbPoints;
            int y = r.nextInt(Math.abs(screenHeight/2))+Math.abs(screenHeight)/4;
            int pos[] = {x,y};
            m_points.add(pos);
        }
        int p[] = {screenWidth, screenHeight/2};
        m_points.add(p);
    }

    void draw (Paint pPaint, Canvas pCanvas) {
        for (int i = 0; i<m_points.size()-1; i++){
            pPaint.setColor(Color.CYAN);
            pCanvas.drawLine(m_points.get(i)[0], m_points.get(i)[1], m_points.get(i+1)[0], m_points.get(i+1)[1], pPaint);
        }
    }

    boolean isCrossing(float x1, float y1, float x2, float y2) {
        if (x2 != x1 && y2 != y1) {

            float a1 = (y2-y1)/(x2-x1);
           // float b1 = a1*x1;
            float b1 = y1 - a1*x1;
            Log.e("crossing ?", "");

            for (int i = 0; i<m_points.size()-1; i++){

                int xr1 = m_points.get(i)[0];
                int yr1 = m_points.get(i)[1];
                int xr2 = m_points.get(i+1)[0];
                int yr2 = m_points.get(i+1)[1];

                if (xr2 != xr1 && yr2 != yr1) {

                    float a2 = (yr2-yr1)/(xr2-xr1);
                    //float b2 = a2*xr1;
                    float b2 = yr1 - a2*xr1;

                    float yCross = (a1*b2-a2*b1)/(a1-a2);
                    float xCross = (yCross-b1)/a1;

                  /*  Log.e("x1", String.valueOf(x1));
                    Log.e("y1", String.valueOf(y1));
                    Log.e("x2", String.valueOf(x2));
                    Log.e("y2", String.valueOf(y2));
                    Log.e("xr1", String.valueOf(xr1));
                    Log.e("yr1", String.valueOf(yr1));
                    Log.e("xr2", String.valueOf(xr2));
                    Log.e("yr2", String.valueOf(yr2));

                    Log.e("xCross", String.valueOf(xCross));
                    Log.e("yCross", String.valueOf(yCross));
                    */

                    //if (xCross > min(x1,x2) && xCross > min(xr1,xr2) && xCross < max(x1,x2) && xCross < max(xr1,xr2) && yCross > min(y1,y2) && yCross > min(yr1,yr2) && yCross < max(y1,y2) && yCross < max(yr1,yr2) ) {
                    if (xCross > min(x1,x2) && xCross > min(xr1,xr2) && xCross < max(x1,x2) && xCross < max(xr1,xr2) ) {

                        Log.e("crossing !!!", "cross !!!! ");

                        return true;
                    }

                }
            }

        }
        return false;
    }


    boolean isCrossing(Station station, Station s2) {
        Log.e("crossing station ?", "c");
        return isCrossing(station.getX(),station.getY(), s2.getX(), s2.getY());
    }
}
