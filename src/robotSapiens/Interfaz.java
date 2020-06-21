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
 * archivo: Interfaz.java
 * 
 * Responsabilidad de la clase: Gestionar la interfaz gráfica (GUI).
 * 
 */

package robotSapiens;

import java.awt.Dimension;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Interfaz extends javax.swing.JFrame {

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Programa programa = new Programa();
    private Font font;
    private ArrayList<Nodo> camino = new ArrayList<>();
    private long tiempoEjecucion = 0;
    
    //Constructor de la clase
    
    public Interfaz() {
        super("Robot-Sapiens");   
        initComponents();
        setLocationRelativeTo(null); 
        
        inicializarTimerTask();
        
        registrarFuente();
        
        lEstado.setFont(font);
        lDisparos.setFont(font);      
        
        dibujarMundo();
        programa.calcularPosicionInicialFinal();
        
        lDisparos.setText("Disparos: " + programa.getNumDisparos());
    } 
    
    //Método dibujarMundo
    //Dibuja el mundo por primera vez, de acuerdo al estado del mundo que haya
    //en el objeto programa
    
    public void dibujarMundo(){                
        
        panelTablero.setLayout(new GridLayout(10, 10));
        JButton boton = new JButton("");        
        
        for (int i = 0; i < 10; i++) {
            
            for (int j = 0; j < 10; j++) {
                boton = new JButton();
                boton.setSize(50, 50);                
                boton.setIcon(new ImageIcon("src/images/" + programa.getMundo()[i][j] + ".png"));                
                panelTablero.add(boton);
            }            
        }    
        
    }

    
    //Método actualizarMundo
    //Cambia los iconos de los botones, dependiendo del estado del mundo que 
    //haya en programa
    
    public void actualizarMundo(){
        
        lDisparos.setText("Disparos: " + programa.getNumDisparos());
        
        int indice=0;
                
        for (int i = 0; i < 10; i++) {
            
            for (int j = 0; j < 10; j++) {
                JButton boton = (JButton)panelTablero.getComponent(indice);                              
                boton.setIcon(new ImageIcon("src/images/" + programa.getMundo()[i][j] + ".png"));                
                indice++;
            }            
        }        
    }    
    
    
    //Método registrarFuente()
    //Registra la fuente de texto usada y la guarda en un atributo
    
    public void registrarFuente(){
        
        try {
            GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/font/joystix monospace.ttf"));
            ge.registerFont(font);
            font = new Font(font.getName(), Font.PLAIN, 14);
            
        } catch (IOException|FontFormatException e) {
            System.out.println("Error al cargar fuente");
        }
        
    }
    
    //Método reiniciarTodo
    //Reinicia el estado del mundo en programa e inicializa los demás atributos
    //de la clase, para que puedan ser utilizados de nuevo en otra ocasión
    
    public void reiniciarTodo(){
        
        programa.reiniciarMundo();
        actualizarMundo();
        programa.calcularPosicionInicialFinal();
        timer = new Timer();
        inicializarTimerTask();
        bSolucionar.setEnabled(true);
        lEstado.setText("Estado: Listo");
        
    }
    
    
    //Método inicializarTimerTask()
    //Inicializa el atributo timerTask de la clase
    //Se pone en un método, pues es necesario inicializarlo varias veces
    //(cada que se vaya a mostrar la solución)
    
    public void inicializarTimerTask(){
        
        this.timerTask = new TimerTask() {
            
            int i=0;
            
            public void run(){
                
                if(i < camino.size()){
                    programa.moverRobot(camino.get(i).getOperador(), camino.get(i).getNumDisparos());                    
                    actualizarMundo();
                    i++;
                }else{
                    String info="Información de la solución\n" +
                            "Cantidad de nodos Expandidos:  " + programa.getCantidadNodosExpandidos() + "\n" +
                            "Profundidad del árbol:         " + programa.getProfundidadArbol() + "\n" +
                            "Costo de la solución:          " + camino.get(camino.size()-1).getCosto() + "\n" +
                            "Tiempo de ejecución:      " + tiempoEjecucion + " milisegundos" ;
                    
                    JOptionPane.showMessageDialog(null, info);
                    reiniciarTodo();                    
                    cancel();
                }
            }
        }; 
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelTablero = new javax.swing.JPanel();
        panelImagen = new javax.swing.JPanel();
        panelSeleccion = new javax.swing.JPanel();
        cbTipoBusqueda = new javax.swing.JComboBox<>();
        cbAlgoritmo = new javax.swing.JComboBox<>();
        bSolucionar = new javax.swing.JButton();
        lEstado = new javax.swing.JLabel();
        lDisparos = new javax.swing.JLabel();
        lImagen = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        bCargarMundo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        bInstrucciones = new javax.swing.JMenuItem();
        bAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(0, 0, 0));

        panelTablero.setBackground(new java.awt.Color(0, 0, 0));
        panelTablero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTablero.setMaximumSize(new java.awt.Dimension(415, 415));
        panelTablero.setMinimumSize(new java.awt.Dimension(415, 4215));
        panelTablero.setPreferredSize(new java.awt.Dimension(415, 415));

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
            panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelImagen.setBackground(new java.awt.Color(0, 0, 0));

        panelSeleccion.setBackground(new java.awt.Color(0, 0, 0));
        panelSeleccion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 3, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        cbTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "No informada", "Informada" }));
        cbTipoBusqueda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipoBusquedaItemStateChanged(evt);
            }
        });

        cbAlgoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));
        cbAlgoritmo.setEnabled(false);
        cbAlgoritmo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbAlgoritmoItemStateChanged(evt);
            }
        });

        bSolucionar.setText("Solucionar");
        bSolucionar.setEnabled(false);
        bSolucionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSolucionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSeleccionLayout = new javax.swing.GroupLayout(panelSeleccion);
        panelSeleccion.setLayout(panelSeleccionLayout);
        panelSeleccionLayout.setHorizontalGroup(
            panelSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cbAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(bSolucionar)
                .addContainerGap())
        );
        panelSeleccionLayout.setVerticalGroup(
            panelSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbTipoBusqueda)
                    .addComponent(bSolucionar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAlgoritmo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lEstado.setBackground(new java.awt.Color(51, 189, 226));
        lEstado.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        lEstado.setForeground(new java.awt.Color(51, 189, 226));
        lEstado.setText("Estado: Listo");

        lDisparos.setBackground(new java.awt.Color(51, 189, 226));
        lDisparos.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        lDisparos.setForeground(new java.awt.Color(51, 189, 226));
        lDisparos.setText("Disparos: 7");

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagenLayout.createSequentialGroup()
                .addComponent(panelSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 60, Short.MAX_VALUE))
            .addGroup(panelImagenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lDisparos, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelImagenLayout.setVerticalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagenLayout.createSequentialGroup()
                .addComponent(panelSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lEstado)
                .addGap(18, 18, 18)
                .addComponent(lDisparos)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        lImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/RobotSapiens.png"))); // NOI18N
        lImagen.setText("jLabel1");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 59, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(102, 205, 170));
        jMenuBar1.setBorder(null);
        jMenuBar1.setMaximumSize(new java.awt.Dimension(92, 30));
        jMenuBar1.setMinimumSize(new java.awt.Dimension(92, 30));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(92, 30));

        jMenu1.setText("Archivo");

        bCargarMundo.setText("Cargar Mundo");
        bCargarMundo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCargarMundoActionPerformed(evt);
            }
        });
        jMenu1.add(bCargarMundo);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        bInstrucciones.setText("Instrucciones");
        bInstrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInstruccionesActionPerformed(evt);
            }
        });
        jMenu2.add(bInstrucciones);

        bAcerca.setText("Acerca de");
        bAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAcercaActionPerformed(evt);
            }
        });
        jMenu2.add(bAcerca);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSolucionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSolucionarActionPerformed
        
        long tiempoInicial, tiempoFinal;  //variables para calcular tiempo
        
        bSolucionar.setEnabled(false);        
        
        String algoritmo = (String)cbAlgoritmo.getSelectedItem(); //algoritmo seleccionado
        
        tiempoInicial = System.currentTimeMillis();
        Nodo nodoSolucion = programa.realizarBusqueda(algoritmo);  //se realiza la búsqueda de la solución
        tiempoFinal = System.currentTimeMillis();                
        
        tiempoEjecucion = tiempoFinal - tiempoInicial; //se calcula el tiempo de ejecución
        
        camino = programa.retornarCamino(nodoSolucion); //se asigna el camino seguido
        
        lEstado.setText("Estado: Mostrando resultado");        
        timer.scheduleAtFixedRate(timerTask, 0, 500);   //se comienza a mostrar la solución
    }//GEN-LAST:event_bSolucionarActionPerformed

    private void bCargarMundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCargarMundoActionPerformed
                
        boolean exito = programa.cargarNuevoMundo();
        
        //Si se cargó correctamente
        if(exito){
            actualizarMundo();
            programa.calcularPosicionInicialFinal();            
            camino.clear();                        
            inicializarTimerTask();
            JOptionPane.showMessageDialog(null, "Mundo cargado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        }        
        
    }//GEN-LAST:event_bCargarMundoActionPerformed

    private void bInstruccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInstruccionesActionPerformed
        
        String info = "ROBOT-SAPIENS\n\n"
                + "Objetivo: Ayudar a Metal Gear a liquidar a Snake\n\n"
                + "Pasos a seguir:\n\n"
                + "1. Seleccionar el tipo de búsqueda (informada o no informada)\n"
                + "2. Seleccionar el algoritmo de búsqueda\n"
                + "3. Presionar el botón 'Solucionar'";
        
        
        JOptionPane.showMessageDialog(null, info);
    }//GEN-LAST:event_bInstruccionesActionPerformed

    private void bAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAcercaActionPerformed
        JOptionPane.showMessageDialog(null, "Desarrollado por:\nJhonier Andrés Calero Rodas\nJuan Pablo Moreno Muñoz\nJoan Manuel Tovar Guzmán");
    }//GEN-LAST:event_bAcercaActionPerformed

    private void cbTipoBusquedaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipoBusquedaItemStateChanged
        
        String itemSeleccionado = (String)cbTipoBusqueda.getSelectedItem();
        
        switch(itemSeleccionado){
            
            case "Seleccione":
                cbAlgoritmo.setEnabled(false);
                cbAlgoritmo.setSelectedIndex(0);
                break;
            
            case "Informada":
                cbAlgoritmo.removeAllItems();
                cbAlgoritmo.addItem("Seleccione");
                cbAlgoritmo.addItem("Avara");
                cbAlgoritmo.addItem("A*");
                cbAlgoritmo.setEnabled(true);                
                break;
            case "No informada":
                cbAlgoritmo.removeAllItems();
                cbAlgoritmo.addItem("Seleccione");
                cbAlgoritmo.addItem("Amplitud");
                cbAlgoritmo.addItem("Costo uniforme");
                cbAlgoritmo.addItem("Profundidad");
                cbAlgoritmo.setEnabled(true);
                break;
        }
        
    }//GEN-LAST:event_cbTipoBusquedaItemStateChanged

    private void cbAlgoritmoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbAlgoritmoItemStateChanged
        
        if(cbAlgoritmo.getSelectedIndex() != 0){
            bSolucionar.setEnabled(true);
        }else{
            bSolucionar.setEnabled(false);
        }
        
    }//GEN-LAST:event_cbAlgoritmoItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bAcerca;
    private javax.swing.JMenuItem bCargarMundo;
    private javax.swing.JMenuItem bInstrucciones;
    private javax.swing.JButton bSolucionar;
    private javax.swing.JComboBox<String> cbAlgoritmo;
    private javax.swing.JComboBox<String> cbTipoBusqueda;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lDisparos;
    private javax.swing.JLabel lEstado;
    private javax.swing.JLabel lImagen;
    private javax.swing.JPanel panelImagen;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelSeleccion;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables
}
