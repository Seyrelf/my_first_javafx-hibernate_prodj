package invest_prodj;

import invest_prodj.model.Investment;
import invest_prodj.model.Note;
import invest_prodj.service.DiaryService;
import invest_prodj.service.InvestmentService;
import invest_prodj.service.NoteService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Portfolio;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public InvestmentService investmentService;
    public NoteService noteService;

    public DiaryService diaryService;
    public WebEngine engine;
    public double webZoom;
    public WebHistory history;
    public String tinkoff_token;

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
    public Button find_web_btn;
    @FXML
    public Button refresh_web_btn;
    @FXML
    public Button back_web_btn;
    @FXML
    public Button zooomIn_web_btn;
    @FXML
    public Button zooomOut_web_btn;
    @FXML
    public WebView browser_webview;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        engine = browser_webview.getEngine();
        engine.load("http://google.com");
        webZoom = 1;
        investmentService = new InvestmentService();
        noteService = new NoteService();
        diaryService = new DiaryService();
        set_info_to_label();
        Timenow();
    }

    public String get_amount_from_tinkoff(){
        tinkoff_token = Key.getTinkoff_token();
        InvestApi api = InvestApi.create(tinkoff_token);
        List<ru.tinkoff.piapi.contract.v1.Account> accounts = api.getUserService().getAccountsSync();
        String ac_one = accounts.get(0).getId();
        String ac_two = accounts.get(1).getId();
        Portfolio portfolio1 = api.getOperationsService().getPortfolioSync(ac_one);
        Portfolio portfolio2 = api.getOperationsService().getPortfolioSync(ac_two);
        BigDecimal inv_schet_amount = portfolio1.getTotalAmountPortfolio().getValue();
        BigDecimal ind_inv_schet_amount = portfolio2.getTotalAmountPortfolio().getValue();
        String amount_invest = String.format("%.2f",ind_inv_schet_amount.add(inv_schet_amount));
        Investment tinkof_invest = investmentService.findInvestment(23);
        tinkof_invest.setAmount(inv_schet_amount);
        Investment tinkof_ind_invest = investmentService.findInvestment(32);
        tinkof_ind_invest.setAmount(ind_inv_schet_amount);
        investmentService.updateInvestment(tinkof_invest);
        investmentService.updateInvestment(tinkof_ind_invest);
        return amount_invest;
    }

    public void set_info_to_label(){
        sport_week_label.setText("  Спорт за неделю: "+diaryService.sport_for_week_sum().toString());
        study_week_label.setText("  Учеба за неделю: "+diaryService.learn_for_week_sum().toString());
        Note note = noteService.findNote(8);
        purpose_label.setText(" " + note.getLink() + " " + note.getNote());

        tinkoff_capital_label.setText("  Капитал Тинькофф: " + get_amount_from_tinkoff());
        try {
            my_capital_label.setText("  Полная сумма: "+investmentService.amount_sum());
        }
        catch (Exception e) {
            my_capital_label.setText("  Полная сумма: 0");
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

    public void loadPage(){
        engine.load("http://" + browser_find_textfield.getText()+".com");
    }

    public void switch_window_to_investment(ActionEvent event) throws IOException {
        root = FXMLLoader.load(MainController.class.getResource("investment_main_window.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Меню управления капиталом");
        stage.setScene(scene);
        stage.show();
    }

    public void switch_window_to_note(ActionEvent event) throws IOException {
        root = FXMLLoader.load(MainController.class.getResource("note.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Заметки");
        stage.setScene(scene);
        stage.show();
    }

    public void switch_window_to_diary(ActionEvent event) throws IOException {
        root = FXMLLoader.load(MainController.class.getResource("diary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Дневник");
        stage.setScene(scene);
        stage.show();
    }
    public void load_btn(ActionEvent event) throws  IOException{
        if(browser_find_textfield.getText().isEmpty()){
            System.out.println("Пустрая строка поиска");
        }
        else  {
            engine.load("http://" + browser_find_textfield.getText()+".com");
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
