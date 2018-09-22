package com.prolandfarming.genericlogin;

import com.google.firebase.auth.FirebaseAuth;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage {

    public String messageID;
    public String text;
    public IUser sender;
    public IUser recipient;
    public Date messageDate;

    public Message(String messageID, String text, IUser sender, IUser recipient, Date messageDate){
        this.messageID = messageID;
        this.text = text;
        this.sender = sender;
        this.recipient = recipient;
        this.messageDate = messageDate;
    }


    @Override
    public String getId() {
        return messageID;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (sender.getId().equals(uid)){
            return recipient;
        }
        else{
            return sender;
        }
    }

    @Override
    public Date getCreatedAt() {
        return messageDate;
    }
}
