/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Login per comprovar que l'usuari i contrasenya existeixen en un fitxer dintre de resources
 * @author reyes
 */
public class Login {
    
    private String ruta;

    public Login(String ruta) {
        this.ruta = ruta;
    }
    
    //retorna true si les credencials son correctes, false si no
    public boolean autentificacio(String nom, String pass){
        //utilitzem inputStream perque l'arxiu estarà empaquetat en el JAR i necessitem llegir en binari
        //getClass retorna la clase, getClassLoader serveix per cargar fitxers de recursos, l'ultim localitza si existeix el fitxer, retorna un inputStream o un null
        try(InputStream inputstream = getClass().getClassLoader().getResourceAsStream(ruta);
                BufferedReader fitxer = new BufferedReader(new InputStreamReader(inputstream))){
            String linea;
            while((linea = fitxer.readLine()) != null){
                String[] credenciales = linea.split(","); //separem les credencials "," 
                if(credenciales.length == 3){
                    String usuari = credenciales[0];
                    String contra = credenciales[1];
                    if(usuari.equals(nom) && contra.equals(pass)){
                        return true;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
}
