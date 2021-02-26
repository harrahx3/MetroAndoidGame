package com.example.minimetro;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Train {

    int WIDTH = 15;
    int HEIGHT = 25;
    int SENS = 1;
    int SPEED = 4;
    long WAITTIME = 500;
    private float m_x;
    private float m_y;
    private Station m_nextStation = null;
    private ArrayList<Passenger> m_passengers = new ArrayList<>();
    private int m_carriages = 0;
    private int m_locomotiveCapacity = 4;
    private int m_carriageCapacity = 4;
    private boolean m_waiting = false;

    Train(float x, float y, Station nextStation, int sens) {
        m_x = x;
        m_y = y;
        m_nextStation = nextStation;
        SENS = sens;
    }
    Train(float x, float y, Station nextStation) {
        m_x = x;
        m_y = y;
        m_nextStation = nextStation;
    }
    void addCarriage() {
        m_carriages ++;
    }
    int getCarriages (){
        return m_carriages;
    }

    float getX() {
        return m_x;
    }

    public void setX(float m_x) {
        this.m_x = m_x;
    }

    float getY() {
        return m_y;
    }
    void setY(float m_y) {
        this.m_y = m_y;
    }
    void setPosition(float x, float y) {
        m_x = x;
        m_y = y;
    }
    Station getNextStation() {
        return m_nextStation;
    }
    void setNextStation(Station newNextStation) {
        m_nextStation = newNextStation;
    }
    List<Passenger> getPassengers () {
        return m_passengers;
    }

    void setWaiting (boolean newWait){
        m_waiting = newWait;
    }
    void move() {
        //Log.d("Étiquette", "move");
        if (!m_waiting){
            float X = m_nextStation.getX() - m_x;
            float Y = m_nextStation.getY() - m_y;
            float k = (float) Math.sqrt(X*X+Y*Y);
            if (k!=0) {
               // Log.d("Étiquette", "moving : x= "+m_x+" y="+m_y+" k="+k+" X="+X+" Y="+Y+" sens="+SENS);
                m_x += X*SPEED/k;
                m_y += Y*SPEED/k;
            }
        }
    }

    void outPassengers() {
        if (m_passengers != null) {
            ArrayList<Passenger> toRemove = new ArrayList<>();
            for (Passenger p : m_passengers) {
                if (p.getType() == m_nextStation.getType()) {
                    toRemove.add(p);
                }
            }
            m_passengers.removeAll(toRemove);
        }
    }


    void inPassengers() {
        while (m_passengers.size() < m_locomotiveCapacity+m_carriages*m_carriageCapacity && m_nextStation.getPassengers().size() > 0) {
            //Log.e("in", ""+m_locomotiveCapacity+m_carriages*m_carriageCapacity);
            m_passengers.add(m_nextStation.getPassengers().get(0));
            m_nextStation.removePassenger(0);
        }
    }
    void draw(Paint pPaint, Canvas pCanvas) {
        for (int i=0; i<m_carriages+1; i++)
            pCanvas.drawRect(getX() - 2*i*WIDTH, getY()-WIDTH, getX()+HEIGHT - 2*i*WIDTH, getY()+WIDTH, pPaint);
        if (getPassengers() != null) {
            for (int i=0; i<getPassengers().size(); i++) {
                getPassengers().get(i).draw(pPaint, pCanvas, getX(), getY(), i);
            }
        }
    }
}
