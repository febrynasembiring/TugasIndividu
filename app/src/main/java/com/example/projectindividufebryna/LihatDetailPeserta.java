package com.example.projectindividufebryna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LihatDetailPeserta extends AppCompatActivity  {
    EditText edit_id_pst, edit_nama_pst, edit_email_pst, edit_hp_pst, edit_ins_pst;
    String id;
    Button button_update_peserta;
    Button button_delete_peserta;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_peserta);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);

        edit_id_pst = findViewById(R.id.edit_id_pst);
        edit_nama_pst = findViewById(R.id.edit_nama_pst);
        edit_email_pst = findViewById(R.id.edit_email_pst);
        edit_hp_pst = findViewById(R.id.edit_hp_pst);
        edit_ins_pst = findViewById(R.id.edit_ins_pst);

        //menerima intent dari class
        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(konfigurasi.PST_ID);
        edit_id_pst.setText(id);

        //button
        button_update_peserta = findViewById(R.id.btn_update_peserta);
        button_delete_peserta = findViewById(R.id.btn_delete_peserta);

        button_update_peserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePeserta();
            }
        });
        button_delete_peserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LihatDetailPeserta.this);
                alertDialogBuilder.setMessage("Yakin Delete?");

                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePeserta();
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

    private void deletePeserta() {
        class DeletePeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailPeserta.this,
                        "Deleting Data...", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... params) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_DELETE_PESERTA, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
                Intent intent = new Intent(LihatDetailPeserta.this, MainActivity.class);
                intent.putExtra("KeyName", "peserta");
                startActivity(intent);
            }
        }
        DeletePeserta dp = new DeletePeserta();
        dp.execute();
    }

    private void updatePeserta() {
        final String nama_pst = edit_nama_pst.getText().toString().trim();
        final String email_pst = edit_email_pst.getText().toString().trim();
        final String hp_pst = edit_hp_pst.getText().toString().trim();
        final String ins_pst = edit_ins_pst.getText().toString().trim();

        class UpdatePeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailPeserta.this,
                        "Updating Data...", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_PST_ID, id);
                hashMap.put(konfigurasi.KEY_PST_NAMA, nama_pst);
                hashMap.put(konfigurasi.KEY_PST_EMAIL, email_pst);
                hashMap.put(konfigurasi.KEY_PST_HP, hp_pst);
                hashMap.put(konfigurasi.KEY_PST_INSTANSI, ins_pst);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(konfigurasi.URL_UPDATE_PESERTA, hashMap);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }

        UpdatePeserta up = new UpdatePeserta();
        up.execute();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LihatDetailPeserta.this);
        alertDialogBuilder.setMessage("Update lagi?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LihatDetailPeserta.this, MainActivity.class);
                        intent.putExtra("KeyName", "peserta");
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> { //inner class
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailPeserta.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_DETAIL_PESERTA, id);
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
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_pst = object.getString(konfigurasi.TAG_JSON_ID_PST);
            String nama_pst = object.getString(konfigurasi.TAG_JSON_NAMA_PST);
            String email_pst = object.getString(konfigurasi.TAG_JSON_EMAIL_PST);
            String hp_pst = object.getString(konfigurasi.TAG_JSON_HP_PST);
            String ins_pst = object.getString(konfigurasi.TAG_JSON_INSTANSI_PST);

            edit_id_pst.setText(id_pst);
            edit_nama_pst.setText(nama_pst);
            edit_email_pst.setText(email_pst);
            edit_hp_pst.setText(hp_pst);
            edit_ins_pst.setText(ins_pst);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}