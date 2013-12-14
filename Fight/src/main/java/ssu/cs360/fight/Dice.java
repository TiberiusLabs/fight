package ssu.cs360.fight;

import java.util.Random;

public class Dice {

    int count;
    int type;
    Random die;

    public Dice() {
        count = 0;
        type = 0;
    }

    public Dice(int type) {
        count = 1;
        this.type = type;
        die = new Random();
    }

    public Dice(int count, int type) {
        this.count = count;
        this.type = type;
        die = new Random();
    }

    int Roll() {
        int result = 0;
        for (int i = 0; i < count; i++)
            result += die.nextInt(type) + 1;

        return result;
    }

    @Override
    public String toString() {
        return count + "d" + type;
    }
}
