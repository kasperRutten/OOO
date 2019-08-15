package model.domain;

public class Artikel {
    private int actVoorraad, id;
    private String omschrijving, artikelGroep, code;
    private double prijs;

    public Artikel(String code, String omschrijving, String artikelGroep, double prijs, int actVoorraad, int id) {
        setCode(code);
        setActVoorraad(actVoorraad);
        setOmschrijving(omschrijving);
        setArtikelGroep(artikelGroep);
        setPrijs(prijs);
        setId(id);
    }

    public Artikel(String code, String omschrijving, String artikelGroep, double prijs, int actVoorraad) {
        setCode(code);
        setActVoorraad(actVoorraad);
        setOmschrijving(omschrijving);
        setArtikelGroep(artikelGroep);
        setPrijs(prijs);
    }

    public Artikel(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getActVoorraad() {
        return actVoorraad;
    }

    public void setActVoorraad(int actVoorraad) {
        this.actVoorraad = actVoorraad;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getArtikelGroep() {
        return artikelGroep;
    }

    public void setArtikelGroep(String artikelGroep) {
        this.artikelGroep = artikelGroep;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
