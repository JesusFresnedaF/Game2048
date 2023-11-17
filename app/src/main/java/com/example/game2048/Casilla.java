package com.example.game2048;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class Casilla {
    private int valor;
    private ColoresFondo estado;
    private boolean ocupada;
    private int bgColor;
    public Casilla(){
        this.valor = 0;
        this.estado = ColoresFondo.COLOR_0;
        this.ocupada = false;
    }

    public void checkState(){
        switch(this.valor){
            case 0:
                this.estado = ColoresFondo.COLOR_0;
                break;
            case 2:
                this.estado = ColoresFondo.COLOR_2;
                break;
            case 4:
                this.estado = ColoresFondo.COLOR_4;
                break;
            case 8:
                this.estado = ColoresFondo.COLOR_8;
                break;
            case 16:
                this.estado = ColoresFondo.COLOR_16;
                break;
            case 32:
                this.estado = ColoresFondo.COLOR_32;
                break;
            case 64:
                this.estado = ColoresFondo.COLOR_64;
                break;
            case 128:
                this.estado = ColoresFondo.COLOR_128;
                break;
            case 256:
                this.estado = ColoresFondo.COLOR_256;
                break;
            case 512:
                this.estado = ColoresFondo.COLOR_512;
                break;
            case 1024:
                this.estado = ColoresFondo.COLOR_1024;
                break;
            case 2048:
                this.estado = ColoresFondo.COLOR_2048;
                break;
        }
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public void sumar(int valor){
        setValor(this.valor + valor);
        Log.d("sumar", this.valor+"");
    }

    public int getBgColor() {
        return bgColor;
    }

    public ColoresFondo getEstado() {
        return estado;
    }

    public void setEstado(ColoresFondo estado) {
        this.estado = estado;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
