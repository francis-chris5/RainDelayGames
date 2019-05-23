package projects.chrisfrancis.raindelaygames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import projects.chrisfrancis.raindelaygames.HighScoreClasses.HighScoresActivity;

public class MainActivity extends AppCompatActivity {

        /*
         * eventually this will be a menu to choose which game to play, it will then start the game activity
         * with extras on the intent, a fragment manager will choose which game fragment to load based off
         * that extra
         */

    private Button btnViewHighScores;
    private ImageButton btnStartFightStreet, btnStartSpaceInvasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnStartFightStreet = findViewById(R.id.btnStartFightStreet);
        btnStartFightStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fightStreetGame = new Intent(MainActivity.this, GameActivity.class);
                fightStreetGame.putExtra("game", "Fight Street");
                startActivity(fightStreetGame);
            }
        });

        btnStartSpaceInvasion = findViewById(R.id.btnStartSpaceInvasion);
        btnStartSpaceInvasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spaceInvasionGame = new Intent(MainActivity.this, GameActivity.class);
                spaceInvasionGame.putExtra("game", "Space Invasion");
                startActivity(spaceInvasionGame);
            }
        });


        btnViewHighScores = findViewById(R.id.btnViewHighScores);
        btnViewHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HighScoresActivity.class));
            }
        });
    }//end onCreate()



}//end MainActivity
