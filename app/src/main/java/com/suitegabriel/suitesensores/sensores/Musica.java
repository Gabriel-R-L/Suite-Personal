package com.suitegabriel.suitesensores.sensores;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.suitegabriel.suitesensores.R;

import androidx.fragment.app.Fragment;

public class Musica extends Fragment {
    //* Creamos el objeto media player para reproducir musica
    MediaPlayer miReproductor;

    //* variables para trabajar con los servicios musica
    private boolean encendida;
    private ImageView botonMusica;

    //* Guardará la canción que se ha usado anteriormente para que no se repita
    private int index_guardado = 0;
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
        //* que comience el servicio
        elegirMusica();

        encendida = !encendida;
    }



    private void elegirMusica()  {
        int[] songResources = {R.raw.bach_suite, R.raw.bumblebee, R.raw.fallout, R.raw.guetta, R.raw.nightcall};
        int[] songImages = {R.drawable.bach_suite, R.drawable.bumblebee, R.drawable.fallout, R.drawable.guetta, R.drawable.nightcall};

        int index = -1;

        do {
           index = (int) (Math.random() * songResources.length);
        } while(index == index_guardado);

        index_guardado = index;

        botonMusica.setImageResource(songImages[index]);

      //* inicializamos el objeto con el nombre de la cancion descargada y guardada en el directorio raw
        miReproductor = MediaPlayer.create(getActivity(), songResources[index]);

        //* para que se reproduzca constantemente
        miReproductor.setLooping(true);

        //* volumen izq y der
        miReproductor.setVolume(50, 50);

        //* inicializamos el reproductor
        miReproductor.start();
    }

    public void apagaMusica() {
        //* volverá el botón a su imagen original
        botonMusica.setImageResource(R.drawable.cd0);
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
}
