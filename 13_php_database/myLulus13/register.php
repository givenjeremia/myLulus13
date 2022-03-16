<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","root","","kelulusan_ubaya");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }
    if(isset($_POST["nrp"]) && isset($_POST["pin"]) && isset($_POST["nama"]) && isset($_POST["tahun_masuk"])  ){
        $nrp= $_POST["nrp"];
        $pin = $_POST["pin"];
        $nama = $_POST["nama"];
        $tahun_masuk = (int)$_POST["tahun_masuk"];
        //CekNRP Apakah Ada?
        $sqlCekNRP = "SELECT * FROM mahasiswa WHERE nrp = $nrp";
		$result = $connect->query($sqlCekNRP);
        if ($result->num_rows > 0) {
            echo json_encode('Gagal');
            die();
        } else {
            $sql = "INSERT INTO `mahasiswa`(`nrp`, `pin`, `nama`, `angkatan`) VALUES ('$nrp','$pin','$nama','$tahun_masuk')";
		    $connect->query($sql);
            echo json_encode('Berhasil');
        }
        
    }
?>
