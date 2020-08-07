package Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.uncoandroidclient.R;

public class RegisterActivity extends AppCompatActivity {

    EditText Username, Password, PasswordRepeat;
    Button Register, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = (EditText)findViewById(R.id.ptxt_register_username);
        Password = (EditText)findViewById(R.id.ptxt_register_password);
        PasswordRepeat = (EditText)findViewById(R.id.ptxt_register_password_r);
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
    }

    private void btnExit(View view) {

    }

    private void btnRegister(View view) {

    }
}