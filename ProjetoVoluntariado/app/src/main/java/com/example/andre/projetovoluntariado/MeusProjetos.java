package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MeusProjetos extends Fragment implements View.OnClickListener  {

    View myView;

    String id[];
    String nomeInstituicao[], Descricao[], Rua[], Complemento[], Bairro[], Email[], Website[], ID_Cidade[];
    int idInstituicao[];
    private String instituicao = "";
    private int idInstituicaoEscolhido =0 ;

    private TextView textViewInstituicaNome, textViewInstituicaoRua, textViewInstituicaoComplemento, textViewInstituicaoBairro, textViewInstituicaoEmail, textViewInstituicaoWebsite, textViewInstituicaoCidade, textViewInstituicaoDescricao;

    private Spinner spinnerInstituicao;

    String nomeProjeto[];
    int idProjeto[];
    private int idProjetoEscolhido =0;
    private Spinner spinnerProjeto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.meus_projetos, container, false);

        //textView da instituicao
        textViewInstituicaNome = (TextView) myView.findViewById(R.id.textViewInstituicaNome);
        textViewInstituicaoRua = (TextView) myView.findViewById(R.id.textViewInstituicaoRua);
        textViewInstituicaoComplemento = (TextView) myView.findViewById(R.id.textViewInstituicaoComplemento);
        textViewInstituicaoBairro = (TextView) myView.findViewById(R.id.textViewInstituicaoBairro);
        textViewInstituicaoEmail = (TextView) myView.findViewById(R.id.textViewInstituicaoEmail);
        textViewInstituicaoWebsite = (TextView) myView.findViewById(R.id.textViewInstituicaoWebsite) ;
        textViewInstituicaoCidade = (TextView) myView.findViewById(R.id.textViewInstituicaoCidade);
        textViewInstituicaoDescricao = (TextView) myView.findViewById(R.id.textViewInstituicaoDescricao);

        spinnerInstituicao = (Spinner) myView.findViewById(R.id.spinnerInstituicao);
        //ListView lista = (ListView) myView.findViewById(R.id.listViewProjetos);
        getInstituicao();

        spinnerProjeto = (Spinner) myView.findViewById(R.id.spinnerProjeto);
        getProjeto();
        //ArrayList<String> projetos = preencherDados();

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.select_dialog_singlechoice, projetos);
        //lista.setAdapter(arrayAdapter);
        return myView;
    }

    private ArrayList preencherDados() {
        ArrayList<String> dados = new ArrayList<String>();
        //dados = null;
        dados.add("Instituição 2");
        dados.add("Instituição 2");
        /*
        for(int i = 0; i <nomeInstituicao.length; i++) {
            dados.add(nomeInstituicao[i]);
        }

        dados.add("Instituição 2");
        dados.add("Instituição 3");
        dados.add("Instituição 4");
        dados.add("Instituição 5");
        dados.add("Instituição 6");
        dados.add("Instituição 7");
        dados.add("Instituição 8");
        */
        return dados;
    }

    public void getInstituicao() {
        final ArrayList<String> listaInstituicao = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_INSTITUICAO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeInstituicao = new String[JA.length()];
                            id = new String[JA.length()];
                            idInstituicao = new int[JA.length()];
                            Descricao = new String[JA.length()];
                            Rua  = new String[JA.length()];
                            Complemento = new String[JA.length()];
                            Bairro = new String[JA.length()];
                            Email = new String[JA.length()];
                            Website = new String[JA.length()];
                            ID_Cidade = new String[JA.length()];
                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeInstituicao[i] = json.getString("nome");
                                idInstituicao[i] = json.getInt("ID_Instituicao");
                                Descricao[i] = json.getString("Descricao");
                                Rua[i] = json.getString("Rua");
                                Complemento[i] = json.getString("Complemento");
                                Bairro[i] = json.getString("Bairro");
                                Email[i] = json.getString("Email");
                                Website[i] = json.getString("Website");
                                ID_Cidade[i] = json.getString("ID_Cidade");
                                //id[i]= json.getString("ID_Estado");
                                listaInstituicao.add(nomeInstituicao[i]);
                            }
                            spinner_instituicao();
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
                                "Teste",
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

    public void spinner_instituicao() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_spinner_item, nomeInstituicao);
        spinnerInstituicao.setAdapter(dataAdapter);
        spinnerInstituicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spinnerInstituicao.setSelection(position);
                instituicao = spinnerInstituicao.getSelectedItem().toString();
                idInstituicaoEscolhido = spinnerInstituicao.getSelectedItemPosition();
                atualizaInstituicao();
                Toast.makeText(
                        myView.getContext(),
                        //error.getMessage(),
                        nomeInstituicao[idInstituicaoEscolhido] + " " + Descricao[idInstituicaoEscolhido] + " " +
                                Rua[idInstituicaoEscolhido] + " " + Complemento[idInstituicaoEscolhido] + " " +
                                Bairro[idInstituicaoEscolhido] + " " + Email[idInstituicaoEscolhido] + " " +
                                Website[idInstituicaoEscolhido] + " " + ID_Cidade[idInstituicaoEscolhido],
                        Toast.LENGTH_LONG
                ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void atualizaInstituicao() {
        textViewInstituicaNome.setText(nomeInstituicao[idInstituicaoEscolhido]);
        textViewInstituicaoRua.setText(Rua[idInstituicaoEscolhido]);
        textViewInstituicaoComplemento.setText(Complemento[idInstituicaoEscolhido]);
        textViewInstituicaoBairro.setText(Bairro[idInstituicaoEscolhido]);
        textViewInstituicaoEmail.setText(Email[idInstituicaoEscolhido]);
        textViewInstituicaoWebsite.setText(Website[idInstituicaoEscolhido]);
        textViewInstituicaoCidade.setText(ID_Cidade[idInstituicaoEscolhido]);
        textViewInstituicaoDescricao.setText(Descricao[idInstituicaoEscolhido]);
    }

    public void getProjeto() {
        final ArrayList<String> listaProjeto = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_PROJETO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeProjeto = new String[JA.length()];
                            id = new String[JA.length()];
                            idProjeto = new int[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeProjeto[i] = json.getString("nome");
                                idProjeto[i] = json.getInt("ID_Projeto");
                                //id[i]= json.getString("ID_Estado");
                                listaProjeto.add(nomeProjeto[i]);
                            }
                            spinner_projeto();
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
                                "Teste",
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

    public void spinner_projeto() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_spinner_item, nomeProjeto);
        spinnerProjeto.setAdapter(dataAdapter);
        spinnerProjeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spinnerProjeto.setSelection(position);
                //instituicao = spinnerProjeto.getSelectedItem().toString();
                idProjetoEscolhido = spinnerProjeto.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }


}