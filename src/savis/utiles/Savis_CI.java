package savis.utiles;

import hillclimbing.HillClimbing;
import savis.RS.*;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.ViewerPipe;
import savis.servicios.*;
import savis.vistas.Error1;
import savis.vistas.Principal;


public class Savis_CI{

    private ArrayList<Ciudad> listaNodos;       
    private Error1 error1;
    private int cantidadEjecuciones = 1, timeStop=0;
    private ArrayList<Ciudad> exclusion;
    private ImportarFicheroTSP importarFicheroTSP;
    private HallarSolucionRS hallarSolucionRS;  
    private PintarSolucionActual pintarSolucionActual;
    private PintarMejorSolucion pintarMejorSolucion;
    private PintarPeorSolucion pintarPeorSolucion;
    private HillClimbing hillClimbing;
    private PintarHillClimbing pintarHillClimbing;
    private PintarNodosManual pintarNodosManual;
    private HallarSolucionRepresentada solucionRepresentada; 
    private AplicarRSMS aplicarRSMS;
    private Principal principal;
    private Tour goodTour;
    private Graph lienzo;

    
    
    public Savis_CI(Frame parent,Graph lienzo) {
        super();
        this.principal = (Principal) parent;
        this.lienzo = lienzo;
    }
    
    public Graph getLienzo() {
        return lienzo;
    }
    
    public void setPintarHillClimbing(PintarHillClimbing pintarHillClimbing) {
        this.pintarHillClimbing = pintarHillClimbing;
    }
    
    public ArrayList<Ciudad> obtenerListadoTSP() {
        return this.listaNodos;
    }
    
    public void setExclusion(ArrayList<Ciudad> exclusion){
         this.exclusion = exclusion;
     }
     
    
    
    public void limpiarLienzo(Graph lienzo) {
        lienzo.clear();
    } 
    
    public void limpiarResultadosNumericos(JLabel SA, JLabel MS, JLabel PS, JLabel iteraciones) {
        SA.setText("");
        MS.setText("");
        PS.setText("");
        iteraciones.setText("");
    }
    
