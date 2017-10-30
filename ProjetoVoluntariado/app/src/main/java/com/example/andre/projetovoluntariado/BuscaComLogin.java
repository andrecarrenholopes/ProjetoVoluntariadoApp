package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
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

public class BuscaComLogin extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    View myView;
    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;


    private Button buttonBusca;

    private EditText editTextBusca;

    private ProgressDialog progressDialog;
    private ArrayList<String> listaInstituicao = new ArrayList<String>();
    private ArrayList<String> listaProjeto = new ArrayList<String>();
    private ArrayList<String> listaVagasDosProjeto = new ArrayList<String>();
    String nomeInstituicao[]  = new String[1];
    int idInstituicao[];
    String nomeProjeto[];
    int idProjeto[];
    String nomeVagasDosProjeto[];
    int idVagasDosProjeto[];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.busca_com_login, container, false);

        searchManager = (SearchManager) getActivity().getSystemService(myView.getContext().SEARCH_SERVICE);

        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();

        buttonBusca = (Button) myView.findViewById(R.id.buttonBusca);
        buttonBusca.setOnClickListener(this);

        editTextBusca = (EditText) myView.findViewById(R.id.editTextBusca);
        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();

        // The app will crash if display list is not called here.
        displayList();

        // This expands the list.
        expandAll();

        progressDialog = new ProgressDialog(myView.getContext());
        return myView;
    }

    private void loadData() {
        ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
        ParentRow parentRow = null;

        parentRow = new ParentRow("Instituições", childRows);
        parentList.add(parentRow);

        parentRow = new ParentRow("Projetos", childRows);
        parentList.add(parentRow);

        parentRow = new ParentRow("Vagas", childRows);
        parentList.add(parentRow);
    }

    private void loadBuscaData() {

        parentList.clear();

        ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
        ParentRow parentRow = null;

        //preenche as instituições
        if(!listaInstituicao.isEmpty()) {
            childRows = new ArrayList<ChildRow>();
            for (int i = 0; i < listaInstituicao.size() ; i++) {
                childRows.add(new ChildRow(R.mipmap.generic_icon, listaInstituicao.get(i), idInstituicao[i]));
            }
            parentRow = new ParentRow("Instituições", childRows);
            parentList.add(parentRow);
            /*Toast.makeText(
                    getApplicationContext(),
                    Integer.toString(listaInstituicao.size()) + " Instituições encontradas",
                    Toast.LENGTH_LONG
            ).show();*/
        }
        else {
            Toast.makeText(
                    myView.getContext(),
                    "Nenhuma Instituiçao encontrada",
                    Toast.LENGTH_LONG
            ).show();
        }

        //preenche os projetos
        if(!listaProjeto.isEmpty()) {
            childRows = new ArrayList<ChildRow>();
            for (int i = 0; i < listaProjeto.size() ; i++) {
                childRows.add(new ChildRow(R.mipmap.generic_icon, listaProjeto.get(i), idProjeto[i]));
            }
            parentRow = new ParentRow("Projetos", childRows);
            parentList.add(parentRow);
            /*Toast.makeText(
                    getApplicationContext(),
                    Integer.toString(listaInstituicao.size()) + " Projetos encontrados",
                    Toast.LENGTH_LONG
            ).show();*/
        }
        else {
            Toast.makeText(
                    myView.getContext(),
                    "Nenhum Projeto encontrado",
                    Toast.LENGTH_LONG
            ).show();
        }

        //preenche com as vagas
        if(!listaVagasDosProjeto.isEmpty()) {
            childRows = new ArrayList<ChildRow>();
            for (int i = 0; i < listaVagasDosProjeto.size() ; i++) {
                childRows.add(new ChildRow(R.mipmap.generic_icon, listaVagasDosProjeto.get(i), idVagasDosProjeto[i]));
            }
            parentRow = new ParentRow("Vagas", childRows);
            parentList.add(parentRow);
            /*Toast.makeText(
                    myView.getContext(),
                    Integer.toString(listaInstituicao.size()) + " Vagas em Projetos encontradas",
                    Toast.LENGTH_LONG
            ).show();*/
        }
        else {
            Toast.makeText(
                    myView.getContext(),
                    "Nenhum Vaga de Projeto encontrada",
                    Toast.LENGTH_LONG
            ).show();
        }

        myList = (ExpandableListView) myView.findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(myView.getContext(), parentList); //BuscaSemLogin.this, parentList);

        myList.setAdapter(listAdapter);
        myList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText(
                        myView.getContext(),
                        "Ultima tentativa",
                        Toast.LENGTH_LONG
                ).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        } //end for (int i = 0; i < count; i++)
    }

    private void displayList() {
        loadData();

        myList = (ExpandableListView) myView.findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(myView.getContext(), parentList);

        myList.setAdapter(listAdapter);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main2,menu); // getMenuInflater().inflate(R.menu.main2, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (android.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return false;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view == buttonBusca) {
            String itemBuscado = editTextBusca.getText().toString().trim();
            getInstituicao(itemBuscado);
            //getProjeto(itemBuscado);
            //getVagasDosProjetos(itemBuscado);
            //loadBuscaData();
        }
        /*else if (view == myList) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new MeuPerfil())
                    .commit();
        }*/
    }
    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }

    public void getInstituicao(String itemBuscado) {
        final String finalItemBuscado = itemBuscado;
        listaInstituicao = new ArrayList<String>();
        progressDialog.setMessage("Buscando Dados...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_TODAS_INSTITUICOES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeInstituicao = new String[JA.length()];
                            idInstituicao = new int[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeInstituicao[i] = json.getString("nome");
                                idInstituicao[i] = json.getInt("ID_Instituicao");

                                listaInstituicao.add(nomeInstituicao[i]);
                            }
                            //loadBuscaData();
                            getProjeto(finalItemBuscado);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

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
                params.put("nome", finalItemBuscado);
                //params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

    public void getProjeto(String itemBuscado) {
        final String finalItemBuscado = itemBuscado;
        listaProjeto = new ArrayList<String>();
        progressDialog.setMessage("Buscando Dados...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_TODOS_PROJETOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeProjeto = new String[JA.length()];
                            idProjeto = new int[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeProjeto[i] = json.getString("nome");
                                idProjeto[i] = json.getInt("ID_Projeto");

                                listaProjeto.add(nomeProjeto[i]);
                            }
                            //loadBuscaData();
                            getVagasDosProjetos(finalItemBuscado);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

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
                params.put("nome", finalItemBuscado);
                //params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

    public void getVagasDosProjetos(String itemBuscado) {
        final String finalItemBuscado = itemBuscado;
        listaVagasDosProjeto = new ArrayList<String>();
        progressDialog.setMessage("Buscando Dados...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_TODAS_VAGAS_DOS_PROJETOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray JA = new JSONArray(response);
                            JSONObject json = null;

                            nomeVagasDosProjeto = new String[JA.length()];
                            idVagasDosProjeto = new int[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeVagasDosProjeto[i] = json.getString("nome");
                                idVagasDosProjeto[i] = json.getInt("ID_Vaga");

                                listaVagasDosProjeto.add(nomeVagasDosProjeto[i]);
                            }
                            loadBuscaData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

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
                params.put("nome", finalItemBuscado);
                params.put("pagina", Integer.toString(0));
                return params;
            }
        };

        RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
    }

}