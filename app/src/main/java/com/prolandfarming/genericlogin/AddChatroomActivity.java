package com.prolandfarming.genericlogin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class AddChatroomActivity extends AppCompatActivity {

    private Spinner mSpinner;

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
