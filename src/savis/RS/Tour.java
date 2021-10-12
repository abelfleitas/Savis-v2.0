package savis.RS;

import java.util.ArrayList;
import java.util.Collections;

public class Tour {

    // Almacena nuestros viajes.
    private ArrayList<Ciudad> tour = new ArrayList<>();
    private double distancia = 0;

    // Construye un tour en blanco.
    public Tour() {
        for (int i = 0; i < TourControlador.totalCiudades(); i++) {
            tour.add(null);
        }
    }

    // Construye un tour a partir de otro dado como argumento.
    public Tour(ArrayList tour) {
        this.tour = (ArrayList) tour.clone();
    }

    // Retorna la información del tour.
    public ArrayList getTour() {
        return tour;
    }

    // Crea una solución inicial aleatoriamente.
    public void generateIndividual() {
        for (int indiceCiudad = 0; indiceCiudad < TourControlador.totalCiudades(); indiceCiudad++) {
            setCiudad(indiceCiudad, TourControlador.getCiudadesDestino(indiceCiudad));
        }
        // Reordena la colección de ciudades.(Permutación)
        Collections.shuffle(tour);
    }

    // Almacena la solución creada por el usuario. 
    public void almacenarSolucionInicialCreada() {
        for (int indiceCiudad = 0; indiceCiudad < TourControlador.totalCiudades(); indiceCiudad++) {
            setCiudad(indiceCiudad, TourControlador.getCiudadesDestino(indiceCiudad));
        }
    }

    // Obtiene una ciudad del tour. Se tiene en cuenta su posición.
    public Ciudad getCiudad(int tourPosicion) {
        return (Ciudad) tour.get(tourPosicion);
    }
   public int getPos(Ciudad x)
   {
    for(int i = 0; i < tour.size(); i++)
    {
     if(tour.get(i).getNombreId() == x.getNombreId())
         return i;
    }
    return -1;
   }
    // Establece un ciudad en cierta posición dentro del tour.
    public void setCiudad(int tourPosicion, Ciudad ciudad) {
        tour.set(tourPosicion, ciudad);
        // Si el tour a sido modificado debemos restablecer la distancia = 0.
        distancia = 0;
    }

    // Obtiene la distancia total del tour.
    public double getDistancia() {
        if (distancia == 0) 
        {           
            double tourDistancia = 0;
            for(int i=0;i<tourTamanno();i++)
            {
               Ciudad ciudadActual = tour.get(i);
               if(i+1 < tourTamanno())
               {
                   Ciudad ciudadNext = tour.get(i+1);
                   tourDistancia += ciudadActual.distanciaA(ciudadNext);
               }
            }   
            Ciudad first = tour.get(0);
            Ciudad last  = tour.get(tour.size()-1);
            tourDistancia += last.distanciaA(first);


            distancia = tourDistancia;            
        }
        return distancia;
    }

    // Obtiene el tamaño del tour.
    public int tourTamanno() {
        return tour.size();
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourTamanno(); i++) {
            geneString += getCiudad(i) + "|";
        }
        return geneString;
    }
    
}
