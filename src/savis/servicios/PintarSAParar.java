package savis.servicios;

import savis.RS.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import savis.utiles.Time;

public class PintarSAParar extends SwingWorker<Integer, Void> {

    private Tour solucionActual;
    private Graph lienzo,lienzoAux2;
    private int velocidad,tamannoSolucionActual = 0;
    private JLabel cargadorAnimadoTexto,distanciaSATexto;
    private JMenuItem item_ExportCurrent,item_list,item_ImportarFile;
    private JButton dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,deleteAristaBoton,pintarAristaBoton,listaExclusionButton,
            aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista;

    public PintarSAParar() {
    }

    public PintarSAParar(Tour solucionActual, Graph lienzo,Graph lienzoAux2, int velocidad, JLabel cargadorAnimadoTexto,JMenuItem item_ExportCurrent,JMenuItem item_ImportarFile,JButton dibujarRepresentacion,JButton ejecutarAlgoritmoBoton,JButton cancelarAlgoritmoBoton,JButton continuar,JButton deleteAristaBoton,JButton pintarAristaBoton,JButton listaExclusionButton,JMenuItem item_list,JButton aumentarCamara,JButton disminuirCamara,JButton rotarLienzoI,JButton rotarLienzoD,JButton arribaBoton,JButton izquierdaBoton,JButton derechaBoton,JButton debajoBoton,JButton restablecerVista,JLabel distanciaSATexto) {
        this.solucionActual = solucionActual;
        this.lienzo = lienzo;
        this.lienzoAux2 = lienzoAux2;
        this.velocidad = velocidad;
        this.cargadorAnimadoTexto = cargadorAnimadoTexto;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_ImportarFile = item_ImportarFile;
        this.dibujarRepresentacion = dibujarRepresentacion;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
        this.cancelarAlgoritmoBoton = cancelarAlgoritmoBoton;
        this.continuar = continuar;
        this.deleteAristaBoton = deleteAristaBoton;
        this.pintarAristaBoton = pintarAristaBoton;
        this.listaExclusionButton = listaExclusionButton;
        this.item_list = item_list;
        this.aumentarCamara = aumentarCamara;
        this.disminuirCamara = disminuirCamara;
        this.rotarLienzoI = rotarLienzoI;
        this.rotarLienzoD = rotarLienzoD;
        this.arribaBoton = arribaBoton;
        this.izquierdaBoton = izquierdaBoton;
        this.derechaBoton = derechaBoton;
        this.debajoBoton = debajoBoton;
        this.restablecerVista = restablecerVista;
        this.distanciaSATexto = distanciaSATexto;
    }

    // Método que cancela la tarea.
    private void cancelarTarea() {
        this.cancel(true);
    }
    
    @Override
    protected Integer doInBackground() throws Exception {
        // Establecemos el estilo de nuestro lienzo.
        //System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        lienzo.addAttribute("ui.quality");
        lienzo.addAttribute("ui.antialias", true);
        lienzo.addAttribute("ui.stylesheet", estiloLienzo);
        
        lienzoAux2.addAttribute("ui.quality");
        lienzoAux2.addAttribute("ui.antialias", true);
        lienzoAux2.addAttribute("ui.stylesheet", estiloLienzo);
        
        tamannoSolucionActual = solucionActual.tourTamanno();
        // Estructura donde se guarda el nodo(Ciudad).
        Node ciudad;
        Node ciudad1;
        String nombreCiudad;
        double coordenadaX;
        double coordenadaY;
        // Se muestra la solucion actual en el lienzo.
        for (int i = 0; i < tamannoSolucionActual; i++) {
            // Guardamos los datos de la ciudad(nombre, coordenadas).
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
                //graph.addEdge("AB", "A", "B");
                int cont = 1;
                for (int j = 0; j < solucionActual.tourTamanno(); j++) {
                    String ciudadActual1 = String.valueOf(solucionActual.getCiudad(j).getNombreId());
                    String ciudadSiguiente1 = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                    this.lienzo.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    this.lienzoAux2.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    cont++;
                    if (j == solucionActual.tourTamanno() - 2) {
                        String ciudadUltima = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                        //lienzo.addAttribute("edge {fill-color:orange;}");
                        this.lienzo.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()), true);
                        this.lienzoAux2.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()), true);
                        cont++;
                    }
                    Thread.sleep(this.velocidad);
                }
            }
            // Paramos el hilo un tiempo corto.
            Thread.sleep(this.velocidad);
        }
        return 0;
    }

    @Override
    protected void done() {
        cargadorAnimadoTexto.setVisible(false);
        this.item_ImportarFile.setEnabled(true);
        this.item_list.setEnabled(true);
        this.item_ExportCurrent.setEnabled(true);
        this.dibujarRepresentacion.setEnabled(true);
        this.ejecutarAlgoritmoBoton.setEnabled(true);
        this.cancelarAlgoritmoBoton.setEnabled(false);
        this.continuar.setEnabled(true);
        this.deleteAristaBoton.setEnabled(true);
        this.pintarAristaBoton.setEnabled(false);    
        this.listaExclusionButton.setEnabled(true);        
        this.aumentarCamara.setEnabled(true);
        this.disminuirCamara.setEnabled(true);
        this.rotarLienzoI.setEnabled(true);
        this.rotarLienzoD.setEnabled(true);
        this.arribaBoton.setEnabled(true);
        this.izquierdaBoton.setEnabled(true);
        this.derechaBoton.setEnabled(true);
        this.debajoBoton.setEnabled(true);
        this.restablecerVista.setEnabled(true);
        //this.distanciaSATexto.setText(String.valueOf(solucionActual.getDistancia()));
       
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
