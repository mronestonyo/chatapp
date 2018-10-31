package com.mronestonyo.chatapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mronestonyo.chatapp.R;
import com.mronestonyo.chatapp.common.CommonFunctions;
import com.mronestonyo.chatapp.pojo.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatAppMessageRecyclerAdapter extends RecyclerView.Adapter<ChatAppMessageRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<ChatMessage> mGridData;
    private String mUsername;
    private static final int MY_MESSAGE = 0;
    private static final int OTHER_MESSAGE = 1;

    public ChatAppMessageRecyclerAdapter(Context context, String username) {
        mContext = context;
        mGridData = new ArrayList<>();
        mUsername = username;
    }

    public void add(ChatMessage item) {
        mGridData.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<ChatMessage> mGridData) {
        for (ChatMessage item : mGridData) {
            add(item);
        }
    }

    public void clear() {
        this.mGridData.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == MY_MESSAGE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_message_item, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_message_item_other, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessage chatMessage = mGridData.get(position);
        holder.txvUsername.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", chatMessage.getUsername()));
        holder.txvMessage.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", chatMessage.getMessage()));
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txvMessage;
        private TextView txvUsername;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvMessage = itemView.findViewById(R.id.txvMessage);
            txvUsername = itemView.findViewById(R.id.txvUsername);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mGridData.get(position);
        if (chatMessage.getUsername().equals(mUsername)) {
            return MY_MESSAGE;
        } else {
            return OTHER_MESSAGE;
        }
    }
}
