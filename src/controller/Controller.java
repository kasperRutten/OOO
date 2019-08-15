package controller;

import model.domain.DB.DBInMem;
import model.domain.DB.ExcelPlugin;
import model.domain.DB.Session;
import model.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.*;

public class Controller {
    private LoadSave database;
    private DatabaseFactory databaseFactory = new DatabaseFactory();
    private Properties properties = new Properties();
    private Session session = new Session();
    private KortingDB kortingDB = new KortingDB();
    private double kortingTotal;

    public Controller() throws IOException{
        start();
    }

    public void start() throws IOException {
        //System.out.println("in controller");
        properties.load();
        database = databaseFactory.makeDatabase(new DBInMem(), new ExcelPlugin(), properties.getType());
        setDatabase(database);
        database.load();
    }

    public LoadSave getDatabase(){
        return database;
    }

    public ObservableList<Artikel> getArtikels(){
        ObservableList<Artikel> list = FXCollections.observableArrayList();
        Collection<Artikel> values = database.getList();
        ArrayList<Artikel> artikelArrayList = new ArrayList<Artikel>(values);

        for (Artikel a : artikelArrayList){
            list.add(a);
        }
        return list;
    }

    public Artikel findArtikelByCode(String code){
        Artikel result = new Artikel();
        Collection<Artikel> list = database.getList();
        for (Artikel a : list){
            if (a.getCode().equalsIgnoreCase(code)){
                result = a;
            }
        }
        return result;
    }

    public String getTotal(Collection<Artikel> list){
        double totalInt = 0;
        for (Artikel a : list){
            totalInt += a.getPrijs();
        }
        return Double.toString(totalInt);
    }


    public void putOnHold(Collection<Artikel> list, String filePath) throws IOException {
        session.save(filePath, list);
    }

    public ObservableList<Artikel> getOnHold(String filePath) throws IOException {
        session.load(filePath);
        //System.out.println("grootte in voor hold: " + session.getArtikelByCode().size());
        ObservableList<Artikel> list = FXCollections.observableArrayList();
        Collection<Artikel> values = session.getArtikelByCode();
        ArrayList<Artikel> artikelArrayList = new ArrayList<Artikel>(values);
        //System.out.println("grootte in hold: " + artikelArrayList.size());


        for (Artikel a : artikelArrayList){
            list.add(a);
        }
        return list;
    }

    public String getOmschrijvingKolom(String naam){
        int count = 24 - naam.length();
        String result = "";
        for (int i = 0; i<count; i++){
            result += (" ");
        }
        return result;
    }

    public String getAantalKolom(String naam){
        int count = 12 - naam.length();
        String result = "";
        for (int i = 0; i<count; i++){
            result += (" ");
        }
        return result;
    }

    public String getPrijsKolom(String naam){
        int count = 21 - naam.length();
        String result = "";
        for (int i = 0; i<count; i++){
            result += (" ");
        }
        return result;
    }

    public String getTotaalKolom(String naam){
        int count = 27 - naam.length();
        String result = "";
        for (int i = 0; i<count; i++){
            result += (" ");
        }
        return result;
    }

    public void setKortingTotal(double kortingTotal) throws IOException {
        kortingDB.save("data//Korting", kortingTotal);
    }
    public double getKortingTotal() throws IOException {
        kortingDB.load("data//Korting");
        return kortingDB.getKorting();
    }


    public void setDatabase(LoadSave database) {
        this.database = database;
    }




}
