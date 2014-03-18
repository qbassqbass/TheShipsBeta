/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsBeta2;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author qbass
 */
public class MainFrame extends JFrame{
    public MainFrame(){
        super("..::The Ships Game::0.01b::..");
        JPanel panel = new MainPanel();
        
        add(panel);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
