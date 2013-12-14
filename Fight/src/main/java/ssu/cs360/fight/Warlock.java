package ssu.cs360.fight;

/**
 * Created by kgboperative on 12/10/13.
 */
public class Warlock extends Monster {

    public Warlock(Abilities abilities, String name) {
        this.abilities = abilities;
        this.name = name;
        maxHP = 100;
        HP = maxHP;
        maxSP = 100;
        SP = maxSP;
        strength = 8;
        dexterity = 10;
        intelligence = 12;
        attack = 0;
        defense = 10;
        evade = 0;
        isAlive = true;
        characterType = CharacterType.MONSTER;
    }

    @Override
    public int getAttackDamage() {
        Dice d20 = new Dice(20);
        return d20.Roll() + 2 * intelligence;
    }

    public String getName() {
        return "Warlock";
    }

    public String getAttackText(String targetName) {
        return "The Warlock flings some dark magical energy at you.";
    }
}
