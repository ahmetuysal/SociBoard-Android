package com.prolandfarming.genericlogin;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;

public class FirebaseDialog {

    public String dialogID;
    public String dialogPhoto;
    public String dialogName;
    public ArrayList<String> usersUID;
    public ArrayList<String> messagesUID;
    // public int unreadCount; TODO

    public FirebaseDialog(String dialogID, String dialogPhoto, String dialogName, ArrayList<String> usersUID, ArrayList<String> messagesUID){
        this.dialogID = dialogID;
        this.dialogPhoto = dialogPhoto;
        this.dialogName = dialogName;
        this.usersUID = usersUID;
        this.messagesUID = messagesUID;
    }

    public FirebaseDialog(){
        this(null, null, null, null, null);
    }

    public Dialog toDialog(){
        DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference("/users/");
        final ArrayList<IUser> users = new ArrayList<>();
        final ArrayList<IMessage> messages = new ArrayList<>();
        for(String userUID : usersUID){
            mDBReference.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    users.add(dataSnapshot.getValue(User.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("firabasedialog_todialog", "loadUserCancelled", databaseError.toException());
                }
            });
        }
        DatabaseReference mDBMessageReference = FirebaseDatabase.getInstance().getReference("/messages/");
        for(String messageUID : messagesUID){
            mDBMessageReference.child(messageUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    messages.add(dataSnapshot.getValue(FirebaseMessage.class).toMessage());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("firabasemessage_tomsg", "loadUserCancelled", databaseError.toException());
                }
            });
        }


        //return new Dialog(dialogID, dialogPhoto, dialogName, users, messages, 0);
        return null;
    }


}
