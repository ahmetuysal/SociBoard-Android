package com.prolandfarming.genericlogin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class AddChatroomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chatroom);



    }
}

class ChatBubble extends Item<ViewHolder> {

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getLayout() {
        return 0;
    }
}
