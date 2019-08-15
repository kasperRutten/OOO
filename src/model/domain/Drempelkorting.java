package model.domain;

import controller.Controller;

import java.io.IOException;
import java.util.Collection;

public class Drempelkorting implements KortingStrat {
    private Controller controller = new Controller();
    private double korting;
    private double drempel;

    public Drempelkorting() throws IOException {

    }

    public Drempelkorting(double korting, double drempel) throws IOException {
        setKorting(korting);
        setDrempel(drempel);
    }

    public void setKorting(double korting) {
        this.korting = korting;
    }

    public double getKorting(){
        return korting;
    }

    public void setDrempel(double drempel){
        this.drempel = drempel;
    }

    public double getDrempel(){
        return drempel;
    }

    @Override
    public double applyKorting(Collection<Artikel> lijst) {
        double total = Double.parseDouble(controller.getTotal(lijst));
        System.out.println("total: " + total);
        if (total > drempel){
            System.out.println("Het is hoger dan 100");
            total  = total* (korting/100);
        }
        return total;
    }
}
