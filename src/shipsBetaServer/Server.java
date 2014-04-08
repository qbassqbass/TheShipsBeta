/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shipsBetaServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.*;

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
    
    public Server(Socket sock){
        this.sockfd = sock;
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

    @Override
    public void run() {
        this.threadName = Thread.currentThread().getName();
        while(!Thread.currentThread().isInterrupted()){
            try{
                message = (Message)this.oin.readObject();
                System.out.println(message);
            } catch (SocketException e){ 
                System.err.println("Client Disconnected");
                Thread.currentThread().interrupt();
            } catch (IOException | ClassNotFoundException ex){
                System.err.println("Error: "+ex);
            }
        }
        
    }
    
}
