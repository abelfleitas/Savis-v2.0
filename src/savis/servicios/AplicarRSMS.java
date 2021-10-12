package savis.servicios;

import savis.RS.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import savis.utiles.Time;
import java.time.Instant;
import java.util.List;
import savis.vistas.Error1;
//ResultadoIntermedio
public class AplicarRSMS extends SwingWorker<Integer, ResultadoIntermedio> {
   
    private double coeficienteEnfriamiento,temperatura,distanciaSA;
    private Graph lienzo,lienzoMejor,lienzoPeor,lienzoAux,lienzoAux1,lienzoAux2;
    private int velocidad,totalIteraciones,tamannoSolucionActual,numeroIteracion;   
    private Tour tourInformacion,tourMejor,tourMalo;
    private PintarSAParar pintarSAParar;
    private PintarMSParar pintarMSParar;
    private PintarPSParar pintarPSParar;
    private JLabel cargadorAnimadoTexto,distanciaMSTexto,distanciaSATexto,distanciaPSTexto,cargadorMS,cargadorPS,iteracionTexto,timeSolution,ficticio;
    private JButton dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,pintarAristaBoton,deleteAristaBoton,listaExclusionButton,
                    aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista;
    JMenuItem item_ExportCurrent,item_ExportBest,item_ExportWorse,item_ImportFile,item_distancia,item_list;
    private boolean primeraVez = false;
    private Time time;
    private ArrayList<Ciudad> exclusion,listaNodosMarcados;
    int contador=0;
        
    public AplicarRSMS(Graph lienzo,Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1,JMenuItem item_ImportFile,JMenuItem item_list,JMenuItem item_distancia,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JButton dibujarRepresentacion,JButton ejecutarAlgoritmoBoton,JButton cancelarAlgoritmoBoton,JButton continuar,JButton pintarAristaButton,JButton deleteAristaBoton,JButton desacerCambiosBoton,JButton listaExclusionButton,JButton aumentarCamara,JButton disminuirCamara,JButton rotarLienzoI,JButton rotarLienzoD,JButton arribaBoton,JButton izquierdaBoton,JButton derechaBoton,JButton debajoBoton,JButton restablecerVista,JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto,JLabel cargadorAnimadoTexto,JLabel cargadorMS, JLabel cargadorPS,double temperatura, double enfriamiento,int totalIteraciones,int velocidad,ArrayList<Ciudad> exclusion,JLabel iteracionTexto,JLabel ficticio,JLabel timeSolution,Tour tourInformacion,int timeStop,ArrayList<Ciudad> listaNodosMarcados) {
        time = new Time();
        time.setSegundos(timeStop);   
        time.Contar();
        this.lienzo = lienzo;
        this.lienzoAux2 = lienzoAux2;           
        this.lienzoMejor = lienzoMejor;                
        this.lienzoAux = lienzoAux;        
        this.lienzoPeor = lienzoPeor;
        this.lienzoAux1 = lienzoAux1;            
        this.item_ImportFile = item_ImportFile;
        this.item_distancia = item_distancia;
        this.item_list = item_list;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_ExportBest = item_ExportBest;
        this.item_ExportWorse = item_ExportWorse;
        this.dibujarRepresentacion = dibujarRepresentacion;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;
        this.cancelarAlgoritmoBoton = cancelarAlgoritmoBoton;
        this.continuar = continuar;
        this.pintarAristaBoton = pintarAristaButton;
        this.deleteAristaBoton = deleteAristaBoton;       
        this.listaExclusionButton = listaExclusionButton;       
        this.aumentarCamara = aumentarCamara;
        this.disminuirCamara = disminuirCamara;
        this.rotarLienzoI = rotarLienzoI;
        this.rotarLienzoD = rotarLienzoD;
        this.arribaBoton = arribaBoton;
        this.izquierdaBoton = izquierdaBoton;
        this.derechaBoton = derechaBoton;
        this.debajoBoton = debajoBoton;
        this.restablecerVista = restablecerVista;
        this.distanciaMSTexto = distanciaMSTexto;
        this.distanciaSATexto = distanciaSATexto;
        this.distanciaPSTexto = distanciaPSTexto;
        this.cargadorAnimadoTexto = cargadorAnimadoTexto;        
        this.cargadorMS = cargadorMS;
        this.cargadorPS = cargadorPS;
        this.temperatura = temperatura;
        this.coeficienteEnfriamiento = enfriamiento;
        this.totalIteraciones = totalIteraciones;
        this.velocidad = velocidad;
        this.exclusion = exclusion;
        this.tourInformacion = tourInformacion;
        this.iteracionTexto = iteracionTexto;                
        this.timeSolution = timeSolution;
        this.ficticio = ficticio;     
        this.cargadorAnimadoTexto.setVisible(true);
        this.cargadorMS.setVisible(true);
        this.cargadorPS.setVisible(true);
        this.dibujarRepresentacion.setEnabled(false);
        this.listaExclusionButton.setEnabled(false);
        this.ejecutarAlgoritmoBoton.setEnabled(false);
        this.deleteAristaBoton.setEnabled(false);        
        this.aumentarCamara.setEnabled(false);
        this.disminuirCamara.setEnabled(false);
        this.rotarLienzoI.setEnabled(false);
        this.rotarLienzoD.setEnabled(false);
        this.arribaBoton.setEnabled(false);
        this.izquierdaBoton.setEnabled(false);
        this.derechaBoton.setEnabled(false);
        this.debajoBoton.setEnabled(false);
        this.restablecerVista.setEnabled(false);       
        this.continuar.setEnabled(false);        
        this.item_ImportFile.setEnabled(false);
        this.item_list.setEnabled(false);
        this.item_distancia.setEnabled(false);
        this.item_ExportCurrent.setEnabled(false);
        this.item_ExportBest.setEnabled(false);
        this.item_ExportWorse.setEnabled(false);  
        this.listaNodosMarcados = listaNodosMarcados;       
        System.out.println("Lista exclusion Sise :"+exclusion.size()); 
        System.out.println("Lista Nodos sin Marcar Sise :"+listaNodosMarcados.size()); 
    }

    
    //Conocido como criterio de Boltzmann.
    private double probabilidadAceptacion(double energia, double nuevaEnergia, double temperatura) {
        // Si la nueva solución es mejor, se acepta con probabilidad igual a 1.
        if (nuevaEnergia < energia) {
            return 1.0;
        }
        // Si la nueva solución es más mala, calculamos ¿qué tan mala es?.
        return Math.exp((energia - nuevaEnergia) / temperatura);
    }

    

