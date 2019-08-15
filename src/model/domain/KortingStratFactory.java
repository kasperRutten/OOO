package model.domain;

import java.io.IOException;

public class KortingStratFactory {
    public KortingStrat makeKorting(String groep,
                                    double drempel,
                                    String strat,
                                    double groepkortingDouble,
                                    double drempelkortingDouble,
                                    double duurstekortingDouble) throws IOException {
        if (strat.equalsIgnoreCase("Bereken groepkorting")) {
            return new Groepkorting(groepkortingDouble, groep);
        } else if (strat.equalsIgnoreCase("Bereken drempelkorting")) {
            return new Drempelkorting(drempelkortingDouble, drempel);
        } else if (strat.equalsIgnoreCase("Bereken duurstekorting")) {
            return new Duurstekorting(duurstekortingDouble);
        } else {
            System.out.println("Korting failed");
            return null;
        }
    }
}
