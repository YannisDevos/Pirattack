package main.java;

public class ReusableBonus extends Bonus {
    
    int cpttours;

    public ReusableBonus(TypeBonus type, int valueBonus) {
        super(type, valueBonus);
        this.cpttours = type.nbtours;
        this.reusable = true;
    }

    public boolean usableBonus(){
        if(this.cpttours == this.type.nbtours){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if(this.type.nbtours == 0){
            return super.toString();
        }
        if(usableBonus()){
            return super.toString() + " utilisable maintenant";
        }
        return super.toString() + " utilisable dans "  + (this.type.nbtours - this.cpttours) + "tours";
        
    }

    public void incrementBonusTour(){
        this.cpttours = this.cpttours + 1;
    }

}
