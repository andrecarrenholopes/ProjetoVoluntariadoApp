    package com.example.andre.projetovoluntariado;

    import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
     * Created by Andre on 21/10/2017.
     */

    public class InformacaoInstituicao extends Fragment implements View.OnClickListener {
        View myView;

        private int idInstituicao;
        //private TextView textViewId;
        private TextView textViewId, textViewNome, textViewDescricao, textViewRua, textViewComplemento, textViewBairro, textViewEmail, textViewWebsite, textViewCidade;

        private String nome;
        private String descricao;
        private String rua;
        private String complemento;
        private String bairro;
        private String email;
        private String website;
        private String cidade;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            myView = inflater.inflate(R.layout.informacao_instituicao, container, false);

            textViewId = (TextView) myView.findViewById(R.id.textViewId);
            textViewId.setText(Integer.toString(idInstituicao));

            getInstituicao();
            Toast.makeText(myView.getContext(), "Info da Instituicao: " + Integer.toString(idInstituicao)  + " " + idInstituicao, Toast.LENGTH_LONG).show();
            return myView;
        }

        @Override
        public void onClick(View v) {

        }


        public int getIdInstituicao() {
            return idInstituicao;
        }

        public void setIdInstituicao(int idInstituicao) {
            this.idInstituicao = idInstituicao;
        }

        public void iniciaTextView() {

            textViewId = (TextView) myView.findViewById(R.id.textViewId);
            textViewNome = (TextView) myView.findViewById(R.id.textViewNome);
            textViewDescricao = (TextView) myView.findViewById(R.id.textViewDescricao);
            textViewRua = (TextView) myView.findViewById(R.id.textViewRua);
            textViewComplemento = (TextView) myView.findViewById(R.id.textViewComplemento);
            textViewBairro = (TextView) myView.findViewById(R.id.textViewBairro);
            textViewEmail = (TextView) myView.findViewById(R.id.textViewEmail);
            textViewWebsite = (TextView) myView.findViewById(R.id.textViewWebsite);
            textViewCidade = (TextView) myView.findViewById(R.id.textViewCidade);

            textViewId.setText(Integer.toString(idInstituicao));
            textViewNome.setText(nome);
            textViewDescricao.setText(descricao);
            textViewRua.setText(rua);
            textViewComplemento.setText(complemento);
            textViewBairro.setText(bairro);
            textViewEmail.setText(email);
            textViewWebsite.setText(website);
            textViewCidade.setText(cidade);
        }

        public void getInstituicao() {
            final ArrayList<String> listaInstituicao = new ArrayList<String>();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_GET_PERFIL_INSTITUICAO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressDialog.dismiss();
                            try {
                                JSONArray JA = new JSONArray(response);
                                JSONObject json = null;

                                /*nomeInstituicao = new String[JA.length()];
                                id = new String[JA.length()];
                                idInstituicao = new int[JA.length()];
                                Descricao = new String[JA.length()];
                                Rua  = new String[JA.length()];
                                Complemento = new String[JA.length()];
                                Bairro = new String[JA.length()];
                                Email = new String[JA.length()];
                                Website = new String[JA.length()];
                                ID_Cidade = new String[JA.length()];*/
                                for (int i =0; i < JA.length(); i++) {
                                    json = JA.getJSONObject(i);
                                    idInstituicao = json.getInt("ID_Instituicao");
                                    nome = json.getString("nome");
                                    descricao = json.getString("Descricao");
                                    rua = json.getString("Rua");
                                    complemento = json.getString("Complemento");
                                    bairro = json.getString("Bairro");
                                    email = json.getString("Email");
                                    website = json.getString("Website");
                                    cidade = json.getString("nomeCidade");
                                    //id[i]= json.getString("ID_Estado");
                                    //listaInstituicao.add(nomeInstituicao[i]);
                                }
                                iniciaTextView();
                                //spinner_instituicao();
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
                    params.put("id_instituicao", Integer.toString(idInstituicao));
                    //params.put("password", password);
                    return params;
                }

            };

            RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
        }

    }
