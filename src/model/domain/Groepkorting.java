package model.domain;

import java.io.IOException;
import java.util.Collection;

public class Groepkorting implements KortingStrat {

    private double korting;
    private Collection<Artikel> lijst;
    private String groep;

    public Groepkorting(){

    }

    public Groepkorting(double korting, String groep) throws IOException {
        setKorting(korting);
        setGroep(groep);
    }

    public void setKorting(double korting) {
        this.korting = korting;
    }

    public double getKorting(){
        return korting;
    }

    public String getGroep(){
        return groep;
    }

    public void setGroep(String groep){
        this.groep = groep;
    }
    @Override
    public double applyKorting(Collection<Artikel> lijst) {
        double kortingTotal = 0;
        //System.out.println("grootte lijst: " + lijst.size());
        for (Artikel a : lijst){
            if (a.getArtikelGroep().equalsIgnoreCase(groep)){
                //System.out.println("huidige prijs: " + a.getPrijs());
                kortingTotal = kortingTotal +  a.getPrijs()*(korting/100);
                //System.out.println(kortingTotal);
            }
        }
        return kortingTotal;
    }
}
