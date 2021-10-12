package savis.servicios;

import savis.RS.Tour;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import savis.utiles.Time;

public class PintarMSParar extends SwingWorker<Integer, Void> {

   

    private Tour solucionActual;
    private Graph lienzoMejor,lienzoAux;        
    private int velocidad,tamannoSolucionActual = 0;
    private JLabel cargadorMSTexto,timeSolution,ficticio;
    private JMenuItem item_ExportBest;
    //private Time time;   

   public PintarMSParar(){
       
   }

    public PintarMSParar(Tour solucionActual, Graph lienzoMejor,Graph lienzoAux, int velocidad, JLabel cargadorMSTexto,JMenuItem item_ExportBest,JLabel timeSolution,int tiempo,JLabel ficticio) {
        //time = new Time(); 
        //time.setSegundos(tiempo);        
        this.solucionActual = solucionActual;
        
        this.lienzoMejor = lienzoMejor;        
        this.lienzoAux = lienzoAux;        
        
        this.velocidad = velocidad;
        this.cargadorMSTexto = cargadorMSTexto;
        this.item_ExportBest = item_ExportBest;
        this.timeSolution = timeSolution;
        this.ficticio = ficticio;
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
            ciudad = lienzoMejor.addNode(nombreCiudad);
            ciudad.addAttribute("label", nombreCiudad);
            ciudad.addAttribute("ui.size", 4);
            ciudad.addAttribute("xy", coordenadaX, coordenadaY);
            
            
            ciudad1 = lienzoAux.addNode(nombreCiudad);
            ciudad1.addAttribute("label", nombreCiudad);
            ciudad1.addAttribute("ui.size", 4);
            ciudad1.addAttribute("xy", coordenadaX, coordenadaY);
            
            if (i + 1 == tamannoSolucionActual) {
                //graph.addEdge("AB", "A", "B");
                int cont = 1;
                for (int j = 0; j < solucionActual.tourTamanno(); j++) {
                    String ciudadActual1 = String.valueOf(solucionActual.getCiudad(j).getNombreId());
                    String ciudadSiguiente1 = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                    this.lienzoMejor.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);
                    this.lienzoAux.addEdge(String.valueOf(cont + 1), ciudadActual1, ciudadSiguiente1);

                    cont++;
                    if (j == solucionActual.tourTamanno() - 2) {
                        String ciudadUltima = String.valueOf(solucionActual.getCiudad(j + 1).getNombreId());
                        //lienzo.addAttribute("edge {fill-color:orange;}");
                        this.lienzoMejor.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()), true);
                        this.lienzoAux.addEdge(String.valueOf(cont + 1), ciudadUltima, String.valueOf(solucionActual.getCiudad(0).getNombreId()), true);
                        cont++;
                    }
                    Thread.sleep(this.velocidad);
                }
            }
            //time.Contar();
            Thread.sleep(this.velocidad);
        }
        return 0;
    }

    
    
    
    @Override
    protected void done() {
        //time.Detener();
        cargadorMSTexto.setVisible(false);
        this.item_ExportBest.setEnabled(true);
        //this.timeSolution.setText(""+time.getSegundos()+" seconds");
        //this.ficticio.setText(""+time.getSegundos());
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
