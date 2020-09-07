package screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uncoandroidclient.R;

import logica.GameState;
import logica.QuestionThread;

public class QuestionActivity extends AppCompatActivity {

    TextView qNumber, qDefinition;
    Button answer1, answer2, answer3, answer4;
    GameState gameState;
    QuestionThread questionThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        gameState = GameState.getGameObject();
        qNumber = (TextView) findViewById(R.id.txtv_qust_qnumb);
        qDefinition = (TextView) findViewById(R.id.txtv_qust_qdef);
        answer1 = (Button) findViewById(R.id.btn_qust_qans1);
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AnswerButton().execute(0);
            }
        });
        answer2 = (Button) findViewById(R.id.btn_qust_qans2);
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AnswerButton().execute(1);
            }
        });
        answer3 = (Button) findViewById(R.id.btn_qust_qans3);
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AnswerButton().execute(2);
            }
        });
        answer4 = (Button) findViewById(R.id.btn_qust_qans4);
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AnswerButton().execute(3);
            }
        });

        //Configurar pregunta en la pantalla
        String qNumbString = qNumber.getText().toString() + gameState.qNumber;
        qNumber.setText(qNumbString);
        qDefinition.setText(gameState.qDefinition);
        answer1.setText(gameState.answers[0]);
        answer2.setText(gameState.answers[1]);
        answer3.setText(gameState.answers[2]);
        answer4.setText(gameState.answers[3]);

        questionThread = new QuestionThread(this);
        gameState.setQuestionThread(questionThread);
        new Thread(questionThread).start();
    }

    class AnswerButton extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            gameState.sendAnswer(integers[0]);
            return null;
        }
    }

    public void endQuestion() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startWaitingActivity();
            }
        });
    }

    private void startWaitingActivity() {
        Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Desabilita el boton de regreso
    }
}