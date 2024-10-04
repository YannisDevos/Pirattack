package main.java;

public class UseBonus {
    public static void main(String[] args) {
        ReusableBonus b1 = new ReusableBonus(TypeBonus.MULTIPY,2);
        ReusableBonus b2 = new ReusableBonus(TypeBonus.ADD,1);
        ReusableBonus b3 = new ReusableBonus(TypeBonus.DIVIDE,2);
        ReusableBonus b4 = new ReusableBonus(TypeBonus.DICE,4);
        ReusableBonus b5 = new ReusableBonus(TypeBonus.PASS,4);

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b4);
        System.out.println(b5);

        b1.cpttours = 0;

        System.out.println(b1);

        for(int i = 0 ;i<4;i++){
            b1.incrementBonusTour();
            System.out.println(b1); 
        }
    }
}
