package Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uncoandroidclient.R;

import Conexion.ClientConnector;
import Conexion.ConnectionRequestHandler;
import Logic.GameState;

public class MainActivity extends AppCompatActivity {

    EditText InputIp;
    Button Connect, Config, Ranking, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameState.resetGameObject();
        InputIp = (EditText) findViewById(R.id.ptxt_main_ip);
        Connect = (Button) findViewById(R.id.btn_main_connect);
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectButton().execute(InputIp.getText().toString());
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

    class ConnectButton extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            if (ipEsValido(strings[0])) {
                if (GameState.getGameObject().connect(strings[0])) {
                    startWaitingActivity();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Error al conectar");
                            Toast.makeText(getApplicationContext(), "La Direccion IP no es válida", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Error: ip no valida");
                        Toast.makeText(getApplicationContext(), "La Direccion IP no es válida", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        /**
         * Devuelve [true] si el parametro de entrada es un ip válido
         *
         * @param ipadress direccion a verificar
         */
        private boolean ipEsValido(String ipadress) {
            if (ipadress == null) {
                return false;
            } else if (ipadress.equals("localhost")) {
                return true;
            }
            String n;
            for (int i = 0; i < 3; i++) {
                if (ipadress.indexOf('.') == -1) {
                    return false;
                }
                n = ipadress.substring(0, ipadress.indexOf('.'));
                ipadress = ipadress.substring(ipadress.indexOf('.') + 1);
                if (!numIpEsValido(n)) {
                    return false;
                }
            }
            return numIpEsValido(ipadress);
        }

        /**
         * Verifica si el String es un numero del rango [0, 255]
         *
         * @param n num a verificar
         */
        private boolean numIpEsValido(String n) {
            try {
                int x = Integer.decode(n);
                if (x >= 0 && x < 256) {
                    return true;
                }
            } catch (NumberFormatException e) {

            }
            return false;
        }
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

    private void startWaitingActivity() {
        Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
    }
}