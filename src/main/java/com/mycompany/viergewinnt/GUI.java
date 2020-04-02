/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.viergewinnt;
import javax.swing.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
public class GUI extends JFrame {
    boolean Vollbild = false;
    public GUI() {
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(null);
        if(PanelClass.vollbild==true) {
            setUndecorated(true);
            Vollbild=true;
        }
        setBackground(Color.blue);
        setVisible(true);
        //JPanel
        JPanel jp = new PanelClass();
        setContentPane(jp);
        if(Vollbild==true)
            PanelClass.vollbild = true;
    }
}
