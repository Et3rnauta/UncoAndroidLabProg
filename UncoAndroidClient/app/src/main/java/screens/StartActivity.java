package screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uncoandroidclient.R;

import components.DatabaseState;
import components.MusicState;

import logica.GameState;

public class StartActivity extends AppCompatActivity {

    EditText Username, Password;
    Button Login, Register, Exit;

    boolean isScreenSpanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Configurar el lenguaje de la app
        Username = (EditText) findViewById(R.id.ptxt_start_username);
        Password = (EditText) findViewById(R.id.ptxt_start_password);
        Login = (Button) findViewById(R.id.btn_start_login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin(view);
            }
        });
        Register = (Button) findViewById(R.id.btn_start_register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegister(view);
            }
        });
        Exit = (Button) findViewById(R.id.btn_start_exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExit(view);
            }
        });

        if (!MusicState.isPlaying()) {
            System.out.println("Entra");
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bg_music);
            try {
                mediaPlayer.prepare();
            } catch (Exception e) {
            }
            mediaPlayer.start();
            MusicState.getMusicObject().setMediaPlayer(mediaPlayer);
        }
        isScreenSpanish = GameState.isSpanish;
    }

    /**
     * Ejecución de Botón de Login
     */
    private void btnLogin(View view) {
        String user = Username.getText().toString(),
                pass = Password.getText().toString();

        if (user.equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese un Nombre de Usuario", Toast.LENGTH_SHORT).show();
        } else if (pass.equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
        } else if (!DatabaseState.getDatabaseObject(this).checkLogIn(user, pass)) {
            Toast.makeText(getApplicationContext(), "El Nombre o la Contraseña son incorrectos", Toast.LENGTH_SHORT).show();
        } else {
            GameState.playerName = user;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Username.setText("");
            Password.setText("");
        }
    }

    /**
     * Ejecución de Botón de Registrar
     */
    private void btnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Ejecución de Botón de Salir
     */
    private void btnExit(View view) {
        this.finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isScreenSpanish != GameState.isSpanish) {
            finish();
            startActivity(getIntent());
        }
    }
}