package main.java;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private int limiteTurn;
    private Dice dice;
    private IslandPool islands;
    private int nbBots;
    private String[] colors = new String[]{Color.BLUE, Color.GREEN, Color.PURPLE, Color.CYAN, Color.RED, Color.YELLOW};

    public Game(int nbPlayers, int limiteTurn) throws WrongInputOfCharacter{
        this.nbBots=6-nbPlayers;
        this.players = new Player[nbPlayers+1+nbBots];
        this.limiteTurn = limiteTurn;
        this.dice = new Dice(6);
        this.islands = new IslandPool(this.players.length);
    }

    // ---------- Méthodes -----------

    public void start() throws WrongInputOfCharacter {
        Scanner sc = new Scanner(System.in);
        int currentTurn = 1;

        this.createPlayers();
        Menu.clearTerminal();
        
        while (currentTurn <= this.limiteTurn) {

            System.out.println("| Tour n°" + currentTurn + " ! |\n");

            for (Player plyr : players) {
                if(plyr.getName() != "Wild"){
                updateScoreAndOwners();
                Display display = new Display(islands, plyr);
                System.out.println(display.getDisplay());
                int chosenIsland = -1;

                while (chosenIsland < 0 || chosenIsland > islands.size()-1) {
                    if(plyr instanceof Bot){
                        System.out.println("LE "+plyr.getName()+" ATTAQUE UNE ILE");
                        Bot bot=(Bot) plyr;
                        if(currentTurn==1){
                            bot.defineTarget(findTarget(islands));
                        } else if (islands.getIsland(bot.getTarget()).getOwner() != null && islands.getIsland(bot.getTarget()).getOwner().equals(plyr)) {
                            bot.defineTarget(findTarget(islands));
                        } 
                        chosenIsland=bot.getTarget();
                    } else {
                        chosenIsland=choseIsland();
                    }
                    
                }

                Menu.clearTerminal();

                if(!(plyr instanceof Bot)){
                    System.out.print("Appuyer sur la touche [ENTRER] pour lancer le dé !");
                    sc.nextLine();
                    Menu.clearTerminal();
                }

                int diceFace = 6;
                int multBonus = 1;
                int addBonus = 0;
                
                if(!plyr.getBonus().isEmpty()){
                   
                    for(int i = 0 ; i<plyr.getBonus().size();i++){
                     
                        if(plyr.getBonus().get(i).getType() ==TypeBonus.DICE){
                            diceFace = diceFace+1;
                         
                        }
                        if(plyr.getBonus().get(i).getType() == TypeBonus.MULTIPY){
                            multBonus = multBonus * plyr.getBonus().get(i).getValue();
                           
                        }
                        if(plyr.getBonus().get(i).getType() ==TypeBonus.ADD){
                            addBonus = addBonus+ plyr.getBonus().get(i).getValue(); 
                            
                        }

                    }
                    
                }
                this.dice = new Dice(diceFace);
                try {
                    dice.roll();
                    System.out.println(dice.getDisplay() + "\n\n");

                    Random r = new Random();
                    if (r.nextInt(10) == 0) {
                        int nb = r.nextInt(5)+1;
                        System.out.println("Vous avez trouvé un trésor !!! Vous avez " + nb + " points en plus pour ce lancer !!");
                        addBonus += nb;
                    }

                    if(diceFace != 6){
                        System.out.println("Grâce au bonus votre dé a " + diceFace + " faces");
                    }
                    if(multBonus != 1){
                        System.out.println("Grâce au bonus votre lancer a été multiplié par " + multBonus );
                    }
                    if(addBonus != 0){
                        System.out.println("Grâce au bonus vous avez " + addBonus + " points ajouté a votre lancer" );
                    }

                } catch (IOException ioe) {
                    ioe.getStackTrace();
                }                
                islands.getPool().get(chosenIsland).takingTerrain(plyr.getCar(), (dice.getCurrentValue()+addBonus)*multBonus, plyr.getColor());
                }
                updateScoreAndOwners();
            }
            currentTurn++;
        }
        Player winner = players[1];
        for (Player p : players) {
            if (winner.getScore() < p.getScore() && p.getName() != "Wild") {
                winner = p;
            }
        }
        Menu.clearTerminal();
        System.out.println(Display.WINNER + "\n");
        System.out.println("Bravo !!!\nLe gagnant est " + winner.getName() + " ! (" + winner.getScore() + ") points\n");
        System.out.print("Appuyer sur la touche [ENTRER] pour continuer !");
        sc.nextLine();
        Menu.clearTerminal();
    }

    private int findTarget(IslandPool islands){
        Random random = new Random();
        int target = random.nextInt(islands.size());
        return target;
    }

    private int choseIsland(){
        Scanner sc=new Scanner(System.in);
        try{
            System.out.print("Choisis une île à attaquer : ");
            return sc.nextInt()-1;
        } catch(InputMismatchException e){
            System.out.println("Vous devez mettre un nombre !");
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("Vous devez mettre un nombre !");
        }
        return choseIsland();
    }


    public void createPlayers() throws WrongInputOfCharacter{
        Scanner sc = new Scanner(System.in);
        players[0] = new Player("Wild");
        for (int i = 1; i < this.players.length-nbBots; i++) {
            try {
                players[i] = new Player(Menu.askInput("Nom du joueur " + (i) + " : "));
                players[i].setColor(this.colors[i-1]);
            } catch (NullPointerException e) {

                System.out.println(e.getMessage());
            }
        }
        while(nbBots!=0){
            players[7-nbBots]=new Bot(players, nbBots);
            players[7-nbBots].setColor(this.colors[7-nbBots-1]);
            nbBots--;
        }
    }

    public void updateScoreAndOwners() {
        for (Player p : players) {
            p.setScore(0);
        }
        for (Island i : islands) {
            i.updateIslandOwner(players);
            if (i.getOwner() != null) {
                i.getOwner().addToScore(1);
            }
        }
    }

}
