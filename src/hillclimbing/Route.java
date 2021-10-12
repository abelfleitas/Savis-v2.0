package hillclimbing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import savis.RS.Ciudad;

public class Route {

    private ArrayList<Ciudad> cities = new ArrayList<>();
    private static Route instancia;
    
    public Route(ArrayList<Ciudad> cities) {
        this.cities = cities;
        Collections.shuffle(this.cities);        
    }
    
    public static Route getInstancia(ArrayList<Ciudad> pcities)
    {
        if (instancia == null) {
            instancia = new Route(pcities);
            //System.out.println("El objeto ha sido creado");
        }
        else {
            System.out.println("");//Ya existe el objeto
        }

        return instancia;
    }
    
    
    public Route(Route route) {
        route.cities.stream().forEach(x->cities.add(x));
    }
    
    public ArrayList<Ciudad> getCities() {
        return cities;
    }
    
    public double getTotalDistance(){
        int citiesSize = cities.size();
        return this.cities.stream().mapToDouble(x->{
           int cityIndex = this.cities.indexOf(x);
           double retunValue = 0;
           if(cityIndex < citiesSize-1) retunValue = x.distanciaA(this.cities.get(cityIndex+1));
           return retunValue;
        }).sum()+this.cities.get(citiesSize-1).distanciaA(this.cities.get(0));
    }
    
    public String getTotalStringDistance(){
        String retunValue = String.format("%.4f",this.getTotalDistance());
        if(retunValue.length() == 7) retunValue = " "+retunValue;
        return retunValue;
    }
    
    public String toString(){
        return Arrays.toString(cities.toArray());
    }

    @Override
    public Route clone(){
        try {
            throw new CloneNotSupportedException();
        } catch (CloneNotSupportedException ex) {
            System.out.println("No se puede clonar un objeto de la clase Route");
        }
        return null; 
    }
    
    
}
