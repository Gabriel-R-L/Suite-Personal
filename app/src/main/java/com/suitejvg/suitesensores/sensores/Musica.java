package com.suitejvg.suitesensores.sensores;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Musica extends Fragment {
    //* Creamos el objeto media player para reproducir musica
    MediaPlayer miReproductor;

    //* variables para trabajar con los servicios musica
    private boolean encendida;
    private ImageView botonMusica;
    public Musica() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //* inicializamos el objeto con el nombre de la cancion descargada y guardada en el directorio raw
        miReproductor = MediaPlayer.create(getActivity(), R.raw.bach_suite);

        //* para que se reproduzca constantemente
        miReproductor.setLooping(true);

        //* volumen izq y der
        miReproductor.setVolume(50,50);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //* Guarda en memoria el xml
        View fragmento = inflater.inflate(R.layout.fragment_musica, container, false);

        //* la imagen del altavoz
        botonMusica = (ImageView) fragmento.findViewById(R.id.musica);

        botonMusica.setOnClickListener(v -> {
            //* si no está encendida, que la apague
            if (encendida) apagaMusica();
            else enciendeMusica();
        });

        //* nos devuelve el fragmento
        return fragmento;
    }

    public void enciendeMusica() {
        //* volverá amarillo el botón
        botonMusica.setImageResource(R.mipmap.musica2);
        //* que comience el servicio
        onStartCommand();

        encendida = !encendida;
    }

    public void apagaMusica() {
        //* volverá el botón a su imagen original
        botonMusica.setImageResource(R.mipmap.musica);
        //* que finalice el servicio
        onStop();

        encendida = !encendida;
    }

    //* Parar la musica al cambiar de actividad
    @Override
    public void onStop() {
        super.onStop();
//        onDestroy();
        miReproductor.release();
    }

    public void onStartCommand() {
        //* inicializamos el objeto con el nombre de la cancion descargada y guardada en el directorio raw
        miReproductor = MediaPlayer.create(getActivity(), R.raw.bach_suite);

        //* para que se reproduzca constantemente
        miReproductor.setLooping(true);

        //* volumen izq y der
        miReproductor.setVolume(50, 50);

        //* inicializamos el reproductor
        miReproductor.start();
    }
}
