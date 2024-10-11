/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import aplicacio.model.TIPUSROL;
import aplicacio.model.Usuari;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Login per comprovar que l'usuari i contrasenya existeixen en un fitxer dintre
 * de resources
 *
 * @author reyes
 * @version 0.3
 */
public class Login {

    private Map<String, Usuari> usuaris = new HashMap<>();

    /**
     * Per construir aquesta classe necessia que li passin la ruta i aquesta cridarà al mètode cargarUsuarios
     * @param ruta la ruta del fitxer on es troba per cargar usuaris
     * @throws IOException si hi ha un error al llegir o cargar el fitxer
     */
    public Login(InputStream ruta) throws IOException {
        cargarUsuarios(ruta);
    }

    /**
     * utilitzem inputStream perque l'arxiu estarà empaquetat en el JAR i necessitem llegir en binari, li pasarem la ruta empaquetada
     * @param ruta la ruta del fitxer on es troba per cargar usuaris
     * @throws IOException si hi ha un error al llegir o cargar el fitxer
     */
    private void cargarUsuarios(InputStream ruta) throws IOException {
        BufferedReader fitxer = new BufferedReader(new InputStreamReader(ruta));
        String linea;
        while ((linea = fitxer.readLine()) != null) {
            String[] credenciales = linea.split(","); //separem les credencials ","
            if (credenciales.length == 3) {
                String nom = credenciales[0].trim();
                String contra = credenciales[1].trim();
                String rol = credenciales[2].trim();

                // com el Map agafa tan sols Usuari, necesitem especificar que usuari es d'una subclase dintre de la clase Usuari
                //ja que si fiquem tan sols una de les subclases, no ens deixará ficar-la en el Map usuaris
                Usuari usuari;

                if (rol.equalsIgnoreCase("Responsable")) {
                    usuari = new Usuari(nom, contra, TIPUSROL.RESPONSABLE);
                } else if (rol.equalsIgnoreCase("Vendedor")) {
                    usuari = new Usuari(nom, contra, TIPUSROL.VENDEDOR);
                } else {
                    continue;
                }
                //posem l'usuari a la llista usuaris
                usuaris.put(nom, usuari);
            }
        }
    }

    /**
     * retorna l'usuari si la autentificacio es correcte
     * @param nom el nom que tindrà l'usuari i el sistema ha de comprovar
     * @param pass la contrasenya que tindrà l'usuari i el sistema ha de comprovar
     * @return retorna l'usuari de la llista usuaris o un null si no coincideix
     */
    public Usuari autentificacio(String nom, String pass) {

        Usuari usuari = usuaris.get(nom);
        //si l'usuari no es null i coincideix amb la contrasenya que li han passat retorna l'usuari de la llista usuaris
        if (usuari != null && usuari.getPassword().equals(pass)) {
            return usuari;
        }

        return null;
    }

}
