/**
 * Proyecto I Inteligencia Artificial
 * Búsqueda informada y no informada
 * Fecha de entrega: 07/04/2017
 *
 * Integrantes: 
 *
 * Jhonier Andrés Calero Rodas		1424599
 * Juan Pablo Moreno Muñoz		1423437
 * Joan Manuel Tovar Guzmán		1423124
 *
 * Universidad del Valle
 * EISC
 *
 * archivo: Posicion.java
 * 
 * Responsabilidad de la clase: Almacenar y manejar la información de una 
 * posición (fila, columna), teniendo en cuenta las operaciones propias de ésta.
 * 
 */

package robotSapiens;

public class Posicion {    
    
    private int fila;
    private int columna;

    //Constructo de la clase
    
    public Posicion(int x, int y) {
        this.fila = x;
        this.columna = y;
    }

    //Métodos get
    
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    //Método equalPos
    //Evalua si la fila y columna son iguales a las del objeto 
    
    public boolean equalPos(Posicion pos){
        
        return ((fila == pos.getFila()) && (columna== pos.getColumna()));
        
    }  
    
    //Métodos izquierda,arriba,derecha,abajo
    //Devuelven la posición resultante de moverse a la respectiva dirección
    
    
    public Posicion izquierda(){
        Posicion pos = new Posicion(fila, columna-1);
        return pos;
    };
    
    public Posicion arriba(){
        Posicion pos = new Posicion(fila-1, columna);
        return pos;
    };
    
    public Posicion derecha(){
        Posicion pos = new Posicion(fila, columna+1);
        return pos;
    };
    
    public Posicion abajo(){
        Posicion pos = new Posicion(fila+1, columna);
        return pos;
    };
    
    
    
}
