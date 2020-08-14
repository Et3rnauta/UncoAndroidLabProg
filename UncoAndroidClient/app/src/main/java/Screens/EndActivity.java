package Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uncoandroidclient.R;

import Logic.GameState;

public class EndActivity extends AppCompatActivity {

    TextView ranking, score;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        ranking = (TextView) findViewById(R.id.txtv_end_endranking);
        ranking.setText("Terminaste " + GameState.getGameObject().getRankPos() + "°");
        score = (TextView) findViewById(R.id.txtv_end_endscore);
        score.setText("Puntaje Final: " + GameState.getGameObject().playerScore);
        btnContinue = (Button) findViewById(R.id.btn_end_endcontinue);
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Desabilita el boton de regreso
    }
}