/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.viergewinnt;
public class Spielzug {
    private Coin[][] spielfeld;
    private int xCor = 0;
    private int yCor = 0;
    private int spielerID = 0;
    public Spielzug(Coin[][] sp, int x, int y, int id){
               spielfeld = sp;
               xCor = x;
               yCor = y;
               spielerID = id;
    }
    public Coin[][] gibSpielfeld(){
        return spielfeld;
    }
    public int gibX(){
        return xCor;
    }
    public int gibY(){
        return yCor;
    }
    public int gibId(){
        return spielerID;
    }
    public void setzeX(int x){
        xCor = x;
    }
    public void setzeY(int y){
        yCor = y;
    }
    public void setzeSpielerID(int id){
        spielerID = id;
    }
    public void setzeSpielfeld(Coin[][] sp){
        spielfeld = sp;
    }
}
