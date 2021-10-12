package savis.servicios;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import savis.RS.Ciudad;

public class PintarNodosManual extends SwingWorker<Integer, Void> {

    private ArrayList<Ciudad> listaNodos;
    private Graph lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1;
    private int tamannoListaNodos = 0;
    private JLabel cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto;
    private JButton cancelarAlgoritmo;
    private JButton ejecutarAlgoritmo;
    private JButton pintarArista;
    private JButton desacerCambios;
    private JButton dibujarRepresentacion;
    private JButton arribaBoton;
    private JButton derechaBoton;
    private JButton debajoBoton;
    private JButton izquierdaBoton;
    private JButton aumentarCamara;
    private JButton disminuirCamara;
    private JButton rotarLienzoI;
    private JButton rotarLienzoD;
    private JButton restablecerVista;
    private JMenuItem importarArchivo,item_configuracion,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse;
    private int velocidad;

    public PintarNodosManual(ArrayList<Ciudad> listaNodos, Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto, JButton cancelarAlgoritmo, JButton ejecutarAlgoritmo, JButton pintarArista, JButton desacerCambios, JButton dibujarRepresentacion, int velocidad, JButton arribaBoton, JButton derechaBoton, JButton debajoBoton, JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI, JButton rotarLienzoD, JButton restablecerVista,JMenuItem item_configuracion,JMenuItem item_list,JMenuItem item_distancia,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem importarArchivo) {
        this.listaNodos = listaNodos;
        this.lienzo = lienzo;
        this.lienzoAux2 = lienzoAux2;        
        this.lienzoMejor = lienzoMejor;
        this.lienzoAux = lienzoAux;        
        this.lienzoPeor = lienzoPeor;
        this.lienzoAux1 = lienzoAux1;        
        this.cargadorAnimadoTexto = cargadorAnimadoTexto;
        this.cargadorMSTexto = cargadorMSTexto;
        this.cargadorPSTexto = cargadorPSTexto;
        this.cancelarAlgoritmo = cancelarAlgoritmo;
        this.ejecutarAlgoritmo = ejecutarAlgoritmo;
        this.pintarArista = pintarArista;
        this.desacerCambios = desacerCambios;
        this.dibujarRepresentacion = dibujarRepresentacion;
        this.velocidad = velocidad;
        this.arribaBoton = arribaBoton;
        this.derechaBoton = derechaBoton;
        this.debajoBoton = debajoBoton;
        this.izquierdaBoton = izquierdaBoton;
        this.aumentarCamara = aumentarCamara;
        this.disminuirCamara = disminuirCamara;
        this.rotarLienzoI = rotarLienzoI;
        this.rotarLienzoD = rotarLienzoD;
        this.restablecerVista = restablecerVista;
        this.importarArchivo = importarArchivo;
        this.item_configuracion = item_configuracion;
        this.item_list = item_list;
        this.item_distancia = item_distancia;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_ExportBest = item_ExportBest;
        this.item_ExportWorse = item_ExportWorse;
    }

    // Método que cancela la tarea.
    private void cancelarTarea() {
        this.cancel(true);
    }

