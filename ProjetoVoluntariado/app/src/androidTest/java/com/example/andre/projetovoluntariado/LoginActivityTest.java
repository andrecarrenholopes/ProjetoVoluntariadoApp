package com.example.andre.projetovoluntariado;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by Andre on 28/10/2017.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule <LoginActivity>(LoginActivity.class);

    private LoginActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Main2Activity.class.getName(), null, false);

    private String userNameCorreto = "andre1";
    private String userNameIncorreto = "fsdfsdfsd";
    private String senha = "123";

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mActivity.findViewById(R.id.editTextUsername);

        SharedPrefManager.getInstance(mActivity).logout();
        assertNotNull(view);
    }

    @Test
    public void testLoginCorrect() {
        assertNotNull(mActivity.findViewById(R.id.buttonLogin));

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(userNameCorreto));
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(senha));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonLogin)).perform(click());

        //onView(withId(R.id.buttonLogin)).perform(click());

        Activity main2Activity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(main2Activity);

        main2Activity.finish();
        SharedPrefManager.getInstance(mActivity).logout();
    }

    @Test
    public void testLoginIncorrect() {
        assertNotNull(mActivity.findViewById(R.id.buttonLogin));

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(userNameIncorreto));
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(senha));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonLogin)).perform(click());

        //onView(withId(R.id.buttonLogin)).perform(click());

        Activity main2Activity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNull(main2Activity);

        if(main2Activity != null) {
            main2Activity.finish();
        }
        SharedPrefManager.getInstance(mActivity).logout();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onClick() throws Exception {

    }

}