package screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uncoandroidclient.R;

import components.DatabaseState;
import logic.GameState;

public class EndActivity extends AppCompatActivity {

    TextView ranking, score, maxscore;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        ranking = (TextView) findViewById(R.id.txtv_end_endranking);
        String rankingString = ranking.getText().toString() + " " + GameState.getGameObject().getRankPos() + "°";
        ranking.setText(rankingString);
        score = (TextView) findViewById(R.id.txtv_end_endscore);
        String scoreString = score.getText().toString() + " " + GameState.getGameObject().playerScore;
        score.setText(scoreString);
        btnContinue = (Button) findViewById(R.id.btn_end_endcontinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
        maxscore = (TextView) findViewById(R.id.txtv_end_maxscore);
        int newScore = GameState.getGameObject().getScore();
        if (!DatabaseState.getDatabaseObject(this).newMaxScore(GameState.playerName, newScore)) {
            maxscore.setText("");
        }
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