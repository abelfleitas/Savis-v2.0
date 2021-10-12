package savis.RS;

import java.util.ArrayList;

public class TourControlador {

    // Almacena nuestras ciudades.
    private static ArrayList ciudadesDestino = new ArrayList<Ciudad>();

    // Inserta una ciudad en la lista de ciudades.
    public static void annadirCiudad(Ciudad ciudad) {
        ciudadesDestino.add(ciudad);
    }
    

    // Restablece la lista de ciudades en blanco.
    public static void inicializarTour() {
        ciudadesDestino.clear();
    }

    // Obtiene una ciudad.
    public static Ciudad getCiudadesDestino(int index) {
        return (Ciudad) ciudadesDestino.get(index);
    }

    // Obtiene la cantidad de ciudades del viaje.
    public static int totalCiudades() {
        return ciudadesDestino.size();
    }
    
    
}
