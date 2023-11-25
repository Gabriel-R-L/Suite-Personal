package com.suitejvg.suitesensores.utils;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

public class Creditos extends Fragment {
    ImageView github;
    ImageView linkedin;
    public Creditos() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creditos, container, false);

        github = view.findViewById(R.id.github);
        linkedin = view.findViewById(R.id.linkedin);

        github.setOnClickListener(v -> {
                //* Mostrar el Toast
                Toast.makeText(getActivity(), "Viajando a mi GitHub", Toast.LENGTH_SHORT).show();
//*          Crear un Intent con la acción de ver
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Gabriel-R-L"));

                //* Verificar si hay una aplicación que puede manejar el Intent
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    //* Iniciar la actividad
                    startActivity(intent);
            }
        });

        linkedin.setOnClickListener(v -> {
            //* Mostrar el Toast
            Toast.makeText(getActivity(), "Viajando a mi LinkedIn", Toast.LENGTH_SHORT).show();
            //* Crear un Intent con la acción de ver
            //* Verificar si hay una aplicación que puede manejar el Intent
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/gabriel-ramos-lopez/"));

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                //* Iniciar la actividad
                startActivity(intent);
            }
        });

        return view;
    }
}