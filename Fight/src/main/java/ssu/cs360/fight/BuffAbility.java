package ssu.cs360.fight;

public class BuffAbility extends Ability {

    public BuffAbility() {
        this.actionType = ActionType.BUFF;
    }

    public int Execute(Character source, Character target) {
        Dice d3 = new Dice(3);
        duration = d3.Roll() + 1;
        SetCharStat(target, statType, GetCharStat(target, statType) + d3.Roll() + 1);

        return duration;
    }
}
