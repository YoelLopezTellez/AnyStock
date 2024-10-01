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
 *
 * @author david
 */
public class ProveidorDAOImpl implements DAOInterface<Proveidor> {
    
    //variable estatica per declarar un patro singleton
    private static ProveidorDAOImpl instance;
    
    static{ //instanciar l'unic objecte de la clase
        instance = new ProveidorDAOImpl();
    }
    
    private ProveidorDAOImpl(){ //declarem el constructor privat per a que ningu pugui cridarlo
    }
    
    public static ProveidorDAOImpl getInstance(){
        return instance;
    }
    
    @Override
    public void afegir(Proveidor p){ //afeixira un nou proveidor a la BBDD
        String sql = "INSERT INTO proveidor (CIF, dataAlta, actiu, motiuInactivitat, nom, valoracioMitjana, minimUnitats, especialitat,id) VALUES (?,?,?,?,?,?,?,?,id);";
        
        try (Connection conn =DataSource.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);){
            
            setearPreparedStatement(pstm, p); //llama a el petode setearPreparedStatement() on estan tots els sets
            
            pstm.executeUpdate();
        }catch(SQLException e){
            
        };
        
    };
    
    @Override
    public void modificar(Proveidor p){
        
        String sql ="UPDATE proveidor SET  CIF = ?, dataAlta = ?, actiu = ?, motiuInactivitat = ?, nom = ?, valoracioMitjana = ?, minimUnitats = ?, especialitat = ? WHERE id = ?";
    
        try (Connection conn =DataSource.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);){
         
            setearPreparedStatement(pstm, p);
            pstm.setInt(9,p.getId());
            
            pstm.executeUpdate();
        }catch(SQLException e){
            
        }
    
    };
    
    @Override
    public void delete(int id){
        String sql ="DELETE FROM proveidor WHERE id= ?";
        
        try (Connection conn =DataSource.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);){
            
            pstm.setInt(1,id);
            pstm.executeUpdate();
            
        }catch(SQLException e){
            
        }
    };
    
    @Override
    public List<Proveidor> LlistarTot(){
        
        String sql="SELECT * FROM proveidor";
        
        List<Proveidor> ret = new ArrayList<Proveidor>();
        
        try (Connection conn =DataSource.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);){
            
            try(ResultSet rs = pstm.executeQuery();){
                while(rs.next()){
                    ret.add(obtenirProveidorResultSet(rs));    
                }
            }
            
            
        }catch(SQLException e){
            
        }        
        
        
        return ret;
    };
    
    @Override
    public Proveidor obtenir(int id){
        Proveidor res = null;
        String sql ="SELECT * FROM proveidor WHERE id = ?";
        
        try (Connection conn =DataSource.getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);){
            //posa en el prepared statment es a dir la query select el id
            pstm.setInt(9,id);
            try(ResultSet rs =pstm.executeQuery()){
                //la id es executada per el prepared statment y el resultat es posa al result set
                while(rs.next()){//per cada resultat es crida al metode obtenirProveidorResultSet() el cual dona una instancia Proveidor amb les dades del rs
                    res = obtenirProveidorResultSet(rs);    
                }

            }
        }catch(SQLException e){  
        }
        return res;
    };
    
    //metode per a fer els sets del preparedStatement en comptes de posarlo adalt
    private void setearPreparedStatement(final PreparedStatement pstm, Proveidor p)throws SQLException{
        
        pstm.setString(1,p.getCIF());
        pstm.setTimestamp(2, (Timestamp) p.getDataAlta());
        pstm.setBoolean(3, p.isActiu());
        pstm.setString(4, p.getMotiuInactivitat());
        pstm.setString(5, p.getNom());
        pstm.setFloat(6, p.getValoracio());
        pstm.setInt(7, p.getMinimUnitats()); 
        pstm.setString(8, p.getEspecialitat());
        
    }
    
    private Proveidor obtenirProveidorResultSet(final ResultSet rs)throws SQLException{
       
        Proveidor p;
        
        String CIF =rs.getString("CIF");
        Date dataAlta = rs.getDate("dataAlta");
        boolean actiu = rs.getBoolean("actiu");
        String motiuInactivitat =rs.getString("motiuInactivitat");
        String nom =rs.getString("nom");
        float valoracio = rs.getFloat("valoracioMitjana");
        int minimUnitats = rs.getInt("minimUnitats");
        String especialitat =rs.getString("especialitat");
        int id = rs.getInt("id");
        p= new Proveidor(nom, CIF, actiu, motiuInactivitat, dataAlta, valoracio, minimUnitats, especialitat);
       
        return p;
    }
}