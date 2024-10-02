/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio;

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
                else if(rol.equalsIgnoreCase("Vendedor"))
                    usuari = new Vendedor(nom,contra);
                else
                    continue;
                
                
            }
        }
    }
    
    //retorna true si les credencials son correctes, false si no
    public boolean autentificacio(String nom, String pass){
        //utilitzem inputStream perque l'arxiu estarà empaquetat en el JAR i necessitem llegir en binari
        //getClass retorna la clase, getClassLoader serveix per cargar fitxers de recursos, l'ultim localitza si existeix el fitxer, retorna un inputStream o un null
        //return usuarios.containsKey(nom) && usuarios.get(nom).
        return false;
    }
    
    
}
