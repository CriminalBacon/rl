package rl.screens;

import rl.Creature;
import rl.Item;

public class DropScreen extends InventoryBasedScreen {


    public DropScreen(Creature player){
        super(player);
    } //Drop Screen

    @Override
    protected String getVerb() {
        return "drop";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
} //DropScreen class
