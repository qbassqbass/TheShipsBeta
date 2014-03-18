/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

/**
 *
 * @author qbass
 */
public class LifeThread implements Runnable{
    private int cycleCount = 0;
    private int lifeCount = 0;
    private int sleepDelay = 500;
    private volatile boolean started = false;
    public volatile boolean pointsGot = false;
    private int[][] tmpPoints;
    private int[][] points;
    
    public void setTmpPoints(int[][] points){
//        this.tmpPoints = new int[points.length][points.length];
        this.tmpPoints = points;
        this.points = points;
    }
    public int[][] getTmpPoints(){
        return tmpPoints;
    }
    public int getLifeCount(){
        return this.lifeCount;
    }
    public void incLifeCount(){
        this.lifeCount++;
    }
    public void decLifeCount(){
        this.lifeCount--;
    }

    public int getCycleCount() {
        return this.cycleCount;
    }
    public void setSleepDelay(int del){
        this.sleepDelay = del;
    }
    public int getSleepDelay(){
        return this.sleepDelay;
    }
    public void setStarted(){
        System.out.println("Started");
        this.started = true;
    }
    public void unsetStarted(){
        System.out.println("Stopped");
        this.started = false;
    }
    public boolean isStarted(){
        return this.started;
    }
    public void reset(){
        this.cycleCount = 0;
        this.points = this.tmpPoints;
        this.pointsGot = false;
    }
    public LifeThread(int[][] p){
//        Thread th = new Thread(this);
        this.tmpPoints = p;
        this.points = this.tmpPoints;
//        th.start();
    }
    
    @Override
    public void run() {
        System.out.println("Running");
        while(true){
            if(this.started){
//                System.out.println("ok");
                try {
                    step();
                    this.pointsGot = false;
                    Thread.sleep(sleepDelay);
                } catch (InterruptedException ex) {
                    System.err.println("Interrupted: "+ex);
                }
            }
        }
    }
    
    private int countAliveNeighbours(int x, int y){
        int neighs = 0;
        if(x == 0){
            if(y == 0){
                neighs += points[x+1][y];
                neighs += points[x+1][y+1];
                neighs += points[x][y+1];
            }else if(y == points.length-1){
                neighs += points[x+1][y];
                neighs += points[x+1][y-1];
                neighs += points[x][y-1];
            }else{
                neighs += points[x+1][y];
                neighs += points[x+1][y-1];
                neighs += points[x+1][y+1];
                neighs += points[x][y-1];
                neighs += points[x][y+1];
            }
        }else if(x == points.length-1){
            if(y == 0){
                neighs += points[x-1][y];
                neighs += points[x-1][y+1];
                neighs += points[x][y+1];
            }else if(y == points.length-1){
                neighs += points[x-1][y];
                neighs += points[x-1][y-1];
                neighs += points[x][y-1];
            }else{
                neighs += points[x-1][y];
                neighs += points[x-1][y-1];
                neighs += points[x-1][y+1];
                neighs += points[x][y-1];
                neighs += points[x][y+1];
            }
        }else{
            if(y == 0){
                neighs += points[x-1][y];
                neighs += points[x-1][y+1];
                neighs += points[x][y+1];
                neighs += points[x+1][y];
                neighs += points[x+1][y+1];
            }else if(y == points.length-1){
                neighs += points[x-1][y];
                neighs += points[x-1][y-1];
                neighs += points[x][y-1];
                neighs += points[x+1][y];
                neighs += points[x+1][y-1];
            }else{
                neighs += points[x-1][y-1];
                neighs += points[x-1][y];
                neighs += points[x-1][y+1];

                neighs += points[x][y-1];
                neighs += points[x][y+1];

                neighs += points[x+1][y-1];
                neighs += points[x+1][y];
                neighs += points[x+1][y+1];
            }
            
        }
        return neighs;
    }
    
    private void step(){
//        System.out.println("step");
        int[][] postPoints = this.points;
        int[][] neighbours = new int[this.points.length][this.points.length];
        this.cycleCount++;
        for(int i = 0; i < this.points.length; i++){
            for(int j = 0; j < this.points[i].length; j++){
                neighbours[i][j] = this.countAliveNeighbours(i, j);
            }
        }
        for(int i = 0; i < this.points.length; i++){
            for(int j = 0; j < this.points[i].length; j++){
                if(this.points[i][j] == 0 & (neighbours[i][j] == 3)){
                    this.points[i][j] = 1;
                    this.incLifeCount();
                }else if(this.points[i][j] == 1){
                    if(neighbours[i][j] < 2 | neighbours[i][j] > 3){
                        this.points[i][j] = 0;
                        this.decLifeCount();
                    }
                }
            }
        }
//        if(this.points[i][j] == 0){
//                    if(this.countAliveNeighbours(i, j) == 3) postPoints[i][j] = 1;
//                }else if(this.points[i][j] == 1){
//                    if(this.countAliveNeighbours(i, j) < 2 
//                            | this.countAliveNeighbours(i, j) > 3) postPoints[i][j] = 0;
//                }
        this.points = postPoints;
    }
    
}
