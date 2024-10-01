/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dades;

import java.util.List;

/**
 *
 * @author reyes
 */
public interface DAOInterface<T> {
    //Métodes per accedir a la base de dades
    //La T representa un tipus generic per poder ser utilitzat en qualsevol entitat com proveidor, familia o referencia
    
    public void afegir(T entitat);
    public void modificar(T entitat);
    public void delete(int id);
    public List<T> LlistarTot();
    public T obtenir(int id);
    
}
