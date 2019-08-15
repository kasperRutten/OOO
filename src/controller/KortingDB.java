package controller;

import java.io.*;

public class KortingDB {
    Writer wr;
    private double korting;

    public void load(String filePath) throws IOException {
        korting = 0;
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            korting = Double.parseDouble(line);
        }
        reader.close();
    }

    public void save(String filePath, double korting) throws IOException {
        wr = new FileWriter(filePath);
        wr.flush();
        wr.append(String.valueOf(korting));
        wr.close();
    }

    public double getKorting(){
        return korting;
    }
}
