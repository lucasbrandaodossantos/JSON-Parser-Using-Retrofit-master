package com.example.lucas.jsonreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);

        arrayList = new ArrayList();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        /*CRIAÇÃO DO OBJETO RETROFIT, RESPONSÁVEL POR MONTAR A URI E CONSTRUIR O JSON QUANDO FOR CHAMADO*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DataMainInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*CRIAÇÃO DE INTERFACE PARA UTILIZAÇÃO DO OBJETO RETROFIT CRIADO ACIMA PARA CHAMAR A URI*/
        DataMainInterface dataMainInterface = retrofit.create(DataMainInterface.class);

        /*CRIAÇÃO DO JSON OBJECT ATRAVÉS DA INTERFACE CRIADA ACIMA*/
        Call<JsonArray> call = dataMainInterface.loadAllData3();

        /*REALIZANDO O CALL DO OBJETO SOLICITADO*/
        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(!response.isSuccessful()){

                    /*CASO TENHA CONEXÃO MAS NÃO TENHA NENHUMA RESPOSTA OBTIDA*/
                    Toast.makeText(MainActivity.this,"ERROR: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    /*CRIAÇÃO DO JSONOBJECT UTILIZANDO A BIBLIOTECA GSON PARA OBTER O CORPO DA RESPONSE*/
                    JsonArray jsonarray = response.body();

                    /*CRIAÇÃO DO ARRAY QUE CONTÉM OS DADOS FINAIS DESEJADOS, LOCALIZADO PELA TAG "data"*/
                    JsonArray jsonArray = jsonarray.getAsJsonArray();

                    /*LOOP PARA PERCORRER O ARRAY CRIADO E EXIBIR NA LISTVIEW EM TEMPO DE EXECUÇÃO*/
                    for (int i = 0; i < jsonArray.size(); i++){
                        DataList d = new DataList();

                        JsonObject js_data = (JsonObject) jsonArray.get(i);

                        d.listaid = js_data.get("listaid").toString();
                        d.nomesolenidade = js_data.get("nomesolenidade").toString();
                        d.ra = js_data.get("ra").toString();
                        d.nomealuno =js_data.get("nomealuno").toString();
                        d.curso =js_data.get("curso").toString();
                        d.rg =js_data.get("rg").toString();
                        d.procurado =js_data.get("procurado").toString();
                        d.status =js_data.get("status").toString();


                        arrayList.add ("ID: " + d.listaid
                                + "\nNOME SOLENIDADE: "+d.nomesolenidade
                                + "\nRA: "+d.ra
                                + "\nNOME DO ALUNO:"+d.nomealuno
                                + "\nCURSO: "+d.curso
                                + "\nRG: "+d.rg
                                + "\nPROCURADO" +d.procurado
                                + "\nSTATUS: "+d.status
                        );
                        lv.setAdapter(adapter);
                    }
                }
            }

            /*CASO HAJA FALHAS NA TENTATIVA DE CALL*/
            @Override
            public void onFailure(Call<JsonArray>call, Throwable t) {
                Toast.makeText(MainActivity.this,"FAILURE ON RESPONSE: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}



