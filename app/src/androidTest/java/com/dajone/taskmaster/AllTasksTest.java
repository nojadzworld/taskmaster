package com.dajone.taskmaster;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AllTasksTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void allTasksPageDisplayed() {
        // Assert that the addTaskToAllTasks button is displayed
        ViewInteraction addTaskButton = onView(withId(R.id.addTaskRouteButton));
        addTaskButton.check(matches(isDisplayed()));

        // Assert that the my_tasks_title TextView is displayed
        ViewInteraction myTasksTitle = onView(withId(R.id.my_tasks_title));
        myTasksTitle.check(matches(isDisplayed()));

    }
}