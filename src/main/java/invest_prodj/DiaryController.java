package invest_prodj;

import invest_prodj.model.Diary;
import invest_prodj.service.DiaryService;
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

public class DiaryController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public DiaryService diaryService;

    public ObservableList<Diary> list_for_diary;

    Dialog<ButtonType> dialog = new Dialog<>();
    //Колонки и таблицы
    @FXML
    public TableView<Diary> diary_table;

    @FXML
    public TableColumn<Diary,Integer>  diary_table_id;
    @FXML
    public TableColumn<Diary, String>  diary_table_date;
    @FXML
    public TableColumn<Diary,String>  diary_table_note;
    @FXML
    public TableColumn<Diary,String>  diary_table_learn_info;
    @FXML
    public TableColumn<Diary,String>  diary_table_sport_info;
    @FXML
    public TableColumn<Diary,Double>  diary_table_learn_time;
    @FXML
    public TableColumn<Diary,Double>  diary_table_sport_time;



    @FXML
    public Label time_label;
    @FXML
    public TextField diary_textField_note;
    @FXML
    public TextField diary_textField_learn;
    @FXML
    public TextField diary_textField_sport;
    @FXML
    public TextField diary_textField_learn_time;
    @FXML
    public TextField diary_textField_sport_time;
    @FXML
    public DatePicker diary_datepicker;

    @FXML
    public DialogPane day_dialog;
    @FXML
    public Label dialog_date_label;
    @FXML
    public Label dialog_sport_time_label;
    @FXML
    public Label dialog_learn_time_label;
    @FXML
    public TextArea dialog_note_textarea;
    @FXML
    public TextArea dialog_sport_info_textarea;
    @FXML
    public TextArea dialog_learn_info_textarea;
    @FXML
    public Button look_day_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        diaryService = new DiaryService();
        set_info_to_diary_table();
        show_all_day_from_diary();
        Timenow();

    }
    public void change_window_to_main(ActionEvent e) throws IOException {
        root = FXMLLoader.load(MainController.class.getResource("main.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.show();
    }

    public void close_program(ActionEvent e){
        System.exit(0);
    }

    public void create_diary(ActionEvent e) throws IOException {
        try {
            Diary diary = new Diary(java.sql.Date.valueOf(diary_datepicker.getValue()),diary_textField_note.getText(),
                    diary_textField_sport.getText(),diary_textField_learn.getText(),new Double(diary_textField_sport_time.getText()),
                    new Double(diary_textField_learn_time.getText()));
            diaryService.saveDiary(diary);
            show_all_day_from_diary();}
        catch (Exception exception){
            get_dialog_err();
        }
    }

    public void delete_diary(ActionEvent e) throws IOException {
        try {
            Diary diary = diary_table.getSelectionModel().getSelectedItem();
            diaryService.deleteDiary(diary);
            show_all_day_from_diary();}
            catch (Exception exception){
            get_dialog_err();
        }
    }

    public void update_diary(ActionEvent e) throws IOException {
        try {
            Diary diary = diary_table.getSelectionModel().getSelectedItem();
            diary.setDate_create(java.sql.Date.valueOf(diary_datepicker.getValue()));
            diary.setNote(diary_textField_note.getText());
            diary.setSport_info(diary_textField_sport.getText());
            diary.setLearn_info(diary_textField_learn.getText());
            diary.setSport_time(new Double(diary_textField_sport_time.getText()));
            diary.setLearn_time(new Double(diary_textField_learn_time.getText()));
            diaryService.updateDiary(diary);
            show_all_day_from_diary();}
        catch (Exception exception){
            get_dialog_err();
        }
    }

    public void take_day_from_diary_table(MouseEvent event){
        Diary diary = diary_table.getSelectionModel().getSelectedItem();
        diary_datepicker.setValue(diary.getDate_create().toLocalDate());
        diary_textField_note.setText(diary.getNote());
        diary_textField_learn.setText(diary.getLearn_info());
        diary_textField_learn_time.setText(String.valueOf(diary.getLearn_time()));
        diary_textField_sport.setText(diary.getSport_info());
        diary_textField_sport_time.setText(String.valueOf(diary.getSport_time()));

    }

    public void show_day(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("day.fxml"));
            fxmlLoader.setController(this);
            DialogPane day_dialog = fxmlLoader.load();
            dialog.setDialogPane(day_dialog);
            Diary diary = diary_table.getSelectionModel().getSelectedItem();
            dialog_date_label.setText(" Дата: " + diary.getDate_create().toLocalDate().toString());
            dialog_note_textarea.setText(diary.getNote());
            dialog_learn_info_textarea.setText(diary.getLearn_info());
            dialog_learn_time_label.setText(" Часы учебы: " +String.valueOf(diary.getLearn_time()));
            dialog_sport_info_textarea.setText(diary.getSport_info());
            dialog_sport_time_label.setText(" Часы спорта: " +String.valueOf(diary.getSport_time()));
            dialog.show();}
        catch (Exception exception){
            get_dialog_err();
        }
        
    }
    public void show_all_day_from_diary(){
        try{
            list_for_diary = FXCollections.observableArrayList();
            for(Diary i:diaryService.findAllDiary()){
                list_for_diary.add(i);
            }
            diary_table.setItems(list_for_diary);}
        catch (Exception exception){
            System.out.println("Нет вложений");
        }
    }
    public void set_info_to_diary_table(){
        diary_table_id.setCellValueFactory(new PropertyValueFactory<Diary,Integer>("id"));
        diary_table_date.setCellValueFactory(new PropertyValueFactory<Diary,String>("date_create"));
        diary_table_note.setCellValueFactory(new PropertyValueFactory<Diary,String>("note"));
        diary_table_learn_info.setCellValueFactory(new PropertyValueFactory<Diary,String>("learn_info"));
        diary_table_sport_info.setCellValueFactory(new PropertyValueFactory<Diary,String>("sport_info"));
        diary_table_learn_time.setCellValueFactory(new PropertyValueFactory<Diary,Double>("learn_time"));
        diary_table_sport_time.setCellValueFactory(new PropertyValueFactory<Diary,Double>("sport_time"));
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

    public void get_dialog_err() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialog_err.fxml"));
        fxmlLoader.setController(this);
        DialogPane day_dialog = fxmlLoader.load();
        dialog.setDialogPane(day_dialog);
        dialog.show();
    }
}
