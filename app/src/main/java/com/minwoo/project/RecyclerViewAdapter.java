package com.minwoo.project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {




    //Add Fragment 에서의 Click Event 를 위한 인터페이스 생성
    public interface OnItemClickListener{
        void onItemClicked(RecyclerView recyclerView, int position, View v);
        void onItemDoubleClicked(RecyclerView recyclerView, int position, View v);
    }
    private ArrayList<ListItem> items;

    private ArrayList<ListItem> unFilteredList;

    public RecyclerViewAdapter(ArrayList<ListItem> list){
        super();

        unFilteredList = list;
        items = list;
    }

    //RecyclerView 에 들어갈 View Holder 정의
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNation;
        private ImageView imageView;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNation = itemView.findViewById(R.id.nation_name);
            imageView = itemView.findViewById(R.id.national_flag);
        }
    }

    public ListItem getListItem(int position){
        return items.get(position);
    }


    @NonNull
    @Override
    //아이템 뷰를 위한 뷰 홀더 객체를 생성하여 리턴함
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);
        holder.imageView.setImageResource(item.getIconId());
        holder.mNation.setText(item.getCountryStr());
    }
    //전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ListItem> filteredResults = null;
                String charString = constraint.toString();

                if(charString.isEmpty()){
                    filteredResults = unFilteredList;
                }else{
                    ArrayList<ListItem> filteringList = new ArrayList<>();
                    for(ListItem item : unFilteredList){
                        if(item.getCountryStr().toLowerCase().contains(charString.toLowerCase())){
                            filteringList.add(item);
                        }
                    }
                    filteredResults = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredResults;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (ArrayList<ListItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
