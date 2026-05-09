package com.example.prerpare.ui.mainlist

import androidx.recyclerview.widget.DiffUtil
import com.example.prerpare.data.model.Article

class ArticleDifferCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        p0: Article,
        p1: Article
    ): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(
        p0: Article,
        p1: Article
    ): Boolean {
        return p0 == p1
    }
}