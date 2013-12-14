package ssu.cs360.fight;

import java.util.ArrayList;

public class Character {

    enum CharacterType {CHARACTER, HERO, MELEE, MAGIC, RANGED, MONSTER}

    int HP;
    int maxHP;
    int SP;
    int maxSP;
    int strength;
    int dexterity;
    int intelligence;
    int attack;
    int defense;
    int evade;
    Boolean isAlive;
    CharacterType characterType;
    Abilities abilities;

    public Character() {
        maxHP = 100;
        HP = maxHP;
        maxSP = 100;
        SP = 100;
        strength = 10;
        dexterity = 10;
        intelligence = 10;
        attack = 0;
        defense = 10;
        evade = 0;
        isAlive = true;
        characterType = CharacterType.CHARACTER;
    }

    public void addAbilities(Abilities abilities) {
        this.abilities = abilities;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public int getHP() {
        return HP;
    }

    public void damage(int HP) {
        this.HP = (this.HP < HP) ? 0 : this.HP - HP;
        if (this.HP == 0)
            kill();
    }

    public void heal(int HP) {
        if (isAlive)
            this.HP = (this.HP + HP > maxHP) ? maxHP : this.HP + HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getSP() {
        return SP;
    }

    public void spendSP(int SP) {
        this.SP = (this.SP < SP) ? this.SP : this.SP - SP;
    }

    public void restoreSP(int SP) {
        this.SP = (this.SP + SP > maxSP) ? maxSP : this.SP + SP;
    }

    public int getMaxSP() {
        return maxSP;
    }

    public void setMaxSP(int maxSP) {
        this.maxSP = maxSP;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void getDefense(int defense) {
        this.defense = defense;
    }

    public int getEvade() {
        return evade;
    }

    public void setEvade(int evade) {
        this.evade = evade;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        isAlive = false;
    }

    public void revive() {
        isAlive = true;
    }

    public void onEvade(Character target) {
    }

    public int getAttackDamage() {
        return 0;
    }

    public int attack(Character target) {

        Dice d20 = new Dice(20);
        boolean critical = false;
        int attackRoll = d20.Roll();
        int damage;

        if (attackRoll == 20)
            critical = true;

        attackRoll += attack;

        if (critical) {
            damage = (int) (getAttackDamage() * 1.5);
            target.damage(damage);
        } else if (attackRoll >= target.getDefense() + target.getEvade()) {
            damage = getAttackDamage();
            target.damage(damage);
        } else if (attackRoll >= target.getDefense()) {
            damage = -1;
            target.onEvade(this);
        } else {
            damage = 0;
        }

        return damage;
    }

    public int useAbility(int index, Character target) {
        return abilities.get(index).Execute(this, target);
    }

    public String getName() {
        return "Character";
    }

    public String getAttackText(String targetName) {
        return "You swing at the " + targetName + ".";
    }
}
