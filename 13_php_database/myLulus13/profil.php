<?php
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost", "root", "", "kelulusan_ubaya");

	if($c->connect_errno) {
		echo json_encode(array('result'=> 'ERROR', 'message' => 'Failed to connect DB'));
        die();
    }
    // Ambil parameter
    if(isset($_GET["nrp"])){
        $nrp= $_GET["nrp"];
        $sql = "SELECT * FROM `mahasiswa` WHERE nrp =$nrp";
        $result = $c->query($sql);
        $array = array();
        if ($result->num_rows > 0) {
            if($obj = $result -> fetch_object()) {
                $array[] = $obj;
            }
            echo json_encode(array('result' => 'OK', 'data' => $array));
        } else {
            echo json_encode(array('result'=> 'ERROR', 'message' => 'No data found'));
            die();
        }
    
        
    }
