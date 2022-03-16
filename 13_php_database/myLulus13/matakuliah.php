<?php
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost", "root", "", "kelulusan_ubaya");

if ($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}

//. . .
if (isset($_GET["nrp"])) {

	$nrp = $_GET["nrp"];
	// $sql = "SELECT mk.kode, mk.nama, mk.sks, (SELECT COUNT(1) FROM mahasiswa_ambil_mk WHERE mahasiswa_ambil_mk.nrp = '$nrp' AND mahasiswa_ambil_mk.kode_mk = mk.kode) AS diambil FROM mk";
	$sql = "SELECT mk.kode, mk.nama, mk.sks,
	(SELECT COUNT(1) FROM mahasiswa_ambil_mk WHERE mahasiswa_ambil_mk.nrp = '$nrp' AND mahasiswa_ambil_mk.kode_mk = mk.kode) AS diambil,
	(SELECT mahasiswa_ambil_mk.nisbi  FROM mahasiswa_ambil_mk WHERE mahasiswa_ambil_mk.nrp = '$nrp' AND mahasiswa_ambil_mk.kode_mk = mk.kode) AS nisbi,
	(SELECT mahasiswa_ambil_mk.semester FROM mahasiswa_ambil_mk WHERE mahasiswa_ambil_mk.nrp = '$nrp' AND mahasiswa_ambil_mk.kode_mk = mk.kode) AS semester,
	(SELECT mahasiswa_ambil_mk.tahun_ambil FROM mahasiswa_ambil_mk WHERE mahasiswa_ambil_mk.nrp = '$nrp' AND mahasiswa_ambil_mk.kode_mk = mk.kode) AS tahun_ambil
	FROM mk";
	
	$result = $c->query($sql);
	$array = array();
	if ($result->num_rows > 0) {
		while ($obj = $result->fetch_object()) {
			$array[] = $obj;
		}
		echo json_encode(array('result' => 'OK', 'data' => $array));
	} else {
		echo json_encode(array('result' => 'ERROR', 'message' => 'No data found'));
		die();
	}
}


?>

<!-- SELECT mk.kode, mk.nama, mk.sks, mak.nisbi , mak.semester , (SELECT COUNT(1) FROM mahasiswa_ambil_mk WHERE mahasiswa_ambil_mk.nrp = '160419118' AND mahasiswa_ambil_mk.kode_mk = mk.kode) AS diambil 
FROM mk inner join mahasiswa_ambil_mk as mak ON mk.kode = mak.kode_mk -->