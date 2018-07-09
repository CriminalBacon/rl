package rl.screens;

import asciiPanel.AsciiPanel;
import rl.*;
import rl.Creature.Creature;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class LookScreen implements Screen {

    private Creature player;
    private World world;
    private List<String> itemsInView;


    public LookScreen(Creature player, World world){
        this.player = player;
        this.world = world;
        itemsInView = new ArrayList<String>();
    } //LookScreen

    @Override
    public void displayOutput(AsciiPanel terminal) {

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("Look in what direction?", 2, 23);


    } //displayOutput

    @Override
    public Screen respondToUserInput(KeyEvent key) {


        if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
            return null;
        } else if (key.getKeyCode() == KeyEvent.VK_UP){
            List<Point> points = player.lookInDirection("up");
            return new DisplayLookScreen(creaturesInDirection(points));

        } else if (key.getKeyCode() == KeyEvent.VK_DOWN){
            List<Point> points = player.lookInDirection("down");
            return new DisplayLookScreen(creaturesInDirection(points));

        } else if (key.getKeyCode() == KeyEvent.VK_RIGHT){
            List<Point> points = player.lookInDirection("right");
            return new DisplayLookScreen(creaturesInDirection(points));

        } else if (key.getKeyCode() == KeyEvent.VK_LEFT){
            List<Point> points = player.lookInDirection("left");
            return new DisplayLookScreen(creaturesInDirection(points));

        }
        return this;


    } //respondToUserInput

    //returns the creatures in the List<Point> as a List<String>
    private List<String> creaturesInDirection(List<Point> points){
        List<String> things = new ArrayList<String>();

        for (Point point : points){
            if ((world.creature(point.x, point.y, player.z) != null)){
                things.add(world.creature(point.x, point.y, player.z).getName());
            }
            if ((world.item(point.x, point.y, player.z) != null)){
                things.add(world.item(point.x, point.y, player.z).getName());
            }

        } //for

        return things;
    } //creaturesInDirection



} //LookScreen class
