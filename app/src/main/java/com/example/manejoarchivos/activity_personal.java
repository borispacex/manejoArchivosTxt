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

public class activity_personal extends AppCompatActivity {

    private static String carnet [] = new String[1000];
    private static String paterno[] = new String[1000];
    private static String materno[] = new String[1000];
    private static String nombres[] = new String[1000];
    private static String telefono[] = new String[1000];

    private TextView tmostrar;
    private EditText etexto;
    private EditText eentrada;

    private static int vuelta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        tmostrar = (TextView) findViewById(R.id.mostrar);
        etexto = (EditText) findViewById(R.id.texto);
        eentrada = (EditText) findViewById(R.id.input);

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
                if (vuelta == 0) linea=lee.readLine();
                res = res + linea+ ";\n";
                extraer(linea+";");
            }
            tmostrar.setText(res);
        } catch (Exception e) {  }

    }

    public void buscar(View view) {
        String textoMostrar = "";
        String palabraBuscar = eentrada.getText().toString().toUpperCase();
        for (int i = 0; i < vuelta - 1; i++) {
            String nombreBuscar = nombres[i] + " ";
            String nombresBuscar[] = nombres[i].split(" ");
            if (paterno[i].equals(palabraBuscar)) {
                textoMostrar = textoMostrar + paterno[i] + " " + materno[i] + " " + nombres[i] + " " + telefono[i] + " " + carnet[i] + "\n";
            } else if (materno[i].equals(palabraBuscar)) {
                textoMostrar = textoMostrar + paterno[i] + " " + materno[i] +" "+ nombres[i] + " " + telefono[i] + " " + carnet[i] + "\n";
            } else if (nombres[i].equals(palabraBuscar)) {
                textoMostrar = textoMostrar + paterno[i] + " " + materno[i] + " " + nombres[i] + " " + telefono[i] + " " + carnet[i] + "\n";
            } else if (nombresBuscar.length > 1 && (nombresBuscar[0].equals(palabraBuscar) || nombresBuscar[1].equals(palabraBuscar))) {
                textoMostrar = textoMostrar + paterno[i] + " " + materno[i] +" "+ nombres[i] + " " + telefono[i] + " " + carnet[i] + "\n";
            }
        }
        etexto.setText(textoMostrar);
    }

    public void retornar(View view) {
        finish();
    }

    public void nuevaBusqueda(View view) {
        etexto.setText("");
        eentrada.setText("");
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
}
