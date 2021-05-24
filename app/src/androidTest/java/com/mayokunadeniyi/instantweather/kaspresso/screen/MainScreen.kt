package com.mayokunadeniyi.instantweather.kaspresso.screen

import com.agoda.kakao.common.views.KView
import com.mayokunadeniyi.instantweather.R
import com.mayokunadeniyi.instantweather.ui.MainActivity

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main

    override val viewClass: Class<*> = MainActivity::class.java

    private val root = KView { withId(R.id.activity_main_root) }

    val homeButton = KView { withId(R.id.homeFragment) }

    val forecastButton = KView { withId(R.id.forecastFragment) }

    val searchButton = KView { withId(R.id.searchFragment) }

    val settingsButton = KView { withId(R.id.settingsFragment) }

    init {
        rootView = root
    }
}