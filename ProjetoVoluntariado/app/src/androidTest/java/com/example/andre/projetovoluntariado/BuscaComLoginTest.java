package com.example.andre.projetovoluntariado;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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

    private String nBusca = "v";

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        BuscaComLogin fragment = new BuscaComLogin();
        //mActivity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        view = mActivity.findViewById(R.id.textView3Busca);
    }

    @Test
    public void testLaunch() {
        assertNotNull(view);
    }

    @Test
    public void testUserBusca() {
        //coloca itens no input de busca
        Espresso.onView(withId(R.id.editTextBuscaComLogin)).perform(typeText(nBusca));
        //aperta o botao
        Espresso.onView(withId(R.id.buttonBuscaComLogin)).perform(click());
        //verifica se retornou alguma coisa na busca
        assertNotNull(mActivity.findViewById(R.id.expandableListView_search));
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