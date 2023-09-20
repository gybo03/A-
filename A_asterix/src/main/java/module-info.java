module com.example.a_asterix {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.a_asterix to javafx.fxml;
    exports com.example.a_asterix;
}