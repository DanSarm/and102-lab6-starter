package com.codepath.articlesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class BestSellerBooksFragment : Fragment(), OnListFragmentInteractionListener {
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var bookAdapter: BestSellerBooksRecyclerViewAdapter
    private val books = mutableListOf<BestSellerBook>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false)
        bookRecyclerView = view.findViewById(R.id.book_recycler_view)
        bookRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bookAdapter = BestSellerBooksRecyclerViewAdapter(books, this)
        bookRecyclerView.adapter = bookAdapter
        fetchBooks()
        return view
    }

    private fun fetchBooks() {
        val client = AsyncHttpClient()
        client.get("https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=${BuildConfig.API_KEY}",
            object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    val resultsJSON = json.jsonObject.getJSONObject("results")
                    val booksArray = resultsJSON.getJSONArray("books")
                    books.clear()
                    for (i in 0 until booksArray.length()) {
                        val bookJson = booksArray.getJSONObject(i)
                        val book = BestSellerBook(
                            title = bookJson.getString("title"),
                            author = bookJson.getString("author"),
                            bookImageUrl = bookJson.getString("book_image"),
                            description = bookJson.optString("description", "No description available"),
                            rank = bookJson.optInt("rank", 0)
                        )
                        books.add(book)
                    }
                    bookAdapter.notifyDataSetChanged()
                }

                override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                    // Handle failure
                }
            })
    }

    override fun onItemClick(item: BestSellerBook) {
        // Handle book item click
    }
}
