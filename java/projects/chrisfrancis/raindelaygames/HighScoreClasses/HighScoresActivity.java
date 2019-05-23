package projects.chrisfrancis.raindelaygames.HighScoreClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import projects.chrisfrancis.raindelaygames.MainActivity;
import projects.chrisfrancis.raindelaygames.R;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        ListView lstHighScores = findViewById(R.id.lstHighScores);
        HighScore[] scores = getHighScoreList();
        HighScoreAdapter highScores = new HighScoreAdapter(this, scores);
        lstHighScores.setAdapter(highScores);
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }//end onCreate()




    public HighScore[] getHighScoreList(){
        HighScoreDatabaseHelper data = new HighScoreDatabaseHelper(this);
        return data.getHighScores();
    }//end getHighScores()



    public void exit(){
        startActivity(new Intent(HighScoresActivity.this, MainActivity.class));
    }//end exit()


}//end HighScores
