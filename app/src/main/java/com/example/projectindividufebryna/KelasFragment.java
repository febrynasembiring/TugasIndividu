package com.example.projectindividufebryna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectindividufebryna.databinding.FragmentKelasLayoutBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KelasFragment  extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener {
    private FragmentKelasLayoutBinding kelasLayoutBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        kelasLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kelas_layout, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = kelasLayoutBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // custom action bar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Kelas");

        // penanganan List View
        kelasLayoutBinding.listviewkelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // membuka detail

                Intent intent = new Intent(getActivity(), LihatDetailKelas.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String kelasid = map.get(konfigurasi.TAG_JSON_ID_KLS).toString();
                intent.putExtra(konfigurasi.KLS_ID, kelasid);
                startActivity(intent);

            }
        });

        // penanganan FAB
        //pegawaiBinding.addFab.setOnClickListener(this);

        // ambil data dari JSON
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Load Data Kelas", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_ALL_KELAS);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                // Toast.makeText(view.getContext(), JSON_STRING, Toast.LENGTH_LONG).show();

                // menampilkan data json kedalam list view
                displayAllDataPeserta();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displayAllDataPeserta() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_KLS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(konfigurasi.TAG_JSON_ID_KLS);
                String namamateri = object.getString(konfigurasi.TAG_JSON_MAT_KLS_ID);
                String namains = object.getString(konfigurasi.TAG_JSON_INS_KLS_ID);


                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(konfigurasi.TAG_JSON_ID_KLS, id);
                kelas.put(konfigurasi.TAG_JSON_MAT_KLS_ID, namamateri);
                kelas.put(konfigurasi.TAG_JSON_INS_KLS_ID, namains);


                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.list_kelas_layout,
                new String[]{konfigurasi.TAG_JSON_ID_KLS, konfigurasi.TAG_JSON_MAT_KLS_ID, konfigurasi.TAG_JSON_INS_KLS_ID},
                new int[]{R.id.listview_ID_kelas, R.id.listview_mat, R.id.listview_ins}
        );
        kelasLayoutBinding.listviewkelas.setAdapter(adapter);
    }

    @Override
    public void doBack() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        // penanganan FAB
        startActivity(new Intent(view.getContext(), TambahKelasActivity.class));
    }
}