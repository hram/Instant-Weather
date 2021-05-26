package com.mayokunadeniyi.instantweather.kaspresso.mocks

import androidx.test.platform.app.InstrumentationRegistry
import com.infeez.mock.MockEnqueueResponse
import com.infeez.mock.MockResponseBuilder
import com.infeez.mock.RequestMethod
import com.infeez.mock.matcher.rulePath
import com.infeez.mock.matcher.startWith

object Mocks {

    val weatherNovayaGollandiyaSuccess = MockEnqueueResponse {
        doResponseWithMatcher(rulePath startWith "/data/2.5/weather") {
            readFromResource("data/2.5/weather/novaya_gollandiya.json")
        }
    }

    val weather500 = MockEnqueueResponse {
        doResponseWithMatcher(rulePath startWith "/data/2.5/weather") {
            responseStatusCode = 500
            emptyBody()
        }
    }

    val indexesQueries = MockEnqueueResponse {
        doResponseWithMatcher(RequestMethod.POST, rulePath startWith "/1/indexes/*/queries") {
            emptyBody()
        }
    }

    val forecastNovayaGollandiyaSuccess = MockEnqueueResponse {
        doResponseWithMatcher(rulePath startWith "/data/2.5/forecast") {
            readFromResource("data/2.5/forecast/novaya_gollandiya.json")
        }
    }

    val forecast500 = MockEnqueueResponse {
        doResponseWithMatcher(rulePath startWith "/data/2.5/forecast") {
            responseStatusCode = 500
            emptyBody()
        }
    }

    val forecastEmpty = MockEnqueueResponse {
        doResponseWithMatcher(rulePath startWith "/data/2.5/forecast") {
            fromString("""{"list":[]}""")
        }
    }

    infix fun MockResponseBuilder.readFromResource(path: String) = fromStream(path.toInputStream())

    fun String.loadResource() = toInputStream().use { it.reader().readText() }

    fun String.toInputStream() = InstrumentationRegistry.getInstrumentation().context.resources.assets.open(this)
}