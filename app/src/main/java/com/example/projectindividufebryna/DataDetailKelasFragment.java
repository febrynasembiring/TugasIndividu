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

import com.example.projectindividufebryna.databinding.FragmentDataDetailKelasBinding;
import com.example.projectindividufebryna.databinding.FragmentDataDetailKelasLayoutBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataDetailKelasFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener{
    private FragmentDataDetailKelasLayoutBinding fragmentDataDetailKelasBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentDataDetailKelasBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_detail_kelas_layout, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = fragmentDataDetailKelasBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // custom action bar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Detail Kelas");

        // penanganan List View
        fragmentDataDetailKelasBinding.listViewDetailKelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // membuka detail dari detail kelas
                Intent intent = new Intent(getActivity(), LihatDetailDariDetailKelas.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String id_kls_dtl = map.get(konfigurasi.TAG_JSON_DETAIL_ID_KLS).toString();
                intent.putExtra(konfigurasi.DTL_ID, id_kls_dtl);
                startActivity(intent);
            }
        });

        // penanganan FAB
        //   fragmentDataDetailKelasBinding.addFab.setOnClickListener(this);

        // ambil data dari JSON
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Ambil Data Detail Kelas", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_ALL_DETAIL);
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
                displayAllDataDetailKelas();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();

    }

    private void displayAllDataDetailKelas() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(konfigurasi.TAG_JSON_DETAIL_ID_KLS);
                String materis = object.getString(konfigurasi.TAG_JSON_DETAIL_NAMA_MATERI);
                String pesertas = object.getString(konfigurasi.TAG_DETAIL_ID_PST);
                String TanggalMulai=object.getString(konfigurasi.TAG_DETAIL_TGL_MULAI);
                String idkls=object.getString(konfigurasi.TAG_DETAIL_ID_KLS);
                String namains=object.getString(konfigurasi.TAG_DETAIL_NAMA_INS);


                HashMap<String, String> materi = new HashMap<>();
                materi.put(konfigurasi.TAG_JSON_DETAIL_ID_KLS, id);
                materi.put(konfigurasi.TAG_JSON_DETAIL_NAMA_MATERI, materis);
                materi.put(konfigurasi.TAG_DETAIL_ID_PST, pesertas);
                materi.put(konfigurasi.TAG_DETAIL_TGL_MULAI, TanggalMulai);
                materi.put(konfigurasi.TAG_DETAIL_ID_KLS, idkls);
                materi.put(konfigurasi.TAG_DETAIL_NAMA_INS, namains);


                list.add(materi);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.list_data_kelas_detail_layout,
                new String[]{konfigurasi.TAG_JSON_DETAIL_ID_KLS, konfigurasi.TAG_JSON_DETAIL_NAMA_MATERI, konfigurasi.TAG_DETAIL_ID_PST,
                        konfigurasi.TAG_DETAIL_TGL_MULAI, konfigurasi.TAG_DETAIL_ID_KLS, konfigurasi.TAG_DETAIL_NAMA_INS},
                new int[]{R.id.listview_ID_dtl_kls, R.id.listview_Nama_Materi_dtl, R.id.listview_jumlah_pst_dtl,R.id.listview_tglmulai, R.id.listviewid_kls, R.id.listview_namains}
        );
        fragmentDataDetailKelasBinding.listViewDetailKelas.setAdapter(adapter);
    }

    // @Override
    public void doBack() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // @Override
    public void onClick(View v) {
        // penanganan FAB
        //  startActivity(new Intent(view.getContext(), TambahDataMateriActivity.class));
    }


}
