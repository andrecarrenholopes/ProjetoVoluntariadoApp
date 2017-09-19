<?php 

	class GetEndereco{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();
			


		}

		/*CRUD -> C -> CREATE */

		public function getEstado(){
			
			mysqli_set_charset( $this->con, 'utf8');
			// Mysql select query
			$result = mysqli_query($this->con, "SELECT ID_Estado, Nome FROM `estado`");
     
			$estados = $result->fetch_all( MYSQLI_ASSOC );
			
			echo json_encode($estados);
		
		}
		

		public function getCidade($estado){
			
			mysqli_set_charset( $this->con, 'utf8');
			$query = "SELECT c.Nome, c.ID_Cidade FROM `cidade` c INNER JOIN `estado` e on c.ID_Estado = e.ID_Estado WHERE e.Nome = '";
			$query .= $estado;
			$query .= "'";
			//echo $query;
			$result = mysqli_query($this->con, $query);
     
			$cidade = $result->fetch_all( MYSQLI_ASSOC );
			
			echo json_encode($cidade);	
		}
		
		
}