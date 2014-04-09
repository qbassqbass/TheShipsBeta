/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.io.Serializable;

/**
 *
 * @author Jakub
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 1L;
    private final int type;
    private final int sender;
    private final String message;
    private Object obj;
    
    public Message(int sender, String mess){ //only for player_id requests/responses
        this.type = -1;
        this.sender = sender;
        this.message = mess;
    }
    
    public Message(int type, int sender, String mess){
        this.type = type;
        this.sender = sender;
        this.message = mess;
    }
    public Message(int type, int sender, String mess, Object obj){
        this.message = mess;
        this.sender = sender;
        this.type = type;
        this.obj = obj;
    }
    
    public Object getObj(){
        return this.obj;
    }
    public String getMessage(){
        return this.message;
    }
    public int getType(){
        return this.type;
    }
    public int getSender(){
        return this.sender;
    }
    
    @Override
    public String toString(){
        return ""+this.type+"|"+this.sender+":"+this.message;
    }
}
