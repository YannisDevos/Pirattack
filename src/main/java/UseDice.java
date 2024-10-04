
package main.java;

import java.io.IOException;

public class UseDice {
    public static void main(String[] args) throws IOException {
        Dice sixDice = new Dice(6);
        for (int idx = 0; idx < 10; idx ++) {
            sixDice.roll();
            System.out.println(sixDice.getDisplay());
        }
    }

}
