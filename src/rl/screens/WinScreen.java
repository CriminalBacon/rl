package rl.screens;

import asciiPanel.AsciiPanel;
import rl.screens.PlayScreen;
import rl.screens.Screen;

import java.awt.event.KeyEvent;

public class WinScreen implements Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won!", 1, 1);
        terminal.writeCenter("-- press [Enter] to restart --", 22);
    } //displayOutput

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    } //respondToUserInput

} //class WinScreen
