package rl;

import rl.Creature.Creature;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class World {

    private Tile[][][] tiles;
    private int width;
    private int height;
    private int depth;
    private List<Creature> creatures;
    private Item[][][] items;


    public World(Tile[][][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.depth = tiles[0][0].length;
        this.creatures = new ArrayList<Creature>();
        this.items = new Item[width][height][depth];

    } //rl.World

    public int getHeight() {
        return height;
    } //getHeight

    public int getWidth() {
        return width;
    } //getWidth

    public int getDepth() {
        return depth;
    } //getDepth

    public List<Creature> getCreatures(){
        return creatures;
    } //getCreatures

    public Item[][][] getItems() {
        return items;
    }

    public Tile tile(int x, int y, int z){
        if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth){
            return Tile.BOUNDS;
        } else {
            return tiles[x][y][z];
        } //else

    } //tile

    public char glyph(int x, int y, int z){
        Creature creature = creature(x, y, z);

        if (creature != null){
            return creature.getGlyph();
        }

        if (item(x, y, z)  != null){
            return item(x, y, z).getGlyph();
        }

        return tile(x, y, z).getGlyph();


    } //glyph

    public Color color(int x , int y, int z){
        Creature creature = creature(x, y, z);
        if (creature != null){
            return creature.getColor();
        }

        if (item(x, y, z)  != null){
            return item(x, y, z).getColor();
        }

        return tile(x, y, z).getColor();


    } //color

    public void dig(int x, int y, int z){
        if (tiles[x][y][z].isDiggable()){
            tiles[x][y][z] = Tile.FLOOR;
        } //if

    } //dig

    //returns item at location
    public Item item(int x, int y, int z){
        return items[x][y][z];
    } //item

    // add creature at empty location
    public void addAtEmptyLocation(Creature creature, int z){
        int x;
        int y;


        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);

        } while (!tile(x, y, z).isGround() || creature(x, y, z) !=null);

        creature.x = x;
        creature.y = y;
        creature.z = z;
        creatures.add(creature);

    } //addAtEmptyLocation

    //add item at empty location
    public void addAtEmptyLocation(Item item, int depth){
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        } while (!tile(x, y, depth).isGround()
                || tile(x, y, depth).isStairs()
                || item(x, y, depth) != null);

        items[x][y][depth] = item;


    } //addAtEmptyLocation

    //gets creature at specific location
    public Creature creature(int x, int y, int z){
        for (Creature creature : creatures){
            if (creature.x == x && creature.y == y && creature.z == z){
                return creature;
            } //if
        }
        return null;
    } //creature

    //removes the creature for the world
    public void remove(Creature other){
        creatures.remove(other);
    } //remove

    //removes item at specific space
    public void remove(int x, int y, int z){
        items[x][y][z] = null;
    } //remove

    //Lets the creatures take their turn
    public void update(){
        List<Creature> toUpdate = new ArrayList<Creature>(creatures);
        for (Creature creature : toUpdate){
            creature.update();
        } //for
    } //update

    public boolean addAtEmptySpace(Item item, int x, int y, int z){

        if (item == null){
            return true;
        } //if

        List<Point> points = new ArrayList<Point>();
        List<Point> checked = new ArrayList<Point>();

        points.add(new Point(x, y, z));

        while (!points.isEmpty()){
            Point p = points.remove(0);
            checked.add(p);

            if (!tile(p.x, p.y, p.z).isGround()){
                continue;
            } //if

            if (items[p.x][p.y][p.z] == null){
                items[p.x][p.y][p.z] = item;
                Creature c = this.creature(p.x, p.y, p.z);
                if (c != null){
                    c.notify("A %s lands between you feet.", item.getName());
                    return true;
                } else {
                    List<Point> neighbors = p.neighbors8();
                    neighbors.removeAll(checked);
                    points.addAll(neighbors);

                } //else
            } //if


        } //while
        return false;
    } //addAtEmptySpace


} //class rl.World

