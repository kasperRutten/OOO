package model.domain;


import model.domain.DB.DBInMem;
import model.domain.DB.ExcelPlugin;

public class DatabaseFactory {
    public Database makeDatabase(DBInMem databaseText, ExcelPlugin databaseExcel, String newDatabaseType){

        if (newDatabaseType.equalsIgnoreCase("text")){
            //System.out.println("in factory, text");
            return TextLoadSave.getInstance(databaseText);
        }
        else if (newDatabaseType.equalsIgnoreCase("excel")){
            //System.out.println("in factory, excel");
            return ExcelLoadSave.getInstance(databaseExcel);
        }
        else{
            System.out.println("failed");
            return null;
        }
    }
}
