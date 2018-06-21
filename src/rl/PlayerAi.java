package rl;

import java.util.List;

public class PlayerAi  extends CreatureAi{

    private List<String> messages;
    private FieldOfView fov;

    public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
        super(creature);
        this.messages = messages;
        this.fov = fov;

    } //PlayerAi

    @Override
    public void onEnter(int x, int y, int z, Tile tile) {
        if (tile.isGround()){
            creature.x = x;
            creature.y = y;
            creature.z = z;
        } else if (tile.isDiggable()){
            creature.dig(x, y, z);
        } //else if

    } //onEnter

    @Override
    public void onNotify(String message) {
        messages.add(message);
    } //onNotify

    @Override
    public boolean canSee(int wx, int wy, int wz) {
        if (creature.isGod()){
            return true;
        }
        return fov.isVisible(wx, wy, wz);
    } //canSee

} //class PlayerAi
