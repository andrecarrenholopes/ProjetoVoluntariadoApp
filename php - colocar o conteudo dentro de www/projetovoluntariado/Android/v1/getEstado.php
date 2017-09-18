<?php 

require_once '../includes/GetEndereco.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='GET'){
	
	$db = new GetEndereco(); 

	$db->getEstado();
}

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['estado']) ) { 
		$db = new GetEndereco(); 
		$db->getCidade( $_POST['estado']);
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}
