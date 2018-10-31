package com.mronestonyo.chatapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mronestonyo.chatapp.R;
import com.mronestonyo.chatapp.adapter.ChatAppMessageRecyclerAdapter;
import com.mronestonyo.chatapp.base.BaseFragment;
import com.mronestonyo.chatapp.common.PreferenceUtils;
import com.mronestonyo.chatapp.pojo.ChatMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAppMessageFragment extends BaseFragment implements View.OnClickListener {

    private ScrollView scrollViewMessages;
    private RecyclerView recyclerViewMessage;
    private LinearLayoutManager lManager;
    private ChatAppMessageRecyclerAdapter mAdapter;
    private List<ChatMessage> mChatMessages;

    private TextView txvSendMessage;
    private EditText edtMessage;

    private String username;

    private List<ChatMessage> chatMessageList;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        showLogout(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_app_message, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        username = PreferenceUtils.getStringPreference(getViewContext(), "KEY_USERNAME");
        scrollViewMessages = (ScrollView) view.findViewById(R.id.scrollViewMessages);
        recyclerViewMessage = view.findViewById(R.id.recyclerViewMessage);
        lManager = new LinearLayoutManager(getViewContext());
        lManager.setStackFromEnd(true);
        recyclerViewMessage.setLayoutManager(lManager);
        recyclerViewMessage.setNestedScrollingEnabled(false);
        mAdapter = new ChatAppMessageRecyclerAdapter(getViewContext(), username);
        recyclerViewMessage.setAdapter(mAdapter);
        txvSendMessage = view.findViewById(R.id.txvSendMessage);
        txvSendMessage.setOnClickListener(this);
        edtMessage = view.findViewById(R.id.edtMessage);
        edtMessage.requestFocus();
    }

    private void initData() {
        mChatMessages = new ArrayList<>();
        chatMessageList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");
        myRef.child("room_all").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                chatMessageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    chatMessageList.add(new ChatMessage(chatMessage.getMessage(), chatMessage.getUsername()));
                }

                mAdapter.clear();
                mAdapter.addAll(chatMessageList);
                scrollDown();
                edtMessage.requestFocus();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("antonhttp", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvSendMessage: {
                String message = edtMessage.getText().toString().trim();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("messages");

                Map<String, String> map = new HashMap<>();
                map.put("Username", username);
                map.put("Message", message);

                myRef.child("room_all").push().setValue(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        if (databaseError != null) {
                            Toast.makeText(getViewContext(), "Something went wrong while saving your data. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else {
                            edtMessage.setText("");
                            edtMessage.requestFocus();
                        }

                    }
                });
                break;
            }
        }
    }

    private void scrollDown() {
        scrollViewMessages.post(new Runnable() {
            @Override
            public void run() {
                scrollViewMessages.fullScroll(View.FOCUS_DOWN);
            }
        });
        edtMessage.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
