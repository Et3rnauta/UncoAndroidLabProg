package screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uncoandroidclient.R;

import components.DatabaseState;
import logica.GameState;

public class RegisterActivity extends AppCompatActivity {

    EditText Username, Password, PasswordRepeat;
    Button Register, Exit;

    boolean isScreenSpanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = (EditText) findViewById(R.id.ptxt_register_username);
        Password = (EditText) findViewById(R.id.ptxt_register_password);
        PasswordRepeat = (EditText) findViewById(R.id.ptxt_register_password_r);
        Register = (Button) findViewById(R.id.btn_register_register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegister(view);
            }
        });
        Exit = (Button) findViewById(R.id.btn_register_exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExit(view);
            }
        });

        isScreenSpanish = GameState.isSpanish;
    }

    private void btnExit(View view) {

    }

    private void btnRegister(View view) {
        String user = Username.getText().toString(),
                pass = Password.getText().toString(),
                pass2 = PasswordRepeat.getText().toString();
        DatabaseState database = DatabaseState.getDatabaseObject(this);

        if (user.equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese un Nombre de Usuario", Toast.LENGTH_SHORT).show();
        } else if (pass.equals("") || pass2.equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese las Contraseñas", Toast.LENGTH_SHORT).show();
        } else if (database.checkUserName(user)) {
            Toast.makeText(getApplicationContext(), "El Nombre de Usuario ya se encuentra en uso", Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(pass2)) {
            Toast.makeText(getApplicationContext(), "Las Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else if (!database.registerUser(user, pass)) {
            Toast.makeText(getApplicationContext(), "Error al registrar el Usuario", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "El Usuario se registro correctamente", Toast.LENGTH_SHORT).show();
            GameState.playerName = user;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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