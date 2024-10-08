/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import aplicacio.model.Referencia;
import aplicacio.model.UOM;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.input.MouseEvent;
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

    /*private int idFamilia;

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
        tfIdFamilia.setText(String.valueOf(idFamilia));
    }

    public int getIdFamilia() {
        return idFamilia;
    }*/
    ConsultaFamiliaController controladorFamilia;

    public ConsultaFamiliaController getControladorFamilia() {
        return controladorFamilia;
    }

    public void setControladorFamilia(ConsultaFamiliaController controladorFamilia) {
        this.controladorFamilia = controladorFamilia;
    }
    
    int idFamilia = controladorFamilia.getId();
    
    
    private ObservableList<Referencia> referenciasObservableList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
       /* taObservacions.getScene().getWindow().setOnShown(event -> {
    System.out.println(controladorFamilia.getId());
    llistarReferencias(idFamilia);
        });*/
                
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

    @FXML
    private void onbtnEliminar_Clicked() {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            referenciaLogica.eliminarReferencia(referenciaSeleccionada.getId());
            referenciasObservableList.remove(referenciaSeleccionada);
            limpiarCampos();
        } else {
            System.out.println("Por favor, selecciona una referencia para eliminar.");
        }
    }

    @FXML
    private void onbtnModificar_Clicked() {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            referenciaSeleccionada.setNom(tfNom.getText());
            referenciaSeleccionada.setObservacions(taObservacions.getText());

            // Validar y convertir la fecha
            if (!tfDataAlta.getText().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    referenciaSeleccionada.setDataAlta(LocalDate.parse(tfDataAlta.getText(), formatter));
                } catch (Exception e) {
                    System.out.println("Formato de fecha incorrecto: " + e.getMessage());
                    return; // No continuar si la fecha es inválida
                }
            }

            // Convertir el campo tf_Proveidor a int antes de asignar
            try {
                int idProveidor = Integer.parseInt(tfProveidor.getText());
                referenciaSeleccionada.setProveidor(idProveidor);
            } catch (NumberFormatException e) {
                System.out.println("ID del proveedor no válido. Asegúrate de que sea un número.");
                return; // Salir si el ID del proveedor es inválido
            }

            try {
                referenciaLogica.modificarReferencia(referenciaSeleccionada);
                llistarReferencias(idFamilia); // Actualiza la lista después de modificar
                tbReferencia.refresh(); // Refresca la tabla
                limpiarCampos(); // Limpia los campos
            } catch (Exception e) {
                System.out.println("Error al modificar la referencia: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, selecciona una referencia para modificar.");
        }
    }

    @FXML
    private void onbtnNova_Clicked() {
        Referencia nuevaReferencia = new Referencia();
        referenciaLogica.afegirReferencia(nuevaReferencia);
        referenciasObservableList.add(nuevaReferencia);
        limpiarCampos();
    }

    @FXML
    private void ontbReferenciaMouseClicked(MouseEvent event) {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            // Setear los valores en los campos de texto
            tfId.setText(String.valueOf(referenciaSeleccionada.getId()));
            tfNom.setText(referenciaSeleccionada.getNom());

            // Formatear la fecha de alta
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            tfDataAlta.setText(referenciaSeleccionada.getDataAlta().format(format));

            // Setear el resto de valores
            tfProveidor.setText(String.valueOf(referenciaSeleccionada.getProveidor()));
            tfQuantitat.setText(String.valueOf(referenciaSeleccionada.getQuantitat()));

            // Verificar si la fecha de alarma es nula antes de formatearla
            if (referenciaSeleccionada.getUltimaDataAlarma() != null) {
                tfDataAlarma.setText(referenciaSeleccionada.getUltimaDataAlarma().format(format));
            } else {
                tfDataAlarma.setText(""); // Dejar el campo vacío si es nulo
            }

            // Setear el resto de valores en los campos correspondientes
            tfIdFamilia.setText(String.valueOf(referenciaSeleccionada.getFamiliaID()));
            tfVegadesAlarma.setText(String.valueOf(referenciaSeleccionada.getVegadesAlarma()));
            tfPreu.setText(String.valueOf(referenciaSeleccionada.getPreuCompra()));
            tfUom.setText(String.valueOf(referenciaSeleccionada.getUom()));
            taObservacions.setText(referenciaSeleccionada.getObservacions());
        }
    }

    private void limpiarCampos() {
        taObservacions.clear();
        tfVegadesAlarma.clear();
        tfIdFamilia.clear();
        tfDataAlarma.clear();
        tfNom.clear();
        tfPreu.clear();
        tfUom.clear();
        tfProveidor.clear();
        tfDataAlta.clear();
        tfQuantitat.clear();
        tfId.clear();
    }
}
