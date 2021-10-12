package savis.vistas;

import savis.RS.Ciudad;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import savis.utiles.Savis_CI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.util.MouseManager;
import org.graphstream.ui.view.util.ShortcutManager;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import savis.RS.Tour;
import savis.utiles.Auxiliar;


public class Principal extends javax.swing.JFrame implements ViewerListener, ShortcutManager,MouseWheelListener,WindowListener,MouseManager{

    private Savis_CI savis;
    private Camera camera;
    private Camera cameraMejor;
    private Camera cameraPeor; 
    
    private Graph lienzo = new MultiGraph("Solucion_Inicial");  
    private Viewer viewer = new Viewer(lienzo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);      
    private View view = viewer.addDefaultView(false);    
    
    private Graph lienzoAux2 = new SingleGraph("Solucion1_Inicial"); 
    private Viewer viewerAux2 = new Viewer(lienzoAux2, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD); 
    private View viewAux2 =  viewerAux2.addDefaultView(false);
    
    private Graph lienzoMejor = new SingleGraph("Mejor_Solucion"); 
    private Viewer viewerMejor = new Viewer(lienzoMejor, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD); 
    private View viewMejor =  viewerMejor.addDefaultView(false);
    
    private Graph lienzoAux = new SingleGraph("Mejor1_Solucion"); 
    private Viewer viewerAux = new Viewer(lienzoAux, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD); 
    private View viewAux =  viewerAux.addDefaultView(false);
    
    private Graph lienzoPeor = new SingleGraph("Peor_Solucion");     
    private Viewer viewerPeor = new Viewer(lienzoPeor, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);;
    private View viewPeor = viewerPeor.addDefaultView(false);
    
    private Graph lienzoAux1 = new SingleGraph("Peor1_Solucion");
    private Viewer viewerAux1 = new Viewer(lienzoAux1, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD); 
    private View viewAux1 =  viewerAux1.addDefaultView(false);
    
    private ViewerPipe fromViewer;
    private Error1 error1;
    private Setting configuracionVista;   
    private PintarArista pa;
    private EliminarArista eli; 
    private Cerrar msj;
    private Distances d;
    private ListaExclusion my_lista_exclusion;   
    private static String path="C:/Users/Dell/Documents/NetBeansProjects/tsp";
    private ArrayList<Ciudad> listaNodos;
    private ArrayList<Ciudad> listaNodosMarcados;
    private ArrayList<Ciudad> exclusion;
    private ArrayList<String> listaNodosPulsados;
    private ArrayList<String> nombreAristasAnnadidas;
    private int contador,contador1;  
    protected boolean know = false;
    boolean variableDibujoManual;
    private boolean need;   
    private String cancelacion;
    private Auxiliar auxiliar;   
    private ArrayList<String> nombreArista;    
    
    public Principal() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");                       
        savis = new Savis_CI(this,lienzo);  
        configuracionVista = new Setting(this,true);
        msj = new Cerrar(this, true);
        initComponents();
        this.prepare();
        lienzoPanel.setPreferredSize(new Dimension(800, 700));       
        lienzoPanel.setLayout(new BorderLayout());                      
        lienzoPanel.add((JPanel) view, BorderLayout.CENTER);          
        
        camera = view.getCamera();
        cameraMejor = viewMejor.getCamera();
        cameraPeor  = viewPeor.getCamera();         
        
        lienzoCurrentAux.setPreferredSize(new Dimension(1021, 718));
        lienzoCurrentAux.setLayout(new BorderLayout());
        lienzoCurrentAux.add((JPanel) viewAux2, BorderLayout.CENTER);  
        lienzoCurrentAux.setVisible(false);                
        
        lienzoMejorPanel.setPreferredSize(new Dimension(322, 250));
        lienzoMejorPanel.setLayout(new BorderLayout());
        lienzoMejorPanel.add((JPanel) viewMejor, BorderLayout.CENTER);        
        
        lienzoBestAux.setPreferredSize(new Dimension(1021, 718));
        lienzoBestAux.setLayout(new BorderLayout());
        lienzoBestAux.add((JPanel) viewAux, BorderLayout.CENTER);  
        lienzoBestAux.setVisible(false);        
        
        lienzoPeorPanel.setPreferredSize(new Dimension(322, 250));
        lienzoPeorPanel.setLayout(new BorderLayout());        
        lienzoPeorPanel.add((JPanel) viewPeor, BorderLayout.CENTER);        
        
        lienzoWorseAux.setPreferredSize(new Dimension(1021, 718));
        lienzoWorseAux.setLayout(new BorderLayout());
        lienzoWorseAux.add((JPanel) viewAux1, BorderLayout.CENTER);  
        lienzoWorseAux.setVisible(false);        
             
        
        fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(lienzo);
        this.savis.funcionTemporizada(fromViewer);       
        exclusion = new ArrayList<>();   
        listaNodosPulsados = new ArrayList<>();
        nombreAristasAnnadidas = new ArrayList<>();
        this.contador =0;
        this.contador1=0;
        eli = new EliminarArista(this, false,pintarAristaBoton,lienzo,deleteAristaBoton,ejecutarAlgoritmoBoton,listaExclusionButton,listaNodosPulsados,continuarAlgoritmoBoton);
        pa = new PintarArista(this, false,pintarAristaBoton,lienzo,ejecutarAlgoritmoBoton,desacerCambiosBoton,listaExclusionButton,continuarAlgoritmoBoton,eli);
        my_lista_exclusion = new ListaExclusion(this, false, need, lienzo, listaExclusionButton);

        this.removeWindowListener(this);
        this.addWindowListener(this);
        view.setShortcutManager(this);
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        variableDibujoManual = false;
        this.need=false;
        
