package com.samsoft.xpendify.widget.swipe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.samsoft.xpendify.widget.swipe.SwipeLayout;
import com.samsoft.xpendify.widget.swipe.implments.SwipeItemMangerImpl;
import com.samsoft.xpendify.widget.swipe.interfaces.SwipeAdapterInterface;
import com.samsoft.xpendify.widget.swipe.interfaces.SwipeItemMangerInterface;
import com.samsoft.xpendify.widget.swipe.util.Attributes;

import java.util.List;

public abstract class RecyclerSwipeAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements SwipeItemMangerInterface, SwipeAdapterInterface {

    public SwipeItemMangerImpl swipeItemManger = new SwipeItemMangerImpl(this);

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH viewHolder, final int position);

    @Override
    public void notifyDatasetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void openItem(int position) {
        swipeItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        swipeItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        swipeItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        swipeItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return swipeItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return swipeItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        swipeItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return swipeItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return swipeItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        swipeItemManger.setMode(mode);
    }
}
