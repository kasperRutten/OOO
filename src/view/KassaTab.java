package view;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import controller.Controller;
import model.domain.Artikel;
import model.domain.Observer;
import model.domain.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class KassaTab implements EventHandler<ActionEvent>, Subject {
    private Controller controller = new Controller();
    private Tab tab;
    private TableView table;
    private ObservableList<Artikel> data = controller.getArtikels();
    private ObservableList<Artikel> bestelling = FXCollections.observableArrayList();
    private TextField codeText;
    private TextField codeDelete;
    private GridPane root = new GridPane();
    private VBox bigRoot = new VBox(5);
    private Label totalLabel = new Label("");
    private Label errorLabel = new Label("");
    private ArrayList<Observer> observers = new ArrayList<>();
    private Artikel artikel;

    public KassaTab(Tab tab) throws IOException {
        this.tab = tab;
        Label codeLabel = new Label("Zoek het artikel met code: ");
        Label codeDeletionLabel = new Label("Verwijder het artikel met code: ");
        codeText = new TextField();
        codeDelete = new TextField();
        Button search = new Button("Zoek");
        search.setOnAction(this);
        Button delete = new Button("Verwijder");
        delete.setOnAction(this);
        Button putOnHold = new Button("Onthoud deze transactie");
        putOnHold.setOnAction(this);
        Button getOnHold = new Button("Haal opgeslagen transactie op");
        getOnHold.setOnAction(this);
        Button getKorting = new Button("Pas korting toe en sluit af");
        getKorting.setOnAction(this);

        root.addRow(0, errorLabel);
        root.addRow(1, totalLabel);
        root.addRow(2, codeLabel, codeText, search);
        root.addRow(3, codeDeletionLabel, codeDelete, delete);
        root.addRow(4, putOnHold);
        root.addRow(5, getOnHold);
        root.addRow(6, getKorting);


        tab.setContent(root);
    }

    @Override
    public void handle(ActionEvent event) {
        bigRoot.getChildren().remove(table);
        if (((Button)event.getSource()).getText().equals("Zoek")) {
            String code = codeText.getCharacters().toString();
            //System.out.println("code in handler: " + code);
            //System.out.println("findArtikelByCode: " + controller.findArtikelByCode(code).getOmschrijving());

            if (controller.findArtikelByCode(code).getOmschrijving() == null) {
                //System.out.println("error");
                errorLabel.setText("Niet bestaande code");
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //System.out.println("geen error");
                bestelling.add(controller.findArtikelByCode(code));
                notifyObserver();
                errorLabel.setText("");
                String total = controller.getTotal(bestelling);
                totalLabel.setText("Te betalen: " + total + " euro");
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (((Button)event.getSource()).getText().equals("Verwijder")){
            //System.out.println("in verwijder met code: " + ((Button)event.getSource()).getText());
            try {
                setBestelling(controller.getOnHold("data//KortingKlaar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String code = codeDelete.getCharacters().toString();
            for (Artikel a : bestelling){
                if (a.getCode().equals(code)){
                    bestelling.remove(a);
                    String total = controller.getTotal(bestelling);
                    totalLabel.setText("Te betalen: " + total + " euro");
                    try {
                        update();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if (((Button)event.getSource()).getText().equals("Onthoud deze transactie")){
            //System.out.println("in opslaan");
            try {
                controller.putOnHold(bestelling, "data//OnHold");
                bestelling.clear();
                String total = controller.getTotal(bestelling);
                totalLabel.setText("Te betalen: " + total + " euro");
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (((Button)event.getSource()).getText().equals("Haal opgeslagen transactie op")){
            try {
                setBestelling(controller.getOnHold("data//OnHold"));
                String total = controller.getTotal(bestelling);
                totalLabel.setText("Te betalen: " + total + " euro");
                //System.out.println("grootte bestelling: " + bestelling.size());
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (((Button)event.getSource()).getText().equals("Pas korting toe en sluit af")){
            try {
                double total = Double.parseDouble(controller.getTotal(bestelling));
                //System.out.println("in afsluit");
                totalLabel.setText("Totaalprijs: " +
                        total +
                        " euro, korting: " +
                        controller.getKortingTotal() +
                        " euro, totaalprijs inclusief korting: " +
                        (total -controller.getKortingTotal()) +
                        " euro.");
                printBestelling();
                controller.setKortingTotal(0);
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() throws IOException {
        table = new TableView();
        bigRoot.getChildren().removeAll(root, table);

        //Omschrijving column
        TableColumn<Map, String> omschrijvingColumn = new TableColumn<>("Omschrijving");
        omschrijvingColumn.setMinWidth(100);
        omschrijvingColumn.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));

        //Prijs column
        TableColumn<Map, String> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setMinWidth(20);
        prijsColumn.setCellValueFactory(new PropertyValueFactory<>("prijs"));

        table.getColumns().add(omschrijvingColumn);
        table.getColumns().add(prijsColumn);
        table.getSortOrder().add(omschrijvingColumn);

        SortedList<Artikel> sortedBestelling = new SortedList<>(bestelling);
        sortedBestelling.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedBestelling);
        table.getSortOrder().add(omschrijvingColumn);

        controller.putOnHold(bestelling, "data//KortingKlaar");

        bigRoot.getChildren().addAll(root, table);
        tab.setContent(bigRoot);

    }

    private void printBestelling() throws IOException {
        double total = Double.parseDouble(controller.getTotal(bestelling));
        Collection<Artikel> fakeList = new ArrayList<>();
        int count;
        System.out.println("_____________________________________________________________");
        System.out.println("|                                                            |");
        System.out.println("|   Omschrijving            Aantal      Prijs                |");
        System.out.println("|   *****************************************                |");
        System.out.println("|                                                            |");
        for (Artikel a : bestelling) {
            count = 0;
            for (Artikel b : bestelling) {
                if (a == b) {
                    count++;
                }
            }
            if (!fakeList.contains(a)){
                System.out.println("|   " +    a.getOmschrijving() + controller.getOmschrijvingKolom(a.getOmschrijving()) + count + controller.getAantalKolom(String.valueOf(count)) + a.getPrijs() + controller.getPrijsKolom(String.valueOf(a.getPrijs())) + "|");
                fakeList.add(a);
            }
        }
        System.out.println("|   *****************************************                |");
        System.out.println("|                                                            |");
        System.out.println("|   Betaald (inclusief korting) : "  + (total - controller.getKortingTotal()) + controller.getTotaalKolom(String.valueOf((total - controller.getKortingTotal()))) + "|");
        System.out.println("_____________________________________________________________");

    }

    @Override
    public void register(Observer o) {
        //System.out.println("in register: ");
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        int obsIndex = observers.indexOf(o);
        observers.remove(obsIndex);
    }

    @Override
    public void notifyObserver() {
        //System.out.println("in notify, " + observers.size() + " observers");
        for (Observer o : observers){
            o.update(artikel);
        }
    }

    public void setArtikel(Artikel artikel){
        this.artikel = artikel;
        notifyObserver();
    }

    public void setBestelling(ObservableList<Artikel> list){
        this.bestelling = list;
    }

    public Collection<Artikel> getBestelling(){
        System.out.println("in getBestelling");
        return bestelling;
    }
}
