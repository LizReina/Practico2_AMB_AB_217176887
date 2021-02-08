package arboles;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
public class ArbolBusquedaBinaria<K extends Comparable<K>,V> implements IArbolBinario<K,V> {
    protected NodoBinario<K,V> raiz;

    public ArbolBusquedaBinaria() {
    }
            
    public ArbolBusquedaBinaria(List<K> claveInOrden,List<V> valorInOrden,
                                List<K> claveNoInOrden,List<V> valorNoInOrden,boolean usandoPreOrden) {
        
        if(claveInOrden.isEmpty() || valorInOrden.isEmpty() || claveNoInOrden.isEmpty() || valorNoInOrden.isEmpty()){
            throw new IllegalArgumentException("no puede aver una lista vacia");
        }
        
        if(claveInOrden.size() != claveInOrden.size() ||
           claveInOrden.size() != claveNoInOrden.size()|| 
           claveInOrden.size() != valorNoInOrden.size() ){
            
             throw new IllegalArgumentException("Los parametros no pueden ser de diferente tamaño");
        }
        
        
        if(usandoPreOrden){
            recostruirConPreOrden(claveInOrden,valorInOrden,claveNoInOrden,valorNoInOrden);
        }else{
             recostruirConPostOrden(claveInOrden,valorInOrden,claveNoInOrden,valorNoInOrden);
        }
    }
   
