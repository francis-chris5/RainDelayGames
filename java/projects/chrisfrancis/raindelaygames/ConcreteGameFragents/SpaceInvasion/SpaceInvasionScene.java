package projects.chrisfrancis.raindelaygames.ConcreteGameFragents.SpaceInvasion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;
import projects.chrisfrancis.raindelaygames.AbstractGameFragments.SceneFragment;
import projects.chrisfrancis.raindelaygames.GameActivity;
import projects.chrisfrancis.raindelaygames.R;
import static android.content.Context.MODE_PRIVATE;
public class SpaceInvasionScene extends SceneFragment {


    ////////////////////////////////////  DATA FIELDS  /////////////////////////////////////////////////////

    private Object[] hud_data = new Object[1];
    private double sceneWidth;
    private double sceneHeight;

    private ImageView imgScene;
    private SharedPreferences controlValues;
    private int score = 0;

    private ImageView imgTruck;
    private ImageView imgMissile;
    private float truckX = 0;
    private float truckY = 0;
    private int truckSpeed = 4;
    private float missileX = 0;
    private float missileY = 0;
    private int missleSpeed = 5;
    private boolean launch = false;
    private int offTarget = 0;
    private SharedPreferences defenderValues;

    private ImageView imgAlien;
    private float alienX = 0;
    private float alienY = 0;
    private int alienSpeed = 4;
    private boolean alienLeft = true;
    private boolean lower = false;
    private float nextAltitude = 0;
    private boolean landing = false;
    private SharedPreferences alienValues;





