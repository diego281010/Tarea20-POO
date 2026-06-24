package com.clinica.tarea20mysql.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void ingresar(ActionEvent event) {

        try {

            String usuario =
                    txtUsuario.getText().trim();

            String password =
                    txtPassword.getText().trim();

            if (usuario.isEmpty()
                    || password.isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Todos los campos son obligatorios."
                );

                return;
            }

            if (usuario.equals("admin") && password.equals("1234")) {
                FXMLLoader loader;

                loader = new FXMLLoader(getClass().getResource("/com/clinica/tarea20mysql/participantes.fxml"));


                Stage stage =
                        new Stage();

                stage.setTitle(
                        "CRUD Participantes"
                );

                stage.setScene(new Scene(loader.load()));

                stage.show();

                Stage loginStage =
                        (Stage) txtUsuario
                                .getScene()
                                .getWindow();

                loginStage.close();

            } else {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Usuario o contraseña incorrectos."
                );
            }

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error 1: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void salir(ActionEvent event) {

        System.exit(0);
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String mensaje
    ) {

        Alert alert =
                new Alert(tipo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}