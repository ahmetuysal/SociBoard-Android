package com.prolandfarming.genericlogin.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chatroom implements Serializable{
    public String chatroomUID;
    public String chatroomName;
    public List<String> usersUID;
    public List<String> messagesUID;
    public String chatroomPhoto;
    public boolean isPrivate;

    public Chatroom(){

    }

    public Chatroom(String chatroomUID, String chatroomName, List<String> usersUID, List<String> messagesUID,
                    String chatroomPhoto, boolean isPrivate){
        this.chatroomUID = chatroomUID;
        this.chatroomName = chatroomName;
        if(usersUID == null)
            this.usersUID = new ArrayList<>();
        else
            this.usersUID = usersUID;

        if (messagesUID == null)
            this.messagesUID = new ArrayList<>();
        else
            this.messagesUID = messagesUID;
        this.chatroomPhoto = chatroomPhoto;
        this.isPrivate = isPrivate;
    }

}
