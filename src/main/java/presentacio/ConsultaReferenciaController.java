/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.CanviPantalla;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ConsultaReferenciaController{

    @FXML
    private Button btnNova;

    @FXML
    private TextField tfVegadesAlarma;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfIdFamilia;

    @FXML
    private TableColumn<?, ?> colProveidor;

    @FXML
    private TableColumn<?, ?> colUom;

    @FXML
    private TableColumn<?, ?> colQuantitat;

    @FXML
    private TextField tfPreu;

    @FXML
    private TextField tfDataAlarma;

    @FXML
    private TableColumn<?, ?> colNom;

    @FXML
    private TextField tfUom;

    @FXML
    private TextArea taObservacions;

    @FXML
    private TextField tfProveidor;

    @FXML
    private TextField tfDataAlta;

    @FXML
    private TextField tfQuantitat;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<?, ?> colPreu;

    @FXML
    private TextField tfId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnFamilia;

    @FXML
    private TableColumn<?, ?> colDataAlta;

    @FXML
    private Button btnSortir;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public void initialize() {
        // Asociar las columnas de la tabla con los atributos de los items usando métodos tradicionales
        colId.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Item, String>("nom"));
        colPreu.setCellValueFactory(new PropertyValueFactory<Item, String>("preu"));
        colQuantitat.setCellValueFactory(new PropertyValueFactory<Item, String>("quantitat"));
        colUom.setCellValueFactory(new PropertyValueFactory<Item, String>("uom"));
        colProveidor.setCellValueFactory(new PropertyValueFactory<Item, String>("proveidor"));
        colDataAlta.setCellValueFactory(new PropertyValueFactory<Item, String>("dataAlta"));

        // Asignar la lista observable a la tabla
        tableView.setItems(itemList);

        // Agregar algunos elementos de ejemplo
        itemList.add(new Item("1", "Producte 1", "10.0", "5", "uom1", "CIF123", "2023-10-01"));
    }

    @FXML
    private void onbtnFamilia_Action(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnFamilia.getScene(), "/cat/copernic/projecte_grup4/ConsultaFamilia.fxml");
    }

    @FXML
    private void onbtnSortir_Action(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnSortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
    }
}
