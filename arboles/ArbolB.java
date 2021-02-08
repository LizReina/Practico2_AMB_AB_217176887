/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import arboles.Exception.ExceptionOrdenInvalida;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author brandon
 * @param <K>
 * @param <V>
 */
public class ArbolB<K extends Comparable<K>,V> extends ArbolMVias<K,V>{
    private int nroMaximoDeDatos;
    private int nroMinimoDeDatos;
    private int nroMinimoDeHijos;

    public ArbolB() {
        super();
        nroMaximoDeDatos=2;
        nroMinimoDeDatos=1;
        nroMinimoDeHijos=2;
                
    }
    
      public ArbolB(int orden) throws ExceptionOrdenInvalida {
        super(orden);
        nroMaximoDeDatos=super.orden-1;
        nroMinimoDeDatos=this.nroMaximoDeDatos/2;
        nroMinimoDeHijos=this.nroMinimoDeDatos+1;
 
    }
      
    @Override
    public void insertar(K clave, V valor) {
        if(esArbolVacio()){ // si es vació solo lo pone co,o la raiz
          this.raiz=new NodoMvias<>(orden+1,clave,valor);
          return;
        }
        
       Stack<NodoMvias<K,V>> pilaAncestros=new Stack<>();
      NodoMvias<K,V> nodoActual=this.raiz;
      while(!NodoMvias.esNodoVacio(nodoActual)){
       int posicionClaveExistente=super.existeClaveEnNodo(nodoActual, clave);
      if(posicionClaveExistente!=POSICION_INVALIDA){
             nodoActual.setValor(posicionClaveExistente,valor);
             nodoActual.setClave(posicionClaveExistente,clave);
          }else{
       //siempre habra campo
      if(nodoActual.esHoja()){
          insertarClaveOrdenadaEnOrden(nodoActual,clave, valor);
          if(nodoActual.cantidadDeClavesNoVacias()>this.nroMaximoDeDatos){
              this.dividir(nodoActual,pilaAncestros);
          }
          nodoActual=NodoMvias.nodoVacio();
      }
      else{
         //bajamos guardando los ancestros en la pila
       int posicionDeDondeBajar=super.porDondeBajar(nodoActual,clave);  
       pilaAncestros.push(nodoActual);
       nodoActual=nodoActual.getHijo(posicionDeDondeBajar);
       }
      }
      }//fin While
}
    private void dividir(NodoMvias<K,V> nodoActual, Stack<NodoMvias<K,V>> pilaDePadres) {
        while (true) {
            int posicionQSube = this.nroMaximoDeDatos / 2;
            // guardamos la clave y el valor
            K claveQSube = nodoActual.getClave(posicionQSube);
            V valorQSube = nodoActual.getValor(posicionQSube);
            // os creamos 2 nuevos nodos 
            NodoMvias<K,V> nodoIzq = new NodoMvias<>(orden + 1);
            NodoMvias<K,V> nodoDer = new NodoMvias<>(orden + 1);
            // si la posicion donde estamos no es == a posicion que sube
            for (int i = 0; i < posicionQSube; i++) {
                // insertamos en el nodoIzq que nos creamos
                insertarOrdenB(nodoIzq, nodoActual.getClave(i), nodoActual.getValor(i));
                nodoIzq.setHijos(i, nodoActual.getHijo(i));
                if (i == posicionQSube - 1) {
                    nodoIzq.setHijos(i + 1, nodoActual.getHijo(i + 1));
                }
            }
            
    // si la posicion donde estamos es >a posicion que sube
          
            for (int i = posicionQSube + 1; i < this.orden; i++) {
                // insertamos en el nodoDer que nos creamos co los hijos del nodo ACTUAL

                insertarOrdenB(nodoDer, nodoActual.getClave(i), nodoActual.getValor(i));
                nodoDer.setHijos(i - (posicionQSube + 1), nodoActual.getHijo(i));
                if (i == this.orden - 1) {
                    nodoDer.setHijos(i - posicionQSube, nodoActual.getHijo(i + 1));
                }
            }
            
         //verificamos si el nodo en el que estamos es el raiz   
            if (pilaDePadres.isEmpty()) {//es la raiz por que ya no hay nada que sacar
            //vaciamos el nodo Actual para que el sea la nueva raiz y ponemos su clave y valor que ya divimos
                vaciarNodo1(nodoActual);
                insertarOrdenB(nodoActual, claveQSube,valorQSube);
               // insertanos sus hijos
                nodoActual.setHijos(0, nodoIzq);
                nodoActual.setHijos(1, nodoDer);
                break;
            } else {//el nodo no es la raiz
            //sacamos el nodo padre del nodo Actual
                NodoMvias<K,V> nodoPadre = pilaDePadres.pop();
             //buscamos la posicion por la que colgaba el nodoActual 
                insertarOrdenB(nodoPadre, claveQSube,valorQSube);
              //insertamos como su hijo
                nodoPadre.setHijos(porDondeBajar(nodoPadre, claveQSube), nodoIzq);
                nodoPadre.setHijos(porDondeBajar(nodoPadre, claveQSube) + 1, nodoDer);
                if (!nodoPadre.estanClavesLlenas()) {
                    break;
                } else {
                    nodoActual = nodoPadre;
                }
            }
        }
    }
    
