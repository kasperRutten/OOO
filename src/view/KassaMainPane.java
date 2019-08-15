package view;


import controller.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class KassaMainPane extends BorderPane {


    private Controller controller = new Controller();

    public KassaMainPane() throws IOException {
        TabPane tabPane = new TabPane();
        Tab kassaTab = new Tab("Kassa");
        Tab artikelTab = new Tab("Artikelen");
        Tab instellingTab = new Tab("Instellingen");
        Tab logTab = new Tab("Log");

        new KassaTab(kassaTab);
        new InstellingenTab(instellingTab, kassaTab);
        new ArtikelTab(artikelTab);

        tabPane.getTabs().add(kassaTab);
        tabPane.getTabs().add(artikelTab);
        tabPane.getTabs().add(instellingTab);
        tabPane.getTabs().add(logTab);
        this.setCenter(tabPane);
    }
}
