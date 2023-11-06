package com.example.quickquiz;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class Room_ListAdapter  extends RecyclerView.Adapter<Room_ListAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Room_ListClass> rooms;
    Room_ListAdapter(Context context, List<Room_ListClass> rooms){
        this.rooms=rooms;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public Room_ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.room, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(Room_ListAdapter.ViewHolder holder, int position) {
        Room_ListClass room = rooms.get(position);
        holder.nameView.setText(room.getName());
        holder.playersView.setText(room.getCount_players()+"/"+room.getMax_count_players());
        holder.categoryView.setText(room.getCategory());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, playersView,categoryView;

        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            playersView = view.findViewById(R.id.players);
            categoryView = view.findViewById(R.id.category);
        }
    }
}
