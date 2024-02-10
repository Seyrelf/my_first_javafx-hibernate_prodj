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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class InvestmentWindowController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public ObservableList<Investment> list_for_investment;

    public InvestmentService investmentService;

    public PersonService personService;
    public ObservableList<Person> list_for_person;


    //Колонки и таблицы
    @FXML
    public TableView<Investment> investment_table;
    @FXML
    public TableColumn<Investment, BigDecimal> investment_table_amount;
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
    public TableColumn<Investment,Integer> investment_table_person_id;

    //Текстовые обьекты
    @FXML
    public Label time_label;
    @FXML
    public TextField investment_textfield_name;
    @FXML
    public TextField investment_textfield_amount;
    @FXML
    public TextField investment_textfield_percent;
    @FXML
    public TextField investment_textfield_note;
    @FXML
    public DatePicker investment_datepicker_name;
    @FXML
    public ComboBox<Person> investment_combobox_name;

    //Кнопки
    @FXML
    public Button main_window_btn;
    @FXML
    public Button person_window_btn;
    @FXML
    public Button exit_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        investmentService = new InvestmentService();
        personService = new PersonService();

        investment_table_amount.setCellValueFactory(new PropertyValueFactory<Investment,BigDecimal>("amount"));
        investment_table_date.setCellValueFactory(new PropertyValueFactory<Investment,String>("data"));
        investment_table_name.setCellValueFactory(new PropertyValueFactory<Investment,String >("name"));
        investment_table_note.setCellValueFactory(new PropertyValueFactory<Investment,String>("note"));
        investment_table_percent.setCellValueFactory(new PropertyValueFactory<Investment,Double>("percent"));
        investment_table_person_id.setCellValueFactory(new PropertyValueFactory<Investment,Integer>("person_id"));


        show_all_investment();
        show_all_person();
        Timenow();   }

    public void change_window_to_person(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(PersonWindowController.class.getResource("person_window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Редактор обьектов");
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

    public void create_investment(ActionEvent e){
        Investment investment = new Investment(investment_textfield_name.getText(),investment_textfield_note.getText(),
                new BigDecimal(investment_textfield_amount.getText()),new Double(investment_textfield_percent.getText()),
                investment_combobox_name.getValue().getId(),java.sql.Date.valueOf((investment_datepicker_name.getValue())));
        investmentService.saveInvestment(investment);
        show_all_investment();
    }

    public void delete_investment(ActionEvent e){
        Investment investment = investment_table.getSelectionModel().getSelectedItem();
        investmentService.deleteInvestment(investment);
        show_all_investment();
    }

    public void change_investment(ActionEvent e){
        Investment investment = investment_table.getSelectionModel().getSelectedItem();
        investment.setName(investment_textfield_name.getText());
        investment.setAmount(new BigDecimal(investment_textfield_amount.getText()));
        investment.setNote(investment_textfield_note.getText());
        investment.setPercent(Double.valueOf(investment_textfield_percent.getText()));
        investment.setData(java.sql.Date.valueOf(investment_datepicker_name.getValue()));
        investmentService.updateInvestment(investment);
        show_all_investment();
    }

    public void close_program(ActionEvent e){
        System.exit(0);
    }

    public void take_investment_from_table(MouseEvent event){
        Investment investment = investment_table.getSelectionModel().getSelectedItem();
        investment_textfield_name.setText(investment.getName());
        investment_textfield_note.setText(investment.getNote());
        investment_textfield_percent.setText(Double.toString(investment.getPercent()));
        investment_textfield_amount.setText(investment.getAmount().toString());
        investment_combobox_name.setValue(personService.findPersonForCombobox(investment.getPerson_id()));
        investment_datepicker_name.setValue(investment.getData().toLocalDate());
    }

    public void show_all_investment(){
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

    public void show_all_person(){
        try{
            list_for_person = FXCollections.observableArrayList();
            for(Person i:personService.findAllPersons()){
                i.setNote(null);
                i.setPhone_number(null);
                i.setInvestments(null);
                list_for_person.add(i);
            }
            investment_combobox_name.setItems(list_for_person);}
        catch (Exception e){
            System.out.println("Нет обьектов");
        }
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


