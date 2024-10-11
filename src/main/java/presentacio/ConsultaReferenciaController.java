/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import logica.ReferenciaLogica;
import logica.Sessio;

/**
 * Controlador de l'FXML per gestionar la consulta de referències. Aquesta
 * classe s'encarrega de gestionar les interaccions de l'usuari amb la
 * interfície d'usuari.
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
     * Inicialitza el controlador de la classe. Aquest mètode és cridat
     * automàticament quan es carrega la vista. Estableix la visibilitat dels
     * botons segons el rol de l'usuari i associa les columnes de la taula amb
     * els atributs de les referències.
     */
    @FXML
    public void initialize() {
        usuari = Sessio.getInstancia().getUsuari();

        if (usuari.getTipusRol() == TIPUSROL.VENDEDOR) {
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            btnNova.setVisible(false);
        }
        // Associar les columnes de la taula amb els atributs de les referències
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPreu.setCellValueFactory(new PropertyValueFactory<>("preuCompra"));
        colQuantitat.setCellValueFactory(new PropertyValueFactory<>("quantitat"));
        colUom.setCellValueFactory(new PropertyValueFactory<>("uom"));
        colProveidor.setCellValueFactory(new PropertyValueFactory<>("proveidor"));
        colDataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        setIdFamilia(0);
        // Assignar la llista observable a la taula
        llistarReferencias(idFamilia);
        tbReferencia.setItems(referenciasObservableList);   
    }

    /**
     * Gestiona l'esdeveniment de clic del botó de filtre. Llegeix l'ID de la
     * família del camp de text i actualitza la llista de referències.
     */
    @FXML
    private void onbtnFiltro_Clicked() {
        setIdFamilia(Integer.parseInt(tfFiltro.getText()));
        limpiarCampos();
        llistarReferencias(idFamilia);
    }

    /**
     * Gestiona l'esdeveniment de clic del botó per canviar a la pantalla de
     * família.
     *
     * @param event L'esdeveniment de clic.
     * @throws IOException Si es produeix un error durant el canvi de pantalla.
     */
    @FXML
    private void onbtnFamilia_Clicked(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnFamilia.getScene(), "/cat/copernic/projecte_grup4/ConsultaFamilia.fxml");
    }

    /**
     * Gestiona l'esdeveniment de clic del botó de sortir.
     *
     * @param event L'esdeveniment de clic.
     * @throws IOException Si es produeix un error durant el canvi de pantalla.
     */
    @FXML
    private void onbtnSortir_Clicked(ActionEvent event) throws IOException {
        CanviPantalla.canviarPantalla(btnSortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
    }

    /**
     * Llista les referències associades a una família determinada.
     *
     * @param idFamilia L'ID de la família per la qual es volen obtenir les
     * referències.
     */
    private void llistarReferencias(int idFamilia) {
        // verifica si salta o no la alarma.
        AlarmaStock(); 
        // Limpiamos la lista observable antes de añadir los datos actualizados
        referenciasObservableList.clear();
        referenciasObservableList.addAll(referenciaLogica.llistarReferencias(idFamilia));
    }
    
    /**
     * Controla si hi han 10 o menys unitats d'un producte per posarlo en vermell
     */
    public void AlarmaStock(){
        // setRowFactory per a definir un RowFactory personalitzat i aixi modificar l'estil per a l'alarma d'estoc.
        tbReferencia.setRowFactory(new Callback<TableView<Referencia>, TableRow<Referencia>>(){
            @Override
            public TableRow<Referencia> call(TableView<Referencia> tableView){
                return new TableRow<Referencia>(){
                    @Override
                    protected void updateItem(Referencia referencia, boolean empty){
                        super.updateItem(referencia, empty);
                        
                        // Si la fila no está buida i la referencia no es null
                        if(referencia != null && !empty) {
                            // Comproba si la quantitat es menor o igual a 10
                            if(referencia.getQuantitat() <= 10){
                                // Cambiar el color del texto de toda la fila
                                setStyle("-fx-background-color: rgba(255, 0, 0, 0.3);");
                            }else{        
                                // Restaura l'estil per defecte si no cumpleix la condició.
                                setStyle("");
                            }
                        }else{
                            setStyle(""); // Restaura l'estil per defecte si está buit.
                        }
                    }
                };
            }
        });
    }
    
    /**
     * Gestiona l'esdeveniment de clic del botó d'eliminació. Elimina la
     * referència seleccionada de la taula i actualitza la llista.
     */
    @FXML
    private void onbtnEliminar_Clicked() {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            referenciaLogica.eliminarReferencia(referenciaSeleccionada.getId());
            referenciasObservableList.remove(referenciaSeleccionada);
            limpiarCampos();
        } else {
            Error.mostrarError("Referència no seleccionada", "Referència no seleccionada. Selecciona una referència per a poder eliminar-la.");
            System.out.println("Por favor, selecciona una referencia para eliminar.");
        }
    }

    /**
     * Gestiona l'esdeveniment de clic del botó de modificació. Modifica les
     * dades de la referència seleccionada amb els valors dels camps de text.
     */
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
                Error.mostrarError("Error en el format de número", "Si us plau, ingressa un número vàlid en els camps de Proveïdor, Quantitat, Preu o Família.");
            } catch (DateTimeParseException e) {
                Error.mostrarError("Error en el format de la data", "La data ha de tenir el format dd/MM/yyyy. Si us plau, revisa el camp de la data.");
            } catch (IllegalArgumentException e) {
                Error.mostrarError("Error en el camp UoM", "El valor ingressat en el camp UoM no és vàlid. Assegura't que sigui una unitat de mesura vàlida.");
            } catch (SQLException e) {
                Error.mostrarError("Error en la base de dades", "Ha ocorregut un error inesperat. Si us plau, assegura't que les dades són correctes i que el proveïdor és existent.");
            } catch (Exception e) {
                Error.mostrarError("Error inesperat", "Ha ocorregut un error inesperat. Si us plau, revisa que les dades siguin vàlides.");
            }
        } else {
            Error.mostrarError("Referència no seleccionada", "Referència no seleccionada. Selecciona una referència per a poder modificar-la.");
            System.out.println("Por favor, selecciona una referencia para modificar.");
        }
    }

    /**
     * Gestiona l'esdeveniment de clic del botó de nova referència. Crea una
     * nova referència i l'afegeix a la llista de referències.
     */
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
        tbReferencia.getSelectionModel().selectLast();
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        tbReferencia.scrollTo(referenciaSeleccionada);
        tbReferencia.requestFocus();
        tbReferencia.getSelectionModel().focus(tbReferencia.getSelectionModel().getSelectedIndex());
        seleccionarReferencia(referenciaSeleccionada);
    }

    /**
     * Gestiona l'esdeveniment de clic en la taula de referències. Selecciona la
     * referència que ha estat clicada i omple els camps de text.
     *
     * @param event L'esdeveniment de clic del ratolí.
     */
    @FXML
    private void ontbReferenciaMouseClicked(MouseEvent event) {
        Referencia referenciaSeleccionada = tbReferencia.getSelectionModel().getSelectedItem();
        seleccionarReferencia(referenciaSeleccionada);
        System.out.println(referenciaSeleccionada.getUltimaDataAlarma());
    }

    /**
     * Omple els camps de text amb les dades de la referència seleccionada.
     *
     * @param referenciaSeleccionada La referència seleccionada per omplir els
     * camps.
     */
    private void seleccionarReferencia(Referencia referenciaSeleccionada) {
        if (referenciaSeleccionada != null) {
            // Setejar els valors en els camps de text
            tfId.setText(String.valueOf(referenciaSeleccionada.getId()));
            tfNom.setText(referenciaSeleccionada.getNom());

            // Formatar la data d'alta
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            tfDataAlta.setText(referenciaSeleccionada.getDataAlta().format(format));

            // Setejar la resta de valors
            tfProveidor.setText(String.valueOf(referenciaSeleccionada.getProveidor()));
            tfQuantitat.setText(String.valueOf(referenciaSeleccionada.getQuantitat()));

            // Verificar si la data d'alarma és nul·la abans de formatar-la
            if (referenciaSeleccionada.getUltimaDataAlarma() != null) {
                tfDataAlarma.setText(referenciaSeleccionada.getUltimaDataAlarma().format(format));
            } else {
                tfDataAlarma.setText(""); // Deixar el camp buit si és nul
            }

            // Setejar la resta de valors en els camps corresponents
            tfIdFamilia.setText(String.valueOf(referenciaSeleccionada.getFamiliaID()));
            tfVegadesAlarma.setText(String.valueOf(referenciaSeleccionada.getVegadesAlarma()));
            tfPreu.setText(String.valueOf(referenciaSeleccionada.getPreuCompra()));
            tfUom.setText(String.valueOf(referenciaSeleccionada.getUom()));
            taObservacions.setText(referenciaSeleccionada.getObservacions());
        }
    }

    /**
     * Neteja tots els camps de text de la interfície d'usuari.
     */
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
