package com.example.andre.projetovoluntariado;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();

            String tipoBuscado = extras.getString("tipoBuscado");
            int id = extras.getInt("idInstituicao");

            switch (tipoBuscado) {
                case "Instituições":
                    loadPerfilInstituicao(id);
                    break;
                case "Projetos":
                    loadPerfilProjeto(id);
                    break;
                case "Vagas":
                    loadPerfilVaga(id);
                    break;
            }
        }
        else {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new BuscaComLogin())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_meu_perfil) {
            //startActivity(new Intent(this, ProfileActivity.class));
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new MeuPerfil())
                    .commit();
        } else if (id == R.id.nav_buscar) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new BuscaComLogin())
                    .commit();
        } else if (id == R.id.nav_minhas_inscricoes) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new MinhasInscricoes())
                    .commit();
        } else if (id == R.id.nav_cadastro_projetos) {
            /*
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new MenuCadastro())
                    .commit();
                    */
            changeFragment(new MenuCadastro());
        } else if (id == R.id.nav_meus_projetos) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new MeusProjetos())
                    .commit();
        } else if (id == R.id.nav_admin) {
            if( SharedPrefManager.getInstance(this).getUserPapel() == 1) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new MeuPerfil())
                        .commit();
            } else {
                Toast.makeText(this, "Usuário sem acesso", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_logout) {
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadPerfilInstituicao(int id) {
        InformacaoInstituicao infoI = new InformacaoInstituicao();

        //Bundle extras = getIntent().getExtras();
        infoI.setIdInstituicao(id);
        //Toast.makeText(this, "Info da Instituicao: " + id, Toast.LENGTH_LONG).show();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , infoI)
                .commit();
    }

    private void loadPerfilProjeto(int id) {
        InformacaoProjeto infoP = new InformacaoProjeto();

        //Bundle extras = getIntent().getExtras();
        infoP.setIdProjeto(id);
        //Toast.makeText(this, "Info do Projeto: " + id, Toast.LENGTH_LONG).show();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , infoP)
                .commit();
    }

    private  void loadPerfilVaga(int id) {
        InformacaoVaga infoV = new InformacaoVaga();

        //Bundle extras = getIntent().getExtras();
        infoV.setIdVaga(id);
        //infoV.getVaga();
        //Toast.makeText(this, "Info da Instituicao: " + id, Toast.LENGTH_LONG).show();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , infoV)
                .commit();
    }

    public FragmentManager buscaFragmentManager() {
        FragmentManager fragmentManager = getFragmentManager();
        return fragmentManager;
    }

    public void changeFragment(Fragment obj) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, obj).commit();
    }
}
