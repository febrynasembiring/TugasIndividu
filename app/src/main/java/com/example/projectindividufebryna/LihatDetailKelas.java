package com.example.projectindividufebryna;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class LihatDetailKelas extends AppCompatActivity {

    EditText edit_id_kls, edit_tgl_mulai_kelas, edit_tgl_akhir_kelas, edit_nama_ins, edit_nama_mat;
    String id;
    Button button_update_kelas;
    Button button_delete_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas);

        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_tgl_mulai_kelas = findViewById(R.id.edit_tgl_mulai_kelas);
        edit_tgl_akhir_kelas = findViewById(R.id.edit_tgl_akhir_kls);
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_nama_mat = findViewById(R.id.edit_nama_mat);

        //get intent dari class
        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(konfigurasi.KLS_ID);
        edit_id_kls.setText(id);

        //button
        button_update_kelas = findViewById(R.id.btn_update_kls);
        button_delete_kelas = findViewById(R.id.btn_delete_kls);

        button_update_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateKelas();
            }
        });
        button_delete_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LihatDetailKelas.this);
                alertDialogBuilder.setMessage("Yakin Delete?");

                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteKelas();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //mengambil data JSON
        getJSON();
    }

    private void deleteKelas() {
        class DeletePeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailKelas.this,
                        "Deleting Data...", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... params) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_DELETE_KELAS, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
                Intent intent = new Intent(LihatDetailKelas.this, MainActivity.class);
                intent.putExtra("KeyName", "kelas");
                startActivity(intent);
            }
        }
        DeletePeserta dp = new DeletePeserta();
        dp.execute();
    }
//
//    private void updatePeserta() {
//        final String nama_pst = edit_nama_pst.getText().toString().trim();
//        final String email_pst = edit_email_pst.getText().toString().trim();
//        final String hp_pst = edit_hp_pst.getText().toString().trim();
//        final String ins_pst = edit_ins_pst.getText().toString().trim();
//
//        class UpdatePeserta extends AsyncTask<Void, Void, String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(LihatDetailPeserta.this,
//                        "Updating Data...", "Harap menunggu...",
//                        false, false);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put(Konfigurasi.KEY_PST_ID, id);
//                hashMap.put(Konfigurasi.KEY_PST_NAMA, nama_pst);
//                hashMap.put(Konfigurasi.KEY_PST_EMAIL, email_pst);
//                hashMap.put(Konfigurasi.KEY_PST_HP, hp_pst);
//                hashMap.put(Konfigurasi.KEY_PST_INSTANSI, ins_pst);
//
//                HttpHandler handler = new HttpHandler();
//                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_PST, hashMap);
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(String message) {
//                super.onPostExecute(message);
//                loading.dismiss();
//                displayDetailData(message);
//            }
//        }
//
//        UpdatePeserta up = new UpdatePeserta();
//        up.execute();
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LihatDetailPeserta.this);
//        alertDialogBuilder.setMessage("Update lagi?");
//
//        alertDialogBuilder.setPositiveButton("Ya",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//
//        alertDialogBuilder.setNegativeButton("Tidak",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //startActivity(new Intent(LihatDetailInstruktur.this, InstrukturFragment.class));
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> { //inner class
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailKelas.this,
                        "Mengambil Data Kelas", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_DETAIL_KELAS, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_KLS);
            JSONObject object = result.getJSONObject(0);

            String id_kls = object.getString(konfigurasi.TAG_JSON_ID_KLS);
            String mulai = object.getString(konfigurasi.TAG_JSON_TGL_MULAI_KLS);
            String akhir = object.getString(konfigurasi.TAG_JSON_TGL_AKHIR_KLS);
            String n_ins = object.getString(konfigurasi.TAG_JSON_INS_KLS_ID);
            String n_mat = object.getString(konfigurasi.TAG_JSON_MAT_KLS_ID);

            edit_id_kls.setText(id_kls);
            edit_tgl_mulai_kelas.setText(mulai);
            edit_tgl_akhir_kelas.setText(akhir);
            edit_nama_ins.setText(n_ins);
            edit_nama_mat.setText(n_mat);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}