package ssu.cs360.fight;

/**
 * Created by kgboperative on 12/10/13.
 */
public class Assassin extends Monster {

    public Assassin(Abilities abilities, String name) {
        this.abilities = abilities;
        this.name = name;
        maxHP = 100;
        HP = maxHP;
        maxSP = 100;
        SP = maxSP;
        strength = 10;
        dexterity = 12;
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
        return d20.Roll() + 2 * dexterity;
    }

    public String getName() {
        return "Assassin";
    }

    public String getAttackText(String targetName) {
        return "The Assassin shoots a black arrow at you.";
    }
}
