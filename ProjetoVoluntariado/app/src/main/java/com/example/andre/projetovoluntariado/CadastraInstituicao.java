package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Andre on 08/09/2017.
 */

public class CadastraInstituicao extends Fragment implements View.OnClickListener{

    View myView;
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cadastra_instituicao, container, false);
        return myView;
    }



    @Override
    public void onClick(View view) {

    }

}