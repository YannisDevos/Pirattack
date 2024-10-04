package main.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IslandPool implements Iterable<Island> {

    private List<Island> pool = new ArrayList<Island>();
    public static final double RATIO = 1.5;

    public IslandPool(int nbPlayers) throws WrongInputOfCharacter{
        for(int i = 0; i < (nbPlayers*IslandPool.RATIO); i++){
            this.pool.add(new Island());
        } 
    }

    public List<Island> getPool() {
        return pool;
    }


    public String toString(){
        String res = "";

        int nbrdelignestotal = 0;

        for (Island i : pool) {
            if (i.getField().length > nbrdelignestotal) {
                nbrdelignestotal = i.getField().length;
            }
        }

        // ligne par ligne
        for (int idx=0 ; idx<nbrdelignestotal ; ++idx) {
            // on parcourt chaque ile
            for (Island i : pool) {
                // on regarde si elle a assez de ligne
                if (i.getField().length > idx) {
                    for (int jdx=0 ; jdx<i.getField()[idx].length ; ++jdx) {
                        res += i.getField()[idx][jdx];
                    }
                }else{
                    for (int jdx=0 ; jdx<i.getField()[0].length ; ++jdx) {
                        res += " ";
                    }
                }
                res += "   ";
            }
            res += "\n";
        }
        return res + "\n";
    }

    @Override
    public Iterator<Island> iterator() {
        return this.pool.iterator();
    }

    public Island getIsland(int i){
        return this.pool.get(i);
    }

    public int size() {
        return this.pool.size();
    }

}
