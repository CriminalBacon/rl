package rl;


import asciiPanel.AsciiPanel;
import rl.screens.BatAi;

import java.util.List;

public class ThingFactory {
    private World world;

    public ThingFactory(World world){
        this.world = world;
    } //ThingFactory


    public Creature newPlayer(List<String> messages, FieldOfView fov){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;

    } //newPlayer

    ///////////////////////////// CREATURES /////////////////////////////

    public Creature newFungus(int depth){
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        fungus.setName("fungus");
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);

        return fungus;
    } //newFungus

    public Creature newRat(int depth){
        Creature rat = new Creature(world, 'r', AsciiPanel.brightMagenta, 8, 2, 0);
        rat.setName("rat");
        world.addAtEmptyLocation(rat, depth);
        new RatAi(rat, this);
        return rat;
    } //newRat

    public Creature newBat(int depth){
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 15, 5, 0);
        bat.setName("bat");
        world.addAtEmptyLocation(bat, depth);
        new BatAi(bat);
        return bat;
    } //newBat


    ///////////////////////////// ITEMS /////////////////////////////

    public Item newRock(int depth){
        Item rock = new Item(',', AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }  //newRock

    public Item newVictoryItem(int depth){
        Item item = new Item('*', AsciiPanel.brightWhite, "teddy bear");
        world.addAtEmptyLocation(item, depth);
        return item;
    } //newVictoryItem


} // class ThingFactory
