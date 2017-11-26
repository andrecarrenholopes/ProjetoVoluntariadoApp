<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_projeto']) ) { 
		$db = new DbOperations(); 
		$db->getPerfilProjeto( $_POST['id_projeto']);
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

//echo json_encode($response);

