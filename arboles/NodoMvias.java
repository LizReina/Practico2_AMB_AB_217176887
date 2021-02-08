/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author liz fernanda reina quispert
 * @param <K>
 * @param <V>
 */
public class NodoMvias<K,V> {
    private List<K> listaClaves;
    private List<V> listaValores;
    private List<NodoMvias<K,V>> listaDeHijos;

    public NodoMvias(int orden) {
        listaDeHijos =new LinkedList<>();
        listaClaves=new LinkedList<>();
        listaValores=new LinkedList<>();
        
        for (int i = 0; i < orden-1; i++) {
            listaDeHijos.add(NodoMvias.nodoVacio());
            listaClaves.add((K)NodoMvias.datoVacio());
            listaValores.add((V)NodoMvias.datoVacio());
        }
      listaDeHijos.add(NodoMvias.nodoVacio());
    }

    public NodoMvias(int orden,K primerClave,V primerValor) {
        this(orden);
        this.listaClaves.set(0, primerClave);
        this.listaValores .set(0, primerValor);
    }
   
    public static NodoMvias nodoVacio(){
        return null;
    }
    
    public static Object datoVacio(){
        return null;
    }

  /* retorna la posicion in
*/

  public K getClave(int posicion){
      return this.listaClaves.get(posicion);
  }
  
    public V getValor(int posicion){
      return this.listaValores.get(posicion);
  }
   
  public void setClave(int posicion, K clave){
       this.listaClaves.set(posicion,clave);
  }
  
   public V setValor(int posicion, V valor){
      return listaValores.set(posicion,valor);
  }
  
    public void setHijos(int posicion, NodoMvias nodo){
       listaDeHijos.set(posicion,nodo);
  }
    
  public NodoMvias<K,V> getHijo(int posicion){
      return listaDeHijos.get(posicion);
  }

  public static boolean esNodoVacio(NodoMvias nodo){
      return nodo == NodoMvias.nodoVacio(); 
  }
  
  public boolean esClaveVacia(int posicion){
      return listaClaves.get(posicion)== NodoMvias.datoVacio();
  }
 
  public boolean esHijoVacio(int posicion){
      return listaDeHijos.get(posicion)== NodoMvias.nodoVacio();
  }
  
  public boolean esHoja(){
      for (int i = 0; i < this.listaDeHijos.size(); i++) {
          if(!esHijoVacio(i)){
              return false;
          }
      }
     return true;
  }
  
  public boolean estanClavesLlenas(){
      for (int i = 0; i < listaClaves.size(); i++) {
          if(this.esClaveVacia(i)){
              return false;
          }
      }
      return true;
  }
  
  public int cantidadDeClavesNoVacias(){
      int cantidad=0;
      for (int i = 0; i < listaClaves.size(); i++) {
          if(!esClaveVacia(i)){
              cantidad++;
          }
      }
      return cantidad;
  }
  
   public int cantidadDeHijosVacios(){
      int cantidad=0;
      for (int i = 0; i < listaDeHijos.size(); i++) {
          if(esHijoVacio(i)){
              cantidad++;
          }
      }
      return cantidad;
  }
   
     public int cantidadDeHijosNoVacios(){
      int cantidad=0;
      for (int i = 0; i < listaDeHijos.size(); i++) {
          if(!esHijoVacio(i)){
              cantidad++;
          }
      }
      return cantidad;
  }
   
 public List<K> getListaDeClaves() {
        return listaClaves;
    }
    
    
    
    @Override
    public String toString() {
        return "Claves:"+listaClaves.toString() + "\n"
                +"Hijos:"+listaDeHijos.toString();
    }
}
