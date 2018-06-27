package rl;

import java.awt.Color;

public class Item {

    private char glyph;
    private Color color;
    private String name;
    private int foodValue;

    public Item(char glyph, Color color, String name){
        this.glyph = glyph;
        this.color = color;
        this.name = name;

    } // Item

    public char getGlyph() {
        return glyph;
    } //getGlyph

    public Color getColor() {
        return color;
    } //getColor

    public String getName() {
        return name;
    } //getName

    public int getFoodValue() {
        return foodValue;
    } //getFoodValue

    public void modifyFoodValue(int amount){
        foodValue += amount;
    }

} //Item class
