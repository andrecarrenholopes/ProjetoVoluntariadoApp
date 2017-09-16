package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Andre on 14/09/2017.
 */

public class MenuCadastro extends Fragment implements View.OnClickListener {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.menu_cadastros, container, false);

        Button buttonCadastraInstituicao = (Button) myView.findViewById(R.id.buttonCadastraInstituicao);
        Button buttonCadastraProjeto = (Button) myView.findViewById(R.id.buttonCadastraProjeto);
        Button buttonCadastraVagaDoProjeto = (Button) myView.findViewById(R.id.buttonCadastraVagasDoProjeto);

        buttonCadastraInstituicao.setOnClickListener(this);
        buttonCadastraProjeto.setOnClickListener(this);
        buttonCadastraVagaDoProjeto.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (v.getId()) {
            case R.id.buttonCadastraInstituicao:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new CadastraInstituicao())
                        .commit();
                break;
            case R.id.buttonCadastraProjeto:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new CadastraInstituicao())
                        .commit();
                break;
            case R.id.buttonCadastraVagasDoProjeto:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new CadastraVagasDoProjeto())
                        .commit();
                break;

        }
    }
}