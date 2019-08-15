package model.domain;

import java.util.Collection;

public class Duurstekorting implements KortingStrat{
    private double korting;

    public Duurstekorting(){

    }

    public Duurstekorting(double korting){
        setKorting(korting);
    }

    public void setKorting(double korting) {
        this.korting = korting;
    }

    public double getKorting(){
        return korting;
    }

    @Override
    public double applyKorting(Collection<Artikel> lijst) {
        Artikel hoogste = new Artikel();
        for (Artikel a : lijst){
            if (a.getPrijs()>hoogste.getPrijs()){
                hoogste = a;
            }
        }
        //System.out.println("prijs duurste: " + hoogste.getPrijs());
        return hoogste.getPrijs()*(korting/100);
    }
}
