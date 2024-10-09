module com.alesandro.ejercicio15h {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;


    opens com.alesandro.ejercicio15h to javafx.fxml;
    exports com.alesandro.ejercicio15h;
    exports com.alesandro.ejercicio15h.model;
    exports com.alesandro.ejercicio15h.dao;
    exports com.alesandro.ejercicio15h.controller;
    opens com.alesandro.ejercicio15h.controller to javafx.fxml;
    exports com.alesandro.ejercicio15h.db;
    opens com.alesandro.ejercicio15h.db to javafx.fxml;
}