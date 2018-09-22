package com.prolandfarming.genericlogin;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, IUser {
    public boolean isMale;
    public String uid;
    public String name;
    public String surname;
    public String email;
    public String phoneNumber;
    public String photoUrl;
    public ArrayList<String> dialogsUID;

    public User (String uid, String email, String name, String surname, String phoneNumber, boolean isMale){
        this (uid, email, name, surname, phoneNumber, isMale,
                "https://scontent.fada1-9.fna.fbcdn.net/v/t1.0-9/16196015_10154888128487744_6901111466535510271_n.png?_nc_cat=0&oh=7c376a1d2a48b490c25f842966b160ae&oe=5C2F0CE9", new ArrayList<String>());
    }
    public User (String uid, String email, String name, String surname, String phoneNumber, boolean isMale, String photoUrl, ArrayList<String> dialogsUID){
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.isMale = isMale;
        this.photoUrl = photoUrl;
        this.dialogsUID = dialogsUID;
    }


    @Override
    public String getId() {
        return uid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return photoUrl;
    }

    public User(){}
}
