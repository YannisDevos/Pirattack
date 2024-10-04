
package main.java;


public class Display {
    public final static String TITLE_ASCII = AsciiReader.read("./res/Pirattack.txt");
    public final static String REGLES = AsciiReader.read("./res/regles.txt");
    public final static String HISTORIQUE = AsciiReader.read("./res/historique.txt");
    public final static String WINNER = AsciiReader.read("./res/winner.txt");
    private IslandPool islands;
    private Player currentPlayer;
    
    public Display(IslandPool islands, Player player) {
        this.islands = islands;
        this.currentPlayer = player;
    }

    public String getDisplay() {
        String result = TITLE_ASCII;
        result += "\n" + islands;
        result += "Bonus des îles : \n";
        for (int idx=1 ; idx<islands.size()+1 ; ++idx) {
            result += "\t-Île " + idx + " " + islands.getPool().get(idx-1).getBonus() + "\n";
        }
        result += "\n";
        result += "| Au tour de " + currentPlayer.getName() + " |\n";
        return result;
    }
}
