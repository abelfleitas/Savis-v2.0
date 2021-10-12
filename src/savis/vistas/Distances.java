package savis.vistas;

import savis.RS.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import javax.swing.JDialog;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Distances extends JDialog  implements KeyListener{

    private Frame parent;
    private Graph lienzo;
    private Error1 error1;
    private ArrayList<Ciudad> listaNodos;
    public Distances(Frame parent, boolean modal,Graph lienzo,ArrayList<Ciudad> listaNodos) {
        super(parent, modal);
        initComponents();
        this.setTitle("Distancia de X1 a X2");
        this.getContentPane().setBackground(new Color(240, 243, 244));
        this.setOpacity(0.9f);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo_1.png")));
        this.setResizable(false);
        SubstanceLookAndFeel.setDecorationType(this.jButton1, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.jButton2, DecorationAreaType.HEADER);
        this.parent = parent;
        this.lienzo = lienzo;
        this.listaNodos = listaNodos;
        SpinnerModel model1 = new SpinnerNumberModel(1, 1, lienzo.getNodeCount(), 1);        
        this.webSpinner1.setModel(model1);
        SpinnerModel model2 = new SpinnerNumberModel(2, 1, lienzo.getNodeCount(), 1);        
        this.webSpinner2.setModel(model2);
        
        webSpinner1.addKeyListener(this);
        webSpinner2.addKeyListener(this);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        webSpinner1 = new com.alee.laf.spinner.WebSpinner();
        webSpinner2 = new com.alee.laf.spinner.WebSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Nodo X1 :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Nodo X2:");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Aceptar");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        webSpinner1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        webSpinner2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(webSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(webSpinner2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(webSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(webSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String pointX1 = this.webSpinner1.getValue().toString();
        String pointX2 = this.webSpinner2.getValue().toString();                
        
        if(Integer.parseInt(pointX1)<=lienzo.getNodeCount() && Integer.parseInt(pointX2)<=lienzo.getNodeCount())
        {
            Node n1 = lienzo.getNode(pointX1);
            Node n2 = lienzo.getNode(pointX2);
            Ciudad c1 = searchCity(n1);
            Ciudad c2 = searchCity(n2);

            double distancia = c1.distanciaA(c2);

            Menssage m = new Menssage(parent, true,distancia);
            m.setLocationRelativeTo(parent);
            m.setVisible(true);
        }
        else if(Integer.parseInt(pointX1)>lienzo.getNodeCount()){
            error1 = new Error1(parent, true);
            error1.setMensaje("EL nodo X1 no existe.");//La ciudad X1 no existe.
            error1.setLocationRelativeTo(parent);
            error1.setVisible(true);         
        }
        else{
            error1 = new Error1(parent, true);
            error1.setMensaje("El nodo X2 no existe.");//La ciudad X2 no existe.
            error1.setLocationRelativeTo(parent);
            error1.setVisible(true); 
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public Ciudad searchCity(Node x){        
        Ciudad informacionNP = listaNodos.get(Integer.parseInt(x.getId())-1);
        int nombreId =  informacionNP.getNombreId();
        double coordenadaX = informacionNP.getCoordenadaX();
        double coordenadaY = informacionNP.getCoordenadaY();  
        Ciudad ciudad = new Ciudad(nombreId, coordenadaX, coordenadaY);
        return ciudad;
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private com.alee.laf.spinner.WebSpinner webSpinner1;
    private com.alee.laf.spinner.WebSpinner webSpinner2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
       char c;
        c=e.getKeyChar();
        if(!(c<'0'|| c>'9'))
        {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
