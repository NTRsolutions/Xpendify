package com.samsoft.xpendify.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.samsoft.xpendify.R;
import com.samsoft.xpendify.animation.animators.holder.AnimateViewHolder;
import com.samsoft.xpendify.callback.OnItemTouchListener;
import com.samsoft.xpendify.drawable.TextDrawable;
import com.samsoft.xpendify.model.SummaryData;
import com.samsoft.xpendify.widget.others.EmptyViewHolder;
import com.samsoft.xpendify.widget.swipe.SimpleSwipeListener;
import com.samsoft.xpendify.widget.swipe.SwipeLayout;
import com.samsoft.xpendify.widget.swipe.adapters.RecyclerSwipeAdapter;

import java.util.Collections;
import java.util.List;


/**
 * Created by Fred on 27-Aug-15.
 */
public class SummaryAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NULL = 1;
    List<SummaryData> summaryData = Collections.emptyList();
    private OnItemTouchListener onItemTouchListener;

    public SummaryAdapter(List<SummaryData> summaryData) {
        this.summaryData = summaryData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_NULL:
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_empty_view, viewGroup, false);
                return new EmptyViewHolder(v1);
            default:
                View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.summary_adapter_list_item, viewGroup, false);
                return new ViewHolder(v2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case TYPE_NULL:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) viewHolder;
                emptyViewHolder.setInfo(R.string.no_expense);
                break;
            default:
                ViewHolder swipeViewHolder = (ViewHolder) viewHolder;
                swipeViewHolder.tvAmount.setText(summaryData.get(position).getAmount());
                swipeViewHolder.tvCategory.setText(summaryData.get(position).getCategory());
                swipeViewHolder.tvDate.setText(summaryData.get(position).getDate());
                swipeViewHolder.tvNote.setText(summaryData.get(position).getNote());
                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .textColor(Color.BLACK)
                        .useFont(Typeface.DEFAULT)
                        .fontSize(60)
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRect(summaryData.get(position).getCategory().substring(0, 1), Color.parseColor(summaryData.get(position).getColor()));
                swipeViewHolder.imageView.setImageDrawable(drawable);
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
        return summaryData.size() > 0 ? summaryData.size() : TYPE_NULL;
    }

    @Override
    public int getItemViewType(int position) {
        if (summaryData.size() == 0) {
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

        ImageView imageView;
        Button actionButton;
        SwipeLayout swipeLayout;
        RelativeLayout viewGroup;
        TextView tvAmount, tvCategory, tvDate, tvNote;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
            actionButton = (Button) itemView.findViewById(R.id.actionButton);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
            viewGroup = (RelativeLayout) itemView.findViewById(R.id.viewGroup);
            viewGroup.setOnClickListener(this);
            actionButton.setOnClickListener(this);
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