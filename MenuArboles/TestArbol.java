/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuArboles;

import arboles.ArbolAVL;
import arboles.ArbolB;
import arboles.ArbolBusquedaBinaria;
import arboles.ArbolMVias;
import arboles.Exception.ExceptionOrdenInvalida;
import arboles.IArbolBinario;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author brandon
 */
public class TestArbol {
    public static void main(String[] args) throws ExceptionOrdenInvalida{
      IArbolBinario<Integer,String> ArbolDeBusqueda = null;
      Scanner entrada= new Scanner(System.in);
        System.out.println("elija el tipo de arbol,ABB, AVL,AMV,AB");
    
        
        String tipoDeArbol=entrada.next();
        tipoDeArbol=tipoDeArbol.toUpperCase();
     switch(tipoDeArbol){
          
          case("ABB"):
              ArbolDeBusqueda=new ArbolBusquedaBinaria<>();
              break;
        
          case("AVL"):
              ArbolDeBusqueda=new ArbolAVL<>();
          break;
          
            case("AMV"):
              ArbolDeBusqueda=new ArbolMVias<>(4);
          break;
      
          
           case("AB"):
              ArbolDeBusqueda=new ArbolB<Integer, String>(3);
          break;
          default:
              System.out.println("tipo de arbol invaldo, escogiendo.. arbol binario de Mvias");
               ArbolDeBusqueda=new ArbolBusquedaBinaria<>();
      }
      
              ArbolDeBusqueda.insertar(20, "cristian soza");
              ArbolDeBusqueda.insertar(7, "julio gonzales");
              ArbolDeBusqueda.insertar(23, "llanos");
              ArbolDeBusqueda.insertar(9, "mario");
              ArbolDeBusqueda.insertar(11, "armando");
              ArbolDeBusqueda.insertar(25, "julia");
              ArbolDeBusqueda.insertar(4, "berta");
              ArbolDeBusqueda.insertar(3, "marcela"); 
              ArbolDeBusqueda.insertar(22, "carol");
              ArbolDeBusqueda.insertar(10, "valeria");
              ArbolDeBusqueda.insertar(8, "marco");
              ArbolDeBusqueda.insertar(24, "polo");
              ArbolDeBusqueda.insertar(50, "sandra");
              ArbolDeBusqueda.insertar(100, "luz");
              ArbolDeBusqueda.insertar(35, "nicol");
           
          
           
            System.out.println(ArbolDeBusqueda.mostrarArbol());
       int opcion= -1;
     Scanner ponerDato= new Scanner(System.in);
     int cantidad;
    while(opcion!=0){
       System.out.println("Introduca Una Opcion ");
       opcion=entrada.nextInt();
      
        switch(opcion){
         
            case 1:  
               
            
  /*  2. Implemente un método recursivo que retorne el nivel en que se encuentra una clave que se recibe como parámetro. Devolver -1 si la clave 
          no está en el árbol */
               
                System.out.println("pregunta 2-> " + ((ArbolMVias)ArbolDeBusqueda).nivelQueEstaClave(ponerDato.nextInt()));
            break;    
           
            case 2:  
               // 3. Implemente un método recursivo que retorne la cantidad de datos no vacíos que hay en el nivel N de un árbol m-vias de búsqueda
                System.out.println("pregunta 3-> " + ((ArbolMVias)ArbolDeBusqueda).cantidadNodosConDatosVacios(ponerDato.nextInt()));
            break;   
            
            case 3:  
           /* 4. Implemente un método recursivo que retorne la cantidad de nodos hojas en un árbol m vías de búsqueda,
          pero solo después del nivel N*/
       
                System.out.println("pregunta 4-> " + ((ArbolMVias)ArbolDeBusqueda).cantidadHojasApartirDeUnNivel(ponerDato.nextInt()));
            break;   
            
            
             case 4:  
            //    6. Implemente un método que retorne verdadero si solo hay hojas en el último nivel de un árbol m-vias de búsqueda. Falso en caso contrario. 
   
              System.out.println("pregunta 6-> " + ((ArbolMVias)ArbolDeBusqueda).hayHojasEnElUltimoNivel());
            break;   
           
            case 5:  
             // 9. Para un árbol b implemente un método que retorne verdadero si todos sus nodos no hojas no tienen datos vacíos, falso en caso contrario. 

                  System.out.println("pregunta 9-> " + ((ArbolB)ArbolDeBusqueda).noHojasTienenVacio());
                  System.out.println(ArbolDeBusqueda.mostrarArbol());
                  
              break; 
             
        }
    }   
 

 }     
    
    
}
