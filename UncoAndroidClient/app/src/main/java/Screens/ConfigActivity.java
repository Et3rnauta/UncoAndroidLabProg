package Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;


import com.example.uncoandroidclient.R;

import Logic.GameState;

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
        Locale myLocale;
        if (GameState.isSpanish) {
            myLocale = new Locale("en");
        } else {
            myLocale = new Locale("es");
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);
        GameState.changeLanguage();
        Intent refresh = new Intent(this, ConfigActivity.class);
        finish();
        startActivity(refresh);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void btnExit(View view) {
    }

    private void btnMusic(View view) {
    }
}