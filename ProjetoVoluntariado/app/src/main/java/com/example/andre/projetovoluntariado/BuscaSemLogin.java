package com.example.andre.projetovoluntariado;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
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


public class BuscaSemLogin  extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    private Button buttonLogin, buttonBusca;

    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;

    public ArrayList<ParentRow> getParentList() {
        return parentList;
    }

    public void setParentList(ArrayList<ParentRow> parentList) {
        this.parentList = parentList;

    }

    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;
    private EditText editTextBusca;

    private ProgressDialog progressDialog;
    private ArrayList<String> listaInstituicao = new ArrayList<String>();
    private ArrayList<String> listaProjeto = new ArrayList<String>();
    private ArrayList<String> listaVagasDosProjeto = new ArrayList<String>();
    String nomeInstituicao[]  = new String[1];
    String nomeProjeto[] ;
    String nomeVagasDosProjeto[] ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_sem_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        buttonBusca = (Button) findViewById(R.id.buttonBusca);
        buttonBusca.setOnClickListener(this);

        editTextBusca = (EditText) findViewById(R.id.editTextBusca);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();

        // The app will crash if display list is not called here.
        displayList();

        // This expands the list.
        expandAll();

        progressDialog = new ProgressDialog(this);
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
                childRows.add(new ChildRow(R.mipmap.generic_icon, listaInstituicao.get(i)));
            }
            parentRow = new ParentRow("Instituição", childRows);
            parentList.add(parentRow);
            /*Toast.makeText(
                    getApplicationContext(),
                    Integer.toString(listaInstituicao.size()) + " Instituições encontradas",
                    Toast.LENGTH_LONG
            ).show();*/
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Nenhuma Instituiçao encontrada",
                    Toast.LENGTH_LONG
            ).show();
        }

        //preenche os projetos
        if(!listaProjeto.isEmpty()) {
            childRows = new ArrayList<ChildRow>();
            for (int i = 0; i < listaProjeto.size() ; i++) {
                childRows.add(new ChildRow(R.mipmap.generic_icon, listaProjeto.get(i)));
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
                    getApplicationContext(),
                    "Nenhum Projeto encontrado",
                    Toast.LENGTH_LONG
            ).show();
        }

        //preenche com as vagas
        if(!listaVagasDosProjeto.isEmpty()) {
            childRows = new ArrayList<ChildRow>();
            for (int i = 0; i < listaVagasDosProjeto.size() ; i++) {
                childRows.add(new ChildRow(R.mipmap.generic_icon, listaVagasDosProjeto.get(i)));
            }
            parentRow = new ParentRow("Vagas de Projetos", childRows);
            parentList.add(parentRow);
            Toast.makeText(
                    getApplicationContext(),
                    Integer.toString(listaInstituicao.size()) + " Vagas em Projetos encontradas",
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Nenhum Vaga de Projeto encontrada",
                    Toast.LENGTH_LONG
            ).show();
        }

        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(BuscaSemLogin.this, parentList);

        myList.setAdapter(listAdapter);

    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        } //end for (int i = 0; i < count; i++)
    }

    private void displayList() {
        loadData();

        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(BuscaSemLogin.this, parentList);

        myList.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();

        return true;
    }

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
        if (view == buttonLogin) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (view == buttonBusca) {
            String itemBuscado = editTextBusca.getText().toString().trim();
            getInstituicao(itemBuscado);
            //getProjeto(itemBuscado);
            //getVagasDosProjetos(itemBuscado);
            //loadBuscaData();

        }
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
        progressDialog.setMessage("Buscando Instituições...");
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
                            int idInstituicao[] = new int[JA.length()];

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
                                getApplicationContext(),
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

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void getProjeto(String itemBuscado) {
        final String finalItemBuscado = itemBuscado;
        listaProjeto = new ArrayList<String>();
        progressDialog.setMessage("Buscando Projetos...");
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
                            int idProjeto[] = new int[JA.length()];

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
                                getApplicationContext(),
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

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void getVagasDosProjetos(String itemBuscado) {
        final String finalItemBuscado = itemBuscado;
        listaVagasDosProjeto = new ArrayList<String>();
        progressDialog.setMessage("Buscando Vagas...");
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
                            int idVagasDosProjeto[] = new int[JA.length()];

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
                                getApplicationContext(),
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

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
