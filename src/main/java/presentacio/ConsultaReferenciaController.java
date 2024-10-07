/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import aplicacio.model.Familia;
import aplicacio.model.Referencia;
import aplicacio.model.UOM;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

    @FXML
    private Button btnNova, btnEliminar, btnModificar, btnFamilia, btnSortir;

    @FXML
    private TextField tfVegadesAlarma, tfNom, tfIdFamilia, tfPreu, tfDataAlarma, tfUom, tfProveidor, tfDataAlta, tfQuantitat, tfId;

    @FXML
    private TableColumn<Referencia, Integer> colQuantitat, colId;

    @FXML
    private TableColumn<Referencia, Float> colPreu;
    
    @FXML
    private TableColumn<Referencia, String> colNom, colProveidor;
    
    @FXML
    private TableColumn<Referencia, LocalDate> colDataAlta;
    
    @FXML
    private TableColumn<Referencia, UOM> colUom;

    @FXML
    private TextArea taObservacions;

    @FXML
    private TableView<Referencia> tbReferencia;

    private ReferenciaLogica referenciaLogica = new ReferenciaLogica();
    int idFamilia = 1;
    private ObservableList<Referencia> referenciasObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        System.out.println("inicializa");
        // Asociar las columnas de la tabla con los atributos de los items usando métodos tradicionales
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPreu.setCellValueFactory(new PropertyValueFactory<>("preuCompra"));
        colQuantitat.setCellValueFactory(new PropertyValueFactory<>("quantitat"));
        colUom.setCellValueFactory(new PropertyValueFactory<>("uom"));
        colProveidor.setCellValueFactory(new PropertyValueFactory<>("proveidor"));
        colDataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));

        // Asignar la lista observable a la tabla
        tbReferencia.setItems(referenciasObservableList);
        llistarReferencias(idFamilia);
    }

    @FXML
    private void onbtnFamilia_Clicked(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnFamilia.getScene(), "/cat/copernic/projecte_grup4/ConsultaFamilia.fxml");
    }

    @FXML
    private void onbtnSortir_Clicked(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnSortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
    }

    private void llistarReferencias(int idFamilia) {
        // Limpiamos la lista observable antes de añadir los datos actualizados
        referenciasObservableList.clear();
        referenciasObservableList.addAll(referenciaLogica.llistarReferencias(idFamilia));
    }
}
