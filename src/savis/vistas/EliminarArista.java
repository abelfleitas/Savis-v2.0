package savis.vistas;

import java.awt.Color;
import javax.swing.JDialog;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import savis.utiles.Auxiliar;

public class EliminarArista extends JDialog implements WindowListener{

    private boolean dibujarAristas;
    private ArrayList<String> listaNodosPulsados;
    private Principal principal;
    private Auxiliar auxiliar;
    private boolean activoContinuar;
    JButton pintarArista,deleteAristaBoton,ejecutarAlgoritmoBoton,listaExclusionButton,continuarAlgoritmoBoton;
    private String estiloLienzo = ""
            + "node {"
            + "fill-color: #10C4B5;"
            + "size: 14px, 14px;"
            + "text-background-mode: plain;"
            + "text-background-color: blue;"
            + "text-alignment:center;"
            + "text-style:bold;"
            + "text-visibility-mode:normal;"
            + "text-size:14px;"
            + "text-color: black;"
            + "}"
            + "edge{"
            + "shape: line; "
            + "size: 1.0; "
            + "fill-color: #666666;"
            + "arrow-size: 10px;"
            + "}";       
    
    private Graph lienzo;
    
    public EliminarArista(Frame parent, boolean modal,JButton pintarArista,Graph lienzo,JButton deleteAristaBoton,JButton ejecutarAlgoritmoBoton,JButton listaExclusionButton,ArrayList<String> listaNodosPulsados,JButton continuarAlgoritmoBoton) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(240, 243, 244));
        this.setOpacity(0.9f);
        SubstanceLookAndFeel.setDecorationType(this.jButton1, DecorationAreaType.HEADER);
        this.setTitle("Información");               
        this.pintarArista = pintarArista;  
        this.lienzo = lienzo;
        this.deleteAristaBoton = deleteAristaBoton;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
        this.continuarAlgoritmoBoton = continuarAlgoritmoBoton;
        this.listaExclusionButton = listaExclusionButton;
        this.listaNodosPulsados = listaNodosPulsados;
        this.auxiliar=null;
        activoContinuar=false;
        this.removeWindowListener(this);
        this.addWindowListener(this);
        
    }

    public boolean getActivoContinuar() {
        return activoContinuar;
    }
    
    
    public void setActivoContinuar(boolean activoContinuar) {
        this.activoContinuar = activoContinuar;
    }
    
    public boolean isEliminarAristas() {
        return dibujarAristas;
    }
    
    public void setEliminarAristas(boolean pdibujarArista){
        this.dibujarAristas = pdibujarArista;
    }    
     public void cerrar(){
         if(this.isCloseGraph()){
            this.ejecutarAlgoritmoBoton.setEnabled(true);
            this.deleteAristaBoton.setSelected(false);
            this.setEliminarAristas(false);
            this.lienzo.setAttribute("ui.stylesheet", estiloLienzo);
            this.listaExclusionButton.setEnabled(true);
            this.listaNodosPulsados.clear();
            if(this.activoContinuar == true){
                this.continuarAlgoritmoBoton.setEnabled(true);
            }
            this.setVisible(false);
        }
        else{
            this.ejecutarAlgoritmoBoton.setEnabled(false);
            this.deleteAristaBoton.setSelected(false);
            this.setEliminarAristas(false);
            this.lienzo.setAttribute("ui.stylesheet", estiloLienzo);
            this.listaExclusionButton.setEnabled(false);
            this.listaNodosPulsados.clear();
            this.continuarAlgoritmoBoton.setEnabled(false);
            this.setVisible(false);
        } 
    }
     
     public void setAuxiliar(Auxiliar aux){
        this.auxiliar = aux;
    }
    
     private boolean isCloseGraph(){
        for (Node n : lienzo) {
            if (lienzo.getNode(n.getId()).getDegree() != 2) {                                
                return false;
            }
        }
        if(auxiliar.isCerrado()==false){
                auxiliar.limpiarArray();
                return false;  
        }
        return true;
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        mensajeTexto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/deleteEdge.png"))); // NOI18N

        mensajeTexto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mensajeTexto.setText("Seleccione dos nodos para eliminar una arista ");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mensajeTexto))
            .addGroup(layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(mensajeTexto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.cerrar();
    }//GEN-LAST:event_jButton1ActionPerformed

    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel mensajeTexto;
    // End of variables declaration//GEN-END:variables


    @Override
    public void windowClosed(WindowEvent e) {      
        this.cerrar();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