    public NodoBinario<K,V> recostruirConPreOrden(List<K> claveInOrden,List<V> valorInOrden,
                                        List<K> claveEnPreOrden,List<V> valorEnPreOrden){
        if(claveInOrden.isEmpty()){
            return (NodoBinario<K,V>)NodoBinario.nodoVacio();
        }
        
        
        int posicionClavePadrePreOrden=0;
        K clavePadre = claveEnPreOrden.get(posicionClavePadrePreOrden);
        V valorPadre = valorEnPreOrden.get(posicionClavePadrePreOrden);
        int posicionClavePadreInOrden =this.posicionClave(clavePadre, claveInOrden);
        
        //para armar la rama izquierda
          List<K> claveInOrdenPorIzquierda=claveInOrden.subList(0,posicionClavePadreInOrden);
          List<V> valorInOrdenPorIzquierda=valorInOrden.subList(0,posicionClavePadreInOrden);
          List<K> claveEnPreOrdenPorIzquierda=claveEnPreOrden.subList(1,posicionClavePadreInOrden+1);
          List<V> valorEnPreOrdenPorIzquierda=valorEnPreOrden.subList(1,posicionClavePadreInOrden+1);
          NodoBinario<K,V> hijoIzquierdo=recostruirConPreOrden(claveInOrdenPorIzquierda,valorInOrdenPorIzquierda,claveEnPreOrdenPorIzquierda,
           valorEnPreOrdenPorIzquierda);
          
          
        //para armar la rama derecha
          List<K> claveInOrdenPorDerecha=claveInOrden.subList(posicionClavePadreInOrden+1,claveInOrden.size());
          List<V> valorInOrdenPorDerecha=valorInOrden.subList(posicionClavePadreInOrden+1,claveInOrden.size());
          List<K> claveEnPreOrdenPorDerecha=claveEnPreOrden.subList(posicionClavePadreInOrden+1,claveEnPreOrden.size());
          List<V> valorEnPreOrdenPorDerecha=valorEnPreOrden.subList(posicionClavePadreInOrden+1,claveEnPreOrden.size());
          NodoBinario<K,V> hijoDerecho=recostruirConPreOrden(claveInOrdenPorDerecha,valorInOrdenPorDerecha,claveEnPreOrdenPorDerecha,
           valorEnPreOrdenPorDerecha);
          
        //armando el nodoActual
        NodoBinario<K,V> nodoActual=new NodoBinario<>(clavePadre,valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        
        return nodoActual;        
    }
    
    public NodoBinario<K,V>  recostruirConPostOrden(List<K> claveInOrden,List<V> valorInOrden,List<K>claveEnPostOrden,List<V> valorEnPostOrden){
        if(claveInOrden.isEmpty()){
            return (NodoBinario<K,V>)NodoBinario.nodoVacio();
        }
        
        int posicionClavePadrePreOrden=claveEnPostOrden.size();
        K clavePadre = claveEnPostOrden.get(posicionClavePadrePreOrden);
        V valorPadre = valorEnPostOrden.get(posicionClavePadrePreOrden);
        int posicionClavePadreInOrden =this.posicionClave(clavePadre, claveInOrden);
        
        //para armar la rama izquierda
          List<K> claveInOrdenPorIzquierda=claveInOrden.subList(0,posicionClavePadreInOrden);
          List<V> valorInOrdenPorIzquierda=valorInOrden.subList(0,posicionClavePadreInOrden);
          List<K> claveEnPostOrdenPorIzquierda=claveEnPostOrden.subList(0,posicionClavePadreInOrden);
          List<V> valorEnPostOrdenPorIzquierda=valorEnPostOrden.subList(0,posicionClavePadreInOrden);
          NodoBinario<K,V> hijoIzquierdo=recostruirConPostOrden(claveInOrdenPorIzquierda,valorInOrdenPorIzquierda,claveEnPostOrdenPorIzquierda,
           valorEnPostOrdenPorIzquierda);
          
          
        //para armar la rama derecha
          List<K> claveInOrdenPorDerecha=claveInOrden.subList(posicionClavePadreInOrden+1,claveInOrden.size());
          List<V> valorInOrdenPorDerecha=valorInOrden.subList(posicionClavePadreInOrden+1,claveInOrden.size());
          List<K> clavePostOrdenPorDerecha=claveEnPostOrden.subList(posicionClavePadreInOrden+1,claveEnPostOrden.size());
          List<V> valorPostOrdenPorDerecha=valorEnPostOrden.subList(posicionClavePadreInOrden+1,claveEnPostOrden.size());
          NodoBinario<K,V> hijoDerecho=recostruirConPostOrden(claveInOrdenPorDerecha,valorInOrdenPorDerecha,clavePostOrdenPorDerecha,
           valorPostOrdenPorDerecha);
          
        //armando el nodoActual
        NodoBinario<K,V> nodoActual=new NodoBinario<>(clavePadre,valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        
        return nodoActual;        
    }
    
    public int posicionClave(K claveABuscar,List<K> listaDeClaves){
        for (int i = 0; i < listaDeClaves.size(); i++) {
            K claveActual=listaDeClaves.get(i);
            if(claveActual.compareTo(claveABuscar)==0){
                return i;
            }
        }
       return -1;
    }
    
      @Override
    public void vaciar() {
      this.raiz=(NodoBinario<K,V>)NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
       return NodoBinario.esNodoVacio(raiz);
    }
 
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
       if(this.esArbolVacio()){
           this.raiz=new NodoBinario(claveAInsertar, valorAInsertar);
           return;
       }
       
       NodoBinario<K,V> nodoAnterior= (NodoBinario<K,V>)NodoBinario.nodoVacio();
       NodoBinario<K,V> nodoActual=this.raiz;
       
       while(!NodoBinario.esNodoVacio(nodoActual)){
           K claveActual=nodoActual.getClave();
           
           if(claveAInsertar.compareTo(claveActual)==0){
               //exepcion
           }
           
           if(claveAInsertar.compareTo(claveActual)>0){
               nodoAnterior=nodoActual;
               nodoActual=nodoActual.getHijoDerecho();
           }else{
                nodoAnterior=nodoActual;
                nodoActual=nodoActual.getHijoIzquierdo(); 
           }
           
       }
       
       NodoBinario<K,V> nodoAInsertar=new NodoBinario<>(claveAInsertar,valorAInsertar);
       K claveDelPadre= nodoAnterior.getClave();
          if(claveAInsertar.compareTo(claveDelPadre)>0){
               nodoAnterior.setHijoDerecho(nodoAInsertar);
               
           }else{
               nodoAnterior.setHijoIzquierdo(nodoAInsertar);
           }
       
    }

    @Override
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
         return nodoActual;
     }
     
     if(claveAEliminar.compareTo(claveActual)<0){
         NodoBinario<K,V> posibleNuevoHijoIzq=eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
         nodoActual.setHijoIzquierdo(posibleNuevoHijoIzq);
         return nodoActual;
     }
    
