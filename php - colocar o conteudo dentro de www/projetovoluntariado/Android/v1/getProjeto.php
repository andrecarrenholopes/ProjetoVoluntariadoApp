<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_user']) AND isset($_POST['id_instituicao']) ) { 
		$db = new DbOperations(); 
		$db->getProjeto( $_POST['id_user'],$_POST['id_instituicao']);
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

//echo json_encode($response);

