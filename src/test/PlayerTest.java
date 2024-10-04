package test;

import java.util.ArrayList;

import main.java.Player;
import main.java.WrongInputOfCharacter;
import main.java.tooManyPlayersInThisList;

public class PlayerTest {
    public static void main(String[] args) throws WrongInputOfCharacter, tooManyPlayersInThisList {
        // Create test players
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Player player3 = new Player("Charlie");

        // Set scores and winner status
        player1.addToScore(10);
        player2.addToScore(20);
        player3.addToScore(30);

        // Print initial player details
        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);

        // Save players to file
        player1.savePlayer(player1);
        player2.savePlayer(player2);
        player3.savePlayer(player3);

        // Load players from file
        Player loadedPlayer = new Player("Temp");
        loadedPlayer.loadingPlayer("Bob");
        System.out.println("Loaded Player: " + loadedPlayer);

        // Prepare list of players for history saving
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        player3.changeToWinner();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);

        // Save players in history
        player1.saveInHistory(listOfPlayers);
    }
}
