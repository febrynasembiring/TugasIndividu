package com.example.projectindividufebryna;


public class konfigurasi {

    public static final String URL_GET_ALL_PESERTA = "http://192.168.0.103/inixtraining/peserta/tr_datas_peserta.php";
    public static final String URL_GET_DETAIL_PESERTA = "http://192.168.0.103/inixtraining/peserta/tr_detail_peserta.php?id_pst=";
    public static final String URL_ADD_PESERTA = "http://192.168.0.103/inixtraining/peserta/tr_add_peserta.php";
    public static final String URL_UPDATE_PESERTA = "http://192.168.0.103/inixtraining/peserta/tr_update_peserta.php";
    public static final String URL_DELETE_PESERTA = "http://192.168.0.103/inixtraining/peserta/tr_delete_peserta.php?id_pst=";

    // key and value JSON yang muncul di browser
    public static final String KEY_PST_ID = "id_pst";
    public static final String KEY_PST_NAMA = "nama_pst";
    public static final String KEY_PST_EMAIL = "email_pst";
    public static final String KEY_PST_HP = "hp_pst";
    public static final String KEY_PST_INSTANSI = "instansi_pst";

    // flag JSON
    public static final String TAG_JSON_ARRAY_PST = "result";
    public static final String TAG_JSON_ID_PST = "id_pst";
    public static final String TAG_JSON_NAMA_PST = "nama_pst";
    public static final String TAG_JSON_EMAIL_PST= "email_pst";
    public static final String TAG_JSON_HP_PST= "hp_pst";
    public static final String TAG_JSON_INSTANSI_PST ="instansi_pst";

    // variabel ID Peserta
    public static final String PST_ID = "peserta_id";

    public static final String URL_GET_ALL_MATERI =
            "http://192.168.0.103/inixtraining/materi/tr_datas_materi.php";
    public static final String URL_GET_DETAIL_MATERI =
            "http://192.168.0.103/inixtraining/materi/tr_detail_materi.php?id_mat=";
    public static final String URL_ADD_MATERI =
            "http://192.168.0.103/inixtraining/materi/tr_add_materi.php";
    public static final String URL_UPDATE_MATERI =
            "http://192.168.0.103/inixtraining/materi/tr_update_materi.php";
    public static final String URL_DELETE_MATERI =
            "http://192.168.0.103/inixtraining/materi/tr_delete_materi.php?id_mat";

    // key and value JSON yang muncul di browser
    public static final String KEY_MAT_ID = "id_mat";
    public static final String KEY_MAT_NAMA = "nama_mat";

    // flag JSON
    public static final String TAG_JSON_ARRAY_MAT = "result_mat";
    public static final String TAG_JSON_ID_MAT = "id_mat";
    public static final String TAG_JSON_NAMA_MAT = "nama_mat";


    // variabel ID Peserta
    public static final String MAT_ID = "id_mat";


    public static final String URL_GET_ALL_INSTRUKTUR= "http://192.168.0.103/inixtraining/instruktur/tr_datas_instruktur.php";
    public static final String URL_GET_DETAIL_INSTRUKTUR =
            "http://192.168.0.103/inixtraining/instruktur/tr_detail_instruktur.php?id_ins=";
    public static final String URL_ADD_INSTRUKTUR =
            "http://192.168.0.103/inixtraining/instruktur/tr_add_instruktur.php";
    public static final String URL_UPDATE_INSTRUKTUR =
            "http://192.168.0.103/inixtraining/instruktur/tr_update_instruktur.php";
    public static final String URL_DELETE_INSTRUKTUR =
            "http://192.168.0.103/inixtraining/instruktur/tr_delete_instruktur.php?id_ins=";

    // key and value JSON yang muncul di browser
    public static final String KEY_INS_ID = "id_ins";
    public static final String KEY_INS_NAMA = "nama_ins";
    public static final String KEY_INS_EMAIL = "email_ins";
    public static final String KEY_INS_HP = "hp_ins";


    // flag JSON
    public static final String TAG_JSON_INS_ID = "id_ins";
    public static final String TAG_JSON_INS_NAMA = "nama_ins";
    public static final String TAG_JSON_INS_EMAIL = "email_ins";
    public static final String TAG_JSON_INS_HP = "hp_ins";
    public static final String TAG_JSON_ARRAY_INS = "result_ins";

    // variabel ID Peserta
    public static final String INS_ID = "id_ins";


