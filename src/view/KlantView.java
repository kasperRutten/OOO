package view;

import controller.Controller;
import model.domain.Artikel;
import model.domain.Observer;
import model.domain.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class KlantView implements Observer {
    private Controller controller = new Controller();
    private Stage stage = new Stage();
    private TableView table;
    private ObservableList<Artikel> bestelling = FXCollections.observableArrayList();
    private Artikel artikel;
    private Label totalLabel = new Label("Te betalen: 0 euro");
    private GridPane root = new GridPane();
    private VBox bigRoot = new VBox(5);
    private static int observerIDTracker = 0;
    private int observerID;
    private Subject kassaTab;


    public KlantView(Subject kassaTab) throws IOException {
        this.kassaTab = kassaTab;
        this.observerID = ++observerIDTracker;
        kassaTab.register(this);
        stage.setTitle("KLANT VIEW");
        stage.setResizable(false);
        stage.setX(775);
        stage.setY(20);
        Group root = new Group();

        open();
        root.getChildren().add(bigRoot);

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public void open(){
        table = new TableView();
        root.addRow(0, totalLabel);

        //Omschrijving column
        TableColumn<Map, String> omschrijvingColumn = new TableColumn<>("Omschrijving");
        omschrijvingColumn.setMinWidth(100);
        omschrijvingColumn.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));

        //Prijs column
        TableColumn<Map, String> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setMinWidth(20);
        prijsColumn.setCellValueFactory(new PropertyValueFactory<>("prijs"));

        //Aantal
        TableColumn<Map, String> aantalColumn = new TableColumn<>("Aantal");
        aantalColumn.setMinWidth(100);
        aantalColumn.setCellValueFactory(new PropertyValueFactory<>("aantal"));

        table.getColumns().add(omschrijvingColumn);
        table.getColumns().add(prijsColumn);
        table.getColumns().add(aantalColumn);
        table.getSortOrder().add(omschrijvingColumn);

        SortedList<Artikel> sortedBestelling = new SortedList<>(bestelling);
        sortedBestelling.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedBestelling);
        table.getSortOrder().add(omschrijvingColumn);

        String total = controller.getTotal(bestelling);
        totalLabel.setText("Te betalen: " + total + " euro");

        bigRoot.getChildren().addAll(root, table);

    }

    @Override
    public void update(Artikel artikel) {
        System.out.println("in update");
        for (Artikel a : bestelling){
            if (artikel.getCode().equals(a.getCode())){
                a.setActVoorraad(a.getActVoorraad()+1);
            }
            else{
                artikel.setActVoorraad(1);
                bestelling.add(artikel);
            }
        }
    }

}
