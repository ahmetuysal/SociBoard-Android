package com.prolandfarming.genericlogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolandfarming.genericlogin.Models.Chatroom;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AddChatroomActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private DatabaseReference mDBChatroomsReference = FirebaseDatabase.getInstance().getReference("/chatrooms/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chatroom);



        mSpinner = findViewById(R.id.add_chatroom_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.add_chat_options, android.R.layout.simple_spinner_item);
        mSpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        Button mockBtn = findViewById(R.id.button_mock);
        mockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpinner.performClick();
                Toast.makeText(AddChatroomActivity.this, "HELLO", Toast.LENGTH_SHORT).show();
            }
        });

        Button addGeneral = findViewById(R.id.add_chatroom_general);
        addGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Chatroom[] chatroom = new Chatroom[1];
                DatabaseReference mChatroomRef = mDBChatroomsReference.child("2751b63f-3de7-4413-a441-b0f2f6f891aa");
                DatabaseReference mChatroomMessagesRef = mChatroomRef.child("messagesUID");
                final List<String> messages = new ArrayList<>();
                mChatroomMessagesRef.orderByKey().limitToFirst(100).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        messages.add(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mChatroomRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String cRoomName = dataSnapshot.child("chatroomName").getValue(String.class);
                        String cRoomPhoto = dataSnapshot.child("chatroomPhoto").getValue(String.class);
                        String cRoomUID = dataSnapshot.child("chatroomUID").getValue(String.class);

                        chatroom[0] = new Chatroom(cRoomUID, cRoomName, new ArrayList<String>(), messages, cRoomPhoto, false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                intent.putExtra(getString(R.string.INTENT_PARAM_KEY_CHATROOM), chatroom[0]);
                startActivity(intent);
                finish();
                // TODO
            }
        });



        //chatrooms.add("127224c4-b9c0-49af-afa1-b57af2d91821");// merve 10d
        //chatrooms.add("2751b63f-3de7-4413-a441-b0f2f6f891aa"); // general
        //chatrooms.add("b86eb5ac-7a12-4446-b223-73ba3d5053f3"); // travel
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
