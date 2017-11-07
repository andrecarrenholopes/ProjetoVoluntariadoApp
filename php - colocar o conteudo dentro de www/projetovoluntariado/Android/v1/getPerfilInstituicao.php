<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_instituicao']) ) { 
		$db = new DbOperations(); 
		$db->getPerfilInstituicao( $_POST['id_instituicao']);
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

//echo json_encode($response);

