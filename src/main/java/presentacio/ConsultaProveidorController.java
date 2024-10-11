package presentacio;

import aplicacio.model.Proveidor;
import exceptions.BuitException;
import exceptions.CifRepetitException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import logica.ProveidorLogica;
/**
 * Controller que controla la pantalla de Consultar Proveidors, controla el poder modificar, mostrar el proveidors
 * a la taula, eliminar, afaixir, retornar o sortir al menu principal, aixi com seleccionar proveidors de la taula i
 * visualitzar les seves dades als camps corresponents amb l'opcio depen el cas de la seva edicio per a posterior modificacio o afeiximent.
 *
 * @author david
 */
public class ConsultaProveidorController implements Initializable {

    private ProveidorLogica provLogic = new ProveidorLogica();
    
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
    private ObservableList<Proveidor> Llistaproveidors = FXCollections.observableArrayList();
    
    private Error error;
    /**
     * Inicialitza el controlador, carregant els proveidors a la taula i ajustant la
     * interfície d'usuari.
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
        colEspecialitat.setCellValueFactory(new PropertyValueFactory<>("especialitat"));
        colMinimUnitats.setCellValueFactory(new PropertyValueFactory<>("minimUnitats"));
        
        
        tbView.setItems(Llistaproveidors);
        listarProveidors();
    }
    
    /**
     * Gestiona l'accio de pulsar el boto de sortir al menu principal
     */
       @FXML
    private void onBtnSortir_Clicked(){
         try {
            CanviPantalla.canviarPantalla(btnSortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Gestiona l'accio de pulsar el boto de afeixir un nou proveidor buit per a poder rellenar-lo a continuacio
     */
    @FXML
    private void onBtnNova_Clicked() {

        Proveidor p = new Proveidor();
        p.setCIF("");
        p.setNom("");
        p.setDataAlta(LocalDate.now());
        p.setActiu(false);
        p.setMotiuInactivitat("");
        p.setValoracio(0.0f);
        p.setEspecialitat("");
        p.setMinimUnitats(0);
        
        try{
            provLogic.afegirProveidor(p);
        }catch(CifRepetitException e){
                error.mostrarError("Error repetit", e.getMessage());
            }catch(BuitException e){
                error.mostrarError("Error buit", e.getMessage());
            }
        Llistaproveidors.add(p);
        listarProveidors();
        limpiarCampos();
    }
    /**
     * Gestiona l'accio de pulsar el boto de eliminar un proveidor seleccionat a la taula de proveidors.
     */
    @FXML
    private void onBtnEliminar_Clicked() {
        Proveidor provSeleccionat = tbView.getSelectionModel().getSelectedItem();
        if (provSeleccionat != null) {
            try {
            provLogic.EliminarProveidor(provSeleccionat.getId());
            
            }catch(Exception e) {
                error.mostrarError("Error al eliminar el proveidor:", e.getMessage());
            }
            Llistaproveidors.remove(provSeleccionat);
            limpiarCampos();
        } else {
            System.out.println("Por favor, selecciona un proveidor para eliminar.");
        }
    }
    /**
     * Gestiona l'accio de pulsar el boto de modificar un proveidor seleccionat a la taula de provaidors.
     */
    @FXML
    private void onBtnModificar_Clicked(){
        Proveidor provSeleccionat = tbView.getSelectionModel().getSelectedItem();
        if (provSeleccionat != null) {
            try {
            provSeleccionat.setCIF(tfCif.getText());
            provSeleccionat.setNom(tfNom.getText());
            provSeleccionat.setDataAlta(dpDataAlta.getValue());
            provSeleccionat.setActiu(cbActiu.isSelected());
            provSeleccionat.setMotiuInactivitat(tfMotiuInactivitat.getText());
            provSeleccionat.setValoracio(Float.parseFloat(tfValoracio.getText()));
            provSeleccionat.setEspecialitat(tfEspecialitat.getText());
            provSeleccionat.setMinimUnitats(Integer.parseInt(tfMinimUnitats.getText()));
            provLogic.ModificarProveidor(provSeleccionat);
            } catch (Exception ex){
                error.mostrarError("Error al modificar", ex.getMessage());
            }
            listarProveidors();
            limpiarCampos();
        } else {
            System.out.println("Por favor, selecciona un proveidor para modificar.");
        }
    }
    /**
     * Llista els proveidors afeixintlos a una taula i refrescant o actualitsant aquesta
     */
    private void listarProveidors() {
        Llistaproveidors.clear();
        Llistaproveidors.addAll(provLogic.llistarProveidors());
    }
    /**
     * Gestiona l'accio de clicar en la taula el proveidor deseat per la seva seleccio, 
     * posara les dades del proveidor als camps corresponents
     */    
    @FXML
    void ontbView_mouseClicked(MouseEvent event) {
        Proveidor provSeleccionat = tbView.getSelectionModel().getSelectedItem();
        
        if(provSeleccionat != null){
        tfId.setText(""+provSeleccionat.getId());
        tfCif.setText(provSeleccionat.getCIF());
        tfNom.setText(provSeleccionat.getNom());
        dpDataAlta.setValue(provSeleccionat.getDataAlta());
        cbActiu.setSelected(provSeleccionat.isActiu());
        tfMotiuInactivitat.setText(provSeleccionat.getMotiuInactivitat());
        tfValoracio.setText(""+provSeleccionat.getValoracio());
        tfEspecialitat.setText(provSeleccionat.getEspecialitat());
        tfMinimUnitats.setText(""+provSeleccionat.getMinimUnitats());
        }
    }
    /**
     * Neteja els camps de dades de la pantalla.
     */  
    private void limpiarCampos() {
        tfId.clear();
        tfNom.clear();
        tfCif.clear();
        dpDataAlta.setValue(java.time.LocalDate.now());
        cbActiu.setSelected(false);
        tfMotiuInactivitat.clear();
        tfValoracio.clear();
        tfEspecialitat.clear();
        tfMinimUnitats.clear();
    }
}
