module com.example.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens com.example.calculator to javafx.fxml;
    exports com.example.calculator;
}