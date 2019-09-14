package com.example.manejoarchivos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
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

    private static String carnetAporte [] = new String[1000];
    private static int aporte[] = new int[1000];
    private static String cat[] = new String[1000];

    private EditText eaporte;
    private EditText etotal;

    private static int vuelta2 = 0;

    private static String datoEnviar = "";

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

        eaporte = (EditText)findViewById(R.id.aporte);
        etotal = (EditText)findViewById(R.id.total);

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

            File pt2 = new File(dir.getAbsolutePath() + File.separator + "bdAportes.txt");
            BufferedReader lee2 = new BufferedReader(new FileReader(pt2));
            String res2 = "", linea2;
            while ((linea2=lee2.readLine())!=null) {
                if (vuelta2 == 0) linea2=lee2.readLine();
                res2 = res2 + linea2+ ";\n";
                extraer2(linea2+";");
            }

            mostrar.setText(res2);

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
    public static void extraer2(String cadena) {
        String palabra = "", voca;
        int pos = 0;
        for (int i=1; i<=cadena.length(); i++) {
            voca = cadena.substring(i-1, i);
            if (voca.compareTo(";")==0) {
                switch(pos) {
                    case 0:
                        carnetAporte[vuelta2] = palabra;
                        break;
                    case 1:
                        aporte[vuelta2] = Integer.parseInt(palabra);
                        break;
                    case 2:
                        cat[vuelta2] = palabra;
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
        vuelta2++;
    }

    public void buscar(View view) {
        int c = 0;
        int suma = 0;
        String carnetBuscar = ecarnet.getText().toString();
        for (int i = 0; i < vuelta - 1; i++) {
            if (carnet[i].equals(carnetBuscar)) {
                epaterno.setText(paterno[i]);
                ematerno.setText(materno[i]);
                enombres.setText(nombres[i]);
                etelefono.setText(telefono[i]);
            }
        }
        for (int i = 0; i < vuelta2 - 1; i++) {
            if (carnetAporte[i].equals(carnetBuscar)) {
                datoEnviar = datoEnviar + cat[i] + "                    " + aporte[i] + "\n";
                suma = suma + aporte[i];
                c ++;
            }
        }
        datoEnviar = datoEnviar + "\nTOTAL" +  "          "  + suma + "\n";
        eaporte.setText(c + "");
        etotal.setText(suma + "");
    }

    public void aportes(View view) {
        Intent vd = new Intent(this, activity_aporte.class);
        vd.putExtra("dato", datoEnviar);
        startActivity(vd);
    }

    public void inicializar(View view) {
        ecarnet.setText("");
        epaterno.setText("");
        ematerno.setText("");
        enombres.setText("");
        etelefono.setText("");
        eaporte.setText("");
        etotal.setText("");
    }

    public void busquedas(View view) {
        Intent vd = new Intent(this, activity_personal.class);
        // vd.putExtra("totalhamburguesas", hamburguesas);
        startActivity(vd);

    }

    public void finalizar(View view) {
        finish();
    }
}
