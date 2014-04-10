/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shipsBetaServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import message.*;
import shipsBeta2.MyPoint;

/**
 *
 * @author Jakub
 */
public class Server implements Runnable{
    
    private final Socket sockfd;
    private ObjectOutputStream oout; // output for objects
    private ObjectInputStream oin; //input for objects
    private String threadName;
    private Message message;
    private final int playerId;
    
    public Server(Socket sock, int id){
        this.sockfd = sock;
        this.playerId = id;
        try{
            this.oout = new ObjectOutputStream(this.sockfd.getOutputStream()); // output for objects
            this.oin = new ObjectInputStream(this.sockfd.getInputStream()); //input for objects
        }catch(IOException e){
            System.err.println("IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
        }
    }
    
    private void endThread(){
        try{
            oin.close();
            oout.close();
            sockfd.close();
            Thread.currentThread().interrupt();
        }catch(IOException e){
            
        }
    }

    private void checkMessage(Message mess) throws IOException{
        if(mess.getMessage().equals("IDREQ")){
            this.oout.writeObject(new Message(this.playerId, "IDRESP"));
        }
        else if(mess.getMessage().equals("SHIPS")){
            ArrayList<MyPoint> points = (ArrayList<MyPoint>)mess.getObj();
            if(points != null){
                ServerFrame.addBoard(this.playerId, points);
//                DEBUG THINGY
//                for(MyPoint p : points){
//                    System.out.println(p);
//                } 
            }
           this.oout.writeObject(new Message(0, this.playerId, "OK"));
        }
        else if(mess.getMessage().equals("SHOT")){
            MyPoint shot = (MyPoint)mess.getObj();
//            System.err.println("Player:"+playerId+" shot at:"+shot);
            //temporary things.
            int opponentId;
            if(playerId == 0) opponentId = 1; else opponentId = 0;
            if(ServerFrame.getBoard(opponentId).contains(shot)){
                System.err.println("Player:"+playerId+" hit "+opponentId+" at "+shot);
                this.oout.writeObject(new Message(2, this.playerId, "HIT", shot));
            }else{
                this.oout.writeObject(new Message(2, this.playerId, "NOHIT", shot));
            }
        }
    }
    @Override
    public void run() {
        this.threadName = Thread.currentThread().getName();
        while(!Thread.currentThread().isInterrupted()){
            try{
                message = (Message)this.oin.readObject();
                checkMessage(message);
//                System.out.println(message);
            } catch (SocketException e){ 
                System.err.println("Client Disconnected");
                this.endThread();
                Thread.currentThread().interrupt();
            } catch (IOException | ClassNotFoundException ex){
                System.err.println("Error: "+ex);
            }
        }
        
    }
    
}
