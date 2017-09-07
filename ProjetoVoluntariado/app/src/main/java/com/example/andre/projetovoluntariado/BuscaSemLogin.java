package com.example.andre.projetovoluntariado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BuscaSemLogin  extends AppCompatActivity implements View.OnClickListener {


    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_sem_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == buttonLogin)
            startActivity(new Intent(this, LoginActivity.class));
    }
}
