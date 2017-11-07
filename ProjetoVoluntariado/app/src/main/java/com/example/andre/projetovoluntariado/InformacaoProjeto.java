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
     * Created by Andre on 22/10/2017.
     */

    public class InformacaoProjeto extends Fragment implements View.OnClickListener {
        View myView;

        private String nomeDoProjeto ="";
        private String descricao="";
        private int idInstituicao;
        private String nomeDaInstituicao="";
        private int idProjeto;

        private TextView textViewNomeProjeto, textViewDescricaoProjeto, textViewInstituicaoDoProjeto;
        //private TextView textViewId;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            myView = inflater.inflate(R.layout.informacao_projeto, container, false);

            //textViewId = (TextView) myView.findViewById(R.id.textViewId);
            //textViewId.setText(Integer.toString(idProjeto));
            //Toast.makeText(myView.getContext(), "Info da Instituicao: " + Integer.toString(idProjeto), Toast.LENGTH_LONG).show();
            getProjeto();

            return myView;
        }

        @Override
        public void onClick(View v) {

        }


        public int getIdProjeto() {
            return idProjeto;
        }

        public void setIdProjeto(int idInstituicao) {
            this.idProjeto = idInstituicao;
        }

        public void getProjeto() {
            final ArrayList<String> listaProjeto = new ArrayList<String>();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_GET_PERFIL_PROJETO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressDialog.dismiss();
                            try {
                                JSONArray JA = new JSONArray(response);
                                JSONObject json = null;


                                for (int i =0; i < JA.length(); i++) {
                                    json = JA.getJSONObject(i);
                                    nomeDoProjeto = json.getString("nome");
                                    descricao = json.getString("Descricao");
                                    idInstituicao = json.getInt("ID_Instituicao");
                                    nomeDaInstituicao = json.getString("NomeDaInstituicao");

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
                    params.put("id_projeto", Integer.toString(idProjeto));
                    //params.put("password", password);
                    return params;
                }

            };

            RequestHandler.getInstance(myView.getContext()).addToRequestQueue(stringRequest);
        }

        public void iniciaTextView() {

            textViewNomeProjeto = (TextView) myView.findViewById(R.id.textViewNomeProjeto);
            textViewDescricaoProjeto = (TextView) myView.findViewById(R.id.textViewDescricaoProjeto);
            textViewInstituicaoDoProjeto = (TextView) myView.findViewById(R.id.textViewInstituicaoDoProjeto);

            textViewNomeProjeto.setText(nomeDoProjeto);
            textViewDescricaoProjeto.setText(descricao);
            textViewInstituicaoDoProjeto.setText(nomeDaInstituicao);
        }
    }
