package com.example.game2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button buttons[][];

    ImageButton reset;
    View fondo;

    TextView puntuacionTxt;

    private Casilla tablero[][];

    private static final int DIMENSION_I = 4;
    private static final int DIMENSION_J = 4;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fondo = findViewById(R.id.fondo);
        buttons = new Button[DIMENSION_I][DIMENSION_J];
        tablero = new Casilla[DIMENSION_I][DIMENSION_J];
        puntuacionTxt = findViewById(R.id.puntuacion);

        iniciarPartida();

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones que quieres realizar cuando se hace clic en el ImageButton
                iniciarPartida();
            }
        });

        fondo.setOnTouchListener(new View.OnTouchListener() {
            private static final int MIN_DISTANCE = 100;
            private float startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ArrayList<Integer[]> posiciones = new ArrayList<>();

                //busco todos los botones cuyo valor sea distinto de 0 y los guardo en un array list de Integer[].
                //posiciones contiene arrays de tamaño 2 que tiene en posiciones[0] la posición i y en posiciones[1] la posicion j
                //donde está el valor distinto a 0
                for (int i = 0; i < DIMENSION_I; i++) {
                    for (int j = 0; j < DIMENSION_J; j++) {
                        if (!buttons[i][j].getText().equals("0")) {
                            Integer pos[] = new Integer[2];
                            pos[0] = i;
                            pos[1] = j;
                            posiciones.add(pos);
                        }
                    }
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        generarNuevo();
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:

                        float endX = event.getX();
                        float endY = event.getY();

                        float diffX = endX - startX;
                        float diffY = endY - startY;

                        for(Integer[] posicion: posiciones){
                            Log.d("posiciones", posicion[0] + " " +posicion[1]);
                            int valorInicial = tablero[posicion[0]][posicion[1]].getValor();
                            tablero[posicion[0]][posicion[1]].setValor(0);
                            tablero[posicion[0]][posicion[1]].setOcupada(false);
                            buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
                            if (Math.abs(diffX) > Math.abs(diffY)) {
                                if (Math.abs(diffX) > MIN_DISTANCE) {
                                    if (diffX > 0) {
                                        // Deslizamiento hacia la derecha
                                        while(posicion[1] < DIMENSION_J - 1){
                                            posicion[1]++;
                                        }
                                        while(tablero[posicion[0]][posicion[1]].isOcupada()){
                                            if(sumables(valorInicial, tablero[posicion[0]][posicion[1]].getValor())){
                                                tablero[posicion[0]][posicion[1]].sumar(valorInicial);
                                                buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
                                            }
                                            else{
                                                posicion[1]--;
                                            }
                                        }
                                    } else {
                                        // Deslizamiento hacia la izquierda
                                        while(posicion[1] > 0){
                                            posicion[1]--;
                                        }
                                        while(tablero[posicion[0]][posicion[1]].isOcupada()){
                                            if(sumables(valorInicial, tablero[posicion[0]][posicion[1]].getValor())){
                                                tablero[posicion[0]][posicion[1]].sumar(valorInicial);
                                                buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
                                            }
                                            else{
                                                posicion[1]++;
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (Math.abs(diffY) > MIN_DISTANCE) {
                                    if (diffY > 0) {
                                        // Deslizamiento hacia abajo
                                        // Tu lógica para mover los botones hacia abajo
                                        // Deslizamiento hacia abajo
                                        while(posicion[0] < DIMENSION_I - 1){
                                            posicion[0]++;
                                        }
                                        while(tablero[posicion[0]][posicion[1]].isOcupada()){
                                            if(sumables(valorInicial, tablero[posicion[0]][posicion[1]].getValor())){
                                                tablero[posicion[0]][posicion[1]].sumar(valorInicial);
                                                buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
                                            }
                                            else{
                                                posicion[0]--;
                                            }
                                        }
                                    } else {
                                        // Deslizamiento hacia arriba
                                        // Tu lógica para mover los botones hacia arriba
                                        while(posicion[0] > 0){
                                            posicion[0]--;
                                        }
                                        if(!tablero[posicion[0]][posicion[1]].isOcupada()){
                                            if(sumables(valorInicial, tablero[posicion[0]][posicion[1]].getValor())){
                                                tablero[posicion[0]][posicion[1]].sumar(valorInicial);
                                                buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
                                            }
                                            else{
                                                posicion[0]--;
                                            }
                                        }else{

                                        }
                                    }
                                }
                            }
                            tablero[posicion[0]][posicion[1]].setValor(2);
                            buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
                        }
                        break;
                }
                pintarBotones();
                return true;
            }
        });
    }

    public boolean sumables(int valor1, int valor2){
        return (valor1 == valor2) && (valor1 != 0);
    }

    public void iniciarPartida(){
        int id = 0;
        for (int i = 0; i < DIMENSION_I; i++) {
            for (int j = 0; j < DIMENSION_J; j++) {
                id++;
                int buttonId = getResources().getIdentifier("button" + (id), "id", getPackageName());
                buttons[i][j] = findViewById(buttonId);
                tablero[i][j] = new Casilla();
                buttons[i][j].setText(tablero[i][j].getValor()+"");
                buttons[i][j].setEnabled(false);
            }
        }

        int inicioI = (int)(Math.random() * DIMENSION_I);
        int inicioJ = (int)(Math.random() * DIMENSION_J);
        tablero[inicioI][inicioJ].setValor(2);
        tablero[inicioI][inicioJ].setOcupada(true);
        buttons[inicioI][inicioJ].setText(tablero[inicioI][inicioJ].getValor()+"");
        getPuntuacionMasAlta();
        pintarBotones();
    }

    public void getPuntuacionMasAlta(){
        int puntuacion = 0;
        for (int i = 0; i < DIMENSION_I; i++) {
            for (int j = 0; j < DIMENSION_J; j++) {
                if(tablero[i][j].getValor() >  puntuacion){
                    puntuacion = tablero[i][j].getValor();
                }
            }
        }
        puntuacionTxt.setText("Puntuación: "+puntuacion);
    }

    public void pintarBotones(){
        for (int i = 0; i < DIMENSION_I; i++) {
            for (int j = 0; j < DIMENSION_J; j++) {
                int color = 0;
                tablero[i][j].checkState();
                switch(tablero[i][j].getEstado()){
                    case COLOR_0:
                        color = getColor(R.color.color_0);
                        break;
                    case COLOR_2:
                        color = getColor(R.color.color_2);
                        break;
                    case COLOR_4:
                        color = getColor(R.color.color_4);
                        break;
                    case COLOR_8:
                        color = getColor(R.color.color_8);
                        break;
                    case COLOR_16:
                        color = getColor(R.color.color_16);
                        break;
                    case COLOR_32:
                        color = getColor(R.color.color_32);
                        break;
                    case COLOR_64:
                        color = getColor(R.color.color_64);
                        break;
                    case COLOR_128:
                        color = getColor(R.color.color_128);
                        break;
                    case COLOR_256:
                        color = getColor(R.color.color_256);
                        break;
                    case COLOR_512:
                        color = getColor(R.color.color_512);
                        break;
                    case COLOR_1024:
                        color = getColor(R.color.color_1024);
                        break;
                    case COLOR_2048:
                        color = getColor(R.color.color_2048);
                        break;
                }
                buttons[i][j].setBackgroundColor(color);
            }
        }
    }


    public void generarNuevo(){
        int inicioI = (int)(Math.random() * DIMENSION_I);
        int inicioJ = (int)(Math.random() * DIMENSION_J);
        //mientras esté ocupada
        while(tablero[inicioI][inicioJ].isOcupada()){
            inicioI = (int)(Math.random() * 3);
            inicioJ = (int)(Math.random() * 3);
        }
        tablero[inicioI][inicioJ].setValor(2);
        tablero[inicioI][inicioJ].setOcupada(true);
        buttons[inicioI][inicioJ].setText(tablero[inicioI][inicioJ].getValor()+"");
    }

    // Implementa otros métodos de la interfaz GestureDetector.OnGestureListener según sea necesario
}