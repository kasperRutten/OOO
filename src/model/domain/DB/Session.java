package model.domain.DB;

import model.domain.Artikel;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Session {

    Collection<Artikel> artikelByCode = new ArrayList<>();
    Writer wr;

    public void load(String filePath) throws IOException {
        artikelByCode.clear();
        Artikel value;
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",", 6);
            value = new Artikel(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
            artikelByCode.add(value);
        }
        reader.close();
    }

    public void save(String filePath, Collection<Artikel> list) throws IOException {
        int index = 0;
        wr = new FileWriter(filePath);
        for (Artikel artikel : list){
            String code = artikel.getCode();
            int actVoorraad = artikel.getActVoorraad();
            double prijs = artikel.getPrijs();
            String omschrijving = artikel.getOmschrijving();
            String artikelGroep = artikel.getArtikelGroep();

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
            wr.flush();
            wr.append(",");
            wr.flush();
            wr.append(String.valueOf(index));
            wr.flush();
            wr.append("\n");
            index++;
        }
        wr.close();
    }

    public Collection<Artikel> getArtikelByCode(){
        return artikelByCode;
    }
}
