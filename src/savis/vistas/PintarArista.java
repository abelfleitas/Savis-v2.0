
package savis.vistas;

import java.awt.Color;
import javax.swing.JButton;
import org.graphstream.graph.Graph;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import javax.swing.JDialog;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.WindowConstants;
import org.graphstream.graph.Node;
import savis.utiles.Auxiliar;

public class PintarArista extends JDialog implements WindowListener{

    Auxiliar auxiliar;
    private boolean dibujarAristas = false;
    private JButton pintarAristaBoton,ejecutarAlgoritmoBoton,desacerCambiosBoton,listaExclusionButton,continuarAlgoritmoBoton;
    private Graph lienzo; 
    private Error1 error1;
    private Principal principal;
    private EliminarArista eli;
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
            + "}"
            + "edge:selected{"
            + "fill-color: #7110C4;"
            + "}";       
    
    public PintarArista(Frame parent, boolean modal,JButton pintarAristaBoton,Graph lienzo,JButton ejecutarAlgoritmoBoton,JButton desacerCambiosBoton,JButton listaExclusionButton,JButton continuarAlgoritmoBoton,EliminarArista eli) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(new Color(240, 243, 244));
        this.setOpacity(0.9f);
        this.setResizable(false);
        SubstanceLookAndFeel.setDecorationType(this.jButton1, DecorationAreaType.HEADER);
        this.setTitle("Información");     
        this.pintarAristaBoton = pintarAristaBoton;
        this.lienzo = lienzo;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
        this.desacerCambiosBoton = desacerCambiosBoton;
        this.listaExclusionButton = listaExclusionButton;
        this.continuarAlgoritmoBoton = continuarAlgoritmoBoton;
        this.eli = eli;
        this.principal = (Principal) parent;
        this.removeWindowListener(this);
        this.addWindowListener(this);
        auxiliar = null;
    }

    
    public void cerrar(){
        this.pintarAristaBoton.setSelected(false);
        this.setVisible(false);
    }
    
    public boolean isDibujarAristas() {
        return dibujarAristas;
    }
    
    public void setDibujarArista(boolean pdibujarArista){
        this.dibujarAristas = pdibujarArista;
    }
    
    public void setAuxiliar(Auxiliar aux){
        this.auxiliar = aux;
    }
    
    public void setLienzo(Graph lienzo) {
        this.lienzo = lienzo;
    }

    public Graph getLienzo() {
        return lienzo;
    }
    
    private boolean isCloseGraph(){
        for (Node n : lienzo) {
            if (lienzo.getNode(n.getId()).getDegree() != 2) {                                
                if (error1 == null) {
                    error1 = new Error1(null, true);
                }
                error1.setMensaje("El diagrama debe encontrarse cerrado.");
                error1.setLocationRelativeTo(null);
                error1.setVisible(true);
                return false;
            }
        }
        if(auxiliar.isCerrado()==false){
          if (error1 == null) {
                    error1 = new Error1(null, true);
                }
                auxiliar.limpiarArray();
                error1.setMensaje("El diagrama debe encontrarse cerrado.");
                error1.setLocationRelativeTo(null);
                error1.setVisible(true);
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/paintEdge.png"))); // NOI18N

        mensajeTexto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mensajeTexto.setText("Selecione dos nodos para trazar una arista");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mensajeTexto)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(mensajeTexto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(this.isCloseGraph()){
            lienzo.removeAttribute("ui.stylesheet");
            lienzo.setAttribute("ui.stylesheet", estiloLienzo);
            setDibujarArista(false);
            this.ejecutarAlgoritmoBoton.setEnabled(true);
            this.listaExclusionButton.setEnabled(true);
            this.pintarAristaBoton.setSelected(false);
            this.pintarAristaBoton.setEnabled(false);
            this.desacerCambiosBoton.setEnabled(false);
            if(this.eli.getActivoContinuar())
            {
                this.continuarAlgoritmoBoton.setEnabled(true);
            }
            this.setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel mensajeTexto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(isCloseGraph()){
           this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
        }
        else{
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        lienzo.removeAttribute("ui.stylesheet");
        lienzo.setAttribute("ui.stylesheet", estiloLienzo);
        setDibujarArista(false);
        this.ejecutarAlgoritmoBoton.setEnabled(true);
        this.listaExclusionButton.setEnabled(true);
        this.pintarAristaBoton.setSelected(false);
        this.pintarAristaBoton.setEnabled(false);
        this.desacerCambiosBoton.setEnabled(false);
        if(this.eli.getActivoContinuar())
        {
            this.continuarAlgoritmoBoton.setEnabled(true);
        }
        this.setVisible(false);
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
