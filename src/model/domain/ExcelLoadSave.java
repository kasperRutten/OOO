package model.domain;

import model.domain.DB.ExcelPlugin;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExcelLoadSave extends Database implements LoadSave{

    private static ExcelLoadSave singleInstance = null;
    private ExcelPlugin excelPlugin;
    private File file = new File("C:\\Users\\Kasper\\Desktop\\bureau\\Documents\\GitHub\\KassaOOO\\data\\artikel.xls");
    private ArrayList<ArrayList<String>> args;

    private ExcelLoadSave(ExcelPlugin excelPlugin){
        this.excelPlugin = excelPlugin;
    }

    public void save(){
        try {
            excelPlugin.write(file, args);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        System.out.println("in excelload");
        try {
            excelPlugin.read(file);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Artikel> getList(){
        System.out.println("in getList");
        List<Artikel> list = new ArrayList<>();
        System.out.println(args);
        for (int i = 0; i<args.size(); i++){
            list.get(i).setCode(args.get(i).get(0));
            list.get(i).setOmschrijving(args.get(i).get(1));
            list.get(i).setArtikelGroep(args.get(i).get(2));
            list.get(i).setPrijs(Double.parseDouble(args.get(i).get(3)));
            list.get(i).setActVoorraad(Integer.parseInt(args.get(i).get(4)));
        }
        return list;
    }

    public static ExcelLoadSave getInstance(ExcelPlugin excelPlugin){
        if (singleInstance == null){
            singleInstance = new ExcelLoadSave(excelPlugin);
        }
        return singleInstance;
    }

}
