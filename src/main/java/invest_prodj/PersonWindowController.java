package invest_prodj;

import invest_prodj.model.Person;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PersonWindowController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public ObservableList<Person> list_for_person;

    public PersonService personService;

    //Таблицы
    @FXML
    public TableView<Person> person_table;

    //Колонки таблицы
    @FXML
    public TableColumn<Person,String> person_table_name;
    @FXML
    public TableColumn<Person,String> person_table_phone;
    @FXML
    public TableColumn<Person,String> person_table_note;
    @FXML
    public TableColumn<Person,Integer> person_table_id;


    //Текста
    @FXML
    public Label time_label;
    @FXML
    public TextField person_textfield_name;
    @FXML
    public TextField person_textfield_phone;
    public TextField person_textfield_note;

    //Кнопки
    @FXML
    public Button main_window_btn;
    @FXML
    public Button investment_window_btn;
    @FXML
    public Button exit_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        personService = new PersonService();

        person_table_name.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
        person_table_phone.setCellValueFactory(new PropertyValueFactory<Person,String>("phone_number"));
        person_table_note.setCellValueFactory(new PropertyValueFactory<Person,String>("note"));
        person_table_id.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id"));

        show_all_person();
        Timenow();
    }

    public void change_window_to_investment(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(InvestmentWindowController.class.getResource("investment_window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Редактор вложений");
        stage.setScene(scene);
        stage.show();
    }

    public void change_window_to_main(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("investment_main_window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Меню управления капиталом");
        stage.setScene(scene);
        stage.show();
    }

    public void take_person_from_table(MouseEvent event){
        Person person = person_table.getSelectionModel().getSelectedItem();
        person_textfield_name.setText(person.getName());
        person_textfield_phone.setText(person.getPhone_number());
        person_textfield_note.setText(person.getNote());
    }

    public void create_person(ActionEvent e){
        Person person = new Person(person_textfield_name.getText(),person_textfield_note.getText(),person_textfield_phone.getText());
        personService.savePerson(person);
        //rsonService.updatePerson(person);
        show_all_person();

    }

    public void change_person(ActionEvent e){
        Person person = person_table.getSelectionModel().getSelectedItem();
        person.setName(person_textfield_name.getText());
        person.setNote(person_textfield_note.getText());
        person.setPhone_number(person_textfield_phone.getText());
        personService.updatePerson(person);
        show_all_person();


    }

    public void delete_person(ActionEvent e){
        Person person = person_table.getSelectionModel().getSelectedItem();
        personService.deletePerson(person_table.getSelectionModel().getSelectedItem());
        show_all_person();


    }

    public void close_program(ActionEvent e){
        System.exit(0);
    }

    public void show_all_person(){
        list_for_person = FXCollections.observableArrayList();
        for(Person i:personService.findAllPersons()){
            list_for_person.add(i);
        }
        person_table.setItems(list_for_person);
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
}
