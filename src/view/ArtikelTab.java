package view;

import controller.Controller;
import model.domain.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Map;


public class ArtikelTab {

    private Controller controller = new Controller();

    private TableView table;
    private Tab tab;
    private ObservableList<Artikel> data = controller.getArtikels();

    public ArtikelTab(Tab tab) throws IOException {
        this.tab = tab;
        //System.out.println("in artikeltab");
        table = new TableView();

        //Code column
        TableColumn<Map, String> codeColumn = new TableColumn<>("Code");
        codeColumn.setMinWidth(100);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        //Actuele Voorraad column
        TableColumn<Map, String> actVoorraadColumn = new TableColumn<>("Actuele Voorraad");
        actVoorraadColumn.setMinWidth(100);
        actVoorraadColumn.setCellValueFactory(new PropertyValueFactory<>("actVoorraad"));

        //Omschrijving column
        TableColumn<Map, String> omschrijvingColumn = new TableColumn<>("Omschrijving");
        omschrijvingColumn.setMinWidth(100);
        omschrijvingColumn.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));

        //Artikelgroep column
        TableColumn<Map, String> artikelGroepColumn = new TableColumn<>("Artikelgroep");
        artikelGroepColumn.setMinWidth(100);
        artikelGroepColumn.setCellValueFactory(new PropertyValueFactory<>("artikelGroep"));

        //Prijs column
        TableColumn<Map, String> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setMinWidth(20);
        prijsColumn.setCellValueFactory(new PropertyValueFactory<>("prijs"));

        table.getColumns().add(codeColumn);
        table.getColumns().add(omschrijvingColumn);
        table.getColumns().add(artikelGroepColumn);
        table.getColumns().add(prijsColumn);
        table.getColumns().add(actVoorraadColumn);
        table.getSortOrder().add(omschrijvingColumn);

        SortedList<Artikel> sortedData = new SortedList<>(data);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        table.getSortOrder().add(omschrijvingColumn);
        tab.setContent(table);



    }

}
