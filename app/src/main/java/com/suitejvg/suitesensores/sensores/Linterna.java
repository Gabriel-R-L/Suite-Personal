package com.suitejvg.suitesensores.sensores;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class Linterna extends Fragment {
    private ImageView botonCamara;
    private SensorManager miManager;

    //* por defecto es false
    private boolean encendida;
    private CameraManager micamara;
    private String idCamara;

    public Linterna() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            micamara = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        }

        try {
            idCamara = micamara.getCameraIdList()[0];
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //* Guarda en memoria el xml
        View fragmento = inflater.inflate(R.layout.fragment_linterna, container, false);

        //* Conseguir imagen linterna
        botonCamara = (ImageView) fragmento.findViewById(R.id.linterna);

        //* Si está encendida, muestra la imagen iluminada
//        if (encendida) botonCamara.setImageResource(R.mipmap.linterna2);

        //* clase anónima para verificar si tenemos o no el flash apagado
        botonCamara.setOnClickListener(v -> {
            if (encendida) {
                botonApagaFlash();
                encendida = false;
            } else {
                botonEnciendeFlash();
                encendida = true;
            }
        });

        return fragmento;
    }

    public void botonEnciendeFlash() {
        if (Math.random() > 0.95) {
            botonCamara.setImageResource(R.mipmap.oficina3);
        } else {
            botonCamara.setImageResource(R.mipmap.oficina2);
        }
        enciendeApaga(encendida);
    }

    public void botonApagaFlash() {
        botonCamara.setImageResource(R.mipmap.oficina);
        enciendeApaga(encendida);
    }

    //* parar la linterna
    public void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                micamara.setTorchMode(idCamara, false);
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void enciendeApaga(boolean estadoflash) {
        try {
            if (estadoflash) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    micamara.setTorchMode(idCamara, false);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    micamara.setTorchMode(idCamara, true);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}