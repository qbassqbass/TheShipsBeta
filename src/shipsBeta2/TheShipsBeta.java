/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsBeta2;

import java.awt.EventQueue;

/**
 * @since 2014-03-04
 * @version 0.01b
 * @author qbass
 */
public class TheShipsBeta {

    /**
     * Main method of The Ships
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(new Runnable(){
            
            @Override
            public void run(){
                new MainFrame();
            }
        });
    }
}
