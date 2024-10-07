/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import aplicacio.model.Referencia;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.CanviPantalla;
import logica.ReferenciaLogica;
/**
 * FXML Controller class
 *
 * @author mario
 */
public class ConsultaReferenciaController {
  private ReferenciaLogica referenciaLogica = new ReferenciaLogica();
    int idFamilia = 0;
    @FXML
    private Button btnNova, btnEliminar, btnModificar, btnFamilia, btnSortir;

    @FXML
    private TextField tfVegadesAlarma, tfNom, tfIdFamilia, tfPreu, tfDataAlarma, tfUom, tfProveidor, tfDataAlta, tfQuantitat, tfId;

    @FXML
    private TableColumn<?, ?> colProveidor, colQuantitat, colUom, colNom, colPreu, colId, colDataAlta;

    @FXML
    private TextArea taObservacions;

    @FXML
    private TableView<Referencia> tbReferencia;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Referencia> referenciasObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Asociar las columnas de la tabla con los atributos de los items usando métodos tradicionales
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPreu.setCellValueFactory(new PropertyValueFactory<>("preu"));
        colQuantitat.setCellValueFactory(new PropertyValueFactory<>("quantitat"));
        colUom.setCellValueFactory(new PropertyValueFactory<>("uom"));
        colProveidor.setCellValueFactory(new PropertyValueFactory<>("proveidor"));
        colDataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));

        // Asignar la lista observable a la tabla
        tbReferencia.setItems(referenciasObservableList);
    }

    @FXML
    private void onbtnFamilia_Action(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnFamilia.getScene(), "/cat/copernic/projecte_grup4/ConsultaFamilia.fxml");
    }

    @FXML
    private void onbtnSortir_Action(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnSortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
    }

    private void listarFamilias() {
        // Limpiamos la lista observable antes de añadir los datos actualizados
        referenciasObservableList.clear();
        referenciasObservableList.addAll(referenciaLogica.llistarReferencias(idFamilia));
    }
}
