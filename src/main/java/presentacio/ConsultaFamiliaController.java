package presentacio;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import logica.FamiliaLogica;
import aplicacio.model.Familia;

public class ConsultaFamiliaController {

    @FXML
    private Button Btn_Eliminar, Btn_Modificar, Btn_Nova, Btn_Productes, Btn_Sortir;

    @FXML
    private TableColumn<Familia, Integer> col_IdFamilia;
    @FXML
    private TableColumn<Familia, String> col_Nom, col_Descripcio, col_Proveidor;
    @FXML
    private TableColumn<Familia, Date> col_DataAlta;
    @FXML
    private TableColumn<Familia, String> col_observacions;

    @FXML
    private TextArea ta_Descripcio, ta_Observacions;

    @FXML
    private TableView<Familia> tb_Familia;

    @FXML
    private TextField tf_DataAlta, tf_ID, tf_Nom, tf_Proveidor;

    private FamiliaLogica familiaLogica = new FamiliaLogica();

    // Usamos un ObservableList para enlazar los datos con la tabla
    private ObservableList<Familia> familiasObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        col_IdFamilia.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_Descripcio.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        col_DataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        col_observacions.setCellValueFactory(new PropertyValueFactory<>("observacions"));
        col_Proveidor.setCellValueFactory(new PropertyValueFactory<>("proveidorPerDefecte"));

        // Vinculamos el ObservableList a la tabla
        tb_Familia.setItems(familiasObservableList);
        listarFamilias();
    }

    @FXML
    private void onBtn_Nova_Clicked() {
        Familia nuevaFamilia = new Familia();
        nuevaFamilia.setNom(tf_Nom.getText());
        nuevaFamilia.setDescripcio(ta_Descripcio.getText());
        nuevaFamilia.setObservacions(ta_Observacions.getText());
        nuevaFamilia.setDataAlta(new Date());
        nuevaFamilia.setProveidorPerDefecte(tf_Proveidor.getText());

        familiaLogica.afegirFamilia(nuevaFamilia);
        // Agregamos la nueva familia a la lista observable para actualizar la tabla
        familiasObservableList.add(nuevaFamilia);
        limpiarCampos();
    }

    @FXML
    private void onBtn_Modificar_Clicked() {
        try {
            int id = Integer.parseInt(tf_ID.getText());
            Familia familiaExistente = familiaLogica.obtenirFamilia(id);
            if (familiaExistente != null) {
                familiaExistente.setNom(tf_Nom.getText());
                familiaExistente.setDescripcio(ta_Descripcio.getText());
                familiaExistente.setObservacions(ta_Observacions.getText());
                familiaExistente.setDataAlta(new Date());
                familiaExistente.setProveidorPerDefecte(tf_Proveidor.getText());
                familiaLogica.modificarFamilia(familiaExistente);
                listarFamilias();
                limpiarCampos();
            } else {
                System.out.println("Familia no encontrada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, introduce un ID válido.");
        }
    }

    @FXML
    private void onBtn_Eliminar_Clicked() {
        try {
            int id = Integer.parseInt(tf_ID.getText());
            familiaLogica.eliminarFamilia(id);
            // Eliminamos la familia de la lista observable para actualizar la tabla
            familiasObservableList.removeIf(f -> f.getId() == id);
            limpiarCampos();
        } catch (NumberFormatException e) {
            System.out.println("Por favor, introduce un ID válido.");
        }
    }

    @FXML
    private void onBtn_Productes_Clicked() {
        try {
            CanviPantalla.canviarPantalla(Btn_Productes.getScene(), "/cat/copernic/projecte_grup4/ConsultaReferencia.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtn_Sortir_Clicked() {
        try {
            CanviPantalla.canviarPantalla(Btn_Sortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarFamilias() {
        // Limpiamos la lista observable antes de añadir los datos actualizados
        familiasObservableList.clear();
        familiasObservableList.addAll(familiaLogica.llistarFamilias());
    }

    @FXML
    private void onTb_FamiliaMouseClicked(MouseEvent event) {
        Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
        if (familiaSeleccionada != null) {
            tf_ID.setText(String.valueOf(familiaSeleccionada.getId()));
            tf_Nom.setText(familiaSeleccionada.getNom());
            ta_Descripcio.setText(familiaSeleccionada.getDescripcio());
            ta_Observacions.setText(familiaSeleccionada.getObservacions());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            tf_DataAlta.setText(sdf.format(familiaSeleccionada.getDataAlta()));
            tf_Proveidor.setText(familiaSeleccionada.getProveidorPerDefecte());
        }
    }

    private void limpiarCampos() {
        tf_ID.clear();
        tf_Nom.clear();
        ta_Descripcio.clear();
        ta_Observacions.clear();
        tf_DataAlta.clear();
        tf_Proveidor.clear();
    }
}