     // entonces ya lo ent¿contro y ahora tomar los casos
     //caso 1-> si es hoja
     if(nodoActual.esHoja()){
         return (NodoBinario<K,V>)NodoBinario.nodoVacio();
     }
     
     //caso 2 si tiene 1 hijo
     if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
         return nodoActual.getHijoIzquierdo();
     }
    
      if(nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
         return nodoActual.getHijoDerecho();
     }
      
     //caso 3 tiene ambos hijos
     NodoBinario<K,V> nodoReemplazo=this.buscarSucesor(nodoActual.getHijoDerecho());
     NodoBinario<K,V> posibleNuevoHijoDerecho=eliminar(nodoActual.getHijoDerecho(),nodoReemplazo.getClave());
     nodoActual.setClave(nodoReemplazo.getClave());
     nodoActual.setValor(nodoReemplazo.getValor());
     return nodoActual;

}

public NodoBinario<K,V> buscarSucesor(NodoBinario<K,V> nodoActual){
    NodoBinario<K,V> nodoActerior=nodoActual;
    while(!NodoBinario.esNodoVacio(nodoActual)){
        nodoActerior=nodoActual;
       nodoActual= nodoActual.getHijoIzquierdo();
    }
    return nodoActerior;
}
    @Override
    public V buscar(K claveABuscar) {
        
        NodoBinario<K,V> nodoActual=this.raiz;
   
       while(!NodoBinario.esNodoVacio(nodoActual)){
           K claveActual=nodoActual.getClave();
           
           if(claveABuscar.compareTo(claveActual)==0){
               return nodoActual.getValor();
           }
           
           if(claveABuscar.compareTo(claveActual)>0){
               nodoActual=nodoActual.getHijoDerecho();
           }else{
                nodoActual=nodoActual.getHijoIzquierdo(); 
           }
           
       }
       
   return null;
    }

    @Override
    public boolean contiene(K clave) {
      return this.buscar(clave)!=null;
    }

    public List<K> recorridoPorNIvel(){
        List<K> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
       
       while(!colaDeNodos.isEmpty()){
           NodoBinario<K,V> nodoActual=colaDeNodos.poll();
           recorrido.add(nodoActual.getClave());
           
           if(!nodoActual.esVacioHijoIzquierdo()){
               colaDeNodos.offer(nodoActual.getHijoIzquierdo());
           }
           
           if(!nodoActual.esVacioHijoDerecho()){
               colaDeNodos.offer(nodoActual.getHijoDerecho());
           }
           
           
       }
      return recorrido;
    }
    
    public List<K> recorridoPreOrden(){
          List<K> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
       pilaDeNodos.push(this.raiz);
       
       while(!pilaDeNodos.isEmpty()){
           NodoBinario<K,V> nodoActual=pilaDeNodos.pop();
           recorrido.add(nodoActual.getClave());
           
           if(!nodoActual.esVacioHijoDerecho()){
               pilaDeNodos.push(nodoActual.getHijoDerecho());
           }
           
           if(!nodoActual.esVacioHijoIzquierdo()){
               pilaDeNodos.push(nodoActual.getHijoIzquierdo());
           }
           
           
       }
      return recorrido;

    }
    
    public List<V> recorridoPreOrdenValor(){
          List<V> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
       pilaDeNodos.push(this.raiz);
       
       while(!pilaDeNodos.isEmpty()){
           NodoBinario<K,V> nodoActual=pilaDeNodos.pop();
           recorrido.add(nodoActual.getValor());
           
           if(!nodoActual.esVacioHijoDerecho()){
               pilaDeNodos.push(nodoActual.getHijoDerecho());
           }
           
           if(!nodoActual.esVacioHijoIzquierdo()){
               pilaDeNodos.push(nodoActual.getHijoIzquierdo());
           }
           
           
       }
      return recorrido;

    }
    /*REOCRRIDO POSTORDEN*/
    public List<K> recorridoEnPostOrden(){
         List<K> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
       NodoBinario<K,V> nodoActual=this.raiz;
      meterPilaParaPostOrden(nodoActual,pilaDeNodos);
           //para sacar de la pila
           while (!pilaDeNodos.isEmpty()) {
             nodoActual=pilaDeNodos.pop();
              recorrido.add(nodoActual.getClave());
              
              if(!pilaDeNodos.isEmpty()){
                   NodoBinario<K,V> nodoTope=pilaDeNodos.peek();
                   if(!nodoTope.esVacioHijoDerecho() && nodoTope.getHijoDerecho() != nodoActual){
                       meterPilaParaPostOrden(nodoTope.getHijoDerecho(),pilaDeNodos);
                   }
              }
           }
         return recorrido; 
       }
   
   private void meterPilaParaPostOrden(NodoBinario<K,V> nodoActual,Stack pilaDeNodos){
        while(!NodoBinario.esNodoVacio(nodoActual)){
             pilaDeNodos.push(nodoActual);
          if(!nodoActual.esVacioHijoIzquierdo()){
              nodoActual=nodoActual.getHijoIzquierdo();
          }else{
               nodoActual=nodoActual.getHijoDerecho();
          }
          
       }
   }
   
    public List<V> recorridoEnPostOrdenValor(){
         List<V> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
       NodoBinario<K,V> nodoActual=this.raiz;
      meterPilaParaPostOrden(nodoActual,pilaDeNodos);
           //para sacar de la pila
           while (!pilaDeNodos.isEmpty()) {
             nodoActual=pilaDeNodos.pop();
              recorrido.add(nodoActual.getValor());
              
              if(!pilaDeNodos.isEmpty()){
                   NodoBinario<K,V> nodoTope=pilaDeNodos.peek();
                   if(!nodoTope.esVacioHijoDerecho() && nodoTope.getHijoDerecho() != nodoActual){
                       meterPilaParaPostOrden(nodoTope.getHijoDerecho(),pilaDeNodos);
                   }
              }
           }
         return recorrido; 
       }
   
  //RECORRIDO INORDEN ITERATIVO 
   public List<K> recorridoInOrden(){
    List<K> recorrido= new ArrayList<>();
     if(esArbolVacio()){
         return recorrido;
     }
     
    Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
    NodoBinario<K,V> nodoActual=this.raiz;
    meterPilaInOrden(nodoActual,pilaDeNodos);
    
    while(!pilaDeNodos.isEmpty()){
        nodoActual=pilaDeNodos.pop();
        recorrido.add(nodoActual.getClave());
        
        if(!nodoActual.esVacioHijoDerecho()){
            nodoActual=nodoActual.getHijoDerecho();
            meterPilaInOrden(nodoActual, pilaDeNodos);
        }
                
    }
    return recorrido;
    
   }
   
   public List<V> recorridoInOrdenValor(){
    List<V> recorrido= new ArrayList<>();
     if(esArbolVacio()){
         return recorrido;
     }
     
    Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
    NodoBinario<K,V> nodoActual=this.raiz;
    meterPilaInOrden(nodoActual,pilaDeNodos);
    
    while(!pilaDeNodos.isEmpty()){
        nodoActual=pilaDeNodos.pop();
        recorrido.add(nodoActual.getValor());
        
        if(!nodoActual.esVacioHijoDerecho()){
            nodoActual=nodoActual.getHijoDerecho();
            meterPilaInOrden(nodoActual, pilaDeNodos);
        }
                
    }
    return recorrido;
    
   }

   private void meterPilaInOrden(NodoBinario<K,V> nodoActual,Stack pilaDeNodos){
        while(!NodoBinario.esNodoVacio(nodoActual)){
         pilaDeNodos.push(nodoActual);
         nodoActual=nodoActual.getHijoIzquierdo();
       }
   }
        
   public List<K> recorridoPreOrdenRecursivo(){
    List<K> recorrido= new ArrayList<>();
    NodoBinario<K,V> nodoActual=this.raiz;
    recoridoPreOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recoridoPreOrdenRec(NodoBinario<K,V> nodoActual,List<K> recorrido){
    if(NodoBinario.esNodoVacio(nodoActual)){
        return;
    }
    
    
    recorrido.add(nodoActual.getClave());
    recoridoPreOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
    recoridoPreOrdenRec(nodoActual.getHijoDerecho(), recorrido);
}
   
 /*RECORRIDO INORDEN RECURSIVO*/
public List<K> recorridoInOrdenRecursivo(){
    List<K> recorrido= new ArrayList<>();
    NodoBinario<K,V> nodoActual=this.raiz;
    recoridoInOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recoridoInOrdenRec(NodoBinario<K,V> nodoActual,List<K> recorrido){
    if(NodoBinario.esNodoVacio(nodoActual)){
        return;
    }
    
    recoridoInOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
    recorrido.add(nodoActual.getClave());
    recoridoInOrdenRec(nodoActual.getHijoDerecho(), recorrido);
}

public List<K> recorridoPostOrdenRecursivo(){
    List<K> recorrido= new ArrayList<>();
    NodoBinario<K,V> nodoActual=this.raiz;
    recoridoPostOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recoridoPostOrdenRec(NodoBinario<K,V> nodoActual,List<K> recorrido){
    if(NodoBinario.esNodoVacio(nodoActual)){
        return;
    }
    
    recoridoPostOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
    recoridoPostOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    recorrido.add(nodoActual.getClave());
    
}
    /*Cantdad NODOS*/
    @Override
    public int size() {
          
        if(esArbolVacio()){
            return 0;
        }
        
        int cantidad=0;
       Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
       pilaDeNodos.push(this.raiz);
       
       while(!pilaDeNodos.isEmpty()){
           NodoBinario<K,V> nodoActual=pilaDeNodos.pop();
           cantidad++;
           
           if(!nodoActual.esVacioHijoDerecho()){
               pilaDeNodos.push(nodoActual.getHijoDerecho());
           }
           
           if(!nodoActual.esVacioHijoIzquierdo()){
               pilaDeNodos.push(nodoActual.getHijoIzquierdo());
           }
           
           
       }
      return cantidad;
    }
    
    public K minimo(){
         if(esArbolVacio()){
            return null;
        }
        
          NodoBinario<K,V> nodoAnterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        NodoBinario<K,V> nodoActual=this.raiz;
       
       while(!NodoBinario.esNodoVacio(nodoActual)){
           nodoAnterior=nodoActual;
          nodoActual=nodoActual.getHijoIzquierdo();      
       }
       return nodoAnterior.getClave();
    }
    
    
    
    public K maximo(){
          if(esArbolVacio()){
            return null;
        }
        
          NodoBinario<K,V> nodoAnterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        NodoBinario<K,V> nodoActual=this.raiz;
       
       while(!NodoBinario.esNodoVacio(nodoActual)){
           nodoAnterior=nodoActual;
          nodoActual=nodoActual.getHijoDerecho();      
       }
       return nodoAnterior.getClave();  
    }

    @Override
    public int altura() {
      // nivel+1;
     return altura(this.raiz);
    }
    

   public int altura(NodoBinario<K,V> nodoActual){
       if(NodoBinario.esNodoVacio(nodoActual)){
           return 0;
       }
       
       int alturaPorIzquierda=altura(nodoActual.getHijoIzquierdo());
       int alturaPorDerecha=altura(nodoActual.getHijoDerecho());
       
       if(alturaPorIzquierda > alturaPorDerecha){
           return alturaPorIzquierda+1;
       }else{
           return alturaPorDerecha+1;
       }
   }

   public int alturaIterativa(){
        if(esArbolVacio()){
            return 0;
        }
        
       Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
        int alturaArbol=0;
      
       
       while(!colaDeNodos.isEmpty()){
          
           int cantidadNodos=colaDeNodos.size();
           int i=0;
           while(i<cantidadNodos){ 
              NodoBinario<K,V> nodoActual=colaDeNodos.poll();
              if(!nodoActual.esVacioHijoIzquierdo()){
               colaDeNodos.offer(nodoActual.getHijoIzquierdo());
              }
           
             if(!nodoActual.esVacioHijoDerecho()){
               colaDeNodos.offer(nodoActual.getHijoDerecho());
             }
             i++;
           }
           
          alturaArbol++;
       }
     return alturaArbol;
   }
   
   public int cantidadHijosDerechos(){
       return cantidadHijosDerechosRec(this.raiz);
   }
   
   public int cantidadHijosDerechosRec(NodoBinario<K,V> nodoActual){
       if(NodoBinario.esNodoVacio(nodoActual)){
           return 0;
       }
       
       int hdPorDerecha=cantidadHijosDerechosRec(nodoActual.getHijoDerecho());
       int hDPorIzquierda=cantidadHijosDerechosRec(nodoActual.getHijoIzquierdo());
       
       if(!nodoActual.esVacioHijoDerecho()){
           return hdPorDerecha+hDPorIzquierda+1;
       }
        
       return hdPorDerecha+hDPorIzquierda;       
   }
   
    @Override
    public int nivel() {
      return  altura()+1;
    }

    
    /**
     * implementar el metodo que retorne si tiene sus dos hijos completos desde un nivel
     *  
     *
     * @return 
     */
    
   public boolean tieneCompletosEnNivel(int nivel){
       return tieneNodosCompletosEnNivel(this.raiz,nivel,0);
   }
   
   public boolean tieneNodosCompletosEnNivel(NodoBinario<K,V> nodoActual,int nivelAbuscar,int nivel){
       if(NodoBinario.esNodoVacio(nodoActual)){
         return false;   
       }
       
       if(nivel==nivelAbuscar){
        return !nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo();
       }
       
       boolean completoPorIzq=tieneNodosCompletosEnNivel(nodoActual.getHijoIzquierdo(), nivelAbuscar, nivel + 1);
       boolean completoPorDer=tieneNodosCompletosEnNivel(nodoActual.getHijoDerecho(), nivelAbuscar, nivel + 1);
        return completoPorIzq && completoPorDer;
   }
   
  /*hacer iterativamente tieneNodosCompletosEnNivel*/
  
  @Override 
  public String mostrarArbol(){
       return this.generarCadenaDeArbol(this.raiz, "", true);
    }
    
    private String generarCadenaDeArbol(NodoBinario<K,V> nodoActual,
        String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "|---(R)" : "|---(R)"); //arbol vacio o no
        } else {
            cadena.append(ponerCodo ? "|---(D)" : "|---(I)");  //derecha o izquierda
        }
        if (NodoBinario.esNodoVacio(nodoActual)) {
            cadena.append("-|\n");
            return cadena.toString();
        }
        cadena.append(nodoActual.getClave().toString());
        cadena.append("\n");

        NodoBinario<K,V> nodoIzq = nodoActual.getHijoIzquierdo();
        String prefijoAux = prefijo + (ponerCodo ? "   ":"|  ");
        cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));

        NodoBinario<K,V> nodoDer = nodoActual.getHijoDerecho();
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));
      return cadena.toString();
     
 }
    
    
    ////////////////////////PRACTICO ARBOL BINARIO///////////////////////////////////////////////////////////////////
    /**
     * 1. Implementar los métodos que no se implementaron en clases o que se implementaron a medias de árboles binarios de búsqueda y AVL 
    
 
     */
    
    /* * 2. Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario*/
    public int cantidadNodosSoloHijoIzq(){
        return cantidadNodosSoloHijoIzqRec(this.raiz);
    }
    
   public int cantidadNodosSoloHijoIzqRec(NodoBinario<K,V> nodoActual){
       if(NodoBinario.esNodoVacio(nodoActual)){
           return 0;
       }
       
     int cantIzquierda=cantidadNodosSoloHijoIzqRec(nodoActual.getHijoIzquierdo());
     int cantDerecha=cantidadNodosSoloHijoIzqRec(nodoActual.getHijoDerecho());
     
     if(!nodoActual.esVacioHijoIzquierdo()){
         return cantIzquierda+cantDerecha+1;
     }
         
     return cantIzquierda+cantDerecha;
     
   }
   
   /* 3. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario*/
    public int cantidadNodosSoloHijoIzquierdo(){
        if(esArbolVacio()){
            return 0;
        }
       
     Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
     NodoBinario<K,V> nodoActual=this.raiz;
     colaDeNodos.offer(nodoActual);
     int cantidadHijosIzquierdos=0;
     
     while(!colaDeNodos.isEmpty()){
         nodoActual=colaDeNodos.poll();
         
         if(!nodoActual.esVacioHijoIzquierdo()){
             colaDeNodos.offer(nodoActual.getHijoIzquierdo());
             cantidadHijosIzquierdos++;
         }
         
         if(!nodoActual.esVacioHijoDerecho()){
             colaDeNodos.offer(nodoActual.getHijoDerecho());
         }
     }
    return cantidadHijosIzquierdos;
    } 
    
    /*4. Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario, 
          pero solo en el nivel N */
    
   
    public int cantidadHijoIzqNivel(int nivelABuscar){
        return cantidadHijoIzqNivelRec(this.raiz,nivelABuscar,0);
            
    }
    
    public int cantidadHijoIzqNivelRec(NodoBinario<K,V> nodoActual,int nivelABuscar,int nivelActual){
       
        if(!NodoBinario.esNodoVacio(nodoActual)){
            if(nivelABuscar==nivelActual && !nodoActual.esVacioHijoIzquierdo())
                return cantidadHijoIzqNivelRec(nodoActual.getHijoIzquierdo(), nivelABuscar,nivelActual+1)+
                        cantidadHijoIzqNivelRec(nodoActual.getHijoDerecho(),nivelABuscar,nivelActual+1)+1; 
           return  cantidadHijoIzqNivelRec(nodoActual.getHijoIzquierdo(), nivelABuscar,nivelActual+1)+ 
                   cantidadHijoIzqNivelRec(nodoActual.getHijoDerecho(),nivelABuscar,nivelActual+1);
        }  
        return 0;
    }       
    
