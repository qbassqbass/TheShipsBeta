/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shipsBeta2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Qbass
 */
public class MainPanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener{
    private final int dim_x = 40; // number of cols
    private final int dim_y = 40; // number of rows
    private final int pSize = 20; //point size pSize x pSize
    private final int dimx = dim_x * pSize;
    private final int dimy = dim_y * pSize;
    
    private int yourColor = 2;
    
    private int point[][] = new int[dimx/pSize][dimy/pSize];
    private ArrayList<MyPoint> points = new ArrayList<MyPoint>();
    Graphics2D g2d;
    
    public int test;
    
    public MainPanel(){
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(this.dimx, this.dimy));
    }
    
    @Override
    protected void paintComponent(Graphics g){
    super.paintComponent(g);
        g2d = (Graphics2D) g;
        for(int i=0;i<this.dimx/pSize;i++){
            for(int j=0;j<this.dimy/pSize;j++){
                    g2d.setPaint(Color.WHITE);
                g2d.fill(new Rectangle2D.Double(i*pSize,j*pSize,pSize-1,pSize-1));
            }
        }
        paint(g2d);
    }
    
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
        for(MyPoint p : this.points){
            g2d.setPaint(p.getCol());
            g2d.fill(new Rectangle2D.Double(p.getX()*pSize, p.getY()*pSize, pSize - 1, pSize - 1));
        }
        
