package com.example.bd4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    static final String FILE_NAME = "inf.txt";
    static BufferedWriter bw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean f = this.ExistBase(FILE_NAME);
        if(!f){
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Создается файл" + FILE_NAME).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("Log_2","Создание файла" + FILE_NAME);
                }
            });
            AlertDialog ad = b.create();
            ad.show();

            File file = new File(super.getFilesDir(),FILE_NAME);
            try{
                file.createNewFile();
                Log.d("Log_2","Файл" + FILE_NAME + "создан");
            }
            catch (IOException ex){
                Log.d("Log_2","Файл" + FILE_NAME + " не создан");
            }
        }
    }

    private boolean ExistBase(String fname) {
        boolean rc = false;
        File f = new File(super.getFilesDir(), fname);
        if (rc = f.exists()) {
            Log.i("Lod_2", "Файл" + fname + "существует");
        } else Log.i("Lod_2", "Файл" + fname + "не найден");
        return rc;
    }

    private void WriteLine(String name, String surname)
    {
        String s = surname + " " + name + "\r\n";
        try
        {
            bw.write(s);
            bw.close();
            Log.d("Log_2","Done!");
        }
        catch(IOException ex)
        {
            Log.d("Log_2", ex.getMessage());
        }
    }
    public void WriteFile(View view){
        File f = new File(getFilesDir(), FILE_NAME);
        if(f.exists()) {
            try {
                PrintWriter writer = new PrintWriter(f);
                writer.print("");
                writer.close();
            }
            catch (IOException e)
            {}
        }
        try {
            FileWriter fw = new FileWriter(f, true);
            bw = new BufferedWriter(fw);
            EditText n = (EditText)findViewById(R.id.editTextTextMultiLine2);
            String name = n.getText().toString();
            EditText sn = (EditText)findViewById(R.id.editTextTextMultiLine3);
            String surname = sn.getText().toString();
            this.WriteLine(name,surname);
        }
        catch (IOException ex){
            Log.d("Log_2", ex.getMessage());
        }
    }

    public void ReadFile(View view){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));
            EditText output = findViewById(R.id.editTextTextMultiLine);
            output.setText("");
            String s = "";
            String str = "";
            while((str = br.readLine())!= null){
                s+= str.toString();
            }
            output.setText(s);
        }
        catch (IOException ex){
            Log.d("Log_2", ex.getMessage());
        }
    }

}
