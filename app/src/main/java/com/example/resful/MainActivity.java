package com.example.resful;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void limpiar (View view){
        TextView resul = findViewById(R.id.txtrespuesta);;
        resul.setText("");
    }
    public void login (View view){
        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService(" https://jsonplaceholder.typicode.com/users"
                ,datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");

    }
    @Override
    public void processFinish(String result) throws JSONException {
        TextView Resultado = (TextView) findViewById(R.id.txtrespuesta);
        String listausu = "";
        JSONArray JSONlista = new JSONArray(result);
        for (int i = 0; i < JSONlista.length(); i++) {
            JSONObject usu = JSONlista.getJSONObject(i);
            String nombre = usu.getString("name");
            String direccion = usu.getString("email");
            String telefono = usu.getString("phone");
            listausu = listausu
                    + " (" + i +") " + " " + nombre + "," +direccion+","+telefono+"\n";
            final int finalI = i;
            final String finalLstUser = listausu;
            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    Resultado.setText(finalLstUser);

                    if (finalI < JSONlista.length() - 1) {
                        Resultado.append("\n");
                    }
                }
            }, (i + 1) * 500);
        }
    }
}