/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.viergewinnt;
import java.awt.*;

public class Coin {

    private Color color;
    private int playerID;

    private int x = 0;
    private int y = 0;


    public Coin(int pPlayerID, Color pColor, int pX, int pY){
        color = pColor;
        playerID = pPlayerID;

        x = pX;
        y = pY;
    }

    public boolean isEmpty(){
        return playerID == 0;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int pX){
        x = pX;
    }
    public void setY(int pY){
        y = pY;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color pColor){
        color = pColor;
    }

    public int getPlayerID(){
        return playerID;
    }

    public void setPlayerID(int pPlayerId){
        playerID = pPlayerId;
    }


}
