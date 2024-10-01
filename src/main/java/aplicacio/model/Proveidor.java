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

public class Proveidor {

    private String nom;
    private String CIF;
    private boolean actiu;
    private String motiuInactivitat;
    private Date dataAlta;
    private float valoracio;
    private int minimUnitats;
    private String especialitat;

    // Constructor
    public Proveidor(String nom, String CIF, boolean actiu, String motiuInactivitat, Date dataAlta, float valoracio, int minimUnitats, String especialitat) {
        this.nom = nom;
        this.CIF = CIF;
        this.actiu = actiu;
        this.motiuInactivitat = motiuInactivitat;
        this.dataAlta = dataAlta;
        this.valoracio = valoracio;
        this.minimUnitats = minimUnitats;
        this.especialitat = especialitat;
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

    public String getEspecialitat() {
        return especialitat;
    }

    public void setMinimUnitats(int minimUnitats) {
        this.minimUnitats = minimUnitats;
    }

    public void setEspecialitat(String especialitat) {
        this.especialitat = especialitat;
    }

}

