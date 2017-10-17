<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();
			
		}

		/*CRUD -> C -> CREATE */

		public function createUser($cpf, $nomecompleto, $email, $nomedeusuario, $pass){
			
			if( $this->isUserExist($cpf, $nomedeusuario,$email) ){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `pessoa`(`CPF`, `Nome Completo`, `DataNascimento`, `Email`, `Papel`, `NomeDeUsuario`, `Senha`, `ID_Cidade`) VALUES (?,?,null,?,2,?,?,null)");
				
				$stmt->bind_param("sssss",$cpf, $nomecompleto, $email, $nomedeusuario, $password);
				
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}
		
		
		
		public function userLogin($username, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT cpf FROM `pessoa` WHERE NomeDeUsuario = ? AND senha = ?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		public function getUserByUsername($username){
			//$stmt = $this->con->prepare("SELECT * FROM `pessoa` WHERE NomeDeUsuario = ?");
			$stmt = $this->con->prepare(" SELECT CPF, DataNascimento, Email, if(Nome IS NULL, 'sem cidade', Nome) AS ID_Cidade, `Nome Completo`, 			  NomeDeUsuario, Papel FROM `pessoa` p 
				LEFT JOIN cidade c on p.ID_Cidade = c.ID_Cidade
				WHERE NomeDeUsuario = ?");

			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		

		private function isUserExist($cpf, $nomedeusuario, $email){
			$stmt = $this->con->prepare("SELECT cpf FROM `pessoa` WHERE NomeDeUsuario = ? OR email = ? OR cpf = ? ");
			$stmt->bind_param("sss", $nomedeusuario, $email, $cpf);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		public function createInstituicao($nome,$descricao,$rua,$complemento,$bairro,$email,$website,$id_cidade, $id_user) {
			//if( $this->instituicaoExist($nome, $nomedeusuario,$email) ){
				//return 0; 
			//}else{
				
				$stmt = $this->con->prepare("INSERT INTO `instituicao`(`Nome`, `Descricao`, `Rua`, `Complemento`, `Bairro`, `Email`, `Logotipo`, `Website`, `ID_Cidade`) VALUES (?,?,?,?,?,?,null,?,?)");
				
				$stmt->bind_param("ssssssss",$nome, $descricao, $rua, $complemento, $bairro, $email,$website,$id_cidade);
				
				if($stmt->execute()){
					$idInst = $stmt->insert_id;
					$stmt = $this->con->prepare("INSERT INTO `pessoa-instituicao`(`ID_Pessoa`, `ID_Instituicao`) VALUES (?,?)");
					$stmt->bind_param("ss",$id_user, $idInst);
					if($stmt->execute()) {
						return 1; 
					}
					else {
						return 2;
					}
				}else{
					return 2; 
				}
			//}
		}
		
		function getInstituicao($id_user){
			
			// array for json response
			$response = array();
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT i.ID_Instituicao, i.nome, i.Descricao, i.Rua, i.Complemento, i.Bairro, i.Email, i.Logotipo, i.Website, i.ID_Cidade FROM `instituicao` i INNER JOIN `pessoa-instituicao` pi on i.ID_Instituicao = pi.ID_Instituicao and pi.ID_Pessoa = ?");
			$stmt->bind_param("s",$id_user);
			$stmt->execute();
			$stmt->bind_result($ID_Instituicao, $nome, $Descricao, $Rua, $Complemento, $Bairro, $Email, $Logotipo, $Website, $ID_Cidade);
			
			while($stmt->fetch()){
				//pushing fetched data in an array 
				$temp = [
				'ID_Instituicao'=>$ID_Instituicao,
				'nome'=>$nome,
				'Descricao'=>$Descricao,
				'Rua'=>$Rua,
				'Complemento'=>$Complemento,
				'Bairro'=>$Bairro,
				'Email'=>$Email,
				'Logotipo'=>$Logotipo,
				'Website'=>$Website,
				'ID_Cidade'=>$ID_Cidade
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		function getTodasInstituicoes($nome){
			
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT i.ID_Instituicao, i.nome FROM `instituicao` i WHERE i.nome like ?");
			$stmt->bind_param("s",$nome);
			$stmt->execute();
			$stmt->bind_result($ID_Instituicao, $nome);
			
			while($stmt->fetch()){
				//pushing fetched data in an array 
				$temp = [
				'ID_Instituicao'=>$ID_Instituicao,
				'nome'=>$nome
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		function getProjeto($id_user){
			
			// array for json response
			$response = array();
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT i.ID_Projeto, i.nome FROM `projeto` i INNER JOIN `pessoa-instituicao` pi on i.ID_Instituicao = pi.ID_Instituicao and pi.ID_Pessoa = ?");
			$stmt->bind_param("s",$id_user);
			$stmt->execute();
			$stmt->bind_result($ID_Projeto, $nome);
			
			while($stmt->fetch()){
				//pushing fetched data in an array 
				$temp = [
				'ID_Projeto'=>$ID_Projeto,
				'nome'=>$nome
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		
		function getEstado(){
			
			// array for json response
			$response = array();
			$response = array();
			 
			 
			// Mysql select query
			$stmt = $this->con->prepare("SELECT ID_Estado, Nome FROM `estado`");
			$stmt->execute();
			$stmt->bind_result($ID_Estado, $Nome);
			
			
			while($stmt->fetch()){
				//pushing fetched data in an array 
				$temp = [
				'ID_Estado'=>$ID_Estado,
				'Nome'=>$Nome
				];
				//echo json_encode( $temp);
				//echo "XXX";
				//echo json_encode($temp);
				//echo "yyy";
				//pushing the array inside the hero array 
				array_push($response, $temp);
				//echo "xxx";
				//print_r($temp);
			}
			
			// keeping response header to json
			header('Content-Type: application/json');
			 
			
			// echoing json result
			//echo "teste2";
			//echo json_encode($response);
			//echo "teste2";
			
			return $response;
			
			
		}
		
		public function createProjeto($nome,$descricao,$id_pessoa,$id_instituicao) {
			//if( $this->instituicaoExist($nome, $nomedeusuario,$email) ){
				//return 0; 
			//}else{
				
				$stmt = $this->con->prepare("
				INSERT INTO `projeto`(`Nome`, `Descricao`, `ID_Pessoa`, `ID_Instituicao`) VALUES ( ?, ?, ?, ?)");
				
				$stmt->bind_param("ssss",$nome, $descricao, $id_pessoa, $id_instituicao);
				
				if($stmt->execute()){	
					return 1; 
				}else{
					return 2; 
				}
			//}
		}
		
		public function createVagasProjeto($nome,$descricao,$quantidade,$requisito,$rua,$complemento,$bairro,$id_cidade, $id_projeto,$id_user) {
			//if( $this->instituicaoExist($nome, $nomedeusuario,$email) ){
				//return 0; 
			//}else{
				
				$stmt = $this->con->prepare("INSERT INTO `vaga`(`Nome`, `Descricao`, `Quantidade`, `Pre-Requisito`, `Rua`, `Complemento`, `Bairro`, `ID_Cidade`, `ID_Projeto`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				
				$stmt->bind_param("sssssssss",$nome,$descricao,$quantidade,$requisito,$rua,$complemento,$bairro,$id_cidade, $id_projeto);
				
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			//}
		}
		
		/*
		public function getInstituicao($estado){
			
			mysqli_set_charset( $this->con, 'utf8');
			$query = "SELECT c.Nome, c.ID_Cidade FROM `cidade` c INNER JOIN `estado` e on c.ID_Estado = e.ID_Estado WHERE e.Nome = '";
			$query .= $estado;
			$query .= "'";
			//echo $query;
			$result = mysqli_query($this->con, $query);
     
			$cidade = $result->fetch_all( MYSQLI_ASSOC );
			
			echo json_encode($cidade);
		}
		*/
}