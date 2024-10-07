/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import aplicacio.model.Proveidor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.ProveidorLogica;
/**
 * FXML Controller class
 *
 * @author david
 */
public class ConsultaProveidorController implements Initializable {

    private ProveidorLogica provLogic;
    
    @FXML
    private Button btnNova;

    @FXML
    private TableColumn<Proveidor, String> colEspecialitat;

    @FXML
    private TableColumn<Proveidor, Boolean> colActiu;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfValoracio;

    @FXML
    private TableColumn<Proveidor, Float> colValoracio;

    @FXML
    private TableColumn<Proveidor, Integer> colMinimUnitats;

    @FXML
    private TableView<Proveidor> tbView;

    @FXML
    private TableColumn<Proveidor, String> colMotiuInactivitat;

    @FXML
    private TextField tfMinimUnitats;

    @FXML
    private TableColumn<Proveidor, Integer> colId;

    @FXML
    private TableColumn<Proveidor, String> colNom;

    @FXML
    private Button btnEliminar;

    @FXML
    private CheckBox cbActiu;

    @FXML
    private TextField tfId;

    @FXML
    private DatePicker dpDataAlta;

    @FXML
    private TextField tfEspecialitat;

    @FXML
    private TextField tfCif;

    @FXML
    private TextArea tfMotiuInactivitat;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<Proveidor, String> colCif;

    @FXML
    private TableColumn<Proveidor, LocalDate> colDataAlta;

    @FXML
    private Button btnSortir;
    private ObservableList<Proveidor> Llistaproveidors;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCif.setCellValueFactory(new PropertyValueFactory<>("CIF"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        colActiu.setCellValueFactory(new PropertyValueFactory<>("actiu"));
        colMotiuInactivitat.setCellValueFactory(new PropertyValueFactory<>("motiuInactivitat"));
        colValoracio.setCellValueFactory(new PropertyValueFactory<>("valoracio"));
        colEspecialitat.setCellValueFactory(new PropertyValueFactory<>("especialitatt"));
        colMinimUnitats.setCellValueFactory(new PropertyValueFactory<>("minimUnitats"));
        
        
        Llistaproveidors = FXCollections.observableArrayList(provLogic.llistarProveidors());
        
        tbView.setItems(Llistaproveidors);
    }
       @FXML
    private void onBtnSortir_action(ActionEvent event){
        
    }

    @FXML
    private void onBtnNova_action(ActionEvent event) {
        String CIF = tfCif.getText();
        String Nom = tfNom.getText();
        LocalDate DataAlta = dpDataAlta.getValue();
        boolean Actiu = cbActiu.isSelected();
        String MotiuIncativitat = tfMotiuInactivitat.getText();
        float Valoracio = Float.parseFloat(tfValoracio.getText());
        String Especialitat = tfEspecialitat.getText();
        int MinimUnitats = Integer.parseInt(tfMinimUnitats.getText());
        
        Proveidor p = new Proveidor();
        p.setCIF(CIF);
        p.setNom(Nom);
        p.setDataAlta(DataAlta);
        p.setActiu(Actiu);
        p.setMotiuInactivitat(MotiuIncativitat);
        p.setValoracio(Valoracio);
        p.setEspecialitat(Especialitat);
        p.setMinimUnitats(MinimUnitats);
        
        
        provLogic.afegirProveidor(p);
    }

    @FXML
    private void onBtnEliminar_action(ActionEvent event) {
        
    }

    @FXML
    private void onBtnModificar_action(ActionEvent event) {
        
    }

    
}
