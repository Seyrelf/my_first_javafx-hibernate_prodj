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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Таблицы
    @FXML
    public TableView<Person> person_table;
    @FXML
    public  TableView<Investment> investment_table;

    //Колонки таблицы
    @FXML
    public TableColumn<Investment,BigDecimal> investment_table_amount;
    @FXML
    public TableColumn<Investment,String> investment_table_date;
    @FXML
    public TableColumn<Investment,String> investment_table_name;
    @FXML
    public TableColumn<Investment,String> investment_table_note;
    @FXML
    public TableColumn<Investment,Double> investment_table_percent;
    @FXML
    public TableColumn<Investment,Integer> investment_table_id;
    @FXML
    public TableColumn<Person,String> person_table_name;
    @FXML
    public TableColumn<Person,Integer> person_table_id;


    //Гифки и изображения

    //Текста
    @FXML
    public Label time_label;
    @FXML
    public Label amount_label;
    @FXML
    public  Label amount_by_person_label;

    //Кнопки
    @FXML
    public Button main_window_btn;
    @FXML
    public Button person_window_btn;
    @FXML
    public Button investment_window_btn;
    @FXML
    public Button show_all_investment_btn;
    @FXML
    public Button exit_btn;

    //@FXML
    //public MediaView media;

    public ObservableList<Person> list_for_person;

    public ObservableList<Investment> list_for_investment;

    public PersonService personService;

    public InvestmentService investmentService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        personService = new PersonService();
        investmentService = new InvestmentService();

        investment_table_amount.setCellValueFactory(new PropertyValueFactory<Investment,BigDecimal>("amount"));
        investment_table_date.setCellValueFactory(new PropertyValueFactory<Investment,String>("data"));
        investment_table_name.setCellValueFactory(new PropertyValueFactory<Investment,String >("name"));
        investment_table_note.setCellValueFactory(new PropertyValueFactory<Investment,String>("note"));
        investment_table_percent.setCellValueFactory(new PropertyValueFactory<Investment,Double>("percent"));
        investment_table_id.setCellValueFactory(new PropertyValueFactory<Investment,Integer>("id"));

        person_table_name.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
        person_table_id.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id"));

        set_amount_label();

        show_all_investment();

        show_all_person();

        Timenow();
    }

    public void change_window_to_person(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(PersonWindowController.class.getResource("person_window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("economy.png")));
        stage.setTitle("Редактор обьектов");
        stage.setScene(scene);
        stage.show();
    }

    public void change_window_to_investment(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(InvestmentWindowController.class.getResource("investment_window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("economy.png")));
        stage.setTitle("Редактор вложений");
        stage.setScene(scene);
        stage.show();
    }

    public void change_window_to_main(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("main_back.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("economy.png")));
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.show();
    }

    public void show_all_investment(ActionEvent e){
        try{
        list_for_investment = FXCollections.observableArrayList();
        for(Investment i:investmentService.findAllInvestments()){
            list_for_investment.add(i);
        }
        investment_table.setItems(list_for_investment);}
        catch (Exception exception){
            System.out.println("Нет вложений");
        }
    }

    public void show_all_investment(){
        try{
        list_for_investment = FXCollections.observableArrayList();
        for(Investment i:investmentService.findAllInvestments()){
            list_for_investment.add(i);
        }
        investment_table.setItems(list_for_investment);}
        catch (Exception e){
            System.out.println("Нет вложений");
        }
    }


    public void show_investment_by_id_mouse_click(MouseEvent e){
        try {
        Person person = person_table.getSelectionModel().getSelectedItem();
        investment_table.setItems(find_investment_by_idp(person.getId()));
        amount_by_person_label.setText("Сумма по обьекту: "+ investmentService.amount_by_person(person.getId()));}
        catch (Exception exception){
            System.out.println("Выберите обьект");
        }
    }

    public ObservableList<Investment> find_investment_by_idp(int id){
        list_for_investment = FXCollections.observableArrayList();
        for(Investment i:investmentService.findInvestmentByIdP(id)){
            list_for_investment.add(i);
        }

        return list_for_investment;
    }


    public void show_all_person(){
        try{
        list_for_person = FXCollections.observableArrayList();
        for(Person i:personService.findAllPersons()){
            list_for_person.add(i);
        }
        person_table.setItems(list_for_person);}
        catch (Exception e){
            System.out.println("Нет обьектов");
        }
    }

    public void set_amount_label(){
        try {
            amount_label.setText("Полная сумма: "+investmentService.amount_sum());
        }
        catch (Exception e) {
            amount_label.setText("Полная сумма: 0");
        }
    }

    public void close_program(ActionEvent e){
        System.exit(0);
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


    /*public void onPlayAudio(MouseEvent event){
        if(media.getMediaPlayer() == null){
            try{
                String file = getClass().getResource("chidori_sound.mp3").toURI().toString();
                Media medias = new Media(file);
                MediaPlayer player = new MediaPlayer(medias);
                media.setMediaPlayer(player);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        media.getMediaPlayer().play();
    }*/

}
