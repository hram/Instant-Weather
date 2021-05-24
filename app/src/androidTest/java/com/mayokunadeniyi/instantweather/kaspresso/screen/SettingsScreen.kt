package com.mayokunadeniyi.instantweather.kaspresso.screen

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.mayokunadeniyi.instantweather.R
import com.mayokunadeniyi.instantweather.ui.settings.SettingsFragment
import org.hamcrest.Matcher

object SettingsScreen : KScreen<SettingsScreen>() {

    override val layoutId: Int = R.xml.preferences

    override val viewClass: Class<*> = SettingsFragment::class.java

    val cache = KTextView { withText("Cache duration (seconds)") }

    val recycler: KRecyclerView = KRecyclerView({
        withId(androidx.preference.R.id.recycler_view)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val title: KTextView = KTextView(parent) { withId(android.R.id.title) }
        val summary: KTextView = KTextView(parent) { withId(android.R.id.summary) }
    }

    init {
        rootView = cache
    }
}