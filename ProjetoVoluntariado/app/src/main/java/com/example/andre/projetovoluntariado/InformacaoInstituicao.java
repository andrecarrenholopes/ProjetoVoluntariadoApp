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
     * Created by Andre on 21/10/2017.
     */

    public class InformacaoInstituicao extends Fragment implements View.OnClickListener {
        View myView;

        private int idInstituicao;
        private TextView textViewId;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            myView = inflater.inflate(R.layout.informacao_instituicao, container, false);

            textViewId = (TextView) myView.findViewById(R.id.textViewId);
            textViewId.setText(Integer.toString(idInstituicao));
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
    }
