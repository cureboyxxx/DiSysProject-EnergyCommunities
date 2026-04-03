package at.uastw.javafxgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EnergyGuiApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                EnergyGuiApplication.class.getResource("main-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 500, 300);

        stage.setTitle("Energy Monitor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}