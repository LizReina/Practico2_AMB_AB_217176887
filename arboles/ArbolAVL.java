/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

/**
 *
 * @author Liz Fernanda Reina Quispert
 * @param <K>
 * @param <V>
 */
public class ArbolAVL<K extends Comparable<K>,V> extends ArbolBusquedaBinaria<K,V>{
   private static final byte DIFERENCIA_MAXIMA = 1;

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if(claveAInsertar== null ){
           throw new IllegalArgumentException("no acepta null");
                   
        }
        
      if(valorAInsertar==null){
         throw new IllegalArgumentException("no acepta null");
      }
      
     super.raiz=this.insert(this.raiz,claveAInsertar,valorAInsertar);
    }
  
  public NodoBinario<K,V> insert(NodoBinario<K,V> nodoActual,K claveAInsertar,V valorAInsertar){
     if(NodoBinario.esNodoVacio(nodoActual)){
         NodoBinario<K,V> nuevoNodo=new NodoBinario<>(claveAInsertar,valorAInsertar);
         return nuevoNodo;
     }
    
    K claveActual=nodoActual.getClave();
    
        if(claveAInsertar.compareTo(claveActual) > 0){
           NodoBinario<K,V> supuestoNuevoHijoDerecho=insert(nodoActual.getHijoDerecho(),claveAInsertar,valorAInsertar);
          nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
          return balancear(nodoActual);
        }
           
         if(claveAInsertar.compareTo(claveActual)<0){
           NodoBinario<K,V> supuestoNuevoHijoIzquierdo=insert(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAInsertar);
          nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
          return balancear(nodoActual);
        }
        
    nodoActual.setValor(valorAInsertar);
    return nodoActual;
  }
  
public NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual){
    int alturaRamaIzq=altura(nodoActual.getHijoIzquierdo());
    int alturaRamaDer=altura(nodoActual.getHijoDerecho());
    int diferencia=alturaRamaIzq -  alturaRamaDer;
      
    if(diferencia > DIFERENCIA_MAXIMA){
        // hay que balancear
      //rotacion a la derecha, preguntando si es simple o doble
      NodoBinario<K,V> hijoIzquierdo=nodoActual.getHijoIzquierdo();
       alturaRamaIzq=altura(hijoIzquierdo.getHijoIzquierdo());
       alturaRamaDer=altura(hijoIzquierdo.getHijoDerecho());
        if(alturaRamaDer > alturaRamaIzq ){
            return rotacionDobleDerecha(nodoActual);
        }else{
            return rotacionSimpleDerecha(nodoActual); 
        }
      
    }else if( diferencia < - DIFERENCIA_MAXIMA){
        NodoBinario<K,V> hijoDerecho=nodoActual.getHijoDerecho();
       alturaRamaIzq=altura(hijoDerecho.getHijoIzquierdo());
       alturaRamaDer=altura(hijoDerecho.getHijoDerecho());
        if(alturaRamaDer < alturaRamaIzq ){
            return rotacionDobleIzquierda(nodoActual);
        }else{
            return rotacionSimpleIzquierda(nodoActual); 
        }
    }
    return nodoActual;
}
        
  public NodoBinario<K,V> rotacionSimpleDerecha(NodoBinario<K,V> nodoActual){
      NodoBinario<K,V> nodoQueRota=nodoActual.getHijoIzquierdo();
      nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
      nodoQueRota.setHijoDerecho(nodoActual);
      return nodoQueRota;
  }
  
   public NodoBinario<K,V> rotacionSimpleIzquierda(NodoBinario<K,V> nodoActual){
      NodoBinario<K,V> nodoQueRota=nodoActual.getHijoDerecho();
      nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
      nodoQueRota.setHijoIzquierdo(nodoActual);
      return nodoQueRota;
  }
   
  public NodoBinario<K,V> rotacionDobleDerecha(NodoBinario<K,V> nodoActual){
    nodoActual.setHijoIzquierdo(rotacionSimpleIzquierda(nodoActual.getHijoIzquierdo()));
    return this.rotacionSimpleDerecha(nodoActual);
  }

public NodoBinario<K,V> rotacionDobleIzquierda(NodoBinario<K,V> nodoActual){
    nodoActual.setHijoDerecho(rotacionSimpleDerecha(nodoActual.getHijoDerecho()));
    return this.rotacionSimpleIzquierda(nodoActual);
  }

   @Override
   
 //  8. Implemente el método eliminar de un árbol AVL 
  
    public V eliminar(K claveABuscar) {
        if(claveABuscar== null ){
           throw new IllegalArgumentException("no acepta null");
                   
        }
        
      V valorARetornar=buscar(claveABuscar);
      if(valorARetornar==null){
         throw new IllegalArgumentException("no acepta null");
      }
      
      this.raiz=eliminar(this.raiz,claveABuscar);
     return valorARetornar;
    }
    
public NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual,K claveAEliminar){
     K claveActual=nodoActual.getClave();
     if(claveAEliminar.compareTo(claveActual)>0){
         NodoBinario<K,V> posibleNuevoHijoDer=eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
         nodoActual.setHijoDerecho(posibleNuevoHijoDer);
         return balancear(nodoActual);
     }
     
     if(claveAEliminar.compareTo(claveActual)<0){
         NodoBinario<K,V> posibleNuevoHijoIzq=eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
         nodoActual.setHijoIzquierdo(posibleNuevoHijoIzq);
         return balancear(nodoActual);
     }
    
     // entonces ya lo ent¿contro y ahora tomar los casos
     //caso 1-> si es hoja
     if(nodoActual.esHoja()){
         return (NodoBinario<K,V>)NodoBinario.nodoVacio();
     }
     
     //caso 2 si tiene 1 hijo
     if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
        return balancear( nodoActual.getHijoIzquierdo());
     }
    
      if(nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
       return balancear(nodoActual.getHijoDerecho());
     }
      
     //caso 3 tiene ambos hijos
     NodoBinario<K,V> nodoReemplazo=this.buscarSucesor(nodoActual.getHijoDerecho());
     NodoBinario<K,V> posibleNuevoHijoDerecho=eliminar(nodoActual.getHijoDerecho(),nodoReemplazo.getClave());
     
     nodoActual.setClave(nodoReemplazo.getClave());
     nodoActual.setValor(nodoReemplazo.getValor());
     
     return balancear(nodoActual);

}
}
