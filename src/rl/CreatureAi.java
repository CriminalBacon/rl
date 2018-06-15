package rl;

public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    } //CreatureAi


    public void onEnter(int x, int y, Tile tile){

    } //onEnter

    public void onUpdate(){

    } //onUpdate

    public void onNotify(String format) {

    } //onNotify
} //class CreatureAi
