package controller;

import model.domain.Artikel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.*;

public class Properties implements EventHandler<ActionEvent> {

    String type;
    String filePath = "data//Properties";
    Writer wr;

    public void load() throws IOException {
        Artikel value;
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            type = line;
        }
        reader.close();
    }

    public void change(String type) throws IOException {
        //System.out.println("in change");
        wr = new FileWriter(filePath);
        wr.append(type);
        wr.close();
    }

    public String getType(){
        return type;
    }

    @Override
    public void handle(ActionEvent event) {
        //System.out.println("in properties");
        try {
            this.change(((Button)event.getSource()).getText());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}