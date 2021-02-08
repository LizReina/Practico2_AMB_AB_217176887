/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles.Exception;

/**
 *
 * @author brandon
 */
public class ExceptionOrdenInvalida extends Exception{

    public ExceptionOrdenInvalida() {
        super("orden invalida");
    }

    public ExceptionOrdenInvalida(String string) {
        super(string);
    }
    
}
