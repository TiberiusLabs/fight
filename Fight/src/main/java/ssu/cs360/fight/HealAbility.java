package ssu.cs360.fight;

public class HealAbility extends Ability {

    public HealAbility() {
        this.actionType = ActionType.HEAL;
    }

    public int Execute(Character source, Character target) {
        int heal = GetAbilityDamage(source);
        source.heal(heal);
        return heal;
    }
}
