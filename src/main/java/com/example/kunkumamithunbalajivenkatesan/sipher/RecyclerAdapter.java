package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<String> time, shift, message;

    RecyclerAdapter(List<String> time, List<String> shift, List<String> message) {
        this.time = time;
        this.shift = shift;
        this.message = message;
    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_v2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.card_message.setText(message.get(position));
        holder.card_shift.setText(shift.get(position));
        holder.card_time.setText(time.get(position));
        switch (new Random().nextInt(6)){
            case 0:holder.card_image.setImageResource(R.drawable.ic_ir);break;
            case 1:holder.card_image.setImageResource(R.drawable.ic_ca);break;
            case 2:holder.card_image.setImageResource(R.drawable.ic_h);break;
            case 3:holder.card_image.setImageResource(R.drawable.ic_he);break;
            case 4:holder.card_image.setImageResource(R.drawable.ic_bw);break;
            case 5:holder.card_image.setImageResource(R.drawable.ic_v);break;
        }

    }

    @Override
    public int getItemCount() {
        return shift.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView card_time, card_shift, card_message;
        private ImageView card_image;
        private MyViewHolder(View view) {
            super(view);
            card_time = view.findViewById(R.id.time_message_card_view);
            card_shift = view.findViewById(R.id.shift_card_view);
            card_message = view.findViewById(R.id.encrypted_message_card_view);
            card_image = view.findViewById(R.id.image_card_view);
        }
    }
}
