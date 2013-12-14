package ssu.cs360.fight;

/**
 * Created by kgboperative on 12/10/13.
 */
public class Mercenary extends Monster {

    public Mercenary(Abilities abilities, String name) {
        this.abilities = abilities;
        this.name = name;
        maxHP = 100;
        HP = maxHP;
        maxSP = 100;
        SP = maxSP;
        strength = 12;
        dexterity = 10;
        intelligence = 8;
        attack = 0;
        defense = 10;
        evade = 0;
        isAlive = true;
        characterType = CharacterType.MONSTER;
    }

    @Override
    public int getAttackDamage() {
        Dice d20 = new Dice(20);
        return d20.Roll() + 2 * strength;
    }

    public String getName() {
        return "Mercenary";
    }

    public String getAttackText(String targetName) {
        return "The Mercenary swings sinister his blade at you.";
    }
}