//        g2d.setColor(Color.red);
//        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
//        g2d.drawString("HELLO WORLD",0,20);
    }
    
    private void checkClick(int x,int y){
        x /= pSize; //x *= pSize;
        y /= pSize; //y *= pSize;
        
        if(point[x][y] > 0){
            point[x][y] = 0;
        }else{
            point[x][y] = this.yourColor;
        }
        repaint();
    }
    
    private void showPlaceForShip(int x, int y, int count){
//        System.out.println("oldx:"+oldx+" oldy:"+oldy);
        if(((oldx + count) > dim_x-1) && rotation)
            oldx -= ((oldx+count)-(dim_x));
        else if(((oldy + count) > dim_y-1) && !rotation)
            oldy -= ((oldy+count)-(dim_y));
        for(int i = 0; i < count; i++){
            if(rotation)
                this.point[oldx + i][oldy] = 0;
            else
                this.point[oldx][oldy + i] = 0;
        }
        int color;
        if(((x + count) > dim_x-1) && rotation)
            x -= ((x+count)-(dim_x));
        else if(((y + count) > dim_y-1) && !rotation)
            y -= ((y+count)-(dim_y));
        if(this.checkIfEligible(x, y, count)) color = 2;
        else color = 3;
        for(int i = 0; i < count; i++){
            if(rotation)
                this.point[x + i][y] = color;//this.yourColor;
            else
                this.point[x][y + i] = color;//this.yourColor;
        }
        repaint();
    }
    
    private boolean checkIfEligible(int x, int y, int count){
//        boolean eligible = true;
        for(int i = 0; i < count; i++){
            if(rotation){
                if(this.points.contains(new MyPoint(x + i, y, Color.BLACK)))
                    return false;
                if((this.points.contains(new MyPoint(x + i + 1, y, Color.BLACK))) |
                    (this.points.contains(new MyPoint(x + i - 1, y, Color.BLACK))) |
                    (this.points.contains(new MyPoint(x + i, y + 1, Color.BLACK))) |
                    (this.points.contains(new MyPoint(x + i, y - 1, Color.BLACK))))
                    return false;
            }else{
                if(this.points.contains(new MyPoint(x, y + i, Color.BLACK)))
                    return false;
                if((this.points.contains(new MyPoint(x + 1, y + i, Color.BLACK))) |
                    (this.points.contains(new MyPoint(x - 1, y + i, Color.BLACK))) |
                    (this.points.contains(new MyPoint(x, y + i + 1, Color.BLACK))) |
                    (this.points.contains(new MyPoint(x, y + i - 1, Color.BLACK))))
                    return false;
            }            
        }        
        return true;
    }
    
    private void placeShip(int x, int y, int count){
        System.err.print("Start x:"+x+" y:"+y);
        if(((x + count) > dim_x-1) && rotation)
            x -= ((x+count)-(dim_x));
        else if(((y + count) > dim_y-1) && !rotation)
            y -= ((y+count)-(dim_y));
        System.err.println("  End x:"+x+" y:"+y);
        if(this.checkIfEligible(x, y, count)){
            for(int i = 0; i < count; i++){
                if(rotation)
                    this.points.add(new MyPoint(x + i, y, Color.BLACK));
                else
                    this.points.add(new MyPoint(x, y + i, Color.BLACK));
            }            
            TheMainFrame.shipsAvailable[ship] -= 1;
            this.point = new int[dimx/pSize][dimy/pSize];
        }
        repaint();
    }
    
    private void checkClick2(int x, int y){
        x /= pSize;
        y /= pSize;
        int ship = (int)this.getClientProperty("ship");
        this.points.add(new MyPoint(x, y, Color.BLACK));
        switch(ship){
            case 0:{
                if(rotation){
                    this.points.add(new MyPoint(x+1, y, Color.BLACK));
                }else{
                    this.points.add(new MyPoint(x, y+1, Color.BLACK));
                }
                break;
            }
            case 1:{
                if(rotation){
                    this.points.add(new MyPoint(x+1, y, Color.BLACK));
                    this.points.add(new MyPoint(x+2, y, Color.BLACK));
                }else{
                    this.points.add(new MyPoint(x, y+1, Color.BLACK));
                    this.points.add(new MyPoint(x, y+2, Color.BLACK));
                }
                break;
            }
            case 2:{
                if(rotation){
                    this.points.add(new MyPoint(x+1, y, Color.BLACK));
                    this.points.add(new MyPoint(x+2, y, Color.BLACK));
                    this.points.add(new MyPoint(x+3, y, Color.BLACK));
                }else{
                    this.points.add(new MyPoint(x, y+1, Color.BLACK));
                    this.points.add(new MyPoint(x, y+2, Color.BLACK));
                    this.points.add(new MyPoint(x, y+3, Color.BLACK));
                }
                break;
            }
            case 3:{
                if(rotation){
                    this.points.add(new MyPoint(x+1, y, Color.BLACK));
                    this.points.add(new MyPoint(x+2, y, Color.BLACK));
                    this.points.add(new MyPoint(x+3, y, Color.BLACK));
                    this.points.add(new MyPoint(x+4, y, Color.BLACK));
                }else{
                    this.points.add(new MyPoint(x, y+1, Color.BLACK));
                    this.points.add(new MyPoint(x, y+2, Color.BLACK));
                    this.points.add(new MyPoint(x, y+3, Color.BLACK));
                    this.points.add(new MyPoint(x, y+4, Color.BLACK));
                }
                break;
            }
        }
        repaint();
    }
    
    private int checkShipLength(int ship){
        switch(ship){
            case 0: return 2;
            case 1: return 3;
            case 2: return 4;
            case 3: return 5;
        }
        return 0;
    }
    
    private int oldx,oldy;
    private int ship;
    private boolean rotation = false;
    public void moveMouse(int x, int y){
        x /= pSize;
        y /= pSize;
        if(x != oldx | y != oldy){
//            System.out.println("DEBUG: MoveMouse x:"+x+" y:"+y);
//            System.out.println("oldx:"+oldx+" oldy:"+oldy);
//            point[x][y] = this.yourColor;
            ship = (int)this.getClientProperty("ship");
            int count = this.checkShipLength(ship);
            if(TheMainFrame.shipsAvailable[ship] > 0)
            showPlaceForShip(x, y, count);
//            switch(ship){
//                case 0:{
//                    showPlaceForShip(x, y, 2);
//                    break;
//                }
//                case 1:{
//                    showPlaceForShip(x, y, 3);
//                    break;
//                }
//                case 2:{
//                    showPlaceForShip(x, y, 4);
//                    break;
//                }
//                case 3:{
//                    showPlaceForShip(x, y, 5);
//                    break;
//                }
//            }
//            switch(ship){
//                case 0:{
//                    if(rotation){
//                        point[x+1][y] = this.yourColor;
//                    }else{
//                        point[x][y+1] = this.yourColor;
//                    }
//                    break;
//                }
//                case 1:{
//                    if(rotation){
//                        point[x+1][y] = this.yourColor;
//                        point[x+2][y] = this.yourColor;
//                    }else{
//                        point[x][y+1] = this.yourColor;
//                        point[x][y+2] = this.yourColor;
//                    }
//                    break;
//                }
//                case 2:{
//                    if(rotation){
//                        point[x+1][y] = this.yourColor;
//                        point[x+2][y] = this.yourColor;
//                        point[x+3][y] = this.yourColor;
//                    }else{
//                        point[x][y+1] = this.yourColor;
//                        point[x][y+2] = this.yourColor;
//                        point[x][y+3] = this.yourColor;
//                    }
//                    break;
//                }
//                case 3:{
//                    if(rotation){
//                        point[x+1][y] = this.yourColor;
//                        point[x+2][y] = this.yourColor;
//                        point[x+3][y] = this.yourColor;
//                        point[x+4][y] = this.yourColor;
//                    }else{
//                        point[x][y+1] = this.yourColor;
//                        point[x][y+2] = this.yourColor;
//                        point[x][y+3] = this.yourColor;
//                        point[x][y+4] = this.yourColor;
//                    }
//                    break;
//                }
//            }
//            point[oldx][oldy] = 0;
//            switch(ship){
//                case 0:{
//                    if(rotation){
//                        point[oldx+1][oldy] = 0;
//                    }else{
//                        point[oldx][oldy+1] = 0;
//                    }
//                    break;
//                }
//                case 1:{
//                    if(rotation){
//                        point[oldx+1][oldy] = 0;
//                        point[oldx+2][oldy] = 0;
//                    }else{
//                        point[oldx][oldy+1] = 0;
//                        point[oldx][oldy+2] = 0;
//                    }
//                    break;
//                }
//                case 2:{
//                    if(rotation){
//                        point[oldx+1][oldy] = 0;
//                        point[oldx+2][oldy] = 0;
//                        point[oldx+3][oldy] = 0;
//                    }else{
//                        point[oldx][oldy+1] = 0;
//                        point[oldx][oldy+2] = 0;
//                        point[oldx][oldy+3] = 0;
//                    }
//                    break;
//                }
//                case 3:{
//                    if(rotation){
//                        point[oldx+1][oldy] = 0;
//                        point[oldx+2][oldy] = 0;
//                        point[oldx+3][oldy] = 0;
//                        point[oldx+4][oldy] = 0;
//                    }else{
//                        point[oldx][oldy+1] = 0;
//                        point[oldx][oldy+2] = 0;
//                        point[oldx][oldy+3] = 0;
//                        point[oldx][oldy+4] = 0;
//                    }
//                    break;
//                }
//            }
//            repaint();
        }
        oldx = x;
        oldy = y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if(e.getButton() == MouseEvent.BUTTON3){
//            this.rotation = !this.rotation;
//        }else{
//            //checkClick2(e.getX(), e.getY());
//            if(ship > -1)
//            if(TheMainFrame.shipsAvailable[ship] > 0){
//                TheMainFrame.shipsAvailable[ship] -= 1;
//                this.placeShip(e.getX()/pSize, e.getY()/pSize, this.checkShipLength(ship));
//            }
//        }
        //checkClick(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3){
            this.rotation = !this.rotation;
            this.point = new int[dimx/pSize][dimy/pSize];
            showPlaceForShip(e.getX()/pSize, e.getY()/pSize, this.checkShipLength(ship));
        }else{
            //checkClick2(e.getX(), e.getY());
            if(ship > -1)
            if(TheMainFrame.shipsAvailable[ship] > 0){
                this.placeShip(e.getX()/pSize, e.getY()/pSize, this.checkShipLength(ship));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
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

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.moveMouse(e.getX(), e.getY());
    }
    
}
