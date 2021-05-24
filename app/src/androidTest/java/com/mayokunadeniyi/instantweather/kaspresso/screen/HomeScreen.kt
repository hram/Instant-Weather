package com.mayokunadeniyi.instantweather.kaspresso.screen

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
import com.agoda.kakao.text.KTextView
import com.mayokunadeniyi.instantweather.R
import com.mayokunadeniyi.instantweather.ui.home.HomeFragment

object HomeScreen : KScreen<HomeScreen>() {

    override val layoutId: Int = R.layout.fragment_home

    override val viewClass: Class<*> = HomeFragment::class.java

    val swipeRefresh = KSwipeRefreshLayout { withId(R.id.swipe_refresh_id) }

    val weatherIn = KTextView { withId(R.id.weather_in_text) }

    val date = KTextView { withId(R.id.date_text) }

    val icon = KImageView { withId(R.id.weather_icon) }

    val temperature = KTextView { withId(R.id.weather_temperature) }

    val main = KTextView { withId(R.id.weather_main) }

    val error = KTextView { withId(R.id.error_text) }

    val detail = KView { withId(R.id.weather_det) }

    val humidity = KTextView { withId(R.id.humidity_text) }

    val pressure = KTextView { withId(R.id.pressure_text) }

    val windSpeed = KTextView { withId(R.id.wind_speed_text) }

    init {
        rootView = swipeRefresh
    }
}