    public Tour getTourMejor() {
        return tourMejor;
    }

    public Tour getTourMalo() {
        return tourMalo;
    }

    public Tour getTourInformacion() {
        return tourInformacion;
    }
    
    private void cargarListaCiudades() {
        for (int i = 0; i < tourInformacion.getTour().size(); i++) {                              
            int nombreId = tourInformacion.getCiudad(i).getNombreId();
            double coordenadaX = tourInformacion.getCiudad(i).getCoordenadaX();
            double coordenadaY = tourInformacion.getCiudad(i).getCoordenadaY();
            Ciudad ciudad = new Ciudad(nombreId, coordenadaX, coordenadaY);                                               
            TourControlador.annadirCiudad(ciudad); 
        }
    }

    
    public int getTotalIteraciones() {
        return totalIteraciones;
    }

    private void ejecutarRS() throws InterruptedException {
        cargarListaCiudades();
        Tour solucionActual = new Tour();
        solucionActual.almacenarSolucionInicialCreada();
        if (primeraVez) {
            tourMejor = new Tour(solucionActual.getTour());
            tourMalo = new Tour(solucionActual.getTour());
        }
        distanciaSA = solucionActual.getDistancia();
        if(!exclusion.isEmpty())
        {
            //System.out.println("Entre en lista de exclusion llena");
            while (temperatura > 1) {
                // Crea un nuevo vecino.
                Tour nuevaSolucion = new Tour(solucionActual.getTour());            
                // Obtiene dos posicione aleatorias en el tour.
               
                int tourPos1 = (int) (listaNodosMarcados.size()* Math.random());
                int tourPos2 = (int) (listaNodosMarcados.size()* Math.random()); 
                
                Ciudad citySwap11 = listaNodosMarcados.get(tourPos1);
                Ciudad citySwap22 = listaNodosMarcados.get(tourPos2);
                //System.out.println(citySwap11.getNombreId() + " - " + citySwap22.getNombreId());
                
                int posaux1 = nuevaSolucion.getPos(citySwap11);
                int posaux2 = nuevaSolucion.getPos(citySwap22);
                //System.out.println(nuevaSolucion.toString());
                //System.out.println(posaux1 + " - " + posaux2);
                Ciudad citySwap1 = nuevaSolucion.getCiudad(posaux1);
                Ciudad citySwap2 = nuevaSolucion.getCiudad(posaux2);
                
                    
                    //System.out.println(nuevaSolucion.getCiudad(tourPos1).getNombreId()+" - "+nuevaSolucion.getCiudad(tourPos2).getNombreId());
                    nuevaSolucion.setCiudad(posaux1, citySwap2);
                     // Intercambia las ciudades de posición. 
                    nuevaSolucion.setCiudad(posaux2, citySwap1);

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
        }
        else{
            //System.out.println("Entre en lista de exclusion vacia");
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
        }
        tourInformacion = tourMejor;        
        TourControlador.inicializarTour();
    }

    @Override
    protected Integer doInBackground() throws Exception {
        Instant st =Instant.now();
        for (int i = 0; i < totalIteraciones; i++) {
            this.temperatura = 10000;
            if (i == 0) {
                primeraVez = true;
            } else {
                primeraVez = false;
            }
            ejecutarRS();
            ResultadoIntermedio ri = new ResultadoIntermedio(distanciaSA, getTourMejor().getDistancia(), getTourMalo().getDistancia(), i+1,time.getSegundos());
            publish(ri);            
            //Thread.sleep(1000);
        }   
        Instant en =Instant.now();
        System.out.println("Termine!!!!!!!!!!!!!");
        return 0;        
    }

    //List<ResultadoIntermedio> lista
    @Override
    protected void process(List<ResultadoIntermedio> lista) {
        ResultadoIntermedio publicacion = lista.get(lista.size()-1);
        distanciaSATexto.setText(String.valueOf(publicacion.getDistanciaSA()));
        distanciaMSTexto.setText(String.valueOf(publicacion.getDistanciaMS()));
        distanciaPSTexto.setText(String.valueOf(publicacion.getDistanciaPS()));
        iteracionTexto.setText(String.valueOf(publicacion.getIteraciones()));
        timeSolution.setText(String.valueOf(publicacion.getSegundos()+"s"));
    }

    @Override
    protected void done() {
        time.Detener();
        System.err.println("Se detuvo");
        pintarSAParar = new PintarSAParar(getTourMejor(), lienzo,lienzoAux2, velocidad, cargadorAnimadoTexto,item_ExportCurrent,item_ImportFile,dibujarRepresentacion,ejecutarAlgoritmoBoton,cancelarAlgoritmoBoton,continuar,deleteAristaBoton,pintarAristaBoton,listaExclusionButton,item_list,aumentarCamara,disminuirCamara,rotarLienzoI,rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton,debajoBoton,restablecerVista,distanciaSATexto);
        pintarSAParar.execute();        
        pintarMSParar = new PintarMSParar(getTourMejor(), lienzoMejor,lienzoAux, velocidad, cargadorMS,item_ExportBest,timeSolution,time.getSegundos(),ficticio);
        pintarMSParar.execute();        
        pintarPSParar = new PintarPSParar(getTourMalo(), lienzoPeor,lienzoAux1, velocidad, cargadorPS,item_ExportWorse);
        pintarPSParar.execute();
        this.item_distancia.setEnabled(true);
    }
    
    private boolean existe(ArrayList<Ciudad>a, int b){
        for(int i=0;i<a.size();i++){
            if(a.get(i).getNombreId()==b){
                return true;
            }
        }
        return false;
    }
    
    /*public int give_A_number(){
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Ciudad> prueba = new ArrayList<>();
        for(int i=0;i<tourInformacion.tourTamanno();i++){
            if(!existe(exclusion,tourInformacion.getCiudad(i).getNombreId())){
                prueba.add(tourInformacion.getCiudad(i));                
            }
        }                
        prueba.removeAll(prueba);
        
        return; 
    }*/
     //int tourPos1 = (int) (nuevaSolucion.tourTamanno()* Math.random());
                //int tourPos2 = (int) (nuevaSolucion.tourTamanno()* Math.random()); 
                
    //boolean distintos = false;
                //boolean distintos1 = false;
                /*for(int k=0;k<exclusion.size();k++)
                {
                    if(existe(exclusion,nuevaSolucion.getCiudad(tourPos1).getNombreId())){
                     
                        distintos = false;
                    }else{
                        distintos = true;
                    }
                    if((existe(exclusion,nuevaSolucion.getCiudad(tourPos2).getNombreId()))){
                         distintos1 = false;
                    }else{
                        distintos1 = true;
                    }
                    
                }*/
                //if(distintos==true && distintos1==true){
                   /* Ciudad citySwap1 = nuevaSolucion.getCiudad(tourPos1);
                    Ciudad citySwap2 = nuevaSolucion.getCiudad(tourPos2);
     /*distanciaSATexto.setText(String.valueOf(distanciaSA));
        distanciaMSTexto.setText(String.valueOf(getTourMejor().getDistancia()));
        distanciaPSTexto.setText(String.valueOf(getTourMalo().getDistancia()));
        iteracionTexto.setText(String.valueOf(publicacion.getIteraciones()));
        timeSolution.setText(String.valueOf(lista.get(lista.size()-1))+"s");
    */
    
    
}

  