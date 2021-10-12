package hillclimbing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import savis.RS.Ciudad;
import savis.servicios.PintarHillClimbing;
import savis.utiles.Savis_CI;
import savis.vistas.BarraProgreso;
import savis.vistas.Principal;

public class HillClimbing extends SwingWorker<Integer,Integer>{
    
    private ArrayList<Ciudad> lista;        
    private BarraProgreso progreso;
    private PintarHillClimbing pintarHillClimbing;
    private JButton dibujarRepresentacion,cancelarAlgoritmo,ejecutarAlgoritmoBoton,pintarAristaBoton,deleteAristaBoton,listaExclusionButton,aumentarCamara, disminuirCamara, rotarLienzoI, rotarLienzoD,arribaBoton,izquierdaBoton,derechaBoton, debajoBoton,restablecerVista;
    private JLabel distanciaSATexto,distanciaMSTexto,distanciaPSTexto,cargadorAnimadoTexto, cargadorMSTexto,cargadorPSTexto,timeSolution,ficticio;
    private Route route;
    Principal parent;
    private static final int ITERATIONS_BEFORE_MAXIMA = 100;
    private Graph lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1;
    private double temperatura, enfriamiento;
    private int velocidad;
    private ArrayList<Ciudad> auxiliar;
    private ArrayList<Ciudad> exclusion;
    private Savis_CI savis;
    private JMenuItem importarArchivo,item_list,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion;

    public HillClimbing(Graph lienzo, Graph lienzoAux2,Graph lienzoMejor,Graph lienzoAux,Graph lienzoPeor,Graph lienzoAux1, ArrayList<Ciudad> listaNodos,double temperatura, double enfriamiento, int velocidad,JLabel cargadorAnimadoTexto,JLabel cargadorMSTexto,JLabel cargadorPSTexto, JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto, JButton cancelarAlgoritmo,JButton dibujarRepresentacion,JButton arribaBoton, JButton derechaBoton, JButton debajoBoton,JButton izquierdaBoton, JButton aumentarCamara, JButton disminuirCamara, JButton rotarLienzoI,JButton rotarLienzoD, JButton restablecerVista,JLabel timeSolution,ArrayList<Ciudad> exclusion,JButton deleteAristaBoton,JButton pintarAristaBoton,JMenuItem item_distancia,JMenuItem item_list,JButton listaExclusionButton,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JMenuItem item_configuracion,JButton ejecutarAlgoritmoBoton,JMenuItem importarArchivo,Principal parent,Savis_CI savis,JLabel ficticio) {
        this.lista = listaNodos;
        auxiliar = new ArrayList<>();
        for(int i=0;i<lista.size();i++){
            auxiliar.add(lista.get(i));
        }
        //route = Route.getInstancia(auxiliar); 
        route = new Route(auxiliar);
        this.lienzo = lienzo;
        this.lienzoAux2 = lienzoAux2;
        this.lienzoMejor = lienzoMejor;
        this.lienzoAux = lienzoAux;
        this.lienzoPeor = lienzoPeor;
        this.lienzoAux1 = lienzoAux1;
        this.temperatura = temperatura; 
        this.enfriamiento = enfriamiento;
        this.velocidad = velocidad;       
        this.dibujarRepresentacion = dibujarRepresentacion;
        this.cancelarAlgoritmo = cancelarAlgoritmo;
        this.ejecutarAlgoritmoBoton = ejecutarAlgoritmoBoton;        
        this.pintarAristaBoton = pintarAristaBoton;
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
        this.distanciaSATexto = distanciaSATexto;
        this.distanciaMSTexto = distanciaMSTexto;
        this.distanciaPSTexto = distanciaPSTexto;
        this.cargadorAnimadoTexto = cargadorAnimadoTexto; 
        this.cargadorMSTexto = cargadorMSTexto;
        this.cargadorPSTexto = cargadorPSTexto;
        this.timeSolution = timeSolution;          
        this.importarArchivo = importarArchivo;
        this.item_configuracion = item_configuracion;
        this.item_list = item_list;
        this.item_distancia = item_distancia;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_ExportBest = item_ExportBest;
        this.item_ExportWorse = item_ExportWorse;  
        this.parent=parent;
        this.savis = savis;
        this.progreso = new BarraProgreso(parent, false,this);
        this.progreso.setLocationRelativeTo(parent);
        this.progreso.setVisible(true);
        this.ficticio=ficticio;
    }
	   
