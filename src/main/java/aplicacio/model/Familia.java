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

public class Familia {

    private String nom;
    private String descripcio;
    private Date dataAlta;
    private String proveidorPerDefecte; // Proveïdor per defecte, opcional
    private String observacions;
    private int id;

    // Constructor
    public Familia(String nom, String descripcio, Date dataAlta, String proveidorPerDefecte, String observacions, int id) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.dataAlta = dataAlta;
        this.proveidorPerDefecte = proveidorPerDefecte;
        this.observacions = observacions;
        this.id = id;
    }

    // Getters y Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getProveidorPerDefecte() {
        return proveidorPerDefecte;
    }

    public void setProveidorPerDefecte(String proveidorPerDefecte) {
        this.proveidorPerDefecte = proveidorPerDefecte;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
