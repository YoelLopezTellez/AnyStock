package aplicacio.model;

import java.util.Date;

public class Usuari {

    private String nomUsuari;
    private String password;
    private Date dataAlta;
    private double sueldoMensual;
    private int anysAntiguitat;
    private TIPUSROL tipusRol;

    // Constructor que recibe todos los atributos
    public Usuari(String nomUsuari, String password, Date dataAlta, double sueldoMensual, int anysAntiguitat, TIPUSROL tipusRol) {
        this.nomUsuari = nomUsuari;
        this.password = password;
        this.dataAlta = dataAlta;
        this.sueldoMensual = sueldoMensual;
        this.anysAntiguitat = anysAntiguitat;
        this.tipusRol = tipusRol;
    }

    // Constructor que solo recibe nomUsuari y password
    public Usuari(String nomUsuari, String password, TIPUSROL tipusRol) {
        this.nomUsuari = nomUsuari;
        this.password = password;
        this.tipusRol = tipusRol;
    }

    // Getters y Setters
    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(double sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

    public int getAnysAntiguitat() {
        return anysAntiguitat;
    }

    public void setAnysAntiguitat(int anysAntiguitat) {
        this.anysAntiguitat = anysAntiguitat;
    }

    public TIPUSROL getTipusRol() {
        return tipusRol;
    }

    public void setTipusRol(TIPUSROL tipusRol) {
        this.tipusRol = tipusRol;
    }
}
