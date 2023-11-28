package com.suitejvg.suitesensores.sensores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.suitejvg.suitesensores.R;

@SuppressLint("ViewConstructor")
public class NivelPantalla extends AppCompatImageView {
    int lado, radio, radioPeq, trazo;

    //* Nuevas variables para el suavizado de los ángulos
    private float smoothedAngleX = 0;
    private float smoothedAngleY = 0;

    //* Para suavizar el movimiento de la imagen y que no parezca que tiene parkinson
    private static final float SMOOTHING_FACTOR = 0.3f;

    //* variaciones del sensor
    float[] angulos;

    //* dibujo de fondo
    Bitmap fondo;
    Paint trazoDibujo;
    Bitmap burbuja;

    public NivelPantalla(Context context, int lado) {
        super(context);

        this.lado = lado;
        radio = lado/2;
        radioPeq = lado/10;
        trazo = lado/100;
        angulos = new float[2];

        angulos[0] = 0;
        angulos[1] = 0;

        fondo = iniciarFondo();
        trazoDibujo = new Paint();
        trazoDibujo.setColor(Color.BLACK);
        trazoDibujo.setTextSize(20);
        BitmapDrawable bola = (BitmapDrawable) ContextCompat.getDrawable(context, R.mipmap.burbuja);
        assert bola != null;
        burbuja = bola.getBitmap();
        burbuja = Bitmap.createScaledBitmap(burbuja, radioPeq*2, radioPeq*2, true);
    }

    private Bitmap iniciarFondo() {
        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
        Bitmap fondo = Bitmap.createBitmap(lado, lado, conf);
        Canvas lienzo = new Canvas(fondo);
        Paint lapiz = new Paint();

        //* Dibujo del primer círculo (verde)
        lapiz.setColor(Color.GREEN);
        lienzo.drawCircle(radio, radio, radio, lapiz);

        //* Segundo fondo para dar profundidad (negro)
        lapiz.setColor(Color.BLACK);
        lienzo.drawCircle(radio, radio, radio-trazo, lapiz);

        //* Dibujar las líneas verdes
        lapiz.setColor(Color.GREEN);
        lienzo.drawLine(radio, 0, radio, lado, lapiz);
        lienzo.drawLine(0, radio, lado, radio, lapiz);

        return fondo;
    }

    public void angulos(float[] angulos) {
        // Aplicar suavizado a los ángulos
        smoothedAngleX = smoothAngle(smoothedAngleX, angulos[0]);
        smoothedAngleY = smoothAngle(smoothedAngleY, angulos[1]);
        invalidate();
    }

    //* Método para suavizar un ángulo
    private float smoothAngle(float currentAngle, float newAngle) {
        // Aplicar interpolación lineal para suavizar el ángulo
        return currentAngle + SMOOTHING_FACTOR * (newAngle - currentAngle);
    }

    //* ajustar dimensiones de la vista
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Obtener las dimensiones del contenedor
        int contenedorAncho = MeasureSpec.getSize(widthMeasureSpec);
        int contenedorAlto = MeasureSpec.getSize(heightMeasureSpec);

        // Centrar la vista en el contenedor
        int left = (contenedorAncho - lado) / 2;
        int top = (contenedorAlto - lado) / 2;

        // Establecer las dimensiones de la vista
        setMeasuredDimension(lado, lado);
        setX(left);
        setY(top);
    }

    protected void onDraw(Canvas lienzo) {
        super.onDraw(lienzo);
        lienzo.drawBitmap(fondo, 0, 0, null);
        //* Calcular la posición de la burbuja
        int posX = radio - radioPeq + (int) (smoothedAngleX / 10 * radio);
        int posY = radio - radioPeq + (int) (smoothedAngleY / 10 * radio);
        posX += 7;
        posY -= 3;
        lienzo.drawBitmap(burbuja, posX, posY, null);
    }
}
