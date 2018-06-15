package rl;

public class FungusAi extends CreatureAi {
    private CreatureFactory factory;
    private int spreadCount;

    public FungusAi(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    } //FungusAi

    public void onUpdate(){
        if (spreadCount < 5 && Math.random() < 0.02){
            spread();
        } //if
    } //onUpdate


    // spreads the fungus
    private void spread(){
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;

        if (!creature.canEnter(x, y)){
            return;
        }

        Creature child = factory.newFungus();
        child.x = x;
        child.y = y;
        creature.doAction("spawn child");
        spreadCount++;
    } //spread

} //class FungusAi
