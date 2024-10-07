module com.alesandro.ejercicio15c {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.alesandro.ejercicio15c to javafx.fxml;
    exports com.alesandro.ejercicio15c;
    exports com.alesandro.model;
}