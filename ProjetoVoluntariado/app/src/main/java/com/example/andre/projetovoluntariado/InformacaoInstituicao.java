package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Andre on 21/10/2017.
 */

public class InformacaoInstituicao extends Fragment implements View.OnClickListener {
    View myView;

    private String nomeInstituicao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.informacao_instituicao, container, false);

        Toast.makeText(myView.getContext(), "Info da Instituicao: " + nomeInstituicao, Toast.LENGTH_LONG).show();
        return myView;
    }

    @Override
    public void onClick(View v) {

    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }
}
