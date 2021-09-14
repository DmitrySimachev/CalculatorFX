package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.engine.Database;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DBInitialize db = new DBInitialize();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/MainFrame.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Калькулятор");
        primaryStage.setOnCloseRequest(e -> db.close());
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
