package com.alesandro.ejercicio15h.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import static javafx.scene.control.TableView.TableViewSelectionModel;

import javafx.scene.control.cell.PropertyValueFactory;
import com.alesandro.ejercicio15h.model.Persona;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Clase que controla los eventos de la ventana
 */
public class PersonasController {
    private TextField txtNombre;

    private TextField txtApellidos;

    private TextField txtEdad;

    private Button btnGuardar;

    private Button btnCancelar;

    @FXML // fx:id="tabla"
    private TableView<Persona> tabla; // Value injected by FXMLLoader

    @FXML // fx:id="colNombre"
    private TableColumn<Persona, String> colNombre; // Value injected by FXMLLoader

    @FXML // fx:id="colApellidos"
    private TableColumn<Persona, String> colApellidos; // Value injected by FXMLLoader

    @FXML // fx:id="colEdad"
    private TableColumn<Persona, Integer> colEdad; // Value injected by FXMLLoader

    @FXML // fx:id="filtroNombre"
    private TextField filtroNombre; // Value injected by FXMLLoader

    private Stage modal;

    private ObservableList<Persona> masterData = FXCollections.observableArrayList();
    private ObservableList<Persona> filteredData = FXCollections.observableArrayList();

    /**
     * Función que se ejecuta cuando se inicia la ventana
     */
    public void initialize() {
        // Modificar las celdas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        tabla.getColumns().setAll(colNombre, colApellidos, colEdad);
        // Filtrar
        filtroNombre.setOnKeyTyped(keyEvent -> filtrar());
    }

    /**
     * Válida los datos del formulario
     *
     * @return si los datos son validos o no
     */
    private boolean validarDatos() {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error += "El campo nombre es obligatorio";
        }
        if (txtApellidos.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "El campo apellidos es obligatorio";
        }
        if (txtEdad.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "El campo edad es obligatorio";
        } else {
            try {
                int edad = Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException e) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "El campo edad tiene que ser numérico";
            }
        }
        if (!error.isEmpty()) {
            alerta(error);
            return false;
        }
        return true;
    }

    /**
     * Función que crea y muestra la modal
     *
     * @param title título de la ventana modal
     */
    public void mostrarModal(String title) {
        modal = new Stage();
        modal.initOwner(tabla.getScene().getWindow());
        modal.initModality(Modality.WINDOW_MODAL);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(75), new ColumnConstraints(200));
        Label lblNombre = new Label("Nombre");
        txtNombre = new TextField();
        gridPane.add(lblNombre, 0, 0, 1, 1);
        gridPane.add(txtNombre, 1, 0, 1, 1);
        Label lblApellidos = new Label("Apellidos");
        txtApellidos = new TextField();
        gridPane.add(lblApellidos, 0, 1, 1, 1);
        gridPane.add(txtApellidos, 1, 1, 1, 1);
        Label lblEdad = new Label("Edad");
        txtEdad = new TextField();
        txtEdad.setMaxWidth(75);
        gridPane.add(lblEdad, 0, 2, 1, 1);
        gridPane.add(txtEdad, 1, 2, 1, 1);
        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(event -> cancelar());
        FlowPane flowPane = new FlowPane(btnGuardar, btnCancelar);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(20);
        gridPane.add(flowPane, 0, 3, 2, 1);
        Scene scene = new Scene(gridPane, 300, 150);
        modal.setScene(scene);
        modal.setResizable(false);
        modal.setTitle(title);
        modal.show();
    }

    /**
     * Función que procesa los datos cuándo se pulsa el botón "Agregar Persona"
     *
     * @param event
     */
    @FXML
    void agregarPersona(ActionEvent event) {
        mostrarModal("Nueva Persona");
        btnGuardar.setOnAction(actionEvent -> agregar());
    }

    /**
     * Función que agrega una persona a la lista
     */
    public void agregar() {
        boolean resultado = validarDatos();
        if (resultado) {
            Persona p = new Persona(txtNombre.getText(), txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));
            if (masterData.contains(p)) {
                alerta("Esa persona ya existe");
            } else {
                tabla.getItems().add(p);
                masterData.add(p);
                confirmacion("Persona añadida correctamente");
                modal.close();
            }
        }
    }

    /**
     * Función que procesa los datos cuándo se pulsa el botón "Modificar Persona"
     *
     * @param event
     */
    @FXML
    void modificarPersona(ActionEvent event) {
        TableViewSelectionModel<Persona> tsm = tabla.getSelectionModel();
        if (tsm.isEmpty()) {
            alerta("Tienes que seleccionar una fila");
        } else {
            Persona p = tsm.getSelectedItem();
            mostrarModal("Modificar Persona");
            btnGuardar.setOnAction(actionEvent -> modificar(p));
            btnCancelar.setOnAction(actionEvent -> cancelar());
            txtNombre.setText(p.getNombre());
            txtApellidos.setText(p.getApellidos());
            txtEdad.setText(p.getEdad() + "");
        }
    }

    /**
     * Función que modifica una persona de la lista
     */
    void modificar(Persona p) {
        boolean resultado = validarDatos();
        if (resultado) {
            Persona p2 = new Persona(txtNombre.getText(), txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));
            if (masterData.contains(p2)) {
                alerta("Esa persona ya existe");
            } else {
                p.setNombre(txtNombre.getText());
                p.setApellidos(txtApellidos.getText());
                p.setEdad(Integer.parseInt(txtEdad.getText()));
                tabla.refresh();
                confirmacion("Actualizada persona correctamente");
                modal.close();
            }
        }
    }

    /**
     * Función que procesa los datos cuándo se pulsa el botón "Eliminar Persona"
     *
     * @param event
     */
    @FXML
    void eliminarPersona(ActionEvent event) {
        TableViewSelectionModel<Persona> tsm = tabla.getSelectionModel();
        if (tsm.isEmpty()) {
            alerta("Tienes que seleccionar una fila");
        } else {
            Persona p = tsm.getSelectedItem();
            ObservableList<Integer> lst = tsm.getSelectedIndices();
            Integer[] indices = new Integer[lst.size()];
            indices = lst.toArray(indices);
            Arrays.sort(indices);
            for (int i = indices.length - 1; i >= 0; i--) {
                tsm.clearSelection(indices[i].intValue());
                tabla.getItems().remove(indices[i].intValue());
            }
            masterData.remove(p);
            confirmacion("Personas eliminadas correctamente");
        }
    }

    /**
     * Función que cierra la ventana modal
     */
    public void cancelar() {
        modal.close();
    }

    /**
     * Filtra la tabla
     */
    public void filtrar() {
        String value = filtroNombre.getText();
        value = value.toLowerCase();
        if (value.isEmpty()) {
            tabla.setItems(masterData);
        } else {
            filteredData.clear();
            for (Persona p : masterData) {
                String nombre = p.getNombre();
                nombre = nombre.toLowerCase();
                if (nombre.startsWith(value)) {
                    filteredData.add(p);
                }
            }
            tabla.setItems(filteredData);
        }
    }

    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Función que muestra un mensaje de confirmación al usuario
     *
     * @param texto contenido del mensaje
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

}
