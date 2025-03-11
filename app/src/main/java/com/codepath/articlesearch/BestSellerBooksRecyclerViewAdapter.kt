package com.codepath.articlesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.articlesearch.R.id
import com.codepath.articlesearch.R.layout

class BestSellerBooksRecyclerViewAdapter(
    private val books: List<BestSellerBook>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<BestSellerBooksRecyclerViewAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.fragment_best_seller_book, parent, false)
        return BookViewHolder(view)
    }

    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mBookTitle: TextView = mView.findViewById(id.book_title)
        val mBookAuthor: TextView = mView.findViewById(id.book_author)
        val mBookRanking: TextView = mView.findViewById(id.ranking)
        val mBookDescription: TextView = mView.findViewById(id.book_description)
        val mBookImage: ImageView = mView.findViewById(id.book_image)
        var mItem: BestSellerBook? = null
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.mItem = book
        holder.mBookTitle.text = book.title
        holder.mBookAuthor.text = book.author
        holder.mBookDescription.text = book.description
        holder.mBookRanking.text = "Rank: ${book.rank}"

        Glide.with(holder.mView)
            .load(book.bookImageUrl)
            .centerInside()
            .into(holder.mBookImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                listener?.onItemClick(book)
            }
        }
    }

    override fun getItemCount(): Int = books.size
}
