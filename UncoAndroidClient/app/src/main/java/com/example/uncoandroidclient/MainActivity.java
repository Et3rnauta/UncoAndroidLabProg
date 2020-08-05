package com.example.uncoandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Conexion.ClientConnector;
import Conexion.ConnectionRequestHandler;

public class MainActivity extends AppCompatActivity {
    private TextView status, output;

    private EditText input;

    private Button connect, send, disconnect;

    private ClientConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.txtv_status);
        output = (TextView) findViewById(R.id.txtv_output);

        input = (EditText) findViewById(R.id.ptxt_input);

        connect = (Button) findViewById(R.id.btn_connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectButton().execute();
            }
        });

        send = (Button) findViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendButton().execute(input.getText().toString());
            }
        });

        disconnect = (Button) findViewById(R.id.btn_disconnect);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DisconnectButton().execute();
            }
        });
    }


    class ConnectButton extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            connector = new ClientConnector("INGRESAR IP");
            if (connector.startConnection(new TestClientHandler())) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("Connected!");
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("Couldn't Connect!");
                    }
                });
            }
            return null;
        }

        private class TestClientHandler extends ConnectionRequestHandler {
            @Override
            public String handle(final String msgReq) {
                final String msg = msgReq;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        output.setText(msg);
                    }
                });
                return null;
            }
        }
    }

    class SendButton extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String message = strings[0];
            connector.makeRequest(message);
            return null;
        }
    }

    class DisconnectButton extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (connector.endConnection()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("Not connected!");
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("YOU CANT DISCONNECT, now you are mine!");
                    }
                });
            }
            return null;
        }
    }
}