package com.prolandfarming.genericlogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FlightChatsActivity extends AppCompatActivity {

    private DialogsList mDialogsList;
    private ArrayList<Dialog> dialogs = new ArrayList<>();
    private ArrayList<FirebaseDialog> fbDialogs = new ArrayList<>();
    private DatabaseReference mDBDialogsReference = FirebaseDatabase.getInstance().getReference("/dialogs/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_chats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_add_flight_chat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDialogsList = findViewById(R.id.flights_dialogs_list);

        DialogsListAdapter<IDialog> dialogsListAdapter = new DialogsListAdapter<>(R.layout.activity_flight_chats, new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                Picasso.get().load(url).into(imageView);
            }
        });

        mDBDialogsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseDialog fbDialog = dataSnapshot.getValue(FirebaseDialog.class);
                fbDialogs.add(fbDialog);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //dialogsListAdapter.addItems(fbDialogs);

        // TODO: mock

        dialogsListAdapter.addItem(MockupData.getDialogs().get(0));
        //dialogsListAdapter.addItem(new Dialog(UUID.randomUUID().toString(), "https://yt3.ggpht.com/a-/AN66SAwIalpMWy5OO0YTmamwLdUD4rqpqwRLOaf2mw=s88-mo-c-c0xffffffff-rj-k-no", "MyDial2", new ArrayList<IUser>(), new ArrayList<IMessage>(), 1));


        mDialogsList.setAdapter(dialogsListAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
