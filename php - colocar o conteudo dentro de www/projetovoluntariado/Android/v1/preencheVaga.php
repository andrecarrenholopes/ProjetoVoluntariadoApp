
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['id_user']) and 
			isset($_POST['id_vaga']))
		{
		//operate the data further 

		$db = new DbOperations(); 
		if($db->getQuantidadeVagas($_POST['id_vaga']) > 0) {
			$result = $db->preencheVaga( 	$_POST['id_user'],
										$_POST['id_vaga']
									);
			if($result == 1){
				$response['error'] = false; 
				$response['message'] = "User registered successfully";
			}elseif($result == 2){
				$response['error'] = true; 
				$response['message'] = "Some error occurred please try again";			
			}elseif($result == 0){
				$response['error'] = true; 
				$response['message'] = "It seems you are already registered, please choose a different email and username";						
			}
		}
		else {
			$response['error'] = true; 
			$response['message'] = "Nao ha vagas suficientes";						
		}
	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
