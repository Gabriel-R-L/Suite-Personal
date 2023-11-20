package com.suitejvg.suitesensores.main;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.suitejvg.suitesensores.R;

public class borrarServicioMusica extends Service {
    //* Creamos el objeto media player para reproducir musica
    MediaPlayer miReproductor;

    public void onCreate() {
        Toast.makeText(this, "ohoslf,ds", Toast.LENGTH_SHORT).show();

        super.onCreate();
        //* inicializamos el objeto con el nombre de la cancion descargada y guardada en el directorio raw
        miReproductor = MediaPlayer.create(this, R.raw.bach_suite);

        //* para que se reproduzca constantemente
        miReproductor.setLooping(true);

        //* volumen izq y der
        miReproductor.setVolume(50,50);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        //* inicializamos el reproductor
        miReproductor.start();
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        //* parar la musica
        if(miReproductor.isPlaying()) {
            miReproductor.stop();
            miReproductor.release();
            miReproductor = null;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
