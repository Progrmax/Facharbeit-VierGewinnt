/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.viergewinnt;

public class Move {
    private int lX;
    private int lY;
    private int Ev;
    private Coin[][] sp;
    public Move(Coin[][]Spielbrett, int lx, int ly, int Evaluation){
        sp = Spielbrett;
        lX= lx;
        lY = ly;
        Ev = Evaluation;
    }

    public Coin[][] gibSpielbrett(){
        return sp;
    }
    public void setzeSpielbrett(Coin[][] SP){
        sp = SP;
    }
    public int gibX(){
        return lX;
    }
    public int gibY(){
        return lY;
    }
    public int gibEvaluation(){
        return Ev;
    }
    public void setzeEvaluation(int ev){
        Ev = ev;
    }

}
