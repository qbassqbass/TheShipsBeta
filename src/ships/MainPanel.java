/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author qbass
 */
public class MainPanel extends JPanel implements MouseListener, KeyListener{
    private final int rowscols = 50; // numer of rows/cols
    private final int bSize = this.rowscols * this.pSize;//400;
    private final int pSize = 10; // size of one point
    private final int dim = bSize + pSize; // window dimensions
    
    private int actualColor = 1;
    
    private LifeThread life;
    private Thread lifeth;
    
    private int point[][] = new int[dim/pSize][dim/pSize];
    private ArrayList<MyPoint> points = new ArrayList<MyPoint>();
    Graphics2D g2d;
    
    public MainPanel(){
        addMouseListener(this);
//        addKeyListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(this.dim, this.dim));
        life = new LifeThread(point);
        life.setTmpPoints(point);
        life.setSleepDelay(100);
        lifeth = new Thread(life);
        lifeth.start();
//        lifeth.interrupt();
        Thread lth = new Thread(new LifeCheckTh());
        lth.start();
    }
    
    class LifeCheckTh implements Runnable{
        public int heh = 0;
        @Override
        public void run() {
            while(true){
                if(!life.pointsGot){
                    point = life.getTmpPoints();
                    life.pointsGot = true;
                    repaint();
                }
            }
        }
    }
    
    private void chColor(MouseEvent e){
        Robot robot;
        try {
            Color col;
            robot = new Robot();
            int x,y,xos,yos;
            x = e.getX();
            y = e.getY();
            xos = e.getXOnScreen();
            yos = e.getYOnScreen();
            Color color = robot.getPixelColor(xos, yos);
            System.out.println("x="+x+" y="+y+" "+color.toString());
            if(color.equals(Color.BLACK)) col = Color.WHITE;
            else col = Color.BLACK;
            x /= pSize;
            y /= pSize;
            x *= pSize;
            y *= pSize;
            points.add(new MyPoint(x,y,col));
            repaint();
        } catch (AWTException ex) {
        }
    }
    private void checkClick(int x,int y){
        x /= pSize; //x *= pSize;
        y /= pSize; //y *= pSize;
        System.out.println("x="+x+" y="+y+" AliveNeighbours="+this.countAliveNeighbours(x, y));
        if(point[x][y] > 0){
            point[x][y] = 0;
            life.decLifeCount();
        }else{
            point[x][y] = this.actualColor;
            life.incLifeCount();
        }
        repaint();
    }
    private int countAliveNeighbours(int x, int y){
        int neighs = 0;
        if(x == 0){
            if(y == 0){
                neighs += point[x+1][y];
                neighs += point[x+1][y+1];
                neighs += point[x][y+1];
            }else if(y == point.length-1){
                neighs += point[x+1][y];
                neighs += point[x+1][y-1];
                neighs += point[x][y-1];
            }else{
                neighs += point[x+1][y];
                neighs += point[x+1][y-1];
                neighs += point[x+1][y+1];
                neighs += point[x][y-1];
                neighs += point[x][y+1];
            }
        }else if(x == point.length-1){
            if(y == 0){
                neighs += point[x-1][y];
                neighs += point[x-1][y+1];
                neighs += point[x][y+1];
            }else if(y == point.length-1){
                neighs += point[x-1][y];
                neighs += point[x-1][y-1];
                neighs += point[x][y-1];
            }else{
                neighs += point[x-1][y];
                neighs += point[x-1][y-1];
                neighs += point[x-1][y+1];
                neighs += point[x][y-1];
                neighs += point[x][y+1];
            }
        }else{
            if(y == 0){
                neighs += point[x-1][y];
                neighs += point[x-1][y+1];
                neighs += point[x][y+1];
                neighs += point[x+1][y];
                neighs += point[x+1][y+1];
            }else if(y == point.length-1){
                neighs += point[x-1][y];
                neighs += point[x-1][y-1];
                neighs += point[x][y-1];
                neighs += point[x+1][y];
                neighs += point[x+1][y-1];
            }else{
                neighs += point[x-1][y-1];
                neighs += point[x-1][y];
                neighs += point[x-1][y+1];
                
                neighs += point[x][y-1];
                neighs += point[x][y+1];

                neighs += point[x+1][y-1];
                neighs += point[x+1][y];
                neighs += point[x+1][y+1];
            }
            
        }
        return neighs;
    }
    
    @Override
    protected void paintComponent(Graphics g){
    super.paintComponent(g);
        g2d = (Graphics2D) g;
        for(int i=0;i<this.dim/pSize;i++){
            for(int j=0;j<this.dim/pSize;j++){
                    g2d.setPaint(Color.WHITE);
                g2d.fill(new Rectangle2D.Double(i*pSize,j*pSize,pSize-1,pSize-1));
            }
        }
        paint(g2d);
    }
//    private void paint(Graphics2D g2d){
//        for(int i = 0;i < points.size();i++){
//            g2d.setPaint(points.get(i).getCol());
//            int x = (int) points.get(i).getX(), y = (int) points.get(i).getY();
//            g2d.fill(new Rectangle2D.Double(x,y,pSize-1,pSize-1));
////            g2d.fill(new RoundRectangle2D.Double(x,y,pSize,pSize,2,2));
//        }
//        g2d.setColor(Color.red);
//        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
//        g2d.drawString("PLAY",0,20);
//        g2d.drawString("STOP",40, 20);
//    }
    private void paint(Graphics2D g2d){
        for(int i = 0; i < point.length;i++){
            for(int j = 0;j < point[i].length;j++){
                if(point[i][j] == 1) g2d.setPaint(Color.BLACK);
                else if(point[i][j] == 2) g2d.setPaint(Color.BLUE);
                else if(point[i][j] == 3) g2d.setPaint(Color.RED);
                else g2d.setPaint(Color.WHITE);
                g2d.fill(new Rectangle2D.Double(i*pSize,j*pSize,pSize-1,pSize-1));
            }
        }
        g2d.setColor(Color.red);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2d.drawString("PLAY",0,20);
        g2d.drawString("STOP",40, 20);
        g2d.drawString("Cycle: "+life.getCycleCount(), 80, 20);
        g2d.drawString("Life: "+life.getLifeCount(),180,20);
    }
    
    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            if(this.actualColor == 3) this.actualColor = 0;
            else this.actualColor++;
        }else if(e.getX()<40 & e.getY()<20){
            System.out.println("PLAY");
            this.life.setStarted();
//            this.lifeth.start();
        }else if(e.getX()<80 & e.getY()<20){
            System.out.println("STOP");
            this.life.unsetStarted();
//            this.lifeth.interrupt();
//        }else chColor(e);
        }else if(e.getX()<120 & e.getY()<20){
            System.out.println("RESET");
            this.life.reset();
        }else checkClick(e.getX(), e.getY());
//        this.point = life.getTmpPoints();
//        System.out.println(life.isStarted());
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
