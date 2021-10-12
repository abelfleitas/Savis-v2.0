package savis.vistas;

import java.awt.Color;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;

public class Setting extends javax.swing.JDialog {

    //private String representacion = "Aleatoria";
    private String representacion = "Hill Climbing";
    //private String representacion = "Manual";
    private double temperatura = 10000;
    private double enfriamiento = 0.0003;
    private int iteraciones = 50;
    private int velocidad = 50;
    private Error1 error1;

    public Setting(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(new Color(240, 243, 244));
        this.setOpacity(0.9f);
        SubstanceLookAndFeel.setDecorationType(this.jButton1, DecorationAreaType.HEADER);
        this.setTitle("Configuración"); 
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jRadioAleatorio = new javax.swing.JRadioButton();
        jRadioHillClimbing = new javax.swing.JRadioButton();
        jRadioManual = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jTemperatura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jEnfriamiento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jIteraciones = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        reguladorVelocidad = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Aceptar");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 51, 51));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/representacionConfig.png"))); // NOI18N
        jLabel8.setText("Solución Inicial");

        buttonGroup1.add(jRadioAleatorio);
        jRadioAleatorio.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jRadioAleatorio.setText("Aleatoria");
        jRadioAleatorio.setFocusable(false);
        jRadioAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioAleatorioActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioHillClimbing);
        jRadioHillClimbing.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jRadioHillClimbing.setSelected(true);
        jRadioHillClimbing.setText("Hill Climbing");
        jRadioHillClimbing.setFocusable(false);
        jRadioHillClimbing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioHillClimbingActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioManual);
        jRadioManual.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jRadioManual.setText("Manual");
        jRadioManual.setFocusable(false);
        jRadioManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioManualActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 51, 51));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/parametrosConfig1.png"))); // NOI18N
        jLabel9.setText("Parámetros ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Temperatura:");

        jTemperatura.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTemperatura.setText("100000");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Enfriamiento:");

        jEnfriamiento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jEnfriamiento.setText("0.0003");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Iteraciones:");

        jIteraciones.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jIteraciones.setText("50");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 51, 51));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/velocidadConfig.png"))); // NOI18N
        jLabel4.setText("Velocidad de Representación");

        reguladorVelocidad.setBackground(new java.awt.Color(240, 243, 244));
        reguladorVelocidad.setMajorTickSpacing(50);
        reguladorVelocidad.setMaximum(1000);
        reguladorVelocidad.setMinimum(50);
        reguladorVelocidad.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioHillClimbing)
                                    .addComponent(jRadioAleatorio)
                                    .addComponent(jRadioManual)))
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel2))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jEnfriamiento)
                                            .addComponent(jIteraciones)
                                            .addComponent(jTemperatura, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabel9)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(reguladorVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioAleatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioHillClimbing)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioManual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEnfriamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jIteraciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reguladorVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (Float.parseFloat(jTemperatura.getText().trim().toString()) <= 0     || 
                Float.parseFloat(jEnfriamiento.getText().trim().toString()) <= 0     || 
                Integer.parseInt(jIteraciones.getText().trim().toString()) <= 0) {
                
                if (error1 == null) 
                {
                    error1 = new Error1(null, true);
                }
                error1.setMensaje("Los campos deben tener números mayores que cero.");//Los campos deben tener números mayores que cero.
                error1.setLocationRelativeTo(null);
                error1.setVisible(true);
                return;
            }
            temperatura = Double.parseDouble(String.valueOf(jTemperatura.getText()));
            enfriamiento = Double.parseDouble(String.valueOf(jEnfriamiento.getText()));
            iteraciones = Integer.parseInt(String.valueOf(jIteraciones.getText()));
            velocidad = reguladorVelocidad.getValue();
            
        } catch (NumberFormatException e) {
            if (error1 == null) {
                error1 = new Error1(null, true);
            }
            error1.setMensaje("Debe llenar los campos con números.");//Debe llenar los campos con números.
            error1.setLocationRelativeTo(null);
            error1.setVisible(true);
            return;
        }
        velocidad = reguladorVelocidad.getValue();
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioAleatorioActionPerformed
        this.representacion = jRadioAleatorio.getText();
    }//GEN-LAST:event_jRadioAleatorioActionPerformed

    private void jRadioHillClimbingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioHillClimbingActionPerformed
        this.representacion = jRadioHillClimbing.getText();
    }//GEN-LAST:event_jRadioHillClimbingActionPerformed

    private void jRadioManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioManualActionPerformed
        this.representacion = jRadioManual.getText();
    }//GEN-LAST:event_jRadioManualActionPerformed

   public String getRepresentacion() {
        return representacion;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getEnfriamiento() {
        return enfriamiento;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public int getVelocidad() {
        return velocidad;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jEnfriamiento;
    private javax.swing.JTextField jIteraciones;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioAleatorio;
    private javax.swing.JRadioButton jRadioHillClimbing;
    private javax.swing.JRadioButton jRadioManual;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTemperatura;
    private javax.swing.JSlider reguladorVelocidad;
    // End of variables declaration//GEN-END:variables
}
