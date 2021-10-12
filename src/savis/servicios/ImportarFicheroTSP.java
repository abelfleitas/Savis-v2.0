package savis.servicios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import savis.RS.Ciudad;
import org.graphstream.graph.Graph;

public class ImportarFicheroTSP extends SwingWorker<ArrayList<Ciudad>, Void> {

    private ArrayList<Ciudad> listaNodos;
    private String direccionFichero;
    private int posicionEB;
    private Graph lienzo;
    private JButton dibujarRepresentacion;

    public ImportarFicheroTSP(String direccionFichero, Graph lienzo,JButton dibujarRepresentacion) {
        this.direccionFichero = direccionFichero;
        this.listaNodos = new ArrayList<>();
        this.lienzo = lienzo;
        this.dibujarRepresentacion = dibujarRepresentacion;
    }
    //Métodos-------------------------------------------------------------------
    //Elimina los espacios en blanco al inicio de la cadena pasada como argumento.

    private StringBuffer eliminarEB(StringBuffer str) {
        posicionEB = str.indexOf(" ");
        while (posicionEB == 0) {
            str.deleteCharAt(posicionEB);
            posicionEB = str.indexOf(" ");
        }
        return str;
    }

    //Extrae los datos del nodo por separado y los guarda en la lista de nodos.
    private void procesarInformacionNodos(String linea) {
        //Almacenan la información de los nodos.
        int idNodo = 0;
        double coordenadaX = 0;
        double coordenadaY;
        //Almacena la cadena modificada.
        StringBuffer lineaModificada = new StringBuffer(linea);
        int contador = 1;
        while (contador <= 2) {
            eliminarEB(lineaModificada);
            if (contador == 1) {
                idNodo = Integer.parseInt(lineaModificada.substring(0, posicionEB));
                lineaModificada.delete(0, posicionEB);
            }
            if (contador == 2) {
                coordenadaX = Double.parseDouble(lineaModificada.substring(0, posicionEB));
                lineaModificada.delete(0, posicionEB);
            }
            contador++;
        }
        eliminarEB(lineaModificada);
        coordenadaY = Double.parseDouble(lineaModificada.toString());
        //Creamos un nodo con la información del nodo.
        Ciudad nodo = new Ciudad(idNodo, coordenadaX, coordenadaY);
        //Guaradamos la información del nodo en la lista nodo.
        this.listaNodos.add(nodo);
        /*System.out.println("# de nodo: " + idNodo);
        System.out.println("Coordenada x: " + coordenadaX);
        System.out.println("Coordenada Y: " + coordenadaY);*/
        //System.out.println(eliminarEB(lineaModificada));
    }

    //Lee los datos almacenados en el fichero pasado como argumento.
    private void leerDatos(String archivo) throws FileNotFoundException, IOException {
        //Permite acceder al bloque de los nodos.
        int siguiente = 0;
        //Almacena la línea una por una del fichero que se desea leer.
        String linea;
        //Almacena la línea modificada.
        StringBuffer lineaModificada;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        //Mientras existan líneas por leer.
        while ((linea = b.readLine()) != null) {
            //Nos aseguramos de estar en el bloque de los nodos.
            if (siguiente == 1) {
                //Preguntamos si no estamos en la línea final.
                if (!linea.equalsIgnoreCase("EOF")) {
                    //Si existe alguna línea en blanco terminamos de leer.
                    if (linea.isEmpty()) {
                        break;
                    }
                    procesarInformacionNodos(linea);
                }
            }
            //Preguntamos si estamos en el bloque de los nodos
            if (linea.equals("NODE_COORD_SECTION") || linea.equals("DISPLAY_DATA_SECTION")) {
                //Establecemos que estamos en el bloque de los nodos.
                siguiente = 1;
            }
        }
        b.close();
    }
    //--------------------------------------------------------------------------
    @Override
    protected ArrayList<Ciudad> doInBackground() throws Exception {
        this.leerDatos(direccionFichero);
        return this.listaNodos;
    }
    @Override
    protected void done() {
        this.dibujarRepresentacion.setEnabled(true);
    } 
}
