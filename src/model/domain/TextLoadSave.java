package model.domain;

import model.domain.DB.DBInMem;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;


public class TextLoadSave extends Database implements LoadSave {

    private static TextLoadSave singleInstance = null;
    private DBInMem dbInMem;
    private Artikel artikel;
    private HashMap<String, Artikel> artikelByCode = new HashMap<>();

    private TextLoadSave(DBInMem dbInMem){
        this.dbInMem = dbInMem;
    }

    public void save() {
        try {
            dbInMem.save(artikel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            //System.out.println("in load");
            dbInMem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Artikel> getList(){
        //System.out.println("in getList");
        //System.out.println(dbInMem.getArtikelByCode().size());
        return dbInMem.getArtikelByCode().values();
    }

    public static TextLoadSave getInstance(DBInMem dbInMem){
        if (singleInstance==null){
            singleInstance = new TextLoadSave(dbInMem);
        }
        return singleInstance;
    }

}
