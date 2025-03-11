package com.codepath.articlesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import com.codepath.articlesearch.createJson

class ArticleListFragment : Fragment() {
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private val articles = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)
        articleRecyclerView = view.findViewById(R.id.article_recycler_view)
        articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter(requireContext(), articles)
        articleRecyclerView.adapter = articleAdapter
        fetchArticles()
        return view
    }

    private fun fetchArticles() {
        val client = AsyncHttpClient()
        client.get("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${BuildConfig.API_KEY}",
            object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.response?.docs?.let { list ->
                        articles.clear()
                        articles.addAll(list)
                        articleAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                    // Handle failure
                }
            })
    }
}