    public void funcionTemporizada(ViewerPipe fromViewer) {
        Timer pumpTimer = new Timer();
        pumpTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fromViewer.pump();
            }
        }, 250, 250);
    }
    
    public void aumentarLienzo(Camera camera) {
        camera.setViewPercent(Math.max(0.0001f, camera.getViewPercent() * 0.9f));
    }

    public void disminuirLienzo(Camera camera) {
        camera.setViewPercent(camera.getViewPercent() * 1.1f);
    }

    public void rotarLienzoIzquierda(Camera camera,Camera cameraM,Camera cameraP) {
        double r = camera.getViewRotation();
        camera.setViewRotation(r + 5);
        
        double r1 = cameraM.getViewRotation();
        cameraM.setViewRotation(r1 + 5);
        
        double r2 = cameraP.getViewRotation();
        cameraP.setViewRotation(r2 + 5);
    }

    public void rotarLienzoDerecha(Camera camera,Camera cameraM,Camera cameraP) {
        double r = camera.getViewRotation();
        camera.setViewRotation(r - 5);
        
        double r1 = cameraM.getViewRotation();
        cameraM.setViewRotation(r1 - 5);
        
        double r2 = cameraP.getViewRotation();
        cameraP.setViewRotation(r2 - 5);
    }
    
    public void  moverIzquierda(Camera camera,Camera cameraMejor,Camera cameraPeor){
        double delta = camera.getGraphDimension() * 0.01f;
        delta *= camera.getViewPercent();
        Point3 p = camera.getViewCenter();
        camera.setViewCenter(p.x + delta, p.y, 0);
        
        double delta1 = cameraMejor.getGraphDimension() * 0.01f;
        delta1 *= cameraMejor.getViewPercent();
        Point3 p1 = cameraMejor.getViewCenter();
        cameraMejor.setViewCenter(p1.x + delta1, p1.y, 0);
        
        double delta2 = cameraPeor.getGraphDimension() * 0.01f;
        delta2 *= cameraPeor.getViewPercent();
        Point3 p2 = cameraPeor.getViewCenter();
        cameraPeor.setViewCenter(p2.x + delta2, p2.y, 0);    
    }
    
    public void  moverDerecha(Camera camera,Camera cameraMejor,Camera cameraPeor){
        double delta = camera.getGraphDimension() * 0.01f;
        delta *= camera.getViewPercent();
        Point3 p = camera.getViewCenter();
        camera.setViewCenter(p.x - delta, p.y, 0);
        
        double delta1 = cameraMejor.getGraphDimension() * 0.01f;
        delta1 *= cameraMejor.getViewPercent();
        Point3 p1 = cameraMejor.getViewCenter();
        cameraMejor.setViewCenter(p1.x - delta1, p1.y, 0);
        
        double delta2 = cameraPeor.getGraphDimension() * 0.01f;
        delta2 *= cameraPeor.getViewPercent();
        Point3 p2 = cameraPeor.getViewCenter();
        cameraPeor.setViewCenter(p2.x - delta2, p2.y, 0);
    }
    
    public void  moverArriba(Camera camera,Camera cameraMejor,Camera cameraPeor){
        double delta = camera.getGraphDimension() * 0.01f;
        delta *= camera.getViewPercent();
        Point3 p = camera.getViewCenter();
        camera.setViewCenter(p.x, p.y - delta, 0);
        
        double delta1 = cameraMejor.getGraphDimension() * 0.01f;
        delta1 *= cameraMejor.getViewPercent();
        Point3 p1 = cameraMejor.getViewCenter();
        cameraMejor.setViewCenter(p1.x, p1.y - delta1, 0);
        
        double delta2 = cameraPeor.getGraphDimension() * 0.01f;
        delta2 *= cameraPeor.getViewPercent();
        Point3 p2 = cameraPeor.getViewCenter();
        cameraPeor.setViewCenter(p2.x, p2.y - delta2, 0);
    }
    
    public void  moverAbajo(Camera camera,Camera cameraMejor,Camera cameraPeor){
        double delta = camera.getGraphDimension() * 0.01f;
        delta *= camera.getViewPercent();
        Point3 p = camera.getViewCenter();
        camera.setViewCenter(p.x, p.y + delta, 0);        
        
        double delta1 = cameraMejor.getGraphDimension() * 0.01f;
        delta1 *= cameraMejor.getViewPercent();
        Point3 p1 = cameraMejor.getViewCenter();
        cameraMejor.setViewCenter(p1.x, p1.y + delta1, 0);
        
        double delta2 = cameraPeor.getGraphDimension() * 0.01f;
        delta2 *= cameraPeor.getViewPercent();
        Point3 p2 = cameraPeor.getViewCenter();
        cameraPeor.setViewCenter(p2.x, p2.y + delta2, 0);
    }
    
    public void exportarSolucionImage(JPanel lienzoSIPanel, File archivo) {
        try {
            BufferedImage imagen = null;
            imagen = new Robot().createScreenCapture(lienzoSIPanel.getBounds());
            Graphics2D graphics2D = imagen.createGraphics();
            lienzoSIPanel.paint(graphics2D);
            ImageIO.write(imagen, "jpeg", archivo);
        } catch (Exception e) {
        }
    }
    
    public void mostrarCargador(JLabel cargadorAnimadoTexto) {
        cargadorAnimadoTexto.setVisible(true);
    }
    
    public void importarArchivo(String direccionArchivo, Graph lienzo, JButton dibujarRepresentacion) {
        try 
        {
            importarFicheroTSP = new ImportarFicheroTSP(direccionArchivo, lienzo,dibujarRepresentacion);
            importarFicheroTSP.execute();
            this.listaNodos = importarFicheroTSP.get();
        } 
        catch (InterruptedException | ExecutionException ex) 
        {
            Logger.getLogger(Savis_CI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desabilitarButtons(JButton cancelarAlgoritmoBoton,JButton ejecutarAlgoritmoBoton,JButton deleteAristaBoton, JButton pintarAristaBoton,JButton desacerCambiosBoton,JButton aumentarCamaraBoton,JButton disminuirCamaraBoton,JButton rotarLienzoIzquierdaBoton,JButton rotarLienzoDerechaBoton,JButton arribaBoton,JButton debajoBoton,JButton izquierdaBoton,JButton derechaBoton,JButton restablecerVista){
        cancelarAlgoritmoBoton.setEnabled(false);
        ejecutarAlgoritmoBoton.setEnabled(false);
        deleteAristaBoton.setEnabled(false);
        pintarAristaBoton.setEnabled(false);
        desacerCambiosBoton.setEnabled(false);        
        aumentarCamaraBoton.setEnabled(false);
        disminuirCamaraBoton.setEnabled(false);
        rotarLienzoIzquierdaBoton.setEnabled(false);
        rotarLienzoDerechaBoton.setEnabled(false);
        arribaBoton.setEnabled(false);
        debajoBoton.setEnabled(false);
        izquierdaBoton.setEnabled(false);
        derechaBoton.setEnabled(false);                
        restablecerVista.setEnabled(false);       
    }
    
    public void solucionInicialAleatoria(Graph lienzo, Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1, ArrayList<Ciudad> listaNodos,double temperatura, double enfriamiento, int velocidad,JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto, JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto, JButton cancelarAlgoritmo,JButton dibujarRepresentacion,JButton arribaBoton, JButton derechaBoton, JButton debajoBoton,JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI,JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,ArrayList<Ciudad> exclusion,JButton deleteAristaBoton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem item_configuracion,JButton ejecutarAlgoritmoBoton,JMenuItem importarArchivo,JLabel ficticio){
        hallarSolucionRS = new HallarSolucionRS(lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,listaNodos, temperatura, enfriamiento, velocidad, cargadorAnimadoTexto, cargadorMSTexto,cargadorPSTexto,distanciaSATexto, distanciaMSTexto,distanciaPSTexto,cancelarAlgoritmo,dibujarRepresentacion,arribaBoton,derechaBoton, debajoBoton, izquierdaBoton, aumentarCamara, disminuirCamara, rotarLienzoI, rotarLienzoD, restablecerVista, timeSolution, exclusion, deleteAristaBoton, pintarAristaBoton, item_distancia, item_list, listaExclusionButton,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion,ejecutarAlgoritmoBoton,importarArchivo,ficticio);
        hallarSolucionRS.execute();
        cantidadEjecuciones = 1;        
    } 
    
    public void solucionInicialHillClimbing(Graph lienzo, Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1, ArrayList<Ciudad> listaNodos,double temperatura, double enfriamiento, int velocidad,JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto, JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto, JButton cancelarAlgoritmo,JButton dibujarRepresentacion,JButton arribaBoton, JButton derechaBoton, JButton debajoBoton,JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI,JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,ArrayList<Ciudad> exclusion,JButton deleteAristaBoton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem item_configuracion,JButton ejecutarAlgoritmoBoton,JMenuItem importarArchivo,JLabel ficticio){            
        hillClimbing = new HillClimbing(lienzo, lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1, listaNodos,temperatura, enfriamiento, velocidad,cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto, distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cancelarAlgoritmo,dibujarRepresentacion,arribaBoton,derechaBoton,debajoBoton,izquierdaBoton,aumentarCamara, disminuirCamara,rotarLienzoI,rotarLienzoD,restablecerVista,timeSolution,exclusion,deleteAristaBoton,pintarAristaBoton,item_distancia,item_list,listaExclusionButton,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion,ejecutarAlgoritmoBoton,importarArchivo,principal,this,ficticio);
        hillClimbing.execute();
    }
       
    public void solucionInicialManual(Graph lienzo, Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1, ArrayList<Ciudad> listaNodos,double temperatura, double enfriamiento, int velocidad,JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto, JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto, JButton cancelarAlgoritmo,JButton dibujarRepresentacion,JButton arribaBoton, JButton derechaBoton, JButton debajoBoton,JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI,JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,ArrayList<Ciudad> exclusion,JButton deleteAristaBoton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem item_configuracion,JMenuItem importarArchivo){
        pintarNodosManual = new PintarNodosManual(listaNodos, lienzo, lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto, cancelarAlgoritmo, cancelarAlgoritmo, pintarAristaBoton, derechaBoton, dibujarRepresentacion, velocidad, arribaBoton, derechaBoton, debajoBoton, izquierdaBoton, aumentarCamara, disminuirCamara, rotarLienzoI, rotarLienzoD, restablecerVista,item_configuracion,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,importarArchivo);
        pintarNodosManual.execute();
    }
    
    public void cancelarHilo(String representacion,JButton ejecutarAlgoritmoBoton,JButton continuarAlgoritmoBoton,JButton pintarAristaBoton,JButton deleteAristaBoton,JButton desacerCambiosBoton,JButton listaExclusionButton,JButton aumentarCamaraBoton,JButton disminuirCamaraBoton,JButton rotarLienzoIzquierdaBoton,JButton rotarLienzoDerechaBoton,JButton arribaBoton,JButton izquierdaBoton,JButton derechaBoton,JButton debajoBoton,JButton restablecerVista,JMenuItem item_ListExclusion,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse) {        
        if (representacion.equals("Aleatoria")){
               hallarSolucionRS.getPintarSolucionActual().cancel(true);
               hallarSolucionRS.getPintarMejorSolucion().cancel(true);
               hallarSolucionRS.getPintarPeorSolucion().cancel(true);
                ejecutarAlgoritmoBoton.setEnabled(false);
                continuarAlgoritmoBoton.setEnabled(false);
                pintarAristaBoton.setEnabled(false);
                deleteAristaBoton.setEnabled(false);
                desacerCambiosBoton.setEnabled(false);
                listaExclusionButton.setEnabled(false);
                aumentarCamaraBoton.setEnabled(false);
                disminuirCamaraBoton.setEnabled(false);
                rotarLienzoIzquierdaBoton.setEnabled(false);
                rotarLienzoDerechaBoton.setEnabled(false);
                arribaBoton.setEnabled(false);
                izquierdaBoton.setEnabled(false);
                derechaBoton.setEnabled(false);
                debajoBoton.setEnabled(false);
                restablecerVista.setEnabled(false);
                item_ListExclusion.setEnabled(false);
                item_ExportCurrent.setEnabled(false);
                item_ExportBest.setEnabled(false);
                item_ExportWorse.setEnabled(false);
        }
        else if (representacion.equals("Hill Climbing")){
            pintarHillClimbing.getPintarSolucionActual().cancel(true);
            pintarHillClimbing.getPintarMejorSolucion().cancel(true);
            pintarHillClimbing.getPintarPeorSolucion().cancel(true);
            ejecutarAlgoritmoBoton.setEnabled(false);
            continuarAlgoritmoBoton.setEnabled(false);
            pintarAristaBoton.setEnabled(false);
            deleteAristaBoton.setEnabled(false);
            desacerCambiosBoton.setEnabled(false);
            listaExclusionButton.setEnabled(false);
            aumentarCamaraBoton.setEnabled(false);
            disminuirCamaraBoton.setEnabled(false);
            rotarLienzoIzquierdaBoton.setEnabled(false);
            rotarLienzoDerechaBoton.setEnabled(false);
            arribaBoton.setEnabled(false);
            izquierdaBoton.setEnabled(false);
            derechaBoton.setEnabled(false);
            debajoBoton.setEnabled(false);
            restablecerVista.setEnabled(false);
            item_ListExclusion.setEnabled(false);
            item_ExportCurrent.setEnabled(false);
            item_ExportBest.setEnabled(false);
            item_ExportWorse.setEnabled(false);
        }
        else
        {
           pintarNodosManual.cancel(true);
           ejecutarAlgoritmoBoton.setEnabled(false);
           continuarAlgoritmoBoton.setEnabled(false);
           pintarAristaBoton.setEnabled(false);
           deleteAristaBoton.setEnabled(false);
           desacerCambiosBoton.setEnabled(false);
           listaExclusionButton.setEnabled(false);
           aumentarCamaraBoton.setEnabled(false);
           disminuirCamaraBoton.setEnabled(false);
           rotarLienzoIzquierdaBoton.setEnabled(false);
           rotarLienzoDerechaBoton.setEnabled(false);
           arribaBoton.setEnabled(false);
           izquierdaBoton.setEnabled(false);
           derechaBoton.setEnabled(false);
           debajoBoton.setEnabled(false);
           restablecerVista.setEnabled(false);
           item_ListExclusion.setEnabled(false);
           item_ExportCurrent.setEnabled(false);
           item_ExportBest.setEnabled(false);
           item_ExportWorse.setEnabled(false);
        }        
    }
    
    public int dibujarAristas(String nombreNodo, JButton pintarArista,ArrayList<String> listaNodosPulsados, ArrayList<String> nombreAristasAnnadidas, Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto,JMenuItem item_ListExclusion,JMenuItem item_distancia,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JButton listaExlusionButton,String the_presentacion,ArrayList<Ciudad> mylistaNodos) {
        int mycont = 0; 
        listaNodosPulsados.add(nombreNodo);
        int tamannoLNP = listaNodosPulsados.size();
        if (tamannoLNP % 2 == 0) 
        {
            Node x = lienzo.getNode(listaNodosPulsados.get(tamannoLNP - 1));
            Node x1 = lienzo.getNode(listaNodosPulsados.get(tamannoLNP - 2));
            String nombreArista = listaNodosPulsados.get(tamannoLNP - 2) + "-" + listaNodosPulsados.get(tamannoLNP - 1);
            if (listaNodosPulsados.get(tamannoLNP - 2).equals(listaNodosPulsados.get(tamannoLNP - 1))) {
                listaNodosPulsados.remove(listaNodosPulsados.get(tamannoLNP - 1));
                tamannoLNP = listaNodosPulsados.size();
                listaNodosPulsados.remove(listaNodosPulsados.get(tamannoLNP - 1));
                listaNodosPulsados.clear();
                if (error1 == null) {
                    error1 = new Error1(null, true);
                }
                error1.setMensaje("No se aceptan lazos.");//
                error1.setLocationRelativeTo(null);
                error1.setVisible(true);
            }
            else if(x.getDegree()>1||x1.getDegree()>1){
                if (error1 == null) {
                    error1 = new Error1(null, true);
                }
                error1.setMensaje("El nodo no puede visitarse más de una vez.");
                error1.setLocationRelativeTo(null);
                error1.setVisible(true);
            }
            else {
                nombreAristasAnnadidas.add(nombreArista);
                if(!the_presentacion.equals("Manual")){
                    lienzo.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));                    
                    lienzoAux2.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoMejor.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoAux.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));                
                }
                else{
                    lienzo.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));                    
                    lienzoAux2.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoMejor.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoAux.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoPeor.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoAux1.addEdge(nombreArista, listaNodosPulsados.get(tamannoLNP - 2), listaNodosPulsados.get(tamannoLNP - 1));    
                }
                if(lienzo.getEdgeCount() == lienzo.getNodeCount()){
                    solucionRepresentada = new HallarSolucionRepresentada(lienzo, mylistaNodos, distanciaSATexto,distanciaMSTexto,distanciaPSTexto,item_ListExclusion,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,listaExlusionButton);
                    solucionRepresentada.execute();    
                }
                mycont++;
            }
        }                  
        return mycont;
    }
    
    public int deleteAristaBy2Node(String nombreNode,Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,  ArrayList<String> listaNodosPulsados){        
        int cont=0;      
        listaNodosPulsados.add(nombreNode);
        int tamannoLNP = listaNodosPulsados.size();
        if (tamannoLNP % 2 == 0) {
            if (listaNodosPulsados.get(tamannoLNP - 2).equals(listaNodosPulsados.get(tamannoLNP - 1))) 
            {
                listaNodosPulsados.clear();
                if (error1 == null) {
                    error1 = new Error1(null, true);
                }
                error1.setMensaje("No se aceptan lazos.");
                error1.setLocationRelativeTo(null);
                error1.setVisible(true);
            }
            else
            {   
                String arista = lienzo.getNode(listaNodosPulsados.get(tamannoLNP - 2))+"-"+lienzo.getNode(listaNodosPulsados.get(tamannoLNP - 1));
                try{
                    lienzo.removeEdge(listaNodosPulsados.get(tamannoLNP - 2),listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoAux2.removeEdge(listaNodosPulsados.get(tamannoLNP - 2),listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoMejor.removeEdge(listaNodosPulsados.get(tamannoLNP - 2),listaNodosPulsados.get(tamannoLNP - 1));
                    lienzoAux.removeEdge(listaNodosPulsados.get(tamannoLNP - 2),listaNodosPulsados.get(tamannoLNP - 1));   
                    cont++;
                }
                catch (Exception e) {
                    if (error1 == null) {
                    error1 = new Error1(null, true);
                    }
                    error1.setMensaje("La arista "+ arista +" no existe.");//No se aceptan lazos.
                    error1.setLocationRelativeTo(null);
                    error1.setVisible(true);
                }                        
            }
        }           
        return cont;        
    }
    
    public void desacerCambios(ArrayList<String> nombreAristasAnnadidas, Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,ArrayList<String> listaNodosPulsados,JButton desacerCambiosBoton) {
        if(!nombreAristasAnnadidas.isEmpty()){
            int tamanoNAA = nombreAristasAnnadidas.size();
            lienzo.removeEdge(nombreAristasAnnadidas.get(tamanoNAA - 1));            
            lienzoAux2.removeEdge(nombreAristasAnnadidas.get(tamanoNAA - 1));
            lienzoMejor.removeEdge(nombreAristasAnnadidas.get(tamanoNAA - 1));
            lienzoAux.removeEdge(nombreAristasAnnadidas.get(tamanoNAA - 1));            
            nombreAristasAnnadidas.remove(tamanoNAA - 1);
        }
        else{
            desacerCambiosBoton.setEnabled(false);
            if (error1 == null) {
                error1 = new Error1(null, true);
            }
            error1.setMensaje("Últimos cambios han sido desechos");//.
            error1.setLocationRelativeTo(null);
            error1.setVisible(true);
            return;
        }
    }
    
    public void gestionarRSSIC(String representacion,Graph lienzo,Graph lienzoAux2, Graph lienzoMejor, Graph lienzoPeor,Graph lienzoAux,Graph lienzoAux1,JMenuItem item_ImportFile, JMenuItem item_list,JMenuItem item_distancia,JMenuItem item_ExportCurrent, JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JButton dibujarRepresentacion,JButton ejecutarAlgoritmoBoton,JButton cancelarAlgoritmoBoton,JButton continuar,JButton pintarAristaButton,JButton deleteAristaBoton,JButton desacerCambiosBoton,JButton listaExclusionButton,JButton aumentarCamara,JButton disminuirCamara,JButton rotarLienzoI,JButton rotarLienzoD,JButton arribaBoton,JButton izquierdaBoton,JButton derechaBoton, JButton debajoBoton,JButton restablecerVista,JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto,JLabel cargadorAnimadoTexto,JLabel cargadorMS, JLabel cargadorPS,double temperatura, double enfriamiento,int totalIteraciones,int velocidad,ArrayList<Ciudad> exclusion,JLabel iteracionTexto,JLabel ficticio,JLabel timeSolution,boolean variable,ArrayList<Ciudad> listaNodosMarcados) {       
        timeStop=Integer.parseInt(ficticio.getText());
        if(representacion.equals("Aleatoria") && variable == false){
            goodTour = hallarSolucionRS.getTourActual();
        }
        else if(representacion.equals("Hill Climbing") && variable == false)
        {
             goodTour = pintarHillClimbing.getTourActual();
        }
        else{
            goodTour = solucionRepresentada.getTourActual();
        }
        aplicarRSMS = new AplicarRSMS(lienzo,lienzoAux2,lienzoMejor,lienzoPeor,lienzoAux,lienzoAux1,item_ImportFile,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,pintarAristaButton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton, aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto,cargadorMS,cargadorPS,temperatura,enfriamiento,totalIteraciones,velocidad,exclusion,iteracionTexto,ficticio,timeSolution,
                                goodTour,timeStop,listaNodosMarcados);
        aplicarRSMS.execute();        
    }

    public void cancelarHiloejecucion() {
        aplicarRSMS.cancel(true);
    }
    
    public void reempezar(String representacion,Graph lienzo,Graph lienzoAux2, Graph lienzoMejor, Graph lienzoPeor,Graph lienzoAux,Graph lienzoAux1,JMenuItem item_ImportFile, JMenuItem item_list,JMenuItem item_distancia,JMenuItem item_ExportCurrent, JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JButton dibujarRepresentacion,JButton ejecutarAlgoritmoBoton,JButton cancelarAlgoritmoBoton,JButton continuar,JButton pintarAristaButton,JButton deleteAristaBoton,JButton desacerCambiosBoton,JButton listaExclusionButton,JButton aumentarCamara,JButton disminuirCamara,JButton rotarLienzoI,JButton rotarLienzoD,JButton arribaBoton,JButton izquierdaBoton,JButton derechaBoton, JButton debajoBoton,JButton restablecerVista,JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto,JLabel cargadorAnimadoTexto,JLabel cargadorMS, JLabel cargadorPS,double temperatura, double enfriamiento,int totalIteraciones,int velocidad,ArrayList<Ciudad> exclusion,JLabel iteracionTexto,JLabel ficticio,JLabel timeSolution,int tiempoLabel,boolean variable,ArrayList<Ciudad> listaNodosMarcados) {
        if (aplicarRSMS.isCancelled()){   
            cancelarAlgoritmoBoton.setEnabled(true);
            limpiarLienzo(lienzo);
            limpiarLienzo(lienzoAux2);
            limpiarLienzo(lienzoMejor);
            limpiarLienzo(lienzoAux);
            limpiarLienzo(lienzoPeor);
            limpiarLienzo(lienzoAux1);
            int cantidadIAjustadas = aplicarRSMS.getTotalIteraciones() - totalIteraciones;
            timeStop = tiempoLabel;
        if(representacion.equals("Aleatoria") && variable == false){
            aplicarRSMS = new AplicarRSMS(lienzo,lienzoAux2,lienzoMejor,lienzoPeor,lienzoAux,lienzoAux1,item_ImportFile,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,pintarAristaButton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton, aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto,cargadorMS,cargadorPS,temperatura,enfriamiento,cantidadIAjustadas,velocidad,exclusion,iteracionTexto,ficticio,timeSolution,
                                aplicarRSMS.getTourMejor(),timeStop,listaNodosMarcados);
            aplicarRSMS.execute();                      
        }
        else if(representacion.equals("Hill Climbing") && variable == false)
        {
             aplicarRSMS = new AplicarRSMS(lienzo,lienzoAux2,lienzoMejor,lienzoPeor,lienzoAux,lienzoAux1,item_ImportFile,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,pintarAristaButton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton, aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto,cargadorMS,cargadorPS,temperatura,enfriamiento,cantidadIAjustadas,velocidad,exclusion,iteracionTexto,ficticio,timeSolution,
                                aplicarRSMS.getTourMejor(),timeStop,listaNodosMarcados);
            aplicarRSMS.execute();
        }
        else{
            aplicarRSMS = new AplicarRSMS(lienzo,lienzoAux2,lienzoMejor,lienzoPeor,lienzoAux,lienzoAux1,item_ImportFile,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,pintarAristaButton,deleteAristaBoton,desacerCambiosBoton,listaExclusionButton, aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto,cargadorMS,cargadorPS,temperatura,enfriamiento,cantidadIAjustadas,velocidad,exclusion,iteracionTexto,ficticio,timeSolution,
                                solucionRepresentada.getTourActual(),timeStop,listaNodosMarcados);
            aplicarRSMS.execute();
        }
            
            
            
        }
        else{
            error1 = new Error1(null, true);
            error1.setMensaje("Todas las iteraciones han sido realizadas.");
            error1.setLocationRelativeTo(null);
            error1.setVisible(true);
        }
    }    
    
}
