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
 * archivo: Nodo.java
 * 
 * Responsabilidad de la clase: Almacenar y manejar la información de un nodo,
 * teniendo en cuentas las operaciones propias de éste.
 * 
 */

package robotSapiens;

import java.util.ArrayList;

public class Nodo {
    
    private Posicion pos;
    private int numDisparos;
    private Nodo padre;
    private String operador;
    private int profundidad;
    private int costo;

    //Constructor de la clase
    public Nodo(Posicion pos, int numDisparos, Nodo padre, String operador, int profundidad, int costo) {
        this.pos = pos;
        this.numDisparos = numDisparos;
        this.padre = padre;
        this.operador = operador;
        this.profundidad = profundidad;
        this.costo = costo;
    }
    
    //Método mover
    //Devuelve un nuevo nodo, cuya posición es el resultado de cambiar la posicion del actual
    // "izquierda", "arriba", "derecha", "abajo"
    
    public Nodo mover(String direccion, int mundo[][]){
        
        Nodo nodo = null;
        Posicion newPos = new Posicion(0,0);
        int numDisparosRestantes = numDisparos;
        String newOperador = direccion;
        int newCosto = costo + 1; //costo acumulado por defecto:1
        
        switch (direccion) {
            case "izquierda":
                newPos = pos.izquierda();
                break;
            case "arriba":
                newPos = pos.arriba();
                break;
            case "derecha":
                newPos = pos.derecha();
                break;
            case "abajo":               
                newPos = pos.abajo();
                break;
        }
        
        //Revisa si en la nueva posicion a la que va hay un robot hostil
        
        if(mundo[newPos.getFila()][newPos.getColumna()] == 3) {
                        
            if(numDisparos > 0){
                //Si quedan disparos, le resta 1 a los del nuevo nodo
                numDisparosRestantes = numDisparos - 1;
            }
            else{
                //Si no quedan disparos, le va a costar al robot 4 puntos adicionales llegar a esa casilla
                newCosto+= 4;            
            }            
        }        
        
        nodo = new Nodo(newPos, numDisparosRestantes, this, newOperador, profundidad+1, newCosto);
        
        return nodo;       
    }
    
    //Método esMeta
    //Revisa si la posicion actual es la posición de meta
    
    public boolean esMeta(Posicion posMeta){    
        
        boolean meta = pos.equalPos(posMeta); 
        
        return meta;
    }        
    
    //Método movimientoValido
    //Recibe un movimiento (1,2,3 ó 4) y el mundo, y decide si el movimiento es válido o no
    
    public boolean movimientoValido(String direccion, int[][]mundo, boolean evitarCiclos){
        
        Posicion newPos = null;        
        
        //Se saca la nueva posición hipotética
        switch (direccion) {
            case "izquierda":
                newPos = pos.izquierda();
                break;
            case "arriba":
                newPos = pos.arriba();
                break;
            case "derecha":
                newPos = pos.derecha();                
                break;
            case "abajo":                
                newPos = pos.abajo();                            
                break;
        }
           
        //Se revisa si la fila y la columna están en el intervalo [0,9]
        //Se revisa si en la nueva posición no hay un muro
        
        boolean valido = ((newPos.getFila() >=0) &&(newPos.getFila() <=9) &&
                      (newPos.getColumna() >=0) && (newPos.getColumna() <=9));
        
        //Si está en el intervalo, se revisa si no hay muro
        
        if(valido){
            valido = valido && (mundo[newPos.getFila()][newPos.getColumna()] != 1);
        }       
        
        //Para evitar devolverse, se valida que la nueva posición no
        //sea igual a la del padre        
        
        if(padre!= null){
            valido = valido && !newPos.equalPos(padre.getPos());
        }
        
        //Si se están evitando ciclos, se revisa que no hayan ciclos
        
        if(evitarCiclos){
            valido = valido && !esCiclo(newPos);
        }
        
        return valido;
    }
    
    //Método esCiclo
    //Revisa si al ir a una nueva posición se va a entrar en un cilo o no
    
    public boolean esCiclo(Posicion newPosicion){
        
        boolean esCiclo = false;
        Nodo nodo = this;
                
        while (nodo != null){
            
            if(newPosicion.equalPos(nodo.getPos())){
                esCiclo = true;
                break;
            }  
            nodo = nodo.getPadre();
        }
        
        return esCiclo;
    }
    
    //Método heuristica
    //Devuelve el valor de la heurística para le nodo actual 
    //(Distancia de Manhattan)
    
    public int heuristica(Posicion posMeta){        
                
        int heuristica = Math.abs(pos.getFila() - posMeta.getFila()) + Math.abs(pos.getColumna() - posMeta.getColumna());
        
        return heuristica;
    }
    
    //Método costoHeuristica
    //Devuelve el valor de costo+heurística = g(n) + h(n)
    
    public int CostoHeuristica(Posicion posMeta){
        
        int valor = heuristica(posMeta) + costo;
        
        return valor;
    }
        
    //Métodos get

    public Posicion getPos() {
        return pos;
    } 

    public int getNumDisparos() {
        return numDisparos;
    }    
    
    public Nodo getPadre() {
        return padre;
    }

    public String getOperador() {
        return operador;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public int getCosto() {
        return costo;
    }
    
    
    
}


