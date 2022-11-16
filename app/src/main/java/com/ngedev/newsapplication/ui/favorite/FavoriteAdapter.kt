package com.ngedev.newsapplication.ui.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ngedev.newsapplication.R
import com.ngedev.newsapplication.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplication.databinding.ItemFavoriteBinding
import com.ngedev.newsapplication.ui.web.WebActivity
import com.ngedev.newsapplication.utils.ImageBinding

class FavoriteAdapter(private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favorites = arrayListOf<FavoriteEntity>()

    var onItemClick: ((FavoriteEntity) -> Unit)? = null

    fun setItems(items: List<FavoriteEntity>) {
        favorites.clear()
        favorites.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemBinding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int = favorites.size

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteEntity) {
            with(binding) {
                ImageBinding.bind(
                    itemView.context,
                    item.urlToImage,
                    R.drawable.loading_image,
                    favoriteArticleImage
                )
                favoriteArticleAuthor.text = item.author
                favoriteArticleTitle.text = item.title

                root.setOnClickListener {
                    Intent(itemView.context, WebActivity::class.java).also { intent ->
                        item.url.let {
                            intent.putExtra(WebActivity.TAG, it)
                        }
                        context.startActivity(intent)
                    }
                }

                buttonDelete.setOnClickListener {
                    showMenu(it, R.menu.favorite_menu, itemView.context, item)
                }
            }


        }

        private fun showMenu(
            view: View,
            optionMenu: Int,
            context: Context,
            favoriteItem: FavoriteEntity,
        ) {
            val popup = PopupMenu(context, view)
            popup.menuInflater.inflate(optionMenu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->

                if (menuItem.itemId == R.id.delete_item) {
                    val materialBuilder = MaterialAlertDialogBuilder(itemView.context).create()
                    val inflater: View =
                        LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
                    val btnDelete: Button = inflater.findViewById(R.id.btn_accept)
                    val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)

                    btnDelete.setOnClickListener {
                        onItemClick?.invoke(favoriteItem)
                        materialBuilder.dismiss()
                    }
                    btnCancel.setOnClickListener {
                        materialBuilder.dismiss()
                    }

                    materialBuilder.setView(inflater)
                    materialBuilder.show()
                }
                true
            }
            popup.setOnDismissListener {}
            popup.show()
        }
    }
}