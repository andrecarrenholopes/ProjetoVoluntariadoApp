package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by Andre on 22/10/2017.
 */

public class InformacaoVaga extends Fragment implements View.OnClickListener {
    View myView;

    private int idVaga;

    private TextView textViewId, textViewNome, textViewDescricao, textViewRequisitos, textViewQuantidade, textViewRua, textViewComplemento, textViewBairro, textViewCidade;

    private Button buttonRegisterVaga;

    private String[] nomeProjeto;
    private String[] nomeVaga;
    private String[] Descricao;
    private String[] Requisito;
    private String[] Quantidade;
    private String[] Rua;
    private String[] Complemento;
    private String[] Bairro;
    private String[] nomeCidade;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.informacao_vaga, container, false);

        //iniciaTextView();
        getVaga();


        //Toast.makeText(myView.getContext(), "Info da Instituicao: " + Integer.toString(idVaga), Toast.LENGTH_LONG).show();
        //getVaga();

        buttonRegisterVaga = (Button) myView.findViewById(R.id.buttonRegisterVaga);
        buttonRegisterVaga.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegisterVaga:
                preencheVaga();
                //Toast.makeText(myView.getContext(), "Obrigado por se inscrever", Toast.LENGTH_LONG).show();
                break;
            }

    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public void getVaga() {
        final ArrayList<String> listaInstituicao = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_VAGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeProjeto = new String[JA.length()];
                            nomeVaga = new String[JA.length()];
                            Descricao = new String[JA.length()];
                            Requisito = new String[JA.length()];
                            Quantidade = new String[JA.length()];
                            Rua  = new String[JA.length()];
                            Complemento = new String[JA.length()];
                            Bairro = new String[JA.length()];
                            nomeCidade = new String[JA.length()];
                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeProjeto[i] = json.getString("nomeProjeto");
                                nomeVaga[i] = json.getString("nomeVaga");
                                Descricao[i] = json.getString("Descricao");
                                Requisito[i] = json.getString("Pre-Requisito");
                                Quantidade[i] = json.getString("Quantidade");
                                Rua[i] = json.getString("Rua");
                                Complemento[i] = json.getString("Complemento");
                                Bairro[i] = json.getString("Bairro");
                                nomeCidade[i] = json.getString("nomeCidade");

                                //listaInstituicao.add(nomeInstituicao[i]);
                            }
                            //spinner_instituicao();
                            iniciaTextView();
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
                params.put("id_vaga", Integer.toString(idVaga));
                //params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

    public void iniciaTextView() {

        if(nomeVaga.length > 0) {

            textViewId = (TextView) myView.findViewById(R.id.textViewId);
            textViewNome = (TextView) myView.findViewById(R.id.textViewNome);
            textViewDescricao = (TextView) myView.findViewById(R.id.textViewDescricao);
            textViewRequisitos = (TextView) myView.findViewById(R.id.textViewRequisitos);
            textViewQuantidade = (TextView) myView.findViewById(R.id.textViewQuantidade);
            textViewRua = (TextView) myView.findViewById(R.id.textViewRua);
            textViewComplemento = (TextView) myView.findViewById(R.id.textViewComplemento);
            textViewBairro = (TextView) myView.findViewById(R.id.textViewBairro);
            textViewCidade = (TextView) myView.findViewById(R.id.textViewCidade);

            textViewId.setText(Integer.toString(idVaga));
            textViewNome.setText(nomeVaga[0]);
            textViewDescricao.setText(Descricao[0]);
            textViewRequisitos.setText(Requisito[0]);
            textViewQuantidade.setText(Quantidade[0]);
            textViewRua.setText(Rua[0]);
            textViewComplemento.setText(Complemento[0]);
            textViewBairro.setText(Bairro[0]);
            textViewCidade.setText(nomeCidade[0]);
        }
    }

    public void preencheVaga() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_PREENCHE_VAGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        Toast.makeText(
                                myView.getContext(),
                                //error.getMessage(),
                                "Erro",
                                Toast.LENGTH_LONG
                        ).show();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;
                            if(JA.length() < 0) {
                                if(JA.getJSONObject(0).getBoolean("error")){
                                    Toast.makeText(
                                            myView.getContext(),
                                            //error.getMessage(),
                                            "Erro",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                                else {
                                    Toast.makeText(
                                            myView.getContext(),
                                            //error.getMessage(),
                                            "Você foi cadastrado com sucesso",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                            else {
                                Toast.makeText(
                                        myView.getContext(),
                                        //error.getMessage(),
                                        "Vaga não preenchida",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);

                                //listaInstituicao.add(nomeInstituicao[i]);
                            }
                            //spinner_instituicao();
                            //getVaga();
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
                params.put("id_vaga", Integer.toString(idVaga));
                //params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }
}
