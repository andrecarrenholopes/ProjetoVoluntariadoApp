package com.example.andre.projetovoluntariado;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Andre on 21/10/2017.
 */
public class CadastraInstituicaoTest {

    public ActivityTestRule<Main2Activity> mActivityTestRule = new ActivityTestRule <Main2Activity>(Main2Activity.class);

    private Main2Activity mActivity = null;
    View view = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        //CadastraInstituicao fragment = new CadastraInstituicao();

        //mActivity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        //view = mActivity.findViewById(R.id.editTextNomeInstituicao);
    }

    @Test
    public void testLaunch() {
        //assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {

    }

/*

    @Test
    public void onCreateView() throws Exception {

    }

    @Test
    public void onClick() throws Exception {

    }

    @Test
    public void getEstado() throws Exception {

    }

    @Test
    public void spinner_estado() throws Exception {

    }

    @Test
    public void getCidade() throws Exception {

    }

    @Test
    public void spinner_cidade() throws Exception {

    }

    @Test
    public void cadastraInstituicao() throws Exception {

    }
    */

}