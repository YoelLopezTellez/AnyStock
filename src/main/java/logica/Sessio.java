/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import aplicacio.model.Usuari;

/**
 * Classe singleton per tenir guardat l'usuari en tot moment amb el canvi d'escenari
 * @author reyes
 * @version 0.2
 */
public class Sessio {
    private static Sessio instancia;
    private Usuari usuari;
    
    /**
     * constructor buit privat per no poder fer una nova instància ja que es una clase Singleton
     */
    private Sessio(){}

    //
    /**
     * mètode per comprovar que tan sols es pot iniciar aquest metode tan sols una vegada
     * @return la única instància de sessio
     */
    public static Sessio getInstancia() {
        
        if(instancia == null)
            instancia = new Sessio();
        return instancia;
    }

    /**
     * metode per establir l'usuari
     * @param usuari serà l'usuari guardat per tota la sessió
     */
    public void iniciarSessio(Usuari usuari){
        this.usuari = usuari;
    }
    
    /**
     * retorna l'usuari actual logueat
     * @return l'usuari actual
     */
    public Usuari getUsuari() {
        return usuari;
    }
    
    /**
     * Possa l'usuari en null
     */
    public void tancarSessio(){
        usuari = null;
    }
}
