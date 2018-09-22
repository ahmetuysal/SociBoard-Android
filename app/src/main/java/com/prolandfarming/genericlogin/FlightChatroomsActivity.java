package com.prolandfarming.genericlogin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolandfarming.genericlogin.Models.Chatroom;
import com.prolandfarming.genericlogin.Models.Message;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FlightChatroomsActivity extends AppCompatActivity {

    private DatabaseReference mDBChatroomsReference = FirebaseDatabase.getInstance().getReference("/chatrooms/");

    private RecyclerView mRecyler;
    private ArrayList<Chatroom> chatRooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_chatrooms);

        Toolbar myToolbar = findViewById(R.id.flight_chatrooms_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_dark);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.sociboard_logo_action_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRecyler = findViewById(R.id.chatrooms_recylerview);

        getChatrooms();
    }

    private void getChatrooms(){
        final GroupAdapter adapter = new GroupAdapter<ViewHolder>();
        mDBChatroomsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chatroom cRoom = dataSnapshot.getValue(Chatroom.class);
                // TODO if user exist in that group
                adapter.add(new ChatroomItem(cRoom));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // TODO
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // TODO
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // TODO
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO
            }
        });
        mRecyler.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.my_flights_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:

                return true;
            case R.id.action_search:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class ChatroomItem extends Item<ViewHolder>{

    private Chatroom mChatroom;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private DatabaseReference mDBMessagesReference = FirebaseDatabase.getInstance().getReference("/messages/");

    public ChatroomItem(Chatroom chatroom){
        mChatroom = chatroom;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        final Message[] lastMessage = {null};
        if (mChatroom.messagesUID != null && !mChatroom.messagesUID.isEmpty()) {
            String lastUID = mChatroom.messagesUID.get(mChatroom.messagesUID.size() - 1);
            mDBMessagesReference.child(lastUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lastMessage[0] = dataSnapshot.getValue(Message.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        View itemView = viewHolder.itemView;
        TextView chatName = itemView.findViewById(R.id.chatroom_name);
        chatName.setText(mChatroom.chatroomName);
        if (lastMessage[0] != null){
            TextView lastMsg = itemView.findViewById(R.id.chatroom_last_message);
            lastMsg.setText(lastMessage[0].text);
            TextView chatDate = itemView.findViewById(R.id.chatroom_last_message_date);
            chatDate.setText(df.format(lastMessage[0].messageDate));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.chatroom_row_flight;
    }
}
