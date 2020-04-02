/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.viergewinnt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.TileObserver;
import java.beans.Visibility;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class PanelClass extends JPanel {
    public static Coin[][] spielbrett;
    Coin animationCoin;
    JButton[] column_buttons;
    int animationY = 0;
    int animationX = 0;
    boolean setting_enable_animation = true;
    boolean start_animation = false;

    int SpielerAnzeigeZug = 1;
    int xSize, ySize;

    int letzterZugX = 0;
    int letzterZugY = 0;
    int tmpSpilerAmZug = 0;
    boolean negamax = false;
    boolean negamaxStop = false;

    boolean Spieler1Gewonnen = false;
    boolean Spieler2Gewonnen = false;
    public static boolean vollbild = false;

    Color farbeSpieler1 = Color.yellow;
    Color farbeSpieler2 = Color.red;

    boolean gameStop = false;
    JButton reset;
    JLabel negamaxTiefeText;
    JTextField NegaMaxTiefe;

    public PanelClass() {
        startGame();
    }

    private void startGame() {
        spielbrett = new Coin[7][6];
        column_buttons = new JButton[7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Color lColor = new Color(150, 150, 150);
                spielbrett[i][j] = new Coin(0, lColor, i * 100 + 150, 200 + j * 100);
            }
        }

        animationCoin = new Coin(3, new Color(255, 255, 255), 0, 100);

        init();
        setTimer();
    }

    private void init() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        xSize = ((int) tk.getScreenSize().getWidth());
        ySize = ((int) tk.getScreenSize().getHeight());
        setSize(xSize, ySize);
        setLayout(null);

        JLabel Titel = new JLabel();
        Titel.setBounds(xSize/2 - xSize/8, ySize/1000,xSize/2,ySize/10);
        Titel.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        Titel.setForeground(Color.black);
        Titel.setText("Vier-Gewinnt");
        this.add(Titel);

        JPanel settings_panel = new JPanel();
        settings_panel.setLayout(new GridLayout(12,1));
        settings_panel.setBounds(xSize / 2, ySize / 2, xSize / 2 - xSize / 14, ySize / 2 - ySize / 14);
        settings_panel.setBackground(Color.white);
        Border border = settings_panel.getBorder();
        Border margin = new LineBorder(Color.gray,4);
        settings_panel.setBorder(new CompoundBorder(border, margin));

        JLabel setting_label = new JLabel();
        setting_label.setText("Optionen");
        setting_label.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        setting_label.setForeground(Color.black);
        setting_label.setHorizontalAlignment(JLabel.CENTER);
        settings_panel.add(setting_label);

        setBackground(Color.white);

        JCheckBox jcb = new JCheckBox();
        jcb.setVisible(true);
        jcb.setText("Animation");
        jcb.setSelected(true);
        jcb.setBackground(Color.white);
        jcb.setForeground(Color.black);
        jcb.addActionListener(actionEvent -> {
            if (jcb.isSelected()) {
                setting_enable_animation = true;
            } else {
                setting_enable_animation = false;
            }


        });

        JCheckBox jcb2 = new JCheckBox();
        jcb2.setVisible(true);
        jcb2.setText("ZufälligerEinwurf(NPC)");
        jcb2.setBackground(Color.white);
        jcb2.setForeground(Color.black);
        jcb2.addActionListener(actionEvent -> {
            if (jcb2.isSelected()) {
                randomKI = true;
            } else {
                randomKI = false;
            }
        });

        JCheckBox jcb3 = new JCheckBox();
        jcb3.setVisible(true);
        jcb3.setText("Negamax");
        jcb3.setBackground(Color.white);
        jcb3.setForeground(Color.black);
        jcb3.addActionListener(actionEvent -> {
            if (jcb3.isSelected()) {
                negamax = true;
            } else {
                negamax = false;
            }


        });

        JCheckBox jcb4 = new JCheckBox();
        jcb4.setVisible(true);
        jcb4.setText("Vollbild");
        jcb4.setBackground(Color.white);
        jcb4.setForeground(Color.black);
        if(vollbild == true){
            jcb4.setSelected(true);
        }
        jcb4.addActionListener(actionEvent -> {
            if (jcb4.isSelected()) {
                vollbild = true;
                main.jf.setVisible(false);
                main.jf = new GUI();

            } else {
                vollbild = false;
                main.jf.setVisible(false);
                main.jf = new GUI();
            }


        });


        negamaxTiefeText = new JLabel();
        negamaxTiefeText.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        negamaxTiefeText.setForeground(Color.black);
        negamaxTiefeText.setHorizontalAlignment(JLabel.LEFT);
        negamaxTiefeText.setText(" ↑↑↑ Hier die Suchtiefe des Negamax eingeben (Bei einem Wert von 7 oder höher kann es zu langen Wartezeiten kommen)");

        NegaMaxTiefe = new JTextField();
        NegaMaxTiefe.setText("5");
        NegaMaxTiefe.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        NegaMaxTiefe.setBackground(Color.white);
        NegaMaxTiefe.setForeground(Color.black);
        NegaMaxTiefe.setSize(100,50);
        NegaMaxTiefe.setBorder(BorderFactory.createEmptyBorder());
        Border border2 = NegaMaxTiefe.getBorder();
        Border margin2 = new LineBorder(Color.lightGray,1);
        NegaMaxTiefe.setBorder(new CompoundBorder(border2, margin2));

        reset = new JButton();
        reset.setBounds(xSize / 2, ySize / 5, 100, 100);
        reset.setText("Spiel zurücksetzen");
        reset.setForeground(Color.black);
        reset.addActionListener(actionEvent -> {
            Reset();
        });

        JLabel farbeSpielerEins = new JLabel();
        farbeSpielerEins.setText("↓↓↓ Farbe Spieler 1 (Kann nur am Anfang einer Runde geändert werden)");
        farbeSpielerEins.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        JLabel farbeSpielerZwei = new JLabel();
        farbeSpielerZwei.setText("↓↓↓ Farbe Spieler 2 (Kann nur am Anfang einer Runde geändert werden)");
        farbeSpielerZwei.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        Color[] Farben2 = {Color.red,Color.orange,Color.pink};
        String[]FarbenStr2 = {"Rot","Orange","Pink"};

        Color[] Farben = {Color.YELLOW,Color.GREEN,Color.cyan};
        String[]FarbenStr = {"Gelb","Grün","Cyan"};
        JComboBox FarbeSpieler1 = new JComboBox(FarbenStr);
        FarbeSpieler1.addActionListener(actionEvent -> {
            if(istSpielfeldLeer()) {
                int tmp = FarbeSpieler1.getSelectedIndex();
                farbeSpieler1 = Farben[tmp];
            }
        });
        JComboBox FarbeSpieler2 = new JComboBox(FarbenStr2);
        FarbeSpieler2.addActionListener(actionEvent -> {
            if(istSpielfeldLeer()) {
                int tmp1 = FarbeSpieler2.getSelectedIndex();
                farbeSpieler2 = Farben2[tmp1];
            }
        });

        settings_panel.add(jcb);
        settings_panel.add(jcb2);
        settings_panel.add(jcb3);
        settings_panel.add(NegaMaxTiefe);
        settings_panel.add(negamaxTiefeText);
        settings_panel.add(jcb4);
        settings_panel.add(reset);
        settings_panel.add(farbeSpielerEins);
        settings_panel.add(FarbeSpieler1);
        settings_panel.add(farbeSpielerZwei);
        settings_panel.add(FarbeSpieler2);
        this.add(settings_panel);


        for (int i = 0; i < 7; i++) {
            JButton jb = new JButton();
            jb.setBounds(100 * i + 125, 100, 100, 50);
            //jb.setText("<html>&nbsp;|<br>V</html>");#
            jb.setText("↓");
            jb.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            jb.setOpaque(false);
            jb.setContentAreaFilled(false);
            jb.setBorderPainted(false);

            final int lX = i;
            jb.addActionListener(actionEvent -> {
                //Ermittle Coin in der Y-Spalte
                int lY = 0;
                for (int j = 0; j < 6; j++) {
                    if (spielbrett[lX][j].isEmpty()) lY++;
                    else j = 7;
                }
                if (SpielerAnzeigeZug == 1 && lY > 0 && !start_animation) {
                    start_animation = true;
                    animationY = lY;
                    animationX = lX;
                    animationCoin.setPlayerID(1);
                    animationCoin.setColor(farbeSpieler1);
                    animationCoin.setX(spielbrett[lX][lY - 1].getX());
                    System.out.println(lX + "   " + (lY - 1));
                    letzterZugX = lX;
                    letzterZugY = lY - 1;
                    SpielerAnzeigeZug = 2;
                } else if (SpielerAnzeigeZug == 2 && lY > 0 && !start_animation) {
                    start_animation = true;
                    animationY = lY;
                    animationX = lX;
                    animationCoin.setPlayerID(2);
                    animationCoin.setColor(farbeSpieler2);
                    animationCoin.setX(spielbrett[lX][lY - 1].getX());
                    letzterZugX = lX;
                    letzterZugY = lY - 1;
                    SpielerAnzeigeZug = 1;
                }
                randomKIbutton = true;
                negamaxStop = true;

            });
            column_buttons[i] = jb;
            this.add(jb);
        }
    }


    public boolean ÜberprüfeObSpielerGewonnen(int x, int y, Coin[][] Spielbrett) {
        spielbrett = Spielbrett;
        int PlayerID = spielbrett[x][y].getPlayerID();
        int aktuellePosY = y;
        int aktuellePosX = x;
        if(PlayerID == 0){
            return false;
        }
        //Gewonnen durch 4 in vertikaler Lage
        if (aktuellePosY <= 2 && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY + 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY + 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY + 3].getPlayerID()) {
            return true;
        }
        //Gewonnen durch 4 in Horizontaler Lage
        if (aktuellePosX <= 3 && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 2][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 3][aktuellePosY].getPlayerID())
            return true;
        if (aktuellePosX <= 4 && aktuellePosX >= 1 && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 2][aktuellePosY].getPlayerID())
            return true;
        if (aktuellePosX <= 5 && aktuellePosX >= 2 && PlayerID == spielbrett[aktuellePosX - 2][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY].getPlayerID())
            return true;
        if (aktuellePosX >= 3 && PlayerID == spielbrett[aktuellePosX - 3][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 2][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID())
            return true;

        //Gewonnen durch 4 in diagonaler Lage nach unten rechts und oben links
        if (aktuellePosX <= 3 && aktuellePosY <= 2 && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY + 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 2][aktuellePosY + 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 3][aktuellePosY + 3].getPlayerID())
            return true;
        if (aktuellePosX <= 4 && aktuellePosX >= 1 && aktuellePosY <= 3 && aktuellePosY >= 1 && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY - 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY + 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 2][aktuellePosY + 2].getPlayerID())
            return true;
        if (aktuellePosX <= 5 && aktuellePosX >= 2 && aktuellePosY <= 4 && aktuellePosY >= 2 && PlayerID == spielbrett[aktuellePosX - 2][aktuellePosY - 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY - 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY + 1].getPlayerID())
            return true;
        if (aktuellePosX >= 3 && aktuellePosY >= 3 && PlayerID == spielbrett[aktuellePosX - 3][aktuellePosY - 3].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 2][aktuellePosY - 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY - 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID())
            return true;

        //Gewonnen durch 4 in diagonaler Lage nach unten links und oben rechts
        if (aktuellePosX >= 3 && aktuellePosY <= 2 && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY + 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 2][aktuellePosY + 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 3][aktuellePosY + 3].getPlayerID())
            return true;
        if (aktuellePosX >= 2 && aktuellePosX <= 5 && aktuellePosY <= 3 && aktuellePosY >= 1 && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY - 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY + 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 2][aktuellePosY + 2].getPlayerID())
            return true;
        if (aktuellePosX >= 1 && aktuellePosX <= 4 && aktuellePosY <= 4 && aktuellePosY >= 2 && PlayerID == spielbrett[aktuellePosX + 2][aktuellePosY - 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY - 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID() && PlayerID == spielbrett[aktuellePosX - 1][aktuellePosY + 1].getPlayerID())
            return true;
        if (aktuellePosX <= 3 && aktuellePosY >= 3 && PlayerID == spielbrett[aktuellePosX + 3][aktuellePosY - 3].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 2][aktuellePosY - 2].getPlayerID() && PlayerID == spielbrett[aktuellePosX + 1][aktuellePosY - 1].getPlayerID() && PlayerID == spielbrett[aktuellePosX][aktuellePosY].getPlayerID())
            return true;

        return false;
    }

    public boolean istSpielfeldLeer(){
        for(int i = 0; i<7;i++){
            if(spielbrett[i][5].getPlayerID()!=0){
                return false;
            }
        }
        return true;
    }
    public void Reset(){
        //System.out.println(xSize + "   -  " + ySize);
        gameStop = false;
        reset.setText("Spiel zurücksetzen");
        start_animation = false;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 6; j++) {
                    Color lColor = new Color(150, 150, 150);
                    spielbrett[i][j] = new Coin(0, lColor, i * 100 + 150, 200 + j * 100);
                }
            }
            SpielerAnzeigeZug = 1;
            Spieler1Gewonnen = false;
            Spieler2Gewonnen = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.blue);
        g.fillRect(100, 150, 750, 650);
        drawOvals(g);
        if (!gameStop) {
            if (start_animation) {
                drawanimatedCoin(g);
            }
        }
        paintNotification(g);
    }


    private void drawOvals(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                g2d.setPaint(spielbrett[i][j].getColor());
                g.fillOval(spielbrett[i][j].getX(), spielbrett[i][j].getY(), 50, 50);
            }
        }
    }

    private void drawanimatedCoin(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(animationCoin.getColor());

        if (setting_enable_animation && animationCoin.getY() < (animationY + 1) * 100 - 10) {
            animationCoin.setY(animationCoin.getY() + 10);
            g.fillOval(animationCoin.getX(), animationCoin.getY(), 50, 50);
        } else {
            start_animation = false;
            animationCoin.setY(100);
            spielbrett[animationX][animationY - 1].setColor(animationCoin.getColor());
            spielbrett[animationX][animationY - 1].setPlayerID(animationCoin.getPlayerID());
            //SpielbrettAusgabe();
            if (ÜberprüfeObSpielerGewonnen(animationX, animationY - 1, spielbrett)) {
                if (SpielerAnzeigeZug == 2) {
                    Spieler1Gewonnen = true;
                    gameStop = true;
                    reset.setText("Neues Spiel beginnen!");
                } else if (SpielerAnzeigeZug == 1) {
                    Spieler2Gewonnen = true;
                    gameStop = true;
                    reset.setText("Neues Spiel beginnen!");
                }

            }
            if (randomKI && randomKIbutton) {
                random();
                randomKIbutton = false;
            }
            if (negamax && negamaxStop) {
                NegaM();
            }
            //evaluate(spielbrett);
        }

    }

    private void paintNotification(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.black);
        if (Spieler1Gewonnen)
            g.drawString("Spieler 1 hat gewonnen", (int) Math.round(xSize/1.65), ySize / 3);
        else if (Spieler2Gewonnen)
            g.drawString("Spieler 2 hat gewonnen", (int) Math.round(xSize/1.65), ySize / 3);
        if (!Spieler1Gewonnen && !Spieler2Gewonnen)
            g.drawString("Spieler " + SpielerAnzeigeZug + " ist am Zug", (int) Math.round(xSize/1.65), ySize / 2 - 100);
    }

    private void setTimer() {
        Timer timer = new Timer(20, arg0 -> repaint());
        timer.start();
    }

    //Algorithmen

    //Zufälliger Einwurf

    boolean randomKI = false;
    boolean randomKIbutton = false; //Sorgt dafür, dass erst nach dem Ende der Animation, die RandomKI ausgeführt wird

    // Die Methode random() sorgt dafür, dass in ein zufällige Spalte, ein Spielstein hineingeworfen wird und somit einen NPC wiederspiegelt, der zufällige Entscheidungen trifft
    public void random() {
        Random rdm = new Random();
        int lx = rdm.nextInt(7);
        boolean whileAn = false;
        while (whileAn == false) {
            int ly = 0;
            ly = 6 - gibAnzahlCoinsInSpalte(spielbrett,lx);
            if (ly > 0) {
                start_animation = true;
                animationY = ly;
                animationX = lx;
                if (SpielerAnzeigeZug == 1) {
                    animationCoin.setColor(farbeSpieler1);
                    animationCoin.setPlayerID(1);
                } else {
                    animationCoin.setColor(farbeSpieler2);
                    animationCoin.setPlayerID(2);
                }
                animationCoin.setX(spielbrett[lx][ly - 1].getX());
                if (SpielerAnzeigeZug == 2)
                    SpielerAnzeigeZug = 1;
                else
                    SpielerAnzeigeZug = 2;
                whileAn = true;
            }
            if (whileAn == false) {
                lx = rdm.nextInt(7);
            }
        }

    }






    //Methoden zur Visualisierung

    public void SpielbrettAusgabe() {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                System.out.print(spielbrett[i][j].getPlayerID() + " ");
            }
            System.out.print("\n");
        }
    }
    public void SpielbrettAusgabe(Coin[][] sp) {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                System.out.print(sp[i][j].getPlayerID() + " ");
            }
            System.out.print("\n");
        }
    }

    //Negamax Alogrithmus
    Move negaMove;
    int Tiefe;
    public void NegaM() {
        int tiefe = 1; boolean reset = false;
        try {
            tiefe = Integer.parseInt(NegaMaxTiefe.getText());
            Tiefe = tiefe;
        }
        catch (Exception e){
            negamaxTiefeText.setText("Dies ist keine richtige Zahl!!");
            reset = true;
        }

        tmpSpilerAmZug = SpielerAnzeigeZug;

        negaMove = new Move (spielbrett,-1,-1,0);
        Negamax(new Spielzug(spielbrett, animationX, animationY-1,2),tiefe,Integer.MIN_VALUE,Integer.MAX_VALUE);

        if(negaMove.gibX() > -1) {
            start_animation = true;
            animationY = negaMove.gibY() + 1;
            animationX = negaMove.gibX();
            animationCoin.setColor(farbeSpieler2);
            animationCoin.setPlayerID(2);
            animationCoin.setX(spielbrett[negaMove.gibX()][negaMove.gibY()].getX());
        }
        negamaxStop = false;
        if (SpielerAnzeigeZug == 1) {
            SpielerAnzeigeZug = 2;
        } else {
            SpielerAnzeigeZug = 1;
        }
        if(reset){
            Reset();
        }
    }
    public int Negamax(Spielzug sz, int depth,int alpha, int beta) {
        int best = Integer.MIN_VALUE;
        if (ÜberprüfeObSpielerGewonnen(sz.gibX(), sz.gibY(), sz.gibSpielfeld()) || depth <= 0) {
            //Zug wird bewertet
            return evaluate(sz.gibSpielfeld(),sz.gibId());
        }

        //Hier werden die verschiedenen Züge generiert
        Move[] moves = generateMoves(sz.gibSpielfeld());
        if(sz.gibId()==1)
            sz.setzeSpielerID(2);
        else
            sz.setzeSpielerID(1);

        int[] Werte = new int[moves.length];
        for (int i = 0; i < moves.length; i++) {
            if (moves[i] != null) {
                //Zug wird ausgeführt
                sz.setzeSpielfeld(makeMove(sz.gibSpielfeld(), moves[i], sz.gibId()));
                //rekursiver Aufruf
                int value = -Negamax(new Spielzug(sz.gibSpielfeld(),moves[i].gibX(), moves[i].gibY(),sz.gibId()),depth-1,-alpha,-beta);
                Werte[i] = value;
                //Zug wird zurückgezogen
                sz.setzeSpielfeld(undoMove(sz.gibSpielfeld(), moves[i]));
                //Suche des besten Wertes eines Zuges einer Spielstellung
                if(value>best){
                    best = value;
                    if(depth == Tiefe) {
                        negaMove = moves[i];
                    }
                }
            }
        }
        //Visualisierung
        if(depth== Tiefe) {
            System.out.println("Die Werte lauten: ");
            for (int i = 0; i < moves.length; i++) {
                System.out.print(Werte[i] + " ");
            }
        }
        return best;
    }

    public Coin[][] makeMove(Coin[][] Sp, Move mv,int SpielerID) {
        if (mv != null) {
            //System.out.println(mv.gibX() + " # " + mv.gibY());
            Sp[mv.gibX()][mv.gibY()]
                    .setPlayerID(SpielerID);
            letzterZugX = mv.gibX();
            letzterZugY = mv.gibY();
            mv.setzeSpielbrett(Sp);
        }
        return Sp;

    }

    public Coin[][] undoMove(Coin[][] Sp, Move mv) {
        if (mv != null) {
            Sp[mv.gibX()][mv.gibY()].setPlayerID(0);
        }
        return Sp;
    }

    public Move[] generateMoves(Coin[][] Spielbrett) {
        Move[] moveT = new Move[7];
        for (int u = 0; u < 7; u++) {
            if (!istSpalteVoll(Spielbrett, u)) {
                moveT[u] = new Move(Spielbrett, u, 5 - gibAnzahlCoinsInSpalte(Spielbrett, u), 0);
            }
        }
        return moveT;
    }

    public boolean istSpalteVoll(Coin[][] Spielbrett, int pX) {
        return Spielbrett[pX][0].getPlayerID() != 0;
    }

    public int gibAnzahlCoinsInSpalte(Coin[][] Spielbrett, int pX) {
        int tmp = 0;
        for (int i = 0; i < 6; i++) {
            if (Spielbrett[pX][i].getPlayerID() != 0)
                tmp++;
        }
        return tmp;
    }

    public int evaluate(Coin[][] Sp,int ID) {
        int PunkteMensch = 0;
        int PunkteKi = 0;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j<7; j++) {
                if(Sp[j][i].getPlayerID()!=0) {
                    if(Sp[j][i].getPlayerID()==2) {
                        int eW = EvaluateWaagerecht(Sp, 2, j, i);
                        int eV = EvaluateDiagonal(Sp, 2, j, i);
                        int eS = EvaluateSenkrecht(Sp, 2, j);
                        //System.out.println(eW + " " + eV + " " + eS + "KI");
                        if (eW <= 1 && eV <= 1 && eS <= 1) {
                            PunkteKi +=1;
                        }
                        else if(eW <= 2 && eV <= 2 && eS <= 2){
                            PunkteKi += 5;
                        }
                        else if(eW <= 3 && eV <= 3 && eS <= 3){
                            PunkteKi += 150;
                        }
                        else if(eW <= 4 && eV <= 4 && eS <= 4){
                            PunkteKi += 1000 ;
                        }

                    }
                    else{
                        int eW = EvaluateWaagerecht(Sp, 1, j, i);
                        int eV = EvaluateDiagonal(Sp, 1, j, i);
                        int eS = EvaluateSenkrecht(Sp, 1, j);
                        //System.out.println(eW + " " + eV + " " + eS + "MENSCH");
                        if (eW <= 1 && eV <= 1 && eS <= 1) {
                            PunkteMensch +=1;
                        }
                        else if(eW <= 2 && eV <= 2 && eS <= 2){
                            PunkteMensch += 5;
                        }
                        else if(eW <= 3 && eV <= 3 && eS <= 3){
                            PunkteMensch += 150;
                        }
                        else if(eW <= 4 && eV <= 4 && eS <= 4){
                            PunkteMensch += 997;
                        }
                    }
                }
            }
        }
        if(ID==2){
            PunkteKi = (int)Math.round(PunkteKi * 1.1);
            return PunkteMensch - PunkteKi;
        }
        else{
            PunkteMensch = (int)Math.round(PunkteMensch * 1.1);
            return PunkteKi - PunkteMensch;
        }



    }

    public int EvaluateWaagerecht(Coin[][] Spielbrett, int Spielerid, int pX, int pY) {
        int Punkte = -1;
        boolean stop1 = true;
        boolean stop2 = true;

        for(int i = 0; i<7; i++){
            int pXtmp = pX -i;
            int pXtmp2 = pX+i;
            if(pXtmp>=0){
                if(Spielerid == Spielbrett[pXtmp][pY].getPlayerID() && stop1){
                    Punkte++;
                }
                else{
                    stop1 = false;
                }
            }
            if(pXtmp2 <=6) {
                if (Spielerid == Spielbrett[pXtmp2][pY].getPlayerID() && stop2) {
                    Punkte++;
                } else {
                    stop2 = false;
                }
            }
        }
        return Punkte;
    }

    public int EvaluateSenkrecht(Coin[][] Spielbrett, int Spielerid, int pX) {
        int Punkte = 0;
        int tmp = 6-gibAnzahlCoinsInSpalte(Spielbrett, pX);
        //System.out.println(tmp +"lä");
        if(tmp < 2){
            return 0;
        }
        for (int j = tmp; j <6; j++) {
            int tmpS = Spielbrett[pX][j].getPlayerID();
            if (tmpS == Spielerid) {
                Punkte++;
            } else {
                //System.out.println(Punkte);
                return Punkte;
            }
        }
        //System.out.println(Punkte);
        return Punkte;

    }
    public int EvaluateDiagonal(Coin[][] Spielbrett, int Spielerid, int pX,int pY) {
        int Punkte = -1;
        int Punkte2 = -1;
        boolean stop1 = true;
        boolean stop2 = true;
        boolean stop3 = true;
        boolean stop4 = true;
        for (int i = 0; i < 7; i++) {
            int pXtmp = pX - i;
            int pXtmp2 = pX + i;
            int pYtmp = pY - i;
            int pYtmp2 = pY + i;
            if (pXtmp >= 0 && pYtmp >= 0) {
                if (stop1 && Spielerid == Spielbrett[pXtmp][pYtmp].getPlayerID()) {
                    Punkte++;
                } else {
                    stop1 = false;
                }
            }
            if (pXtmp2 <= 6 && pYtmp >= 0) {
                if (stop2 && Spielerid == Spielbrett[pXtmp2][pYtmp].getPlayerID()) {
                    Punkte2++;
                } else {
                    stop2 = false;
                }
            }
            if (pXtmp >= 0 && pYtmp2 <= 5) {
                if (stop3 && Spielerid == Spielbrett[pXtmp][pYtmp2].getPlayerID()) {
                    Punkte2++;
                } else {
                    stop3 = false;
                }
            }
            if (pXtmp2 <= 6 && pYtmp2 <= 5) {
                if (stop4 && Spielerid == Spielbrett[pXtmp2][pYtmp2].getPlayerID()) {
                    Punkte++;
                } else {
                    stop4 = false;
                }
            }
        }
        if(Punkte>Punkte2)
            return Punkte;
        else
            return Punkte2;
    }
}

