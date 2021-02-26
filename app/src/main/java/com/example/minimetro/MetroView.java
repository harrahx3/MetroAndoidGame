package com.example.minimetro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class MetroView extends SurfaceView implements SurfaceHolder.Callback {

    final SurfaceHolder mSurfaceHolder;
    DrawingThread mThread;

    private ArrayList<Station> m_stations = new ArrayList<>();
    private int nbTypes = 3;
    private ArrayList<River> m_rivers = new ArrayList<>();

    public ArrayList<Station> getStations() {
        return m_stations;
    }
    public void addStation(Station newStation) {
        m_stations.add(newStation);
    }
    public void addStation() {
        Random r = new Random();
        float radius = 15;
        float x;
        float y;
        int type = r.nextInt(nbTypes);
        boolean cantBuild;
        do {
            cantBuild = false;
            x = 2*radius + r.nextInt(Math.abs((int)(getWidth()-2*radius - 2*radius)));
            y = 2*radius + r.nextInt(Math.abs((int)(getHeight()-2*radius - 2*radius)));
            for (Station s : m_stations) {
                float X = x - s.getX();
                float Y = y - s.getY();
                if (Math.sqrt(X*X+Y*Y) < 2*radius + 2*s.RADIUS) {
                    cantBuild = true;
                }
            }
        } while (cantBuild);

        addStation(new Station(x, y, radius, type));
    }
    private ArrayList<Line> m_lines = new ArrayList<>();
    public void addLine(Line newLine) {
        m_lines.add(newLine);
    }
    public void addPassenger() {
        if (getStations().size() > 1) {
            Random r = new Random();
            Station depart = getStations().get(r.nextInt(getStations().size()-1));
            //Station destination;
            int type;
            do {
                type = r.nextInt(nbTypes);
                //destination = getStations().get(r.nextInt(getStations().size()-1));
            }while (depart.getType() == type);
            depart.addPassenger(new Passenger(type));
        }
    }
    public void addRiver() {
        m_rivers.add(new River(getWidth(), getHeight(),1));
    }

    Paint mPaint;

    public MetroView(Context pContext) {
        super(pContext);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        // Dessiner le fond de l'écran en premier
        pCanvas.drawColor(Color.GRAY);

        if (m_rivers != null) {
            for (River river : m_rivers) {
                river.draw(mPaint, pCanvas);
            }
        }

        if(m_stations != null) {
            for(Station s : m_stations) {
                s.draw(mPaint, pCanvas);
                /*mPaint.setColor(s.COLOR);
                pCanvas.drawCircle(s.getX(), s.getY(), s.RADIUS, mPaint);
                if (s.getPassengers() != null) {
                    for (int i=0; i<s.getPassengers().size(); i++) {
                        Passenger p = s.getPassengers().get(i);
                        mPaint.setColor(p.COLOR);
                        pCanvas.drawCircle(s.getX()+s.RADIUS + (2*i+1)*p.RADIUS, s.getY()+s.RADIUS + (2*i+1)*p.RADIUS, p.RADIUS, mPaint);
                    }
                }*/
            }
        }
        if(m_lines != null) {
            for (Line l : m_lines) {
                if (l != cur_line) {
                    l.draw(mPaint, pCanvas);
                }
                /*l.updateTrains();
                mPaint.setColor(l.COLOR);
                for (int i=0; i<l.getStations().size()-1; i++) {
                    //float X = l.getStations().get(i+1).getX() - l.getStations().get(i).getX();
                    //float Y = l.getStations().get(i+1).getY() - l.getStations().get(i).getY();
                   /* float k = (float) Math.sqrt(X*X+Y*Y);
                    if (k!=0) {
                        //Log.d("Étiquette", "moving : x= "+m_x+" y="+m_y+" k="+k+" X="+X+" Y="+Y);
                        float left = l.getStations().get(i).getX() + l.WIDTH/2 * Y/k;
                        float top = l.getStations().get(i).getY() - l.WIDTH/2 * X/k;
                        float right = l.getStations().get(i+1).getX() - l.WIDTH/2 * Y/k;
                        float bot = l.getStations().get(i+1).getY() + l.WIDTH/2 * X/k;
                        pCanvas.drawRect(left, top, right, bot, mPaint);
                    }
                   // pCanvas.drawRect(l.getStations().get(i).getX(), l.getStations().get(i).getY()-2, l.getStations().get(i+1).getX(), l.getStations().get(i+1).getY()+2, mPaint);
                    //pCanvas.rotate((float)Math.atan(Y/X));
                    pCanvas.drawLine(l.getStations().get(i).getX(), l.getStations().get(i).getY(), l.getStations().get(i+1).getX(), l.getStations().get(i+1).getY(), mPaint);
                }
                pCanvas.drawCircle(l.m_tails[0][0], l.m_tails[0][1], l.TAILSRADIUS, mPaint);
                pCanvas.drawCircle(l.m_tails[1][0], l.m_tails[1][1], l.TAILSRADIUS, mPaint);
                pCanvas.drawLine(l.getStations().get(0).getX(),l.getStations().get(0).getY(), l.m_tails[0][0], l.m_tails[0][1], mPaint);
                pCanvas.drawLine(l.getStations().get(l.getStations().size()-1).getX(),l.getStations().get(l.getStations().size()-1).getY(), l.m_tails[1][0], l.m_tails[1][1], mPaint);

                if (l.getTrains() != null) {
                    for (Train t : l.getTrains()) {
                        mPaint.setColor(l.COLOR);
                        t.draw(mPaint, pCanvas);
                        }
                    }
                }*/
            }
        }
        if (cur_line != null) {
            cur_line.draw(mPaint, pCanvas);
        }
        mPaint.setColor(0x77000000);
        pCanvas.drawCircle(newElements[0][0], newElements[0][1], 25, mPaint);
        pCanvas.drawCircle(newElements[1][0], newElements[1][1], 25, mPaint);
        pCanvas.drawCircle(newElements[3][0], newElements[3][1], 25, mPaint);
        pCanvas.drawCircle(newElements[2][0], newElements[2][1], 25, mPaint);
        mPaint.setColor(0x77770000);
        mPaint.setTextSize(15);
        pCanvas.drawText(""+buildable[0], newElements[0][0], newElements[0][1], mPaint);
        pCanvas.drawText(""+buildable[1], newElements[1][0], newElements[1][1], mPaint);
        pCanvas.drawText(""+buildable[3], newElements[3][0], newElements[3][1], mPaint);
        pCanvas.drawText(""+buildable[2], newElements[2][0], newElements[2][1], mPaint);

        if (cur_train != null) {
            cur_train.draw(mPaint, pCanvas);
            //pCanvas.drawRect(cur_train.getX(), cur_train.getY(), cur_train.getX()+cur_train.WIDTH, cur_train.getY()+cur_train.HEIGHT, mPaint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight) {
        //
    }

    @Override
    public void surfaceCreated(SurfaceHolder pHolder) {
        mThread.keepDrawing = true;
        mThread.start();
        // Quand on crée la boule, on lui indique les coordonnées de l'écran
       /* if(mBoule != null ) {
            this.mBoule.setHeight(getHeight());
            this.mBoule.setWidth(getWidth());
        }*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder pHolder) {
        mThread.keepDrawing = false;
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }

    Line cur_line = null;
    int cur_j = -1;
    int newElements[][] = {{30,100},{30,150},{30,300},{30,250}};
    int buildable[]= {2,0,1,0}; //trains, carriages, lines, tunels

    Train cur_train = null;
    enum State {NOTHING, NEWTRAIN, NEWCARRIAGE, NEWLINE, PROLONGLINE}
    State state = State.NOTHING;

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        Log.d("touch", "event");
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();
        float X;
        float Y;
        performClick();
        
        switch (event.getAction()) {
            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN :
                Log.e("touch", "x="+currentX+" y="+currentY+" i="+cur_line+" j="+cur_j);
                X = newElements [0][0] - currentX;
                Y = newElements[0][1] - currentY;
                if (Math.sqrt(X*X+Y*Y) < 30 && buildable[0]>0) {
                    state = State.NEWTRAIN;
                    cur_train = new Train(newElements[0][0], newElements[0][1], null, 1);
                } else {
                    X = newElements [1][0] - currentX;
                    Y = newElements[1][1] - currentY;
                    if (Math.sqrt(X*X+Y*Y) < 30 && buildable[1]>0) {
                        state = State.NEWCARRIAGE;
                        cur_train = new Train(newElements[1][0], newElements[1][1], null, 1);
                    } else {
                        for (Line l : m_lines) {
                            for (int j=0; j<l.m_tails.length; j++) {
                                X = l.m_tails[j][0] - currentX;
                                Y = l.m_tails[j][1] - currentY;
                                if (Math.sqrt(X*X+Y*Y) < 2*l.TAILSRADIUS) {
                                    cur_j = j;
                                    cur_line = l;
                                    state = State.PROLONGLINE;
                                }
                            }
                        }
                        if (buildable[2]>0) {
                            for (Station s : m_stations) {
                                X = s.getX() - currentX;
                                Y = s.getY() - currentY;
                                if (Math.sqrt(X*X+Y*Y) < s.RADIUS) {
                                    cur_line = new Line(s, Line.linesColor[m_lines.size() % Line.linesColor.length]);
                                    cur_j = 0;
                                    state = State.NEWLINE;
                                }
                            }
                        }
                    }
                }
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:
                //Log.e("move", "x="+currentX+" y="+currentY+" i="+cur_line+" j="+cur_j);
                switch (state) {
                    case NEWTRAIN:
                    case NEWCARRIAGE:
                        if (cur_train != null) {
                            cur_train.setPosition(currentX, currentY);
                        }
                        break;
                    case NEWLINE:
                    case PROLONGLINE:
                        if (cur_line != null && cur_j !=-1) {
                            cur_line.m_tails[cur_j][0] = currentX;
                            cur_line.m_tails[cur_j][1] = currentY;
                        }
                        break;
                }
                break;

            // lorsque le doigt quitte l'écran
            case MotionEvent.ACTION_UP :
                Log.e("up", "x="+currentX+" y="+currentY+" i="+cur_line+" j="+cur_j);
                switch (state) {
                    case NEWTRAIN:
                        if (cur_train != null) {
                            for (Station s : m_stations) {
                                X = s.getX() - currentX;
                                Y = s.getY() - currentY;
                                if (Math.sqrt(X*X+Y*Y) < 2*s.RADIUS) {
                                    for (Line l : m_lines) {
                                        if (l.getStations().contains(s)) {
                                            int index = l.getStations().indexOf(s);
                                            int sens = 1;
                                            if (index != 0 && index != l.getStations().size()-1) {
                                                float Xa = l.getStations().get(index-1).getX() - s.getX();
                                                float Ya = l.getStations().get(index-1).getY() - s.getY();
                                                float Xb = l.getStations().get(index+1).getX() - s.getX();
                                                float Yb = l.getStations().get(index+1).getY() - s.getY();
                                                if (Math.sqrt(Xa*Xa+Ya*Ya) > Math.sqrt(Xb*Xb+Yb*Yb)) {
                                                    sens = -1;
                                                }
                                            }
                                            l.addTrain(s, sens);
                                            buildable[0]--;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        cur_train = null;
                        state = State.NOTHING;
                        break;
                    case NEWCARRIAGE:
                        if (cur_train != null) {
                            for (Line l : m_lines) {
                                for (Train t : l.getTrains()) {
                                    X = t.getX() - currentX;
                                    Y = t.getY() - currentY;
                                    if (Math.sqrt(X*X+Y*Y) < 2*t.WIDTH) {
                                        t.addCarriage();
                                        buildable[1]--;
                                        break;
                                    }
                                }
                            }
                        }
                        cur_train = null;
                        state = State.NOTHING;
                        break;
                    case NEWLINE:
                    case PROLONGLINE:
                        if (cur_line != null && cur_j !=-1) {
                            for (Station s : m_stations) {
                                X = s.getX() - currentX;
                                Y = s.getY() - currentY;
                                if (Math.sqrt(X*X+Y*Y) < 2*s.RADIUS) {

                                    if (cur_line.getStations().size() >=3 ){
                                        if (cur_j == 0 && s == cur_line.getStations().get(1)) {
                                            if (cur_line.canRemoveStation(0)) {
                                                for (River river : m_rivers) {
                                                    if (river.isCrossing(cur_line.getStations().get(0), s))
                                                        buildable[3] ++;

                                                }
                                                cur_line.removeStation(0);
                                            }
                                            break;
                                        }
                                        else if (cur_j == 1 && s == cur_line.getStations().get(cur_line.getStations().size()-2)) {
                                            int i=cur_line.getStations().size()-1;
                                            if (cur_line.canRemoveStation(i)) {
                                                for (River river : m_rivers) {
                                                    if (river.isCrossing(cur_line.getStations().get(i), s))
                                                        buildable[3] ++;

                                                }
                                                cur_line.removeStation(i);
                                            }

                                            break;
                                        }
                                    }

                                    boolean canAdd = true;
                                    for (Station st : cur_line.getStations()) {
                                        if (s == st) {
                                            canAdd = false;
                                        }
                                    }
                                    for (River river : m_rivers) {
                                        if ((cur_j == 0 && river.isCrossing(cur_line.getStations().get(0), s)) || (cur_j == 1 && river.isCrossing(cur_line.getStations().get(cur_line.getStations().size()-1), s))){
                                            if (buildable[3] == 0) {
                                                canAdd = false;
                                            }
                                            else {
                                                buildable[3]--;
                                            }
                                        }
                                    }

                                    if (canAdd) {
                                        cur_line.addStation(s, cur_j==1);
                                        if (state == State.NEWLINE) {
                                            addLine(cur_line);
                                            buildable[2]--;
                                        }
                                    }
                                }
                            }

                            cur_line.updateTail();
                            cur_line = null;
                            cur_j = -1;
                        }
                        state = State.NOTHING;
                        break;
                    }
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        Log.d("click","click" );
        return false;
    }

    private class DrawingThread extends Thread {
        boolean keepDrawing = true;

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            Canvas canvas;
            while (keepDrawing) {
                canvas = null;

                try {
                    canvas = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        try {
                            if (canvas != null)
                                onDraw(canvas);
                        } catch (ConcurrentModificationException e) {}
                    }
                } finally {
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
            }
        }
    }
}
