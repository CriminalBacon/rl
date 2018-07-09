package rl.screens;

import rl.Creature.Creature;
import rl.Item;

public class EquipScreen extends InventoryBasedScreen{

    public EquipScreen(Creature player){
        super(player);
    } //EquipScreen


    @Override
    protected String getVerb() {
        return "wear or wield";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.getAttackValue() > 0 || item.getDefenseValue() > 0;
    }

    @Override
    protected Screen use(Item item) {
        player.equip(item);
        return null;
    }


} //EquipScreen class
