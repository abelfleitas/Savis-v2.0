package savis.servicios;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import savis.RS.Ciudad;
import savis.RS.Tour;
import savis.RS.TourControlador;
import savis.utiles.Time;

public class PintarHillClimbing extends SwingWorker<Integer, Tour>{
    
    private ArrayList<Ciudad> listaNodos;

    
    private double coeficienteEnfriamiento,temperatura;
    private Graph lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1; 
    private int velocidad,tamannoSolucionActual;
    private Tour tourMejor,tourMalo,tour;    
    private PintarSolucionActual pintarSolucionActual;
    private PintarMejorSolucion pintarMejorSolucion;
    private PintarPeorSolucion pintarPeorSolucion;     
    private JLabel cargadorAnimadoTexto,cargadorMSTexto,cargadorPSTexto,distanciaSATexto,distanciaMSTexto,distanciaPSTexto,timeSolution,ficticio;    
    private JButton cancelarAlgoritmo,dibujarRepresentacion,arribaBoton,derechaBoton,debajoBoton,izquierdaBoton,aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,restablecerVista,deleteAristaBoton,pintarAristaBoton,listaExclusionButton,ejecutarAlgoritmoBoton;
    private JMenuItem importarArchivo,item_distancia,item_list,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion;
    //private Time time;
    private ArrayList<Ciudad> exclusion;
    

    public PintarHillClimbing(Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,ArrayList<Ciudad> listaNodos, double temperatura, double enfriamiento, int velocidad, JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto,JLabel distanciaSATexto, JLabel distanciaMSTexto,JLabel distanciaPSTexto,JButton cancelarAlgoritmo,JButton dibujarRepresentacion,JButton arribaBoton,JButton derechaBoton, JButton debajoBoton, JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI, JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,ArrayList<Ciudad> exclusion,JButton deleteAristaBoton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem item_configuracion,JButton ejecutarAlgoritmoBoton,JMenuItem importarArchivo,JLabel ficticio) {
        //time = new Time();
        this.lienzo = lienzo;
        this.lienzoAux2 = lienzoAux2;
        this.lienzoMejor = lienzoMejor;
        this.lienzoAux = lienzoAux;
        this.lienzoPeor = lienzoPeor;
        this.lienzoAux1 = lienzoAux1;    
        this.listaNodos = listaNodos;
        this.temperatura = temperatura;
        this.coeficienteEnfriamiento = enfriamiento;
        this.velocidad = velocidad;
        this.cargadorAnimadoTexto = cargadorAnimadoTexto;
        this.cargadorMSTexto = cargadorMSTexto;
        this.cargadorPSTexto = cargadorPSTexto;        
        this.distanciaSATexto = distanciaSATexto;
        this.distanciaMSTexto = distanciaMSTexto;
        this.distanciaPSTexto = distanciaPSTexto;        
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
        this.exclusion = exclusion;
        this.deleteAristaBoton = deleteAristaBoton;
        this.deleteAristaBoton.setEnabled(false);
        this.pintarAristaBoton = pintarAristaBoton;
        this.importarArchivo = importarArchivo;
        this.item_distancia = item_distancia;
        this.item_list = item_list;
        this.listaExclusionButton = listaExclusionButton;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_ExportBest = item_ExportBest;
        this.item_ExportWorse = item_ExportWorse;
        this.item_configuracion = item_configuracion;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
        //time.Contar();
        this.ficticio=ficticio;
    }


    // Obtiene el objeto de pintar nodos.
    public PintarSolucionActual getPintarSolucionActual() {
        return pintarSolucionActual;
    }
    
    public PintarMejorSolucion getPintarMejorSolucion() {
        return pintarMejorSolucion;
    }
    
    public PintarPeorSolucion getPintarPeorSolucion() {
        return pintarPeorSolucion;
    }

   

    // Después de obtenidos los nodos del fichero, se llena la estructura de datos del algoritmo con esta lista.
    private void cargarListaCiudades() {
        for (int i = 0; i < listaNodos.size(); i++) 
        {            
            int nombreId = listaNodos.get(i).getNombreId();
            double coordenadaX = listaNodos.get(i).getCoordenadaX();
            double coordenadaY = listaNodos.get(i).getCoordenadaY();
            Ciudad ciudad = new Ciudad(nombreId, coordenadaX, coordenadaY);
            TourControlador.annadirCiudad(ciudad);            
        }
    }
    
    public Tour getTourActual(){
        return tour;
    }

    
    
    // Ejecuta el Recocido Simulado.
    private void ejecutarRS() throws InterruptedException {
        cargarListaCiudades();
        Tour solucionActual = new Tour();
        solucionActual.almacenarSolucionInicialCreada();
        publish(solucionActual);
        tour = solucionActual;
        System.out.println(""+tour.getDistancia());
        TourControlador.inicializarTour();
    }
   
    @Override
    protected Integer doInBackground() throws Exception {
        ejecutarRS();
        return 0;
    }

    @Override
    protected void process(java.util.List<Tour> lista) {
        Tour tour = lista.get(0);
        pintarSolucionActual = new PintarSolucionActual(tour, lienzo,lienzoAux2,velocidad, cargadorAnimadoTexto, distanciaSATexto, cancelarAlgoritmo,dibujarRepresentacion, arribaBoton, derechaBoton, debajoBoton, izquierdaBoton, aumentarCamara, disminuirCamara, rotarLienzoI, rotarLienzoD, restablecerVista,timeSolution,deleteAristaBoton,pintarAristaBoton,item_distancia,item_list,listaExclusionButton,item_ExportCurrent,item_configuracion,ejecutarAlgoritmoBoton,importarArchivo,ficticio);
        pintarSolucionActual.execute();        
        pintarMejorSolucion = new PintarMejorSolucion(tour, lienzoMejor, lienzoAux, velocidad, cargadorMSTexto, distanciaMSTexto,item_ExportBest);
        pintarMejorSolucion.execute();        
        pintarPeorSolucion = new PintarPeorSolucion(tour, lienzoPeor, lienzoAux1, velocidad, cargadorPSTexto, distanciaPSTexto,item_ExportWorse);
        pintarPeorSolucion.execute();
    }

    @Override
    protected void done() {
       super.done();
    }

    
    
}
