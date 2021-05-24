package com.mayokunadeniyi.instantweather.kaspresso.screen

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.mayokunadeniyi.instantweather.R
import com.mayokunadeniyi.instantweather.ui.search.SearchFragment
import org.hamcrest.Matcher

object SearchScreen : KScreen<SearchScreen>() {

    override val layoutId: Int = R.layout.fragment_search

    override val viewClass: Class<*> = SearchFragment::class.java

    private val root = KView { withId(R.id.fragment_search_root) }

    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.location_search_recyclerview)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val name: KTextView = KTextView(parent) { withId(R.id.location_name) }
    }

    init {
        rootView = root
    }
}