package arboles;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brandon
 * @param <K>
 * @param <V>
 */
public interface IArbolBinario<K extends Comparable<K>,V>{
    public K minimo();
    public K maximo();
    public void insertar(K clave,V valor);
    public V eliminar(K clave)  ;
    public V buscar(K clave);
    public boolean contiene(K clave);
    public int size();
    public int altura();
   
    public void vaciar();
    public boolean esArbolVacio();
    public int nivel();
    
    public List<K> recorridoPorNIvel();
    public List<K> recorridoPreOrden();
    public List<K> recorridoEnPostOrden();
    public List<K> recorridoInOrden();
    
     public String mostrarArbol();

  
}
