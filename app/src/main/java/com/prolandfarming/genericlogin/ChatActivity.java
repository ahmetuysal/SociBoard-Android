package com.prolandfarming.genericlogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolandfarming.genericlogin.Models.Chatroom;
import com.prolandfarming.genericlogin.Models.Message;
import com.prolandfarming.genericlogin.Models.User;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private Chatroom mChatroom;
    private RecyclerView mRecyler;
    private DatabaseReference mDBMessagesReference = FirebaseDatabase.getInstance().getReference("/messages/");
    private DatabaseReference mChatroomReference = FirebaseDatabase.getInstance().getReference("/chatrooms/");
    private User mUser;
    private String mUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private EditText mEdittext;
    final GroupAdapter adapter = new GroupAdapter<ViewHolder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            mUser = (User) InternalStorage.readObject(this, "user_credentials.data");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();

        mChatroom = (Chatroom) intent.getSerializableExtra(getString(R.string.INTENT_PARAM_KEY_CHATROOM));
        //getSupportActionBar().setTitle(mChatroom.chatroomName);
        mRecyler = findViewById(R.id.chat_recyler);

        mEdittext = findViewById(R.id.chat_entry_text);

        FloatingActionButton fab_send_btn = findViewById(R.id.chat_send_message_fab);
        fab_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtmsg = mEdittext.getText().toString();
                if(!txtmsg.isEmpty())
                    sendMessage(txtmsg);
            }
        });
        initMessages();
    }

    private void initMessages() {
        if(mChatroom != null && mChatroom.messagesUID != null) {
            for (String msgUID : mChatroom.messagesUID) {
                mDBMessagesReference.child(msgUID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Message msg = dataSnapshot.getValue(Message.class);
                        if (msg.senderUID.equals(mUserUID)) {
                            adapter.add(new ToMessageItem(msg));
                        } else {
                            adapter.add(new FromMessageItem(msg));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
        mRecyler.setAdapter(adapter);

    }

    private void sendMessage(String message) {
        Message newMessage = new Message(UUID.randomUUID().toString(), message, mUserUID, mUser.name, Calendar.getInstance().getTime());
        adapter.add(new ToMessageItem(newMessage));
        mDBMessagesReference.child(newMessage.messageUID).setValue(newMessage);

        mChatroomReference.child(mChatroom.chatroomUID).child("messagesUID/").child((System.currentTimeMillis())+"").setValue(newMessage.messageUID);
    }
}

class FromMessageItem extends Item<ViewHolder>{

    private DatabaseReference mDBUsersReference = FirebaseDatabase.getInstance().getReference("/users/");
    Message mMessage;

    public FromMessageItem(Message message) {
        mMessage = message;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        final View itemView = viewHolder.itemView;
        mDBUsersReference.child(mMessage.senderUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User sender = dataSnapshot.getValue(User.class);
                if (sender.photoUrl != null){
                    CircleImageView senderPhoto = itemView.findViewById(R.id.chat_sender_image);
                    Picasso.get().load(sender.photoUrl).into(senderPhoto);
                }
                TextView titleText = itemView.findViewById(R.id.chat_text_sender);
                titleText.setText(sender.name);
                TextView messageText = itemView.findViewById(R.id.chat_text);
                messageText.setText(mMessage.text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getLayout() {
       return R.layout.chat_from_row;
    }
}

class ToMessageItem extends Item<ViewHolder>{

    Message mMessage;

    public ToMessageItem(Message message) {
        mMessage = message;
    }
    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        final View itemView = viewHolder.itemView;
        TextView messageText = itemView.findViewById(R.id.chat_to_text);
        messageText.setText(mMessage.text);
    }

    @Override
    public int getLayout() {
        return R.layout.chat_to_row;
    }
}

