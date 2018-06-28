package rl;

import java.awt.Color;

public class Item {

    private char glyph;
    private Color color;
    private String name;
    private int foodValue;
    private int attackValue;
    private int defenseValue;

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

    public int getAttackValue() {
        return attackValue;
    } //getAttachValue

    public void modifyAttackValue(int amount) {
        this.attackValue += amount;
    } //modifyAttackValue;

    public int getDefenseValue() {
        return defenseValue;
    } //getDefenseValue

    public void modifyDefenseValue(int amount) {
        this.defenseValue += amount;
    } //modifyDefenseValue

} //Item class
