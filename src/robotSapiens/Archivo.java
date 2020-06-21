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
 * archivo: Archivo.java
 * 
 * Responsabilidad de la clase: Manejar las operaciones relacionadas con 
 * la carga de archivos de texto.
 * 
 */

package robotSapiens;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Archivo {      
    
    File archivo = new File("prueba.txt");
    private int numDisparos = 0;
    private int[][] mundo = new int[10][10];

    //Métodos get
    
    public int getNumDisparos() {
        return numDisparos;
    }

    public int[][] getMundo() {
        return mundo;
    }

    public File getArchivo() {
        return archivo;
    }    
        
    //Método ingresarFila
    //Ingresa una fila del archivo a la fila correspondiente de la matriz
    
    public void ingresarFila(String linea, int fila){
        
        StringTokenizer token = new StringTokenizer(linea);
        
        int columna = 0;
        
        while(token.hasMoreTokens()){
            mundo[fila][columna] = Integer.parseInt(token.nextToken());
            columna++;
        }        
    }
    
    //Método leerArchivo
    //Lee el archivo que haya en el atributo de la clase y guarda el mundo
    
    public void leerArchivo(){
        
        FileReader fr = null;
        BufferedReader br = null;
     
        try {
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea="";             
            
            int numLinea = 0;
            
            while((linea=br.readLine())!=null){
                if(numLinea != 0){
                    ingresarFila(linea, numLinea-1);
                }else{
                    numDisparos = Integer.parseInt(linea);
                }
                numLinea++;
            }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo. Revise su formato", "Error", JOptionPane.ERROR_MESSAGE);            
        }finally{
            try{                   
                if( null != fr ){  
                    fr.close();    
                }                 
            }catch (Exception e2){
                JOptionPane.showMessageDialog(null, "Error al leer el archivo. Revise su formato", "Error", JOptionPane.ERROR_MESSAGE);                
            }
        }        
    }
    
    //Método cargarNuevoMundo
    //Proporciona la opción de cargar un nuevo mundo con un selector de archivos
    //y guarda la información del mundo cargado

    public boolean cargarNuevoMundo(){   
        
        boolean exito = false;
        
        JFileChooser fChooser = new JFileChooser();
        fChooser.showOpenDialog(null);
        
        File newFile = fChooser.getSelectedFile();
        
        if(newFile != null){            
            archivo = newFile;
            leerArchivo();
            exito = true;            
        }else{
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return exito;
        
    }   
}
