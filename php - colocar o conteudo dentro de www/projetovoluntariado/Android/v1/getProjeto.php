<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['id_user']) ) { 
		$db = new DbOperations(); 
		$db->getProjeto( $_POST['id_user']);
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

//echo json_encode($response);

