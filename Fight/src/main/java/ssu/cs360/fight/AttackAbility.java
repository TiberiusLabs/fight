package ssu.cs360.fight;

public class AttackAbility extends Ability {

    public AttackAbility() {
        this.actionType = ActionType.ATTACK;
    }

    public int Execute(Character source, Character target) {
        Dice d20 = new Dice(20);
        boolean critical = false;
        int attackRoll = d20.Roll();
        int damage;

        if (attackRoll == 20)
            critical = true;

        attackRoll += source.getAttack();

        if (critical) {
            damage = (int) (GetAbilityDamage(source) * 1.5);
            target.damage(damage);
        } else if (attackRoll >= target.getDefense() + target.getEvade()) {
            damage = GetAbilityDamage(source);
            target.damage(damage);
        } else {
            damage = 0;
        }

        return damage;
    }
}
