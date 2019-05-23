package projects.chrisfrancis.raindelaygames.HighScoreClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import projects.chrisfrancis.raindelaygames.MainActivity;
import projects.chrisfrancis.raindelaygames.R;

public class HighScoreInputActivity extends AppCompatActivity {

    private HighScoreDatabaseHelper db;
    private Button btnDone;
    private TextView lblNamePrompt;
    private EditText txtNewHighScoreName;
    private String game;
    private double score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_input);


        db = new HighScoreDatabaseHelper(this);

        game = getIntent().getStringExtra("newHighScoreGame");
        score = getIntent().getDoubleExtra("newHighScoreScore", 0);

        lblNamePrompt = findViewById(R.id.lblNamePrompt);
        lblNamePrompt.setText("That\'s a new high score for " + game + "! Please enter your name.");


        txtNewHighScoreName = findViewById(R.id.txtNewHighScoreName);
        txtNewHighScoreName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch(actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        updateRecord();
                        return true;
                    default:
                        return false;
                }
            }
        });
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecord();
            }
        });
    }//end onCreate()

    public String getName(){
        String name = txtNewHighScoreName.getText().toString();
        return name;
    }//end getName()

    public void updateRecord(){
        String name = getName();
        db.removeHighScore(game);
        db.addHighScore(new HighScore(name, score, game));
        startActivity(new Intent(HighScoreInputActivity.this, MainActivity.class));
    }//end updateRecord()



}//end HighScoreInputActivity
