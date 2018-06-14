package rl;

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import rl.screens.Screen;
import rl.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private Screen screen;


    public ApplicationMain(){
        super();
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();

    } //rl.ApplicationMain




    public static void main(String[] args){

        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);



    } //main

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();

    } //repaint


    @Override
    public void keyTyped(KeyEvent e) {

    } //keyTyped

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    } //keyPressed

    @Override
    public void keyReleased(KeyEvent e) {

    } //keyReleased


} //rl.ApplicationMain