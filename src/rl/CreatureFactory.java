package rl;


import asciiPanel.AsciiPanel;
import rl.screens.BatAi;

import java.util.List;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    } //CreatureFactory


    public Creature newPlayer(List<String> messages, FieldOfView fov){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;

    } //newPlayer

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


} // class CreatureFactory
