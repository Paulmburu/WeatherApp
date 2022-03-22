package github.paulmburu.weatherapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import github.paulmburu.weatherapp.ui.mainActivity.MainActivity
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.screen.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class MainScreen : Screen<MainScreen>() {
    val weatherImageBackground = KImageView{ withId(R.id.no_data_image_view)}
}

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val screen = MainScreen()


    @Test
    fun on_launch_should_display_progress_bar() {
        screen {
            weatherImageBackground.isDisplayed()
        }
    }
}