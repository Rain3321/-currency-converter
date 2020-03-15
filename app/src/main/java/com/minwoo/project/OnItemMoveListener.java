package com.minwoo.project;

public interface OnItemMoveListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemRemove(int position);
}

