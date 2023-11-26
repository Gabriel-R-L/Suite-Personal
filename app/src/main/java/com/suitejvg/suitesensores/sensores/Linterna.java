package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

import java.util.Objects;

public class Linterna extends Fragment {
    private ImageView botonCamara;

    //* por defecto es false
    private boolean encendida;
    private CameraManager micamara;
    private String idCamara;

    public Linterna() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            micamara = (CameraManager) Objects.requireNonNull(requireActivity()).getSystemService(Context.CAMERA_SERVICE);
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
        randomLanternImage();
        enciendeApaga(encendida);
    }

    private void randomLanternImage() {
        int[] imageResources = {R.mipmap.office1, R.mipmap.office2, R.mipmap.office3, R.mipmap.office4, R.mipmap.office5, R.mipmap.office6, R.mipmap.office7, R.mipmap.office8, R.mipmap.office9, R.mipmap.office10, R.mipmap.office11};
        if (Math.random() >= 0.8) {
            int index = (int) (Math.random() * imageResources.length);
            botonCamara.setImageResource(imageResources[index]);
        } else {
            botonCamara.setImageResource(R.mipmap.office1);
        }
    }

    public void botonApagaFlash() {
        botonCamara.setImageResource(R.mipmap.office0);
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