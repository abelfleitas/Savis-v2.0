package savis.RS;

public class Ciudad {

    // Identificador de la ciudad.
    private int nombreId;
    private double coordenadaX;
    private double coordenadaY;

    // Construye una ciudad en una posición generada aleatoriamente.
    public Ciudad(int nombreId) {
        this.nombreId = nombreId;
        this.coordenadaX = (double) (Math.random() * 200);
        this.coordenadaY = (double) (Math.random() * 200);
    }

    // Construye una ciudad en las coordenadas dadas.
    public Ciudad(int nombreId, double coordenadaX, double coordenadaY) {
        this.nombreId = nombreId;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    // Métodos de acceso a los atributos de la clase.
    public int getNombreId() {
        return nombreId;
    }

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    // Obtiene la distancia desde la ciudad actual a la pasada como argumento.
    public double distanciaA(Ciudad ciudad) {
        double xDistancia = ciudad.getCoordenadaX() - getCoordenadaX();
        double yDistancia = ciudad.getCoordenadaY() - getCoordenadaY();
        double distancia = Math.sqrt((xDistancia * xDistancia) + (yDistancia * yDistancia));
        return distancia;
    }

    /*@Override
    public String toString() {
        return getCoordenadaX() + ", " + getCoordenadaY();
    }*/
    
    @Override
    public String toString(){
        return this.nombreId+"";
    }
}
