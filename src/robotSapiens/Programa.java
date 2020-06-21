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
 * archivo: Programa.java
 * 
 * Responsabilidad de la clase: Almacenar el estado del mundo, y proveer las
 * operaciones necesarias para realizar búsquedas informadas y no informadas.
 * 
 */

package robotSapiens;

import java.util.ArrayList;

public class Programa {
    
    private Archivo archivo = new Archivo();
    private int numDisparos;
    private Posicion posInicial;
    private Posicion posMeta;
    private Posicion posActual;
    private int[][] mundo = new int[10][10];
    private int colorCache = 0;  //Guarda el "color" de la casilla anterior (para mostrar la solución)
    private int cantidadNodosExpandidos = 0;
    private int profundidadArbol = 0;
    private ArrayList<Nodo> arbolCompleto = new ArrayList<>(); //guarda el árbol completo
    
    //Constructor de la clase
    
    public Programa(){
        
        archivo.leerArchivo();
        mundo = archivo.getMundo();
        numDisparos = archivo.getNumDisparos();        
    }   

    //Métodos get
    
    public Archivo getArchivo() {
        return archivo;
    }  
    
    public int getNumDisparos() {
        return numDisparos;
    }

    public Posicion getPosInicial() {
        return posInicial;
    }

    public Posicion getPosMeta() {
        return posMeta;
    }

    public int[][] getMundo() {
        return mundo;
    }

    public int getCantidadNodosExpandidos() {
        return cantidadNodosExpandidos;
    }

    public int getProfundidadArbol() {
        return profundidadArbol;
    }
    
    //Método reiniciarMundo
    //Reinicia todas los atributos de la clase a su valor inicial
    
    public void reiniciarMundo(){
        
        archivo.leerArchivo();
        mundo = archivo.getMundo();
        numDisparos = archivo.getNumDisparos();
        colorCache = 0;
        cantidadNodosExpandidos = 0;
        profundidadArbol = 0;
        arbolCompleto.clear();
        
    }
    
    //Método cargarNuevoMundo
    //Carga de nuevo el mundo del archivo y guarda su estado en los atributos
    //de la clase
    
    public boolean cargarNuevoMundo(){
        
        boolean exito = archivo.cargarNuevoMundo();
        
        if(exito){            
            mundo = archivo.getMundo();
            numDisparos = archivo.getNumDisparos();  
        }                
        
        return exito;        
    }   
    
    //Calcula la posicion inicial y la final y las guarda en atributos de la clase
    
