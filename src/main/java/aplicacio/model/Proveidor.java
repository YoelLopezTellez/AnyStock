/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio.model;

import java.util.Date;

/**
 * Clase que representa un proveedor en el sistema. Contiene información
 * relevante como nombre, CIF, estado activo, motivo de inactividad, fecha de
 * alta, valoración, número mínimo de unidades y especialidad.
 *
 * @author Yoel
 */
public class Proveidor {

    private String CIF;
    private Date dataAlta;
    private boolean actiu;
    private String motiuInactivitat;
    private String nom;
    private float valoracio;
    private int minimUnitats;
    private String especialitat;
    private int id;

    /**
     * Es un cosntructor buit de Proveidors per posar amb seters en comptes de parametres, aixi esta
     * mes optimitzat per al ResultSet
     */
    public Proveidor(){
    }
    
    /**
     * Constructor de la clase Proveidor.
     *
     * 
     * @param CIF Código de identificación fiscal del proveedor.
     * @param dataAlta Fecha de alta del proveedor.
     * @param actiu Estado activo del proveedor.
     * @param motiuInactivitat Motivo de inactividad (si no está activo).
     * @param nom Nombre del proveedor.
     * @param valoracio Valoración del proveedor.
     * @param minimUnitats Número mínimo de unidades.
     * @param especialitat Especialidad del proveedor.
     * @param id El id del Proveidor.
     */
    public Proveidor(String CIF, Date dataAlta, boolean actiu, String motiuInactivitat, String nom, float valoracio, int minimUnitats, String especialitat, int id) {
        
        this.CIF = CIF;
        this.dataAlta = dataAlta;
        this.actiu = actiu;
        this.motiuInactivitat = motiuInactivitat;
        this.nom = nom;
        this.valoracio = valoracio;
        this.minimUnitats = minimUnitats;
        this.especialitat = especialitat;
        this.id = id;
    }

    // Getters y Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public String getMotiuInactivitat() {
        return motiuInactivitat;
    }

    public void setMotiuInactivitat(String motiuInactivitat) {
        this.motiuInactivitat = motiuInactivitat;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public float getValoracio() {
        return valoracio;
    }

    public void setValoracio(float valoracio) {
        this.valoracio = valoracio;
    }

    public int getMinimUnitats() {
        return minimUnitats;
    }

    public void setMinimUnitats(int minimUnitats) {
        this.minimUnitats = minimUnitats;
    }

    public String getEspecialitat() {
        return especialitat;
    }

    public void setEspecialitat(String especialitat) {
        this.especialitat = especialitat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