    public static final String URL_GET_ALL_KELAS= "http://192.168.0.103/inixtraining/kelas/tr_datas_kelas.php";
    public static final String URL_GET_DETAIL_KELAS =
            "http://192.168.0.103/inixtraining/kelas/tr_detail_kelas.php?id_kls=";
    public static final String URL_ADD_KELAS =
            "http://192.168.0.103/inixtraining/kelas/tr_add_kelas.php";
    public static final String URL_UPDATE_KELAS=
            "http://192.168.0.103/inixtraining/kelas/tr_update_kelas.php";
    public static final String URL_DELETE_KELAS =
            "http://192.168.0.103/inixtraining/kelas/tr_delete_kelas.php?id_kls=";

    // key and value JSON yang muncul di browser
    public static final String KEY_KLS_ID = "id_kls";
    public static final String KEY_TGL_AKHIR_KLS = "tgl_akhir_kls";
    public static final String KEY_TGL_MULAI_KLS = "tgl_mulai_kls";
    public static final String KEY_INS_KLS= "id_ins";
    public static final String KEY_MAT_KLS= "id_mat";

    // flag JSON
    public static final String TAG_JSON_ARRAY_KLS = "result_kls";
    public static final String TAG_JSON_ID_KLS = "id_kls";
    public static final String TAG_JSON_TGL_MULAI_KLS = "tgl_mulai_kls";
    public static final String TAG_JSON_TGL_AKHIR_KLS= "tgl_akhir_kls";
    public static final String TAG_JSON_INS_KLS_ID = "id_ins";
    public static final String TAG_JSON_MAT_KLS_ID= "id_mat";


    // variabel ID kelas
    public static final String KLS_ID = "kelas_id";


    // untuk data detail kelas
    public static final String URL_GET_ALL_DETAIL = "http://192.168.0.103/inixtraining/detail_kelas/tr_datas_detail_kelas.php";
    public static final String URL_GET_DETAIL_DETAIL = "http://192.168.0.103/inixtraining/detail_kelas/tr_detail_detail_kelas.php?id_detail_kls=";
    public static final String URL_ADD_DETAIL = "http://192.168.0.103/inixtraining/detail_kelas/tr_add_detail_kelas.php";
    public static final String URL_UPDATE_DETAIL = "http://192.168.0.103/inixtraining/detail_kelas/tr_update_detail_kelas.php";
    public static final String URL_DELETE_DETAIL = "http://192.168.0.103/inixtraining/detail_kelas/tr_delete_detail_kelas.php?id_detail_kls=";

  //  public static final String URL_GET_DETAIL_DETAIL_ID = "http://192.168.0.103/inixindo/detail_kelas/tr_detail_detail_kelas.php?id_detail_kls=";
    // key and value JSON yang muncul di browser
    public static final String KEY_DETAIL_ID = "id_detail_kls";
    public static final String KEY_DETAIL_NAMA_MATERI ="s.nama_mat";
    public static final String KEY_DETAIL_ID_PST= "COUNT(cd.id_pst)";


    // flag JSON
    public static final String TAG_JSON_ARRAY_DTL = "result";
    public static final String TAG_JSON_DETAIL_ID_KLS = "id_detail_kls";
    public static final String TAG_JSON_DETAIL_NAMA_MATERI= "nama_mat";
    public static final String TAG_DETAIL_TGL_MULAI= "tgl_mulai_kls";
    public static final String TAG_DETAIL_ID_KLS= "id_kls";
    public static final String TAG_DETAIL_ID_PST= "COUNT(cd.id_pst)";
    public static final String TAG_DETAIL_NAMA_INS= "nama_ins";


    public static final String TAG_JSON_ARRAY = "result";

    // variabel ID Peserta
    public static final String DTL_ID = "id_detail_kls";


    public static final String TAG_JSON_DETAIL_DARI_DETAIL_KLS_ID="id_kls";



    public static final String ID_DETAIL_DR_DETAIL_KLS="id_kls";
    public static final String TAG_JSON_ID_KELAS_UTK_DTL="id_kls";
    public static final String TAG_JSON_DETAIL_DARI_DETAIL_ID_KELAS="id_detail_kls";
    public static final String TAG_JSON_DETAIL_DARI_DETAIL_NAMA_PST="nama_pst";


   // public static final String URL_GET_DETAIL_DETAIL_ID = "http://192.168.0.103/inixindo/detail_kelas/tr_detail_detail_kelas.php?id_detail_kls=";
}
