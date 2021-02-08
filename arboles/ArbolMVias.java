/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import arboles.Exception.ExceptionOrdenInvalida;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author liz fernanda reina quispert
 * @param <K>
 * @param <V>
 */
public class ArbolMVias<K extends Comparable<K>,V> 
        implements IArbolBinario<K,V> {

    protected NodoMvias<K,V> raiz;
    protected int POSICION_INVALIDA=-1;
    protected int orden;

    
    public ArbolMVias() {
        this.orden=3;
    }
    
    public ArbolMVias(int orden)throws ExceptionOrdenInvalida {
      if(orden<3){
          throw new ExceptionOrdenInvalida(); 
      }
        this.orden = orden;
    }
    
    @Override
     public K minimo(){
         if(esArbolVacio()){
            return null;
        }
        
          NodoMvias<K,V> nodoAnterior=( NodoMvias<K,V>)NodoMvias.nodoVacio().nodoVacio();
         NodoMvias<K,V> nodoActual=this.raiz;
       
       while(!NodoMvias.esNodoVacio(nodoActual)){
           nodoAnterior=nodoActual;
           nodoActual=nodoActual.getHijo(0);     
       }
       return nodoAnterior.getClave(0);
    }
    
    
    
    @Override
    public K maximo(){
          if(esArbolVacio()){
            return null;
        }
        
          NodoMvias<K,V> nodoAnterior=(NodoMvias<K,V>)NodoMvias.nodoVacio();
        NodoMvias<K,V> nodoActual=this.raiz;
       
       while(!NodoMvias.esNodoVacio(nodoActual)){
           nodoAnterior=nodoActual;
          nodoActual=nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());      
       }
       return nodoAnterior.getClave(nodoActual.cantidadDeClavesNoVacias());  
    }
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if(esArbolVacio()){
            this.raiz=new NodoMvias<>(this.orden,claveAInsertar,valorAInsertar);
            return;
        }else{
        
      NodoMvias<K,V> nodoActual=this.raiz;
        while (!NodoMvias.esNodoVacio(nodoActual)) {
          int posicionClaveExistente=this.existeClaveEnNodo(nodoActual,claveAInsertar);
           if(posicionClaveExistente !=POSICION_INVALIDA){
              nodoActual.setValor(posicionClaveExistente, valorAInsertar);
           }
           
           if(nodoActual.esHoja()){
               if(nodoActual.estanClavesLlenas()){
                 int posicionPorDondeBajar=this.porDondeBajar(nodoActual,claveAInsertar); 
                NodoMvias<K,V> nuevoHijo=new NodoMvias<>(this.orden,claveAInsertar,valorAInsertar);
                nodoActual.setHijos(posicionPorDondeBajar,nuevoHijo);
               }else{
                 this.insertarClaveOrdenadaEnOrden(nodoActual,claveAInsertar,valorAInsertar);
               }
             nodoActual=NodoMvias.nodoVacio();
           }else{
               int posicionPorDondeBajar=this.porDondeBajar(nodoActual,claveAInsertar); 
               if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
                  NodoMvias<K,V> nuevoHijo=new NodoMvias<>(this.orden,claveAInsertar,valorAInsertar);
                nodoActual.setHijos(posicionPorDondeBajar,nuevoHijo);
                nodoActual=NodoMvias.nodoVacio();  
               }else{
                   nodoActual=nodoActual.getHijo(posicionPorDondeBajar);
               }
           }
           
            
        }
        }
    }

   public int porDondeBajar(NodoMvias<K,V> nodoActual,K claveAInsertar){
       
       for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
         K claveActual=nodoActual.getClave(i);
           if(claveAInsertar.compareTo(claveActual)<0){
               return i;
           }
       }
     
       return nodoActual.cantidadDeClavesNoVacias();
               
   }
   
   public void insertarClaveOrdenadaEnOrden(NodoMvias<K,V> nodoActual,K claveAInsertar,V valorAInsertar){
      int  posicionAPoner=porDondeBajar(nodoActual, claveAInsertar);
       for (int i = nodoActual.cantidadDeClavesNoVacias()-1; i >= posicionAPoner ; i--) {
          K claveEnTurno=nodoActual.getClave(i);
           if(claveEnTurno.compareTo(claveAInsertar)>0){
               nodoActual.setClave(i+1, nodoActual.getClave(i));
               nodoActual.setValor(i+1, nodoActual.getValor(i));
               nodoActual.setHijos(i+1, nodoActual.getHijo(i));
           }
       }
               nodoActual.setClave(posicionAPoner,claveAInsertar);
               nodoActual.setValor(posicionAPoner,valorAInsertar);
               nodoActual.setHijos(posicionAPoner,NodoMvias.nodoVacio());
   }
   
    protected int existeClaveEnNodo(NodoMvias<K,V> nodoActual,K claveABuscar){
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
          K claveActual=nodoActual.getClave(i);
            if(claveABuscar.compareTo(claveActual) == 0){
                return i;
            }
        }
      return  POSICION_INVALIDA;
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

    public NodoMvias<K,V> eliminar(NodoMvias<K,V> nodoActual,K claveAEliminar){
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual=nodoActual.getClave(i);
            
            if(claveAEliminar.compareTo(claveActual)==0){
                if(nodoActual.esHoja()){
                    this.eliminarclaveDeNodo(nodoActual,i);
                    if(nodoActual.cantidadDeClavesNoVacias()==0){
                        return NodoMvias.nodoVacio();
                    }
                    return nodoActual;
                }
                    //si llega aqui es nodo no hoja
                K claveReemplazo;
               if(this.hayHijoMasAdelante(nodoActual,i)){
                  claveReemplazo=this.buscarClaveSucesorInOrden(nodoActual,claveAEliminar);
               }else{
                  claveReemplazo=this.buscarClavePredecesorInOrden(nodoActual,claveAEliminar);
                   
               }
               V valorReemplazo=this.buscar(claveReemplazo);
               nodoActual= eliminar(nodoActual, claveReemplazo);
               nodoActual.setClave(i, claveReemplazo);
               nodoActual.setValor(i, valorReemplazo);
               return nodoActual;
                
            }
            
            if(claveAEliminar.compareTo(claveActual)<0){
                NodoMvias<K,V> supuestoNuevoHijo=this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijos(i,supuestoNuevoHijo);
                return nodoActual;
             }
        }//fin for
        
                NodoMvias<K,V> supuestoNuevoHijo=this.eliminar(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), claveAEliminar);
                nodoActual.setHijos(nodoActual.cantidadDeClavesNoVacias(),supuestoNuevoHijo);
                return nodoActual;
           
    
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
  
   public K buscarClavePredecesorInOrden(NodoMvias<K,V> nodoActual,K claveAEliminar){
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
       return recorridoInOrden.get(posicionInOrden-1);
   }
   
    @Override
    public V buscar(K claveABuscar) {
        NodoMvias<K,V> nodoActual= this.raiz;
        while(!NodoMvias.esNodoVacio(nodoActual)){
            boolean huboCambioDeNodoActual=false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual ; i++) {
                K claveActual=nodoActual.getClave(i);
                if(claveABuscar.compareTo(claveActual)==0){
                    return nodoActual.getValor(i);
                }
                
                if(claveABuscar.compareTo(claveActual)<0){
                   huboCambioDeNodoActual=true;
                   nodoActual=nodoActual.getHijo(i);
                  
                }
            }
           if(!huboCambioDeNodoActual){
                    nodoActual=nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
                }
        }
           return (V)NodoMvias.datoVacio();
    }


    @Override
    public boolean contiene(K clave) {
        return buscar(clave)!= null;
    }

    @Override
    public int size() {
          if(esArbolVacio()){
            return 0;
        }
        
       Queue<NodoMvias<K,V>> colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
        int cantidad=0;
      
       
       while(!colaDeNodos.isEmpty()){
            NodoMvias<K,V> nodoActual=colaDeNodos.poll();
           for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() ; i++) {
               cantidad++;
              if(!nodoActual.esHijoVacio(i)){
               colaDeNodos.offer(nodoActual.getHijo(i));
              }
           
           }
          
             if(!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
               colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
             }
       }
     return cantidad;
    }

    @Override
    public int altura() {
     return altura(this.raiz);
    }
    

   public int altura(NodoMvias<K,V> nodoActual){
       if(NodoMvias.esNodoVacio(nodoActual)){
           return 0;
       }
       int alturaMayor=0;
       for (int i = 0; i < orden; i++) {
           int alturaDeHijo=altura(nodoActual.getHijo(i));
           if(alturaDeHijo>alturaMayor){
               alturaMayor=alturaDeHijo;
           }
       }
         return alturaMayor+1;
       
   }


    @Override
    public void vaciar() {
        this.raiz=NodoMvias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
       return NodoMvias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
         return  this.altura() - 1;
    }

    @Override
    public List<K> recorridoPorNIvel() {
         List<K> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Queue<NodoMvias<K,V>> colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
       
       while(!colaDeNodos.isEmpty()){
           NodoMvias<K,V> nodoActual=colaDeNodos.poll();
           
           for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
              recorrido.add(nodoActual.getClave(i));
           
           if(!nodoActual.esHijoVacio(i)){
               colaDeNodos.offer(nodoActual.getHijo(i));
           } 
           }
           
           
           if(nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
               colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
           }
           
           
       }
      return recorrido;
    }

    public List<K> recorridoPostOrdenRecursivo(){
    List<K> recorrido= new ArrayList<>();
    NodoMvias<K,V> nodoActual=this.raiz;
    recoridoPostOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recoridoPostOrdenRec(NodoMvias<K,V> nodoActual,List<K> recorrido){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return;
    }
    
    recoridoPostOrdenRec(nodoActual.getHijo(0), recorrido);
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
      recoridoPostOrdenRec(nodoActual.getHijo(i+1), recorrido);
      recorrido.add(nodoActual.getClave(i));     
    }
  
}

    @Override
