package screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uncoandroidclient.R;

import java.util.ArrayList;
import java.util.List;

import components.DatabaseState;
import database.RecyclerViewAdapter;

public class RankingActivity extends AppCompatActivity {

    ArrayList<String> names, scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        names = new ArrayList<>();
        scores = new ArrayList<>();

        //Obtiene los nombres de los jugadores y sus respectivos puntajes maximos
        List<String[]> data = DatabaseState.getDatabaseObject(this).getRankData();
        String[] aux;
        for (int i = 0; i < data.size(); i++) {
            aux = data.get(i);
            names.add(aux[0]);
            scores.add(aux[1]);
        }

        //Inicializa el objeto RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyv_rank_ranking);
        recyclerView.setAdapter(new RecyclerViewAdapter(names, scores, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}