    //////////////////////////////////  INITIALIZE THE GAME /////////////////////////////////////////////////


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        engineSpeed = 84;
        gameName = "Space Invasion";
        gameScore = 0.0;
        gameInstructions = "Use the left and right green arrows to position the launcher \n Use the red button to fire the missle";
        controlValues = getActivity().getSharedPreferences("controlValues", MODE_PRIVATE);
        defenderValues = getActivity().getSharedPreferences("defenderValues", MODE_PRIVATE);
        alienValues = getActivity().getSharedPreferences("alienValues", MODE_PRIVATE);
    }//end onCreate()




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.space_invasion_scene, null);
        imgScene = content.findViewById(R.id.imgScene);
        imgAlien = content.findViewById(R.id.imgAlien);
        imgMissile = content.findViewById(R.id.imgMissile);
        imgTruck = content.findViewById(R.id.imgTruck);
        return content;
    }//end onCreateView()




    @Override
    public void onPause(){
        SharedPreferences.Editor editControl = controlValues.edit();
        editControl.putBoolean("alienLeft", alienLeft);
        editControl.putBoolean("lower", lower);
        editControl.putBoolean("launch", launch);
        editControl.putBoolean("landing", landing);
        editControl.putInt("score", score);
        editControl.commit();
        SharedPreferences.Editor editDefender = defenderValues.edit();
        editDefender.putFloat("truckX", truckX);
        editDefender.putFloat("truckY", truckY);
        editDefender.putFloat("missileX", missileX);
        editDefender.putFloat("missileY", missileY);
        editDefender.putInt("offTarget", offTarget);
        editDefender.commit();
        SharedPreferences.Editor editAlien = alienValues.edit();
        editAlien.putFloat("alienX", alienX);
        editAlien.putFloat("alienY", alienY);
        editAlien.putInt("alienSpeed", alienSpeed);
        editAlien.putFloat("nextAltitude", nextAltitude);
        editAlien.commit();

        super.onPause();
    }//end onPause()




    @Override
    public void onResume(){
        super.onResume();
        alienLeft = controlValues.getBoolean("alienLeft", true);
        lower = controlValues.getBoolean("lower", false);
        launch = controlValues.getBoolean("launch", false);
        landing = controlValues.getBoolean("landing", false);
        score = controlValues.getInt("score", 0);
        truckX = defenderValues.getFloat("truckX", 0);
        truckY = defenderValues.getFloat("truckY", 0);
        missileX = defenderValues.getFloat("missileX", 0);
        missileY = defenderValues.getFloat("missileY", 0);
        offTarget = defenderValues.getInt("offTarget", 0);
        imgTruck.setTranslationX(truckX);
        imgTruck.setTranslationY(truckY);
        imgMissile.setTranslationX(missileX);
        imgMissile.setTranslationY(missileY);
        if(launch){
            fire();
            imgMissile.setImageResource(R.drawable.missilelaunch);
        }
        else{
            imgMissile.setImageResource(R.drawable.missile);
        }
        alienX = alienValues.getFloat("alienX", alienX);
        alienY = alienValues.getFloat("alienY", alienY);
        alienSpeed = alienValues.getInt("alienSpeed", 4);
        nextAltitude = alienValues.getFloat("nextAltitude", 0);
        imgAlien.setTranslationX(alienX);
        imgAlien.setTranslationY(alienY);
        hud_data[0] = score;
        sceneToHUDInterface.passHUDData(hud_data);
    }//end onResume()




    @Override
    public Object[] getStartStuff() {
        sceneWidth = imgScene.getLayoutParams().width;
        sceneHeight = imgScene.getLayoutParams().height;
        alienSpeed = (int)((double)4/650 * sceneWidth);
        truckSpeed = (int)((double)4/650 * sceneWidth);
        missleSpeed = (int)((double)5/450 * sceneHeight);
        launchAttack();
        hud_data[0] = score;
        return hud_data;
    }//end getStartSuff()




    /////////////////////////////////////////////  GETTERS AND SETTERS  /////////////////////////////////////////////

    @Override
    public double getGameScore() {
        setGameScore();
        return gameScore;
    }//end getGameScore()



    @Override
    public String getGameName() {
        return gameName;
    }//end getGameName()



    public void setGameScore(){
        gameScore = (double)score;
    }




    ////////////////////////////////////////////  CONTROL METHODS  ////////////////////////////////////////////////////

    @Override
    public void moveUp() { }
    @Override
    public void moveDown() { }




    @Override
    public void moveLeft() {
        if(!launch) {
            if (imgTruck.getX() > 0) {
                imgTruck.setTranslationX(imgTruck.getTranslationX() - truckSpeed);
                imgMissile.setTranslationX(imgMissile.getTranslationX() - truckSpeed);
                truckX -= truckSpeed;
                missileX -= truckSpeed;
            }
        }
    }//end moveLeft()




    @Override
    public void moveRight() {
        if(!launch) {
            if (imgTruck.getX() < sceneWidth - imgTruck.getLayoutParams().width) {
                imgTruck.setTranslationX(imgTruck.getTranslationX() + truckSpeed);
                imgMissile.setTranslationX(imgMissile.getTranslationX() + truckSpeed);
                truckX += truckSpeed;
                missileX += truckSpeed;
            }
        }
    }//end moveRight()




    @Override
    public void releaseUp() { }
    @Override
    public void releaseDown() { }
    @Override
    public void releaseLeft() { }
    @Override
    public void releaseRight() { }





    @Override
    public void clickRed() {
        fire();
    }//end clickRed()





    @Override
    public void longClickRed() { }
    @Override
    public void clickBlue() { }
    @Override
    public void longClickBlue() { }
    @Override
    public void clickPurple() { }
    @Override
    public void longClickPurple() { }
    @Override
    public void clickYellow() { }
    @Override
    public void longClickYellow() { }




    @Override
    public void restartGame() {
        confirmReset();
    }


    ////////////////////////////////////////////  OTHER METHODS //////////////////////////////////////////////////////


    public void fire(){
        launch = true;

        final Handler upHandle = new Handler();
        Runnable goUp = new Runnable() {
            @Override
            public void run() {
                moveMissileUp();
                checkHit();
                upHandle.postDelayed(this, 100);
                if(!launch){
                    upHandle.removeCallbacks(this);
                }
            }
        };
        upHandle.postDelayed(goUp, 100);
    }//end fire()

    public void moveMissileUp(){
        if(imgMissile.getY() > -25){
            imgMissile.setImageResource(R.drawable.missilelaunch);
            imgMissile.setTranslationY(imgMissile.getTranslationY() - missleSpeed);
            missileY -= missleSpeed;
        }
        else{
            launch = false;
            imgMissile.setImageResource(R.drawable.missile);
            imgMissile.setTranslationY(0);
            missileY = 0;

            offTarget++;
            if(offTarget < 3) {
                lower = true;
                alienSpeed += (int)((double)4/650 * sceneWidth);
                score -= 100;
                nextAltitude = imgAlien.getY() + (int)((double)94/450 * sceneHeight);
                hud_data[0] = score;
                sceneToHUDInterface.passHUDData(hud_data);
            }
            else{
                lower = true;
                nextAltitude = imgTruck.getY() - (int)((double)12/450 * sceneHeight);
            }
        }
    }//end moveMissleUp()

    public void checkHit(){
        float missX = imgMissile.getX() + 25;
        float missY = imgMissile.getY() + 6;
        float shipTop = imgAlien.getY() + 5;
        float shipRight = imgAlien.getX() + 110;
        float shipBottom = imgAlien.getY() + 50;
        float shipLeft = imgAlien.getX() + 15;
        if(missX > shipLeft && missX < shipRight && missY > shipTop && missY < shipBottom){
            launch = false;
            imgMissile.setImageResource(R.drawable.empty);
            imgAlien.setImageResource(R.drawable.explosion);
            Timer blast = new Timer();
            blast.schedule(new TimerTask(){
                @Override
                public void run(){
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            imgMissile.setTranslationY(0);
                            missileY = 0;
                            imgAlien.setImageResource(R.drawable.empty);
                            imgMissile.setImageResource(R.drawable.missile);
                            alienSpeed = (int)((double)4/650 * sceneWidth);
                            if(offTarget == 0){
                                score += 400;
                            }
                            else if(offTarget == 1){
                                score += 200;
                            }
                            else if(offTarget == 2){
                                score += 100;
                            }
                            hud_data[0] = score;
                            sceneToHUDInterface.passHUDData(hud_data);
                            offTarget = 0;
                            nextShip();
                        }
                    });
                }
            }, 1250);
        }
    }//end checkHit()


    public void nextShip(){
        Timer incoming = new Timer();
        incoming.schedule(new TimerTask(){
            @Override
            public void run(){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alienX = 0;
                        alienY = 0;
                        imgAlien.setTranslationY(0);
                        imgAlien.setTranslationX(0);
                        imgAlien.setImageResource(R.drawable.alien);
                    }
                });
            }
        }, 2500);
    }//end nextShip()

    public void launchAttack(){
        final Handler fleet = new Handler();
        Runnable fly = new Runnable() {
            @Override
            public void run() {
                flightPlan();
                fleet.postDelayed(this, 100);
                if(landing){
                    fleet.removeCallbacks(this);
                }
            }
        };
        fleet.postDelayed(fly, 100);
    }//end launchAttack()

    public void flightPlan(){
        //move back and forth, if a missile misses move down, after three misses land
        if(alienLeft) {
            if (imgAlien.getX() > 0) {
                imgAlien.setTranslationX(imgAlien.getTranslationX() - alienSpeed);
            }
            else{
                alienLeft = false;
            }
        }
        else{
            if(imgAlien.getX() < sceneWidth - imgAlien.getLayoutParams().width){
                imgAlien.setTranslationX(imgAlien.getTranslationX() + alienSpeed);
            }
            else{
                alienLeft = true;
            }
        }

        if(lower){
            if(imgAlien.getY() > nextAltitude){
                if(offTarget >= 3){
                    landing = true;
                }
                lower = false;
                nextAltitude = 0;
                if(landing){
                    invasion();
                }
            }
            else{
                imgAlien.setTranslationY(imgAlien.getTranslationY() + (int)((double)8/450 * sceneHeight));
            }
        }
    }//end flightPlan()

    public void invasion(){
        imgMissile.setImageResource(R.drawable.empty);
        imgTruck.setImageResource(R.drawable.explosion);
        Timer blast = new Timer();
        blast.schedule(new TimerTask(){
            @Override
            public void run(){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgTruck.setImageResource(R.drawable.empty);

                        truckX = 0;
                        truckY = 0;
                        missileX = 0;
                        missileY = 0;
                        launch = false;
                        offTarget = 0;

                        alienX = 0;
                        alienY = 0;
                        alienSpeed = (int)((double)4/650 * sceneWidth);
                        alienLeft = true;
                        lower = false;
                        nextAltitude = 0;
                        GameActivity gameActivity = (GameActivity)sceneToHUDInterface;
                        gameActivity.checkHighScore();
                    }
                });
            }
        }, 1500);
    }//end invasion()

    public void confirmReset(){
        AlertDialog.Builder confirm = new AlertDialog.Builder(getActivity());
        confirm.setMessage("Are you sure you want to start a new game?");
        confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirm.show();
    }//end confirmReset()


    public void resetGame(){

        score = 0;

        truckX = 0;
        truckY = 0;
        missileX = 0;
        missileY = 0;
        truckSpeed = (int)((double)4/650 * sceneWidth);
        missleSpeed = (int)((double)5/450 * sceneHeight);
        launch = false;
        offTarget = 0;

        alienX = 0;
        alienY = 0;
        alienSpeed = (int)((double)4/650 * sceneWidth);
        alienLeft = true;
        lower = false;
        nextAltitude = 0;
        landing = false;

        imgTruck.setImageResource(R.drawable.truck);
        imgMissile.setImageResource(R.drawable.missile);
        imgMissile.setTranslationY(0);

        hud_data[0] = score;
        sceneToHUDInterface.passHUDData(hud_data);

        launchAttack();
    }//end resetGame()


}//end SpaceInvasionScene
