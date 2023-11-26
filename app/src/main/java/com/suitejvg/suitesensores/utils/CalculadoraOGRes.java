package com.suitejvg.suitesensores.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.suitejvg.suitesensores.R;

public class CalculadoraOGRes extends Fragment {
    private TextView tituloOperacion, resultado;
    private Button regresar;
    private double num1, num2;
    private String tipoBtnPulsado;
    public CalculadoraOGRes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setContentView(R.layout.fragment_calculadora_o_g_res);

        tituloOperacion = (TextView) requireActivity().findViewById(R.id.tituloOperacion);
        resultado = (TextView) requireActivity().findViewById(R.id.resultado);

        // Recuperar los numeros de la main activity
        num1 = requireActivity().getIntent().getExtras().getDouble("num1");
        num2 = requireActivity().getIntent().getExtras().getDouble("num2");
        tipoBtnPulsado = requireActivity().getIntent().getExtras().getString("tipoBtnPulsado");

        calculoOperacion();
    }

    public void calculoOperacion() {
        double operacion = 0;
        char simbolo = '\0';

        // Calcular la operación deseada
        switch (tipoBtnPulsado) {
            case "Sumar":
                tituloOperacion.setText("Suma");
                operacion = num1 + num2;
                simbolo = '+';
                break;
            case "Restar":
                tituloOperacion.setText("Resta");
                operacion = num1 - num2;
                simbolo = '-';
                break;
            case "Multiplicar":
                tituloOperacion.setText("Multiplicación");
                operacion = num1 * num2;
                simbolo = '*';
                break;
            case "Dividir":
                tituloOperacion.setText("División");
                operacion = Math.round((num1 / num2) * 100.0) / 100.0;
                simbolo = '/';
                break;
        }


        mostrarOperacion(operacion, simbolo);
    }

    public void mostrarOperacion(double operacion, char simbolo) {
        // Guardar los datos para pasar a la view
        String preResultado = formatNumber(num1) + " " + simbolo + " " + formatNumber(num2) + " = " + formatNumber(operacion);

        // Establecer resultado en la view
        resultado.setText(preResultado);
    }

    private static String formatNumber(double num) {
        return (num % 1 == 0) ? String.valueOf((int) num) : String.valueOf(num);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculadora_o_g_res, container, false);
    }
}