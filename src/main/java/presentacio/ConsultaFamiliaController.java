package presentacio;

import logica.Sessio;
import aplicacio.model.TIPUSROL;
import aplicacio.model.Usuari;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
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
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.SelectionMode;

/**
 * Controlador que gestiona la consulta, modificació i eliminació de famílies de
 * productes a la interfície d'usuari. Aquesta classe permet a l'usuari
 * visualitzar la llista de famílies de productes, així com crear, modificar i
 * eliminar famílies. També s'encarrega de gestionar la selecció de famílies a
 * la taula, actualitzant els camps de text amb les dades corresponents, i
 * aplicant validacions en els camps d'entrada. A més, adapta la interfície en
 * funció del tipus d'usuari, ocultant opcions segons el rol (VENDEDOR) de
 * l'usuari actual. La classe utilitza el model de dades 'Familia' i la lògica
 * associada a les operacions sobre famílies a través de la classe
 * 'FamiliaLogica'.
 *
 * @author Yoel
 */
public class ConsultaFamiliaController {

    @FXML
    private Button Btn_Eliminar, Btn_Modificar, Btn_Nova, Btn_Productes, Btn_Sortir;

    @FXML
    private TableColumn<Familia, Integer> col_IdFamilia;
    @FXML
    private TableColumn<Familia, String> col_Nom, col_Descripcio, col_Proveidor;
    @FXML
    private TableColumn<Familia, LocalDate> col_DataAlta;
    @FXML
    private TableColumn<Familia, String> col_observacions;

    @FXML
    private TextArea ta_Descripcio, ta_Observacions;

    @FXML
    private TableView<Familia> tb_Familia;

    @FXML
    private TextField tf_DataAlta, tf_ID, tf_Nom, tf_Proveidor;

    private FamiliaLogica familiaLogica = new FamiliaLogica();

    private ObservableList<Familia> familiasObservableList = FXCollections.observableArrayList();

    /**
     * Inicialitza el controlador, carregant les famílies i ajustant la
     * interfície d'usuari.
     */
    @FXML
    public void initialize() {

        // Carregar l'usuari que tenim guardat a la sessió
        Usuari usuari = Sessio.getInstancia().getUsuari();

        // Si l'usuari és de tipus VENDEDOR, ocultem els botons "Eliminar", "Nova" i "Modificar"
        if (usuari.getTipusRol() == TIPUSROL.VENDEDOR) {
            Btn_Eliminar.setVisible(false);
            Btn_Nova.setVisible(false);
            Btn_Modificar.setVisible(false);
        }

        col_IdFamilia.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_Descripcio.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        col_DataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        col_observacions.setCellValueFactory(new PropertyValueFactory<>("observacions"));
        col_Proveidor.setCellValueFactory(new PropertyValueFactory<>("proveidorPerDefecte"));

        tb_Familia.setItems(familiasObservableList);
        listarFamilias();
    }

    /**
     * Gestiona l'acció de crear una nova família.
     */
    public void onBtn_Nova_Clicked() {
        // Crear una nova família i assignar valors per defecte
        Familia nuevaFamilia = new Familia();
        nuevaFamilia.setDataAlta(LocalDate.now()); // Estableix la data d'alta amb la data actual
        nuevaFamilia.setProveidorPerDefecte(0);    // Proveïdor buit
        nuevaFamilia.setObservacions("");          // Observacions buides
        nuevaFamilia.setNom("");                   // Nom buit
        nuevaFamilia.setDescripcio("");            // Descripció buida

        // Crida al mètode de lògica per afegir la família
        familiaLogica.afegirFamilia(nuevaFamilia);

        // Actualitzar la llista de famílies a la interfície
        listarFamilias();

        // Seleccionar l'última fila a la taula
        TableViewSelectionModel<Familia> selectionModel = tb_Familia.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE); // Configura la selecció en mode únic

