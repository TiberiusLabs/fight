package ssu.cs360.fight;

public class DebuffAbility extends Ability {

    public DebuffAbility() {
        this.actionType = ActionType.DEBUFF;
    }

    public int Execute(Character source, Character target) {
        Dice d20 = new Dice(20);
        int attackRoll = d20.Roll() + source.getAttack();

        attackRoll += source.getAttack();

        if (attackRoll >= target.getDefense() + target.getEvade()) {
            Dice d3 = new Dice(3);
            duration = d3.Roll() + 1;
            SetCharStat(target, statType, GetCharStat(target, statType) - d3.Roll() - 1);
        } else {
            duration = 0;
        }

        return duration;
    }
}
