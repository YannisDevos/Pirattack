package main.java;

import java.util.Random;

public class Island {

    public static final int MIN_HEIGHT = 2;
    public static final int MIN_LENGTH = 2;
    public static final int MAX_HEIGHT = 5;
    public static final int MAX_LENGTH = 8;
    private Random gen;
    private String[][] field;
    private Player owner;

    private Bonus bonus;

    public Island()throws WrongInputOfCharacter{
        this.owner = new Player("Wild");
        this.owner.getBonus().add(bonus);
        this.gen = new Random();
        this.field = new String[this.gen.nextInt(Island.MIN_HEIGHT, Island.MAX_HEIGHT)][this.gen.nextInt(Island.MIN_LENGTH, Island.MAX_LENGTH)];
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[0].length; j++){
                this.field[i][j] = ".";
            }
        } 
        this.bonus = new Bonus(TypeBonus.values()[this.gen.nextInt(0,3)],this.gen.nextInt(1,4));  

    }
    public static int[] findFistEmptySlot(String[][] field){
        int[] res = new int[2];
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[0].length; j++){
                if(field[i][j].equals(".")){
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        } 
        return new int[] {0,0};
    }

    public void takingTerrain(char car, int points, String color){
        int i = Island.findFistEmptySlot(field)[0];
        int j = Island.findFistEmptySlot(field)[1];
        int count = 0;

        while(i < field.length && count < points){
            while(j < field[0].length && count < points){
                field[i][j] = ""+color + car + Color.RESET;
                count++;
                j++;
            }
            i++;
            j = 0;
            if(i == field.length){
                i = 0;
            }
        }
    }

    public static Player getPlayerFromCar(char car, Player[] playerPool){
        for (Player p: playerPool) {
            if(p.getCar() == car) return p; 
        }
        return null;
    }

    public Player getIslandOwner(Player[] playerPool){
        Player res = null;
        String currentCar = "";
        String lastCar = "";
        int count = 0;
        int buffer = 0;
        for(int i = 0; i < this.field.length; i++){
            for(int j = 0; j < this.field[0].length; j++){
                currentCar = this.field[i][j];
                if(currentCar.equals(".")){
                    return res;
                }else if(!currentCar.equals(lastCar)){
                    count++;
                    if(buffer < count){
                        res = getPlayerFromCar(currentCar.charAt(0), playerPool);
                        buffer = count;
                        lastCar = currentCar;
                        count = 0;
                    } 
                }else{
                    count++;
                    lastCar = currentCar;
                } 
            }
        }
        return res;
    }

    public void updateIslandOwner(Player[] playerPool){

        if(this.owner != null && this.owner.getName() != "Wild"){
            for(int i = 0 ;i<playerPool.length;i++){
                if(playerPool[i] == this.owner){
                    playerPool[i].getBonus().remove(this.bonus);
                }
            }
        }
        for(int i = 0 ;i<playerPool.length;i++){
            if(playerPool[i] == this.getIslandOwner(playerPool)){
                playerPool[i].getBonus().add(this.bonus);
           
            }
        }
        this.owner = this.getIslandOwner(playerPool);
        if(this.owner != null){
            System.out.println(this.owner.getName() + " a pris possession de l'île ");
        }
        

    }
     public Player getOwner() {
         return owner;
     }

    public String toString(){
        String res = "";
        for(int i = 0; i < this.field.length; i++){
            for(int j = 0; j < this.field[0].length; j++){
                res += this.field[i][j];
            }
            res+= "\n";
        } 
        return res;
    }

    public String[][] getField() {
        return field;
    }


    public Bonus getBonus() {
        return bonus;
    }
    
}
