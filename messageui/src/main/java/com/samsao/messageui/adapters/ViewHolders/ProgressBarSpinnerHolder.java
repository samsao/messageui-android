package com.samsao.messageui.adapters.ViewHolders;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

/**
 * Created by lcampos on 2015-09-29.
 */
public class ProgressBarSpinnerHolder extends RecyclerView.ViewHolder {

    protected ProgressBar mProgressBar;
    private int[] mProgressBarPadding;
    private Integer mProgressBarColor;

    public ProgressBarSpinnerHolder(ProgressBar progressBar, int progressBarColor, int[] progressBarPadding) {
        super(progressBar);
        mProgressBar = progressBar;
        mProgressBarColor = progressBarColor;
        mProgressBarPadding = progressBarPadding;

        if (mProgressBarColor == null) {
            mProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(mProgressBarColor, PorterDuff.Mode.SRC_IN);
        }
        mProgressBar.setPadding(mProgressBarPadding[0], mProgressBarPadding[1], mProgressBarPadding[2], mProgressBarPadding[3]);
    }
}