/*5. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario, 
     pero solo después del nivel N */
    
  public int cantidadHijoIzqNivelIterativo(int nivel){
      if(esArbolVacio()){
          return 0;
      }
     Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
      NodoBinario<K,V> nodoActual=this.raiz;
     colaDeNodos.offer(nodoActual);
     int cantidadHijosIzquierdos=0;
     
     while(!colaDeNodos.isEmpty()){   
           int cantidadNodos=colaDeNodos.size();
           int i=0;
           while(i<cantidadNodos){ 
             nodoActual=colaDeNodos.poll();
             if(nivel<0){
              if(!nodoActual.esVacioHijoIzquierdo()){
               cantidadHijosIzquierdos++;
             }
            }
     
             if(!nodoActual.esVacioHijoIzquierdo()){
               colaDeNodos.offer(nodoActual.getHijoIzquierdo());
              }
           
             if(!nodoActual.esVacioHijoDerecho()){
               colaDeNodos.offer(nodoActual.getHijoDerecho());
             }
             i++;
           }
      nivel--;
       }
     return cantidadHijosIzquierdos;
  }
  
  /*6. Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo izquierdo no vacío en un árbol binario,
     pero solo antes del nivel N */
  public int cantidadHijoIzqAntesNivelIterativo(int nivel){
      if(esArbolVacio()){
          return 0;
      }
     Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
      NodoBinario<K,V> nodoActual=this.raiz;
     colaDeNodos.offer(nodoActual);
     int cantidadHijosIzquierdos=0;
     
     while(!colaDeNodos.isEmpty()){
           int cantidadNodos=colaDeNodos.size();
           int i=0;
           while(i<cantidadNodos){ 
             nodoActual=colaDeNodos.poll();
             
           if(nivel>0){
           if(!nodoActual.esVacioHijoIzquierdo()){
               cantidadHijosIzquierdos++;
           }
        }
             
              if(!nodoActual.esVacioHijoIzquierdo()){
               colaDeNodos.offer(nodoActual.getHijoIzquierdo());
              }
           
             if(!nodoActual.esVacioHijoDerecho()){
               colaDeNodos.offer(nodoActual.getHijoDerecho());
             }
             i++;
           }
         nivel--;
       }
     return cantidadHijosIzquierdos;
  }
  /*7. Implemente un método recursivo que reciba como parámetro otro árbol binario de búsqueda que retorne verdadero, 
     si el árbol binario es similar al árbol binario recibido como parámetro, falso en caso contrario. */
      
  public boolean arbolSimilar(ArbolBusquedaBinaria nuevoArbol){
    /*  if(altura() != nuevoArbol.altura()){
        return false;
      }*/
      return arbolSimilar(this.raiz,nuevoArbol.raiz);
  }
  
  public boolean arbolSimilar(NodoBinario<K,V> nodoActual,NodoBinario<K,V> nodoOtroArbol){  
    if(NodoBinario.esNodoVacio(nodoActual) && !NodoBinario.esNodoVacio(nodoOtroArbol)){
          return false;
      }
      if(true){    
    return (!nodoActual.esVacioHijoIzquierdo() && !nodoOtroArbol.esVacioHijoIzquierdo());
      }  
     boolean hijoIzquierdo=arbolSimilar(nodoActual.getHijoIzquierdo(),nodoOtroArbol.getHijoIzquierdo());
     boolean hijoDerecho=arbolSimilar(nodoActual.getHijoDerecho(),nodoOtroArbol.getHijoDerecho());
      return hijoIzquierdo && hijoDerecho;
  }
    
  /*9. Para un árbol binario implemente un método que retorne la cantidad de nodos que tienen ambos hijos desde el nivel N.*/
    public int cantidadNodosAmbosHijos(int nivel){
       return cantidadNodosAmbosHijosRec(this.raiz,nivel,0);
   }
   
   public int cantidadNodosAmbosHijosRec(NodoBinario<K,V> nodoActual,int nivelAbuscar,int nivel){
       if(NodoBinario.esNodoVacio(nodoActual)){
         return 0;   
       }
       int cantIzq=cantidadNodosAmbosHijosRec(nodoActual.getHijoIzquierdo(), nivelAbuscar, nivel + 1);
       int cantDer=cantidadNodosAmbosHijosRec(nodoActual.getHijoDerecho(), nivelAbuscar, nivel + 1);
       
       if(nivel>nivelAbuscar){
         if( !nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()){
           return cantDer+cantIzq+1;
          }
       }
     return cantDer+cantIzq;
   }

   /*11. Implementar un método que retorne verdadero si un árbol binario esta lleno.*/
    public boolean arbolEstaLleno(){
        if(esArbolVacio()){
            return false;
        }
      Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
      NodoBinario<K,V> nodoActual=this.raiz;
     colaDeNodos.offer(nodoActual);
     int altura=altura();
     boolean esCompleto=true;
     
     while(!colaDeNodos.isEmpty() && esCompleto){
           int cantidadNodos=colaDeNodos.size();
           int i=0;
           while(i<cantidadNodos){ 
             nodoActual=colaDeNodos.poll();
             
        if(altura==1){
           if(!nodoActual.esHoja()){
             esCompleto=false;
           }
        }else if(nodoActual.esVacioHijoIzquierdo() || nodoActual.esVacioHijoDerecho()){
               esCompleto=false;
           }
             
              if(!nodoActual.esVacioHijoIzquierdo()){
               colaDeNodos.offer(nodoActual.getHijoIzquierdo());
              }
           
             if(!nodoActual.esVacioHijoDerecho()){
               colaDeNodos.offer(nodoActual.getHijoDerecho());
             }
             i++;
           }
         altura--;
       }
     return esCompleto;
    }
  /*10. Implementar un método que retorne un nuevo árbol binario de búsqueda invertido. */
    public NodoBinario<K,V> arbolInvertido(){    
        return arbolInvertidoRec(this.raiz);
    }
    
     public NodoBinario<K,V> arbolInvertidoRec(NodoBinario<K,V> nodoActual){
         if(NodoBinario.esNodoVacio(nodoActual)){
             return null;
         }
         
        NodoBinario<K,V> nodoIzquierdo=arbolInvertidoRec(nodoActual.getHijoIzquierdo());
        NodoBinario<K,V> nodoDerecho=arbolInvertidoRec(nodoActual.getHijoDerecho());
         
         nodoActual.setHijoDerecho(nodoIzquierdo);
         nodoActual.setHijoIzquierdo(nodoDerecho);
         
        return nodoActual;
     }
} 