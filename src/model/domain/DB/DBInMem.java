package model.domain.DB;

import model.domain.Artikel;

import java.io.*;
import java.util.HashMap;

public class DBInMem {

    HashMap<String, Artikel> artikelByCode = new HashMap<>();
    String filePath = "data//ArtikelTxt";
    Writer wr;

    public void load() throws IOException {
        Artikel value;
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",", 5);
            String key = parts[0];
            value = new Artikel(key, parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
            artikelByCode.put(key, value);
        }
        reader.close();
    }

    public void save(Artikel artikel) throws IOException {
        String code = artikel.getCode();
        int actVoorraad = artikel.getActVoorraad();
        double prijs = artikel.getPrijs();
        String omschrijving = artikel.getOmschrijving();
        String artikelGroep = artikel.getArtikelGroep();

        wr = new FileWriter(filePath);
        wr.append("\n");
        wr.flush();
        wr.append(String.valueOf(code));
        wr.flush();
        wr.append(",");
        wr.flush();
        wr.append(omschrijving);
        wr.flush();
        wr.append(",");
        wr.flush();
        wr.append(artikelGroep);
        wr.flush();
        wr.append(",");
        wr.flush();
        wr.append(String.valueOf(prijs));
        wr.flush();
        wr.append(",");
        wr.flush();
        wr.append(String.valueOf(actVoorraad));
        wr.close();
    }

    public HashMap<String, Artikel> getArtikelByCode(){
        return artikelByCode;
    }
}
