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
    private Screen subscreen;
    private List<String> archiveMessages;
    ;

    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 23;
        messages = new ArrayList<String>();
        archiveMessages = new ArrayList<String>();
        createWorld();
        fov = new FieldOfView(world);
        ThingFactory thingFactory = new ThingFactory(world);
        createCreatures(thingFactory);
        createItems(thingFactory);


    } //PlayScreen

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write(player.getGlyph(), player.x - left, player.y - top, player.getColor());

        String stats = String.format(" %3d/%3d hp %8s", player.getHp(), player.getMaxHp(), hunger());
        terminal.write(stats, 1, 23);

        String pos = String.format(" %2d, %2d, %2d", player.x, player.y, player.z);
        terminal.write(pos, 40, 23);

        displayMessages(terminal, messages);

        if(subscreen != null){
            subscreen.displayOutput(terminal);
        } //if

    } //displayOutput

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (subscreen != null){
            subscreen = subscreen.respondToUserInput(key);
        } else {

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
                case KeyEvent.VK_D:
                    subscreen = new DropScreen(player);
                    break;
                case KeyEvent.VK_M:
                    subscreen = new MessageScreen(archiveMessages);
                    break;
                case KeyEvent.VK_E:
                    subscreen = new EatScreen(player);
                    break;
                case KeyEvent.VK_W:
                    subscreen = new EquipScreen(player);
                    break;
                case KeyEvent.VK_0:
                    player.setGod(!player.isGod());
                    break;
            } //switch

            switch (key.getKeyChar()) {
                case '<':
                    if (userIsTryingToEXit()){
                        return userExits();
                    } else {
                        player.moveBy(0, 0, -1);
                    }
                    break;
                case '>':
                    player.moveBy(0, 0, 1);
                    break;
                case '.':
                    player.moveBy(0, 0, 0);
                    break;
                case 'g':
                    player.pickup();
                    break;
            } //switch

        } //if

        if (subscreen == null) {
            world.update();
        } //if

        if (player.getHp() < 1){
            return new LoseScreen();
        } //if

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
    private void createCreatures(ThingFactory thingFactory) {
        player = thingFactory.newPlayer(messages, fov);


        for (int z = 0; z < world.getDepth(); z++) {

            //fungus generator
            for (int i = 0; i < 8; i++) {
                thingFactory.newFungus(z);

            } //for

            //rat generator
            for (int i = 0; i < 10; i++){
                thingFactory.newRat(z);
            }


            //bat generator
            for (int i = 0; i < 20; i++){
                thingFactory.newBat(z);
            } //for

        }
    } //createCreatures

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.writeCenter(messages.get(i), top + i);
        } //for

        archiveMessages(messages);
        messages.clear();
    } //displayMessages

    private void createItems(ThingFactory factory){
        for (int z = 0; z < world.getDepth(); z++){

            // rock factory
            for (int i = 0; i < world.getWidth() * world.getHeight() / 20; i++){
                factory.newRock(z);
            } //for

            //weapons factory
            for (int i = 0; i < 2; i++){
                factory.randomWeapon(z);
            } //for

            //armor factory
            for (int i = 0; i < 2; i++){
                factory.randomArmor(z);
            }

        } //for

        factory.newVictoryItem(0);


    } //createItems

    private void archiveMessages(List<String> messages){
        //only keeps 23 messages in archive
        if (archiveMessages.size() + messages.size() >= 23){
            for (int i = 0; i <= messages.size(); i++){
                archiveMessages.remove(i);
            } //for
        } //if

        archiveMessages.addAll(messages);


    } //archiveMessages

    private boolean userIsTryingToEXit(){
        return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
    } //userIsTryingToExit

    private Screen userExits(){
        for (Item item : player.getInventory().getItems()){
            if (item != null && item.getName().equals("teddy bear")){
                return new WinScreen();
            } //if

        }
        return new LoseScreen();
    } //userExits

    private String hunger(){
        if(player.getFood() < player.getMaxFood() * 0.1){
            return "Starving";
        } else if (player.getFood() < player.getMaxFood() * 0.2){
            return "Hungry";
        } else if (player.getFood() < player.getMaxFood() * 0.9){
            return "Stuffed";
        } else if (player.getFood() < player.getMaxFood() * 0.8) {
            return "Full";
        } else {
            return "";
        }

    } //hunger


} //class PlayScreen
