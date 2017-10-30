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

public class CadastraInstituicao extends Fragment implements View.OnClickListener{

    View myView;
    private EditText  editTextNome, editTextDescricao, editTextRua, editTextComplemento, editTextBairro, editTextEmail, editTextWebsite;
    private String estado = "São Paulo";
    private String cidade = null;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private Spinner spinnerEstado, spinnerCidade;

    String id[];
    String nomeEstado[];
    String nomeCidade[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cadastra_instituicao, container, false);

        spinnerEstado = (Spinner) myView.findViewById(R.id.spinnerEstado);
        spinnerCidade = (Spinner) myView.findViewById(R.id.spinnerCidade);

        editTextNome = (EditText) myView.findViewById(R.id.editTextNomeInstituicao);
        editTextDescricao  = (EditText) myView.findViewById(R.id.editTextDescricao);
        editTextRua = (EditText) myView.findViewById(R.id.editTextRua);
        editTextComplemento = (EditText) myView.findViewById(R.id.editTextComplemento);
        editTextBairro = (EditText) myView.findViewById(R.id.editTextBairro);
        editTextEmail = (EditText) myView.findViewById(R.id.editTextEmail);
        editTextWebsite = (EditText) myView.findViewById(R.id.editTextWebsite);;


        getEstado();
        getCidade();

        buttonRegister = (Button) myView.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);

        progressDialog = new ProgressDialog(myView.getContext());
        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                cadastraInstituicao();
                break;
        }
    }

    public void getEstado() {
        final ArrayList<String> listaEstado = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_ENDERECO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeEstado = new String[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeEstado[i] = json.getString("Nome");
                                //id[i]= json.getString("ID_Estado");
                                listaEstado.add(nomeEstado[i]);
                            }
                            spinner_estado();
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
                //params.put("username", username);
                //params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

    public void spinner_estado() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_spinner_item, nomeEstado);
        spinnerEstado.setAdapter(dataAdapter);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spinnerEstado.setSelection(position);
                estado = spinnerEstado.getSelectedItem().toString();
                if(cidade != null) {
                    getCidade();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void getCidade() {
        final ArrayList<String> listaCidade = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ENDERECO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();

                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeCidade = new String[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeCidade[i] = json.getString("Nome");
                                //id[i]= json.getString("ID_Estado");
                                listaCidade.add(nomeCidade[i]);

                            }
                            spinner_cidade();
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
                                error.getMessage(),
//                                "Teste1",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("estado", estado);
                //params.put("password", password);
                return params;
            }

        };
        //Toast.makeText(myView.getContext(), "Map", Toast.LENGTH_LONG).show();
        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

    public void spinner_cidade() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_spinner_item, nomeCidade);
        spinnerCidade.setAdapter(dataAdapter);
        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spinnerCidade.setSelection(position);
                cidade = spinnerCidade.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void cadastraInstituicao() {
        //final ArrayList<String> listaCidade = new ArrayList<String>();
        final String nome = editTextNome.getText().toString().trim();
        final String descricao = editTextDescricao.getText().toString().trim();
        final String rua = editTextRua.getText().toString().trim();
        final String complemento = editTextComplemento.getText().toString().trim();
        final String bairro = editTextBairro.getText().toString().trim();
        final String emailInst = editTextEmail.getText().toString().trim();
        final String website = editTextWebsite.getText().toString().trim();
        final String id_cidade = "1";

        progressDialog.setMessage("Registrando instituição...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_CADASTRA_INSTITUICAO,
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
                params.put("rua", rua);
                params.put("complemento", complemento);
                params.put("bairro", bairro);
                params.put("email", emailInst);
                params.put("website", website);
                params.put("id_cidade", id_cidade);
                params.put("id_user", Integer.toString(SharedPrefManager.getInstance(myView.getContext()).getUserId()));
                return params;
            }
        };


        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }
}