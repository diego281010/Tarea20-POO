package com.clinica.tarea20mysql.controller;


import com.clinica.tarea20mysql.dao.ParticipanteDAO;
import com.clinica.tarea20mysql.factory.ParticipanteFactory;
import com.clinica.tarea20mysql.modelo.Participante;
import com.clinica.tarea20mysql.strategy.ValidacionCedula;
import com.clinica.tarea20mysql.strategy.ValidacionCorreo;
import com.clinica.tarea20mysql.strategy.ValidacionEdad;
import com.clinica.tarea20mysql.strategy.ValidacionStrategy;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ParticipanteController implements Initializable {

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<String> cbEstadoCivil;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private RadioButton rbMatutina;

    @FXML
    private RadioButton rbVespertina;

    @FXML
    private RadioButton rbNocturna;

    @FXML
    private TextArea txtObservaciones;

    @FXML
    private TableView<Participante> tablaParticipantes;

    @FXML
    private TableColumn<Participante, Integer> colId;

    @FXML
    private TableColumn<Participante, String> colCedula;

    @FXML
    private TableColumn<Participante, String> colNombre;

    @FXML
    private TableColumn<Participante, String> colApellido;

    @FXML
    private TableColumn<Participante, Integer> colEdad;

    @FXML
    private TableColumn<Participante, String> colCorreo;

    @FXML
    private TableColumn<Participante, String> colEstadoCivil;

    @FXML
    private TableColumn<Participante, String> colJornada;

    @FXML
    private TableColumn<Participante, String> colCategoria;

    private final ParticipanteDAO dao =
            new ParticipanteDAO();

    private ToggleGroup grupoJornada;

    private Participante participanteSeleccionado;

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle) {

        grupoJornada = new ToggleGroup();

        rbMatutina.setToggleGroup(grupoJornada);
        rbVespertina.setToggleGroup(grupoJornada);
        rbNocturna.setToggleGroup(grupoJornada);

        cbEstadoCivil.getItems().addAll(
                "Soltero",
                "Casado",
                "Divorciado",
                "Viudo"
        );

        cbCategoria.getItems().addAll(
                "Infantil",
                "Juvenil",
                "Adulto",
                "Master"
        );

        configurarTabla();

        cargarParticipantes();

        tablaParticipantes.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {

                    if (newValue != null) {
                        cargarFormulario(newValue);
                    }

                });
    }

    private void configurarTabla() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colCedula.setCellValueFactory(
                new PropertyValueFactory<>("cedula"));

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        colApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellido"));

        colEdad.setCellValueFactory(
                new PropertyValueFactory<>("edad"));

        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo"));

        colEstadoCivil.setCellValueFactory(
                new PropertyValueFactory<>("estadoCivil"));

        colJornada.setCellValueFactory(
                new PropertyValueFactory<>("jornada"));

        colCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria"));
    }

    private void cargarParticipantes() {

        ObservableList<Participante> lista =
                dao.listarTodos();

        tablaParticipantes.setItems(lista);
    }

    @FXML
    private void guardar() {

        try {

            if (!validarFormulario()) {
                return;
            }

            if (dao.existeCorreo(
                    txtCorreo.getText().trim())) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "El correo ya existe."
                );

                return;
            }

            Participante participante =
                    ParticipanteFactory.crearParticipante(
                            txtCedula.getText().trim(),
                            txtNombre.getText().trim(),
                            txtApellido.getText().trim(),
                            Integer.parseInt(
                                    txtEdad.getText().trim()
                            ),
                            txtCorreo.getText().trim(),
                            cbEstadoCivil.getValue(),
                            obtenerJornada(),
                            cbCategoria.getValue(),
                            txtObservaciones.getText()
                    );

            if (dao.insertar(participante)) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Participante registrado."
                );

                cargarParticipantes();
                limpiar();
            }

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }

    @FXML
    private void actualizar() {

        try {

            if (participanteSeleccionado == null) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Seleccione un registro."
                );

                return;
            }

            if (!validarFormulario()) {
                return;
            }

            if (dao.existeCorreo(
                    txtCorreo.getText().trim(),
                    participanteSeleccionado.getId())) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "El correo ya existe."
                );

                return;
            }

            participanteSeleccionado.setCedula(
                    txtCedula.getText().trim());

            participanteSeleccionado.setNombre(
                    txtNombre.getText().trim());

            participanteSeleccionado.setApellido(
                    txtApellido.getText().trim());

            participanteSeleccionado.setEdad(
                    Integer.parseInt(
                            txtEdad.getText().trim()));

            participanteSeleccionado.setCorreo(
                    txtCorreo.getText().trim());

            participanteSeleccionado.setEstadoCivil(
                    cbEstadoCivil.getValue());

            participanteSeleccionado.setJornada(
                    obtenerJornada());

            participanteSeleccionado.setCategoria(
                    cbCategoria.getValue());

            participanteSeleccionado.setObservaciones(
                    txtObservaciones.getText());

            if (dao.actualizar(
                    participanteSeleccionado)) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Registro actualizado."
                );

                cargarParticipantes();
                limpiar();
            }

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }

    @FXML
    private void eliminar() {

        try {

            if (participanteSeleccionado == null) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Seleccione un registro."
                );

                return;
            }

            Alert alert =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            alert.setHeaderText(null);

            alert.setContentText(
                    "¿Eliminar participante?"
            );

            Optional<ButtonType> respuesta =
                    alert.showAndWait();

            if (respuesta.isPresent()
                    && respuesta.get()
                    == ButtonType.OK) {

                if (dao.eliminar(
                        participanteSeleccionado.getId())) {

                    mostrarAlerta(
                            Alert.AlertType.INFORMATION,
                            "Registro eliminado."
                    );

                    cargarParticipantes();
                    limpiar();
                }
            }

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }

    @FXML
    private void limpiar() {

        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtCorreo.clear();

        cbEstadoCivil.setValue(null);
        cbCategoria.setValue(null);

        grupoJornada.selectToggle(null);

        txtObservaciones.clear();

        participanteSeleccionado = null;

        tablaParticipantes.getSelectionModel()
                .clearSelection();
    }

    private void cargarFormulario(
            Participante participante) {

        participanteSeleccionado = participante;

        txtCedula.setText(
                participante.getCedula());

        txtNombre.setText(
                participante.getNombre());

        txtApellido.setText(
                participante.getApellido());

        txtEdad.setText(
                String.valueOf(
                        participante.getEdad()));

        txtCorreo.setText(
                participante.getCorreo());

        cbEstadoCivil.setValue(
                participante.getEstadoCivil());

        cbCategoria.setValue(
                participante.getCategoria());

        txtObservaciones.setText(
                participante.getObservaciones());

        switch (participante.getJornada()) {

            case "Matutina" ->
                    rbMatutina.setSelected(true);

            case "Vespertina" ->
                    rbVespertina.setSelected(true);

            case "Nocturna" ->
                    rbNocturna.setSelected(true);
        }
    }

    private boolean validarFormulario() {

        if (txtCedula.getText().isEmpty()
                || txtNombre.getText().isEmpty()
                || txtApellido.getText().isEmpty()
                || txtEdad.getText().isEmpty()
                || txtCorreo.getText().isEmpty()
                || cbEstadoCivil.getValue() == null
                || cbCategoria.getValue() == null
                || obtenerJornada() == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Complete todos los campos obligatorios."
            );

            return false;
        }

        ValidacionStrategy valCedula =
                new ValidacionCedula();

        if (!valCedula.validar(
                txtCedula.getText())) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    valCedula.getMensajeError()
            );

            return false;
        }

        ValidacionStrategy valEdad =
                new ValidacionEdad();

        if (!valEdad.validar(
                txtEdad.getText())) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    valEdad.getMensajeError()
            );

            return false;
        }

        ValidacionStrategy valCorreo =
                new ValidacionCorreo();

        if (!valCorreo.validar(
                txtCorreo.getText())) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    valCorreo.getMensajeError()
            );

            return false;
        }

        return true;
    }

    private String obtenerJornada() {

        if (rbMatutina.isSelected()) {
            return "Matutina";
        }

        if (rbVespertina.isSelected()) {
            return "Vespertina";
        }

        if (rbNocturna.isSelected()) {
            return "Nocturna";
        }

        return null;
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String mensaje) {

        Alert alert =
                new Alert(tipo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}