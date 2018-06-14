package rl.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import rl.screens.PlayScreen;
import rl.screens.Screen;

public class StartScreen implements Screen {


    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("rl tutorial", 1, 1);
        terminal.writeCenter("-- press [Enter] to start --", 22);
    } //displayOutput

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    } //respondToUserInput
} //class StartScreen


