package rl;


import asciiPanel.AsciiPanel;

import java.util.List;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    } //CreatureFactory


    public Creature newPlayer(List<String> messages){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
        world.addAtEmptyLocation(player);
        new PlayerAi(player, messages);
        return player;

    } //newPlayer

    public Creature newFungus(){
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus, this);
        return fungus;
    } //Creature

    public Creature newRat(){
        Creature rat = new Creature(world, 'r', AsciiPanel.brightMagenta, 8, 2, 0);
        world.addAtEmptyLocation(rat);
        new RatAi(rat);
        return rat;
    }



} // class CreatureFactory