        int rowCount = tb_Familia.getItems().size(); // Obtener el número de filas
        if (rowCount > 0) {
            // Seleccionar la última fila
            selectionModel.select(rowCount - 1);

            // Assegurar que la última fila sigui visible
            tb_Familia.scrollTo(rowCount - 1);

            // Establecer el foco en la tabla
            tb_Familia.requestFocus(); // Posa el focus a la TableView

            // Activar el model de selecció, assegurant que estigui efectivament seleccionada
            tb_Familia.getSelectionModel().focus(rowCount - 1);

            // Actualitzar els camps de text amb les dades de la família seleccionada
            actualizarCamposConFamiliaSeleccionada();
        }
    }

    @FXML
    private void onBtn_Modificar_Clicked() {
        Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
        if (familiaSeleccionada != null) {
            familiaSeleccionada.setNom(tf_Nom.getText());
            familiaSeleccionada.setDescripcio(ta_Descripcio.getText());
            familiaSeleccionada.setObservacions(ta_Observacions.getText());

            // Validar i convertir la data
            if (!tf_DataAlta.getText().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    familiaSeleccionada.setDataAlta(LocalDate.parse(tf_DataAlta.getText(), formatter));
                } catch (Exception e) {
                    Error.mostrarError("Error de format", "Format de data incorrecte: " + e.getMessage()); // Añadir título y mensaje
                    return; // No continuar si la data és invàlida
                }
            }

            // Convertir el camp tf_Proveidor a int abans d'assignar
            try {
                int idProveidor = Integer.parseInt(tf_Proveidor.getText());
                familiaSeleccionada.setProveidorPerDefecte(idProveidor);
            } catch (NumberFormatException e) {
                Error.mostrarError("Error d'ID", "ID del proveïdor no vàlid. Assegura't que sigui un número."); // Añadir título y mensaje
                return; // Sortir si l'ID del proveïdor és invàlid
            }

            try {
                familiaLogica.modificarFamilia(familiaSeleccionada);
                listarFamilias(); // Actualitza la llista després de modificar
                tb_Familia.refresh(); // Refresca la taula
                limpiarCampos(); // Neteja els camps
            } catch (Exception e) {
                Error.mostrarError("Error en modificació", "Error al modificar la família: " + e.getMessage()); // Añadir título y mensaje
                e.printStackTrace();
            }
        } else {
            Error.mostrarError("Error de selecció", "Si us plau, selecciona una família per modificar."); // Añadir título y mensaje
        }
    }

    @FXML
    private void onBtn_Eliminar_Clicked() {
        Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
        if (familiaSeleccionada != null) {
            try {
                familiaLogica.eliminarFamilia(familiaSeleccionada.getId());
                familiasObservableList.remove(familiaSeleccionada);
                limpiarCampos();
                System.out.println("Familia eliminada correctamente.");
            } catch (Exception e) {
                // Mostrar el missatge d'error a l'usuari utilitzant la classe Error
                Error errorDialog = new Error();
                errorDialog.mostrarError("Error al eliminar familia", e.getMessage());
                // També pots mostrar l'error a la consola si ho desitges
                System.out.println("Error al eliminar la familia: " + e.getMessage());
            }
        } else {
            // Missatge quan no hi ha família seleccionada
            Error errorDialog = new Error();
            errorDialog.mostrarError("Selecció de família", "Per favor, selecciona una família per eliminar.");
            System.out.println("Si us plau, selecciona una família per eliminar.");
        }
    }

    /**
     * Actualitza els camps de text i àrees de text amb les dades de la família
     * seleccionada.
     */
    private void actualizarCamposConFamiliaSeleccionada() {
        Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
        if (familiaSeleccionada != null) {
            // Actualitzar el camp ID
            tf_ID.setText(String.valueOf(familiaSeleccionada.getId()));
            // Actualitzar el camp Nom
            tf_Nom.setText(familiaSeleccionada.getNom());
            // Actualitzar l'àrea de text Descripció
            ta_Descripcio.setText(familiaSeleccionada.getDescripcio());
            // Actualitzar l'àrea de text Observacions
            ta_Observacions.setText(familiaSeleccionada.getObservacions());

            // Format per a la data
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Actualitzar el camp Data d'Alta
            tf_DataAlta.setText(familiaSeleccionada.getDataAlta().format(format));
            // Actualitzar el camp Proveïdor per defecte
            tf_Proveidor.setText(String.valueOf(familiaSeleccionada.getProveidorPerDefecte()));
        }
    }

    @FXML
    private void onBtn_Productes_Clicked() {
        try {
            // Canviar la pantalla a la vista de Consulta de Referències
            CanviPantalla.canviarPantalla(Btn_Productes.getScene(), "/cat/copernic/projecte_grup4/ConsultaReferencia.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtn_Sortir_Clicked() {
        try {
            // Canviar la pantalla a la vista del menú
            CanviPantalla.canviarPantalla(Btn_Sortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Llistar les famílies disponibles i afegir-les a la llista observable.
     */
    private void listarFamilias() {
        // Netejar la llista abans d'afegir les noves famílies
        familiasObservableList.clear();
        // Afegir famílies a la llista observable
        familiasObservableList.addAll(familiaLogica.llistarFamilias());
    }

    @FXML
    private void onTb_FamiliaMouseClicked(MouseEvent event) {
        // Actualitzar els camps de text amb les dades de la família seleccionada
        actualizarCamposConFamiliaSeleccionada();
    }

    /**
     * Neteja els camps de text de la interfície.
     */
    private void limpiarCampos() {
        tf_ID.clear();
        tf_Nom.clear();
        ta_Descripcio.clear();
        ta_Observacions.clear();
        tf_DataAlta.clear();
        tf_Proveidor.clear();
    }
}
