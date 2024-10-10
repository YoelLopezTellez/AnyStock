/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Classe d'excepció per cridar-lo quan hi ha un format invàlid
 * @author reyes
 * @version 0.1
 */
public class FormatInvalidException extends Exception{

    public FormatInvalidException() {
    }

    public FormatInvalidException(String message) {
        super(message);
    }
    
}
