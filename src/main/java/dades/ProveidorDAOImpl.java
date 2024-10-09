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
import java.time.LocalDate;
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

    //Variable estatica per declarar un patro singleton.
    private static ProveidorDAOImpl instance;

    static { //Instanciar l'unic objecte de la clase.
        instance = new ProveidorDAOImpl();
    }

    private ProveidorDAOImpl() { //Declarem el constructor privat per a que ningu pugui cridarlo.
    }

    public static ProveidorDAOImpl getInstance() {
        return instance;
    }

    /**
     * Afegeix un Proveidor a la base de dades.
     *
     * @param p Es el Proveïdor a afeixir a la base de dades.
     */
    @Override
    public void afegir(Proveidor p) {
        String sql = "INSERT INTO proveidor (CIF, dataAlta, actiu, motiuInactivitat, nom, valoracioMitjana, minimUnitats, especialitat) VALUES (?,?,?,?,?,?,?,?);";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            setearPreparedStatement(pstm, p); //Llama a el petode setearPreparedStatement() on estan tots els sets dels "?".

            pstm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));// pone en dataAlta la fecha actual 
            pstm.executeUpdate();//Executa el prepared statement.
        } catch (SQLException e) {
        }
    }
    
    public boolean existeixCIF(String cif){
        String sql = "SELECT COUNT(*) FROM proveidor WHERE cif = ?";
        
        try(Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);){
            pstm.setString(1, cif);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Modifica un Proveïdor a la base de dades.
     *
     * @param p Es el Proveïdor modificat que es modificara a la base de dades.
     */
    @Override
    public void modificar(Proveidor p) throws Exception{
        String sql = "UPDATE proveidor SET  CIF = ?, dataAlta = ?, actiu = ?, motiuInactivitat = ?, nom = ?, valoracioMitjana = ?, minimUnitats = ?, especialitat = ? WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            setearPreparedStatement(pstm, p);//Crida a el metode setearPreparedStatement() on estan tots els sets dels "?".

            pstm.setDate(2, java.sql.Date.valueOf(p.getDataAlta()));//posa en dataAlta la data del proveidor.

            pstm.setInt(9, p.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Elimina un Proveïdor a la base de dades.
     *
     * @param id Es el id del Proveïdor que s'eliminara de la base de dades .
     */
    @Override
    public void delete(int id) {
        String quitarProveidorFamiliaSql ="UPDATE familia SET PROVEIDOR_id = null WHERE id = ?";
        String sql = "DELETE FROM proveidor WHERE id= ?";
        String verificarProveidorSql ="SELECT * FROM proveidor WHERE id = ?";
        String verificarFamiliasSql = "SELECT COUNT(*) FROM familia WHERE PROVEIDOR_id = ?";
        String verificarReferenciaSql ="SELECT COUNT(*) FROM referencia WHERE PROVEIDOR_id = ?";
        
        try (Connection conn = DataSource.getConnection();PreparedStatement pstmquitarProveidorFamilia = conn.prepareStatement(quitarProveidorFamiliaSql);PreparedStatement pstmVerificarReferencia = conn.prepareStatement(verificarReferenciaSql); PreparedStatement pstm = conn.prepareStatement(sql);PreparedStatement pstmVerificarFamilia = conn.prepareStatement(verificarFamiliasSql);PreparedStatement pstmVerificarProveidor = conn.prepareStatement(verificarProveidorSql);) {
            
            // Verificar si el proveidor existeix
            pstmVerificarProveidor.setInt(1, id);
            try (ResultSet rsProveidor = pstmVerificarProveidor.executeQuery()) {
                if (!rsProveidor.next()) {
                    System.out.println("Error: El proveidor con ID " + id + " no existe.");
                    return;
                }
            }
            //Verificar si hi ha referencies asociades
            pstmVerificarReferencia.setInt(1, id);
            try (ResultSet rsReferencias = pstmVerificarReferencia.executeQuery()) {
                 if (rsReferencias.next() && rsReferencias.getInt(1) > 0) {
                    System.out.println("Error: No es pot eliminar el proveidor perque te referencies asociades.");
                    return;
                }
            }
            // Verificar si hay familias asociadas y poner null en proveidor_id si las hay antes de eliminarla
            pstmVerificarFamilia.setInt(1, id);
            try (ResultSet rsFamilias = pstmVerificarFamilia.executeQuery()) {
                
                if (rsFamilias.next() && rsFamilias.getInt(1) > 0) {
                    pstmquitarProveidorFamilia.setInt(1, rsFamilias.getInt(1));
                    pstmquitarProveidorFamilia.executeUpdate();
                    System.out.println("Eliminado proveidor con referencias asociadas a familias.");
                    
                }
            }
            // Eliminar la familia
            pstm.setInt(1, id);
            pstm.executeUpdate();
            System.out.println("Proveidor elimint correctament.");

        } catch (SQLException e) {
            System.out.println("message:"+e.getMessage());
        }
    }

    /**
     * Llista tots els Proveïdors en un ArrayList.
     *
     * @return Llista ArrayList de Proveïdors.
     */
    @Override
    public List<Proveidor> LlistarTot() {
        String sql = "SELECT * FROM proveidor";

        List<Proveidor> ret = new ArrayList<Proveidor>(); //crea una arraylist de proveidors.
        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) { //cada sortida o proveidor de la query guardada en el resultSet s'afegeix al ArrayList.
                    ret.add(obtenirProveidorResultSet(rs));
                }
            }
        } catch (SQLException e) {
        }
        return ret;
    }

    /**
     * Obte o selecciona un Proveïdor a partir del seu id.
     *
     * @param id La id del Proveïdor a seleccionar o obtenir.
     * @return Un Proveïdor.
     */
    @Override
    public Proveidor obtenir(int id) {
        Proveidor res = null;
        String sql = "SELECT * FROM proveidor WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {
            //Posa en el prepared statment es a dir la query select el id.
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                //La id es executada per el prepared statment y el resultat es posa al result set.
                while (rs.next()) {//Per cada resultat es crida al metode obtenirProveidorResultSet() el cual dona una instancia Proveidor amb les dades del rs.
                    res = obtenirProveidorResultSet(rs);
                }
            }
        } catch (SQLException e) {
        }
        return res;
    }

    /**
     * Afeigeix una llista de proveïdors a ka base de dades, si algun CIF
     * proveïdor es igual al de la base de dades no l'afegeix i el guarde en un
     * ArrayList de proveídors no afeixits que retorna.
     *
     * @param list Llista de Proveïdors a afeixir.
     * @return Una llista dels Proveïdors que no han sigut afeixits perque ja
     * estan a la base de dades.
     */
    public List<Proveidor> afeixirLlistaProveidors(List<Proveidor> list) {

        List<Proveidor> proveidorsNoAfeixits = new ArrayList<Proveidor>();
        for (Proveidor p : list) {
            if ((obtenirProvPerCIF(p.getCIF())) == null) {
                afegir(p);
            } else {
                proveidorsNoAfeixits.add(p);
            }

        }

        return proveidorsNoAfeixits;
    }

    /**
     * A partir del CIF S'obte el proveïdor el id retornat sera -1.
     *
     * @param CIF el CIF del proveidor.
     * @return El proveidor que te el CIF especificat.
     */
    public Proveidor obtenirProvPerCIF(String CIF) {
        Proveidor res = null;
        String sql = "SELECT * FROM proveidor WHERE CIF = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql);) {

            pstm.setString(1, CIF);
            try (ResultSet rs = pstm.executeQuery()) {

                while (rs.next()) {
                    res = obtenirProveidorResultSet(rs);
                }
            }
        } catch (SQLException e) {
        }
        return res;
    }

    /**
     * Posa al PreparedStatement els paramentres en les posicions per no repetir
     * codi.
     *
     * @param pstm PreparedStatement.
     * @param p Proveïdor.
     */
    private void setearPreparedStatement(final PreparedStatement pstm, Proveidor p) throws SQLException {
        pstm.setString(1, p.getCIF());
        pstm.setBoolean(3, p.isActiu());
        pstm.setString(4, p.getMotiuInactivitat());
        pstm.setString(5, p.getNom());
        pstm.setFloat(6, p.getValoracio());
        pstm.setInt(7, p.getMinimUnitats());
        pstm.setString(8, p.getEspecialitat());
    }

    /**
     * Obte un Proveïdor a partir del contigut del Output de la consulta
     * contingut en el ResultSet.
     *
     * @param rs ResultSet.
     * @return Proveidor seleccionat o contingut en el ResultSet.
     */
    private Proveidor obtenirProveidorResultSet(final ResultSet rs) throws SQLException {
        Proveidor p; //Un proveidor vuit.
        //Cridem alconstructor buit i posen amb sets els seus atributs.
        p = new Proveidor();

        //Fem els Sets per posar els atributs en el Proveidor.
        p.setCIF(rs.getString("CIF"));

        // obtenim el Date y lo pasamos a LocalDate
        p.setDataAlta(rs.getDate("dataAlta").toLocalDate());

        p.setActiu(rs.getBoolean("actiu"));
        p.setMotiuInactivitat(rs.getString("motiuInactivitat"));
        p.setNom(rs.getString("nom"));
        p.setValoracio(rs.getFloat("valoracioMitjana"));
        p.setMinimUnitats(rs.getInt("minimUnitats"));
        p.setEspecialitat(rs.getString("especialitat"));
        p.setId(rs.getInt("id"));
        return p;
    }
}
