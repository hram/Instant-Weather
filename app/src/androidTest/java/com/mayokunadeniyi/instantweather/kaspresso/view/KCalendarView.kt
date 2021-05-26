package com.mayokunadeniyi.instantweather.kaspresso.view

import android.view.View
import androidx.test.espresso.DataInteraction
import com.agoda.kakao.common.KakaoDslMarker
import com.agoda.kakao.common.actions.BaseActions
import com.agoda.kakao.common.assertions.BaseAssertions
import com.agoda.kakao.common.builders.ViewBuilder
import com.agoda.kakao.common.views.KBaseView
import com.agoda.kakao.text.KTextView
import com.mayokunadeniyi.instantweather.R
import org.hamcrest.Matcher

@KakaoDslMarker
class KCalendarView : KBaseView<KCalendarView>, BaseActions, BaseAssertions {

    val title: KTextView

    constructor(function: ViewBuilder.() -> Unit) : super(function) {
        title = KTextView { withId(R.id.txt_title) }
    }

    constructor(parent: Matcher<View>, function: ViewBuilder.() -> Unit) : super(parent, function) {
        title = KTextView(parent) { withId(R.id.txt_title) }
    }

    constructor(parent: DataInteraction, function: ViewBuilder.() -> Unit) : super(parent, function) {
        title = KTextView(parent) { withId(R.id.txt_title) }
    }

    fun chooseDate(date: String) {
        KTextView {
            withId(R.id.txt_day)
            withText(date)
        }.click()
    }
}