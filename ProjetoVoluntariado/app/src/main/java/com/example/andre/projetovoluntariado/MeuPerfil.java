package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andre on 08/09/2017.
 */

public class MeuPerfil extends Fragment {

    View myView;
    private TextView textViewUsername, textViewUserEmail, textViewUserNomeCompleto, textViewUserCidade, textViewUserCPF, textViewUserNascimento;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.meu_perfil, container, false);

        textViewUsername = (TextView) myView.findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) myView.findViewById(R.id.textViewUseremail);
        textViewUserNomeCompleto = (TextView) myView.findViewById(R.id.textViewUserNomeCompleto);
        textViewUserCidade = (TextView) myView.findViewById(R.id.textViewUserCidade);
        textViewUserCPF = (TextView) myView.findViewById(R.id.textViewUserCPF);
        textViewUserNascimento = (TextView) myView.findViewById(R.id.textViewUserNascimento);

        textViewUserEmail.setText(SharedPrefManager.getInstance(myView.getContext()).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(myView.getContext()).getUserName());
        textViewUserNomeCompleto.setText(SharedPrefManager.getInstance(myView.getContext()).getNomeCompleto());
        textViewUserCidade.setText(SharedPrefManager.getInstance(myView.getContext()).getUserCidade());
        textViewUserCPF.setText(SharedPrefManager.getInstance(myView.getContext()).getCpfDeVerdade());
        textViewUserNascimento.setText(SharedPrefManager.getInstance(myView.getContext()).getUserNascimento());
        return myView;
    }
}