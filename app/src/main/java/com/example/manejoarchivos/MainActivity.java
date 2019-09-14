package com.example.manejoarchivos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    private static String carnet [] = new String[1000];
    private static String paterno[] = new String[1000];
    private static String materno[] = new String[1000];
    private static String nombres[] = new String[1000];
    private static String telefono[] = new String[1000];

    private TextView mostrar;
    private EditText ecarnet;
    private EditText epaterno;
    private EditText ematerno;
    private EditText enombres;
    private EditText etelefono;


    private static int vuelta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrar = (TextView)findViewById(R.id.mostrar);

        ecarnet = (EditText)findViewById(R.id.carnet);
        epaterno = (EditText)findViewById(R.id.paterno);
        ematerno = (EditText)findViewById(R.id.materno);
        enombres = (EditText)findViewById(R.id.nombres);
        etelefono = (EditText)findViewById(R.id.telefono);


        String estado = Environment.getExternalStorageState();

        if (!estado.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "No hay SD Card!", Toast.LENGTH_SHORT).show();
            finish();
        }
        try {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            File dir = Environment.getExternalStorageDirectory();
            File pt = new File(dir.getAbsolutePath() + File.separator + "bdPersonal.txt");
            BufferedReader lee = new BufferedReader(new FileReader(pt));
            String res = "", linea;
            while ((linea=lee.readLine())!=null) {
                res = res + linea+ ";\n";
                extraer(linea+";");
            }
            mostrar.setText(res);

        } catch (Exception e) {  }

    }
    public static void extraer(String cadena) {
        String palabra = "", voca;
        int pos = 0;
        for (int i=1; i<=cadena.length(); i++) {
            voca = cadena.substring(i-1, i);
            if (voca.compareTo(";")==0) {
                switch(pos) {
                    case 0:
                        paterno[vuelta] = palabra;
                        break;
                    case 1:
                        materno[vuelta] = palabra;
                        break;
                    case 2:
                        nombres[vuelta] = palabra;
                        break;
                    case 3:
                        telefono[vuelta] = palabra;
                        break;
                    case 4:
                        carnet[vuelta] = palabra;
                        break;
                    default:
                        System.out.println("error dando valor a vector");
                }
                palabra = "";
                pos++;
            }else{
                palabra = palabra + voca;
            }
        }
        vuelta++;
    }

    public void buscar(View view) {
        for (int i = 0; i < vuelta - 1; i++) {
            if (carnet[i].equals(ecarnet.getText().toString())) {
                epaterno.setText(paterno[i]);
                ematerno.setText(materno[i]);
                enombres.setText(nombres[i]);
                etelefono.setText(telefono[i]);
            }
        }
    }

    public void aportes(View view) {

    }

    public void inicializar(View view) {

    }

    public void busquedas(View view) {

    }

    public void finalizar(View view) {

    }
}
