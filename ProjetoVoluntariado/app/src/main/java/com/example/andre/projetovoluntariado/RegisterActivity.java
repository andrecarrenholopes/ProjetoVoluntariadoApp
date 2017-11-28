package com.example.andre.projetovoluntariado;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextCPF, editTextNomeCompleto, editTextNomeDeUsuario, editTextEmail, editTextSenha;
    private Button buttonRegister;
    private ProgressDialog progressDialog;

    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }

        editTextCPF = (EditText) findViewById(R.id.editTextCPF);
        editTextNomeCompleto = (EditText) findViewById(R.id.editTextNomeCompleto);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextNomeDeUsuario = (EditText) findViewById(R.id.editTextNomeDeUsuario);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    private void registerUser() {

        final String cpf = editTextCPF.getText().toString().trim();
        final String nomeCompleto = editTextNomeCompleto.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String nomeDeUsuario = editTextNomeDeUsuario.getText().toString().trim();
        final String password = editTextSenha.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cpf", cpf);
                params.put("nomecompleto", nomeCompleto);
                params.put("email", email);
                params.put("nomedeusuario", nomeDeUsuario);
                params.put("password", password);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if ((view == buttonRegister)&&(validate())){
            registerUser();
        }
        if(view == textViewLogin)
            startActivity(new Intent(this, LoginActivity.class));
    }

    public boolean validate(){
        boolean valid = true;

        String vcpf = editTextCPF.getText().toString().trim();
        String vnomeCompleto = editTextNomeCompleto.getText().toString().trim();
        String vemail = editTextEmail.getText().toString().trim();
        String vnomeDeUsuario = editTextNomeDeUsuario.getText().toString().trim();
        String vpassword = editTextSenha.getText().toString().trim();

        if((vcpf.isEmpty())||(vcpf.length()!=11)||(vcpf.contains("^[0-9]"))){
            Toast.makeText(this,"CPF inválido",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if((vnomeCompleto.isEmpty())||(vnomeCompleto.contains("^[a-Z]"))){
            Toast.makeText(this,"Nome inválido",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        //if((vemail.isEmpty())||(vemail.contains("^[a-Z@]"))){
        if((isValidEmail(vemail))&&(vemail.contains("[.]"))){
            Toast.makeText(this,"Email inválido",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(vnomeDeUsuario.isEmpty()){
            Toast.makeText(this,"Nome usuario inválido",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(vpassword.isEmpty()){
            Toast.makeText(this,"Senha inválida",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private static boolean isValidEmail(String emailt) {
        return !TextUtils.isEmpty(emailt) && android.util.Patterns.EMAIL_ADDRESS.matcher(emailt).matches();
    }

}