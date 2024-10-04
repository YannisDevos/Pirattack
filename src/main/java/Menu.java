package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static String title = Display.TITLE_ASCII;

    private static String regles = "" +
    "Pirattack est un jeu de stratégie au tour par tour où le but est de récupérer un maximum d'îles tout en empêchant les adversaires d'en faire autant.\n" +
    "Pour gagner une partie, il suffit de posséder le plus d'îles dans la totalité de l'archipel.\n" +
    "Quand vous prenez possession d'une île, cela vous apporte un bonus.\n" +
    "Des trésors se cachent sur les îles, à vous de les trouver pour remporter des bonus.\n\n" +
    "Liste des Bonus :\n\n" +
    "Bonus possession d'îles :\n" +
    "    -    Ajoute une valeur au dé (1 - 3)\n" +
    "    -    x2 (4 tours)\n" +
    "    -    Ajoute une face à votre dé\n\n" +
    "Bonus Trésors :\n" +
    "    -    1 à 5 points ajoutés au roll actuel.\n"
    ;

    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void afficherHistory(){
        String csvFile = "res/history/history.csv";
        String ligne = "";
        List<String> lignesCSV = new ArrayList<>();  // Utilisation d'une liste pour stocker les lignes du CSV
    
        try (FileReader file=new FileReader(csvFile);BufferedReader br = new BufferedReader(file)) {
            // Lire le fichier CSV et stocker chaque ligne dans la liste
            while ((ligne = br.readLine()) != null) {
                lignesCSV.add(ligne);
           }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // Afficher le contenu du CSV de la dernière ligne à la première
        for (String lignes : lignesCSV) {
            String str[]=lignes.split(",");
            for(int i=0;i<str.length;i++){
                if(i==1){
                    System.out.print(" as won against ");
                }
                System.out.print(str[i]+" ");
            }
            System.out.println();
        }
    }

    /*
     * Demande une input au joueur et la return
     * retourne -1 si l'input est invalide
     */
    private static int makeChoice() {
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        try{
            choice = sc.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Entrée invalide !");
        } catch(NullPointerException e){
            System.out.println("null pointer");
        } finally {
            //sc.close();
        }
        return choice;
    }

    /*
     * Pose une question au joueur et la repete tant que
     * le joueur ne donne pas de reponse valide
     */
    public static int askQuestion(String prompt) {
        int choice = -1;
        while (choice == -1) {
            System.out.print(prompt);
            choice = makeChoice();
        };
        clearTerminal();
        return choice;
    }

    /*
     * Pose une question et demande confirmation
     */
    public static String askInput(String question) {
        String input = "";
        boolean confirmationConfirmee = false;
        while (!confirmationConfirmee) {
            clearTerminal();
            System.out.print(question);
            Scanner sc = new Scanner(System.in);
            try {
                input = sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
            confirmationConfirmee = Menu.askQuestion(Menu.makePrompt("Êtes-vous sûr ?", "Oui", "Non")) == 1;
        };
        clearTerminal();
        return input;
    }

    /*
     * Cette fonction sert a creer un prompt de question facilement
     * Elle prend en parametre la question puis toutes ses propositions
     * ou les choix possibles quoi
     */
    public static String makePrompt(String question, String... propositions) {
        String result = question + "\n";
        int idx = 1;
        for (String p : propositions) {
            result += "[" + idx++ + "] " + p + "\n";
        }
        result += "\n" + "Choix : ";
        return result;
    }

    /*
     * Lance le menu
     */
    public static void lancerMenu() throws WrongInputOfCharacter {
        clearTerminal();
        int choice = 0;
        while (choice != 4) {
            choice = askQuestion(makePrompt(title, "Jouer", "Regles", "Historique", "Quitter"));
            if (choice == 1) {
                int nbrDeJoueurs = 0;
                while (nbrDeJoueurs < 1 || nbrDeJoueurs > 6) {
                    nbrDeJoueurs = askQuestion("Nombre de joueurs (1 à 6) : ");
                }
                int nbrDeTours = 5 + 5*askQuestion(makePrompt("Choisissez la durée de la partie", "10 tours", "15 tours", "20 tours", "25 tours"));
                Game partie = new Game(nbrDeJoueurs, nbrDeTours);
                Player.forbiddenCars = new ArrayList<Character>();
                partie.start();
            }
            if (choice == 2) {
                System.out.println(Display.REGLES + regles);
                boolean retour = false;
                while (!retour) {
                    retour = askQuestion("[1] Retour\nChoix : ") == 1;
                }
            }
            if (choice == 3) {
                System.out.println(Display.HISTORIQUE);
                afficherHistory();
                boolean retour = false;
                while (!retour) {
                    retour = askQuestion("[1] Retour\nChoix : ") == 1;
                }
            }
        };
    }
}