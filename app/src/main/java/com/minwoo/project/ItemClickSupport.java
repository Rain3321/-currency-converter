package com.minwoo.project;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private OnDoubleClickListener mOnDuOnDoubleClickListener =new OnDoubleClickListener() {
        @Override
        public void onDoubleClick(View v) {
            if(mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemDoubleClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
        @Override
        public void onSingClick(View v) {
            if(mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if(mOnItemClickListener != null){
                view.setOnClickListener(mOnDuOnDoubleClickListener);
            }
        }
        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };
    private ItemClickSupport(RecyclerView recyclerView){
        this.mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.add_country_list, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachStateChangeListener);
    }
    public static ItemClickSupport addTo(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.add_country_list);
        if(support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }
    public static ItemClickSupport removeFrom(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.add_country_list);
        if(support != null){
            support.detach(view);
        }
        return support;
    }
    public ItemClickSupport setOnItemClickListener(RecyclerViewAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachStateChangeListener);
        view.setTag(R.id.add_country_list, null);
    }

}
