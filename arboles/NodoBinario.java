package arboles;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brandon
 */
public class NodoBinario<K,V> {
   private K clave;
   private V valor;
   private NodoBinario<K,V> hijoIzquierdo;
   private NodoBinario<K,V> hijoDerecho;

   //creacion De constructor sin parametro
   public NodoBinario() {
    }

   //creacion De constructor sin parametro
  
 //  nodo= new NodoBinario<NodoBinario<K,V>, string>(20,"Carlos juaquin");
    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public NodoBinario<K, V> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }


     public boolean esVacioHijoIzquierdo(){
       return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
   }
     public boolean esVacioHijoDerecho(){
       return NodoBinario.esNodoVacio(this.getHijoDerecho());
     }
     
     public boolean esHoja(){
       return this.esVacioHijoIzquierdo() && this.esVacioHijoDerecho(); 
   }
   
    public boolean esNodoCompleto(NodoBinario<?,?> nodo){
        return !esVacioHijoIzquierdo() && !esVacioHijoDerecho();
    }
     
    public static boolean esNodoVacio(NodoBinario<?,?> nodo){
        return nodo==null;
   }

   public static NodoBinario<?,?> nodoVacio(){
       return null;
   }
   
}
