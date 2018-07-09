package rl.Creature;


public class BatAi extends CreatureAi {


    public BatAi(Creature creature) {
        super(creature);
    } //BatAi

    public void onUpdate(){
        wander();
        wander();
    } //onUpdate

} // class BatAi
