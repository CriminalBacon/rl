package rl.screens;

import asciiPanel.AsciiPanel;
import rl.Creature;
import rl.Item;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class InventoryBasedScreen implements Screen{

    protected Creature player;
    private String letters;


    protected abstract String getVerb();
    protected abstract boolean isAcceptable(Item item);
    protected abstract Screen use(Item item);


    public InventoryBasedScreen(Creature player){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";

    } //InventoryBasedScreen


    public void displayOutput(AsciiPanel terminal){
        ArrayList<String> lines = MessageUtils.padMessages(getList());

        int y = 22 - lines.size();
        int x = 4;

        if (lines.size() > 0){
            terminal.clear(' ', x, y, 20, lines.size());
        } //if

        terminal.write(MessageUtils.createBoarder(lines), x, y++);

        for (String line : lines){
            terminal.write(line, x, y++);
        } //for

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("What would you like to " + getVerb() + "?", 2, 23);

        terminal.repaint();


    } //displayOutput

    private ArrayList<String> getList(){
        ArrayList<String> lines = new ArrayList<String>();
        Item[] inventory = player.getInventory().getItems();

        for (int i = 0; i < inventory.length; i++){
            Item item = inventory[i];

            if (item == null || !isAcceptable(item)){
                continue;
            } //if

            String line = letters.charAt(i) + " - " + item.getGlyph() + " " + item.getName();

            lines.add(line);

        } //for

        return lines;

    } //getList


    public Screen respondToUserInput(KeyEvent key){
        char c = key.getKeyChar();

        Item[] items = player.getInventory().getItems();

        if (letters.indexOf(c) > -1
                && items.length > letters.indexOf(c)
                && items[letters.indexOf(c)] != null
                && isAcceptable(items[letters.indexOf(c)])){
            return use(items[letters.indexOf(c)]);
        } else if (key.getKeyCode() == KeyEvent.VK_ESCAPE){
            return null;
        } else {
            return this;
        } //else

    } //respondToUserInput

} //InventoryBasedScreen class
