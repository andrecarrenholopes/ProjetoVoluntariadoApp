package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class CadastraProjeto extends Fragment implements View.OnClickListener{

    View myView;
    private EditText editTextNome, editTextDescricao;
    private Button buttonCadastraProjeto;
    private Spinner spinnerInstituicao;
    private ProgressDialog progressDialog;

    String id[];
    String nomeInstituicao[];
    int idInstituicao[];
    private String instituicao = "";
    private int idInstituicaoEscolhido =0 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cadastra_projeto, container, false);

        spinnerInstituicao = (Spinner) myView.findViewById(R.id.spinnerInstituicao);

        editTextNome = (EditText) myView.findViewById(R.id.editTextBusca);
        editTextDescricao = (EditText) myView.findViewById(R.id.editTextDescricao);

        getInstituicao();

        buttonCadastraProjeto = (Button) myView.findViewById(R.id.buttonCadastraProjeto);
        buttonCadastraProjeto.setOnClickListener(this);

        progressDialog = new ProgressDialog(myView.getContext());
        return myView;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCadastraProjeto:
                cadastraProjeto();
                break;
        }
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

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeInstituicao[i] = json.getString("nome");
                                idInstituicao[i] = json.getInt("ID_Instituicao");
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }


    public void cadastraProjeto() {
        //final ArrayList<String> listaCidade = new ArrayList<String>();
        final String nome = editTextNome.getText().toString().trim();
        final String descricao = editTextDescricao.getText().toString().trim();


        //Toast.makeText(myView.getContext(), instituicao + " " + idInstituicao[idInstituicaoEscolhido], Toast.LENGTH_LONG).show();



        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_CADASTRA_PROJETO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(myView.getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(myView.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome", nome);
                params.put("descricao", descricao);
                params.put("id_instituicao", Integer.toString(idInstituicao[idInstituicaoEscolhido]));
                params.put("id_pessoa", Integer.toString(SharedPrefManager.getInstance(myView.getContext()).getUserId()));
                return params;
            }
        };


        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);

    }

}