    public void calcularPosicionInicialFinal(){
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(mundo[i][j] == 2){
                    posInicial = new Posicion(i, j);
                    posActual = new Posicion(i,j);
                    mundo[i][j] = 0; // Cambia la pos inicial de 2 a 0
                }else if(mundo[i][j] == 4){
                    posMeta = new Posicion(i,j);
                }
            }
        }        
    }  
    
    
    //Método realizarBusqueda
    //Recibe el tipo de búsqueda a usar y realiza la búsqueda de la solución
    //Devuelve el nodo solución
    
    public Nodo realizarBusqueda(String tipoBusqueda){
        
        //Arraylist para representar el árbol
        ArrayList<Nodo> arbol = new ArrayList<>();              
        Nodo nodoAExpandir = new Nodo(posInicial, numDisparos, null, null, 0, 0); //Nodo raíz        
        boolean evitarCiclos = (tipoBusqueda.equals("Profundidad") || tipoBusqueda.equals("Avara"));
        
        arbol.add(nodoAExpandir);
        arbolCompleto.add(nodoAExpandir);
        
        int posNodoAExpandir = 0;          
        
        String[] prioridad = {"arriba", "derecha", "abajo", "izquierda"};                 
        
        while(!nodoAExpandir.esMeta(posMeta)){                           
            
            cantidadNodosExpandidos++;                        
            //System.out.println("(" + nodoAExpandir.getPos().getFila() + "," + nodoAExpandir.getPos().getColumna() + ")");        
            
            //Se crean los nodos hijos
            
            for(int i=0; i<4; i++){
                if(nodoAExpandir.movimientoValido(prioridad[i], mundo, evitarCiclos)){
                    arbol.add(nodoAExpandir.mover(prioridad[i], mundo));
                    arbolCompleto.add(nodoAExpandir.mover(prioridad[i], mundo));                    
                }                
            }                       
            
            //Decisión de próximo nodo a expandir
            
            arbol.remove(posNodoAExpandir);
            
            switch (tipoBusqueda) {
                case "Amplitud":
                    nodoAExpandir = arbol.get(0);
                    break;
                case "Costo uniforme":
                    posNodoAExpandir = posMenorCosto(arbol);
                    nodoAExpandir = arbol.get(posNodoAExpandir);
                    break;
                case "Profundidad":
                    posNodoAExpandir = posMayorProfundidad(arbol);
                    nodoAExpandir = arbol.get(posNodoAExpandir);
                    break;
                case "Avara":
                    posNodoAExpandir = posMenorHeuristica(arbol);
                    nodoAExpandir = arbol.get(posNodoAExpandir);
                    break;
                case "A*":                    
                    posNodoAExpandir = posMenorCostoHeuristica(arbol);
                    nodoAExpandir = arbol.get(posNodoAExpandir);
                    break;
            }
        }              
        
        //Se suma el último nodo expandido, que fue el nodo meta        
        cantidadNodosExpandidos++; 
        
        mundo[posInicial.getFila()][posInicial.getColumna()] = 2;
        
        determinarProfundidadArbol();

        return nodoAExpandir;
        
    }
       
    //Método posMenorCosto
    //Devuelve la posicion del nodo de menor costo de un 
    //ArrayList de nodos (árbol)
    
    public int posMenorCosto(ArrayList<Nodo> arbol){
        
        int posMenorCosto = 0;
        int menorCosto = arbol.get(0).getCosto();
                
        for (int i = 0; i < arbol.size(); i++) {
            
            int costoNodo = arbol.get(i).getCosto();            
            
            if(costoNodo < menorCosto){
                posMenorCosto = i;
                menorCosto = costoNodo;                
            }            
        }
        
        return posMenorCosto;        
    }   
    
    //Método posMayorProfundidad
    //Devuelve la posicion del nodo con mayor profundidad a la izquierda
    //de determinado ArrayList de nodos (árbol)
    
    public int posMayorProfundidad(ArrayList<Nodo> arbol){
        
        int posMayorProfundidad = 0;
        int mayorProfundidad = arbol.get(0).getProfundidad();
                
        for (int i = 0; i < arbol.size(); i++) {
            
            int profundidadNodo = arbol.get(i).getProfundidad();            
            
            if(profundidadNodo > mayorProfundidad){
                posMayorProfundidad = i;
                mayorProfundidad = profundidadNodo;                
            }            
        }
        
        return posMayorProfundidad;        
    }

    //Método determinarProfundidadArbol
    //Determina la mayor profundidad del árbol. Para esto, busca el nodo
    //con mayor profundidad del árbol completo
    
    public void determinarProfundidadArbol(){
        
        int posMayor = posMayorProfundidad(arbolCompleto);
        
        profundidadArbol = arbolCompleto.get(posMayor).getProfundidad();        
    }
    
    //Método retornarCamino
    //Con el nodo solución, retorna el camino a seguir, representado como
    //un ArrayList de nodos ordenados según el camino
    
    public ArrayList<Nodo> retornarCamino(Nodo nodoMeta){
                
        ArrayList<Nodo> caminoInvertido = new ArrayList<>();
        Nodo nodo = nodoMeta;        
        
        while(nodo.getPadre() != null){            
                    
            caminoInvertido.add(nodo);
            nodo = nodo.getPadre();
        }
        
        ArrayList<Nodo> camino = new ArrayList<>();        
        
        for (int i = caminoInvertido.size()-1; i >= 0; i--) {            
            camino.add(caminoInvertido.get(i));                                 
        }                     
        
        return camino;        
    }  

    //Método posMenorHeuristica
    //Devuelve la posicion del nodo con menor heurísitca
    
    public int posMenorHeuristica(ArrayList<Nodo> arbol){
        
        int posMenorHeuristica = 0;
        int menorHeuristica = arbol.get(0).heuristica(posMeta);
                
        for (int i = 0; i < arbol.size(); i++) {
            
            int heuristicaNodo = arbol.get(i).heuristica(posMeta);            
            
            if(heuristicaNodo < menorHeuristica){
                posMenorHeuristica = i;
                menorHeuristica = heuristicaNodo;                
            }            
        }
        
        return posMenorHeuristica;        
    }    
    
    
    //Método posMenorCostoHeuristica
    //Devuelve la posicion del nodo de menor costo+heurística
    
    public int posMenorCostoHeuristica(ArrayList<Nodo> arbol){
        
        int posMenorCostoHeuristica = 0;
        int menorCostoHeuristica = arbol.get(0).CostoHeuristica(posMeta);
                
        for (int i = 0; i < arbol.size(); i++) {
            
            int costoHeurNodo = arbol.get(i).CostoHeuristica(posMeta);            
            
            if(costoHeurNodo < menorCostoHeuristica){
                posMenorCostoHeuristica = i;
                menorCostoHeuristica = costoHeurNodo;                
            }            
        }
        
        return posMenorCostoHeuristica;        
    }   
    
    //Método moverRobot
    //Cambia la posición del robot en la matriz del mundo y cambia el número de
    //disparos del mundo, según el parámetro recibido.  
    //Este método se implementa con el objetivo de simular el movimiento del
    //robot por el mundo gráficamente (con la interfaz)
    
    public void moverRobot(String direccion, int numDisparos){     
        
        int fila = posActual.getFila(), columna = posActual.getColumna();        
        
        switch(direccion){
            
            case "izquierda":                                
                mundo[fila][columna] = colorCache;   //posicion que deja el robot --> se le pone el color cache
                colorCache = mundo[fila][columna-1]; //nueva posicion del robot  --> se guarda su color en colorCache
                mundo[fila][columna-1] = 2;          //Se mueve el 2 (robot)
                posActual = posActual.izquierda();   //Se mueve el atributo posActual a la izquerda
                break;
            case "arriba":                                
                mundo[fila][columna] = colorCache;
                colorCache = mundo[fila-1][columna];
                mundo[fila-1][columna] = 2;
                posActual = posActual.arriba();     //Se mueve el atributo posActual arriba
                break;
            case "derecha":                                
                mundo[fila][columna] = colorCache;
                colorCache = mundo[fila][columna+1];
                mundo[fila][columna+1] = 2;
                posActual = posActual.derecha();    //Se mueve el atributo posActual a la derecha
                break;
            case "abajo":                                
                mundo[fila][columna] = colorCache;
                colorCache = mundo[fila+1][columna];
                mundo[fila+1][columna] = 2;
                posActual = posActual.abajo();      //Se mueve el atributo posActual abajo
                break;            
        }
        
        this.numDisparos = numDisparos;  //Cambia el valor de la variable numDisparos
    }
    
    
    
    
    
}
