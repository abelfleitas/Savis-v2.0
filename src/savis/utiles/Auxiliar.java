package savis.utiles;

import java.util.ArrayList;
import java.util.Iterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Auxiliar {

    private Graph lienzo;
    private  ArrayList<Node> myNodes;
    
    public Auxiliar(Graph lienzo) {
       this.lienzo = lienzo;
       myNodes = new ArrayList<>();
    }
       
    public boolean isCerrado(){ 
        myNodes.add(lienzo.getNode(0));
        int i=0;
        Node tempVecinoAnterior = lienzo.getNode(0);
        Node tempInicial = lienzo.getNode(0);       
            while(i<lienzo.getNodeCount()-1)
            {
                Iterator<? extends Node > nodesArray = myNodes.get(myNodes.size()-1).getNeighborNodeIterator();
                if(myNodes.size()==1){
                     myNodes.add(nodesArray.next());
                }
                else
                {
                    tempVecinoAnterior = myNodes.get(myNodes.size()-2);
                    while(nodesArray.hasNext())
                    {
                        Node node= nodesArray.next();
                         if(node!=tempVecinoAnterior && node!=tempInicial ){
                             myNodes.add(node);

                         }   
                    }               
                i++;
                }
            }    
        if(myNodes.size() != lienzo.getNodeCount()){
            return false;
        }
        else{
            return true;
        }
    } 
    
    public void limpiarArray(){
        this.myNodes.clear();
    }
        
}
