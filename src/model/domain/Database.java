package model.domain;

public abstract class Database implements LoadSave{

    /*private DBInMem dbInMem;
    private Artikel artikel;

    public void save() {
        try {
            dbInMem.save(artikel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            dbInMem.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Artikel> getList(){
        return dbInMem.getArtikelByCode().values();
    }
    */

}
