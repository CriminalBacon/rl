package rl.screens;


import asciiPanel.AsciiPanel;
import rl.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class PlayScreen implements Screen {


    private World world;
    private int screenWidth;
    private int screenHeight;
    private Creature player;
    private List<String> messages;
    private FieldOfView fov;


    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 23;
        messages = new ArrayList<String>();
        createWorld();
        fov = new FieldOfView(world);

        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);


    } //PlayScreen

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write(player.getGlyph(), player.x - left, player.y - top, player.getColor());

        String stats = String.format(" %3d/%3d hp", player.getHp(), player.getMaxHp());
        terminal.write(stats, 1, 23);

        String pos = String.format(" %2d, %2d, %2d", player.x, player.y, player.z);
        terminal.write(pos, 40, 23);

        displayMessages(terminal, messages);

    } //displayOutput

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return new LoseScreen();
            case KeyEvent.VK_ENTER:
                return new WinScreen();

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                player.moveBy(-1, 0, 0);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.moveBy(1, 0, 0);
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.moveBy(0, -1, 0);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.moveBy(0, 1, 0);
                break;

            case KeyEvent.VK_Y:
                player.moveBy(-1, -1, 0);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1, 0);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1, 0);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1, 0);
                break;


        } //switch

        switch (key.getKeyChar()) {
            case '<':
                player.moveBy(0, 0, -1);
                break;
            case '>':
                player.moveBy(0, 0, 1);
        } //switch

        world.update();

        return this;

    } //respondToUserInput

    private void createWorld() {
        world = new WorldBuilder(90, 32, 5)
                .makeCaves()
                .build();

    } //createWorld


    //calculates how far along the X-axis we can scroll
    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));

    } //getScrollX

    //calculates how far along the Y-axis we can scroll
    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));

    } //getScrollY


    //show only the monsters and tiles that can be seen.  Tiles are grey outside the line of sight
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        fov.update(player.x, player.y, player.z, player.getVisionRadius());

        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                if (player.canSee(wx, wy, player.z)) {
                    terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
                } else {
                    terminal.write(fov.tile(wx, wy, player.z).getGlyph(), x, y, Color.darkGray);
                } //else
            }
        }
    }

    //populates world with player and creatures
    private void createCreatures(CreatureFactory creatureFactory) {
        player = creatureFactory.newPlayer(messages, fov);

        for (int z = 0; z < world.getDepth(); z++) {
            for (int i = 0; i < 8; i++) {
                creatureFactory.newFungus(z);
                creatureFactory.newRat(z);
            } //for
        }
    } //createCreatures

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        } //for

        messages.clear();
    } //displayMessages

} //class PlayScreen
