package savis.servicios;

import savis.RS.Tour;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class PintarMejorSolucion extends SwingWorker<Integer, Void> {

    private Tour solucionActual;
    private Graph lienzoMejor,lienzoAux;
    private int velocidad,tamannoSolucionActual = 0;
    private JLabel distanciaMSTexto,cargadorMSTexto;
    private JMenuItem item_ExportBest;

    public PintarMejorSolucion(Tour solucionActual, Graph lienzoMejor,Graph lienzoAux, int velocidad, JLabel cargadorMSTexto, JLabel distanciaMSTexto,JMenuItem item_ExportBest) {
        this.solucionActual = solucionActual;
        this.lienzoMejor = lienzoMejor;
        this.lienzoAux = lienzoAux;
        this.velocidad = velocidad;
        this.distanciaMSTexto=distanciaMSTexto;
        this.cargadorMSTexto = cargadorMSTexto;
        this.item_ExportBest = item_ExportBest;
    }    

    private void cancelarTarea() {
        this.cancel(true);
    }

    @Override
    protected Integer doInBackground() throws Exception {
        lienzoMejor.addAttribute("ui.quality");
        lienzoMejor.addAttribute("ui.antialias", true);
        lienzoMejor.addAttribute("ui.stylesheet", estiloLienzoMejor);   
        
        lienzoAux.addAttribute("ui.quality");
        lienzoAux.addAttribute("ui.antialias", true);
        lienzoAux.addAttribute("ui.stylesheet", estiloLienzoAux);        
        
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
            
            ciudad = lienzoMejor.addNode(nombreCiudad);            
            ciudad.addAttribute("label", nombreCiudad);           
            ciudad.addAttribute("ui.size", 7);            
            ciudad.addAttribute("xy", coordenadaX, coordenadaY);
            
            ciudad1 = lienzoAux.addNode(nombreCiudad);            
            ciudad1.addAttribute("label", nombreCiudad);           
            ciudad1.addAttribute("ui.size", 7);            
            ciudad1.addAttribute("xy", coordenadaX, coordenadaY);
            
            if (i + 1 == tamannoSolucionActual) {
                int cont = 1;
                for (int j = 0; j < solucionActual.tourTamanno(); j++) {
                    String ciudadActual1 = String.valueOf(solucionActual.getCiudad(j).getNombreId());
                    String ciudadSiguiente1 = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                    this.lienzoMejor.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    
                    this.lienzoAux.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    cont++;                    
                    if (j == solucionActual.tourTamanno() - 2) {
                        String ciudadUltima = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                        
                        this.lienzoMejor.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()),true);
                        
                        this.lienzoAux.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()),true);

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
        cargadorMSTexto.setVisible(false);
        distanciaMSTexto.setText(String.valueOf(solucionActual.getDistancia()));  
        this.item_ExportBest.setEnabled(true);
    }
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
