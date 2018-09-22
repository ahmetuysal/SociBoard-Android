package com.prolandfarming.genericlogin.Models;
import java.util.Date;

public class Message {

    public String messageUID;
    public String text;
    public String senderUID;
    public String senderName;
    public Date messageDate;

    public Message(String messageID, String text, String senderUID, String senderName, Date messageDate){
        this.messageUID = messageID;
        this.text = text;
        this.senderUID = senderUID;
        this.senderName = senderName;
        this.messageDate = messageDate;
    }

    public Message(){

    }
}
