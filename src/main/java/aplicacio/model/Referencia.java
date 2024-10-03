/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio.model;

import java.util.Date;

/**
 * Clase que representa una referencia de producto en el sistema. Contiene
 * información relevante como nombre, unidad de medida, cantidad, familia,
 * proveedor, fecha de alta y precio.
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
    private UOM uom;  // Unidad de Medida
    private Date dataAlta;
    private Date ultimaDataAlarma;
    private String proveidor;
    private int familiaID;

    /**
     * Constructor de la clase Referencia.
     *
     * @param id Identificador único de la referencia.
     * @param vegadesAlarma Las veces que ha saltado la alarma de stock sobre esta referencia.
     * @param preuCompra Precio de la referencia.
     * @param observacions Observaciones sobre las referencias.
     * @param quantitat Cantidad disponible.
     * @param nom Nombre de la referencia.
     * @param uom Unidad de medida de la referencia.
     * @param dataAlta Fecha de alta de la referencia.
     * @param ultimDataAlarma Fecha en la que saltó la alarma por ultima vez.
     * @param proveidor Proveedor de la referencia.
     * @param familiaID La id de la familia
     */
    public Referencia(int id, int vegadesAlarma, float preuCompra, String observacions, int quantitat, String nom, UOM uom, Date dataAlta, Date ultimaDataAlarma, String proveidor, int familiaID) {
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

    // Getters y Setters

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

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public Date getUltimaDataAlarma() {
        return ultimaDataAlarma;
    }

    public void setUltimaDataAlarma(Date ultimaDataAlarma) {
        this.ultimaDataAlarma = ultimaDataAlarma;
    }

    public String getProveidor() {
        return proveidor;
    }

    public void setProveidor(String proveidor) {
        this.proveidor = proveidor;
    }    

    public int getFamiliaID() {
        return familiaID;
    }

    public void setFamiliaID(int familiaID) {
        this.familiaID = familiaID;
    }
    
}
