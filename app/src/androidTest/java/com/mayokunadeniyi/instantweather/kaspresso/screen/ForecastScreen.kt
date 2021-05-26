package com.mayokunadeniyi.instantweather.kaspresso.screen

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
import com.agoda.kakao.text.KTextView
import com.mayokunadeniyi.instantweather.R
import com.mayokunadeniyi.instantweather.kaspresso.view.KCalendarView
import com.mayokunadeniyi.instantweather.ui.forecast.ForecastFragment
import org.hamcrest.Matcher

object ForecastScreen : KScreen<ForecastScreen>() {

    override val layoutId: Int = R.layout.fragment_forecast

    override val viewClass: Class<*> = ForecastFragment::class.java

    val swipeRefresh = KSwipeRefreshLayout { withId(R.id.forecast_swipe_refresh) }

    val calendar = KCalendarView { withId(R.id.calendarView) }

    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.forecast_recyclerview)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    val error = KTextView { withId(R.id.forecast_error_text) }

    val empty = KTextView { withId(R.id.empty_list_text) }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val main: KTextView = KTextView(parent) { withId(R.id.weather_main) }
        val icon: KView = KView(parent) { withId(R.id.weather_icon) }
        val description: KTextView = KTextView(parent) { withId(R.id.weather_description) }
        val temperature: KTextView = KTextView(parent) { withId(R.id.city_temp) }
        val humidity: KTextView = KTextView(parent) { withId(R.id.text_humidity) }
        val pressure: KTextView = KTextView(parent) { withId(R.id.text_pressure) }
        val speed: KTextView = KTextView(parent) { withId(R.id.text_speed) }
        val date: KTextView = KTextView(parent) { withId(R.id.text_date) }
    }

    init {
        rootView = swipeRefresh
    }
}