package savis.servicios;

import savis.RS.Ciudad;
import savis.RS.Tour;
import savis.RS.TourControlador;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import savis.utiles.Time;

public class HallarSolucionRS extends SwingWorker<Integer, Tour> {

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
    private Time time;
    private ArrayList<Ciudad> exclusion;
    

    public HallarSolucionRS(Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,ArrayList<Ciudad> listaNodos, double temperatura, double enfriamiento, int velocidad, JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto,JLabel distanciaSATexto, JLabel distanciaMSTexto,JLabel distanciaPSTexto,JButton cancelarAlgoritmo,JButton dibujarRepresentacion,JButton arribaBoton,JButton derechaBoton, JButton debajoBoton, JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI, JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,ArrayList<Ciudad> exclusion,JButton deleteAristaBoton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem item_configuracion,JButton ejecutarAlgoritmoBoton,JMenuItem importarArchivo,JLabel ficticio) {
        time = new Time();
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
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
        ficticio = ficticio;
    }


    //Métodos-------------------------------------------------------------------
    //Conocido como criterio de Boltzmann.
    private double probabilidadAceptacion(double energia, double nuevaEnergia, double temperatura) {
        // Si la nueva solución es mejor, se acepta con probabilidad igual a 1.
        if (nuevaEnergia < energia) {
            return 1.0;
        }
        // Si la nueva solución es más mala, calculamos ¿qué tan mala es?.
        return Math.exp((energia - nuevaEnergia) / temperatura);
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

    public Tour getTourActual(){
        return tour;
    }
    
    // Obtiene las soluciones.
    public Tour getTourMejor() {
        return tourMejor;
    }

    public Tour getTourMalo() {
        return tourMalo;
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

    // Ejecuta el Recocido Simulado.
    private void ejecutarRS() throws InterruptedException {
        time.Contar();
        cargarListaCiudades();
        Tour solucionActual = new Tour();
        solucionActual.generateIndividual();
        publish(solucionActual);
        tour = new Tour(solucionActual.getTour());
        tourMejor = new Tour(solucionActual.getTour());
        tourMalo = new Tour(solucionActual.getTour()); 
        while (temperatura > 1) {
            // Crea un nuevo vecino.
            Tour nuevaSolucion = new Tour(solucionActual.getTour());

            // Obtiene dos posicione aleatorias en el tour.
            int tourPos1 = (int) (nuevaSolucion.tourTamanno() * Math.random());
            int tourPos2 = (int) (nuevaSolucion.tourTamanno() * Math.random());             

            // Obtiene las dos ciudades que se encuentran en estas posiciones.
            Ciudad citySwap1 = nuevaSolucion.getCiudad(tourPos1);
            Ciudad citySwap2 = nuevaSolucion.getCiudad(tourPos2);

            // Intercambia las ciudades de posición.
            nuevaSolucion.setCiudad(tourPos2, citySwap1);
            nuevaSolucion.setCiudad(tourPos1, citySwap2);

            // Obtiene la energía de las soluciones.
            double energiaActual = solucionActual.getDistancia();
            double energiaVecino = nuevaSolucion.getDistancia();

            // Decidimos si debemos aceptar el vecino.
            if (probabilidadAceptacion(energiaActual, energiaVecino, temperatura) > Math.random()) {
                solucionActual = new Tour(nuevaSolucion.getTour());
            }
            // Se mantiene la mejor solución encontrada hasta el momento.
            if (solucionActual.getDistancia() < tourMejor.getDistancia()) {
                tourMejor = new Tour(solucionActual.getTour());
            }
            // Se mantiene la peor solución encontrada hasta el momento.
            if (solucionActual.getDistancia() > tourMalo.getDistancia()) {
                tourMalo = solucionActual;
            }
            // Sistema de enfriamiento.
            temperatura *= 1 - coeficienteEnfriamiento;    
        }
        TourControlador.inicializarTour();
    }

    //--------------------------------------------------------------------------
    @Override
    protected Integer doInBackground() throws Exception {
        ejecutarRS();
        return 0;
    }

    @Override
    protected void process(java.util.List<Tour> lista) {
        // Almacena cada publicación realizada.
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
       time.Detener();
    }

}