        cancelacion = "Representar";
        
    }
    
    
    public void setLienzo(Graph lienzo){
        this.lienzo= lienzo;
    }
    
    public void setListaNodos(ArrayList<Ciudad> listaNodos) {
        this.listaNodos = listaNodos;
        System.err.println("Lista de Nodos Principal : "+listaNodos);
    }
    
    public ArrayList<Ciudad> getListaNodos() {
        return this.listaNodos;
    }

    public void setVariable(boolean variable){
        this.variableDibujoManual = variable;
    }
    public void setContador1(int contador1) {
        this.contador1 = contador1;
    }
    public int getContador1() {
        return contador1;
    }   
    public void setContador(int contador) {
        this.contador = contador;
    }
    public int getContador() {
        return contador;
    }   
    public boolean getNeed(){
        return this.need;
    }   
    public void setNeed(boolean var){
        this.need = var;
    }   
    public boolean alreadyExists(String nodo){
        boolean x = false;
        for(int i =0;i<exclusion.size();i++)
        {
            if(nodo.equals(""+exclusion.get(i).getNombreId())){
                x = true;
            }
        }
        return x;
    }    
    JButton getPintarArista(){
        return this.pintarAristaBoton;
    }    
    JButton getDeleteArista(){
        return this.deleteAristaBoton;
    }
    
    void setCancelacion(String can){
        cancelacion = can;
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ficticio = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        lienzoMejorPanel = new javax.swing.JPanel();
        cargadorMSTexto = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        lienzoPeorPanel = new javax.swing.JPanel();
        cargadorPSTexto = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nombreFicheroTexto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        distanciaMSTexto = new javax.swing.JLabel();
        distanciaPSTexto = new javax.swing.JLabel();
        iteracionTexto = new javax.swing.JLabel();
        timeSolution = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        distanciaSATexto = new javax.swing.JLabel();
        lienzoPanel = new javax.swing.JPanel();
        lienzoBestAux = new javax.swing.JPanel();
        lienzoWorseAux = new javax.swing.JPanel();
        lienzoCurrentAux = new javax.swing.JPanel();
        cargadorAnimadoTexto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cancelarAlgoritmoBoton = new javax.swing.JButton();
        deleteAristaBoton = new javax.swing.JButton();
        desacerCambiosBoton = new javax.swing.JButton();
        listaExclusionButton = new javax.swing.JButton();
        aumentarCamaraBoton = new javax.swing.JButton();
        disminuirCamaraBoton = new javax.swing.JButton();
        rotarLienzoIzquierdaBoton = new javax.swing.JButton();
        rotarLienzoDerechaBoton = new javax.swing.JButton();
        arribaBoton = new javax.swing.JButton();
        izquierdaBoton = new javax.swing.JButton();
        derechaBoton = new javax.swing.JButton();
        debajoBoton = new javax.swing.JButton();
        restablecerVista = new javax.swing.JButton();
        continuarAlgoritmoBoton = new javax.swing.JButton();
        dibujarRepresentacionBoton = new javax.swing.JButton();
        ejecutarAlgoritmoBoton = new javax.swing.JButton();
        pintarAristaBoton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        webMenuBar1 = new com.alee.laf.menu.WebMenuBar();
        webMenu1 = new com.alee.laf.menu.WebMenu();
        importarArchivo = new com.alee.laf.menu.WebMenuItem();
        webSeparator1 = new com.alee.laf.separator.WebSeparator();
        salir = new com.alee.laf.menu.WebMenuItem();
        webMenu2 = new com.alee.laf.menu.WebMenu();
        item_configuracion = new com.alee.laf.menu.WebMenuItem();
        webSeparator3 = new com.alee.laf.separator.WebSeparator();
        item_ListExclusion = new com.alee.laf.menu.WebMenuItem();
        webSeparator5 = new com.alee.laf.separator.WebSeparator();
        item_Distance = new com.alee.laf.menu.WebMenuItem();
        webSeparator4 = new com.alee.laf.separator.WebSeparator();
        webMenu4 = new com.alee.laf.menu.WebMenu();
        item_ExportCurrent = new com.alee.laf.menu.WebMenuItem();
        webSeparator6 = new com.alee.laf.separator.WebSeparator();
        item_ExportBest = new com.alee.laf.menu.WebMenuItem();
        webSeparator7 = new com.alee.laf.separator.WebSeparator();
        item_ExportWorse = new com.alee.laf.menu.WebMenuItem();
        webMenu3 = new com.alee.laf.menu.WebMenu();
        contenido = new com.alee.laf.menu.WebMenuItem();
        webSeparator2 = new com.alee.laf.separator.WebSeparator();
        about = new com.alee.laf.menu.WebMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mejor Solución");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator7))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lienzoMejorPanel.setBackground(new java.awt.Color(255, 255, 255));
        lienzoMejorPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        cargadorMSTexto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/load.gif"))); // NOI18N

        javax.swing.GroupLayout lienzoMejorPanelLayout = new javax.swing.GroupLayout(lienzoMejorPanel);
        lienzoMejorPanel.setLayout(lienzoMejorPanelLayout);
        lienzoMejorPanelLayout.setHorizontalGroup(
            lienzoMejorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lienzoMejorPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cargadorMSTexto)
                .addContainerGap())
        );
        lienzoMejorPanelLayout.setVerticalGroup(
            lienzoMejorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoMejorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cargadorMSTexto)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Peor Solución");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator8))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lienzoPeorPanel.setBackground(new java.awt.Color(255, 255, 255));
        lienzoPeorPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));

        cargadorPSTexto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/load.gif"))); // NOI18N

        javax.swing.GroupLayout lienzoPeorPanelLayout = new javax.swing.GroupLayout(lienzoPeorPanel);
        lienzoPeorPanel.setLayout(lienzoPeorPanelLayout);
        lienzoPeorPanelLayout.setHorizontalGroup(
            lienzoPeorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lienzoPeorPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cargadorPSTexto)
                .addContainerGap())
        );
        lienzoPeorPanelLayout.setVerticalGroup(
            lienzoPeorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoPeorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cargadorPSTexto)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Resultados Numéricos");
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        nombreFicheroTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nombreFicheroTexto.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(nombreFicheroTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addComponent(nombreFicheroTexto))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mejor Solución :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Peor Solución :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Iteraciones :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tiempo :");

        distanciaMSTexto.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        distanciaMSTexto.setForeground(new java.awt.Color(255, 255, 255));

        distanciaPSTexto.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        distanciaPSTexto.setForeground(new java.awt.Color(255, 255, 255));

        iteracionTexto.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        iteracionTexto.setForeground(new java.awt.Color(255, 255, 255));

        timeSolution.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        timeSolution.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Solución Actual :");

        distanciaSATexto.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        distanciaSATexto.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeSolution, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(iteracionTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(14, 14, 14))
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator3)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(distanciaPSTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(13, 13, 13)))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(distanciaSATexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(distanciaMSTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(distanciaSATexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(distanciaMSTexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(distanciaPSTexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(iteracionTexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeSolution)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lienzoMejorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lienzoPeorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lienzoMejorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lienzoPeorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lienzoPanel.setBackground(new java.awt.Color(255, 255, 255));
        lienzoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
        lienzoPanel.setAlignmentX(0.0F);
        lienzoPanel.setAlignmentY(0.0F);

        lienzoBestAux.setBackground(new java.awt.Color(255, 102, 102));

        lienzoWorseAux.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout lienzoCurrentAuxLayout = new javax.swing.GroupLayout(lienzoCurrentAux);
        lienzoCurrentAux.setLayout(lienzoCurrentAuxLayout);
        lienzoCurrentAuxLayout.setHorizontalGroup(
            lienzoCurrentAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        lienzoCurrentAuxLayout.setVerticalGroup(
            lienzoCurrentAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout lienzoWorseAuxLayout = new javax.swing.GroupLayout(lienzoWorseAux);
        lienzoWorseAux.setLayout(lienzoWorseAuxLayout);
        lienzoWorseAuxLayout.setHorizontalGroup(
            lienzoWorseAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoWorseAuxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lienzoCurrentAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        lienzoWorseAuxLayout.setVerticalGroup(
            lienzoWorseAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoWorseAuxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lienzoCurrentAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout lienzoBestAuxLayout = new javax.swing.GroupLayout(lienzoBestAux);
        lienzoBestAux.setLayout(lienzoBestAuxLayout);
        lienzoBestAuxLayout.setHorizontalGroup(
            lienzoBestAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoBestAuxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lienzoWorseAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        lienzoBestAuxLayout.setVerticalGroup(
            lienzoBestAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoBestAuxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lienzoWorseAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        cargadorAnimadoTexto.setBackground(new java.awt.Color(255, 0, 0));
        cargadorAnimadoTexto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cargadorAnimadoTexto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/load.gif"))); // NOI18N
        cargadorAnimadoTexto.setLabelFor(this);
        cargadorAnimadoTexto.setAlignmentY(0.0F);

        javax.swing.GroupLayout lienzoPanelLayout = new javax.swing.GroupLayout(lienzoPanel);
        lienzoPanel.setLayout(lienzoPanelLayout);
        lienzoPanelLayout.setHorizontalGroup(
            lienzoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lienzoBestAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(cargadorAnimadoTexto)
                .addContainerGap())
        );
        lienzoPanelLayout.setVerticalGroup(
            lienzoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lienzoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lienzoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lienzoBestAux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(lienzoPanelLayout.createSequentialGroup()
                        .addComponent(cargadorAnimadoTexto)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        cancelarAlgoritmoBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancelarAlgoritmo1.png"))); // NOI18N
        cancelarAlgoritmoBoton.setBorder(null);
        cancelarAlgoritmoBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelarAlgoritmoBoton.setFocusable(false);
        cancelarAlgoritmoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarAlgoritmoBotonActionPerformed(evt);
            }
        });

        deleteAristaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/trash.png"))); // NOI18N
        deleteAristaBoton.setBorder(null);
        deleteAristaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteAristaBoton.setFocusable(false);
        deleteAristaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAristaBotonActionPerformed(evt);
            }
        });

        desacerCambiosBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/desacerCambios1.png"))); // NOI18N
        desacerCambiosBoton.setBorder(null);
        desacerCambiosBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        desacerCambiosBoton.setFocusable(false);
        desacerCambiosBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desacerCambiosBotonActionPerformed(evt);
            }
        });

        listaExclusionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exclusion1.png"))); // NOI18N
        listaExclusionButton.setBorder(null);
        listaExclusionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listaExclusionButton.setFocusable(false);
        listaExclusionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaExclusionButtonActionPerformed(evt);
            }
        });

        aumentarCamaraBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/aumentarCamara1.png"))); // NOI18N
        aumentarCamaraBoton.setBorder(null);
        aumentarCamaraBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aumentarCamaraBoton.setFocusable(false);
        aumentarCamaraBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aumentarCamaraBotonActionPerformed(evt);
            }
        });

        disminuirCamaraBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disminuirCamara1.png"))); // NOI18N
        disminuirCamaraBoton.setBorder(null);
        disminuirCamaraBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        disminuirCamaraBoton.setFocusable(false);
        disminuirCamaraBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disminuirCamaraBotonActionPerformed(evt);
            }
        });

        rotarLienzoIzquierdaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rotarIzquierda1.png"))); // NOI18N
        rotarLienzoIzquierdaBoton.setBorder(null);
        rotarLienzoIzquierdaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotarLienzoIzquierdaBoton.setFocusable(false);
        rotarLienzoIzquierdaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotarLienzoIzquierdaBotonActionPerformed(evt);
            }
        });

        rotarLienzoDerechaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rotarDerecha1.png"))); // NOI18N
        rotarLienzoDerechaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotarLienzoDerechaBoton.setFocusable(false);
        rotarLienzoDerechaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotarLienzoDerechaBotonActionPerformed(evt);
            }
        });

        arribaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Arrow - up.png"))); // NOI18N
        arribaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        arribaBoton.setFocusable(false);
        arribaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arribaBotonActionPerformed(evt);
            }
        });

        izquierdaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Arrow - left.png"))); // NOI18N
        izquierdaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        izquierdaBoton.setFocusable(false);
        izquierdaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izquierdaBotonActionPerformed(evt);
            }
        });

        derechaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Arrow - right.png"))); // NOI18N
        derechaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        derechaBoton.setFocusable(false);
        derechaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derechaBotonActionPerformed(evt);
            }
        });

        debajoBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Arrow - down.png"))); // NOI18N
        debajoBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        debajoBoton.setFocusable(false);
        debajoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debajoBotonActionPerformed(evt);
            }
        });

        restablecerVista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/restablecerLienzo1.png"))); // NOI18N
        restablecerVista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        restablecerVista.setFocusable(false);
        restablecerVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restablecerVistaActionPerformed(evt);
            }
        });

        continuarAlgoritmoBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/continue.png"))); // NOI18N
        continuarAlgoritmoBoton.setBorder(null);
        continuarAlgoritmoBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        continuarAlgoritmoBoton.setFocusable(false);
        continuarAlgoritmoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarAlgoritmoBotonActionPerformed(evt);
            }
        });

        dibujarRepresentacionBoton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dibujarRepresentacionBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dibujarRepresentacion1.png"))); // NOI18N
        dibujarRepresentacionBoton.setBorder(null);
        dibujarRepresentacionBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dibujarRepresentacionBoton.setFocusable(false);
        dibujarRepresentacionBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dibujarRepresentacionBotonActionPerformed(evt);
            }
        });

        ejecutarAlgoritmoBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ejcutarAlgoritmo1.png"))); // NOI18N
        ejecutarAlgoritmoBoton.setBorder(null);
        ejecutarAlgoritmoBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ejecutarAlgoritmoBoton.setFocusable(false);
        ejecutarAlgoritmoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarAlgoritmoBotonActionPerformed(evt);
            }
        });

        pintarAristaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pintarArista1.png"))); // NOI18N
        pintarAristaBoton.setBorder(null);
        pintarAristaBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pintarAristaBoton.setFocusable(false);
        pintarAristaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pintarAristaBotonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Representar");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Continuar");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ejecutar Recocido Simulado");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Cancelar");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Pintar Arista");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Volver");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Eliminar Arista");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Lista de Exclusión");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Izquierda");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Aumentar ");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Disminuir");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Rotar Izquierda");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Rotar Derecha");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Subir");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Derecha");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Restablecer");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Bajar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deleteAristaBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(desacerCambiosBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ejecutarAlgoritmoBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelarAlgoritmoBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(continuarAlgoritmoBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pintarAristaBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dibujarRepresentacionBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aumentarCamaraBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(disminuirCamaraBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rotarLienzoIzquierdaBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listaExclusionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rotarLienzoDerechaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(arribaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(derechaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(debajoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(restablecerVista, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(izquierdaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dibujarRepresentacionBoton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ejecutarAlgoritmoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cancelarAlgoritmoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(continuarAlgoritmoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pintarAristaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deleteAristaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(desacerCambiosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listaExclusionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(aumentarCamaraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(disminuirCamaraBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rotarLienzoIzquierdaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rotarLienzoDerechaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(arribaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(izquierdaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(derechaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(debajoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(restablecerVista, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(75, 75, 75))
        );

        webMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/file.png"))); // NOI18N
        webMenu1.setText("Archivo");
        webMenu1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        importarArchivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        importarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/folder.png"))); // NOI18N
        importarArchivo.setText("Importar Archivo");
        importarArchivo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        importarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarArchivoActionPerformed(evt);
            }
        });
        webMenu1.add(importarArchivo);
        webMenu1.add(webSeparator1);

        salir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        salir.setText("Salir");
        salir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        webMenu1.add(salir);

        webMenuBar1.add(webMenu1);

        webMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/tools.png"))); // NOI18N
        webMenu2.setText("Herramientas");
        webMenu2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        item_configuracion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        item_configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/configuracion_item.png"))); // NOI18N
        item_configuracion.setText("Configuración");
        item_configuracion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        item_configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_configuracionActionPerformed(evt);
            }
        });
        webMenu2.add(item_configuracion);
        webMenu2.add(webSeparator3);

        item_ListExclusion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        item_ListExclusion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list.png"))); // NOI18N
        item_ListExclusion.setText("Lista de Exclusión");
        item_ListExclusion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        item_ListExclusion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ListExclusionActionPerformed(evt);
            }
        });
        webMenu2.add(item_ListExclusion);
        webMenu2.add(webSeparator5);

        item_Distance.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        item_Distance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/distancia.png"))); // NOI18N
        item_Distance.setText("Distancia de X1 a X2");
        item_Distance.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        item_Distance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_DistanceActionPerformed(evt);
            }
        });
        webMenu2.add(item_Distance);
        webMenu2.add(webSeparator4);

        webMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Image.png"))); // NOI18N
        webMenu4.setText("Exportar Solución");
        webMenu4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        item_ExportCurrent.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        item_ExportCurrent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Image.png"))); // NOI18N
        item_ExportCurrent.setText("Solución Actual");
        item_ExportCurrent.setActionCommand("");
        item_ExportCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        item_ExportCurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ExportCurrentActionPerformed(evt);
            }
        });
        webMenu4.add(item_ExportCurrent);
        webMenu4.add(webSeparator6);

        item_ExportBest.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        item_ExportBest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Image.png"))); // NOI18N
        item_ExportBest.setText("Mejor Solución");
        item_ExportBest.setActionCommand("");
        item_ExportBest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        item_ExportBest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ExportBestActionPerformed(evt);
            }
        });
        webMenu4.add(item_ExportBest);
        webMenu4.add(webSeparator7);

        item_ExportWorse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        item_ExportWorse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Image.png"))); // NOI18N
        item_ExportWorse.setText("Peor Solución");
        item_ExportWorse.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        item_ExportWorse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_ExportWorseActionPerformed(evt);
            }
        });
        webMenu4.add(item_ExportWorse);

        webMenu2.add(webMenu4);

        webMenuBar1.add(webMenu2);

        webMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N
        webMenu3.setText("Ayuda");
        webMenu3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        contenido.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        contenido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/help.png"))); // NOI18N
        contenido.setText("Contenido");
        contenido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        contenido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contenidoActionPerformed(evt);
            }
        });
        webMenu3.add(contenido);
        webMenu3.add(webSeparator2);

        about.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        about.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo.png"))); // NOI18N
        about.setText("Acerca de Savis v2.0");
        about.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        webMenu3.add(about);

        webMenuBar1.add(webMenu3);

        setJMenuBar(webMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lienzoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 626, Short.MAX_VALUE)
            .addComponent(lienzoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        About a = new About(this,true);
        a.setLocationRelativeTo(this);
        a.setVisible(true);
    }//GEN-LAST:event_aboutActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        msj.setLocationRelativeTo(this);         
        msj.setVisible(true);
    }//GEN-LAST:event_salirActionPerformed

    private void item_configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_configuracionActionPerformed
        configuracionVista.setLocationRelativeTo(this);
        configuracionVista.setVisible(true);
    }//GEN-LAST:event_item_configuracionActionPerformed

    private void importarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarArchivoActionPerformed
        this.timeSolution.setText("");                    
        boolean x=false;
        File archivo = null;
        JFileChooser selector = new JFileChooser(path);
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selector.setDialogTitle("Importar Archivo");
        FileFilter filtro = new FileNameExtensionFilter("*.tsp", "tsp");
        selector.setFileFilter(filtro);
        if (JFileChooser.APPROVE_OPTION == selector.showOpenDialog(this)) 
        {
            archivo = selector.getSelectedFile();
            String direccionArchivo = archivo.getAbsolutePath();
            this.savis.limpiarResultadosNumericos(distanciaSATexto,distanciaMSTexto, distanciaPSTexto, iteracionTexto);
            this.nombreFicheroTexto.setText(archivo.getName());
            this.savis.importarArchivo(direccionArchivo, lienzo,dibujarRepresentacionBoton);
            this.listaNodos = this.savis.obtenerListadoTSP();
            listaNodosMarcados = this.savis.obtenerListadoTSP(); 
            this.deleteAristaBoton.setEnabled(false);
            this.pintarAristaBoton.setEnabled(false);
            x=true;
        }
        if(x == true){    
            this.exclusion.removeAll(exclusion);
            this.savis.limpiarLienzo(lienzo);
            this.savis.limpiarLienzo(lienzoMejor);
            this.savis.limpiarLienzo(lienzoPeor);
            this.savis.limpiarLienzo(lienzoAux);
            this.savis.limpiarLienzo(lienzoAux1);
            this.savis.limpiarLienzo(lienzoAux2);             
            this.item_ExportCurrent.setEnabled(false);
            this.item_ExportWorse.setEnabled(false);
            this.item_ExportBest.setEnabled(false);
            this.listaExclusionButton.setEnabled(false);
            this.item_ListExclusion.setEnabled(false);
            this.item_Distance.setEnabled(false);
            this.continuarAlgoritmoBoton.setEnabled(false);
            this.savis.desabilitarButtons(cancelarAlgoritmoBoton, ejecutarAlgoritmoBoton,deleteAristaBoton, pintarAristaBoton, desacerCambiosBoton, aumentarCamaraBoton, disminuirCamaraBoton, rotarLienzoIzquierdaBoton, rotarLienzoDerechaBoton, arribaBoton, debajoBoton, izquierdaBoton, derechaBoton, restablecerVista);
            error1  = new Error1(this,x);
            error1.setMensaje("El archivo "+archivo.getName()+" ha sido importado");
            error1.setIcon("/icons/info_48.png");
            error1.setLocationRelativeTo(this);
            error1.setVisible(x);
        }
    }//GEN-LAST:event_importarArchivoActionPerformed

    private void restablecerVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restablecerVistaActionPerformed
        view.getCamera().resetView();
        viewMejor.getCamera().resetView();
        viewPeor.getCamera().resetView();
    }//GEN-LAST:event_restablecerVistaActionPerformed

    private void debajoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debajoBotonActionPerformed
        this.savis.moverAbajo(camera, cameraMejor, cameraPeor);
    }//GEN-LAST:event_debajoBotonActionPerformed

    private void derechaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derechaBotonActionPerformed
        this.savis.moverDerecha(camera, cameraMejor, cameraPeor);
    }//GEN-LAST:event_derechaBotonActionPerformed

    private void izquierdaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izquierdaBotonActionPerformed
        this.savis.moverIzquierda(camera, cameraMejor, cameraPeor);
    }//GEN-LAST:event_izquierdaBotonActionPerformed

    private void arribaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arribaBotonActionPerformed
        this.savis.moverArriba(camera, cameraMejor, cameraPeor);
    }//GEN-LAST:event_arribaBotonActionPerformed

    private void rotarLienzoDerechaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotarLienzoDerechaBotonActionPerformed
       this.savis.rotarLienzoDerecha(camera,cameraMejor,cameraPeor);
    }//GEN-LAST:event_rotarLienzoDerechaBotonActionPerformed

    private void rotarLienzoIzquierdaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotarLienzoIzquierdaBotonActionPerformed
        this.savis.rotarLienzoIzquierda(camera,cameraMejor,cameraPeor);
    }//GEN-LAST:event_rotarLienzoIzquierdaBotonActionPerformed

    private void disminuirCamaraBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disminuirCamaraBotonActionPerformed
        this.savis.disminuirLienzo(camera);
        this.savis.disminuirLienzo(cameraMejor);
        this.savis.disminuirLienzo(cameraPeor);
    }//GEN-LAST:event_disminuirCamaraBotonActionPerformed

    private void aumentarCamaraBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aumentarCamaraBotonActionPerformed
        this.savis.aumentarLienzo(camera);
        this.savis.aumentarLienzo(cameraMejor);
        this.savis.aumentarLienzo(cameraPeor);
    }//GEN-LAST:event_aumentarCamaraBotonActionPerformed

    private void dibujarRepresentacionBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dibujarRepresentacionBotonActionPerformed
        this.setCancelacion("Representacion");
        this.continuarAlgoritmoBoton.setEnabled(false);
        pa.setDibujarArista(false);
        pa.setVisible(false);
        eli.setEliminarAristas(false);
        eli.setVisible(false);
        my_lista_exclusion.changeSelected(false);
        my_lista_exclusion.setVisible(false);
        deleteAristaBoton.setSelected(false);
        pintarAristaBoton.setSelected(false);        
        this.timeSolution.setText("");
        this.setVariable(false);
        this.exclusion.removeAll(exclusion);
        this.savis.setExclusion(exclusion);
        view.getCamera().resetView();
        this.savis.limpiarLienzo(lienzo);
        this.savis.limpiarLienzo(lienzoAux2);
        this.savis.limpiarLienzo(lienzoMejor);
        this.savis.limpiarLienzo(lienzoPeor);        
        this.savis.limpiarLienzo(lienzoAux);
        this.savis.limpiarLienzo(lienzoAux1);
        this.savis.limpiarResultadosNumericos(distanciaSATexto,distanciaMSTexto, distanciaPSTexto, iteracionTexto);
        this.savis.desabilitarButtons(cancelarAlgoritmoBoton, ejecutarAlgoritmoBoton, deleteAristaBoton, pintarAristaBoton, desacerCambiosBoton, aumentarCamaraBoton, disminuirCamaraBoton, rotarLienzoIzquierdaBoton, rotarLienzoDerechaBoton, arribaBoton, debajoBoton, izquierdaBoton, derechaBoton, restablecerVista);                      
        this.item_configuracion.setEnabled(false);
        this.item_ListExclusion.setEnabled(false);
        this.item_Distance.setEnabled(false);       
        this.item_ExportCurrent.setEnabled(false);
        this.item_ExportBest.setEnabled(false);
        this.item_ExportWorse.setEnabled(false);
        this.listaExclusionButton.setEnabled(false);
        if (configuracionVista == null) 
        {
            configuracionVista = new Setting(this, true);
        }
        if (configuracionVista.getRepresentacion().equals("Aleatoria")) 
        {
            this.dibujarRepresentacionBoton.setEnabled(false);
            this.cancelarAlgoritmoBoton.setEnabled(true);
            this.importarArchivo.setEnabled(false);
            this.savis.solucionInicialAleatoria(lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,this.listaNodos, configuracionVista.getTemperatura(), configuracionVista.getEnfriamiento(), configuracionVista.getVelocidad(), cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto,distanciaSATexto,distanciaMSTexto,distanciaPSTexto, cancelarAlgoritmoBoton,  dibujarRepresentacionBoton, arribaBoton, derechaBoton,debajoBoton,izquierdaBoton, aumentarCamaraBoton, disminuirCamaraBoton, rotarLienzoIzquierdaBoton, rotarLienzoDerechaBoton, restablecerVista,timeSolution,exclusion,deleteAristaBoton,pintarAristaBoton,item_Distance,item_ListExclusion,listaExclusionButton,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion,ejecutarAlgoritmoBoton,importarArchivo,ficticio);
            this.savis.mostrarCargador(cargadorAnimadoTexto);
            this.savis.mostrarCargador(cargadorMSTexto);
            this.savis.mostrarCargador(cargadorPSTexto);
            
        } 
        else if(configuracionVista.getRepresentacion().equals("Hill Climbing"))
        {
            this.dibujarRepresentacionBoton.setEnabled(false);
            this.cancelarAlgoritmoBoton.setEnabled(false);   
            this.importarArchivo.setEnabled(false);
            this.savis.solucionInicialHillClimbing(lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,this.listaNodos, configuracionVista.getTemperatura(), configuracionVista.getEnfriamiento(), configuracionVista.getVelocidad(), cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto,distanciaSATexto,distanciaMSTexto,distanciaPSTexto, cancelarAlgoritmoBoton,  dibujarRepresentacionBoton, arribaBoton, derechaBoton,debajoBoton,izquierdaBoton, aumentarCamaraBoton, disminuirCamaraBoton, rotarLienzoIzquierdaBoton, rotarLienzoDerechaBoton, restablecerVista,timeSolution,exclusion,deleteAristaBoton,pintarAristaBoton,item_Distance,item_ListExclusion,listaExclusionButton,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion,ejecutarAlgoritmoBoton,importarArchivo,ficticio);
            this.savis.mostrarCargador(cargadorAnimadoTexto);
            this.savis.mostrarCargador(cargadorMSTexto);
            this.savis.mostrarCargador(cargadorPSTexto);
        }
        else if(configuracionVista.getRepresentacion().equals("Manual")){
            this.dibujarRepresentacionBoton.setEnabled(false);
            this.cancelarAlgoritmoBoton.setEnabled(true);
            this.importarArchivo.setEnabled(false);
            this.savis.solucionInicialManual(lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,this.listaNodos, configuracionVista.getTemperatura(), configuracionVista.getEnfriamiento(), configuracionVista.getVelocidad(), cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto,distanciaSATexto,distanciaMSTexto,distanciaPSTexto, cancelarAlgoritmoBoton,  dibujarRepresentacionBoton, arribaBoton, derechaBoton,debajoBoton,izquierdaBoton, aumentarCamaraBoton, disminuirCamaraBoton, rotarLienzoIzquierdaBoton, rotarLienzoDerechaBoton, restablecerVista,timeSolution,exclusion,deleteAristaBoton,pintarAristaBoton,item_Distance,item_ListExclusion,listaExclusionButton,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion,importarArchivo);
            this.savis.mostrarCargador(cargadorAnimadoTexto);
            this.savis.mostrarCargador(cargadorMSTexto);
            this.savis.mostrarCargador(cargadorPSTexto);
        }
    }//GEN-LAST:event_dibujarRepresentacionBotonActionPerformed

    private void item_ExportCurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ExportCurrentActionPerformed
        try {
            File archivo = null;
            JFileChooser selector = new JFileChooser();
            String direccionArchivo = null;
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            selector.setDialogTitle("Exportar Imagen");
            javax.swing.filechooser.FileFilter filtro = new FileNameExtensionFilter("*.jpg", "jpg");
            selector.setFileFilter(filtro);
            if (JFileChooser.APPROVE_OPTION == selector.showSaveDialog(null)) {
                archivo = selector.getSelectedFile();
                if (!filtro.accept(archivo)) {
                    String s = archivo.getAbsolutePath();
                    if (archivo.exists())
                    {
                        archivo.delete();
                    }
                    archivo = new File(archivo.getAbsolutePath() + ".jpg");
                    archivo.createNewFile();
                } 
                else if (!(selector.getSelectedFile()).exists()) {
                    archivo.createNewFile();
                }
            }
            this.savis.exportarSolucionImage(lienzoCurrentAux, archivo);
        }
        catch (HeadlessException | IOException e) 
        {
        }
    }//GEN-LAST:event_item_ExportCurrentActionPerformed

    private void item_ExportBestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ExportBestActionPerformed
        try {
            File archivo = null;
            JFileChooser selector = new JFileChooser();
            String direccionArchivo = null;
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            selector.setDialogTitle("Exportar Imagen");
            javax.swing.filechooser.FileFilter filtro = new FileNameExtensionFilter("*.jpg", "jpg");
            selector.setFileFilter(filtro);
            if (JFileChooser.APPROVE_OPTION == selector.showSaveDialog(null)) {
                archivo = selector.getSelectedFile();
                if (!filtro.accept(archivo)) {
                    String s = archivo.getAbsolutePath();
                    if (archivo.exists())
                    {
                        archivo.delete();
                    }
                    archivo = new File(archivo.getAbsolutePath() + ".jpg");
                    archivo.createNewFile();
                } 
                else if (!(selector.getSelectedFile()).exists()) {
                    archivo.createNewFile();
                }
            }
            this.savis.exportarSolucionImage(lienzoBestAux, archivo);
        }
        catch (HeadlessException | IOException e) 
        {
        }
    }//GEN-LAST:event_item_ExportBestActionPerformed

    private void item_ExportWorseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ExportWorseActionPerformed
       try {
            File archivo = null;
            JFileChooser selector = new JFileChooser();
            String direccionArchivo = null;
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            selector.setDialogTitle("Exportar Imagen");
            javax.swing.filechooser.FileFilter filtro = new FileNameExtensionFilter("*.jpg", "jpg");
            selector.setFileFilter(filtro);
            if (JFileChooser.APPROVE_OPTION == selector.showSaveDialog(null)) {
                archivo = selector.getSelectedFile();
                if (!filtro.accept(archivo)) {
                    String s = archivo.getAbsolutePath();
                    if (archivo.exists())
                    {
                        archivo.delete();
                    }
                    archivo = new File(archivo.getAbsolutePath() + ".jpg");
                    archivo.createNewFile();
                } 
                else if (!(selector.getSelectedFile()).exists()) {
                    archivo.createNewFile();
                }
            }
            this.savis.exportarSolucionImage(lienzoWorseAux, archivo);
        }
        catch (HeadlessException | IOException e) 
        {
        }
    }//GEN-LAST:event_item_ExportWorseActionPerformed

    private void cancelarAlgoritmoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarAlgoritmoBotonActionPerformed
        if(cancelacion.equals("Representacion"))
        {
        this.savis.cancelarHilo(configuracionVista.getRepresentacion(),ejecutarAlgoritmoBoton,continuarAlgoritmoBoton,pintarAristaBoton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton,aumentarCamaraBoton,disminuirCamaraBoton,rotarLienzoIzquierdaBoton,rotarLienzoDerechaBoton,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,item_ListExclusion,item_ExportCurrent,item_ExportBest,item_ExportWorse);
        this.savis.limpiarResultadosNumericos(distanciaSATexto, distanciaMSTexto, distanciaPSTexto, iteracionTexto);
        }
        else
        {
            view.getCamera().resetView();
            this.savis.cancelarHiloejecucion();
        }
    }//GEN-LAST:event_cancelarAlgoritmoBotonActionPerformed

    private void pintarAristaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pintarAristaBotonActionPerformed
        this.auxiliar = new Auxiliar(lienzo);
        this.pa.setAuxiliar(auxiliar);
        this.pintarAristaBoton.setSelected(true);
        this.pa.setLocationRelativeTo(this);
        this.pa.setDibujarArista(true);
        this.eli.setEliminarAristas(false);
        if(!this.eli.isEliminarAristas()){
            this.eli.cerrar();
        }
        this.lienzo.setAttribute("ui.stylesheet", estiloLienzo2);
        this.pa.setVisible(true);
    }//GEN-LAST:event_pintarAristaBotonActionPerformed

    private void contenidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contenidoActionPerformed
        try {
            Runtime.getRuntime().exec("cmd /C Savis_v2.0_Help.exe");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_contenidoActionPerformed

    private void continuarAlgoritmoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuarAlgoritmoBotonActionPerformed
        if(my_lista_exclusion.isVisible()){
                my_lista_exclusion.cerrar();
        }
        if(exclusion.size() == lienzo.getNodeCount()){  
                Error1 err = new Error1(this,true);
                error1.setLocationRelativeTo(this);
                error1.setMensaje("No hay nodos disponibles para intercambiar");
                error1.setVisible(true);
            }
        else{
            int cantidadI = Integer.parseInt(iteracionTexto.getText().trim());
            int x = Integer.parseInt(ficticio.getText());    
            this.savis.reempezar(configuracionVista.getRepresentacion(),lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,importarArchivo,item_ListExclusion,item_Distance,item_ExportCurrent,item_ExportBest,item_ExportWorse,dibujarRepresentacionBoton,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuarAlgoritmoBoton,pintarAristaBoton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton,aumentarCamaraBoton,disminuirCamaraBoton,rotarLienzoIzquierdaBoton,rotarLienzoDerechaBoton,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto,configuracionVista.getTemperatura(),configuracionVista.getEnfriamiento(),cantidadI,configuracionVista.getVelocidad(),exclusion,iteracionTexto,ficticio,timeSolution,x,variableDibujoManual,listaNodosMarcados);
        }
    }//GEN-LAST:event_continuarAlgoritmoBotonActionPerformed

    private void deleteAristaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAristaBotonActionPerformed
            this.auxiliar = new Auxiliar(lienzo);
            this.eli.setAuxiliar(auxiliar);
            this.listaNodosPulsados.clear();
            this.deleteAristaBoton.setSelected(true);
            this.lienzo.setAttribute("ui.stylesheet", estiloLienzo1);
            this.listaExclusionButton.setEnabled(false);
            this.ejecutarAlgoritmoBoton.setEnabled(false);
            this.desacerCambiosBoton.setEnabled(false);
            this.eli.setLocationRelativeTo(this);
            this.eli.setEliminarAristas(true);
            if(this.continuarAlgoritmoBoton.isEnabled())
            {
                this.continuarAlgoritmoBoton.setEnabled(false);
                this.eli.setActivoContinuar(true);
            }
            if(this.pa.isDibujarAristas()){
               pa.setDibujarArista(false);
               this.pa.cerrar();
            }
            this.eli.setVisible(true);         
    }//GEN-LAST:event_deleteAristaBotonActionPerformed

    private void desacerCambiosBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desacerCambiosBotonActionPerformed
        this.listaExclusionButton.setEnabled(false);
        this.savis.desacerCambios(nombreAristasAnnadidas,lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,listaNodosPulsados,desacerCambiosBoton);
    }//GEN-LAST:event_desacerCambiosBotonActionPerformed

    private void listaExclusionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaExclusionButtonActionPerformed
        my_lista_exclusion = new ListaExclusion(this, false,need,lienzo,listaExclusionButton);
        my_lista_exclusion.changeSelected(true);
        my_lista_exclusion.setLocation(720,80);
        my_lista_exclusion.setTitle("Lista de Exclusión");
        my_lista_exclusion.setVisible(true);
        lienzo.setAttribute("ui.stylesheet", estiloLienzo3);
    }//GEN-LAST:event_listaExclusionButtonActionPerformed

    private void item_ListExclusionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_ListExclusionActionPerformed
       if(exclusion.isEmpty())
        {
             Error1 e = new Error1(this, true);
             e.setLocationRelativeTo(this);
             e.setIcon("/icons/info_48.png");
             e.setMensaje("La lista de exclusión se encuentra vacía.");
             e.setVisible(true);             
        }
        else{
            List l = new List(this,true,exclusion,listaNodosMarcados);
            l.setLocationRelativeTo(this);
            l.setVisible(true);
        }
    }//GEN-LAST:event_item_ListExclusionActionPerformed

    private void item_DistanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_DistanceActionPerformed
        d = new Distances(this,true,lienzo,listaNodos);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }//GEN-LAST:event_item_DistanceActionPerformed

    private void ejecutarAlgoritmoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarAlgoritmoBotonActionPerformed
        try {
            if(my_lista_exclusion.isVisible()){
                my_lista_exclusion.cerrar();
            }
            if(exclusion.size() == lienzo.getNodeCount()){  
                Error1 err = new Error1(this,true);
                error1.setLocationRelativeTo(this);
                error1.setMensaje("No hay nodos disponibles para intercambiar");
                error1.setVisible(true);
            }
            else{
                this.savis.limpiarLienzo(lienzo);
                this.savis.limpiarLienzo(lienzoAux2);
                this.savis.limpiarLienzo(lienzoMejor);
                this.savis.limpiarLienzo(lienzoAux);
                this.savis.limpiarLienzo(lienzoPeor);
                this.savis.limpiarLienzo(lienzoAux1);
                this.setCancelacion("Ejecucion");
                this.timeSolution.setText("");
                //this.ficticio.setText("");
                this.savis.desabilitarButtons(cancelarAlgoritmoBoton, ejecutarAlgoritmoBoton, deleteAristaBoton, pintarAristaBoton, desacerCambiosBoton, aumentarCamaraBoton, disminuirCamaraBoton, rotarLienzoIzquierdaBoton, rotarLienzoDerechaBoton, arribaBoton, debajoBoton, izquierdaBoton, derechaBoton, restablecerVista);
                this.cancelarAlgoritmoBoton.setEnabled(true);
                this.savis.gestionarRSSIC(configuracionVista.getRepresentacion(),lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,importarArchivo,item_ListExclusion,item_Distance,item_ExportCurrent,item_ExportBest,item_ExportWorse,dibujarRepresentacionBoton,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuarAlgoritmoBoton,pintarAristaBoton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton,aumentarCamaraBoton,disminuirCamaraBoton,rotarLienzoIzquierdaBoton,rotarLienzoDerechaBoton,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto,configuracionVista.getTemperatura(),configuracionVista.getEnfriamiento(),configuracionVista.getIteraciones(),configuracionVista.getVelocidad(),exclusion,iteracionTexto,ficticio,timeSolution,variableDibujoManual,listaNodosMarcados);              
            }
        } 
        catch (NumberFormatException e)
        {
            if (error1 == null) 
            {
                error1 = new Error1(null, true);
            }
            error1.setMensaje("Solo se admiten números mayores que cero.");
            error1.setLocationRelativeTo(null);
            error1.setVisible(true);
            return;
        }
    }//GEN-LAST:event_ejecutarAlgoritmoBotonActionPerformed
    
    public void prepare(){
        SubstanceLookAndFeel.setDecorationType(this.dibujarRepresentacionBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.cancelarAlgoritmoBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.ejecutarAlgoritmoBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.continuarAlgoritmoBoton, DecorationAreaType.HEADER);        
        SubstanceLookAndFeel.setDecorationType(this.deleteAristaBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.pintarAristaBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.desacerCambiosBoton, DecorationAreaType.HEADER); 
        SubstanceLookAndFeel.setDecorationType(this.listaExclusionButton, DecorationAreaType.HEADER);  
        SubstanceLookAndFeel.setDecorationType(this.aumentarCamaraBoton, DecorationAreaType.HEADER);       
        SubstanceLookAndFeel.setDecorationType(this.disminuirCamaraBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.rotarLienzoIzquierdaBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.rotarLienzoDerechaBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.arribaBoton, DecorationAreaType.HEADER);       
        SubstanceLookAndFeel.setDecorationType(this.izquierdaBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.derechaBoton, DecorationAreaType.HEADER); 
        SubstanceLookAndFeel.setDecorationType(this.debajoBoton, DecorationAreaType.HEADER);
        SubstanceLookAndFeel.setDecorationType(this.restablecerVista, DecorationAreaType.HEADER); 
        TooltipManager.setTooltip (this.dibujarRepresentacionBoton,"Representar", TooltipWay.right,5);
        TooltipManager.setTooltip (this.ejecutarAlgoritmoBoton,"Ejecutar Recocido Simulado", TooltipWay.right,5);
        TooltipManager.setTooltip (this.cancelarAlgoritmoBoton,"Cancelar", TooltipWay.right,5);
        TooltipManager.setTooltip (this.continuarAlgoritmoBoton,"Continuar", TooltipWay.right,5);
        TooltipManager.setTooltip (this.pintarAristaBoton,"Pintar Arista", TooltipWay.right,5);
        TooltipManager.setTooltip (this.deleteAristaBoton,"Eliminar Arista", TooltipWay.right,5);
        TooltipManager.setTooltip (this.desacerCambiosBoton,"Volver Atrás", TooltipWay.right,5);   
        TooltipManager.setTooltip (this.listaExclusionButton,"Lista de Exclusión", TooltipWay.right,5);
        TooltipManager.setTooltip (this.aumentarCamaraBoton,"Aumentar", TooltipWay.right,5);
        TooltipManager.setTooltip (this.disminuirCamaraBoton,"Disminuir", TooltipWay.right,5);
        TooltipManager.setTooltip (this.rotarLienzoIzquierdaBoton,"Rotar Izquierda", TooltipWay.right,5);
        TooltipManager.setTooltip (this.rotarLienzoDerechaBoton,"Rotar Derecha", TooltipWay.right,5);
        TooltipManager.setTooltip (this.arribaBoton,"Subir", TooltipWay.right,5);
        TooltipManager.setTooltip (this.izquierdaBoton,"Izquierda", TooltipWay.right,5);
        TooltipManager.setTooltip (this.derechaBoton,"Derecha", TooltipWay.right,5);
        TooltipManager.setTooltip (this.debajoBoton,"Bajar", TooltipWay.right,5);
        TooltipManager.setTooltip (this.restablecerVista,"Restablecer", TooltipWay.right,5);
        this.dibujarRepresentacionBoton.setEnabled(false);
        this.cancelarAlgoritmoBoton.setEnabled(false);
        this.ejecutarAlgoritmoBoton.setEnabled(false);
        this.continuarAlgoritmoBoton.setEnabled(false);
        this.deleteAristaBoton.setEnabled(false);
        this.pintarAristaBoton.setEnabled(false);
        this.desacerCambiosBoton.setEnabled(false);
        this.listaExclusionButton.setEnabled(false);
        this.aumentarCamaraBoton.setEnabled(false);
        this.disminuirCamaraBoton.setEnabled(false);
        this.rotarLienzoIzquierdaBoton.setEnabled(false);
        this.rotarLienzoDerechaBoton.setEnabled(false);
        this.arribaBoton.setEnabled(false);
        this.izquierdaBoton.setEnabled(false);
        this.derechaBoton.setEnabled(false);
        this.debajoBoton.setEnabled(false);
        this.restablecerVista.setEnabled(false);
        this.cargadorAnimadoTexto.setVisible(false);
        this.cargadorMSTexto.setVisible(false);
        this.cargadorPSTexto.setVisible(false);                
        this.item_ListExclusion.setEnabled(false);
        this.item_Distance.setEnabled(false);
        this.listaExclusionButton.setEnabled(false);
        this.item_ExportCurrent.setEnabled(false);
        this.item_ExportWorse.setEnabled(false);
        this.item_ExportBest.setEnabled(false); 
    } 
    

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.menu.WebMenuItem about;
    private javax.swing.JButton arribaBoton;
    private javax.swing.JButton aumentarCamaraBoton;
    private javax.swing.JButton cancelarAlgoritmoBoton;
    private javax.swing.JLabel cargadorAnimadoTexto;
    private javax.swing.JLabel cargadorMSTexto;
    private javax.swing.JLabel cargadorPSTexto;
    private com.alee.laf.menu.WebMenuItem contenido;
    private javax.swing.JButton continuarAlgoritmoBoton;
    private javax.swing.JButton debajoBoton;
    private javax.swing.JButton deleteAristaBoton;
    private javax.swing.JButton derechaBoton;
    private javax.swing.JButton desacerCambiosBoton;
    private javax.swing.JButton dibujarRepresentacionBoton;
    private javax.swing.JButton disminuirCamaraBoton;
    private javax.swing.JLabel distanciaMSTexto;
    private javax.swing.JLabel distanciaPSTexto;
    private javax.swing.JLabel distanciaSATexto;
    private javax.swing.JButton ejecutarAlgoritmoBoton;
    private javax.swing.JLabel ficticio;
    private com.alee.laf.menu.WebMenuItem importarArchivo;
    private com.alee.laf.menu.WebMenuItem item_Distance;
    private com.alee.laf.menu.WebMenuItem item_ExportBest;
    private com.alee.laf.menu.WebMenuItem item_ExportCurrent;
    private com.alee.laf.menu.WebMenuItem item_ExportWorse;
    private com.alee.laf.menu.WebMenuItem item_ListExclusion;
    private com.alee.laf.menu.WebMenuItem item_configuracion;
    private javax.swing.JLabel iteracionTexto;
    private javax.swing.JButton izquierdaBoton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JPanel lienzoBestAux;
    private javax.swing.JPanel lienzoCurrentAux;
    private javax.swing.JPanel lienzoMejorPanel;
    private javax.swing.JPanel lienzoPanel;
    private javax.swing.JPanel lienzoPeorPanel;
    private javax.swing.JPanel lienzoWorseAux;
    private javax.swing.JButton listaExclusionButton;
    private javax.swing.JLabel nombreFicheroTexto;
    private javax.swing.JButton pintarAristaBoton;
    private javax.swing.JButton restablecerVista;
    private javax.swing.JButton rotarLienzoDerechaBoton;
    private javax.swing.JButton rotarLienzoIzquierdaBoton;
    private com.alee.laf.menu.WebMenuItem salir;
    private javax.swing.JLabel timeSolution;
    private com.alee.laf.menu.WebMenu webMenu1;
    private com.alee.laf.menu.WebMenu webMenu2;
    private com.alee.laf.menu.WebMenu webMenu3;
    private com.alee.laf.menu.WebMenu webMenu4;
    private com.alee.laf.menu.WebMenuBar webMenuBar1;
    private com.alee.laf.separator.WebSeparator webSeparator1;
    private com.alee.laf.separator.WebSeparator webSeparator2;
    private com.alee.laf.separator.WebSeparator webSeparator3;
    private com.alee.laf.separator.WebSeparator webSeparator4;
    private com.alee.laf.separator.WebSeparator webSeparator5;
    private com.alee.laf.separator.WebSeparator webSeparator6;
    private com.alee.laf.separator.WebSeparator webSeparator7;
    // End of variables declaration//GEN-END:variables

    @Override
    public void viewClosed(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buttonPushed(String nombreNodo) {
        try{
            if(configuracionVista.getRepresentacion().equals("Aleatoria")|| configuracionVista.getRepresentacion().equals("Hill Climbing")){
                
                if(eli.isEliminarAristas() == true){
                    contador1=this.savis.deleteAristaBy2Node(nombreNodo,lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,listaNodosPulsados);
                    this.setContador1(contador1);
                    nombreAristasAnnadidas.clear();
                    if(this.getContador1()>0){
                        this.pintarAristaBoton.setEnabled(true);
                    }
                }
                else if(pa.isDibujarAristas() == true){
                    contador = this.savis.dibujarAristas(nombreNodo, pintarAristaBoton,listaNodosPulsados, nombreAristasAnnadidas, lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,item_ListExclusion,item_Distance,item_ExportCurrent,item_ExportBest,item_ExportWorse,listaExclusionButton,configuracionVista.getRepresentacion(),listaNodos);
                    this.setContador(contador);
                    pa.setLienzo(lienzo);
                    if(this.getContador()>0){
                        this.setVariable(true);
                        this.desacerCambiosBoton.setEnabled(true);
                        this.deleteAristaBoton.setEnabled(true);
                        this.listaExclusionButton.setEnabled(false);
                    }
                }
                
                else if(need==true){
                    if(!alreadyExists(nombreNodo))
                    {
                        Information i = new Information(this, true, nombreNodo,listaNodos,exclusion,listaNodosMarcados);
                        i.setLocationRelativeTo(this);
                        i.setVisible(true);
                    }
                    else{
                        Error1 e = new Error1(this, true);
                        e.setLocationRelativeTo(this);
                        e.setMensaje("El nodo ya existe en la lista de exclusión.");
                        e.setVisible(true); 
                    }    
               } 
            }
            else{
               if(pa.isDibujarAristas() == true){
                    contador = this.savis.dibujarAristas(nombreNodo,pintarAristaBoton, listaNodosPulsados, nombreAristasAnnadidas, lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,item_ListExclusion,item_Distance,item_ExportCurrent,item_ExportBest,item_ExportWorse,listaExclusionButton,configuracionVista.getRepresentacion(),listaNodos);
                    this.setContador(contador);
                    pa.setLienzo(lienzo);
                    if(this.getContador()>0){
                        this.setVariable(true);
                        this.desacerCambiosBoton.setEnabled(true);
                        this.deleteAristaBoton.setEnabled(true);
                        this.listaExclusionButton.setEnabled(false);
                    }
               }
               else if(eli.isEliminarAristas() == true){
                    contador1=this.savis.deleteAristaBy2Node(nombreNodo,lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,listaNodosPulsados);
                    nombreAristasAnnadidas.clear();
                    this.setContador1(contador1);
                    if(this.getContador1()>0){
                        this.pintarAristaBoton.setEnabled(true);
                    }
               }
               else if(need==true){
                    if(!alreadyExists(nombreNodo))
                    {
                        Information i = new Information(this, true, nombreNodo,listaNodos,exclusion,listaNodosMarcados);
                        i.setLocationRelativeTo(this);
                        i.setVisible(true);
                    }
                    else{
                        Error1 e = new Error1(this, true);
                        e.setLocationRelativeTo(this);
                        e.setMensaje("El nodo ya existe en la lista de exclusión.");
                        e.setVisible(true); 
                    }    
               }
            }        
        }
        catch(Exception e){
            
        }
    }

    @Override
    public void buttonReleased(String string) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init(GraphicGraph gg, View view) {
        this.view = view;
        view.addKeyListener(this);
        viewMejor.addKeyListener(this);
        viewPeor.addKeyListener(this);
    }

    @Override
    public void release() {
        view.removeKeyListener(this);
        viewMejor.removeKeyListener(this);
        viewPeor.removeKeyListener(this);
        TooltipManager.hideAllTooltips();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'R') 
        {
            view.getCamera().resetView();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if ((event.getModifiers() & KeyEvent.CTRL_MASK) != 0) 
        {
             know = true;
        }  
        else if (event.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if ((event.getModifiers() & KeyEvent.SHIFT_MASK) != 0) 
            {
               this.savis.rotarLienzoIzquierda(camera,cameraMejor,cameraPeor);
            } 
            else {
                this.savis.moverIzquierda(camera, cameraMejor, cameraPeor);
            }
        } 
        else if (event.getKeyCode() == KeyEvent.VK_RIGHT) 
        {
            if ((event.getModifiers() & KeyEvent.SHIFT_MASK) != 0) 
            {
                this.savis.rotarLienzoDerecha(camera,cameraMejor,cameraPeor);
            } 
            else {
                this.savis.moverDerecha(camera, cameraMejor, cameraPeor);
            }
        } 
        else if (event.getKeyCode() == KeyEvent.VK_UP)
        {
            this.savis.moverArriba(camera, cameraMejor, cameraPeor);
        }
        else if (event.getKeyCode() == KeyEvent.VK_DOWN) 
        {
            this.savis.moverAbajo(camera, cameraMejor, cameraPeor);
        }
        else if (event.getKeyCode()== KeyEvent.VK_R) 
        {
            camera.resetView();
            cameraMejor.resetView();
            cameraPeor.resetView();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        know = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        String message;
        int notches = e.getWheelRotation();
        if (notches < 0 && know == true) 
        {
        this.savis.aumentarLienzo(camera);
        this.savis.aumentarLienzo(cameraMejor);
        this.savis.aumentarLienzo(cameraPeor);
        } 
        else if(know == true)
        {
            this.savis.disminuirLienzo(camera);
            this.savis.disminuirLienzo(cameraMejor);
            this.savis.disminuirLienzo(cameraPeor);
        
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    @Override
    public void windowClosing(WindowEvent e) {
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        msj.setLocationRelativeTo(this);         
        msj.setVisible(true);   
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
       
    private String estiloLienzo1 = ""
            + "node {"
            + "fill-color: #f44336;"
            + "size: 20px, 20px;"
            + "text-background-mode: plain;"
            + "text-background-color: blue;"
            + "text-alignment:center;"
            + "text-style:bold;"
            + "text-visibility-mode:normal;"
            + "text-size:14px;"
            + "text-color: black;"
            + "}"
            + "edge{"
            + "shape: line;"
            + "size: 1.5; "
            + "fill-color:#00838f;"
            + "arrow-size: 10px;"
            + "}"
            +"node:clicked{"
            + "fill-color: #7110C4;"
            + "}";
    
    private String estiloLienzo2 = ""
            + "node {"
            + "fill-color: #00838f;"
            + "size: 20px, 20px;"
            + "text-background-mode: plain;"
            + "text-background-color: blue;"
            + "text-alignment:center;"
            + "text-style:bold;"
            + "text-visibility-mode:normal;"
            + "text-size:14px;"
            + "text-color: black;"
            + "}"
            + "edge{"
            + "shape: line;"
            + "size: 1.5; "
            + "fill-color:#f44336;"
            + "arrow-size: 10px;"
            + "}"
            + "node:clicked{"
            + "fill-color: #7110C4;"
            + "}";
    
    private String estiloLienzo3 = ""
            + "node {"
            + "fill-color: #00838f;"
            + "size: 20px, 20px;"
            + "text-background-mode: plain;"
            + "text-background-color: blue;"
            + "text-alignment:center;"
            + "text-style:bold;"
            + "text-visibility-mode:normal;"
            + "text-size:14px;"
            + "text-color: black;"
            + "}"
            + "edge{"
            + "shape: line;"
            + "size: 1.5; "
            + "fill-color:#666666;"
            + "arrow-size: 10px;"
            + "}"
            + "node:clicked{"
            + "fill-color: #7110C4;"
            + "}";
    

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


   
    
}
