package com.example.andre.projetovoluntariado;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Andre on 29/10/2017.
 */
public class MenuCadastroTest {

    @Rule
    public ActivityTestRule<Main2Activity> mActivityTestRule = new ActivityTestRule <Main2Activity>(Main2Activity.class);

    private Main2Activity mActivity = null;
    View view = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        mActivity.changeFragment(new MenuCadastro());

        getInstrumentation().waitForIdleSync();
        view = mActivity.findViewById(R.id.buttonCadastraInstituicao);
    }

    @Test
    public void testLaunch() {
        assertNotNull(view);
    }

    @Test
    public void testCadastraInstituicaoLaunch() {
        assertNotNull(mActivity.findViewById(R.id.buttonCadastraInstituicao));
        onView(withId(R.id.buttonCadastraInstituicao)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(loginActivity);

        loginActivity.finish();
    }

    @Test
    public void testCadastraProjeto() {}

    @Test
    public void testCadastraVagasDoProjeto() {}



    @After
    public void tearDown() throws Exception {

    }

}