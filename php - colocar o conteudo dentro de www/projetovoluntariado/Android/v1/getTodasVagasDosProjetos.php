<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(	isset($_POST['nome']) and
		isset($_POST['pagina']) ) { 
		$db = new DbOperations(); 
		$db->getTodasVagasDosProjetos( "%{$_POST['nome']}%", $_POST['pagina']);
		
	}
	else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

//echo json_encode($response);

