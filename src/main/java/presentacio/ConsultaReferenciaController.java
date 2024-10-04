/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ConsultaReferenciaController implements Initializable {

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
