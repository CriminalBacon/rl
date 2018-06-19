package rl.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class MessageScreen implements Screen {

    private List<String> messagesArchive;

    public MessageScreen(List<String> messages){
        this.messagesArchive = new ArrayList<String>();
        this.messagesArchive = messages;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int y = 22 - messagesArchive.size();

        int x = 4;
        List<String> paddedMessageArchive = MessageUtils.padMessages(messagesArchive);

        if (messagesArchive.size() > 0){
            terminal.clear(' ', x, y, 20, messagesArchive.size());
        }

        terminal.write(MessageUtils.createBoarder(paddedMessageArchive), x, y++);

        for (String message : paddedMessageArchive){
            terminal.write(message, x, y++);
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.repaint();

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (key.getKeyCode() == KeyEvent.VK_ESCAPE
                || key.getKeyCode() == KeyEvent.VK_M ) {
            return null;
        } else {
            return this;
        }
    }


} //MessageScreen class
