package com.example.andre.projetovoluntariado;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Andre on 29/10/2017.
 */
public class BuscaComLoginTest {

    @Rule
    public ActivityTestRule<Main2Activity> mActivityTestRule = new ActivityTestRule <Main2Activity>(Main2Activity.class);

    private Main2Activity mActivity = null;
    View view = null;
    //Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

    private String nBusca = "inst";
    private String nBuscaSemResultado = "vsdgfsdgsdf";

    BuscaComLogin fragment;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        fragment = new BuscaComLogin();
        //mActivity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        getInstrumentation().waitForIdleSync();
        view = mActivity.findViewById(R.id.textView3Busca);
    }

    @Test
    public void testLaunch() {
        assertNotNull(view);
    }

    @Test
    public void testUserBusca() {

        fragment.clearParentList();
        //coloca itens no input de busca
        Espresso.onView(withId(R.id.editTextBuscaComLogin)).perform(typeText(nBusca));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonBuscaComLogin)).perform(click());
        //verifica se retornou alguma coisa na busca
        assertNotNull(mActivity.findViewById(R.id.expandableListView_search));

        getInstrumentation().waitForIdleSync();
        ArrayList<ParentRow> parent = fragment.getParentList();
        assertEquals(true, parent.isEmpty());
    }

    @Test
    public void testUserBuscaSemResultado() {

        fragment.clearParentList();
        //coloca itens no input de busca
        Espresso.onView(withId(R.id.editTextBuscaComLogin)).perform(typeText(nBuscaSemResultado));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonBuscaComLogin)).perform(click());
        //verifica se retornou alguma coisa na busca
        View view = mActivity.findViewById(R.id.expandableListView_search);
        assertNotNull(view);
        //mActivity.getParentList();
        getInstrumentation().waitForIdleSync();
        ArrayList<ParentRow> parent = fragment.getParentList();
        assertEquals(true, parent.isEmpty());

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstituicao() throws Exception {

    }

    @Test
    public void getProjeto() throws Exception {

    }

    @Test
    public void getVagasDosProjetos() throws Exception {

    }

}