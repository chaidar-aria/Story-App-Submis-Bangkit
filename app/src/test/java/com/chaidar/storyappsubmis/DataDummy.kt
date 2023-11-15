package com.chaidar.storyappsubmis

import com.chaidar.storyappsubmis.backend.response.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                "photoUrl + $i",
                "createdAt $i",
                "Name $i",
                "description $i",
                1.0,
                "ID $i",
                1.0
            )
            items.add(quote)
        }
        return items
    }
}