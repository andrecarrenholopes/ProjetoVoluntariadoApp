package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Andre on 08/09/2017.
 */

public class MinhasInscricoes extends Fragment {

    View myView;

    Spinner spinnerVagasInscritas;
    TextView textViewIdI, textViewNomeI, textViewDescricaoI, textViewRequisitosI, textViewQuantidadeI, textViewRuaI, textViewComplementoI, textViewBairroI, textViewCidadeI;
    Button buttonRegisterVagaI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.minhas_inscricoes, container, false);
        return myView;
    }

    /*
    public void getInscricoesEmVagas() {
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
    */
}