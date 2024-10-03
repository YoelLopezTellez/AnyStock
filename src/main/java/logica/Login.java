/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import aplicacio.model.Responsable;
import aplicacio.model.Usuari;
import aplicacio.model.Venedor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Login per comprovar que l'usuari i contrasenya existeixen en un fitxer dintre de resources
 * @author reyes
 * @version 0.2
 */
public class Login {
    
    private Map<String, Usuari> usuaris = new HashMap<>();

    public Login(InputStream ruta) throws IOException {
        cargarUsuarios(ruta);
    }
    
    //utilitzem inputStream perque l'arxiu estarà empaquetat en el JAR i necessitem llegir en binari, li pasarem la ruta empaquetada
    private void cargarUsuarios(InputStream ruta) throws IOException{
        BufferedReader fitxer = new BufferedReader(new InputStreamReader(ruta));
        String linea;
        while((linea = fitxer.readLine()) != null){
            String[] credenciales = linea.split(","); //separem les credencials ","
            if(credenciales.length == 3){
                String nom = credenciales[0].trim();
                String contra = credenciales[1].trim();
                String rol = credenciales[2].trim();
                
                // com el Map agafa tan sols Usuari, necesitem especificar que usuari es d'una subclase dintre de la clase Usuari
                //ja que si fiquem tan sols una de les subclases, no ens deixará ficar-la en el Map usuaris
                Usuari usuari;
                
                if(rol.equalsIgnoreCase("Responsable"))
                    usuari = new Responsable(nom,contra);
                else if(rol.equalsIgnoreCase("Venedor"))
                    usuari = new Venedor(nom,contra);
                else
                    continue;
                
                usuaris.put(nom, usuari);
            }
        }
    }
    
    //retorna l'usuari si la autentificacio es correcte
    public Usuari autentificacio(String nom, String pass){
        
        Usuari usuari = usuaris.get(nom);
        
        if(usuari != null && usuari.getPassword().equals(pass))
            return usuari;
        
        return null;
    }
    
    
}
