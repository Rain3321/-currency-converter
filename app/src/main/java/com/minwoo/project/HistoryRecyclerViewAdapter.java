package com.minwoo.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> implements OnItemMoveListener {

    private List<HistoryItem> items = new ArrayList<>();


    public void addData(HistoryItem item){
        items.add(0,item);


    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textMoney1, textMoney2, textCurrency1, textCurrency2;
        private ImageView imageView1, imageView2;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMoney1 = itemView.findViewById(R.id.history_money_1);
            textMoney2 = itemView.findViewById(R.id.history_money_2);
            textCurrency1 = itemView.findViewById(R.id.history_currency_1);
            textCurrency2 = itemView.findViewById(R.id.history_currency_2);
            imageView1 = itemView.findViewById(R.id.history_image_1);
            imageView2 = itemView.findViewById(R.id.history_image_2);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem item = items.get(position);
        holder.imageView1.setImageResource(item.getIconId1());
        holder.imageView2.setImageResource(item.getIconId2());
        holder.textMoney1.setText(item.getMoney1());
        holder.textMoney2.setText(item.getMoney2());
        holder.textCurrency1.setText(item.getCurrency1());
        holder.textCurrency2.setText(item.getCurrency2());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
