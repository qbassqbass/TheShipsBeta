/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

import java.awt.EventQueue;

/**
 * @since 2013-07-26
 * @version 0.1b
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
