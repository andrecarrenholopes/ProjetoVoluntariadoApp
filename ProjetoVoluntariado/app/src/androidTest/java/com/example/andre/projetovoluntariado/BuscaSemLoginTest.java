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

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * Created by Andre on 26/10/2017.
 */
public class BuscaSemLoginTest {

    @Rule
    public ActivityTestRule<BuscaSemLogin> mActivityTestRule = new ActivityTestRule <BuscaSemLogin>(BuscaSemLogin.class);

    private BuscaSemLogin mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

    private String nBusca = "vaga";
    private String nBuscaSemResultado = "vaererega";
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mActivity.findViewById(R.id.textViewBuscadeOng);

        assertNotNull(view);
    }

    @Test
    public void testLoginLaunch() {
        assertNotNull(mActivity.findViewById(R.id.buttonLogin));
        onView(withId(R.id.buttonLogin)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(loginActivity);

        loginActivity.finish();
    }

    @Test
    public void testUserBusca() {
        //coloca itens no input de busca
        Espresso.onView(withId(R.id.editTextBusca)).perform(typeText(nBusca));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonBusca)).perform(click());
        //verifica se retornou alguma coisa na busca
        assertNotNull(mActivity.findViewById(R.id.expandableListView_search));

        ArrayList<ParentRow> parent = mActivity.getParentList();

        assertEquals(false, parent.isEmpty());
    }

    @Test
    public void testUserBuscaSemResultado() {
        //coloca itens no input de busca
        Espresso.onView(withId(R.id.editTextBusca)).perform(typeText(nBuscaSemResultado));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonBusca)).perform(click());
        //verifica se retornou alguma coisa na busca
        assertNotNull(mActivity.findViewById(R.id.expandableListView_search));

        //View view = mActivity.findViewById(R.id.expandableListView_search);

        ArrayList<ParentRow> parent = mActivity.getParentList();

        assertEquals(true, parent.isEmpty());
    }

    @After
    public void tearDown() throws Exception {

    }

}