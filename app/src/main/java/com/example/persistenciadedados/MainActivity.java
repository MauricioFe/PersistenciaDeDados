package com.example.persistenciadedados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputTexto;
    TextView txtTexto;
    RadioGroup rgTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTexto = findViewById(R.id.edtText);
        txtTexto = findViewById(R.id.txtTexto);
        rgTipo = findViewById(R.id.RadioGroup);

        findViewById(R.id.btnSalvar).setOnClickListener(this);
        findViewById(R.id.btnLer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean ler = false;
        if (v.getId() == R.id.btnLer)
            ler = true;
        int tipo = rgTipo.getCheckedRadioButtonId();
        if (ler) {
            switch (tipo) {
                case R.id.rbtInterna:
                    carregarInterno();
                    break;
                case R.id.rbtExterna_privado:
                    break;
                case R.id.rbtExterna_publica:
                    break;
            }
        } else {
            switch (tipo) {
                case R.id.rbtInterna:
                    break;
                case R.id.rbtExterna_privado:
                    break;
                case R.id.rbtExterna_publica:
                    break;
            }
        }
    }

    private void salvarInterno() {
        try {
            FileOutputStream fos = openFileOutput("arquivo.txt", Context.MODE_PRIVATE);
            salvar(fos);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("NGVL", "salvarInterno: Erro ao salvar o arquivo", e);
        }
    }

    private void carregarInterno() {
        try {
            FileInputStream fis = openFileInput("arquivo.txt");
            carregar(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarNoSdCard(boolean privado) {
    }

    private void carregarDoSdCard(boolean privado) {
    }

    private void salvar(FileOutputStream fos) throws IOException {
        String[] linhas = TextUtils.split(
                inputTexto.getText().toString(), "\n"
        );
        PrintWriter writter = new PrintWriter(fos);
        for (String linha : linhas) {
            writter.println(linha);
        }
        writter.flush();
        writter.close();
        fos.close();
    }

    private void carregar(FileInputStream fis) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        StringBuilder sb = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (sb.length() != 0)
                sb.append("\n");
            sb.append(linha);
        }
        reader.close();
        fis.close();
        txtTexto.setText(sb.toString());
    }
}