   public Route findShortesRoute(Route currentRoute) throws InterruptedException{
        Route adjacentRoute;
        int iterToMaximaCounter = 0;
        String compareRoutes = null;
        while (iterToMaximaCounter < ITERATIONS_BEFORE_MAXIMA) {
            adjacentRoute = obtainAdjacentRoute(new Route(currentRoute));
            if(adjacentRoute.getTotalDistance() <= currentRoute.getTotalDistance()){
                compareRoutes = "<= (proceed)";
                iterToMaximaCounter=0;
                currentRoute = new Route(adjacentRoute);
                route = new Route(currentRoute);
            } 
            else{
                compareRoutes = "> (stay) - iterations # " + iterToMaximaCounter++;
                publish(iterToMaximaCounter);Thread.sleep(5);
                //System.out.println("         | " + compareRoutes);
                //System.out.println(currentRoute +"  |      "+currentRoute.getTotalStringDistance());
            }
            /*if(iterToMaximaCounter == ITERATIONS_BEFORE_MAXIMA){
                System.out.println("      | potential maxima"); 
            }            
            else {
                System.out.println("         | " + compareRoutes);
            }*/
        }
        return currentRoute;
    }
    
    private Route obtainAdjacentRoute(Route route){
        int x1=0;
        int x2=0;
        while(x1 == x2 ){
            x1 = (int) (route.getCities().size() * Math.random());
            x2 = (int) (route.getCities().size() * Math.random());
        }
        Ciudad city1 = route.getCities().get(x1);
        Ciudad city2 = route.getCities().get(x2);
        route.getCities().set(x2, city1);
        route.getCities().set(x1, city2);                
        return route;
    }
     

    @Override
    protected Integer doInBackground() throws Exception { 
        this.findShortesRoute(route);
        return 0;
    }
    
    
    @Override
    protected void process(List<Integer> listado) {
        this.progreso.getProgressBar().setValue(listado.get(listado.size()-1));
        this.distanciaSATexto.setText(String.valueOf(route.getTotalDistance()));
        this.distanciaMSTexto.setText(String.valueOf(route.getTotalDistance()));
        this.distanciaPSTexto.setText(String.valueOf(route.getTotalDistance()));
    }
    
    @Override
    protected void done() {
       this.progreso.setVisible(false);
       this.cancelarAlgoritmo.setEnabled(true);
       pintarHillClimbing = new PintarHillClimbing(lienzo,lienzoAux2,lienzoMejor,lienzoAux,lienzoPeor,lienzoAux1,route.getCities(), temperatura, enfriamiento, velocidad,cargadorAnimadoTexto, cargadorMSTexto,cargadorPSTexto,distanciaSATexto, distanciaMSTexto,distanciaPSTexto,cancelarAlgoritmo,dibujarRepresentacion,arribaBoton,derechaBoton, debajoBoton, izquierdaBoton, aumentarCamara, disminuirCamara, rotarLienzoI, rotarLienzoD, restablecerVista, timeSolution, exclusion, deleteAristaBoton, pintarAristaBoton, item_distancia, item_list, listaExclusionButton,item_ExportCurrent,item_ExportBest,item_ExportWorse,item_configuracion,ejecutarAlgoritmoBoton,importarArchivo,ficticio);    
       pintarHillClimbing.execute();
       this.savis.setPintarHillClimbing(pintarHillClimbing);
    }
    
    
   
    
  
}
    

