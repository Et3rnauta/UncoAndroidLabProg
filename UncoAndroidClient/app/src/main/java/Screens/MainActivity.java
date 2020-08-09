package Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uncoandroidclient.R;

import Conexion.ClientConnector;
import Conexion.ConnectionRequestHandler;
import TestConexion.TestConexionActivity;

public class MainActivity extends AppCompatActivity {

    EditText InputIp;
    Button Connect, Config, Ranking, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputIp = (EditText) findViewById(R.id.ptxt_main_ip);
        Connect = (Button) findViewById(R.id.btn_main_connect);
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectButton().execute();
            }
        });
        Config = (Button) findViewById(R.id.btn_main_config);
        Config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnConfig(view);
            }
        });
        Ranking = (Button) findViewById(R.id.btn_main_ranking);
        Ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRanking(view);
            }
        });
        Exit = (Button) findViewById(R.id.btn_main_exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExit(view);
            }
        });
    }

    private void btnExit(View view) {
    }

    private void btnRanking(View view) {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }

    private void btnConfig(View view) {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }
}