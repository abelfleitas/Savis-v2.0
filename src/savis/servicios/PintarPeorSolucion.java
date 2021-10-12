package savis.servicios;

import savis.RS.Tour;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class PintarPeorSolucion extends SwingWorker<Integer, Void> {

    private Tour solucionActual;
    private Graph lienzoPeor,lienzoAux1;
    private int velocidad,tamannoSolucionActual = 0;
    private JLabel distanciaPSTexto,cargadorPSTexto;
    private JMenuItem item_ExportWorse;


    public PintarPeorSolucion(Tour solucionActual, Graph lienzoPeor,Graph lienzoAux1, int velocidad, JLabel cargadorPSTexto, JLabel distanciaPSTexto,JMenuItem item_ExportWorse) {
        this.solucionActual = solucionActual;
        this.lienzoPeor = lienzoPeor;
        this.lienzoAux1 = lienzoAux1;
        this.velocidad = velocidad;
        this.distanciaPSTexto= distanciaPSTexto;
        this.cargadorPSTexto = cargadorPSTexto;
        this.item_ExportWorse = item_ExportWorse;
    }    

    // Método que cancela la tarea.
    private void cancelarTarea() {
        this.cancel(true);
    }

    @Override
    protected Integer doInBackground() throws Exception {
        lienzoPeor.addAttribute("ui.quality");
        lienzoPeor.addAttribute("ui.antialias", true);
        lienzoPeor.addAttribute("ui.stylesheet", estiloLienzoPeor);   
        
        lienzoAux1.addAttribute("ui.quality");
        lienzoAux1.addAttribute("ui.antialias", true);
        lienzoAux1.addAttribute("ui.stylesheet", estiloLienzoAux1);        
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
            
            ciudad = lienzoPeor.addNode(nombreCiudad);            
            ciudad.addAttribute("label", nombreCiudad);           
            ciudad.addAttribute("ui.size", 7);            
            ciudad.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad1 = lienzoAux1.addNode(nombreCiudad);            
            ciudad1.addAttribute("label", nombreCiudad);           
            ciudad1.addAttribute("ui.size", 7);            
            ciudad1.addAttribute("xy", coordenadaX, coordenadaY);
            
            if (i + 1 == tamannoSolucionActual) {
                int cont = 1;
                for (int j = 0; j < solucionActual.tourTamanno(); j++) {
                    String ciudadActual1 = String.valueOf(solucionActual.getCiudad(j).getNombreId());
                    String ciudadSiguiente1 = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                    this.lienzoPeor.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    
                    this.lienzoAux1.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    cont++;                    
                    if (j == solucionActual.tourTamanno() - 2) {
                        String ciudadUltima = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                        
                        this.lienzoPeor.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()),true);
                        
                        this.lienzoAux1.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()),true);

                        cont++;
                    }
                    Thread.sleep(this.velocidad);
                }
            }
            Thread.sleep(this.velocidad);
        }
        return 0;
    }

    @Override
    protected void done() {
        cargadorPSTexto.setVisible(false);
        distanciaPSTexto.setText(String.valueOf(solucionActual.getDistancia()));
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
    
     
}
