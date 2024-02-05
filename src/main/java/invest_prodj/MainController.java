package invest_prodj;

import invest_prodj.model.Investment;
import invest_prodj.model.Person;
import invest_prodj.service.InvestmentService;
import invest_prodj.service.PersonService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public ObservableList<Person> list_for_person;

    public ObservableList<Investment> list_for_investment;

    public PersonService personService;

    public InvestmentService investmentService;

    public MediaPlayer mediaPlayer;

    //Текста
    @FXML
    public Label time_label;
    @FXML
    public Label dollar_label;
    @FXML
    public Label hkdollar_label;
    @FXML
    public Label btn_label;
    @FXML
    public Label ton_label;
    @FXML
    public Label my_capital_label;
    @FXML
    public Label mexc_capital_label;
    @FXML
    public Label tinkoff_capital_label;
    @FXML
    public Label study_week_label;
    @FXML
    public Label sport_week_label;
    @FXML
    public Label purpose_label;
    @FXML
    public TextField browser_find_textfield;

    //Кнопки
    @FXML
    public Button diary_btn;
    @FXML
    public Button investment_btn;
    @FXML
    public Button note_btn;
    @FXML
    public Button exit_btn;
    @FXML
    public Button find_btn;
    @FXML
    public Button refresh_btn;
    @FXML
    public Button back_btn;
    @FXML
    public Button zooomIn_btn;
    @FXML
    public Button zooomOut_btn;



    @FXML
    public WebView browser_webview;

    public WebEngine engine;
    public double webZoom;

    public WebHistory history;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        engine = browser_webview.getEngine();
        engine.load("http://google.com");
        webZoom = 1;


        personService = new PersonService();
        investmentService = new InvestmentService();
        Timenow();

    }


    private void Timenow(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(true){
                try{
                    Thread.sleep(1000);}
                catch (Exception e){
                    System.out.println(e);}
                Platform.runLater(()->{time_label.setText(sdf.format(new Date()));});}});
        thread.start();
    }

    public void loadPage(){

        engine.load("http://" + browser_find_textfield.getText()+".com");
    }

    public void switch_window_to_investment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("investment_main_window.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Меню управления капиталом");
        stage.setScene(scene);
        stage.show();
    }
    public void load_btn(ActionEvent event){
        try {
            loadPage();
        }
        catch (Exception exception){
            System.out.println("Пустая строка поиска");
        }
    }

    public void reload_web_window(ActionEvent event){
        engine.reload();

    }

    public void zoomIn(ActionEvent event){
        webZoom += 0.25;
        browser_webview.setZoom(webZoom);
    }

    public void zoomOut(ActionEvent event){
        webZoom -= 0.25;
        browser_webview.setZoom(webZoom);
    }

    public void back(ActionEvent event){
        try {
            history = engine.getHistory();
            ObservableList<WebHistory.Entry> entries = history.getEntries();
            history.go(-1);
            browser_find_textfield.setText(entries.get(history.getCurrentIndex()).getUrl());
        }
        catch (Exception e){
            System.out.println("Некуда переходить");
        }}
    public void forward(ActionEvent event){
        try {
            history = engine.getHistory();
            ObservableList<WebHistory.Entry> entries = history.getEntries();
            history.go(1);
            browser_find_textfield.setText(entries.get(history.getCurrentIndex()).getUrl());
        }
        catch (Exception e){
            System.out.println("Некуда переходить");
        }

    }

    public void close_program(ActionEvent e){
        System.exit(0);
    }


}
