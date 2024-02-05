package invest_prodj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1320, 860);
        stage.setTitle("Главное меню");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("style/img/economy.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main( String[] args )
    {launch();
    }

}
