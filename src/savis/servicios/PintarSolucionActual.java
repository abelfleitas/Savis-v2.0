package savis.servicios;

import savis.RS.Tour;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.List;
import savis.utiles.Time;


public class PintarSolucionActual extends SwingWorker<Integer, Integer>{ 
    
    private Tour solucionActual;
    private Graph lienzo,lienzoAux2;
    private int velocidad,tamannoSolucionActual = 0;
    private JLabel distanciaSATexto,cargadorAnimadoTexto,timeSolution,ficticio;
    private JButton cancelarAlgoritmo,dibujarRepresentacion,arribaBoton,derechaBoton,debajoBoton,izquierdaBoton,
            aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,restablecerVista,deleteAristaButton,pintarAristaBoton,listaExclusionButton,ejecutarAlgoritmoBoton;
    private JMenuItem importarArchivo,item_distancia,item_list,item_ExportCurrent,item_configuracion;
    private Time time;
    
    

    public PintarSolucionActual(Tour solucionActual, Graph lienzo,Graph lienzoAux2, int velocidad, JLabel cargadorAnimadoTexto, JLabel distanciaSATexto, JButton cancelarAlgoritmo, JButton dibujarRepresentacion, JButton arribaBoton, JButton derechaBoton, JButton debajoBoton, JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI, JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,JButton deleteAristaButton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_configuracion,JButton ejecutarAlgoritmoBoton,JMenuItem importarArchivo,JLabel ficticio){
        time = new Time();
        time.Contar();  
        this.pintarAristaBoton = pintarAristaBoton;
        this.solucionActual = solucionActual;
        this.lienzo = lienzo;
        this.lienzoAux2 = lienzoAux2;
        this.velocidad = velocidad;
        this.cargadorAnimadoTexto = cargadorAnimadoTexto;
        this.distanciaSATexto = distanciaSATexto;
        this.cancelarAlgoritmo = cancelarAlgoritmo;
        this.dibujarRepresentacion = dibujarRepresentacion;
        this.arribaBoton = arribaBoton;
        this.derechaBoton = derechaBoton;
        this.debajoBoton = debajoBoton;
        this.izquierdaBoton = izquierdaBoton;
        this.aumentarCamara = aumentarCamara;
        this.disminuirCamara = disminuirCamara;
        this.rotarLienzoI = rotarLienzoI;
        this.rotarLienzoD = rotarLienzoD;
        this.restablecerVista = restablecerVista;
        this.timeSolution = timeSolution;
        this.deleteAristaButton = deleteAristaButton;
        this.importarArchivo =importarArchivo;
        this.item_distancia = item_distancia;
        this.item_list = item_list;
        this.listaExclusionButton = listaExclusionButton;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_configuracion = item_configuracion;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
        this.ficticio=ficticio;
    }

    public Graph getLienzo() {
        return lienzo;
    }

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
        lienzoAux2.addAttribute("ui.stylesheet", estiloLienzo);        
        tamannoSolucionActual = solucionActual.tourTamanno();                
        Node ciudad;
        Node ciudad1;        
        String nombreCiudad;
        double coordenadaX;
        double coordenadaY;
        for (int i = 0; i < tamannoSolucionActual; i++) {
            
            nombreCiudad = String.valueOf(solucionActual.getCiudad(i).getNombreId());
            coordenadaX = solucionActual.getCiudad(i).getCoordenadaX();
            coordenadaY = solucionActual.getCiudad(i).getCoordenadaY();
            
            ciudad = lienzo.addNode(nombreCiudad);            
            ciudad.addAttribute("label", nombreCiudad);           
            ciudad.addAttribute("ui.size", 7);            
            ciudad.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad1 = lienzoAux2.addNode(nombreCiudad);            
            ciudad1.addAttribute("label", nombreCiudad);           
            ciudad1.addAttribute("ui.size", 7);            
            ciudad1.addAttribute("xy", coordenadaX, coordenadaY);
            
            if (i + 1 == tamannoSolucionActual) {
                int cont = 1;
                for (int j = 0; j < solucionActual.tourTamanno(); j++) {
                    String ciudadActual1 = String.valueOf(solucionActual.getCiudad(j).getNombreId());
                    String ciudadSiguiente1 = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                    this.lienzo.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    
                    this.lienzoAux2.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    cont++;                    
                    if (j == solucionActual.tourTamanno() - 2) {
                        String ciudadUltima = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                        
                        this.lienzo.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()),true);
                        
                        this.lienzoAux2.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()),true);

                        cont++;
                    }                     
                    Thread.sleep(this.velocidad);
                    publish(time.getSegundos());
                }
            }           
            Thread.sleep(this.velocidad);
            publish(time.getSegundos());
        }
        
        return 0;
    }

    @Override
    protected void process(List<Integer> listado) {
        timeSolution.setText(String.valueOf(listado.get(listado.size()-1))+"s");
    }
    
    @Override
    protected void done() {
        super.done();
        this.cancelarAlgoritmo.setEnabled(false);
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
        cargadorAnimadoTexto.setVisible(false);
        distanciaSATexto.setText(String.valueOf(solucionActual.getDistancia()));
        this.deleteAristaButton.setEnabled(true);
        this.pintarAristaBoton.setEnabled(false);
        this.importarArchivo.setEnabled(true);
        this.item_configuracion.setEnabled(true);
        this.item_list.setEnabled(true);
        this.item_distancia.setEnabled(true);
        this.listaExclusionButton.setEnabled(true);
        this.item_ExportCurrent.setEnabled(true);
        this.ejecutarAlgoritmoBoton.setEnabled(true);
        time.Detener();
        ficticio.setText(""+time.getSegundos());
    }
    
    
    
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
}
