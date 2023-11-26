package com.suitejvg.suitesensores.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

public class CalculadoraOG extends Fragment {

    private final Activity calculadora = requireActivity();
    private EditText num1, num2;
    private Button btnFuncion;
    private View[] idBtnsOperaciones = {calculadora.findViewById(R.id.sumar), calculadora.findViewById(R.id.restar), calculadora.findViewById(R.id.multiplicar), calculadora.findViewById(R.id.dividir)};
    private String valorBtn;
    public CalculadoraOG() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Buscar los números
        num1 = (EditText) requireActivity().findViewById(R.id.editTextNumberDecimal);
        num2 = (EditText) requireActivity().findViewById(R.id.editTextNumberDecimal2);
    }

    // Buscar los botones
    public void buscarBtn(View view) {
        // Recoger el valor de TODOS los botones
        btnFuncion = (Button) view;

        // Comprobar que no haya datos vacíos
        if (num1.getText().toString().isEmpty() || num2.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(requireActivity(), "No has introducido números", Toast.LENGTH_LONG);
            toast.show();
        } else {
            // Obtener el texto del botón
            valorBtn = btnFuncion.getText().toString();

            // Ir a la otra actividad
            verResultado();
        }
    }

    public void verResultado() {
        // Instanciar la otra actividad
        Intent actCalculos = new Intent(getActivity(), CalculadoraOG.class);

        // Enviar números a la otra actividad
        actCalculos.putExtra("num1", Double.parseDouble(num1.getText().toString()));
        actCalculos.putExtra("num2", Double.parseDouble(num2.getText().toString()));
        actCalculos.putExtra("tipoBtnPulsado",valorBtn);

        // Iniciar la otra actividad
        startActivity(actCalculos);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        for (final View idBoton : idBtnsOperaciones) {
            // Asigna un OnClickListener a cada botón
            idBoton.setOnClickListener(v -> buscarBtn(getView()));

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculadora_o_g, container, false);
    }
}