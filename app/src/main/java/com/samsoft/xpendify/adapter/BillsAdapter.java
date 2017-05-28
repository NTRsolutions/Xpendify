package com.samsoft.xpendify.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.samsoft.xpendify.R;
import com.samsoft.xpendify.animation.animators.holder.AnimateViewHolder;
import com.samsoft.xpendify.callback.OnItemTouchListener;
import com.samsoft.xpendify.model.BillsData;
import com.samsoft.xpendify.widget.others.EmptyViewHolder;
import com.samsoft.xpendify.widget.swipe.SimpleSwipeListener;
import com.samsoft.xpendify.widget.swipe.SwipeLayout;
import com.samsoft.xpendify.widget.swipe.adapters.RecyclerSwipeAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Fred on 26-Oct-15.
 */

public class BillsAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NULL = 1;
    List<BillsData> billsData = Collections.emptyList();
    private OnItemTouchListener onItemTouchListener;

    public BillsAdapter(List<BillsData> billsData) {
        this.billsData = billsData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_NULL:
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_empty_view, viewGroup, false);
                return new EmptyViewHolder(v1);
            default:
                View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bills_adapter_list_item, viewGroup, false);
                return new ViewHolder(v2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case TYPE_NULL:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) viewHolder;
                emptyViewHolder.setInfo(R.string.no_expense);
                break;
            default:
                ViewHolder swipeViewHolder = (ViewHolder) viewHolder;
                swipeViewHolder.tvCategory.setText(billsData.get(position).getBill_category());
                swipeViewHolder.tvAmount.setText(billsData.get(position).getBill_amount());
                swipeViewHolder.tvDueDate.setText(billsData.get(position).getDue_date());
                swipeViewHolder.tvStatus.setText(billsData.get(position).getStatus());
                swipeViewHolder.tvNote.setText(billsData.get(position).getNote());
                swipeItemManger.bind(swipeViewHolder.itemView, position);
                swipeViewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                    @Override
                    public void onOpen(SwipeLayout layout) {
                        YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.appImageView));
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return billsData.size() > 0 ? billsData.size() : TYPE_NULL;
    }

    @Override
    public int getItemViewType(int position) {
        if (billsData.size() == 0) {
            return TYPE_NULL;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    class ViewHolder extends AnimateViewHolder implements View.OnClickListener {
        TextView tvDueDate, tvCategory, tvAmount, tvNote, tvStatus;
        SwipeLayout swipeLayout;
        Button actionButton;
        LinearLayout viewGroup;

        public ViewHolder(View itemView) {
            super(itemView);


            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvDueDate = (TextView) itemView.findViewById(R.id.tvDueDate);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            actionButton = (Button) itemView.findViewById(R.id.actionButton);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
            viewGroup = (LinearLayout) itemView.findViewById(R.id.viewGroup);
            actionButton.setOnClickListener(this);
            viewGroup.setOnClickListener(this);

        }

        @Override
        public void animateAddImpl(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(viewPropertyAnimatorListener)
                    .start();
        }

        @Override
        public void preAnimateAddImpl() {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void animateRemoveImpl(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(300)
                    .setListener(viewPropertyAnimatorListener)
                    .start();
        }

        @Override
        public void onClick(View v) {
            if (onItemTouchListener != null) {
                onItemTouchListener.onItemTouchListener(getAdapterPosition(), v);
            }
        }
    }
}