     public void vaciarNodo1(NodoMvias<K,V> nodo) {
        for (int i = 0; i < orden; i++) {
            nodo.setClave(i, null);
            nodo.setValor(i, null);
            nodo.setHijos(i, null);
        }
            nodo.setHijos(orden, null);
        
    }
     
      public void insertarOrdenB(NodoMvias<K,V> nodoActual,K clave,V valor){  
     int  posicionAPoner=porDondeBajar(nodoActual, clave);
       for (int i = nodoActual.cantidadDeClavesNoVacias()-1; i >= posicionAPoner ; i--) {
          K claveEnTurno=nodoActual.getClave(i);
           if(claveEnTurno.compareTo(clave)>0){
               if(hayHijoMasAdelante(nodoActual, i) && nodoActual.esClaveVacia(i+1)){
                   nodoActual.setClave(i+1, nodoActual.getClave(i));
                   nodoActual.setValor(i+1, nodoActual.getValor(i));
                   nodoActual.setHijos(i+2, nodoActual.getHijo(i+1));
                   nodoActual.setHijos(i+1, nodoActual.getHijo(i));
                  
               }else{
               nodoActual.setClave(i+1, nodoActual.getClave(i));
               nodoActual.setValor(i+1, nodoActual.getValor(i));
               nodoActual.setHijos(i+1, nodoActual.getHijo(i));
                 }
           }
       }
               nodoActual.setClave(posicionAPoner,clave);
               nodoActual.setValor(posicionAPoner,valor);
               nodoActual.setHijos(posicionAPoner,NodoMvias.nodoVacio());
       
   }
    
 public V Eliminar(K claveABuscar){
   if(claveABuscar== null ){
           throw new IllegalArgumentException("no acepta null");
                   
        }
        
    
      Stack<NodoMvias<K,V>> pilaDeAncestros=new Stack<>();
      
      NodoMvias<K,V> nodoActual=this.buscarNodoDeLaClave(claveABuscar,pilaDeAncestros);
      if(!NodoMvias.esNodoVacio(nodoActual)){
          throw new IllegalArgumentException("la clave no existe");
      }
     
      int posicionDeLaClaveEnElNodo=super.porDondeBajar(nodoActual, claveABuscar);
     V valorARetornar=nodoActual.getValor(posicionDeLaClaveEnElNodo);
     
     if(nodoActual.esHoja()){
         super.eliminarclaveDeNodo(nodoActual, posicionDeLaClaveEnElNodo);
         if(nodoActual.cantidadDeClavesNoVacias()<this.nroMinimoDeDatos){
             if(pilaDeAncestros.isEmpty()){
                 if(nodoActual.cantidadDeClavesNoVacias()==0){
                     super.vaciar();
                 }
             }else{
                 this.prestarseOFusionar(nodoActual,pilaDeAncestros);
             }
                 
         }
     }else{
         pilaDeAncestros.push(nodoActual);
         NodoMvias<K,V> nodoDelPredecesor=this.buscarClavePredecesorInOrden(nodoActual.getHijo(posicionDeLaClaveEnElNodo),pilaDeAncestros);
         int posicionDelPredecesor=nodoDelPredecesor.cantidadDeClavesNoVacias()-1;
         K clavePredecesor=nodoDelPredecesor.getClave(posicionDelPredecesor);
         V valorPredecesor=nodoDelPredecesor.getValor(posicionDelPredecesor);
         super.eliminarclaveDeNodo(nodoDelPredecesor, posicionDelPredecesor);
         nodoActual.setClave(posicionDeLaClaveEnElNodo, clavePredecesor);
         nodoActual.setValor(posicionDeLaClaveEnElNodo, valorPredecesor);
         if(nodoDelPredecesor.cantidadDeClavesNoVacias()<this.nroMaximoDeDatos){
              this.prestarseOFusionar(nodoDelPredecesor,pilaDeAncestros); 
         }
         
     }
     return valorARetornar;
    }
    
