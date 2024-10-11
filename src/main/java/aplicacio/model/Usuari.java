package aplicacio.model;

import java.util.Date;

/**
 * Classe de entitat per poder guardar un usuari, comparar les seves creedencials i el seu tipus de rol
 * @author reyes
 * @version 0.2
 */

public class Usuari {

    private String nomUsuari;
    private String password;
    private Date dataAlta;
    private double sueldoMensual;
    private int anysAntiguitat;
    private TIPUSROL tipusRol;

    /**
     * constructor per iniciar una nova instància amb tots els camps
     * @param nomUsuari nom de l'usuari que es crea
     * @param password contrasenya de l'usuari que es crea
     * @param dataAlta data de alta quan es crea l'usuari
     * @param sueldoMensual el sou mensual que cobra aquest usuari
     * @param anysAntiguitat els anys que porta en l'empresa
     * @param tipusRol si es de rol responsable o vendedor
     */
    public Usuari(String nomUsuari, String password, Date dataAlta, double sueldoMensual, int anysAntiguitat, TIPUSROL tipusRol) {
        this.nomUsuari = nomUsuari;
        this.password = password;
        this.dataAlta = dataAlta;
        this.sueldoMensual = sueldoMensual;
        this.anysAntiguitat = anysAntiguitat;
        this.tipusRol = tipusRol;
    }

    // Constructor que solo recibe nomUsuari y password
    /**
     * Constructor tan sols amb aquests tres camps per iniciar sessio
     * @param nomUsuari nom de l'usuari que es crea
     * @param password contrasenya de l'usuari que es crea
     * @param tipusRol si es de rol responsable o vendedor
     */
    public Usuari(String nomUsuari, String password, TIPUSROL tipusRol) {
        this.nomUsuari = nomUsuari;
        this.password = password;
        this.tipusRol = tipusRol;
    }

    // Getters i Setters
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
