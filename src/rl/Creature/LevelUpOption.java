package rl.Creature;

public abstract class LevelUpOption {

    private String name;

    public LevelUpOption(String name){
        this.name = name;
    } //LevelUpOption

    public String getName() {
        return name;
    } //getName

    public abstract void invoke(Creature creature);

} //LevelUpOption class
