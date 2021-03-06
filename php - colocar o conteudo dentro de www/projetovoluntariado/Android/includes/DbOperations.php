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
				$stmt = $this->con->prepare("INSERT INTO `pessoa`(`Nome Completo`, `DataNascimento`, `Email`, `Papel`, `NomeDeUsuario`, `Senha`, `ID_Cidade`,`cpfDeVerdade`) VALUES (?,null,?,2,?,?,null,?)");
				
				$stmt->bind_param("sssss", $nomecompleto, $email, $nomedeusuario, $password,$cpf);
				
				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
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
		
		public function preencheVaga($id_user,$id_vaga) {
			//if( $this->instituicaoExist($nome, $nomedeusuario,$email) ){
				//return 0; 
			//}else{
			$stmt = $this->con->prepare("INSERT INTO `vaga-pessoa`(`ID_Pessoa`, `ID_Vaga`) VALUES (?, ?)");
			
			$stmt->bind_param("ss",$id_user, $id_vaga);
			
			if($stmt->execute()){	
				return 1; 
			}else{
				return 2; 
			}
			
		}
		
		/*CRUD -> R -> READ */
		
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
			$stmt = $this->con->prepare(" SELECT CPF, DataNascimento, Email, if(Nome IS NULL, 'sem cidade', Nome) AS ID_Cidade, `Nome Completo`, NomeDeUsuario, Papel, cpfDeVerdade FROM `pessoa` p 
				LEFT JOIN cidade c on p.ID_Cidade = c.ID_Cidade
				WHERE NomeDeUsuario = ?");

			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		
		private function isUserExist($cpf, $nomedeusuario, $email){
			$stmt = $this->con->prepare("SELECT cpf FROM `pessoa` WHERE NomeDeUsuario = ? OR email = ? OR cpfDeVerdade = ? ");
			$stmt->bind_param("sss", $nomedeusuario, $email, $cpf);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
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
		
		function getPerfilInstituicao($id_instituicao){
			
			// array for json response
			$response = array();
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("
			SELECT `ID_Instituicao`, i.`Nome`, `Descricao`, `Rua`, `Complemento`, `Bairro`, `Email`, `Logotipo`, `Website`, c.`Nome` as nomeCidade FROM `instituicao` i INNER JOIN cidade c on c.`ID_Cidade` = i.`ID_Cidade` WHERE `ID_Instituicao` = ?");
			$stmt->bind_param("s",$id_instituicao);
			$stmt->execute();
			$stmt->bind_result($ID_Instituicao, $nome, $Descricao, $Rua, $Complemento, $Bairro, $Email, $Logotipo, $Website, $nomeCidade);
			
			while($stmt->fetch()){
				$nomeCidade = mb_convert_encoding($nomeCidade, "UTF-8", "Windows-1252");
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
				'nomeCidade'=>$nomeCidade
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		function getPerfilProjeto($id_projeto){
			
			// array for json response
			$response = array();
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT p.`ID_Projeto`, p.`Nome`, p.`Descricao`, p.`ID_Instituicao`, i.`Nome` FROM `projeto` p INNER JOIN instituicao i on i.`ID_Instituicao` = p.`ID_Instituicao` WHERE `ID_Projeto` = ?");
			$stmt->bind_param("s",$id_projeto);
			$stmt->execute();
			$stmt->bind_result($ID_Projeto, $nome, $Descricao, $ID_Instituicao, $NomeInstituicao);
			
			while($stmt->fetch()){
				//$nomeCidade = mb_convert_encoding($nomeCidade, "UTF-8", "Windows-1252");
				//pushing fetched data in an array 
				$temp = [
				'ID_Projeto'=>$ID_Projeto,
				'nome'=>$nome,
				'Descricao'=>$Descricao,
				'ID_Instituicao'=>$ID_Instituicao,
				'NomeDaInstituicao'=>$NomeInstituicao
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		function getProjeto($id_user, $id_instituicao){
			
			// array for json response
			$response = array();
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT i.ID_Projeto, i.nome, i.`Descricao` FROM `projeto` i INNER JOIN `pessoa-instituicao` pi on i.ID_Instituicao = pi.ID_Instituicao and pi.ID_Pessoa = ? and pi.ID_Instituicao = ?");
			$stmt->bind_param("ss",$id_user, $id_instituicao);
			$stmt->execute();
			$stmt->bind_result($ID_Projeto, $nome, $descricao);
			
			while($stmt->fetch()){
				//pushing fetched data in an array 
				$temp = [
				'ID_Projeto'=>$ID_Projeto,
				'nome'=>$nome,
				'Descricao'=>$descricao
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		function getVaga($id_vaga){
			
			// array for json response
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT p.`Nome` as nomeProjeto , v.`ID_Vaga`, v.`Nome` as nomeVaga, v.`Descricao`, v.`Pre-Requisito`, v.`Quantidade` - (SELECT count(*) FROM `vaga-pessoa` WHERE `ID_Vaga` = ?) as `Quantidade`, v.`Rua`, v.`Complemento`, v.`Bairro`, c.`Nome` as nomeCidade FROM `vaga` v INNER JOIN `projeto` p on p.`ID_Projeto` = v.`ID_Projeto` INNER JOIN `cidade` c on c.`ID_Cidade` = v.`ID_Cidade` WHERE `ID_Vaga` = ?");
			$stmt->bind_param("ss",$id_vaga,$id_vaga);
			$stmt->execute();
			$stmt->bind_result($nomeProjeto, $ID_Vaga, $nomeVaga, $Descricao, $Requisito, $Quantidade, $Rua, $Complemento, $Bairro, $nomeCidade);

			
			while($stmt->fetch()){
				
				$nomeCidade = mb_convert_encoding($nomeCidade, "UTF-8", "Windows-1252");
				//pushing fetched data in an array 
				$temp = [
				'nomeProjeto'=>$nomeProjeto,
				'nomeVaga'=>$nomeVaga,
				'ID_Vaga'=>$ID_Vaga,
				'Descricao'=>$Descricao,
				'Pre-Requisito'=>$Requisito,
				'Quantidade'=>$Quantidade,
				'Rua'=>$Rua,
				'Complemento'=>$Complemento,
				'Bairro'=>$Bairro,
				'nomeCidade'=>$nomeCidade
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			
			// keeping response header to json
			header('Content-Type: application/json');
			
			// echoing json result
			echo json_encode($response);
			
		}
		
		function getVagaByProjeto($id_projeto){
			
			// array for json response
			$response = array();
			 
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("
				SELECT p.`Nome` as nomeProjeto , v.`ID_Vaga`, v.`Nome` as nomeVaga, v.`Descricao`, v.`Pre-Requisito`, v.`Quantidade` - (SELECT count(*) FROM `vaga-pessoa` WHERE `ID_Vaga` = v.`ID_Vaga`) as `Quantidade`, v.`Rua`, v.`Complemento`, v.`Bairro`, c.`Nome` as nomeCidade
				FROM `vaga` v 
				INNER JOIN `projeto` p on p.`ID_Projeto` = v.`ID_Projeto`
				INNER JOIN `cidade` c on c.`ID_Cidade` = v.`ID_Cidade`
				WHERE  p.`ID_Projeto` = ?");
			$stmt->bind_param("s",$id_projeto);
			$stmt->execute();
			$stmt->bind_result($nomeProjeto, $ID_Vaga, $nomeVaga, $Descricao, $Requisito, $Quantidade, $Rua, $Complemento, $Bairro, $nomeCidade);

			
			while($stmt->fetch()){
				
				$nomeCidade = mb_convert_encoding($nomeCidade, "UTF-8", "Windows-1252");
				//pushing fetched data in an array 
				$temp = [
				'nomeProjeto'=>$nomeProjeto,
				'nomeVaga'=>$nomeVaga,
				'ID_Vaga'=>$ID_Vaga,
				'Descricao'=>$Descricao,
				'Pre-Requisito'=>$Requisito,
				'Quantidade'=>$Quantidade,
				'Rua'=>$Rua,
				'Complemento'=>$Complemento,
				'Bairro'=>$Bairro,
				'nomeCidade'=>$nomeCidade
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
		
		function getTodosProjetos($nome){
			
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT `ID_Projeto`, `Nome` FROM `projeto` WHERE `Nome` like ?");
			$stmt->bind_param("s",$nome);
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
		
		function getTodasVagasDosProjetos($nome, $pagina){
			
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("SELECT `ID_Vaga`, `Nome` FROM `vaga` WHERE `Nome` like ? limit 20 OFFSET ?");
			$stmt->bind_param("ss",$nome, $pagina);
			$stmt->execute();
			$stmt->bind_result($ID_Vaga, $nome);
			
			while($stmt->fetch()){
				//pushing fetched data in an array 
				$temp = [
				'ID_Vaga'=>$ID_Vaga,
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
		
		function getInscricoesEmVagas($id_user) {
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("			
				SELECT v.`ID_Projeto`, v.`ID_Vaga`, v.`Nome`, v.`Descricao`, v.`Pre-Requisito`, v.`Quantidade` - (SELECT count(*) FROM `vaga-pessoa`  WHERE `ID_Vaga` = v.`ID_Vaga`) as `Quantidade`, v.`ADistancia`, v.`Rua`, v.`Numero`, v.`Complemento`, v.`Bairro`, v.`Pontual`, c.`Nome` as `nomeCidade`, p.`ID_Instituicao`
				FROM `vaga` v
				INNER JOIN `vaga-pessoa` vp ON v.`ID_Vaga` = vp.`ID_Vaga`
				INNER JOIN `cidade` c ON c.`ID_Cidade` = v.`ID_Cidade`
				INNER JOIN `projeto` p ON v.`ID_Projeto` = p.`ID_Projeto`
				WHERE vp.`ID_Pessoa` = ? ");
			$stmt->bind_param("s",$id_user);
			$stmt->execute();
			$stmt->bind_result($ID_Projeto, $ID_Vaga, $Nome, $Descricao, $PreRequisito, $Quantidade, $ADistancia, $Rua, $Numero, $Complemento, $Bairro, $Pontual, $nomeCidade, $ID_Instituicao);
			
			while($stmt->fetch()){
				$nomeCidade = mb_convert_encoding($nomeCidade, "UTF-8", "Windows-1252");
				//pushing fetched data in an array 
				$temp = [
				'ID_Projeto'=>$ID_Projeto, 
				'ID_Vaga'=>$ID_Vaga,
				'Nome'=>$Nome,
				'Descricao'=>$Descricao, 
				'Pre-Requisito'=>$PreRequisito, 
				'Quantidade'=>$Quantidade, 
				'ADistancia'=>$ADistancia, 
				'Rua'=>$Rua, 
				'Numero'=>$Numero, 
				'Complemento'=>$Complemento, 
				'Bairro'=>$Bairro, 
				'Pontual'=>$Pontual, 
				'nomeCidade'=>$nomeCidade,
				'ID_Instituicao'=>$ID_Instituicao
				];
				//pushing the array inside the hero array 
				array_push($response, $temp);
			}
			 
			// keeping response header to json
			header('Content-Type: application/json');
			 
			// echoing json result
			echo json_encode($response);
		}
		
		function getQuantidadeVagas($id_vaga) {
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("			
				SELECT DISTINCT (v.`ID_Vaga`) as idVaga, v.`Quantidade` - (SELECT count(*) FROM `vaga-pessoa` vp WHERE vp.`ID_Vaga` = idVaga) as vagasRestantes 
				FROM `vaga` v
				WHERE v.`ID_Vaga` = ? ");
			$stmt->bind_param("s",$id_vaga);
			$stmt->execute();
			
			$stmt->bind_result($ID_Vaga, $vagasRestantes);
			while($stmt->fetch()){
				if($vagasRestantes > 0) {
					return $vagasRestantes;
				}
				else{
					return 0;
				}					
			}
			return 0;
		}
		
		/*CRUD -> U -> UPDATE */
		
		/*CRUD -> D -> DELETE */
		
		function deleteCandidaturaEmVaga($id_user, $id_vaga) {
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("			
				DELETE FROM `vaga-pessoa` WHERE `ID_Pessoa` = ? AND `ID_Vaga` = ? ");
			$stmt->bind_param("ss",$id_user,$id_vaga);
			if($stmt->execute() && ($stmt->affected_rows >= 1)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		function deleteVaga($id_vaga) {
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("			
				DELETE FROM `vaga` WHERE `ID_Vaga` = ? ");
			$stmt->bind_param("s",$id_vaga);
			
			if($stmt->execute() && ($stmt->affected_rows >= 1)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		function deleteProjeto($id_projeto) {
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("			
				DELETE FROM `projeto` WHERE `ID_Projeto` = ? ");
			$stmt->bind_param("s",$id_projeto);
			
			if($stmt->execute() && ($stmt->affected_rows >= 1)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		function deleteInstituicao($id_instituicao) {
			// array for json response
			$response = array();
			
			// Mysql select query
			//$stmt = $this->con->prepare("SELECT ID_Instituicao, nome FROM `instituicao`");
			$stmt = $this->con->prepare("			
				DELETE FROM `instituicao` WHERE `ID_Instituicao` = ? ");
			$stmt->bind_param("s",$id_instituicao);
			
			if($stmt->execute() && ($stmt->affected_rows >= 1)) {
				return true;
			}
			else {
				return false;
			}
		}
}