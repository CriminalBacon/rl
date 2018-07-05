package rl.screens;

import asciiPanel.AsciiPanel;
import rl.Point;
import rl.Tile;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DisplayLookScreen implements Screen {

    private List<String> things;

    public DisplayLookScreen(List<String> things){
        this.things = things;
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("You see: ", 2, 23);

        String pointString = "";
        for (String thing : things){
            if (thing != null) {
                pointString += thing + " ";

            }
        }
        terminal.write(pointString, 11, 23);

    } //displayOutput

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ESCAPE
                || key.getKeyCode() == KeyEvent.VK_M ) {
            return null;
        } else {
            return this;
        }
    } //respondToUserInput

} //DisplayLookScreen class
