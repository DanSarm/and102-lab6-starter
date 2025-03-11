package com.codepath.articlesearch

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single book from the NY Times API.
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the Gson library.
 */
data class BestSellerBook(
    @SerializedName("rank")
    val rank: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("book_image")
    val bookImageUrl: String,

    @SerializedName("publisher")
    val publisher: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("amazon_product_url")
    val amazonUrl: String? = null
)
