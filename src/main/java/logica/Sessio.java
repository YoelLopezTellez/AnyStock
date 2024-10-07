/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import aplicacio.model.Usuari;

/**
 *
 * @author reyes
 */
public class Sessio {
    private static Sessio instancia;
    private Usuari usuari;
    
    //constructor buit per no fer un new ja que es una clase Singleton
    private Sessio(){}

    //metode per comprovar que tan sols es pot iniciar aquest metode tan sols una vegada
    public static Sessio getInstancia() {
        
        if(instancia == null)
            instancia = new Sessio();
        return instancia;
    }

    //metode per establir l'usuari
    public void iniciarSessio(Usuari usuari){
        this.usuari = usuari;
    }
    
    public Usuari getUsuari() {
        return usuari;
    }
    
    public void tancarSessio(){
        usuari = null;
    }
}
