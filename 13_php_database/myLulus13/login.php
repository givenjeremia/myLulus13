<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","root","","kelulusan_ubaya");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }
    if(isset($_POST["nrp"]) && isset($_POST["pin"])){
        $nrp = $_POST["nrp"];
        $pin = $_POST["pin"];
        $sql = "SELECT * FROM mahasiswa WHERE nrp = $nrp AND pin=$pin";
		$result = $connect->query($sql);
	        $array = array();

        if ($result->num_rows > 0) {
            echo json_encode('Berhasil');
        } else {
            echo json_encode('Gagal');
            die();
        }
    }
?>
