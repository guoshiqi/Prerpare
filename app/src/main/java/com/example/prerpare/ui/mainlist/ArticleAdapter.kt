package com.example.prerpare.ui.mainlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prerpare.data.model.Article
import com.example.prerpare.databinding.ItemArticleBinding

class ArticleAdapter(
    private val onItemClick: (Article, Int) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val items = mutableListOf<Article>()

    fun setData(newList: List<Article>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.tvTitle.text = item.title
            binding.tvDesc.text = item.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onItemClick(items[currentPosition], currentPosition)
            }
        }
    }

    override fun getItemCount(): Int = items.size


}