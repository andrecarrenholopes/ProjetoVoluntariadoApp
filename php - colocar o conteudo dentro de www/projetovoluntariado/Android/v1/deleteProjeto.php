<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_projeto']) ) { 
		$db = new DbOperations(); 
		if($db->deleteProjeto( $_POST['id_projeto'])) {
			$response['error'] = false; 
			$response['message'] = "Projeto deletado";
		}
		else {
			$response['error'] = true;
			$response['message'] = "Erro ao deletar inscricao em vaga";
		}
			
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);

