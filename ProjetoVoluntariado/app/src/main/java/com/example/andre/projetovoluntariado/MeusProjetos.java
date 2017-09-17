package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Andre on 08/09/2017.
 */

public class MeusProjetos extends Fragment implements View.OnClickListener  {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.meus_projetos, container, false);

        ListView lista = (ListView) myView.findViewById(R.id.listViewProjetos);

        ArrayList<String> projetos = preencherDados();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.select_dialog_singlechoice, projetos);
        lista.setAdapter(arrayAdapter);
        return myView;
    }

    private ArrayList preencherDados() {
        ArrayList<String> dados = new ArrayList<String>();

        dados.add("Instituição 1");
        dados.add("Instituição 2");
        dados.add("Instituição 3");
        dados.add("Instituição 4");
        dados.add("Instituição 5");
        dados.add("Instituição 6");
        dados.add("Instituição 7");
        dados.add("Instituição 8");
        return dados;
    }

    @Override
    public void onClick(View v) {

    }
}