  private NodoMvias<K,V> buscarNodoDeLaClave(K claveAEliminar,Stack<NodoMvias<K,V>> pilaAncestros){
    NodoMvias<K,V> nodoActual=this.raiz;
    while(!NodoMvias.esNodoVacio(nodoActual)){
        int tamanoDeNodoActual=nodoActual.cantidadDeClavesNoVacias();
        NodoMvias<K,V> anterior=nodoActual;
        for (int i = 0; i < tamanoDeNodoActual && anterior==nodoActual; i++) {
           K claveactual=nodoActual.getClave(i);
           if(claveAEliminar.compareTo(claveactual)==0){
             return nodoActual;
                     
           }
           
           if(claveAEliminar.compareTo(claveactual)<0){
               if(nodoActual.esHoja()){
                    pilaAncestros.push(nodoActual);
                 nodoActual=nodoActual.getHijo(i);
               }else{
                   return NodoMvias.nodoVacio();
               }
           }
        }//fin for
         if (anterior==nodoActual){
               if(nodoActual.esHoja()){
                    pilaAncestros.push(nodoActual);
                 nodoActual=nodoActual.getHijo(tamanoDeNodoActual);
               }else{
                   return NodoMvias.nodoVacio();
               }
         }
        
    }//fin while
    
   return NodoMvias.nodoVacio();
    }
    
   public void eliminarclaveDeNodo(NodoMvias<K,V> nodoActual,int posicion){
       nodoActual.setClave(posicion,null);
         nodoActual.setValor(posicion,null);
         
        for (int j = posicion; j < orden - 2; j++) {
            if (nodoActual.esClaveVacia(j + 1)) {
                 return;
            }
            nodoActual.setClave(j,nodoActual.getClave(j+1));
            nodoActual.setValor(j,nodoActual.getValor(j+1));
            
            
            nodoActual.setClave(j+1,null);
            nodoActual.setValor(j+1,null);
        }
   }
   
      private void eliminarPosicioCconElHijo(NodoMvias<K,V> nodoActual, int posicionAEliminar) {
        for (int i = posicionAEliminar; i < this.orden - 1; i++) {
            nodoActual.setClave(i, nodoActual.getClave(i + 1));
             nodoActual.setValor(i, nodoActual.getValor(i + 1));
            nodoActual.setHijos(i, nodoActual.getHijo(i + 1));
        }
        nodoActual.setHijos(this.orden - 1, NodoMvias.nodoVacio());
    }
     
    private void insertarPrimerDatoEHijo(NodoMvias<K,V> nodoActual, NodoMvias<K,V> hijoDelHermanoIzquierdo, K claveDelPadre,V valorDelPadre) {
        int posicionDelUltimoDatoDelNodoActual = nodoActual.cantidadDeClavesNoVacias()- 1;
        for (int i = posicionDelUltimoDatoDelNodoActual; i >= 0 ; i--) {
            nodoActual.setClave(i + 1, nodoActual.getClave(i));
            nodoActual.setValor(i + 1, nodoActual.getValor(i));
            nodoActual.setHijos(i + 2, nodoActual.getHijo(i + 1));
        }
        nodoActual.setHijos(1, nodoActual.getHijo(0));
        nodoActual.setClave(0,claveDelPadre);
        nodoActual.setValor(0,valorDelPadre);
        nodoActual.setHijos(0, hijoDelHermanoIzquierdo);
    }
      private int posicionDelNodoActualEnPadre(NodoMvias<K,V> nodoPadreDelNodoActual, NodoMvias<K,V> nodoActual) {
        for (int i = 0; i < this.orden - 1; i++) { //si o si sera uno de los hijos
            if (nodoPadreDelNodoActual.getHijo(i) == nodoActual) {
                return i;
            }
        }
        return this.orden - 1;
    }
      
    
      public void prestarseOFusionar(NodoMvias<K,V> nodoActual,Stack<NodoMvias<K,V>> pilaDeAncestros){
      NodoMvias<K,V> nodoPadreDelNodoActual = pilaDeAncestros.pop();
        int posicionDelNodoActualEnelPadre = this.posicionDelNodoActualEnPadre(nodoPadreDelNodoActual,nodoActual);
        //primero vere si me puedo prestar
        boolean mePreste = false;
        //tengo hermano a mi Derecha?
        if (!nodoPadreDelNodoActual.esHijoVacio(posicionDelNodoActualEnelPadre + 1)) { //si existe hermano derecho
            NodoMvias<K,V> hermanoDerecho = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre + 1); //lo sacamos
            //puedo prestarme del siguiente?
            if (hermanoDerecho.cantidadDeClavesNoVacias()> this.nroMinimoDeDatos) { //me puede prestar?
                K claveDelHermanoDerecho = hermanoDerecho.getClave(0);
                V valorDelHermanoDerecho = hermanoDerecho.getValor(0);
                
                NodoMvias<K,V> hijoDelHermanoDerecho = hermanoDerecho.getHijo(0);
                eliminarPosicioCconElHijo(hermanoDerecho, 0); //elimina el primer clave y valor e hijo del hermanoDerecho
                K claveDelPadre = nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre);
                V valorDelPadre = nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre);
                
