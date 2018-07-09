package rl.Creature;

import java.util.ArrayList;
import java.util.List;

public class LevelUpController {

    private static LevelUpOption[] options = new LevelUpOption[]{
            new LevelUpOption("Increased hit points"){
                public void invoke(Creature creature) {
                    creature.gainMaxHp();
                } //invoke

    },
    new LevelUpOption("Increased attack value"){
        public void invoke(Creature creature){
            creature.gainAttackValue();
        }
    },
    new LevelUpOption("Increased defense value"){
                public void invoke(Creature creature){
                    creature.gainDefenseValue();
                }
    },
    new LevelUpOption("Increased vision"){
                public void invoke(Creature creature){
                    creature.gainVision();
                }

    }



    };

    public void autoLevelUp(Creature creature){
        options[(int)(Math.random() * options.length)].invoke(creature);
    } //autoLevelUp

    //returns a list of all of the level up options
    public static List<String> getLevelUpOptions() {
        List<String> getOptions = new ArrayList<String>();

        for (LevelUpOption option : options){
            getOptions.add(option.getName());
        } //for

        return getOptions;

    }


    //returns specific level up option
    public LevelUpOption getLevelUpOption(String name){
        for (LevelUpOption option : options){
            if (option.getName().equals(name)){
                return option;
            }
        }
        return null;
    }

} //LevelUpController
