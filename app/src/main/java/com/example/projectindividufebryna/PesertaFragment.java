package com.example.projectindividufebryna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import com.example.projectindividufebryna.databinding.FragmentPesertaLayoutBinding;

public class PesertaFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener {
    private FragmentPesertaLayoutBinding pesertaBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pesertaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_peserta_layout, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = pesertaBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // custom action bar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Peserta");

        // penanganan List View
        pesertaBinding.listviewpeserta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LihatDetailPeserta.class);
               HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
               String pstId = map.get(konfigurasi.TAG_JSON_ID_PST).toString();
               intent.putExtra(konfigurasi.PST_ID, pstId);
                startActivity(intent);
            }
        });

        // penanganan FAB
        pesertaBinding.addFab.setOnClickListener(this);

        // ambil data dari JSON
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Proses Ambil Data Peserta", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_ALL_PESERTA);
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
            JSONArray jsonArray = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_PST);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String pst_id = object.getString(konfigurasi.TAG_JSON_ID_PST);
                String nama_pst = object.getString(konfigurasi.TAG_JSON_NAMA_PST);

                HashMap<String, String> peserta = new HashMap<>();
                peserta.put(konfigurasi.TAG_JSON_ID_PST, pst_id);
                peserta.put(konfigurasi.TAG_JSON_NAMA_PST, nama_pst);

                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.list_peserta_layout,
                new String[]{konfigurasi.TAG_JSON_ID_PST, konfigurasi.TAG_JSON_NAMA_PST},
                new int[]{R.id.listview_ID_peserta, R.id.listview_Nama_peserta}
        );
        pesertaBinding.listviewpeserta.setAdapter(adapter);
    }

    @Override
    public void doBack() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, new MateriFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        // penanganan FAB
        startActivity(new Intent(view.getContext(), TambahDataPesertaActivity.class));
    }


}