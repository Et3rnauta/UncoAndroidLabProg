package Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uncoandroidclient.R;

import org.w3c.dom.Text;

import Logic.GameState;
import Logic.WaitingThread;

public class WaitingActivity extends AppCompatActivity {

    TextView qNumber;
    WaitingThread waitingThread;
    GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        gameState = GameState.getGameObject();
        qNumber = (TextView) findViewById(R.id.txtv_wait_qnumb);
        qNumber.setText("Pregunta N° " + gameState.qNumber);

        waitingThread = new WaitingThread(this);
        gameState.setWaitingThread(waitingThread);
        new Thread(waitingThread).start();
    }

    public void endGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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
}