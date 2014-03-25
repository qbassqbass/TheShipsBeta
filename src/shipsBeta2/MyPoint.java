/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsBeta2;

import ships.*;
import java.awt.Color;

/**
 * @version 0.4
 * @since 2013-07-20
 * @author qbass
 */
public class MyPoint {
    private int x,y;
    private Color col;
    private final int max = 400; // IMPORTANT TO DO!!
    
    public MyPoint(int x,int y, Color col){
        if(x>max)this.x = max;
        else if(x<0) this.x = 0;
        else this.x = x;
        if(y>max)this.y = max;
        else if(y<0) this.y = 0;
        else this.y = y;
        this.col = col;
//        System.out.println("x:"+this.x+" y:"+this.y);
    }
    @Override
    public boolean equals(Object obj){
        boolean isEqual = false;
        if (this.getClass() == obj.getClass()){
            MyPoint p = (MyPoint) obj;
            if(p.x == this.x && p.y == this.y) isEqual = true;
        }
            
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        return hash;
    }
    public void setCol(Color col){
        this.col = col;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Color getCol(){
        return this.col;
    }
    
}
