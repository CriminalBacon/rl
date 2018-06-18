package rl;

public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    } //CreatureAi


    public void onEnter(int x, int y, int z, Tile tile){

    } //onEnter

    public void onUpdate(){

    } //onUpdate

    public void onNotify(String format) {

    } //onNotify

    public boolean canSee(int wx, int wy, int wz) {
        if (creature.z != wz){
            return false;
        } //if

        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.getVisionRadius()*creature.getVisionRadius()){
            return false;
        }

        for (Point p : new Line(creature.x, creature.y, wx, wy)){
            if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy){
                continue;

            } //if

            return false;
        } //for

        return true;

    } //canSee
} //class CreatureAi
