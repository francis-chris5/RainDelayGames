package projects.chrisfrancis.raindelaygames;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import projects.chrisfrancis.raindelaygames.ConcreteGameFragents.FightStreet.FightStreetScene;
import projects.chrisfrancis.raindelaygames.ConcreteGameFragents.FightStreet.FightStreetHUD;
import projects.chrisfrancis.raindelaygames.ConcreteGameFragents.SpaceInvasion.SpaceInvasionHUD;
import projects.chrisfrancis.raindelaygames.ConcreteGameFragents.SpaceInvasion.SpaceInvasionScene;
import projects.chrisfrancis.raindelaygames.ConcreteGameFragents.TestGame.TestGameHUD;
import projects.chrisfrancis.raindelaygames.ConcreteGameFragents.TestGame.TestGameScene;
import projects.chrisfrancis.raindelaygames.ControlFragments.ActionControlsFragment;
import projects.chrisfrancis.raindelaygames.ControlFragments.DirectionControlsFragment;
import projects.chrisfrancis.raindelaygames.AbstractGameFragments.HUDFragment;
import projects.chrisfrancis.raindelaygames.AbstractGameFragments.SceneFragment;
import projects.chrisfrancis.raindelaygames.HighScoreClasses.HighScore;
import projects.chrisfrancis.raindelaygames.HighScoreClasses.HighScoreDatabaseHelper;
import projects.chrisfrancis.raindelaygames.HighScoreClasses.HighScoreInputActivity;

public class GameActivity extends AppCompatActivity implements DirectionControlsFragment.DirectionInterface, ActionControlsFragment.ActionInterface, HUDFragment.HUDInterface, SceneFragment.SceneToHUDInterface {


    /*
     * This is the activity that ties all the fragments for the engine together, controls, HUD and scene all set up
     * to work in conjunction. As games are developed the intent to start this from the main menu will carry an extra
     * which will indicate which game is to be loaded in as a fragment transaction.
     * The idea here is to kind of use fragments akin to the old cartridge games with each game composed of a game scene
     * fragment and a HUD fragment. The control buttons and menu buttons (help, exit --check high scores, etc.) will be
     * passed on to the particular game scene fragment
     */


    private String currentGame = new String("Current Game"); //set this from extra passed in with intent
    private double currentScore = 0;

        /*
         * speed in milliseconds, so frames-per-second is 1000 divided by this, touch listener needs this
         * number to be passed in from the game
         */
    private int engineSpeed = 40;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
            //determine what game is to be loaded
        currentGame = getIntent().getStringExtra("game");
        loadGame();
    }//end onCreate()


    @Override
    protected void onStart() {
        super.onStart();
        getEngineSpeed(); //set up frames per second
        passHUDData(getStartStuff()); //initialize the HUD
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_activity_action_bar_menu, menu);
        return true;
    }//end onCreateOptionsMenu()



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        switch(item.getItemId()){
            case R.id.miNew:
                sceneFragment.restartGame();
                return true;
            case R.id.miHelp:
                sceneFragment.showHelp();
                return true;
            case R.id.miExit:
                checkHighScore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//end onOptionsItemSelected()



    public void loadGame(){
        FragmentManager boss = getSupportFragmentManager();
        FragmentTransaction trade = boss.beginTransaction();
        switch (currentGame){
            case "Test Game":
                trade.add(R.id.frgHUD, new TestGameHUD());
                trade.add(R.id.frgScene, new TestGameScene());
                trade.commit();
                break;
            case "Fight Street":
                trade.add(R.id.frgHUD, new FightStreetHUD());
                trade.add(R.id.frgScene, new FightStreetScene());
                trade.commit();
                break;
            case "Space Invasion":
                trade.add(R.id.frgHUD, new SpaceInvasionHUD());
                trade.add(R.id.frgScene, new SpaceInvasionScene());
                trade.commit();
                break;
        }
    }//end loadGame()



        /*
         * Here are the events to be passed on to the particular game/hud fragments
         */


    @Override
    public Object[] getStartStuff() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        return sceneFragment.getStartStuff();
    }

    public void getEngineSpeed(){
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        engineSpeed = sceneFragment.getEngineSpeed();
        DirectionControlsFragment directionControlsFragment = (DirectionControlsFragment)getSupportFragmentManager().findFragmentById(R.id.frgDirectionControls);
        directionControlsFragment.setEngineSpeed(engineSpeed);
    }

    @Override
    public void clickRed() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.clickRed();
    }//end clickRed()

    @Override
    public void clickBlue() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.clickBlue();
    }//end clickBlue()

    @Override
    public void clickPurple() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.clickPurple();
    }//end clickPurple()

    @Override
    public void clickYellow() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.clickYellow();
    }//end clickYellow()

    @Override
    public void longClickRed() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.longClickRed();
    }

    @Override
    public void longClickBlue() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.longClickBlue();
    }

    @Override
    public void longClickPurple() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.longClickPurple();
    }

    @Override
    public void longClickYellow() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.longClickYellow();
    }

    @Override
    public void moveUp() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.moveUp();
    }//end moveUp()

    @Override
    public void moveDown() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.moveDown();
    }//end moveDown()

    @Override
    public void moveLeft() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.moveLeft();
    }//end moveLeft()

    @Override
    public void moveRight() {
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        sceneFragment.moveRight();
    }//end moveRight()

    @Override
    public void passHUDData(Object... objects) {
        HUDFragment hudFragment = (HUDFragment)getSupportFragmentManager().findFragmentById(R.id.frgHUD);
        hudFragment.updateHUDData(objects);
    }//end passHUDData()


    @Override
    public void showHelp(String instructions) {
        Toast.makeText(GameActivity.this, instructions, Toast.LENGTH_LONG).show();
    }//end showHelp()


    public void checkHighScore(){
            //compares current score to high score in database for this game
        SceneFragment sceneFragment = (SceneFragment)getSupportFragmentManager().findFragmentById(R.id.frgScene);
        currentScore = sceneFragment.getGameScore();
        currentGame = sceneFragment.getGameName();
        HighScoreDatabaseHelper db = new HighScoreDatabaseHelper(this);
        HighScore current = db.getCurrentScore(currentGame);
        if(currentScore > current.getScore()){
            Intent saveScore = new Intent(GameActivity.this, HighScoreInputActivity.class);
            saveScore.putExtra("newHighScoreGame", currentGame);
            saveScore.putExtra("newHighScoreScore", currentScore);
            startActivity(saveScore);
        }
        else{
            startActivity(new Intent(GameActivity.this, MainActivity.class));
        }
    }//end checkHighScore()

}//end GameActivity
