package ssu.cs360.fight;

public class Melee extends Hero {

    public Melee() {
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
        characterType = CharacterType.MELEE;
    }

    @Override
    public int getAttackDamage() {
        Dice d20 = new Dice(20);
        return d20.Roll() + 2 * strength;
    }

    public String getName() {
        return "Fighter";
    }

    public String getAttackText(String targetName) {
        return "You swing your sword at the " + targetName;
    }
}
