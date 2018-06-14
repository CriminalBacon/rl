package rl;

import java.awt.*;

public class Creature {
    private World world;

    // we are specifically not using getters and setters for x and y
    public int x;
    public int y;

    private char glyph;
    private Color color;
    private CreatureAi ai;

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    } //Creature

    public char getGlyph() {
        return glyph;
    } //getGlyph

    public Color getColor() {
        return color;
    } //getColor

    //uses setter injection instead of using the constructor
    public void setCreatureAi(CreatureAi ai){
        this.ai = ai;
    } //setCreatureAi

    public void dig(int wx, int wy){
        world.dig(wx, wy);
    } //dig

    public void moveBy(int mx, int my){
        ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
    } //moveBy

} //class Creature
