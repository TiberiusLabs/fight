package ssu.cs360.fight;

public class Ability {

    enum ActionType {ATTACK, HEAL, BUFF, DEBUFF}
    enum StatType {ATTACK, DEFENSE, EVADE, STRENGTH, DEXTERITY, INTELLIGENCE}

    ActionType actionType;
    StatType statType;
    String name;
    String description;
    String playerBattleText;
    String enemyBattleText;
    int duration;
    int power;
    int cost;

    public Ability() {
    }

    public String GetName() {
        return name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String GetDescription() {
        return description;
    }

    public void SetDescription(String description) {
        this.description = description;
    }

    public String GetPlayerBattleText() {
        return playerBattleText;
    }

    public void SetPlayerBattleText(String playerBattleText) {
        this.playerBattleText = playerBattleText;
    }

    public String GetEnemyBattleText() {
        return enemyBattleText;
    }

    public void SetEnemyBattleText(String enemyBattleText) {
        this.enemyBattleText = enemyBattleText;
    }

    public int GetDuration() {
        return duration;
    }

    public void SetDuration(int duration) {
        this.duration = duration;
    }

    public int GetPower() {
        return power;
    }

    public void SetPower(int power) {
        this.power = power;
    }

    public int GetCost() {
        return cost;
    }

    public void SetCost(int cost) {
        this.cost = cost;
    }

    public String GetActionType() {
        switch (actionType) {
            case ATTACK:
                return "ATTACK";
            case HEAL:
                return "HEAL";
            case BUFF:
                return "BUFF";
            case DEBUFF:
                return "DEBUFF";
            default:
                return "";
        }
    }

    public String GetStatType() {
        switch (statType) {
            case ATTACK:
                return "ATTACK";
            case DEFENSE:
                return "DEFENSE";
            case EVADE:
                return "EVADE";
            case STRENGTH:
                return "STRENGTH";
            case DEXTERITY:
                return "DEXTERITY";
            case INTELLIGENCE:
                return "INTELLIGENCE";
            default:
                return "";
        }
    }

    public void SetStatType(String type) {
        if (type.equals("ATTACK")) {
            statType = StatType.ATTACK;
        } else if (type.equals("DEFENSE")) {
            statType = StatType.DEFENSE;
        } else if (type.equals("EVADE")) {
            statType = StatType.EVADE;
        } else if (type.equals("STRENGTH")) {
            statType = StatType.STRENGTH;
        } else if (type.equals("DEXTERITY")) {
            statType = StatType.DEXTERITY;
        } else if (type.equals("INTELLIGENCE")) {
            statType = StatType.INTELLIGENCE;
        }
    }

    public int GetCharStat(Character character, StatType stat) {
        switch (stat) {
           case ATTACK:
               return character.getAttack();
           case DEFENSE:
               return character.getDefense();
           case EVADE:
               return character.getEvade();
           case STRENGTH:
               return character.getStrength();
           case DEXTERITY:
               return character.getDexterity();
           case INTELLIGENCE:
               return character.getIntelligence();
            default:
               return 0;
        }
    }

    public void SetCharStat(Character character, StatType stat, int value) {
        switch (stat) {
            case ATTACK:
                character.setAttack(value);
                break;
            case DEFENSE:
                character.getDefense(value);
                break;
            case EVADE:
                character.getDefense(value);
                break;
            case STRENGTH:
                character.setStrength(value);
                break;
            case DEXTERITY:
                character.setDexterity(value);
                break;
            case INTELLIGENCE:
                character.setIntelligence(value);
                break;
        }
    }

    public int Execute(Character source, Character target) {
        return 0;
    }

    public int GetAbilityDamage(Character character) {
        Dice d20 = new Dice(20);
        return d20.Roll() + 2 * GetCharStat(character, statType) + power;
    }
}
