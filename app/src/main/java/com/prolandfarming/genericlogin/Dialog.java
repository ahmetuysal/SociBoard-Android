package com.prolandfarming.genericlogin;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dialog implements IDialog<Message> {

    public String dialogID;
    public String dialogPhoto;
    public String dialogName;
    public ArrayList<User> users;
    public ArrayList<Message> messages;
    public int unreadCount;

    public Dialog(String dialogID, String dialogPhoto, String dialogName, ArrayList<User> users, ArrayList<Message> messages, int unreadCount){
        this.dialogID = dialogID;
        this.dialogPhoto = dialogPhoto;
        this.dialogName = dialogName;
        this.users = users;
        this.messages = messages;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return dialogID;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    @Override
    public void setLastMessage(Message message) {
        messages.add(message);
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }
}
