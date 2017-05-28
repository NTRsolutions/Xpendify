package com.samsoft.xpendify.widget.others;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;


import com.samsoft.xpendify.R;
import com.samsoft.xpendify.animation.animators.holder.AnimateViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SAMSOFT on 25-Apr-16.
 */
public class EmptyViewHolder extends AnimateViewHolder {

    @Bind(R.id.info) TextView info;

    public EmptyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info.setText(info);
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
}
