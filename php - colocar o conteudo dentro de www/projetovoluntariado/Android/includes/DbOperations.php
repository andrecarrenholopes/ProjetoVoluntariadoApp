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

		private function createInstituicao($nome,$descricao,$rua,$numero,$complemento,$bairro,$email,$logotipo,$website,$id_cidade) {
			//if( $this->instituicaoExist($nome, $nomedeusuario,$email) ){
				//return 0; 
			//}else{
				
				$stmt = $this->con->prepare("INSERT INTO `instituicao`(`Nome`, `Descricao`, `Rua`, `Complemento`, `Bairro`, `Email`, `Logotipo`, `Website`, `ID_Cidade`) VALUES ([?,?,?,?,?,?,null,?,?)");
				
				$stmt->bind_param("sssss",$cpf, $nomecompleto, $email, $nomedeusuario, $password);
				
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			//}
		}
		
		function getInstituicao(){
			
			// array for json response
			$response = array();
			$response = array();
			 
			 
			// Mysql select query
			$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
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
}