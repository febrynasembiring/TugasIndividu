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

import com.example.projectindividufebryna.databinding.FragmentMateriLayoutBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MateriFragment extends  Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener {
    private FragmentMateriLayoutBinding materiLayoutBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        materiLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_materi_layout, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = materiLayoutBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // custom action bar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Materi");

        // penanganan List View
        materiLayoutBinding.listviewmateri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // membuka detail
                Intent intent = new Intent(getActivity(), LihatDetailMateri.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String matId = map.get(konfigurasi.TAG_JSON_ID_MAT).toString();
                intent.putExtra(konfigurasi.MAT_ID, matId);
                startActivity(intent);

            }
        });

        // penanganan FAB
        materiLayoutBinding.addFab.setOnClickListener(this);

        // ambil data dari JSON
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Ambil Data Materi", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_ALL_MATERI);
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
                displayAllDataMateri();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displayAllDataMateri() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_MAT);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(konfigurasi.TAG_JSON_ID_MAT);
                String name = object.getString(konfigurasi.TAG_JSON_NAMA_MAT);


                HashMap<String, String> materi = new HashMap<>();
                materi.put(konfigurasi.TAG_JSON_ID_MAT, id);
                materi.put(konfigurasi.TAG_JSON_NAMA_MAT, name);


                list.add(materi);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.list_materi_layout,
                new String[]{konfigurasi.TAG_JSON_ID_MAT, konfigurasi.TAG_JSON_NAMA_MAT},
                new int[]{R.id.listview_ID_materi, R.id.listview_Nama_Materi}
        );
        materiLayoutBinding.listviewmateri.setAdapter(adapter);
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
        startActivity(new Intent(view.getContext(), TambahDataMateriActivity.class));
    }
}
