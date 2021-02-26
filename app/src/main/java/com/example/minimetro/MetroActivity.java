package com.example.minimetro;

import android.app.Activity;
import android.os.Bundle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MetroActivity extends Activity {

    private MetroView m_view = null;
   // private MetroEngine m_engine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_view = new MetroView(this);
        setContentView(m_view);




       // m_engine = new MetroEngine(this);

       /* ArrayList<Station> stations = new ArrayList<>();
        Station c = new Station(20,20);
        stations.add(c);
        stations.add( new Station(200,20));
        stations.add( new Station(400,20));
        //stations.add( new Station(300,100));


        ArrayList<Line> lines = new ArrayList<>();
        Line a = new Line(stations);
        a.addTrain();
        lines.add(a);
        m_view.setLines(lines);

        ArrayList<Station> stations2 = new ArrayList<>();
        Station b = new Station(400,200);
        b.addPassenger(new Passenger(c));
        stations2.add(b);
        stations2.add( new Station(200,200));
        stations2.add( new Station(200,100));
        stations2.addAll(stations);
        m_view.setStations(stations2);*/

//        m_view.addStation();
  //      m_view.addStation();

        TimerTask newStation = new TimerTask() {
            @Override
            public void run() {
            m_view.addStation();
            }
        };
        Timer timer = new Timer();
        timer.schedule(newStation, 0, 3000);

        TimerTask newPassenger = new TimerTask() {
            @Override
            public void run() {
            m_view.addPassenger();
            }
        };
        Timer timer2 = new Timer();
        timer2.schedule(newPassenger, 0, 5000);

        TimerTask nextWeek = new TimerTask() {
            @Override
            public void run() {
                m_view.buildable[0]++;
                m_view.buildable[1]++;
                m_view.buildable[2]++;
                m_view.buildable[3]++;
            }
        };
        Timer timer3 = new Timer();
        timer3.schedule(nextWeek, 30000, 30000);

        TimerTask newRiver = new TimerTask() {
            @Override
            public void run() {
                m_view.addRiver();
            }
        };
        Timer timer4 = new Timer();
        timer4.schedule(newRiver, 1000);

    }

   /* @Override
    protected void onResume() {
        super.onResume();
        m_engine.resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        m_engine.stop();
    }
*/


    /*
    @Override
    public Dialog onCreateDialog (int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case VICTORY_DIALOG:
                builder.setCancelable(false)
                        .setMessage("Bravo, vous avez gagné !")
                        .setTitle("Champion ! Le roi des Zörglubienotchs est mort grâce à vous !")
                        .setNeutralButton("Recommencer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // L'utilisateur peut recommencer s'il le veut
                                m_engine.reset();
                                m_engine.resume();
                            }
                        });
                break;

            case DEFEAT_DIALOG:
                builder.setCancelable(false)
                        .setMessage("La Terre a été détruite à cause de vos erreurs.")
                        .setTitle("Bah bravo !")
                        .setNeutralButton("Recommencer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                m_engine.reset();
                                m_engine.resume();
                            }
                        });
        }
        return builder.create();

    }

    @Override
    public void onPrepareDialog (int id, Dialog box) {
        // A chaque fois qu'une boîte de dialogue est lancée, on arrête le moteur physique
        m_engine.stop();
    }*/
}
