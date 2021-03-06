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

import com.example.projectindividufebryna.databinding.FragmentInstrukturLayoutBinding;


public class InstrukturFragment  extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener {
    private FragmentInstrukturLayoutBinding instrukturBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        instrukturBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_instruktur_layout, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = instrukturBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // custom action bar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Instruktur");

        // penanganan List View
        instrukturBinding.listviewinstruktur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LihatDetailInstruktur.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String insId = map.get(konfigurasi.TAG_JSON_INS_ID).toString();
                intent.putExtra(konfigurasi.INS_ID, insId);
                startActivity(intent);
            }
        });

        // penanganan FAB
      instrukturBinding.addFab.setOnClickListener(this);

        // ambil data dari JSON
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Ambil Data Instruktur", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(konfigurasi.URL_GET_ALL_INSTRUKTUR);
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
                displayAllDataInstruktur();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displayAllDataInstruktur() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY_INS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String ins_id = object.getString(konfigurasi.TAG_JSON_INS_ID);
                String nama_ins = object.getString(konfigurasi.TAG_JSON_INS_NAMA);

                HashMap<String, String> instruktur = new HashMap<>();
                instruktur.put(konfigurasi.TAG_JSON_INS_ID, ins_id);
                instruktur.put(konfigurasi.TAG_JSON_INS_NAMA, nama_ins);

                list.add(instruktur);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.list_instruktur_layout,
                new String[]{konfigurasi.TAG_JSON_INS_ID, konfigurasi.TAG_JSON_INS_NAMA},
                new int[]{R.id.listview_ID_instruktur, R.id.listview_Nama_instruktur}
        );
        instrukturBinding.listviewinstruktur.setAdapter(adapter);
    }

    @Override
    public void doBack() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, new InstrukturFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        // penanganan FAB
        startActivity(new Intent(view.getContext(), TambahDataInstrukturActivity.class));
    }
}