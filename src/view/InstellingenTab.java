package view;

import controller.Controller;
import controller.Properties;
import model.domain.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Collection;

public class InstellingenTab implements EventHandler<ActionEvent> {
    private Tab tab, kassaTab;
    private Controller controller = new Controller();
    private ArtikelTab artikelTab;
    private KortingStrat kortingStrat;
    private TextField groepKortingText, groepText, drempelKortingText, drempelText, duursteKortingText;
    private KortingStratFactory kortingStratFactory = new KortingStratFactory();
    private KassaTab kassa;
    private Collection<Artikel> bestelling;
    public InstellingenTab(Tab tab, Tab kassaTab) throws IOException {
        this.kassaTab = kassaTab;
        Button excel = new Button();
        excel.setText("Excel");
        excel.setOnAction(new Properties());
        Button text = new Button();
        text.setText("Text");
        text.setOnAction(new Properties());
        Label dbChoice = new Label("Database");

        Label groepKortingLabel = new Label("Groepkorting");
        groepKortingText = new TextField("5");
        Label groepLabel = new Label("Groep");
        groepKortingText.setMaxWidth(50);
        groepText = new TextField("gr1");
        groepText.setMaxWidth(50);
        Button groepKortingButton = new Button("Bereken groepkorting");

        Label drempelKortingLabel = new Label("Drempelkorting");
        drempelKortingText = new TextField("5");
        Label drempelLabel = new Label("Drempel in euro");
        drempelText = new TextField("100");
        drempelText.setMaxWidth(50);
        drempelKortingText.setMaxWidth(50);
        Button drempelKortingButton = new Button("Bereken drempelkorting");

        Label duursteKortingLabel = new Label("duurstekorting");
        duursteKortingText = new TextField("25");
        duursteKortingText.setMaxWidth(50);
        Button duursteKortingButton = new Button("Bereken duurstekorting");

        groepKortingButton.setOnAction(this);
        drempelKortingButton.setOnAction(this);
        duursteKortingButton.setOnAction(this);

        GridPane root = new GridPane();
        root.addRow(0, dbChoice, excel, text);
        root.addRow(1, groepKortingLabel, groepKortingText);
        root.addRow(2, groepLabel, groepText, groepKortingButton);
        root.addRow(3, drempelKortingLabel, drempelKortingText);
        root.addRow(4, drempelLabel, drempelText, drempelKortingButton);
        root.addRow(5, duursteKortingLabel, duursteKortingText, duursteKortingButton);

        tab.setContent(root);
    }

    @Override
    public void handle(ActionEvent event) {
        double kortingTotal;
        String strat = ((Button)event.getSource()).getText();
        double groepKorting = Double.parseDouble(groepKortingText.getText());
        double drempelKorting = Double.parseDouble(drempelKortingText.getText());
        double duursteKorting = Double.parseDouble(duursteKortingText.getText());
        try {
            bestelling = controller.getOnHold("data//KortingKlaar");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            kortingStrat = kortingStratFactory.makeKorting(groepText.getText(),
                    Double.parseDouble(drempelText.getText()),
                    strat,
                    groepKorting,
                    drempelKorting,
                    duursteKorting);
            controller.setKortingTotal(kortingStrat.applyKorting(bestelling));
            controller.putOnHold(bestelling, "data//KortingKlaar");
            //System.out.println("grootte bestelling: " + bestelling.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
