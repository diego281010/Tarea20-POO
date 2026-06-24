module com.clinica.tarea20mysql {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens com.clinica.tarea20mysql.controller to javafx.fxml;
    opens com.clinica.tarea20mysql.modelo to javafx.base;

    exports com.clinica.tarea20mysql;
}