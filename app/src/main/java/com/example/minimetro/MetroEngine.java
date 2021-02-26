package com.example.minimetro;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class MetroEngine {

    private List<Station> m_stations = null;
    private List<Line> m_lines = null;

    private MetroActivity m_activity = null;

   /* MetroEngine(MetroActivity view) {
        m_activity = view;
        m_activity.getBaseContext().getSystemService(Service.INPUT_SERVICE);
        m_activity.onTouchEvent()
    }*/



    //View.OnTouchListener

    /*SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            float x = pEvent.values[0];
            float y = pEvent.values[1];

            if(mBoule != null) {
                // On met à jour les coordonnées de la boule
                RectF hitBox = mBoule.putXAndY(x, y);

                // Pour tous les blocs du labyrinthe
                for(Bloc block : mBlocks) {
                    // On crée un nouveau rectangle pour ne pas modifier celui du bloc
                    RectF inter = new RectF(block.getRectangle());
                    if(inter.intersect(hitBox)) {
                        // On agit différement en fonction du type de bloc
                        switch(block.getType()) {
                            case TROU:
                                mActivity.showDialog(LabyrintheActivity.DEFEAT_DIALOG);
                                break;

                            case DEPART:
                                break;

                            case ARRIVEE:
                                mActivity.showDialog(LabyrintheActivity.VICTORY_DIALOG);
                                break;
                        }
                        break;
                    }
                }
            }
        }*/

     /*   @Override
        public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {

        }
    };*/

   /* public LabyrintheEngine(LabyrintheActivity pView) {
        mActivity = pView;
        mManager = (SensorManager) mActivity.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    // Remet à zéro l'emplacement de la boule
    public void reset() {
        mBoule.reset();
    }

    // Arrête le capteur
    public void stop() {
        mManager.unregisterListener(mSensorEventListener, mAccelerometre);
    }

    // Redémarre le capteur
    public void resume() {
        mManager.registerListener(mSensorEventListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
    }

    // Construit le labyrinthe
    public List<Bloc> buildLabyrinthe() {

    }*/

}
