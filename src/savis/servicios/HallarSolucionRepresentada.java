package savis.servicios;

import savis.RS.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class HallarSolucionRepresentada extends SwingWorker<Integer, Tour>{

    private ArrayList<Ciudad> listaNodos;
    private int tamannoSolucionActual;
    private Tour tourNew;
    private JLabel distanciaSATexto,distanciaMSTexto,distanciaPSTexto;  
    private JMenuItem item_ListExclusion,item_distancia,item_ExportCurrent,item_ExportBest,item_ExportWorse;
    private JButton listaExlusionButton;
    private double distanciaTotal;
    private  ArrayList<Node> myNodes;
    private ArrayList<Ciudad> auxiliar;
    private Graph lienzo;
    

    public HallarSolucionRepresentada(Graph lienzo,ArrayList<Ciudad> listaNodos, JLabel distanciaSATexto,JLabel distanciaMSTexto,JLabel distanciaPSTexto,JMenuItem item_ListExclusion,JMenuItem item_distancia,JMenuItem item_ExportCurrent,JMenuItem item_ExportBest,JMenuItem item_ExportWorse,JButton listaExlusionButton){
        this.listaNodos = listaNodos;
        this.lienzo = lienzo;
        this.distanciaSATexto = distanciaSATexto;
        this.distanciaMSTexto = distanciaMSTexto;
        this.distanciaPSTexto = distanciaPSTexto;
        this.item_ListExclusion = item_ListExclusion;
        this.item_distancia = item_distancia;
        this.item_ExportCurrent = item_ExportCurrent;
        this.item_ExportBest = item_ExportBest;
        this.item_ExportWorse = item_ExportWorse;
        this.listaExlusionButton = listaExlusionButton;
        myNodes = new ArrayList<>();
        auxiliar = new ArrayList<>();
        this.llenarMyArray();        
    }
    
    public void setLienzo(Graph lienzo) {
        this.lienzo = lienzo;
    }
    
    public ArrayList<Ciudad> getListaNodos() {
        return listaNodos;
    }

    public void setListaNodos(ArrayList<Ciudad> listaNodos) {
        this.listaNodos = listaNodos;
    }
    
    public void llenarMyArray(){                 
        myNodes.add(lienzo.getNode(0));
    }
    
    public void completarLista() {
        int i = 0;Node tempVecinoAnterior = lienzo.getNode(0);Node tempInicial = lienzo.getNode(0);
        while (i < lienzo.getNodeCount() - 1) {
            Iterator<? extends Node> nodesArray = myNodes.get(myNodes.size() - 1).getNeighborNodeIterator();
            if (myNodes.size() == 1) {
                myNodes.add(nodesArray.next());
            } else {
                tempVecinoAnterior = myNodes.get(myNodes.size() - 2);
                while (nodesArray.hasNext()) {
                    Node node = nodesArray.next();
                    if (node != tempVecinoAnterior && node != tempInicial) {
                        myNodes.add(node);

                    }
                }
                i++;
            }
        }this.llenarTour();
    }
    
    public void llenarTour(){
        for(int i=0;i<myNodes.size();i++)
        {          
            this.annadeTour(myNodes.get(i));
        }
    }
    
    public void annadeTour(Node x){        
        Ciudad informacionNP = listaNodos.get(Integer.parseInt(x.getId())-1);
        int nombreId =  informacionNP.getNombreId();
        double coordenadaX = informacionNP.getCoordenadaX();
        double coordenadaY = informacionNP.getCoordenadaY();  
        Ciudad ciudad = new Ciudad(nombreId, coordenadaX, coordenadaY);
        TourControlador.annadirCiudad(ciudad);        
        auxiliar.add(ciudad);
    }
    
    public Tour getTourActual(){
        return tourNew;
    }
     
    private void ejecutarRS()throws Exception{
        this.completarLista();
        Tour solucionActual = new Tour();        
        tourNew = new Tour(auxiliar);        
        solucionActual.almacenarSolucionInicialCreada();
        publish(solucionActual);
        TourControlador.inicializarTour();        
    }

    @Override
    protected Integer doInBackground() throws Exception {
        this.ejecutarRS();
        return 0;
    }
    
    @Override
    protected void process(java.util.List<Tour> lista) {
       Tour tour = lista.get(0);
       distanciaSATexto.setText(String.valueOf(tour.getDistancia()));
       distanciaMSTexto.setText(String.valueOf(tour.getDistancia()));
    }
    
    @Override
    protected void done() {
       this.item_ListExclusion.setEnabled(true);
       this.item_distancia.setEnabled(true);
       this.item_ExportCurrent.setEnabled(true);
       this.item_ExportBest.setEnabled(true);
       this.item_ExportWorse.setEnabled(true);
    }
    

 
}
