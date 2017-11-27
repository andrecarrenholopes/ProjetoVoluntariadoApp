package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andre on 08/09/2017.
 */

public class MinhasInscricoes extends Fragment implements View.OnClickListener{

    View myView;

    private Spinner spinnerVagasInscritas;
    private TextView textViewIdI, textViewNomeI, textViewDescricaoI, textViewRequisitosI, textViewQuantidadeI, textViewRuaI, textViewComplementoI, textViewBairroI, textViewCidadeI, textViewNenhumaVaga;
    private Button buttonIrProjeto, buttonIrInstituicao, buttonCancelarParticipacao;

    private String[]  nomeVaga, descricao, requisito, rua, complemento, bairro, nomeCidade;
    private int [] idProjeto, idVaga, quantidade,numero,idInstituicao;
    private boolean[] aDistancia, pontual;
    private int idVagaEscolhida=0;
    private LinearLayout linearLayoutMinhasInscricoes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.minhas_inscricoes, container, false);

        spinnerVagasInscritas = (Spinner) myView.findViewById(R.id.spinnerVagasInscritas);

        linearLayoutMinhasInscricoes = (LinearLayout) myView.findViewById(R.id.linearLayoutMinhasInscricoes);
        textViewNenhumaVaga = (TextView) myView.findViewById(R.id.textViewNenhumaVaga);

        getInscricoesEmVagas();

        buttonIrProjeto = (Button) myView.findViewById(R.id.buttonIrProjeto);
        buttonIrInstituicao = (Button) myView.findViewById(R.id.buttonIrInstituicao);
        buttonCancelarParticipacao = (Button) myView.findViewById(R.id.buttonCancelarParticipacao);

        buttonIrProjeto.setOnClickListener(this);
        buttonIrInstituicao.setOnClickListener(this);
        buttonCancelarParticipacao.setOnClickListener(this);


        return myView;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (v.getId()) {
            case R.id.buttonIrProjeto:
                Intent i = new Intent(myView.getContext(), Main2Activity.class);
                Bundle extras = new Bundle();
                extras.putInt("idInstituicao", idProjeto[idVagaEscolhida]);
                extras.putString("tipoBuscado", "Projetos");
                i.putExtras(extras);
                myView.getContext().startActivity(i);

                /*
                InformacaoInstituicao infoI = new InformacaoInstituicao();
                infoI.setIdInstituicao(idProjeto[idVagaEscolhida]);
                infoI.getInstituicao();
                infoI.iniciaTextView();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , infoI)
                        .commit();
                */
                break;
            case R.id.buttonIrInstituicao:
                i = new Intent(myView.getContext(), Main2Activity.class);
                extras = new Bundle();
                extras.putInt("idInstituicao", idInstituicao[idVagaEscolhida]);
                extras.putString("tipoBuscado", "Instituições");
                i.putExtras(extras);
                myView.getContext().startActivity(i);
                break;
            case R.id.buttonCancelarParticipacao:
                postDeletaInscricaoCandidato();
                getInscricoesEmVagas();
                break;

        }
    }


    public void getInscricoesEmVagas() {
        final ArrayList<String> listaInstituicao = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_INSCRICOES_EM_VAGAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;
                            if(JA.length()>0) {
                                idProjeto = new int[JA.length()];
                                idVaga= new int[JA.length()];
                                nomeVaga= new String[JA.length()];
                                descricao= new String[JA.length()];
                                requisito= new String[JA.length()];
                                quantidade= new int[JA.length()];
                                aDistancia= new boolean[JA.length()];
                                rua= new String[JA.length()];
                                numero= new int[JA.length()];
                                complemento= new String[JA.length()];
                                bairro= new String[JA.length()];
                                pontual= new boolean[JA.length()];
                                nomeCidade= new String[JA.length()];
                                idInstituicao= new int[JA.length()];

                                for (int i =0; i < JA.length(); i++) {
                                    json = JA.getJSONObject(i);
                                    try {
                                        idProjeto[i] = json.getInt("ID_Projeto");
                                        idVaga[i] = json.getInt("ID_Vaga");
                                        nomeVaga[i] = json.getString("Nome");
                                        descricao[i] = json.getString("Descricao");
                                        requisito[i] = json.getString("Pre-Requisito");
                                        quantidade[i] = json.getInt("Quantidade");
                                        // Ta dando erro nos retornos null
                                        // aDistancia[i] = json.getBoolean("ADistancia");
                                        aDistancia[i] = false;
                                        rua[i] = json.getString("Rua");
                                        //numero[i] = json.getInt("Numero");
                                        numero[i] = 0;
                                        complemento[i] = json.getString("Complemento");
                                        bairro[i] = json.getString("Bairro");
                                        //pontual[i] = json.getBoolean("Pontual");
                                        pontual[i] = false;
                                        nomeCidade[i] = json.getString("nomeCidade");
                                        idInstituicao[i] = json.getInt("ID_Instituicao");
                                    }catch(Exception e){
                                        Toast.makeText(
                                                myView.getContext(),
                                                //error.getMessage(),
                                                "teste erro",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                    //listaInstituicao.add(nomeInstituicao[i]);
                                }
                                textViewNenhumaVaga.setVisibility(myView.GONE);
                                linearLayoutMinhasInscricoes.setVisibility(myView.VISIBLE);
                                spinnerVagas();
                                iniciaTextView();
                            }
                            else {
                                textViewNenhumaVaga.setVisibility(myView.VISIBLE);
                                linearLayoutMinhasInscricoes.setVisibility(myView.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(
                                myView.getContext(),
                                //error.getMessage(),
                                "Erro",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", Integer.toString(SharedPrefManager.getInstance(myView.getContext()).getUserId()));
                //params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

    public void spinnerVagas() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_spinner_item, nomeVaga);
        spinnerVagasInscritas.setAdapter(dataAdapter);
        spinnerVagasInscritas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spinnerVagasInscritas.setSelection(position);
                //instituicao = spinnerProjeto.getSelectedItem().toString();
                idVagaEscolhida = spinnerVagasInscritas.getSelectedItemPosition();
                atualizaInstituicao();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }


    public void iniciaTextView() {
        textViewIdI = (TextView) myView.findViewById(R.id.textViewIdI);
        textViewNomeI = (TextView) myView.findViewById(R.id.textViewNomeI);
        textViewDescricaoI = (TextView) myView.findViewById(R.id.textViewDescricaoI);
        textViewRequisitosI = (TextView) myView.findViewById(R.id.textViewRequisitosI);
        textViewQuantidadeI = (TextView) myView.findViewById(R.id.textViewQuantidadeI);
        textViewRuaI = (TextView) myView.findViewById(R.id.textViewRuaI);
        textViewComplementoI = (TextView) myView.findViewById(R.id.textViewComplementoI);
        textViewBairroI = (TextView) myView.findViewById(R.id.textViewBairroI);
        textViewCidadeI = (TextView) myView.findViewById(R.id.textViewCidadeI);

        textViewIdI.setText(Integer.toString(idVaga[idVagaEscolhida]));

        String [] teste = null;
        textViewNomeI.setText(null);
        //textViewNomeI.setText(nomeVaga[idVagaEscolhida]);
        textViewDescricaoI.setText(descricao[idVagaEscolhida]);
        textViewRequisitosI.setText(requisito[idVagaEscolhida]);
        textViewQuantidadeI.setText(Integer.toString(quantidade[idVagaEscolhida]));
        textViewRuaI.setText(rua[idVagaEscolhida]);
        textViewComplementoI.setText(complemento[idVagaEscolhida]);
        textViewBairroI.setText(bairro[idVagaEscolhida]);
        textViewCidadeI.setText(nomeCidade[idVagaEscolhida]);
    }

    public void atualizaInstituicao() {
        textViewIdI.setText(Integer.toString(idVaga[idVagaEscolhida]));
        textViewNomeI.setText(nomeVaga[idVagaEscolhida]);
        textViewDescricaoI.setText(descricao[idVagaEscolhida]);
        textViewRequisitosI.setText(requisito[idVagaEscolhida]);
        textViewQuantidadeI.setText(Integer.toString(quantidade[idVagaEscolhida]));
        textViewRuaI.setText(rua[idVagaEscolhida]);
        textViewComplementoI.setText(complemento[idVagaEscolhida]);
        textViewBairroI.setText(bairro[idVagaEscolhida]);
        textViewCidadeI.setText(nomeCidade[idVagaEscolhida]);
    }

    public void postDeletaInscricaoCandidato() {
        final ArrayList<String> listaInstituicao = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_DELETE_CANDIDATURA_EM_VAGAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //JSONArray JA = new JSONArray(response);
                            //JSONObject json = null;
                            getInscricoesEmVagas();

                            Toast.makeText(
                                    myView.getContext(),
                                    //error.getMessage(),
                                    "Inscrição em vaga cancelada!",
                                    Toast.LENGTH_LONG
                            ).show();
                            //mostra info

                        } catch (JSONException e) {
                            Toast.makeText(
                                    myView.getContext(),
                                    //error.getMessage(),
                                    "Erro!",
                                    Toast.LENGTH_LONG
                            ).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(
                                myView.getContext(),
                                //error.getMessage(),
                                "Erro",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", Integer.toString(SharedPrefManager.getInstance(myView.getContext()).getUserId()));
                params.put("id_vaga", Integer.toString(idVaga[idVagaEscolhida]));
                return params;
            }
        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }
}