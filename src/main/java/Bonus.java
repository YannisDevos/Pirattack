package main.java;

public class Bonus {
    protected TypeBonus type;
    private int valueBonus;
    protected boolean reusable;

    public Bonus(TypeBonus type, int valueBonus){
        this.type = type;
        this.valueBonus = valueBonus;
        this.reusable = false;
    }

    public String toString(){
        switch (type) {
            case MULTIPY :
               return "vous avez un bonus de multiplication par " + this.valueBonus +  " de votre lancer"; 
            case ADD :
                return "vous avez un bonus qui ajoute " + this.valueBonus + " a votre lancer"; 
            case DIVIDE :
                return "vous avez un malus qui divise par " + this.valueBonus + " de votre lancer";
            case DICE :
                return "vous avez un bonus qui rajoute une face a votre dé";
            case PASS : 
                return "vous avez un malus qui vous fais passer votre tour";
            default : 
                return "";
        }
    }

    public int getValue(){
        return this.valueBonus;
    }
    public TypeBonus getType(){
        return type;
    }

}
