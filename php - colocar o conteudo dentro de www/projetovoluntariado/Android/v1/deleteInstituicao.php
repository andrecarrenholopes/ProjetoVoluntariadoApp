<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_instituicao']) ) { 
		$db = new DbOperations(); 
		if($db->deleteInstituicao( $_POST['id_instituicao'])) {
			$response['error'] = false; 
			$response['message'] = "Instituicao deletada";
		}
		else {
			$response['error'] = true;
			$response['message'] = "Erro ao deletar instituição";
		}
			
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);

