package savis.vistas;

import savis.RS.Ciudad;
import savis.utiles.Item;
import savis.utiles.ItemRenderer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;

public class List extends JDialog implements ActionListener,ListSelectionListener{

    private ArrayList<Ciudad> listaNodos,listaNodosMarcados;
    private DefaultListModel listModel;
    
    public List(Frame parent, boolean modal,ArrayList<Ciudad> listaNodos,ArrayList<Ciudad> listaNodosMarcados) {
        super(parent, modal);
        initComponents();
        this.setTitle("Lista de Exclusión");
        this.getContentPane().setBackground(new Color(240, 243, 244)); 
        this.setOpacity(0.9f);
        SubstanceLookAndFeel.setDecorationType(this.jButton1, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.jButton2, DecorationAreaType.HEADER);
        this.setResizable(false);     
        this.listaNodos = listaNodos;  
        this.listaNodosMarcados = listaNodosMarcados;        
        this.listModel = new DefaultListModel();                               
        for(int i=0;i<this.listaNodos.size();i++)
        {
            String x="Nodo:  "+this.listaNodos.get(i).getNombreId();
            listModel.addElement(new Item(x,new ImageIcon(this.getClass().getResource("/icons/nodo.png"))));            
        }
        jList1.setModel(listModel);
        jList1.setCellRenderer(new ItemRenderer());               
        jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jList1.addListSelectionListener(this);
        jButton2.setEnabled(false);
        jButton2.addActionListener(this);
        System.err.println("Sise lista nodos marcados : "+listaNodosMarcados);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.setFocusable(false);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == jList1) {
            if (jList1.getSelectedIndex() == -1)
                jButton2.setEnabled(false);
            else 
            {
                jButton2.setEnabled(true);
            }
        }
        
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButton2){
            if(jList1.getSelectedIndices().length == 1){
                listaNodosMarcados.add(listaNodos.get(jList1.getSelectedIndex()));
                listaNodos.remove(jList1.getSelectedIndex());
                listModel.removeElementAt(jList1.getSelectedIndex());
                //listaNodosMarcados.add(jList1.getSelectedIndex());
            }
            else{
                int size1 = jList1.getSelectedIndices().length;
                for(int i=0;i<size1;i++)
                {
                   listaNodosMarcados.add(listaNodos.get(jList1.getSelectedIndex()));
                   listaNodos.remove(jList1.getSelectedIndex());
                   listModel.removeElementAt(jList1.getSelectedIndex()); 
                }
            }
        }    
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
