package com.prolandfarming.genericlogin;

import java.io.Serializable;

public class User implements Serializable{
    public boolean isMale;
    public String name;
    public String surname;
    public String email;
    public String phoneNumber;

    public User (String email, String name, String surname, String phoneNumber, boolean isMale){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.isMale = isMale;
    }

    public User(){}
}
