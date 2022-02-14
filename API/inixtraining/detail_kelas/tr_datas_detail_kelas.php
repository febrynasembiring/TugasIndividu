<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT cd.id_detail_kls, c.id_kls, s.nama_mat, i.nama_ins, c.tgl_mulai_kls, 
			COUNT(cd.id_pst)FROM tb_detail_kelas cd JOIN tb_peserta p ON cd.id_pst = p.id_pst JOIN tb_kelas c ON cd.id_kls = c.id_kls JOIN tb_materi s ON c.id_mat = s.id_mat 
		JOIN tb_instruktur i on i.id_ins=c.id_ins GROUP BY s.nama_mat ORDER BY cd.id_detail_kls ASC";
	

		//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_detail_kls"=>$row['id_detail_kls'],
			"nama_mat"=>$row['nama_mat'],
            		"COUNT(cd.id_pst)"=>$row['COUNT(cd.id_pst)'],
			"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"id_kls"=>$row['id_kls'],
			"nama_ins"=>$row['nama_ins']
				));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>