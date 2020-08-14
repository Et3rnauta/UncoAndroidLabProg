package Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uncoandroidclient.R;

public class ConfigActivity extends AppCompatActivity {

    Button Music, Language, Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Music = (Button) findViewById(R.id.btn_config_music);
        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMusic(view);
            }
        });
        Language = (Button) findViewById(R.id.btn_config_lang);
        Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLanguage(view);
            }
        });
        Exit = (Button) findViewById(R.id.btn_config_exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExit(view);
            }
        });
    }

    private void btnLanguage(View view) {
    }

    private void btnExit(View view) {
    }

    private void btnMusic(View view) {
    }
}