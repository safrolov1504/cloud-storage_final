
import Controllers.ChangeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        ChangeStage.changeStageDo(primaryStage, "/loginInterface.fxml","Welcome PC");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
