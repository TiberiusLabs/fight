package ssu.cs360.fight;

public class Magic extends Hero {

    public Magic() {
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
        characterType = CharacterType.MAGIC;
    }

    @Override
    public int getAttackDamage() {
        Dice d20 = new Dice(20);
        return d20.Roll() + 2 * intelligence;
    }

    public String getName() {
        return "Mage";
    }

    public String getAttackText(String targetName) {
        return "You throw a ball of magical energy at the " + targetName;
    }
}
