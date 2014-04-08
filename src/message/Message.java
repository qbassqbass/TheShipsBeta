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
    private int type;
    private String message;
    private Object obj;
    
    public Message(int type, String mess){
        this.type = type;
        this.message = mess;
    }
    public Message(int type, String mess, Object obj){
        this.message = mess;
        this.type = type;
        this.obj = obj;
    }
    
    @Override
    public String toString(){
        return ""+this.type+":"+this.message;
    }
}
