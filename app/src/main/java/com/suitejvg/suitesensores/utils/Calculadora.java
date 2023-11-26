package com.suitejvg.suitesensores.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

/*
? Librerías para el cálculo de operaciones
? requiere en build.gradle del module app, en la parte de las dependecias
? incluir lo siguiente: implementation ("net.objecthunter:exp4j:0.4.8")
 */
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Calculadora extends Fragment {

    TextView operacionTV;
    TextView resultadoTV;
    private String datosText = "";

    public Calculadora() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);

        //* Instancio los textos donde se muestran las operaciones y resultados
        operacionTV = view.findViewById(R.id.operacionTV);
        resultadoTV = view.findViewById(R.id.resultadoTV);

        //* Cargar los botones de la calculadora desde el controlador
        generarElementos(view);

        return view;
    }


    private void generarElementos(View view) {
        LinearLayout buttonContainer = view.findViewById(R.id.buttonContainer);

        String[][] buttonValues = {
                {"C", "CE"},
                {"(", ")", "^", "/"},
                {"7", "8", "9", "*"},
                {"4", "5", "6", "-"},
                {"1", "2", "3", "+"},
                {".", "0", "="}
        };

        //* Crear y agregar botones al contenedor principal
        for (String[] rowValues : buttonValues) {
            //* Crear un nuevo LinearLayout para cada fila
            LinearLayout rowLayout = new LinearLayout(getActivity());
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            //* Agregar botones a la fila actual
            for (String value : rowValues) {
                Button button = new Button(getActivity());
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1
                ));

                button.setText(value);

                //* Personalizar botones
                if (value.equals("=")) {
                    button.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    button.setTextSize(getResources().getDimension(R.dimen.calcButtonTextSize));
                    button.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2
                    ));
                    button.setTextColor(Color.WHITE);
                } else if (value.equals("C")) {
                    button.setTextSize(getResources().getDimension(R.dimen.calcButtonTextSize));
                    button.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                } else if (value.equals("CE")) {
                    button.setTextSize(getResources().getDimension(R.dimen.calcButtonTextSize));
                    button.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.teal_700));
                } else if (value.equals("(") || value.equals(")") || value.equals("^") || value.equals("/") ||
                        value.equals("*") || value.equals("-") || value.equals("+")) {
                    button.setTextSize(getResources().getDimension(R.dimen.calcButtonTextSize));
                    button.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    button.setBackground(null);
                } else {
                    //* Configuración para los botones que no se personalizan
                    button.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    button.setTextSize(getResources().getDimension(R.dimen.calcButtonTextSize));
                    button.setBackground(null);
                    button.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1));
                }

                //? Agregar el botón a la fila actual
                rowLayout.addView(button);


                //? Agregar un listener al botón para manejar clics
                button.setOnClickListener((View.OnClickListener) v -> listenerOperaciones((Button) v));
            }

            //* Agregar la fila al contenedor principal
            buttonContainer.addView(rowLayout);
        }
    }

    @SuppressLint("DefaultLocale")
    private void listenerOperaciones(Button btn) {
        //* Obtener el texto del botón clicado
        String buttonText = btn.getText().toString();

        switch (buttonText) {
            case "=":
                try {
                    double resultado = evaluarExpresion(datosText);
                    String resultadoFormateado;

                    //* Formatear el resultado
                    if (resultado % 1 == 0) {
                        resultadoFormateado = String.format("%.0f", resultado);
                    } else {
                        resultadoFormateado = String.format("%.2f", resultado);
                    }

                    resultadoTV.setText(resultadoFormateado);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error en la expresión", Toast.LENGTH_SHORT).show();
                }
                break;

            case "C":
                if (datosText.length() > 0) {
                    datosText = datosText.substring(0, datosText.length() - 1);
                    operacionTV.setText(datosText);
                }
                break;
            case "CE":
                if (datosText.length() > 0) {
                    datosText = "";
                    operacionTV.setText("");
                    resultadoTV.setText("0");
                }
                break;

//            case "(":
//            case ")":
//                datosText += buttonText;
//                operacionTV.setText(datosText);
//                break;

            default:
                datosText += buttonText;
                operacionTV.setText(datosText);
                break;
        }
    }

    //* método de la librería para los cálculos
    private double evaluarExpresion(String expresion) {
        try {
            Expression e = new ExpressionBuilder(expresion).build();
            return e.evaluate();
        } catch (ArithmeticException e) {
            // Manejar errores de aritmética (por ejemplo, división por cero)
            throw new RuntimeException("Error aritmético: " + e.getMessage());
        } catch (Exception e) {
            // Otros errores
            throw new RuntimeException("Error en la expresión: " + e.getMessage());
        }
}}