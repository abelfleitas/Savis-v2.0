package savis.RS;

public class ResultadoIntermedio {

    private double distanciaSA;
    private double distanciaMS;
    private double distanciaPS;
    private int iteraciones;
    private int segundos;

    public ResultadoIntermedio(double distanciaSA, double distanciaMS, double distanciaPS, int iteraciones,int segundos) {
        this.distanciaSA = distanciaSA;
        this.distanciaMS = distanciaMS;
        this.distanciaPS = distanciaPS;
        this.iteraciones = iteraciones;
        this.segundos =  segundos;
    }

    public double getDistanciaSA() {
        return distanciaSA;
    }

    public void setDistanciaSA(double distanciaSA) {
        this.distanciaSA = distanciaSA;
    }
    
    public double getDistanciaMS() {
        return distanciaMS;
    }

    public void setDistanciaMS(double distanciaMS) {
        this.distanciaMS = distanciaMS;
    }

    public double getDistanciaPS() {
        return distanciaPS;
    }

    public void setDistanciaPS(double distanciaPS) {
        this.distanciaPS = distanciaPS;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }
    
    public int getSegundos() {
        return segundos;
    }

    public void setDistancia(int segundos) {
        this.segundos = segundos;
    }

    
    
}
