package rl.Creature;

import rl.Creature.Creature;
import rl.Creature.CreatureAi;
import rl.Path;
import rl.Point;

import java.util.List;

public class ZombieAi extends CreatureAi {

    private Creature player;

    public ZombieAi(Creature creature, Creature player){
        super(creature);
        this.player = player;
    } //ZombieAi

    public void onUpdate(){
        if (Math.random() < 0.2){
            return;
        } //if

        if (creature.canSee(player.x, player.y, player.z)){
            hunt(player);
        } else {
            wander();
        }

    } //onUpdate

    public void hunt(Creature target){
        List<Point> points = new Path(creature, target.x, target.y).getPoints();

        int mx = points.get(0).x - creature.x;
        int my = points.get(0).y - creature.y;

        creature.moveBy(mx, my, 0);

    } //hunt


} //ZombieAi class
