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

    public Item newDagger(int depth){
        Item item = new Item(')', AsciiPanel.white, "dagger");
        item.modifyAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;

    } //newDagger

    public Item newSword(int depth){
        Item item = new Item(')', AsciiPanel.white, "sword");
        item.modifyAttackValue(10);
        world.addAtEmptyLocation(item, depth);
        return item;

    } //newSword

    public Item newStaff(int depth){
        Item item = new Item(')', AsciiPanel.white, "staff");
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;

    } //newStaff

    public Item newLightArmor(int depth){
        Item item = new Item('[', AsciiPanel.green, "tunic");
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item, depth);
        return item;
    } //newLightArmor

    public Item newMediumArmor(int depth){
        Item item = new Item('[', AsciiPanel.green, "chainmail");
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item, depth);
        return item;

    } //newMediumArmor

    public Item newHeavyArmor(int depth){
        Item item = new Item('[', AsciiPanel.green, "platemail");
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item, depth);
        return item;

    } //newHeavyArmor

    public Item randomWeapon(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newDagger(depth);
            case 1:
                return newSword(depth);
            default:
                return newStaff(depth);
        } //switch

    } //randomWeapon

    public Item randomArmor(int depth){
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newLightArmor(depth);
            case 1:
                return newSword(depth);
            default:
                return newHeavyArmor(depth);
        } //switch


    } //randomArmor






} // class ThingFactory
