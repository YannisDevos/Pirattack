
package main.java;

import java.io.IOException;
import java.util.Random;

public class Dice {
    private final int VALUE_MAX;
    private final Random GEN;
    private String displayString;
    private int currentValue;

    public Dice(int VALUE_MAX) {
        this.VALUE_MAX = VALUE_MAX + 1;
        this.GEN = new Random();
        this.displayString = "";
    }

    private int newValue() {
        return this.GEN.nextInt(1, VALUE_MAX);
    }

    public String getDisplay() {
        return this.displayString;
    }

    public void roll() throws IOException {
        this.currentValue = newValue();
        this.displayString = getValueDisplay(this.currentValue);
    }

    private String getValueDisplay(int value) throws IOException {
        final String FILEPATH = "./res/dice" + value + ".txt";
        return AsciiReader.read(FILEPATH);
    }

    public int getCurrentValue(){
        return this.currentValue;
    }
}