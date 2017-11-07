package com.example.andre.projetovoluntariado;

/**
 * Created by Andre on 31/08/2017.
 */

public class Constants {
        private static final String ROOT_URL = "http://10.0.3.2/projetovoluntariado/Android/v1/";
        public static final String URL_REGISTER = ROOT_URL+"registerUser.php";
        public static final String URL_LOGIN = ROOT_URL+"userLogin.php";
        public static final String URL_ENDERECO = ROOT_URL+"getEstado.php";

        public static final String URL_CADASTRA_PROJETO = ROOT_URL+"cadastraProjeto.php";
        public static final String URL_CADASTRA_INSTITUICAO = ROOT_URL+"cadastraInstituicao.php";
        public static final String URL_CADASTRA_VAGAS_PROJETO = ROOT_URL+"cadastraVagasProjeto.php";

        public static final String URL_PREENCHE_VAGA =  ROOT_URL+"preencheVaga.php"; //cadastra o usuario na vaga

        public static final String URL_GET_INSTITUICAO = ROOT_URL+"getInstituicao.php"; //intituição do usuário pra cadastrar projeto
        public static final String URL_GET_PROJETO = ROOT_URL+"getProjeto.php"; //projeto do usuário pra cadastrar vagas

        public static final String URL_GET_VAGA = ROOT_URL+"getVaga.php"; //projeto do usuário pra cadastrar vagas
        public static final String URL_GET_PERFIL_INSTITUICAO = ROOT_URL+"getPerfilInstituicao.php"; //info de apenas uma instituição
        public static final String URL_GET_PERFIL_PROJETO = ROOT_URL+"getPerfilProjeto.php"; //info de apenas um projeto

        public static final String URL_GET_TODAS_INSTITUICOES = ROOT_URL+"getTodasInstituicoes.php";
        public static final String URL_GET_TODOS_PROJETOS = ROOT_URL+"getTodosProjetos.php";
        public static final String URL_GET_TODAS_VAGAS_DOS_PROJETOS = ROOT_URL+"getTodasVagasDosProjetos.php";
}
