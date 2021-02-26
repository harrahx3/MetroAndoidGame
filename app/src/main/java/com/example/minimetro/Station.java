package com.example.minimetro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class Station {

    float RADIUS = 13;
    int COLOR = Color.BLACK;
    private int m_capacity = 5;
    private float m_x;
    private float m_y;
    private ArrayList<Passenger> m_passengers = new ArrayList<>();
    private int m_type = 0;

    Station(float x, float y) {
        m_x = x;
        m_y = y;
    }
    Station(float x, float y, float radius, int type) {
        m_x = x;
        m_y = y;
        RADIUS = radius;
        m_type = type;
    }

    float getX() {
        return m_x;
    }

    void setX(float m_x) {
        this.m_x = m_x;
    }

    float getY() {
        return m_y;
    }

    public void setY(float m_y) {
        this.m_y = m_y;
    }

    List<Passenger> getPassengers() {
        return m_passengers;
    }
    void addPassenger(Passenger newPassenger) {
        m_passengers.add(newPassenger);
    }
    void clearPassengers() {
        m_passengers.clear();
    }
    void removePassenger(Passenger passenger) {
        m_passengers.remove(passenger);
    }
    void removePassenger() {
        m_passengers.remove(0);
    }
    void removePassenger(int index) {
        m_passengers.remove(index);
    }
    boolean isBrimming() {
        return m_passengers.size() > m_capacity;
    }
    void draw(Paint pPaint, Canvas pCanvas) {
        if (isBrimming())
            pPaint.setColor(Color.RED);
        else
            pPaint.setColor(COLOR);
        switch (m_type) {
            case 0 :
                pCanvas.drawCircle(getX(), getY(), RADIUS, pPaint);
                break;
            case 1 :
                pCanvas.drawRect(getX()-RADIUS, getY()-RADIUS, getX()+RADIUS, getY()+RADIUS, pPaint);
                break;
            case 2 :
                pCanvas.drawLine(getX()-RADIUS, getY()-RADIUS, getX()+RADIUS, getY()+RADIUS, pPaint);
                pCanvas.drawLine(getX()-RADIUS, getY()+RADIUS, getX()+RADIUS, getY()-RADIUS, pPaint);
                break;
        }
        if (getPassengers() != null) {
            for (int i=0; i<getPassengers().size(); i++) {
                getPassengers().get(i).draw(pPaint, pCanvas, getX(), getY(), i);
            }
        }
    }

    int getType() {
        return m_type;
    }
}
