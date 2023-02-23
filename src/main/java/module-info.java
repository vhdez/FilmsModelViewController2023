module com.example.filmsmvc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.filmsmvc to javafx.fxml;
    exports com.example.filmsmvc;
}