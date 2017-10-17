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
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;


    private ProgressDialog progressDialog;
    private ArrayList<String> listaInstituicao = new ArrayList<String>();
    String nomeInstituicao[]  = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_sem_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        buttonBusca = (Button) findViewById(R.id.buttonBusca);
        buttonBusca.setOnClickListener(this);

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
        getInstituicao();

        parentList.clear();

        ArrayList<ChildRow> childRows = new ArrayList<ChildRow>();
        ParentRow parentRow = null;

        childRows.add(new ChildRow(R.mipmap.generic_icon
                ,"Instituição do Juvenal"));
        childRows.add(new ChildRow(R.mipmap.generic_icon
                ,"Instituição do Pedro"));
        parentRow = new ParentRow("Instituições", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow(R.mipmap.generic_icon
                ,"Projeto X"));
        childRows.add(new ChildRow(R.mipmap.generic_icon
                ,"Projeto Y"));
        parentRow = new ParentRow("Projetos", childRows);
        parentList.add(parentRow);

        childRows = new ArrayList<ChildRow>();
        childRows.add(new ChildRow(R.mipmap.generic_icon
                ,"Vaga Y"));
        childRows.add(new ChildRow(R.mipmap.generic_icon
                ,"Vaga Z"));
        parentRow = new ParentRow("Vagas", childRows);
        parentList.add(parentRow);

        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(BuscaSemLogin.this, parentList);

        myList.setAdapter(listAdapter);

        if(!listaInstituicao.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    //error.getMessage(),
                    "PHP" + listaInstituicao.get(0),
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    //error.getMessage(),
                    "listaInstituicao" + nomeInstituicao.length,
                    Toast.LENGTH_LONG
            ).show();
        }
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
            loadBuscaData();
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

    public void getInstituicao() {
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
                            int idInstituicao[] = new int[JA.length()];

                            for (int i =0; i < JA.length(); i++) {
                                json = JA.getJSONObject(i);
                                nomeInstituicao[i] = json.getString("nome");
                                idInstituicao[i] = json.getInt("ID_Instituicao");

                                listaInstituicao.add(nomeInstituicao[i]);
                            }

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
                params.put("nome", "Inst");
                //params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
