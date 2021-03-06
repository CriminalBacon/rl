package rl.Creature;

import rl.Creature.Creature;
import rl.Creature.CreatureAi;
import rl.ThingFactory;

public class FungusAi extends CreatureAi {
    private ThingFactory factory;
    private int spreadCount;

    public FungusAi(Creature creature, ThingFactory factory) {
        super(creature);
        this.factory = factory;
    } //FungusAi

    public void onUpdate(){
        if (spreadCount < 5 && Math.random() < 0.01){
            spread();
        } //if
    } //onUpdate


    // spreads the fungus
    private void spread(){
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;

        if (!creature.canEnter(x, y, creature.z)){
            return;
        }

        Creature child = factory.newFungus(creature.z);
        child.x = x;
        child.y = y;
        child.z = creature.z;
        creature.doAction("spawn child");
        spreadCount++;
    } //spread

} //class FungusAi
