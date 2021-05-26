package com.mayokunadeniyi.instantweather.kaspresso.forecast

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mayokunadeniyi.instantweather.data.model.NetworkWeatherCondition
import com.mayokunadeniyi.instantweather.data.model.NetworkWeatherDescription
import com.mayokunadeniyi.instantweather.data.model.WeatherForecast
import com.mayokunadeniyi.instantweather.data.model.Wind
import com.mayokunadeniyi.instantweather.kaspresso.BaseAndroidTest
import com.mayokunadeniyi.instantweather.kaspresso.mocks.Mocks
import com.mayokunadeniyi.instantweather.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastTest : BaseAndroidTest() {

    private val sectionId = 234567890L // ID раздела для выгрузки в TestRail

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val precondition = "Приложение запускалось ранее и были выданы разрешения к данным о местоположении устройства."

    @Test
    fun forecastSuccessLoadDataTest() = createTest(sectionId,
        "Успешная загрузка прогноза погоды",
        "Основной позитивный сценарий запуска приложения, переход на экран прогноза и отображения прогноза погоды",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
            add(Mocks.forecastNovayaGollandiyaSuccess)
        }, init = {
            forecastSize = 40
            forecast {
                index = 0
                main = "Rain"
                description = "moderate rain"
                temperature = "11.35℃"
                humidity = "82.0%"
                pressure = "1011.0hPa"
                speed = "6.17m/s"
                date = "2021-05-26 15:00:00"
            }
            forecast {
                index = 1
                main = "Rain"
                description = "light rain"
                temperature = "10.48℃"
                humidity = "84.0%"
                pressure = "1012.0hPa"
                speed = "3.03m/s"
                date = "2021-05-26 18:00:00"
            }
        },
        before = {
            activityTestRule.launchActivity(null)
        }) {
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickForecastAndCheckForecastScreen()
    }

    @Test
    fun forecastErrorLoadDataAndRefreshSuccessTest() = createTest(sectionId,
        "Ошибка загрузки погоды",
        "Ошибка загрузки погоды и успешное обновление",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
            add(Mocks.forecast500)
        }, init = {
            forecastSize = 40
            forecast {
                index = 0
                main = "Rain"
                description = "moderate rain"
                temperature = "11.35℃"
                humidity = "82.0%"
                pressure = "1011.0hPa"
                speed = "6.17m/s"
                date = "2021-05-26 15:00:00"
            }
            forecast {
                index = 1
                main = "Rain"
                description = "light rain"
                temperature = "10.48℃"
                humidity = "84.0%"
                pressure = "1012.0hPa"
                speed = "3.03m/s"
                date = "2021-05-26 18:00:00"
            }
        },
        before = {
            activityTestRule.launchActivity(null)
        }) { server ->
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickForecastAndCheckForecastScreenWithError()

        server.replace(Mocks.forecast500, Mocks.forecastNovayaGollandiyaSuccess)

        stepForecastScreenSwipeToRefreshAndCheckForecastScreen()
    }

    @Test
    fun forecastLoadDataEmptyAndRefreshSuccessTest() = createTest(sectionId,
        "Нет данных за выбранный день",
        "Получение пустого списка и успешное обновление",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
            add(Mocks.forecastEmpty)
        }, init = {
            forecastSize = 40
            forecast {
                index = 0
                main = "Rain"
                description = "moderate rain"
                temperature = "11.35℃"
                humidity = "82.0%"
                pressure = "1011.0hPa"
                speed = "6.17m/s"
                date = "2021-05-26 15:00:00"
            }
            forecast {
                index = 1
                main = "Rain"
                description = "light rain"
                temperature = "10.48℃"
                humidity = "84.0%"
                pressure = "1012.0hPa"
                speed = "3.03m/s"
                date = "2021-05-26 18:00:00"
            }
        },
        before = {
            activityTestRule.launchActivity(null)
        }) { server ->
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickForecastAndCheckForecastScreenWithEmpty()

        server.replace(Mocks.forecastEmpty, Mocks.forecastNovayaGollandiyaSuccess)

        stepForecastScreenSwipeToRefreshAndCheckForecastScreen()
    }

    @Test
    fun forecastLoadFromStorageDataTest() = createTest(sectionId,
        "Отображение сохраненного прогноза погоды и обновление. Солнце сменилось на дождь (((",
        "Прогноз был получен ранее и сохранен в БД по этому при запуске сетевого запроса не происходит, запрос происходит по жесту PullToRefresh",
        precondition, {
            add(Mocks.weatherNovayaGollandiyaSuccess)
            // запрос спциально не мокаем чтобы проверить что он не вызывается
        }, init = {
            forecastSize = 2
            forecast {
                index = 0
                main = "Clouds"
                description = "few clouds"
                temperature = "11.35℃"
                humidity = "82.0%"
                pressure = "1011.0hPa"
                speed = "6.17m/s"
                date = "2021-05-26 15:00:00"
            }
            forecast {
                index = 1
                main = "Clear"
                description = "clear sky"
                temperature = "10.48℃"
                humidity = "84.0%"
                pressure = "1012.0hPa"
                speed = "3.03m/s"
                date = "2021-05-26 18:00:00"
            }
            // заполняем БД
            weatherRepository.storeForecastData(listOf(
                WeatherForecast(100, "2021-05-26 15:00:00", Wind(6.17, 180), listOf(NetworkWeatherDescription(501, "Clouds", "few clouds", "10d")), NetworkWeatherCondition(11.35, 1011.0, 82.0)),
                WeatherForecast(101, "2021-05-26 18:00:00", Wind(3.03, 218), listOf(NetworkWeatherDescription(500, "Clear", "clear sky", "10d")), NetworkWeatherCondition(10.48, 1012.0, 84.0))))
        },
        before = {
            activityTestRule.launchActivity(null)
        }) { server ->
        stepStartAppAndCheckHomeScreen()
        stepMainScreenClickForecastAndCheckForecastScreen()

        // обновляем требования под новые данные
        data.forecastSize = 40
        data.forecast[0].main = "Rain"
        data.forecast[0].description = "moderate rain"
        data.forecast[1].main = "Rain"
        data.forecast[1].description = "light rain"

        // мокаем запрос
        server.add(Mocks.forecastNovayaGollandiyaSuccess)

        stepForecastScreenSwipeToRefreshAndCheckForecastScreen()
    }
}