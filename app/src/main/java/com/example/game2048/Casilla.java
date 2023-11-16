package com.example.game2048;

import android.graphics.Color;

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
        this.bgColor = Color.rgb(191, 191, 191);
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
        if(ocupada){
            this.valor = 2;
            this.estado = ColoresFondo.COLOR_2;
        }else{
            this.valor = 0;
        }
        this.ocupada = ocupada;
    }

    public void sumar(int valor){
        this.valor = this.valor + valor;
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
