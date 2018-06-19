package rl;

import java.awt.*;

public class RatAi extends CreatureAi {


    public RatAi(Creature creature, CreatureFactory creatureFactory) {
        super(creature);
    } //RatAi

    @Override
    public void onUpdate() {
        attackPlayerWithMelee();

    } //onUpdate


} //class RatAi
