package com.example.andre.projetovoluntariado;

/**
 * Created by Andre on 01/09/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Belal on 26/11/16.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_CPF = "usercpf";
    private static final String KEY_NASCIMENTO = "usernascimento";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_ID_CIDADE = "usercidade";
    private static final String KEY_NOME_COMPLETO = "useremail";
    private static final String KEY_NOME_DE_USUARIO = "username";
    private static final String KEY_USER_PAPEL = "papel";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public boolean userLogin(int cpf, String dataNascimento, String email, String id_cidade, String nomeCompleto, String nomeDeUsuario, int papel){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_CPF, cpf);
        editor.putString(KEY_NASCIMENTO, dataNascimento);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_ID_CIDADE, id_cidade);
        editor.putString(KEY_NOME_COMPLETO, nomeCompleto);
        editor.putString(KEY_NOME_DE_USUARIO, nomeDeUsuario);
        editor.putInt(KEY_USER_PAPEL, papel);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_NOME_DE_USUARIO, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public int getUserId(){
        return getUserCPF();
    }

    public int getUserCPF(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_CPF, 0);
    }

    public String getUserNascimento(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NASCIMENTO, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserCidade(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID_CIDADE, null);
    }

    public String getNomeCompleto(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NOME_COMPLETO, null);
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NOME_DE_USUARIO, null);
    }

    public int getUserPapel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_PAPEL, 0);
    }



}
