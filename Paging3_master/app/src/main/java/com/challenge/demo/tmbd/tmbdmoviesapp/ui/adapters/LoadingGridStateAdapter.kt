package com.challenge.demo.tmbd.tmbdmoviesapp.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.challenge.demo.tmbd.tmbdmoviesapp.ui.viewholders.LoadingGridStateViewHolder

class LoadingGridStateAdapter: LoadStateAdapter<LoadingGridStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadingGridStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingGridStateViewHolder {
        return LoadingGridStateViewHolder.create(parent)
    }
}