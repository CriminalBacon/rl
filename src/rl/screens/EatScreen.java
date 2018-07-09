package rl.screens;

import rl.Creature.Creature;
import rl.Item;

public class EatScreen extends InventoryBasedScreen{

    public EatScreen(Creature player){
        super(player);
    } //EatScreen


    @Override
    protected String getVerb() {
        return "eat";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.getFoodValue() != 0;
    }

    @Override
    protected Screen use(Item item) {
        player.eat(item);
        return null;
    } //use

} //EatScreen class
