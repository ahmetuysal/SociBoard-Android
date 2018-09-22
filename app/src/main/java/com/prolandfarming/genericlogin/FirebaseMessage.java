package com.prolandfarming.genericlogin;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class FirebaseMessage {

    public String messageID;
    public String text;
    public String senderUID;
    public String recipientUID;
    public Date messageDate;

    public FirebaseMessage(String messageID, String text, String senderUID, String recipientUID, Date messageDate){
        this.messageID = messageID;
        this.text = text;
        this.senderUID = senderUID;
        this.recipientUID = recipientUID;
        this.messageDate = messageDate;
    }

    public Message toMessage(){
        User sender = null;
        User recipient = null;
        DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference("/users/");
        mDBReference.child(senderUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDBReference.child(recipientUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (sender != null && recipient != null){
            return new Message(messageID, text, sender, recipient, messageDate);
        }else{
            return null;
        }
    }


}