public List<K> recorridoPreOrden(){
    List<K> recorrido= new ArrayList<>();
    NodoMvias<K,V> nodoActual=this.raiz;
    recoridoPreOrdenRec(nodoActual,recorrido);
    return recorrido;
}   

void recoridoPreOrdenRec(NodoMvias<K,V> nodoActual,List<K> recorrido){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return;
    }
    
   
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
     recorrido.add(nodoActual.getClave(i));      
     recoridoPreOrdenRec(nodoActual.getHijo(i), recorrido); 
    }
    
    recoridoPreOrdenRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
}

public List<K> recorridoInOrden(){
       List<K> recorrido= new ArrayList<>();
    NodoMvias<K,V> nodoActual=this.raiz;
    recorridoInOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recorridoInOrdenRec(NodoMvias<K,V> nodoActual,List<K> recorrido){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return;
    }
    
   
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
     recorridoInOrdenRec(nodoActual.getHijo(i), recorrido); 
     recorrido.add(nodoActual.getClave(i));     
    }
    
    recorridoInOrdenRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
}
    

  
    public int cantidadDeDatosVacios() {
        return cantidadDeDatosVacios(this.raiz);
    }

    public int cantidadDeDatosVacios(NodoMvias<K,V> nodoActual){
        if(NodoMvias.esNodoVacio(nodoActual)){
            return 0;
        }
        
        int cantidad=0;
        for (int i = 0; i < orden-1; i++) {
           cantidad+= cantidadDeDatosVacios(nodoActual.getHijo(i));
             if(nodoActual.esClaveVacia(i)){
               cantidad++;
             }
        }
       cantidad+= cantidadDeDatosVacios(nodoActual.getHijo(orden-1));
       return cantidad;
    }
    
   public int cantidadDeHojas(){
       return cantidadDeHojasRec(this.raiz);
   }
   
   public int cantidadDeHojasRec(NodoMvias<K,V> nodoActual){
      if(NodoMvias.esNodoVacio(nodoActual)){
            return 0;
        }
       if(nodoActual.esHoja()){
          return 1;
        }
       
      int cantidad=0;
       for (int i = 0; i < orden; i++) {
        cantidad+= cantidadDeHojasRec(nodoActual.getHijo(i));
       
       }
       return cantidad;
   }
   
    public int cantidadDeHojasDelNivel(int nivel){
        return cantidadDeHojasDelNivelRec(this.raiz,nivel,0);
    }
    
     public int cantidadDeHojasDelNivelRec(NodoMvias<K,V> nodoActual,int nivelABuscar,int nivel){
       if(NodoMvias.esNodoVacio(nodoActual)){
           return 0;
       }
        
       int cantidad=0;
           if(nivelABuscar>=nivel){
          if(!nodoActual.esHoja()){
            return 1;
          }
      } 

        for (int i = 0; i < orden; i++) {
            cantidad=cantidad+cantidadDeHojasDelNivelRec(nodoActual.getHijo(i),nivelABuscar,nivel+1);
        }

       return cantidad;
    }

    @Override
    public String mostrarArbol() {
  
        return generarCadenaDeArbol(this.raiz, "", false);
    }
    
    private String generarCadenaDeArbol(NodoMvias<K,V> nodoActual,String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "└──(R)" : "├──(R)"); //arbol vacio o no
        } else {
            cadena.append(ponerCodo ? "└──(D)" : "├──(I)");  //derecha o izquierda
        }
        if (NodoMvias.esNodoVacio(nodoActual)) {
            cadena.append("╣\n");
            return cadena.toString();
        }
        
        cadena.append(nodoActual.getListaDeClaves().toString());
        cadena.append("\n");
        
        String prefijoAux = prefijo + (ponerCodo ? "   ":"|   ");
        for (int i = 0; i < this.orden - 1; i++) {
            NodoMvias<K,V> nodoIzq = nodoActual.getHijo(i);
            cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));
        }
        
        NodoMvias<K,V> nodoDer = nodoActual.getHijo(this.orden - 1);
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));

        return cadena.toString();
    }

    

    @Override
    public List<K> recorridoEnPostOrden() {
           List<K> recorrido=new LinkedList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;  
      }  
      
      public void recorridoEnPostOrden(NodoMvias<K,V> nodoActual,List<K> recorrido){
          if (NodoMvias.esNodoVacio(nodoActual)){
             return ; 
          }
          recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
          for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
               recorridoEnPostOrden(nodoActual.getHijo(i+1), recorrido);
               recorrido.add(nodoActual.getClave(i)); 
          }
          
    }
   
      
   
  /*
  1 Implementar los métodos que no se implementaron en clases o que se implementaron a medias de árboles m vias de búsqueda y arboles B

  
  
  5. Implemente un método iterativo que retorne la cantidad de datos vacios y no vacíos en un árbol b, 
    pero solo antes del nivel N 
    
    7. Implemente un método que retorne verdadero si un árbol m-vias esta balanceado según las reglas de un árbol B. Falso en caso contrario. 
    8. Implemente un método privado que reciba un dato como parámetro y que retorne cual sería el sucesor inorden de dicho dato, 
            sin realizar el recorrido en inOrden. 
      
   
  */  
    
  /*  2. Implemente un método recursivo que retorne el nivel en que se encuentra una clave que se recibe como parámetro. Devolver -1 si la clave 
          no está en el árbol */
      
  public int nivelQueEstaClave(K claveABuscar){
        return nivelQueEstaClaveRec(this.raiz,claveABuscar);
    }
    
     public int nivelQueEstaClaveRec(NodoMvias<K,V> nodoActual,K claveABuscar){
       if(NodoMvias.esNodoVacio(nodoActual)){
            return 0;
        }
        
        for (int i = 0; i < orden; i++) {
           int nivel= nivelQueEstaClaveRec(nodoActual.getHijo(i),claveABuscar)+1;
             if(nodoActual.getClave(i).compareTo(claveABuscar)==0){
               return nivel;
             }
             
        }
       return -1;
     }
     
  // 3. Implemente un método recursivo que retorne la cantidad de datos no vacíos que hay en el nivel N de un árbol m-vias de búsqueda
        public int  cantidadNodosConDatosVacios(int nivel){    
          return cantidadNodosConDatosNoVacios(this.raiz,nivel,0);
      }
   
      private int cantidadNodosConDatosNoVacios(NodoMvias<K,V> nodoActual,int nivelABuscar,int nivelActual){
         if(NodoMvias.esNodoVacio(nodoActual)){
             return 0;
         }
       
        int cantidad=0;
        for (int i = 0; i < orden-1; i++) {
           cantidad+=cantidadNodosConDatosNoVacios(nodoActual.getHijo(i),nivelABuscar,nivelActual+1);
          if(nivelActual==nivelABuscar){
           if(!nodoActual.esClaveVacia(i)){
               cantidad++;
             }
        }
        }
       return cantidad;
     }
  
  /* 4. Implemente un método recursivo que retorne la cantidad de nodos hojas en un árbol m vías de búsqueda,
          pero solo después del nivel N*/
        public int cantidadHojasApartirDeUnNivel(int nivel){
       return cantidadHojasApartirDeUnNivel(this.raiz,nivel,0);
   }
    private int cantidadHojasApartirDeUnNivel(NodoMvias<K,V> nodoActual,int nivelObjetivo,int nivel){
      if(NodoMvias.esNodoVacio(nodoActual)){
          return 0;
      }  
      
      if(nivel>=nivelObjetivo){
          if (nodoActual.esHoja()){
              return 1;
          }
      }
      
      int cantidad=0;
          for (int i = 0; i < orden; i++) {
              cantidad=cantidad+cantidadHojasApartirDeUnNivel(nodoActual.getHijo(i),
                      nivelObjetivo, nivel+1);
          } 
      return cantidad;
    
    }
    
//    6. Implemente un método que retorne verdadero si solo hay hojas en el último nivel de un árbol m-vias de búsqueda. Falso en caso contrario. 
       public boolean hayHojasEnElUltimoNivel(){
          return  hayHojasEnElUltimoNivel(this.raiz,1);
       }
       
      private boolean hayHojasEnElUltimoNivel(NodoMvias<K,V> nodoActual,int nivel){
           if(NodoMvias.esNodoVacio(nodoActual)){
               return false;
           }
           
            if(nivel==nivel()){
            return  nodoActual.esHoja();
            
           }
            
          for (int i = 0; i < orden; i++) {
              return hayHojasEnElUltimoNivel(nodoActual.getHijo(i),nivel+1);
          }
          
      return true;
       }
}
