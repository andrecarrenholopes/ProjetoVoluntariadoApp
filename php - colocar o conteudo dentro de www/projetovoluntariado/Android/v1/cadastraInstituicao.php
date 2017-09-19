
<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['nome']) and 
		isset($_POST['descricao']) and
		isset($_POST['rua']) and
		isset($_POST['complemento']) and
		isset($_POST['bairro']) and
		isset($_POST['email']) and
		isset($_POST['website']) and
		isset($_POST['id_cidade']) and
		isset($_POST['id_user']) )
		{
		//operate the data further 
		$db = new DbOperations(); 
		$result = $db->createInstituicao( 	
									$_POST['nome'],
									$_POST['descricao'],
									$_POST['rua'],
									$_POST['complemento'],
									$_POST['bairro'],
									$_POST['email'],
									$_POST['website'],
									$_POST['id_cidade'],
									$_POST['id_user']
								);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Instituição registrada com sucesso";
		}elseif($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again";			
		}elseif($result == 0){
			$response['error'] = true; 
			$response['message'] = "It seems you are already registered, please choose a different email and username";						
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
