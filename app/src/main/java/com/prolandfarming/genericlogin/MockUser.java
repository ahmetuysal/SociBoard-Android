package com.prolandfarming.genericlogin;

import com.stfalcon.chatkit.commons.models.IUser;

public class MockUser implements IUser {

    String id;
    String name;
    String avatar;

    public MockUser(String id, String name, String avatar){
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
