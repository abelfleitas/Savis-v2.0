package savis.vistas;

import java.awt.Color;
import javax.swing.JDialog;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import org.graphstream.graph.Graph;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;

public class ListaExclusion extends JDialog implements WindowListener{

    boolean need;
    Principal principal;
    private Graph lienzo;
    JButton listaExclusion;
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
    public ListaExclusion(Frame parent, boolean modal,boolean need,Graph lienzo,JButton listaExclusion) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(240, 243, 244));
        this.setOpacity(0.9f);
        SubstanceLookAndFeel.setDecorationType(this.jButton1, DecorationAreaType.HEADER);
        this.need = need;
        this.principal = (Principal) parent;
        this.principal.setNeed(true);
        this.removeWindowListener(this);
        this.addWindowListener(this);
        this.lienzo = lienzo;
        this.listaExclusion = listaExclusion;
        principal.getPintarArista().setEnabled(false);
        principal.getDeleteArista().setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        mensajeTexto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Text Edit1.png"))); // NOI18N

        mensajeTexto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mensajeTexto.setText("Doble click en un nodo para añadirlo a la Lista de Exclusión.");

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
                .addGap(191, 191, 191)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mensajeTexto)
                        .addGap(18, 18, 18)))
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.principal.setNeed(false);
        lienzo.removeAttribute("ui.stylesheet");
        lienzo.setAttribute("ui.stylesheet", estiloLienzo);
        this.listaExclusion.setSelected(false);
        principal.getPintarArista().setEnabled(false);
        principal.getDeleteArista().setEnabled(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void changeSelected(boolean var){
        this.listaExclusion.setSelected(var);  
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel mensajeTexto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowClosed(WindowEvent e) {
        this.principal.setNeed(false);
        lienzo.removeAttribute("ui.stylesheet");
        lienzo.setAttribute("ui.stylesheet", estiloLienzo);
        this.listaExclusion.setSelected(false);
        principal.getPintarArista().setEnabled(true);
        principal.getDeleteArista().setEnabled(true);
        this.setVisible(false);    
    }
    
    
    public void cerrar(){
        this.principal.setNeed(false);
        lienzo.removeAttribute("ui.stylesheet");
        lienzo.setAttribute("ui.stylesheet", estiloLienzo);
        this.listaExclusion.setSelected(false);
        principal.getPintarArista().setEnabled(true);
        principal.getDeleteArista().setEnabled(true);
        this.setVisible(false);
    }
    
    
    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
