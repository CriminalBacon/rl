package rl.screens;


import asciiPanel.AsciiPanel;
import rl.Creature;
import rl.CreatureFactory;
import rl.World;
import rl.WorldBuilder;

import java.awt.event.KeyEvent;


public class PlayScreen implements Screen {


    private World world;
    private int screenWidth;
    private int screenHeight;
    private Creature player;


    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        createWorld();

        CreatureFactory creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();

    } //PlayScreen

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write(player.getGlyph(), player.x - left, player.y - top, player.getColor());


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
                player.moveBy(-1, 0);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.moveBy(1, 0);
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.moveBy(0, -1);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.moveBy(0, 1);
                break;

            case KeyEvent.VK_Y:
                player.moveBy(-1, -1);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1);
                break;


        } //switch

        return this;

    } //respondToUserInput

    private void createWorld() {
        world = new WorldBuilder(90, 31)
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

    //displays tiles using the left and top to know which section of the world to display
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            } //for y

        } //for x

    } //displayTiles

    //makes sure we never scoll out of bounds
//    private void scrollBy(int mx, int my){
//        centerX = Math.max(0, Math.min(centerX + mx, world.getWidth() - 1));
//        centerY = Math.max(0, Math.min(centerY + my, world.getHeight() - 1));
//    } //scrollBy


} //class PlayScreen
