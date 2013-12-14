package ssu.cs360.fight;

public class Ranged extends Hero {

    public Ranged() {
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
        characterType = CharacterType.RANGED;
    }

    @Override
    public int getAttackDamage() {
        Dice d20 = new Dice(20);
        return d20.Roll() + 2 * dexterity;
    }

    public String getName() {
        return "Ranger";
    }

    public String getAttackText(String targetName) {
        return "You shoot an arrow at the " + targetName;
    }
}
