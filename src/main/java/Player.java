package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Player implements Serializable, Joueur{
    private String name;
    private int score;
    private Boolean AsWon=false;
    public static List<Character> forbiddenCars = new ArrayList<Character>();
    protected char car;
    private String color;

    private List<Bonus> bonus;

    public Player(String name) throws WrongInputOfCharacter{
        this.bonus = new ArrayList<Bonus>();
        this.name=name;
        this.score=0;
        if(this.name != "Wild"){
            askChar();
        }
        
    }

    @SuppressWarnings("resource")
    public void askChar() throws WrongInputOfCharacter{
        String car="";
        System.out.print("Donnez un caractère pour vous identifier : ");
        try{
            Scanner sc=new Scanner(System.in);
            car=sc.nextLine();
            if(car.length()>1){
                throw new WrongInputOfCharacter();
            }
        } catch (InputMismatchException e){
            System.out.println("Vous devez entrer un caractère !");
            askChar();
        } catch (WrongInputOfCharacter e){
            System.out.println("Vous devez entrer un caractère !");
            askChar();
        }
        if(!forbiddenCars.contains(car.charAt(0))){
            this.car= car.charAt(0);
            forbiddenCars.add(this.car);
        }else{
            System.out.println("Ce caractère est déjà pris, veuillez rééssayer");
            askChar();
        }
    }

    public String getColor(){return this.color;}
    public int getScore(){return this.score;}
    public char getCar(){return this.car;}
    public boolean isWinner(){return this.AsWon;}
    public void changeToWinner(){this.AsWon=true;}
    public String getName(){return this.name;}
    public void addToScore(int pts){this.score+=pts;}
    public String toString(){return "Name: "+this.getName()+", Score: "+this.getScore();}

    public void savePlayer(Player player) {
        String dirPath = "groupe-7/res/Player/";
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs(); 
        }

        String filePath = dirPath + player.getName() + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(filePath)))) {
            out.writeObject(player);
            System.out.println("Player " + player.getName() + " saved successfully at " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving player: " + e.getMessage());
        }
    }

    public void loadingPlayer(String name) {
        String filePath = "groupe-7/res/Player/" + name + ".ser";
        try (FileInputStream fileIn = new FileInputStream(filePath); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Player player = (Player) in.readObject();
            this.name = player.getName();
            this.score = player.getScore();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading player: " + e.getMessage());
        }
    }
    
    public void saveInHistory(ArrayList<Player> listOfPlayer) throws tooManyPlayersInThisList, WrongInputOfCharacter {
        if(listOfPlayer.size()>6){
            throw new tooManyPlayersInThisList();
        }

        String str = "";
        String trueStr = "";

        for (Player player : listOfPlayer) {
            if (!player.isWinner()) {
                str = str+ player.getName() + ",";
            } else {
                trueStr = player.getName()+",";
            }
        }

        if (listOfPlayer.size()<6){
            for(int i=0;i<6-listOfPlayer.size();i++){
                str+="null,";
            }
        }
    
        trueStr +=str.substring(0,str.length()-1)+"::";
        String[] parts = trueStr.split("::");
    
        String filePath = "res/history/history.csv";
    
        ArrayList<String> existingHistory = new ArrayList<>();
        try (FileReader file=new FileReader(filePath); BufferedReader reader = new BufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingHistory.add(line); 
            }
        } catch (IOException e) {
            System.out.println("history.csv not found at " + filePath + ", a new file will be created.");
        }
    
        for (String part : parts) {
            if (!part.trim().isEmpty()) { 
                existingHistory.add(part);
            }
        }

        try (FileWriter file=new FileWriter(filePath); PrintWriter writer = new PrintWriter(file)) {
            for (String entry : existingHistory) {
                writer.println(entry);
            }
            System.out.println("History updated in " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setColor(String newColor){
        this.color = newColor;
    }

    public List<Bonus> getBonus(){
        return this.bonus;
    }

    
}