                int posicionAInsertarDatoEnNodoActual = nodoActual.cantidadDeClavesNoVacias();
                nodoPadreDelNodoActual.setClave(posicionDelNodoActualEnelPadre, claveDelHermanoDerecho);
                nodoPadreDelNodoActual.setValor(posicionDelNodoActualEnelPadre, valorDelHermanoDerecho);
                nodoActual.setClave(posicionAInsertarDatoEnNodoActual, claveDelPadre);
                nodoActual.setValor(posicionAInsertarDatoEnNodoActual, valorDelPadre);
                
                nodoActual.setHijos(posicionAInsertarDatoEnNodoActual + 1, hijoDelHermanoDerecho);
                mePreste = true;
            }
        }
        if (!mePreste && (posicionDelNodoActualEnelPadre != 0)) { //tendre hermano a la izquierda
        NodoMvias<K,V> hermanoIzquierdo = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre - 1);
            //puedo prestarme del anterior?
            if (hermanoIzquierdo.cantidadDeClavesNoVacias()> this.nroMinimoDeDatos) {
                int posicionDelUltimoDatoDelHermanoIzquierdo = hermanoIzquierdo.cantidadDeClavesNoVacias()- 1;
                K claveDelHermanoIzquierdo = hermanoIzquierdo.getClave(posicionDelUltimoDatoDelHermanoIzquierdo);
                V valorDelHermanoIzquierdo = hermanoIzquierdo.getValor(posicionDelUltimoDatoDelHermanoIzquierdo);
                
                NodoMvias<K,V> hijoDelHermanoIzquierdo = hermanoIzquierdo.getHijo(posicionDelUltimoDatoDelHermanoIzquierdo + 1);
                eliminarPosicioCconElHijo(hermanoIzquierdo, posicionDelUltimoDatoDelHermanoIzquierdo + 1); //elimina solo el hijo
                hermanoIzquierdo.setClave(posicionDelUltimoDatoDelHermanoIzquierdo, (K)NodoMvias.datoVacio()); //ahora elimino la clave
                hermanoIzquierdo.setValor(posicionDelUltimoDatoDelHermanoIzquierdo, (V)NodoMvias.datoVacio()); //ahora elimino el valor
                K claveDelPadre = nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre - 1); //sacamos el clave del padre
                V valorDelPadre = nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre - 1); //sacamos el clave del padre
                
                nodoPadreDelNodoActual.setClave(posicionDelNodoActualEnelPadre - 1, claveDelHermanoIzquierdo); //le ponemos la clave del hermano
                nodoPadreDelNodoActual.setValor(posicionDelNodoActualEnelPadre - 1, valorDelHermanoIzquierdo); //le ponemos la clave del hermano
                
                this.insertarPrimerDatoEHijo(nodoActual, hijoDelHermanoIzquierdo , claveDelPadre,valorDelPadre); //insertamos lo prestado
                mePreste = true;
            }
        }
        //si no me preste me tengo que fusionar
        if (!mePreste) {
            //puedo fusionarme con el hermano Derecho?
            if (!nodoPadreDelNodoActual.esHijoVacio(posicionDelNodoActualEnelPadre + 1)) {
                NodoMvias<K,V> hermanoDerecho = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre + 1);
                NodoMvias<K,V> nuevoHijo = new NodoMvias<>(this.orden + 1);
                //cargo el nuevo hijo
                //con los datos del nodoActual
                int posicionActualDelNuevoHijo = 0;
                for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoActual.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoActual.getValor(i));
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre));
                nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre));
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(posicionActualDelNuevoHijo));
                eliminarPosicioCconElHijo(nodoPadreDelNodoActual, posicionDelNodoActualEnelPadre);
                posicionActualDelNuevoHijo++; //hacemos espacio para los datos e hijos del hermnano Derecho
                //ahora cargamos el hermanoDerecho
                int cantidadDeDatosDelHermanoDerecho = hermanoDerecho.cantidadDeClavesNoVacias();
                for (int i = 0; i < cantidadDeDatosDelHermanoDerecho; i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, hermanoDerecho.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, hermanoDerecho.getValor(i));
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoDerecho.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoDerecho.getHijo(cantidadDeDatosDelHermanoDerecho));
                nodoPadreDelNodoActual.setHijos(posicionDelNodoActualEnelPadre, nuevoHijo);
            } else if (posicionDelNodoActualEnelPadre != 0) {
                NodoMvias<K,V> hermanoIzquierdo = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre - 1);
                NodoMvias<K,V> nuevoHijo = new NodoMvias<>(this.orden + 1);
                //cargamos al nuevohijo
                //primero El hermanoIzquierdo
                int posicionActualDelNuevoHijo = 0;
                for (int i = 0; i < hermanoIzquierdo.cantidadDeClavesNoVacias(); i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, hermanoIzquierdo.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, hermanoIzquierdo.getValor(i));
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoIzquierdo.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre - 1));
                nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre - 1));
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoIzquierdo.getHijo(posicionActualDelNuevoHijo));
                eliminarPosicioCconElHijo(nodoPadreDelNodoActual, posicionDelNodoActualEnelPadre - 1);
                posicionActualDelNuevoHijo++;
                //ahora cargamos el nodoActula
                int cantidadDeDatosDelNodoActual = nodoActual.cantidadDeClavesNoVacias();
                for (int i = 0; i < cantidadDeDatosDelNodoActual; i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoActual.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoActual.getValor(i));
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(cantidadDeDatosDelNodoActual));
                nodoPadreDelNodoActual.setHijos(posicionDelNodoActualEnelPadre - 1, nuevoHijo);
            }
        }
        if (!pilaDeAncestros.isEmpty()) {
            if (nodoPadreDelNodoActual.cantidadDeClavesNoVacias()< this.nroMinimoDeDatos) {
                prestarseOFusionar(nodoPadreDelNodoActual, pilaDeAncestros);
            }
        } else {
            if (nodoPadreDelNodoActual.cantidadDeClavesNoVacias()== 0) { //cambiamos de raiz
                this.raiz = nodoPadreDelNodoActual.getHijo(0);
            }
        }
 }
   
   public boolean hayHijoMasAdelante(NodoMvias<K,V> nodoActual,int posicion){
       if(nodoActual.esHijoVacio(posicion+1) && posicion < this.orden){
           return false;
       }
     return true;
   }
   
  public K buscarClaveSucesorInOrden(NodoMvias<K,V> nodoActual,K claveAEliminar){
        List<K> recorridoInOrden=new LinkedList<>();
       recorridoInOrdenRec(nodoActual, recorridoInOrden);
       int posicionInOrden=0;
       boolean existe=false;
       for (int i = 0; i < recorridoInOrden.size() && existe== false; i++) {
           if(recorridoInOrden.get(i).compareTo(claveAEliminar)==0){
               posicionInOrden=i;
               existe=true;
           }
       }
       return recorridoInOrden.get(posicionInOrden+1);
  }
  
  // 9. Para un árbol b implemente un método que retorne verdadero si todos sus nodos no hojas no tienen datos vacíos, falso en caso contrario. 
  public boolean noHojasTienenVacio(){
       return noHojasTienenVacioRec(this.raiz);
    }

    public boolean noHojasTienenVacioRec(NodoMvias<K,V> nodoActual){
        if(NodoMvias.esNodoVacio(nodoActual)){
            return false;
        }
        
        if(!nodoActual.esHoja()){
            return nodoActual.estanClavesLlenas();
        }
        
        for (int i = 0; i < orden-1; i++) {
          return noHojasTienenVacioRec(nodoActual.getHijo(i));
        }
       return true;
    }
}
