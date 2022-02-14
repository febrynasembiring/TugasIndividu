package com.example.projectindividufebryna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatDetailDariDetailKelas extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String id_kls;
    private ListView list_view;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_detail_dari_detail_kelas_layout);
        list_view = findViewById(R.id.listview_detail_dari_detail_kelas);
        list_view.setOnItemClickListener(this);

        //bikin tombol delete

        Intent receiveIntent = getIntent();
        id_kls = receiveIntent.getStringExtra(konfigurasi.DTL_ID);

        //method untuk ambil data JSON
        getJSON();
    }

//    private void deleteKelas() {
//        class DeleteKelas extends AsyncTask<Void, Void, String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(LihatDetailDariDetailKelas.this,
//                        "Deleting Data...", "Harap menunggu...",
//                        false, false);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                HttpHandler handler = new HttpHandler();
//                String result = handler.sendGetResponse(konfigurasi.URL_DELETE_DETAIL_DARI_DETAIL_KLS, id_kls);
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(String message) {
//                super.onPostExecute(message);
//                loading.dismiss();
//                Intent intent = new Intent(LihatDetailDariDetailKelas.this, MainActivity.class);
//                intent.putExtra("KeyName", "detail kelas");
//                startActivity(intent);
//            }
//        }
//        DeleteKelas dk = new DeleteKelas();
//        dk.execute();
//    }

    private void getJSON() {

        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailDariDetailKelas.this,
                        "Mengambil Data Detail dari Detail kelas", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_DETAIL_DETAIL, id_kls);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON", message);

                //fungsi nampilin lv data
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON", JSON_STRING);

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(konfigurasi.TAG_JSON_ID_KELAS_UTK_DTL);
                String id_detail_kls = object.getString(konfigurasi.TAG_JSON_DETAIL_DARI_DETAIL_ID_KELAS);
                String list_nama_pst = object.getString(konfigurasi.TAG_JSON_DETAIL_DARI_DETAIL_NAMA_PST);

                HashMap<String, String> detaildaridetailkelas = new HashMap<>();
                detaildaridetailkelas.put(konfigurasi.TAG_JSON_ID_KELAS_UTK_DTL, id_kls);
                detaildaridetailkelas.put(konfigurasi.TAG_JSON_DETAIL_DARI_DETAIL_ID_KELAS, id_detail_kls);
                detaildaridetailkelas.put(konfigurasi.TAG_JSON_DETAIL_DARI_DETAIL_NAMA_PST, list_nama_pst);

//                ini biar ubah data jsn ke arrar
                list.add(detaildaridetailkelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list,
                R.layout.list_detail_dari_detail_kelas_layout,
                new String[]{konfigurasi.TAG_JSON_ID_KELAS_UTK_DTL, konfigurasi.TAG_JSON_DETAIL_DARI_DETAIL_ID_KELAS, konfigurasi.TAG_JSON_DETAIL_DARI_DETAIL_NAMA_PST},
                new int[]{R.id.txv_id_kls, R.id.txv_mulai_kls, R.id.txv_akhir_kls}
        );
        list_view.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //click detail dari (detail dari detail)--
    }
}