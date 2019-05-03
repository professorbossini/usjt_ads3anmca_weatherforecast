package br.com.bossini.usjt_ads3anmca_weatherforecast;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;
    private List<Weather> previsoes;
    private EditText locationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        previsoes = new ArrayList<>();
        adapter = new WeatherAdapter(previsoes, this);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade = locationEditText.getEditableText().toString();
                String endereco =
                        getString(
                                R.string.web_service_url,
                                cidade,
                                getString(
                                        R.string.api_key
                                )
                        );
                obtemPrevisoesV3(endereco);
            }
        });
    }

    public void obtemPrevisoesV1(String endereco) {
        try {
            URL url = new URL(endereco);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String linha = null;
            StringBuilder resultado = new StringBuilder("");
            while ((linha = reader.readLine()) != null) {
                resultado.append(linha);
            }
            String json = resultado.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void obtemPrevisoesV2(String endereco) {
        new Thread(() -> {
            try {
                URL url = new URL(endereco);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String linha = null;
                StringBuilder resultado = new StringBuilder("");
                while ((linha = reader.readLine()) != null) {
                    resultado.append(linha);
                }
                String json = resultado.toString();
                Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void obtemPrevisoesV3(String endereco) {
        new Thread(() -> {
            try {
                URL url = new URL(endereco);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String linha = null;
                StringBuilder resultado = new StringBuilder("");
                while ((linha = reader.readLine()) != null) {
                    resultado.append(linha);
                }
                String json = resultado.toString();
                runOnUiThread(() -> {
                    Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}