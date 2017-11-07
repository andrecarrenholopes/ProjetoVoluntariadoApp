package com.example.andre.projetovoluntariado;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Andre on 29/10/2017.
 */
public class MenuCadastroTest {

    @Rule
    public ActivityTestRule<Main2Activity> mActivityTestRule = new ActivityTestRule <Main2Activity>(Main2Activity.class);

   // @Rule
    //public ActivityTestRule<Main2Activity> mActivityTestRuleTemp = new ActivityTestRule <Main2Activity>(Main2Activity.class);

    private Main2Activity mActivity = null;
    //private Main2Activity mActivityTemp = null;
    View view = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
       // mActivityTemp = mActivityTestRuleTemp.getActivity();

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
        /*assertNotNull(mActivity.findViewById(R.id.buttonCadastraInstituicao));
        onView(withId(R.id.buttonCadastraInstituicao)).perform(click());

        mActivityTemp.changeFragment(new CadastraInstituicao());

        assertEquals(mActivityTemp, mActivity);*/
        //Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        //assertNotNull(loginActivity);

        //loginActivity.finish();
    }

    @Test
    public void testCadastraProjeto() {}

    @Test
    public void testCadastraVagasDoProjeto() {}



    @After
    public void tearDown() throws Exception {

    }

}