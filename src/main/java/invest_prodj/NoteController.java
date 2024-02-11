package invest_prodj;

import invest_prodj.model.Diary;
import invest_prodj.model.Note;
import invest_prodj.service.InvestmentService;
import invest_prodj.service.NoteService;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class NoteController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Dialog<ButtonType> dialog = new Dialog<>();

    private NoteService noteService;

    public ObservableList<Note> list_for_note;

    @FXML
    public Label time_label;

    @FXML
    public TableView<Note> table_note;

    @FXML
    public TableColumn<Note,String> note_table_note;
    @FXML
    public TableColumn<Note,String> note_table_link;
    @FXML
    public TableColumn<Note,Integer> note_table_id;

    @FXML
    public TextField link_field;
    @FXML
    public TextArea note_area;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        noteService = new NoteService();
        show_all_note();
        Timenow();
    }
    public void switch_window_to_main(ActionEvent e) throws IOException {
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

    public void create_note(ActionEvent event){
        Note note = new Note(note_area.getText(),link_field.getText());
        noteService.saveNote(note);
        show_all_note();
    }

    public void change_note(ActionEvent event) throws IOException {
        try {
            Note note = table_note.getSelectionModel().getSelectedItem();
            note.setNote(note_area.getText());
            note.setLink(link_field.getText());
            noteService.updateNote(note);
            show_all_note();}
        catch (Exception exception){
            get_dialog_err();}
    }

    public void delete_note(ActionEvent event) throws IOException {
        try {
            Note note = table_note.getSelectionModel().getSelectedItem();
            noteService.deleteNote(note);
            show_all_note();}
        catch (Exception exception){
            get_dialog_err();
        }
    }

    public void take_info_from_table(MouseEvent event){
        Note note = table_note.getSelectionModel().getSelectedItem();
        link_field.setText(note.getLink());
        note_area.setText(note.getNote());
    }

    public void show_all_note() {
        set_info_to_note_table();
        try {
            list_for_note = FXCollections.observableArrayList();
            for (Note i : noteService.findAllNote()) {
                list_for_note.add(i);
            }
            table_note.setItems(list_for_note);
        } catch (Exception exception) {
            System.out.println("Нет вложений");
        }
    }
    public void set_info_to_note_table(){
       note_table_id.setCellValueFactory(new PropertyValueFactory<Note,Integer>("id"));
       note_table_note.setCellValueFactory(new PropertyValueFactory<Note,String>("note"));
       note_table_link.setCellValueFactory(new PropertyValueFactory<Note,String>("link"));
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
