<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","root","","kelulusan_ubaya");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }
    if(isset($_POST["nrp"]) && isset($_POST["kode_mk"]) && isset($_POST["tahun_ambil"]) && isset($_POST["semester"]) && isset($_POST['nisbi'])  ){
        $kode_mk= $_POST["kode_mk"];
        $nrp = $_POST["nrp"];
        $tahun_ambil = $_POST["tahun_ambil"];
        $semester = $_POST["semester"];
        $nisbi = $_POST['nisbi'];

        //CekNRP Apakah Ada?
        $sql = "INSERT INTO `mahasiswa_ambil_mk`(`nrp`, `kode_mk`, `semester`, `tahun_ambil`, `nisbi`) VALUES ('$nrp', '$kode_mk', '$semester', '$tahun_ambil', '$nisbi')";
		    $connect->query($sql);
            echo json_encode('Berhasil '.$sql);
        
    }
?>
