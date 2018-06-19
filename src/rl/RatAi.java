package rl;

public class RatAi extends CreatureAi {


    public RatAi(Creature creature, ThingFactory thingFactory) {
        super(creature);
    } //RatAi

    @Override
    public void onUpdate() {
        attackPlayerWithMelee();

    } //onUpdate


} //class RatAi
