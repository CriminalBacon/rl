package rl;

import rl.Tile;

import java.awt.*;


public class World {

    private Tile[][] tiles;
    private int width;
    private int height;

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    } //rl.World

    public int getHeight() {
        return height;
    } //getHeight

    public int getWidth() {
        return width;
    } //getWidth

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

    public Color color(int x ,int y){
        return tile(x, y).getColor();
    } //color

} //class rl.World
