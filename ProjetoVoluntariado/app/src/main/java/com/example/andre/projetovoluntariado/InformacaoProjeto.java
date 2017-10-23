    package com.example.andre.projetovoluntariado;

    import android.app.Fragment;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    /**
     * Created by Andre on 22/10/2017.
     */

    public class InformacaoProjeto extends Fragment implements View.OnClickListener {
        View myView;

        private int idProjeto;
        private TextView textViewId;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            myView = inflater.inflate(R.layout.informacao_projeto, container, false);

            textViewId = (TextView) myView.findViewById(R.id.textViewId);
            textViewId.setText(Integer.toString(idProjeto));
            Toast.makeText(myView.getContext(), "Info da Instituicao: " + Integer.toString(idProjeto), Toast.LENGTH_LONG).show();
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
    }
