package com.example.minimetro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Passenger {
    //Station DESTINATION = null;
    int COLOR = Color.WHITE;
    int RADIUS = 7;
    private int m_type = 0;
    /*Passenger(Station d) {
        DESTINATION = d;
    }*/
    Passenger (int d) {
        m_type = d;
    }

    int getType() {
        return m_type;
    }

    void draw(Paint pPaint, Canvas pCanvas, float X, float Y, int i) {
        pPaint.setColor(COLOR);   //(2*i+1)*RADIUS
        switch (getType()) {
            case 0:
                pCanvas.drawCircle(X+RADIUS + (2*(i%2)+1)*RADIUS, Y+RADIUS + (i-(i%2)+1)*RADIUS, RADIUS, pPaint);
                break;
            case 1:
                pCanvas.drawLine(X+RADIUS + (2*(i%2)+1)*RADIUS - RADIUS, Y+RADIUS + (i-(i%2)+1)*RADIUS - RADIUS, X+RADIUS + (2*(i%2)+1)*RADIUS +RADIUS, Y+RADIUS + (i-(i%2)+1)*RADIUS +RADIUS, pPaint);
                break;
            case 2:
                pCanvas.drawLine(X+RADIUS + (2*(i%2)+1)*RADIUS - RADIUS, Y+RADIUS + (i-(i%2)+1)*RADIUS - RADIUS, X+RADIUS + (2*(i%2)+1)*RADIUS +RADIUS, Y+RADIUS + (i-(i%2)+1)*RADIUS +RADIUS, pPaint);
                pCanvas.drawLine(X+RADIUS + (2*(i%2)+1)*RADIUS - RADIUS, Y+RADIUS + (i-(i%2)+1)*RADIUS +RADIUS, X+RADIUS + (2*(i%2)+1)*RADIUS +RADIUS,Y+RADIUS + (i-(i%2)+1)*RADIUS - RADIUS , pPaint);
                break;
        }
    } 
}
