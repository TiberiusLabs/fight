#ifndef DICE_H
#define DICE_H

// File: Dice.h
// Author: Amandeep Gill
// Contents: This class defines the dice that mimic table-top dice rolls

class Dice {
    public:
        // default constructor
        Dice();

        // Overloaded constructor to set the number of sides on the dice
        Dice(int s);

        // returns the value of a random roll of this dice
        int Roll();

    private:
        int sides;
};

#endif
