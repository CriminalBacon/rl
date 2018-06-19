package rl;

import java.awt.Color;
import asciiPanel.AsciiPanel;


public enum Tile {
    FLOOR((char)250, AsciiPanel.yellow),
    WALL((char)178, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack),
    STAIRS_DOWN('>', AsciiPanel.white),
    STAIRS_UP('<', AsciiPanel.white),
    UNKNOWN(' ', AsciiPanel.white);

    private char glyph;
    private Color color;

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    } //rl.Tile

    public char getGlyph(){
        return glyph;
    } //getGlyph

    public Color getColor(){
        return color;
    } //getColor

    public boolean isDiggable(){
        return this == Tile.WALL;
    }

    public boolean isGround(){
        return this != WALL && this != BOUNDS;
    } //isGround

    public boolean isStairs() {return this == Tile.STAIRS_UP || this == Tile.STAIRS_DOWN;
    }
} //enum rl.Tile
