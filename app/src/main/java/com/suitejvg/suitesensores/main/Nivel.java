package com.suitejvg.suitesensores.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class Nivel extends Fragment implements SensorEventListener {

    //* objetos del sensormanager
    private SensorManager miManager;
    private Sensor miSensor;
    private NivelPantalla pantalla;

    public Nivel() { }

    @Override
    //* inCreate para vistas normales. onCreateView para fragmentos
    public final void onCreate(Bundle savedInstanceState) {
        //* sobreescribe a la clase padre
        super.onCreate(savedInstanceState);

        //* acceder al servicio de sensores de nuestro dispositivo
        miManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //* usa el sensor específico para esta actividad
        miSensor = miManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //* conseguir el tamaño de la pantalla
        int lado = getResources().getDimensionPixelSize(R.dimen.maximo);

        //* instanciar la clase nivelPantalla
        pantalla = new NivelPantalla(getActivity(), lado);
    }

    @Override
    @NonNull
    //* dibuja la interfaz
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return pantalla;
    }

    public void onResume() {
        super.onResume();
        //* ponemos a la escucha el sensor
        miManager.registerListener(this, miSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onPause() {
        super.onPause();
        //* dejar de escuchar los eventos
        miManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //* recibirá el cambio de inclinación en el dispositivo (si lo hay)
        pantalla.angulos(event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}