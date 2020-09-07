package screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uncoandroidclient.R;

import logica.GameState;
import logica.WaitingThread;

public class WaitingActivity extends AppCompatActivity {

    TextView qScore;
    WaitingThread waitingThread;
    GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        gameState = GameState.getGameObject();
        qScore = (TextView) findViewById(R.id.txtv_wait_qscore);
        String score = qScore.getText().toString() + " " + gameState.playerScore;
        qScore.setText(score);

        waitingThread = new WaitingThread(this);
        gameState.setWaitingThread(waitingThread);
        new Thread(waitingThread).start();
    }

    public void endGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startEndActivity();
            }
        });
    }

    private void startEndActivity() {
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
        finish();
    }

    public void startQuestion() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startQuestionActivity();
            }
        });
    }

    private void startQuestionActivity() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Desabilita el boton de regreso
    }
}