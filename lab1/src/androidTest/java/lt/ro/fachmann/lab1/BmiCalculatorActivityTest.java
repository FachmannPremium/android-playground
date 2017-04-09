package lt.ro.fachmann.lab1;

/**
 * Created by bartl on 02.04.2017.
 */

import android.os.RemoteException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class BmiCalculatorActivityTest {


    @Rule
    public ActivityTestRule<BmiCalculatorActivity> mActivityRule =
            new ActivityTestRule<>(BmiCalculatorActivity.class);

    public static Matcher<View> withTextColor(final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public boolean matchesSafely(TextView warning) {
                return color == warning.getCurrentTextColor();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
            }
        };
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    @Test
    public void properCountingForMetric() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Change units")).perform(click());
        onView(withText("Metric")).perform(click());

        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click(), clearText(), typeText("170"), closeSoftKeyboard());
        onView(withId(R.id.text_bmi)).check(matches(withText(containsString(String.format(Locale.getDefault(), "%.2f", 24.22f)))));
    }

    @Test
    public void properResultColors() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Change units")).perform(click());
        onView(withText("Metric")).perform(click());

        onView(withId(R.id.edit_height)).perform(click(), clearText(), typeText("170"), closeSoftKeyboard());

        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.text_bmi)).check(matches(withTextColor(0xff26a69a)));

        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.text_bmi)).check(matches(withTextColor(0xff1b5e20)));

        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("80"), closeSoftKeyboard());
        onView(withId(R.id.text_bmi)).check(matches(withTextColor(0xffe16c18)));

        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("110"), closeSoftKeyboard());
        onView(withId(R.id.text_bmi)).check(matches(withTextColor(0xffb71c1c)));
    }

    @Test
    public void properCountingForImperial() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Change units")).perform(click());
        onView(isRoot()).perform(waitFor(200));

        onView(withText(containsString("I"))).perform(click());


        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("170"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click(), clearText(), typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.text_bmi)).check(matches(withText(containsString(String.format(Locale.getDefault(), "%.2f", 24.39f)))));
    }

    @Test
    public void forWrongInputResultInvisible() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Change units")).perform(click());
        onView(withText("Metric")).perform(click());

        onView(withId(R.id.edit_mass)).perform(click()).perform(clearText(), replaceText("170"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click()).perform(clearText(), replaceText("70"), closeSoftKeyboard());

        onView(withId(R.id.edit_mass)).perform(click()).perform(clearText(), replaceText("282"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click()).perform(clearText(), replaceText("993"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(200));
        onView(withId(R.id.text_bmi)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        onView(withId(R.id.edit_mass)).perform(click()).perform(clearText(), replaceText("999.3"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click()).perform(clearText(), replaceText("0"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(200));
        onView(withId(R.id.text_bmi)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));


        onView(withId(R.id.edit_mass)).perform(click()).perform(clearText(), replaceText("0"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click()).perform(clearText(), replaceText("12.23"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(200));
        onView(withId(R.id.text_bmi)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));


        onView(withId(R.id.edit_mass)).perform(click()).perform(clearText(), replaceText("0"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click()).perform(clearText(), replaceText("0"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(200));
        onView(withId(R.id.text_bmi)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void aboutActivityIsOpeningProperly() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("About")).perform(click());
    }

    @Test
    public void rotateTest() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        try {
            device.setOrientationNatural();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Change units")).perform(click());
        onView(withText("Metric")).perform(click());


        onView(withId(R.id.edit_mass)).perform(click(), clearText(), typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.edit_height)).perform(click(), clearText(), typeText("170"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(200));
        onView(withId(R.id.text_bmi)).check(matches(withText(containsString(String.format(Locale.getDefault(), "%.2f", 24.22f)))));


        try {
            device.setOrientationLeft();
            onView(isRoot()).perform(waitFor(3000));
            onView(withId(R.id.text_bmi)).check(matches(withText(containsString(String.format(Locale.getDefault(), "%.2f", 24.22f)))));

            device.setOrientationRight();
            onView(isRoot()).perform(waitFor(3000));
            onView(withId(R.id.text_bmi)).check(matches(withText(containsString(String.format(Locale.getDefault(), "%.2f", 24.22f)))));

            device.setOrientationNatural();
            onView(isRoot()).perform(waitFor(3000));
            onView(withId(R.id.text_bmi)).check(matches(withText(containsString(String.format(Locale.getDefault(), "%.2f", 24.22f)))));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}