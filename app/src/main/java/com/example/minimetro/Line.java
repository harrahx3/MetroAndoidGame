package com.example.minimetro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Line {

    static int [] linesColor = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.MAGENTA};

    float TAILSRADIUS = 8;
    float WIDTH = 4;
    int COLOR = Color.RED;
    private ArrayList<Station> m_stations  = new ArrayList<>();
    private ArrayList<Train> m_trains = new ArrayList<>();
    float[][] m_tails = {{0,0},{0,0}};

    Line(ArrayList<Station> stations) {
        m_stations = stations;
        updateTail();
    }
    Line(Station station, int color) {
        m_stations.add(station);
        COLOR = color;
        updateTail();
    }

    void updateTail() {
        m_tails[0][0] = m_stations.get(0).getX()+3*TAILSRADIUS;
        m_tails[0][1] = m_stations.get(0).getY()-3*TAILSRADIUS;
        m_tails[1][0] = m_stations.get(m_stations.size() - 1).getX()+3*TAILSRADIUS;
        m_tails[1][1] =  m_stations.get(m_stations.size() - 1).getY()-3*TAILSRADIUS;
    }

    void addStation(Station newStation) {
        addStation(newStation, true);
    }
    void addStation(Station newStation, boolean end) {
        if (end)
            m_stations.add(newStation);
        else
            m_stations.add(0, newStation);
        updateTail();
    }
    List<Station> getStations() {return m_stations;}
    boolean canRemove(Station station){
        boolean canRemove = true;
        for (Train train : m_trains) {
            if (train.getNextStation() == station) {
                canRemove = false;
            }
        }
        return canRemove;
    }
    void removeStation(Station station) {
        if (this.canRemove(station)) {
            m_stations.remove(station);
        }
    }
    boolean canRemoveStation(int i) {
        return canRemove(m_stations.get(i));
    }
    void removeStation(int i) {
        removeStation(m_stations.get(i));
    }

    List<Train> getTrains() {
        return m_trains;
    }
    void addTrain() {
        addTrain(0,1);
    }

    void addTrain(Station station, int sens) {
        addTrain(m_stations.indexOf(station),sens);
    }
    void addTrain (int i, int sens) {
        if (i+sens >= m_stations.size() || i+sens <= 0)
            sens *= -1;
        m_trains.add(new Train(m_stations.get(i).getX(), m_stations.get(i).getY(), m_stations.get(i+sens), sens));
    }

    void addTrain(Station station){
        addTrain(station, 1);
    }

    void updateTrains() {
        //Log.d("Étiquette", "update");
        for (final Train t : m_trains) {
            t.move();
            float X = t.getX() - t.getNextStation().getX();
            float Y = t.getY() - t.getNextStation().getY();
            if (Math.sqrt(X*X+Y*Y) < t.SPEED) {
                t.outPassengers();
                t.setWaiting(true);
                Timer timer = new Timer();
                TimerTask go = new TimerTask() {
                    @Override
                    public void run() {
                        t.setWaiting(false);
                    }
                };
                timer.schedule(go, t.WAITTIME);

                t.inPassengers();
                int i = 0;
                while (m_stations.get(i) != t.getNextStation()) {
                    i++;
                }
                if (i == m_stations.size()-1){
                    t.SENS = -1;
                } else if ( i == 0) {
                    t.SENS = 1;
                }
                t.setNextStation(m_stations.get(i+t.SENS));
            }
        }
    }
    
    void draw (Paint pPaint, Canvas pCanvas) {
        updateTrains();
        pPaint.setColor(COLOR);
        for (int i=0; i<m_stations.size()-1; i++) {
            //float X = m_stations.get(i+1).getX() - m_stations.get(i).getX();
            //float Y = m_stations.get(i+1).getY() - m_stations.get(i).getY();
            // pCanvas.drawRect(m_stations.get(i).getX(), m_stations.get(i).getY()-2, m_stations.get(i+1).getX(), m_stations.get(i+1).getY()+2, pPaint);
            //pCanvas.rotate((float)Math.atan(Y/X));
            pCanvas.drawLine(m_stations.get(i).getX(), m_stations.get(i).getY(), m_stations.get(i+1).getX(), m_stations.get(i+1).getY(), pPaint);
        }
        pCanvas.drawCircle(m_tails[0][0], m_tails[0][1], TAILSRADIUS, pPaint);
        pCanvas.drawCircle(m_tails[1][0], m_tails[1][1], TAILSRADIUS, pPaint);
        pCanvas.drawLine(m_stations.get(0).getX(),m_stations.get(0).getY(), m_tails[0][0], m_tails[0][1], pPaint);
        pCanvas.drawLine(m_stations.get(m_stations.size()-1).getX(),m_stations.get(m_stations.size()-1).getY(), m_tails[1][0], m_tails[1][1], pPaint);

        if (getTrains() != null) {
            for (Train t : getTrains()) {
                pPaint.setColor(COLOR);
                t.draw(pPaint, pCanvas);
            }
        }
    }
}
