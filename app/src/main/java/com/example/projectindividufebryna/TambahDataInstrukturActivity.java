package com.example.projectindividufebryna;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class TambahDataInstrukturActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_nama_ins, edit_email_ins, edit_hp_ins;
    Button btn_tambah_ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data_instruktur);
//        getSupportActionBar().setTitle("Tambah Instruktur");
   //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_email_ins = findViewById(R.id.edit_email_ins);
        edit_hp_ins = findViewById(R.id.edit_hp_ins);
        btn_tambah_ins = findViewById(R.id.btn_tambah_ins);

        btn_tambah_ins.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        tambahInstruktur();
        onBackPressed();
    }

    private void tambahInstruktur() {
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String email_ins = edit_email_ins.getText().toString().trim();
        final String hp_ins = edit_hp_ins.getText().toString().trim();

        class TambahInstructor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahDataInstrukturActivity.this,
                        "Menambah Data Instruktur...", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_INS_NAMA, nama_ins);
                hashMap.put(konfigurasi.KEY_INS_EMAIL, email_ins);
                hashMap.put(konfigurasi.KEY_INS_HP, hp_ins);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(konfigurasi.URL_ADD_INSTRUKTUR, hashMap);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
            }
        }
        TambahInstructor tn = new TambahInstructor();
        tn.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        onBackPressed();
        return super.onCreateOptionsMenu(menu);
    }
}