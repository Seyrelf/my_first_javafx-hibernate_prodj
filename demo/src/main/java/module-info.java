module invest_prodj.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens invest_prodj.demo to javafx.fxml;
    exports invest_prodj.demo;
}