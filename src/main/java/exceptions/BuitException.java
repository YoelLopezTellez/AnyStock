/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Classe d'excepció per cridar-lo quan hi ha un camp buit
 * @author reyes
 * @version 0.1
 */
public class BuitException extends Exception{

    public BuitException() {
    }

    public BuitException(String message) {
        super(message);
    }
    
}
