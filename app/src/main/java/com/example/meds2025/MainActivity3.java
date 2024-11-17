package com.example.meds2025;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;

public class MainActivity3 extends AppCompatActivity {

    Button buttonAdd;
    Button buttonRemove;

    TextView campoNome;
    TextView campoDose;
    TextView campoHoje;
    TextView campoCaixa;

    Conexao conexao = new Conexao(this);

    public String fixingString(String nome){
        String[] arrayNome = nome.split(" ",0);
        String nomeJoin = String.join(" ", arrayNome);
        return nomeJoin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        buttonAdd = findViewById(R.id.button);
        buttonRemove = findViewById(R.id.button3);
        campoNome = findViewById(R.id.editTextTextPersonName);
        campoDose = findViewById(R.id.editTextTextPersonName2);
        campoHoje = findViewById(R.id.editTextTextPersonName5);
        campoCaixa = findViewById(R.id.novoIdText1);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                nome = fixingString(nome);
                float dose = Float.parseFloat(campoDose.getText().toString());
                float hoje = Float.parseFloat(campoHoje.getText().toString());
                float quantCaixa = Float.parseFloat(campoCaixa.getText().toString());

                adicionarRemedio(nome, dose, hoje, quantCaixa);
                campoNome.setText("");
                campoDose.setText("");
                campoHoje.setText("");
                campoCaixa.setText("");
            }
        });
        /*buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDelete = ;
                nomeDelete = fixingString(nomeDelete);
                excluirRemedio(nomeDelete);
            }
        });
        */
    }
    public void adicionarRemedio(String nome, float quantDia, float quantHoje, float quantCaixa){
        /// Area Banco de Dados ///
        SQLiteDatabase db = conexao.getWritableDatabase();

        //////////////////////////////////////////////////////////////
        MainActivity2 mainActivity2 = new MainActivity2();
        float diasAtuais = mainActivity2.calcularAtualNovo();
        float numeroDez = quantHoje + diasAtuais * quantDia;
        if(quantCaixa != 0){
            while(numeroDez > quantCaixa){
                numeroDez -= quantCaixa;
            }
        }

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("quantdia", quantDia);
        values.put("quanthj", quantHoje);
        values.put("quantcaixa", quantCaixa);
        values.put("quantdez", numeroDez);


        db.insert("remedios", null, values); // Insere o registro
        db.close(); // Fecha o banco de dados

        Toast.makeText(MainActivity3.this, "Ação de Adicionar Realizada", Toast.LENGTH_LONG).show();
    }
    /*
    public void excluirRemedio(String nome) {

        SQLiteDatabase db = conexao.getReadableDatabase();
        int id = 100000;
        String query = "SELECT * FROM remedios WHERE nome = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nome});
        cursor.close();
        cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ // Verifica se há algum resultado
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close(); // Fecha o cursor
        /////
        db.delete("remedios", "id = ?", new String[]{String.valueOf(id)});
        db.close();

    }*/
}