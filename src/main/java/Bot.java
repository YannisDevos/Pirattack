package main.java;

import java.util.Random;

public class Bot extends Player implements Joueur{

    private int target;

    public Bot(Player[] currentPlayers, int nbBot) throws WrongInputOfCharacter {
        super("Bot"+nbBot);
        this.target=0;
    }

    public void defineTarget(int i){
        this.target=i;
    }

    public int getTarget(){
        return this.target;
    }

    public void askChar() throws WrongInputOfCharacter{
        Random random = new Random();
        int randomInt = random.nextInt(26);
        char randomChar = (char) ('a' + randomInt);
        this.car=randomChar;
    }
}
