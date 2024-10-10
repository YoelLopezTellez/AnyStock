/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio.model;

import java.time.LocalDate;

/**
 * Classe que representa una referència de producte en el sistema. Conté
 * informació rellevant com a nom, unitat de mesura, quantitat, família,
 * proveïdor, data d'alta i preu.
 *
 * @author Mario
 */
public class Referencia {

    private int id;
    private int vegadesAlarma;
    private float preuCompra;
    private String observacions;
    private int quantitat;
    private String nom;
    private UOM uom;  // Unitat de mesura
    private LocalDate dataAlta;
    private LocalDate ultimaDataAlarma;
    private int proveidor;
    private int familiaID;

    /**
     * Constructor de la classe Referència.
     *
     * @param id Identificador únic de la referència.
     * @param vegadesAlarma Les vegades que ha saltat l'alarma d'estoc sobre
     * aquesta referència.
     * @param preuCompra Preu de la referència.
     * @param observacions Observacions sobre les referències.
     * @param quantitat Quantitat disponible.
     * @param nom Nom de la referència.
     * @param uom Unitat de mesura de la referència.
     * @param dataAlta Data d'alta de la referència.
     * @param ultimDataAlarma Data en la qual va saltar l'alarma per última
     * vegada.
     * @param proveidor Proveïdor de la referència.
     * @param familiaID La id de la família
     */
    public Referencia(int id, int vegadesAlarma, float preuCompra, String observacions, int quantitat, String nom, UOM uom, LocalDate dataAlta, LocalDate ultimaDataAlarma, int proveidor, int familiaID) {
        this.id = id;
        this.vegadesAlarma = vegadesAlarma;
        this.preuCompra = preuCompra;
        this.observacions = observacions;
        this.quantitat = quantitat;
        this.nom = nom;
        this.uom = uom;
        this.dataAlta = dataAlta;
        this.ultimaDataAlarma = ultimaDataAlarma;
        this.proveidor = proveidor;
        this.familiaID = familiaID;
    }

    public Referencia() {

    }

    // Getters i Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVegadesAlarma() {
        return vegadesAlarma;
    }

    public void setVegadesAlarma(int vegadesAlarma) {
        this.vegadesAlarma = vegadesAlarma;
    }

    public float getPreuCompra() {
        return preuCompra;
    }

    public void setPreuCompra(float preuCompra) {
        this.preuCompra = preuCompra;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public UOM getUom() {
        return uom;
    }

    public void setUom(UOM uom) {
        this.uom = uom;
    }

    public LocalDate getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(LocalDate dataAlta) {
        this.dataAlta = dataAlta;
    }

    public LocalDate getUltimaDataAlarma() {
        return ultimaDataAlarma;
    }

    public void setUltimaDataAlarma(LocalDate ultimaDataAlarma) {
        this.ultimaDataAlarma = ultimaDataAlarma;
    }

    public int getProveidor() {
        return proveidor;
    }

    public void setProveidor(int proveidor) {
        this.proveidor = proveidor;
    }

    public int getFamiliaID() {
        return familiaID;
    }

    public void setFamiliaID(int familiaID) {
        this.familiaID = familiaID;
    }

}
