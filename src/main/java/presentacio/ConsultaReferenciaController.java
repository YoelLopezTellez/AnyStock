/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import aplicacio.model.Familia;
import aplicacio.model.Referencia;
import aplicacio.model.TIPUSROL;
import aplicacio.model.UOM;
import aplicacio.model.Usuari;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logica.ReferenciaLogica;
import logica.Sessio;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ConsultaReferenciaController {

    @FXML
    private Button btnNova, btnEliminar, btnModificar, btnFamilia, btnSortir, btnFiltro;

    @FXML
    private TextField tfVegadesAlarma, tfNom, tfIdFamilia, tfPreu, tfDataAlarma, tfUom, tfProveidor, tfDataAlta, tfQuantitat, tfId, tfFiltro;

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

    private int idFamilia;

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    private ObservableList<Referencia> referenciasObservableList = FXCollections.observableArrayList();

    private Usuari usuari;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        usuari = Sessio.getInstancia().getUsuari();

        if (usuari.getTipusRol() == TIPUSROL.VENDEDOR) {
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            btnNova.setVisible(false);
        }
        // Asociar las columnas de la tabla con los atributos de los items usando métodos tradicionales
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPreu.setCellValueFactory(new PropertyValueFactory<>("preuCompra"));
        colQuantitat.setCellValueFactory(new PropertyValueFactory<>("quantitat"));
        colUom.setCellValueFactory(new PropertyValueFactory<>("uom"));
        colProveidor.setCellValueFactory(new PropertyValueFactory<>("proveidor"));
        colDataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        setIdFamilia(0);
        // Asignar la lista observable a la tabla
        llistarReferencias(idFamilia);
        tbReferencia.setItems(referenciasObservableList);
    }

    @FXML
    private void onbtnFiltro_Clicked() {
        setIdFamilia(Integer.parseInt(tfFiltro.getText()));
        limpiarCampos();
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

    @FXML
    private void onbtnEliminar_Clicked() {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            referenciaLogica.eliminarReferencia(referenciaSeleccionada.getId());
            referenciasObservableList.remove(referenciaSeleccionada);
            limpiarCampos();
        } else {
            Error.mostrarError("Referencia no seleccionada", "Referencia no seleccionada. Selecciona una referencia para poder eliminarla.");
            System.out.println("Por favor, selecciona una referencia para eliminar.");
        }
    }

    @FXML
    private void onbtnModificar_Clicked() {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {

            try {
                referenciaSeleccionada.setNom(tfNom.getText());
                referenciaSeleccionada.setObservacions(taObservacions.getText());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                referenciaSeleccionada.setDataAlta(LocalDate.parse(tfDataAlta.getText(), formatter));
                int idProveidor = Integer.parseInt(tfProveidor.getText());
                referenciaSeleccionada.setProveidor(idProveidor);
                int quantitat = Integer.parseInt(tfQuantitat.getText());
                referenciaSeleccionada.setQuantitat(quantitat);

                float preu = Float.parseFloat(tfPreu.getText());
                referenciaSeleccionada.setPreuCompra(preu);
                UOM uom = UOM.valueOf(tfUom.getText().toUpperCase());
                referenciaSeleccionada.setUom(uom);
                int familia = Integer.parseInt(tfIdFamilia.getText());
                referenciaSeleccionada.setFamiliaID(familia);
                referenciaLogica.modificarReferencia(referenciaSeleccionada);
                llistarReferencias(idFamilia); // Actualiza la lista después de modificar
                tbReferencia.refresh(); // Refresca la tabla
                limpiarCampos(); // Limpia los campos
            } catch (NumberFormatException e) {
                Error.mostrarError("Error en el formato de número", "Por favor, ingresa un número válido en los campos de Proveedor, Cantidad, Precio o Familia.");
            } catch (DateTimeParseException e) {
                Error.mostrarError("Error en el formato de la fecha", "La fecha debe tener el formato dd/MM/yyyy. Por favor, revisa el campo de la fecha.");
            } catch (IllegalArgumentException e) {
                Error.mostrarError("Error en el campo UoM", "El valor ingresado en el campo UoM no es válido. Asegúrate de que sea una unidad de medida válida.");
            } catch (SQLException e) {
                Error.mostrarError("Error en la base de datos", "Ha ocurrido un error inesperado. Por favor, asegurate de que los datos son correctos y que el proveedor es existente.");
            }catch (Exception e) {
                Error.mostrarError("Error inesperado", "Ha ocurrido un error inesperado. Por favor, revisa que los datos sean validos.");
            }
        } else {
            Error.mostrarError("Referencia no seleccionada", "Referencia no seleccionada. Selecciona una referencia para poder modificarla.");
            System.out.println("Por favor, selecciona una referencia para modificar.");
        }
    }

    @FXML
    private void onbtnNova_Clicked() {
        Referencia nuevaReferencia = new Referencia();
        nuevaReferencia.setDataAlta(LocalDate.now()); // Establece la fecha de alta con la fecha actual
        if (idFamilia == 0) {
            nuevaReferencia.setFamiliaID(1);
        } else {
            nuevaReferencia.setFamiliaID(idFamilia);
        }

        referenciaLogica.afegirReferencia(nuevaReferencia);
        llistarReferencias(idFamilia);
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
