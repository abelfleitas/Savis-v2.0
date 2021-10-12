package savis.servicios;

import savis.RS.Tour;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class PintarPSParar extends SwingWorker<Integer, Void> {

    private Tour solucionActual;
    private Graph lienzoPeor,lienzoAux1;
    private int velocidad,tamannoSolucionActual = 0;
    private JLabel cargadorPSTexto;
    private JMenuItem item_ExportWorse;
    
    public PintarPSParar() {
    }

    public PintarPSParar(Tour solucionActual, Graph lienzoPeor,Graph lienzoAux1, int velocidad, JLabel cargadorPSTexto,JMenuItem item_ExportWorse) {
        this.solucionActual = solucionActual;
        this.lienzoPeor = lienzoPeor;        
        this.lienzoAux1 = lienzoAux1;       
        this.velocidad = velocidad;
        this.cargadorPSTexto = cargadorPSTexto;
        this.item_ExportWorse = item_ExportWorse;
    }

    // Método que cancela la tarea.
    private void cancelarTarea() {
        this.cancel(true);
    }
    
    @Override
    protected Integer doInBackground() throws Exception {
        // Establecemos el estilo de nuestro lienzo.
        //System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        lienzoPeor.addAttribute("ui.quality");
        lienzoPeor.addAttribute("ui.antialias", true);
        lienzoPeor.addAttribute("ui.stylesheet", estiloLienzoPeor);
        
        lienzoAux1.addAttribute("ui.quality");
        lienzoAux1.addAttribute("ui.antialias", true);
        lienzoAux1.addAttribute("ui.stylesheet", estiloLienzoAux);
        tamannoSolucionActual = solucionActual.tourTamanno();
        // Estructura donde se guarda el nodo(Ciudad).
        Node ciudad,ciudad1;
        String nombreCiudad;
        double coordenadaX;
        double coordenadaY;
        // Se muestra la solucion actual en el lienzo.
        for (int i = 0; i < tamannoSolucionActual; i++) {
            // Guardamos los datos de la ciudad(nombre, coordenadas).
            nombreCiudad = String.valueOf(solucionActual.getCiudad(i).getNombreId());
            coordenadaX = solucionActual.getCiudad(i).getCoordenadaX();
            coordenadaY = solucionActual.getCiudad(i).getCoordenadaY();
            //System.out.println(nombreCiudad);
            // Creamos el lienzo con el nombre de la ciudad actual.
            ciudad = lienzoPeor.addNode(nombreCiudad);
            ciudad.addAttribute("label", nombreCiudad);
            ciudad.addAttribute("ui.size", 4);
            ciudad.addAttribute("xy", coordenadaX, coordenadaY);

            ciudad1 = lienzoAux1.addNode(nombreCiudad);
            ciudad1.addAttribute("label", nombreCiudad);
            ciudad1.addAttribute("ui.size", 4);
            ciudad1.addAttribute("xy", coordenadaX, coordenadaY);
            
            if (i + 1 == tamannoSolucionActual) {
                //graph.addEdge("AB", "A", "B");
                int cont = 1;
                for (int j = 0; j < solucionActual.tourTamanno(); j++) {
                    String ciudadActual1 = String.valueOf(solucionActual.getCiudad(j).getNombreId());
                    String ciudadSiguiente1 = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                    this.lienzoPeor.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    this.lienzoAux1.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);

                    cont++;
                    if (j == solucionActual.tourTamanno() - 2) {
                        String ciudadUltima = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                        //lienzo.addAttribute("edge {fill-color:orange;}");
                        this.lienzoPeor.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()), true);
                        this.lienzoAux1.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()), true);
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
        this.cargadorPSTexto.setVisible(false);
        this.item_ExportWorse.setEnabled(true);
    }
   
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
            + "fill-color: #666666;}"

            + "node:clicked{"
            + "size: 7; "
            + "fill-color: #EE5413;"
            + "}"

            + "node:selected{"
            + "size: 7; "
            + "fill-color: #EE5413;"
            + "}";
     
     private String estiloLienzoAux = ""
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
}
