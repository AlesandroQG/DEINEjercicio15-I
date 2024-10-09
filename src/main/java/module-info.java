module com.alesandro.ejercicio15i {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;


    opens com.alesandro.ejercicio15i to javafx.fxml;
    exports com.alesandro.ejercicio15i;
    exports com.alesandro.ejercicio15i.model;
    exports com.alesandro.ejercicio15i.dao;
    exports com.alesandro.ejercicio15i.controller;
    opens com.alesandro.ejercicio15i.controller to javafx.fxml;
    exports com.alesandro.ejercicio15i.db;
    opens com.alesandro.ejercicio15i.db to javafx.fxml;
}