package rl;

import java.util.ArrayList;

import java.util.List;

public class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    } //CreatureAi


    public void onEnter(int x, int y, int z, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
            creature.z = z;
        } else {
            creature.doAction("bump into a wall");

        } //else

    } //onEnter

    public void onUpdate() {

    } //onUpdate

    public void onNotify(String format) {

    } //onNotify

    public boolean canSee(int wx, int wy, int wz) {
        if (creature.z != wz) {
            return false;
        } //if

        if ((creature.x - wx) * (creature.x - wx) + (creature.y - wy) * (creature.y - wy) > creature.getVisionRadius() * creature.getVisionRadius()) {
            return false;
        }

        for (Point p : new Line(creature.x, creature.y, wx, wy)) {
            if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy) {
                continue;

            } //if

            return false;
        } //for

        return true;

    } //canSee

    public void wander() {
        int mx = (int) (Math.random() * 3) - 1;
        int my = (int) (Math.random() * 3) - 1;

        Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
        if (other != null && other.getGlyph() == '@') {
            return;
        } else {
            creature.moveBy(mx, my, 0);
        } // else
    } //wander

    public void attackPlayerWithMelee() {
        int vision = creature.getVisionRadius();

        //Line lineOfSight = new Line(creature.x, creature.y, creature.x + vision, creature.y + vision);
        List<Point> points = getPointsInView(creature.x, creature.y, vision);

        for (Point point : points){
            Creature player = creature.creature(point.x, point.y, point.z);
            if (creature.canSee(point.x, point.y, point.z) && player != null && player.getGlyph() == '@'){
                creature.doAction("see you");
                moveTowards(point.x, point.y);
            }
        }


       } //meleePlayer

    private List<Point> getPointsInView(int mx, int my, int radius){
        List<Point> points = new ArrayList<Point>();
        for (int x = mx - radius; x < mx + radius; x++){
            for (int y = my - radius; y < my + radius; y++){
                points.add(new Point(x, y, creature.z));
            }
        }

        return points;

    } //getPointsInView

    private void moveTowards(int x, int y){
        int origX = creature.x;
        int origY = creature.y;

        if (x > origX){
            creature.moveBy(1, 0, 0);

        } else if (x < origX){
            creature.moveBy(-1, 0, 0);
        } else if (x == origX){
            if (y > origY){
                creature.moveBy(0, 1, 0);
            } else if (y < origY){
                creature.moveBy(0, -1, 0);
            } //else if y

        } //else


    } //moveTowards

    } //class CreatureAi
