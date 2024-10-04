package main.java;

/**
 * UseIsland
 */
public class UseIsland {
    public static void main(String[] args) throws WrongInputOfCharacter{
        Player p1 = new Player("Titi");
        Player p2 = new Player("Toto");
        Player p3 = new Player("Tutu");
        Island i1 = new Island();
        System.out.println(i1);
        i1.takingTerrain(p1.getCar(), 7, Color.BLUE);
        System.out.println(i1);
        i1.takingTerrain(p2.getCar(), 6, Color.RED);
        System.out.println(i1);
        System.out.println(i1.getIslandOwner(new Player[] {p1,p2}));
    }
}

