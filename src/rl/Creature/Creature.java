package rl.Creature;

import rl.*;

import java.awt.*;
import java.util.List;

public class Creature {
    private World world;

    // we are specifically not using getters and setters for x and y
    public int x;
    public int y;
    public int z;

    private char glyph;
    private Color color;
    private CreatureAi ai;
    private int maxHp;
    private int hp;
    private int attackValue;
    private int defenseValue;
    private int visionRadius;
    private String name;
    private Inventory inventory;
    private int maxFood;
    private int food;
    private Item weapon;
    private Item armor;
    private int xp;
    private int level;

    private boolean god;

    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
        visionRadius = 6;
        this.inventory = new Inventory(10);
        this.maxFood = 1000;
        this.food = maxFood / 3 * 2;
        this.level = 1;

        this.god = false;
    } //Creature

    public char getGlyph() {
        return glyph;
    } //getGlyph

    public Color getColor() {
        return color;
    } //getColor

    public int getMaxHp() {
        return maxHp;
    } //getMaxHp

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    } //setMaxHp

    public int getHp() {
        return hp;
    } //getHp

    public void setHp(int hp) {
        this.hp = hp;
    } //setHp

    public int getAttackValue() {
        return attackValue + (weapon == null ? 0 : weapon.getAttackValue());
    } //getAttackValue

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    } //setAttackValue

    public int getDefenseValue() {
        return defenseValue;
    } //getDefenseValue

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue + (armor == null ? 0 : weapon.getDefenseValue());
    } //setDefenseValue

    public int getVisionRadius() {
        return visionRadius;
    } //getVisionRadius

    public String getName() {
        return name;
    } //name

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getMaxFood() {
        return maxFood;
    } //getMaxFood

    public int getFood() {
        return food;
    } //getFood

    public Item getWeapon() {
        return weapon;
    } //getWeapon

    public Item getArmor() {
        return armor;
    } //getArmor

    public int getXp() {
        return xp;
    } //getXp

    public int getLevel() {
        return level;
    } //getLevel

    public boolean isGod() {
        return god;
    } //isGod

    public void setGod(boolean god) {
        this.god = god;
    }

    //uses setter injection instead of using the constructor
    public void setCreatureAi(CreatureAi ai){
        this.ai = ai;
    } //setCreatureAi

    public void dig(int wx, int wy, int wz){
        modifyFood(-10);
        world.dig(wx, wy, wz);
        doAction("dig a tunnel");

    } //dig

    public void moveBy(int mx, int my, int mz){
        if (mx == 0 && my == 0 && mz == 0){
            return;
        }

        Tile tile = world.tile(x+mx, y+my, z+mz);
        Creature other = world.creature(x+mx, y+my, z+mz);

        if (mz == -1){
            if (tile == Tile.STAIRS_DOWN){
                doAction("walk up the stairs to level %d", z+mz+1);
            } else {
                doAction("try to go up but are stopped by the cave ceiling");
                return;
            } //else

        } else if (mz ==1){
            if (tile == Tile.STAIRS_UP){
                doAction("walk down the stairs to level %d", z+mz+1);
            } else{
                doAction("try to go down but are stopped by the cave floor");
                return;
            }

        } //else if


        if (other == null){
            ai.onEnter(x+mx, y+my, z+mz, tile);
        } else {
            attack(other);
        } //else


    } //moveBy



    //the damage is a random number from 1 to the attackers attack value minus the defenders defense
    public void attack(Creature other){
        int amount = Math.max(0, attackValue - other.getDefenseValue());
        amount = (int)(Math.random() * amount) + 1;
        doAction("attack the %s for %d damage", other.getName(), amount);
        other.modifyHp(-amount);
        if ((other.hp) < 1){
            gainXp(other);
        }

    } //attack

    public void modifyHp(int amount){
        hp += amount;

        if (hp < 1){
            doAction("die");
            leaveCorpse();
            world.remove(this);
        }

    } //modifyHp


    //Lets the creature take their turn
    public void update(){
        modifyFood(-1);
        ai.onUpdate();
    } //update

    public boolean canEnter(int wx, int wy, int mz){
        if (world.tile(wx, wy, mz).isGround() && world.creature(wx, wy, mz) == null){
            return true;
        } else {
            return false;
        }


    } //canEnter

    public void notify(String message, Object ... params){
        ai.onNotify(String.format(message, params));
    } //notify


    //notifies nearby creatures when something happens
    public void doAction(String message, Object ... params){

        for (int ox = -visionRadius; ox < visionRadius+1; ox++){
            for(int oy = -visionRadius; oy < visionRadius+1; oy++){
                if (ox*ox + oy*oy > visionRadius*visionRadius){
                    continue;
                } //if

                Creature other = world.creature(x+ox, y+oy, z);

                if (other == null){
                    continue;
                }

                if (other == this){
                    other.notify("You " + message + ".", params);
                } else if (other.canSee(x, y, z)){
                    other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
                } //else

            } //for oy


        } //for ox

    } //doAction

    public boolean canSee(int wx, int wy, int wz){
        return ai.canSee(wx, wy, wz);
    } //canSee

    public Tile tile(int wx, int wy, int wz){
        return world.tile(wx, wy, wz);
    } //tile


    public Creature creature(int wx, int wy, int wz){
        return world.creature(wx, wy, wz);
    } //creature


    public void pickup(){
        Item item = world.item(x, y, z);

        if (item == null) {
            doAction("grab at the ground");
        } else if (inventory.isFull()) {
            doAction("inventory is full");
        } else {
            doAction("pickup a %s", item.getName());
            world.remove(x, y, z);
            inventory.add(item);
        } //else

    } //pickup


    public void drop(Item item){
        if (world.addAtEmptySpace(item, x, y , z)) {
            doAction("drop a " + item.getName());
            inventory.remove(item);
            unequip(item);
        } else {
            notify("There is nowhere to drop the %s.", item.getName());
        } //else

    } //drop


    private void leaveCorpse(){
        Item corpse = new Item('%', color, name + " corpse");
        corpse.modifyFoodValue(maxHp * 3);
        world.addAtEmptySpace(corpse, x, y, z);

    } //leaveCorpse

    public void modifyFood(int amount){
        food += amount;
        if (food > maxFood){
            food = maxFood;
        } else if (food < 1 && isPlayer()){
            modifyHp(-1000);
        } //else

    } //modifyFood

    public boolean isPlayer(){
        return glyph == '@';
    } //isPlayer


    public void eat(Item item){
        if (item.getFoodValue() < 0){
            notify("Gross!");
        }

        modifyFood(item.getFoodValue());
        inventory.remove(item);
        unequip(item);
    } //eat


    public void unequip(Item item){
        if (item == null){
            return;
        }

        if (item == armor){
            doAction("remove a " + item.getName());
            armor = null;
        } else if (item == weapon){
            doAction("put away a " + item.getName());
            weapon = null;
        } //if

    } //unequip

    public void equip(Item item){
        if (item.getAttackValue() == 0 && item.getDefenseValue() ==0){
            return;
        }

        if (item.getAttackValue() >= item.getDefenseValue()){
            unequip(weapon);
            doAction("wield a " + item.getName());
            weapon = item;
        } else {
            unequip(armor);
            doAction("put on a " + item.getName());
            armor = item;
        } //else


    } //equip


    // returns the points the player can see in a certain direction.
    // currently only used for the player
    public List<rl.Point> lookInDirection(String dir){

        int vision = getVisionRadius();

        switch (dir){
            case "down":
                Line linedown = new Line(x, y, x, y + vision);
                return linedown.getPoints();
            case "up":
                Line lineup = new Line(x, y, x, Math.abs(y - vision));
                return lineup.getPoints();
            case "right":
                Line lineRight = new Line(x, y, x + vision, y);
                return lineRight.getPoints();
            case "left":
                Line lineLeft = new Line(x, y, Math.abs(x - vision), y);
                return lineLeft.getPoints();
            default:
                return null;

        } //switch

    } //lookInDirection

    // when player levels, the hp is set to max hp
    public void modifyXp(int amount){
        xp += amount;

        notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);

        while(xp > (int)(Math.pow(level, 1.5) * 20)){
            level++;
            doAction("advance to level %d", level);
            ai.onGainLevel();
            hp = maxHp;
        } //while

    } //modifyXp

    //by subtracting the killers level, tougher creatures are worth more xp and easy creatures are worth no xp
    public void gainXp(Creature other){
        int amount = other.maxHp
                + other.getAttackValue()
                + other.getDefenseValue()
                - level * 2;

        if (amount > 0){
            modifyXp(amount);
        } //if

    } //gainXp

    public void gainMaxHp(){
        maxHp += 10;
        hp = maxHp;
        doAction("looks healthier");

    } //gainMaxHp

    public void gainAttackValue(){
        attackValue += 2;
        doAction("look stronger");

    } //gainAttackValue

    public void gainDefenseValue(){
        defenseValue += 2;
        doAction("look tougher");

    } //gainDefenseValue

    public void gainVision(){
        visionRadius += 1;
        doAction("look more aware");
    }

    //assumes the first word is the verb.  may want to move this in the future since it is grammar
    private String makeSecondPerson(String text){
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words){
            builder.append(" ");
            builder.append(word);
        } //for
        return builder.toString().trim();
    } //makeSecondPerson

} //class Creature
