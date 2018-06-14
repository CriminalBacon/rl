package rl;

import rl.Tile;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class World {

    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Creature> creatures;


    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<Creature>();
    } //rl.World

    public int getHeight() {
        return height;
    } //getHeight

    public int getWidth() {
        return width;
    } //getWidth

    public List<Creature> getCreatures(){
        return creatures;
    } //getCreatures

    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height){
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        } //else

    } //tile

    public char glyph(int x, int y){
        return tile(x,y).getGlyph();
    } //glyph

    public Color color(int x , int y){
        return tile(x, y).getColor();
    } //color

    public void dig(int x, int y){
        if (tiles[x][y].isDiggable()){
            tiles[x][y] = Tile.FLOOR;
        } //if

    } //dig

    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        } while (!tile(x, y).isGround() || creature(x, y) !=null);

        creature.x = x;
        creature.y = y;
        creatures.add(creature);

    } //addAtEmptyLocation

    //gets creature at specific location
    public Creature creature(int x, int y){
        for (Creature creature : creatures){
            if (creature.x == x && creature.y == y){
                return creature;
            } //if
        }
        return null;
    } //creature

    //removes the creature for the world
    public void remove(Creature other){
        creatures.remove(other);
    } //remove

    //Lets the creatures take their turn
    public void update(){
        List<Creature> toUpdate = new ArrayList<Creature>(creatures);
        for (Creature creature : toUpdate){
            creature.update();
        } //for
    } //update



} //class rl.World
