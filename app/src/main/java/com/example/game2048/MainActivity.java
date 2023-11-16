package com.example.game2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

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


        gestureDetector = new GestureDetectorCompat(this, this);
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
        tablero[inicioI][inicioJ].setOcupada(true);
        tablero[inicioI][inicioJ].setValor(2);
        buttons[inicioI][inicioJ].setText(tablero[inicioI][inicioJ].getValor()+"");
        getPuntuacionMasAlta();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

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
        buttons[inicioI][inicioJ].setText(tablero[inicioI][inicioJ].getValor()+"");
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        float diffX = event2.getX() - event1.getX();
        float diffY = event2.getY() - event1.getY();

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

        //recorro ese arraylist para mover todos los botones que ocupen esas posiciones que guardé antes
        for(Integer[] posicion:posiciones){
            tablero[posicion[0]][posicion[1]].setOcupada(false);
            buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
            int posInitI = posicion[0];
            int posInitJ = posicion[1];
            int valorInicial = tablero[posInitI][posInitJ].getValor();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Deslizamiento horizontal
                if (diffX > 0) {
                    // Deslizamiento hacia la derecha
                    while(posicion[1] < DIMENSION_J - 1){
                        posicion[1]++;
                    }
                    while(tablero[posicion[0]][posicion[1]].isOcupada()){
                        posicion[1]--;
                    }
                } else {
                    // Deslizamiento hacia la izquierda
                    while(posicion[1] > 0){
                        posicion[1]--;
                    }
                    while(tablero[posicion[0]][posicion[1]].isOcupada()){
                        posicion[1]++;
                    }
                }
            } else {
                // Deslizamiento vertical
                if (diffY > 0) {
                    // Deslizamiento hacia abajo
                    while(posicion[0] < DIMENSION_I - 1){
                        posicion[0]++;
                    }
                    while(tablero[posicion[0]][posicion[1]].isOcupada()){
                        posicion[0]--;
                    }
                } else {
                    // Deslizamiento hacia arriba
                    while(posicion[0] > 0){
                        posicion[0]--;
                    }
                    while(tablero[posicion[0]][posicion[1]].isOcupada()){
                        posicion[0]++;
                    }
                }
            }
            tablero[posicion[0]][posicion[1]].setOcupada(true);
            buttons[posicion[0]][posicion[1]].setText(tablero[posicion[0]][posicion[1]].getValor()+"");
        }
        generarNuevo();
        getPuntuacionMasAlta();
        return true;
    }

    // Implementa otros métodos de la interfaz GestureDetector.OnGestureListener según sea necesario
}