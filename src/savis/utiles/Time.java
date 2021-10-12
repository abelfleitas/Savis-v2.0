/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savis.utiles;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Time {
    
    private Timer timer = new Timer(); 
    private int segundos=0;

    class Contador extends TimerTask {
        public void run() {
            segundos++;
            System.out.println(segundos);
        }
    }
    public void Contar()
    {
        timer = new Timer();
        timer.schedule(new Contador(), 0, 1000);
    }
    //Detiene el contador
    public void Detener() {
        timer.cancel();
    }
    public int getSegundos()
    {
        return this.segundos;
    }
    
    public void setSegundos(int segundos)
    {
        this.segundos = segundos;
    }
}
