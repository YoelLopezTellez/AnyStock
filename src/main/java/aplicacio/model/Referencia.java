/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio.model;

/**
 *
 * @author Yoel
 */
import java.util.Date;

public class Referencia {

    private String nom;
    private String uom;  // Unidad de Medida
    private int quantitat;
    private String familia;
    private String proveidor;
    private Date dataAlta;
    private float preu;
    private int id;

    // Constructor
    public Referencia(String nom, String uom, int quantitat, String familia, String proveidor, Date dataAlta, float preu, int id) {
        this.nom = nom;
        this.uom = uom;
        this.quantitat = quantitat;
        this.familia = familia;
        this.proveidor = proveidor;
        this.dataAlta = dataAlta;
        this.preu = preu;
        this.id = id;
    }

    // Getters y Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getProveidor() {
        return proveidor;
    }

    public void setProveidor(String proveidor) {
        this.proveidor = proveidor;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
