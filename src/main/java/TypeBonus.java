package main.java;

public enum TypeBonus{


    MULTIPY(4),ADD(0),DICE(0),DIVIDE(6),PASS(7);


    public int nbtours;

    TypeBonus(int nbtours){
        this.nbtours = nbtours;
    }
}