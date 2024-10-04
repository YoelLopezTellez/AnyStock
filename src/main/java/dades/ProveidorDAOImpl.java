/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dades;

import aplicacio.model.Proveidor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Es el Data Acces Object de la clase Proveïdor, que implementa la interfaç
 * DAOInterface on estaran els metodes afegir, modificar, eliminar, obtenir i
 * llistar proveïdors.
 *
 * @author david
 */
public class ProveidorDAOImpl implements DAOInterface<Proveidor>, DAOInterfaceLlistat<Proveidor> {

    //Variable estatica per declarar un patro singleton
    private static ProveidorDAOImpl instance;

    static { //Instanciar l'unic objecte de la clase
        instance = new ProveidorDAOImpl();
    }

    private ProveidorDAOImpl() { //Declarem el constructor privat per a que ningu pugui cridarlo
    }

    public static ProveidorDAOImpl getInstance() {
        return instance;
    }
    /**
     * Afegeix un Proveidor a la base de dades
     *
     * @param p Es el Proveïdor a afeixir a la base de dades
     */
    @Override
    public void afegir(Proveidor p) {
        String sql = "INSERT INTO proveidor (CIF, dataAlta, actiu, motiuInactivitat, nom, valoracioMitjana, minimUnitats, especialitat,id) VALUES (?,?,?,?,?,?,?,?,id);";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            setearPreparedStatement(pstm, p); //Llama a el petode setearPreparedStatement() on estan tots els sets dels "?"
            pstm.executeUpdate();//Executa el prepared statement
        } catch (SQLException e) {
        }
    }
    /**
     * Modifica un Proveïdor a la base de dades
     * 
     * @param p Es el Proveïdor modificat que es modificara a la base de dades 
     */
    @Override
    public void modificar(Proveidor p) {
        String sql = "UPDATE proveidor SET  CIF = ?, dataAlta = ?, actiu = ?, motiuInactivitat = ?, nom = ?, valoracioMitjana = ?, minimUnitats = ?, especialitat = ? WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            setearPreparedStatement(pstm, p);//Crida a el metode setearPreparedStatement() on estan tots els sets dels "?"
            pstm.setInt(9, p.getId()); //Posa el id a la posicio 9 
            pstm.executeUpdate();
        } catch (SQLException e) {
        }
    } 
    /**
     * Elimina un Proveïdor a la base de dades
     * 
     * @param id Es el id del Proveïdor que s'eliminara de la base de dades 
     */    
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM proveidor WHERE id= ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setInt(1, id); //posa el id donat a la posicio 1
            pstm.executeUpdate();
        } catch (SQLException e) {
        }
    }    
    /**
     * Llista tots els Proveïdors en un ArrayList
     * 
     * @return Llista ArrayList de Proveïdors
     */    
    @Override
    public List<Proveidor> LlistarTot() {
        String sql = "SELECT * FROM proveidor";

        List<Proveidor> ret = new ArrayList<Proveidor>(); //crea una arraylist de proveidors
        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) { //cada sortida o proveidor de la query guardada en el resultSet s'afegeix al ArrayList
                    ret.add(obtenirProveidorResultSet(rs));
                }
            }
        } catch (SQLException e) {
        }
        return ret;
    }
    /**
     * Obte o selecciona un Proveïdor a partir del seu id
     * 
     * @param id La id del Proveïdor a seleccionar o obtenir
     * @return Un Proveïdor
     */   
    @Override
    public Proveidor obtenir(int id) {
        Proveidor res = null;
        String sql = "SELECT * FROM proveidor WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            //Posa en el prepared statment es a dir la query select el id
            pstm.setInt(9, id);
            try (ResultSet rs = pstm.executeQuery()) {
                //La id es executada per el prepared statment y el resultat es posa al result set
                while (rs.next()) {//Per cada resultat es crida al metode obtenirProveidorResultSet() el cual dona una instancia Proveidor amb les dades del rs
                    res = obtenirProveidorResultSet(rs);
                }
            }
        } catch (SQLException e) {
        }
        return res;
    }  
    /**
     * Posa al PreparedStatement els paramentres en les posicions per no
     * repetir codi
     * 
     * @param pstm PreparedStatement
     * @param p Proveïdor
     */   
    private void setearPreparedStatement(final PreparedStatement pstm, Proveidor p) throws SQLException {
        pstm.setString(1, p.getCIF());
        pstm.setTimestamp(2, (Timestamp) p.getDataAlta());
        pstm.setBoolean(3, p.isActiu());
        pstm.setString(4, p.getMotiuInactivitat());
        pstm.setString(5, p.getNom());
        pstm.setFloat(6, p.getValoracio());
        pstm.setInt(7, p.getMinimUnitats());
        pstm.setString(8, p.getEspecialitat());
    }
    /**
     * Obte un Proveïdor a partir del contigut del Output de la consulta contingut en
     * el ResultSet
     * 
     * @param rs ResultSet
     * @return Proveidor seleccionat o contingut en el ResultSet
     */
    private Proveidor obtenirProveidorResultSet(final ResultSet rs) throws SQLException {
        Proveidor p; //Un proveidor vuit

        //Creem variables per a cada atribut del proveidor amb les dades del ResultSet
        String CIF = rs.getString("CIF");
        Date dataAlta = rs.getDate("dataAlta");
        boolean actiu = rs.getBoolean("actiu");
        String motiuInactivitat = rs.getString("motiuInactivitat");
        String nom = rs.getString("nom");
        float valoracio = rs.getFloat("valoracioMitjana");
        int minimUnitats = rs.getInt("minimUnitats");
        String especialitat = rs.getString("especialitat");
        int id = rs.getInt("id");
        
        //Instanciem el priveidor vuit amb el constructor posant-li els atributs guardats anteriorment
        p = new Proveidor(nom, CIF, actiu, motiuInactivitat, dataAlta, valoracio, minimUnitats, especialitat);
        return p;
    }
}
