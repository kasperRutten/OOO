package application;

import javafx.application.Application;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import view.KassaTab;
import view.KassaView;
import view.KlantView;

import java.io.IOException;

public class Main extends Application {
    private KassaTab kassaTab = new KassaTab(new Tab());
    public Main() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            KassaView kassaView = new KassaView();
            KlantView klantView = new KlantView(kassaTab);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
