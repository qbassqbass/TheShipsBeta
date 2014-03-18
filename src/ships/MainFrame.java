/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author qbass
 */
public class MainFrame extends JFrame{
    public MainFrame(){
        super("The Game of Life");
        JPanel panel = new MainPanel();
        
        add(panel);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
