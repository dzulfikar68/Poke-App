package io.github.dzulfikar68.pokeapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import io.github.dzulfikar68.pokeapp.view.DetailActivity
import io.github.dzulfikar68.pokeapp.view.HomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonInstrumentedTest {

    @get:Rule
    var homeActivityRule = ActivityTestRule(HomeActivity::class.java)

    @get:Rule
    var detailActivityRule = ActivityTestRule(DetailActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadPokemonList() {
        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadPokemonDetail() {
        onView(withId(R.id.rv_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.iv_poke)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
    }

    @Test
    fun loadPokemonStats() {
        onView(withId(R.id.rv_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_ab)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc_ab)).check(matches(isDisplayed()))
    }

    @Test
    fun loadPokemonEvolutions() {
        onView(withId(R.id.rv_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(ViewMatchers.withText("EVOLUTIONS")).perform(click())
        onView(withId(R.id.rv_evolutions)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_evo1)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_evo2)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_level)).check(matches(isDisplayed()))
    }

}