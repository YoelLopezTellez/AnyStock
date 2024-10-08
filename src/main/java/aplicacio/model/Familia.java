/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio.model;

import java.time.LocalDate;


/**
 * Clase que representa una familia de productos. Contiene información básica
 * como nombre, descripción, fecha de alta, proveedor por defecto, observaciones
 * e ID.
 *
 * @author Yoel
 */
public class Familia {

    private int id;
    private LocalDate dataAlta;
    private String observacions;
    private String nom;
    private String descripcio;
    private int proveidorPerDefecte; 

    /**
     * Constructor de la clase Familia.
     *
     * @param nom Nombre de la familia.
     * @param descripcio Descripción de la familia.
     * @param dataAlta Fecha de alta de la familia.
     * @param proveidorPerDefecte Proveedor por defecto.
     * @param observacions Observaciones sobre la familia.
     * @param id Identificador de la familia.
     */
    public Familia(int id, LocalDate dataAlta, String observacions, String nom, String descripcio, int proveidorPerDefecte) {
        this.id = id;
        this.dataAlta = dataAlta;
        this.observacions = observacions;
        this.nom = nom;
        this.descripcio = descripcio;
        this.proveidorPerDefecte = proveidorPerDefecte;

    }

    //Constructor vacio
    public Familia() {

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

    public LocalDate getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(LocalDate dataAlta) {
        this.dataAlta = dataAlta;
    }

    public int getProveidorPerDefecte() {
        return proveidorPerDefecte;
    }

    public void setProveidorPerDefecte(int proveidorPerDefecte) {
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
