<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_vaga']) ) { 
		$db = new DbOperations(); 
		if($db->deleteVaga( $_POST['id_vaga'])) {
			$response['error'] = false; 
			$response['message'] = "Inscricao em vaga deletada";
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

