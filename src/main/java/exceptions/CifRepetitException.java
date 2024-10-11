/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Classe d'excepció per cridar-lo quan hi ha un cif repetit
 * @author reyes
 * @version 0.1
 */
public class CifRepetitException extends Exception {

    public CifRepetitException() {
    }

    public CifRepetitException(String message) {
        super(message);
    }
    
}