    @Override
    protected Integer doInBackground() throws Exception {
        lienzo.addAttribute("ui.quality");
        lienzo.addAttribute("ui.antialias", true);
        lienzo.addAttribute("ui.stylesheet", estiloLienzo);
        
        lienzoAux2.addAttribute("ui.quality");
        lienzoAux2.addAttribute("ui.antialias", true);
        lienzoAux2.addAttribute("ui.stylesheet", estiloLienzoAux2);
               
        lienzoMejor.addAttribute("ui.quality");
        lienzoMejor.addAttribute("ui.antialias", true);
        lienzoMejor.addAttribute("ui.stylesheet", estiloLienzoMejor);
        
        lienzoAux.addAttribute("ui.quality");
        lienzoAux.addAttribute("ui.antialias", true);
        lienzoAux.addAttribute("ui.stylesheet", estiloLienzoAux);
        
        lienzoPeor.addAttribute("ui.quality");
        lienzoPeor.addAttribute("ui.antialias", true);
        lienzoPeor.addAttribute("ui.stylesheet", estiloLienzoPeor);
        
        lienzoAux1.addAttribute("ui.quality");
        lienzoAux1.addAttribute("ui.antialias", true);
        lienzoAux1.addAttribute("ui.stylesheet", estiloLienzoAux1);
        
        tamannoListaNodos = listaNodos.size();
        // Estructura donde se guarda el nodo(Ciudad).
        Node ciudad,ciudad1,ciudad2,ciudad3,ciudad4,ciudad5;        
        String nombreCiudad;
        double coordenadaX;
        double coordenadaY;
        for (int i = 0; i < tamannoListaNodos; i++) {
            nombreCiudad = String.valueOf(listaNodos.get(i).getNombreId());
            coordenadaX = listaNodos.get(i).getCoordenadaX();
            coordenadaY = listaNodos.get(i).getCoordenadaY();
            
            ciudad = lienzo.addNode(nombreCiudad);           
            ciudad.addAttribute("label", nombreCiudad);            
            ciudad.addAttribute("ui.size", 7);
            ciudad.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad1 = lienzoAux2.addNode(nombreCiudad);           
            ciudad1.addAttribute("label", nombreCiudad);            
            ciudad1.addAttribute("ui.size", 7);
            ciudad1.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad2 = lienzoMejor.addNode(nombreCiudad);           
            ciudad2.addAttribute("label", nombreCiudad);            
            ciudad2.addAttribute("ui.size", 7);
            ciudad2.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad3 = lienzoAux.addNode(nombreCiudad);           
            ciudad3.addAttribute("label", nombreCiudad);            
            ciudad3.addAttribute("ui.size", 7);
            ciudad3.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad4 = lienzoPeor.addNode(nombreCiudad);           
            ciudad4.addAttribute("label", nombreCiudad);            
            ciudad4.addAttribute("ui.size", 7);
            ciudad4.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad5 = lienzoAux1.addNode(nombreCiudad);           
            ciudad5.addAttribute("label", nombreCiudad);            
            ciudad5.addAttribute("ui.size", 7);
            ciudad5.addAttribute("xy", coordenadaX, coordenadaY);
            
            Thread.sleep(velocidad);
        }
        return 0;
    }

    @Override
    protected void done() {
        this.cargadorAnimadoTexto.setVisible(false);
        this.cargadorMSTexto.setVisible(false);
        this.cargadorPSTexto.setVisible(false);
        this.cancelarAlgoritmo.setEnabled(false);
        this.pintarArista.setEnabled(true);
        this.dibujarRepresentacion.setEnabled(true);
        this.arribaBoton.setEnabled(true);
        this.derechaBoton.setEnabled(true);
        this.debajoBoton.setEnabled(true);
        this.izquierdaBoton.setEnabled(true);
        this.aumentarCamara.setEnabled(true);
        this.disminuirCamara.setEnabled(true);
        this.rotarLienzoI.setEnabled(true);
        this.rotarLienzoD.setEnabled(true);
        this.restablecerVista.setEnabled(true);
        this.importarArchivo.setEnabled(true);
        this.item_distancia.setEnabled(true);
        this.item_configuracion.setEnabled(true);
    }
    
    
    private String estiloLienzo = ""
            + "node {"
            + "fill-color: #10C4B5;"
            + "size-mode: fit;"
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
      
            
            + "node:clicked{"
            + "fill-color: #7110C4;"
            + "}"
            
            + "node:selected{"
            + "fill-color: #7110C4;"
            + "}";  
    
    private String estiloLienzoAux2 = ""
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
    
    private String estiloLienzoPeor = ""
            + "node {"
            + "fill-color: #F0B914;"
            + "text-color: black;"
            + "size: 10px, 10px;"
            + "text-background-mode: plain;"
            + "text-background-color: blue;"
            + "text-alignment:center;"
            + "text-style:bold;"
            + "}"
            
            + "edge {"
            + "shape: line;"
            + "size: 0.8;"
            + "arrow-size: 5px,4px;"
            + "fill-color: #666666;"
            + "}";
    
    private String estiloLienzoAux1 = ""
            + "node {"
            + "fill-color: #F0B914;"
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
    
    private String estiloLienzoMejor = ""
            + "node {"
            + "fill-color: #CA109B;"
            + "text-color: black;"
            + "size: 10px, 10px;"
            + "text-background-mode: plain;"
            + "text-background-color: blue;"
            + "text-alignment:center;"
            + "text-style:bold;"
            + "}"
            
            + "edge {"
            + "shape: line;"
            + "size: 0.8;"
            + "arrow-size: 5px,4px;"
            + "fill-color: #666666;"
            + "}";
    
    
    
    private String estiloLienzoAux = ""
            + "node {"
            + "fill-color: #CA109B;"
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
}
