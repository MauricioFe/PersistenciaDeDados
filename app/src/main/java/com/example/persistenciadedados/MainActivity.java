package com.example.persistenciadedados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
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
                    carregarDoSdCard(true);
                    break;
                case R.id.rbtExterna_publica:
                    carregarDoSdCard(false);
                    break;
            }
        } else {
            switch (tipo) {
                case R.id.rbtInterna:
                    salvarInterno();
                    break;
                case R.id.rbtExterna_privado:
                    salvarNoSdCard(true);
                    break;
                case R.id.rbtExterna_publica:
                    salvarNoSdCard(false);
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
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File meuDir = getExternalDir(privado);
            try {
                if (!meuDir.exists()) {
                    meuDir.mkdir();
                }
                File arquivoTxt = new File(meuDir, "arquivo.txt");
                if (!arquivoTxt.exists()) {
                    arquivoTxt.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(arquivoTxt);
                salvar(fos);
            } catch (IOException e) {
                Log.d("NGVL", "Erro ao salvar arquivo", e);
            }
        } else {
            Log.e("NGVL", "Não é possível escrever no SD Card");
        }
    }


    private File getExternalDir(boolean privado) {
        if (privado) {
            return getExternalFilesDir(null);
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    private void carregarDoSdCard(boolean privado) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            File meuDir = getExternalDir(privado);
            if (meuDir.exists()) {
                File arquivoTxt = new File(meuDir, "arquivo.txt");
                if (arquivoTxt.exists()) {
                    try {
                        arquivoTxt.createNewFile();
                        FileInputStream fis = new FileInputStream(arquivoTxt);
                        carregar(fis);
                    } catch (IOException e) {
                        Log.d("NGLV", "Erro ao carregar arquivo", e);
                    }
                }
            }
        }
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
        Toast.makeText(this, "Arquivo salvo com sucesso", Toast.LENGTH_LONG).show();
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


