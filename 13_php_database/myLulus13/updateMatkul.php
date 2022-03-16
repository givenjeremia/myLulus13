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
        $sql = "UPDATE `mahasiswa_ambil_mk` SET `semester`='$semester',`tahun_ambil`='$tahun_ambil',`nisbi`='$nisbi' WHERE `nrp`='$nrp' AND `kode_mk`='$kode_mk'";
		    $connect->query($sql);
            echo json_encode('Berhasil '.$sql);
        
    }
?>
