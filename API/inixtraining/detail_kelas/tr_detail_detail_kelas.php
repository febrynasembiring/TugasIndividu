<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id_detail_kls = $_GET['id_detail_kls'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM tb_detail_kelas cd JOIN tb_peserta p ON cd.id_pst = p.id_pst JOIN tb_kelas c ON cd.id_kls = c.id_kls JOIN 
	tb_materi s ON c.id_mat = s.id_mat JOIN tb_instruktur i on i.id_ins=c.id_ins GROUP BY c.id_kls";
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();

	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_detail_kls"=>$row['id_detail_kls'],
			"id_pst"=>$row['id_pst'],
                        "nama_pst"=>$row['nama_pst'],
			"id_mat"=>$row['id_mat'],
			"nama_mat"=>$row['nama_mat'],
			"id_kls"=>$row['id_kls']

		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>