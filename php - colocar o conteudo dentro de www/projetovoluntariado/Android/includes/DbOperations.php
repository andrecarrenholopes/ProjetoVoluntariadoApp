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
			$stmt = $this->con->prepare("SELECT * FROM `pessoa` WHERE NomeDeUsuario = ?");
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

	}