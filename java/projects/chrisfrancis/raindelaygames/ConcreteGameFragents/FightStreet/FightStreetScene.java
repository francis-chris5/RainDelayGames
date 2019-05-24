package projects.chrisfrancis.raindelaygames.ConcreteGameFragents.FightStreet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import projects.chrisfrancis.raindelaygames.AbstractGameFragments.SceneFragment;
import projects.chrisfrancis.raindelaygames.R;
import static android.content.Context.MODE_PRIVATE;


public class FightStreetScene extends SceneFragment {

    //////////////////////////////////////////////  DATA FIELDS  //////////////////////////////////////////////////////////


    private Object[] hud_data = new Object[2]; //for HUD, 0 is wins, 1 is losses
    private DecimalFormat df = new DecimalFormat("#.##");
    private double meters;
    private double sceneWidth;
    private double sceneHeight;

    private ImageView imgScene;
    private SharedPreferences controlValues;
    private int win = 0;
    private int loss = 0;

    private ImageView imgHero;
    private float heroX = 0;
    private float heroY = 0;
    private boolean heroLeft = false;
    private boolean heroAttack = false;
    private double heroDamage = 0.0;
    private boolean heroKnockOut = false;
    private AnimationDrawable heroWalk;
    private boolean heroMoving = false;
    private SharedPreferences heroValues;

    private ImageView imgBadGuy;
    private float badX = 0;
    private float badY = 0;
    private int direction = 0;
    private boolean badLeft = true;
    private boolean badAttack = false;
    private boolean conflict = false;
    private boolean assault = false;
    private double badGuyDamage = 0.0;
    private boolean badKnockOut = false;
    private AnimationDrawable badGuyWalk;
    private boolean badGuyMoving = true;
    private SharedPreferences badGuyValues;




    ///////////////////////////////////////////  INITIALIZE THE GAME  ///////////////////////////////////////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        engineSpeed = 100;
        gameName = "Fight Street";
        gameScore = getGameScore();
        gameInstructions = "Green Arrows to move \n Blue Button to hit";
        controlValues = getActivity().getSharedPreferences("controlValues", MODE_PRIVATE);
        heroValues = getActivity().getSharedPreferences("heroValues", MODE_PRIVATE);
        badGuyValues = getActivity().getSharedPreferences("badGuyValues", MODE_PRIVATE);
    }//end onCreate()



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fight_street_scene, null);
        imgScene = content.findViewById(R.id.imgScene);
        imgHero = content.findViewById(R.id.imgHero);
        imgBadGuy = content.findViewById(R.id.imgBadGuy);
        generateOpponent();
        return content;
    }//end onCreateView()




    @Override
    public void onPause(){
        SharedPreferences.Editor editControl = controlValues.edit();
        editControl.putBoolean("heroLeft", heroLeft);
        editControl.putBoolean("heroAttack", heroAttack);
        editControl.putBoolean("heroKnockOut", heroKnockOut);
        editControl.putBoolean("badLeft", badLeft);
        editControl.putBoolean("badAttack", badAttack);
        editControl.putBoolean("conflict", conflict);
        editControl.putBoolean("assault", assault);
        editControl.putBoolean("badKnockOut", badKnockOut);
        editControl.putInt("win", win);
        editControl.putInt("loss", loss);
        editControl.commit();
        SharedPreferences.Editor editHero = heroValues.edit();
        editHero.putFloat("heroX", heroX);
        editHero.putFloat("heroY", heroY);
        editHero.putFloat("heroDamage", (float)heroDamage);
        editHero.commit();
        SharedPreferences.Editor editBadGuy = badGuyValues.edit();
        editBadGuy.putFloat("badX", badX);
        editBadGuy.putFloat("badY", badY);
        editBadGuy.putFloat("badGuyDamage", (float)badGuyDamage);
        editBadGuy.commit();
        super.onPause();
    }//end onPause()




    @Override
    public void onResume(){
        super.onResume();

        heroLeft = controlValues.getBoolean("heroLeft", false);
        heroAttack = controlValues.getBoolean("heroAttack", false);
        heroKnockOut = controlValues.getBoolean("heroKnockOut", false);
        badLeft = controlValues.getBoolean("badLeft", false);
        badAttack = controlValues.getBoolean("badAttack", false);
        conflict = controlValues.getBoolean("conflict", false);
        assault = controlValues.getBoolean("assault", false);
        badKnockOut = controlValues.getBoolean("badKnockOut", false);
        win = controlValues.getInt("win", 0);
        loss = controlValues.getInt("loss", 0);
        heroX = heroValues.getFloat("heroX", 0f);
        heroY = heroValues.getFloat("heroY", 0f);
        heroDamage = heroValues.getFloat("heroDamage", 0);
        imgHero.setTranslationX(heroX);
        imgHero.setTranslationY(heroY);
        badX = badGuyValues.getFloat("badX", 0f);
        badY = badGuyValues.getFloat("badY", 0f);
        badGuyDamage = badGuyValues.getFloat("badGuyDamage", 0);
        imgBadGuy.setTranslationX(badX);
        imgBadGuy.setTranslationY(badY);
        hud_data[0] = win;
        hud_data[1] = loss;
        sceneToHUDInterface.passHUDData(hud_data);
        drawHero();
        drawBadGuy();
        if(conflict){
            badMoves();
        }
    }//end onResume()

    @Override
    public Object[] getStartStuff() {
        sceneWidth = imgScene.getLayoutParams().width;
        sceneHeight = imgScene.getLayoutParams().height;
        meters =imgHero.getLayoutParams().height / 1.8288; //6ft man, checking this for physics engine in future games
        imgHero.setBackgroundResource(R.drawable.empty_animation);
        heroWalk = (AnimationDrawable)imgHero.getBackground();
        imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
        badGuyWalk = (AnimationDrawable)imgBadGuy.getBackground();
        hud_data[0] = win;
        hud_data[1] = loss;
        return hud_data;
    }//end getStartStuff()


    @Override
    public double getGameScore(){
        if(loss != 0) {
            return (double)win / loss;
        }
        else{
            return (double)win;
        }
    }//end getGameScore()


    @Override
    public String getGameName(){
        return gameName;
    }//end getGameName()






    //////////////////////////////////////////////  CONTROL METHODS  ////////////////////////////////////////////////////



    @Override
    public void moveUp() {
        if(imgHero.getY() > 0.16 * sceneHeight && !heroKnockOut) {
            imgHero.setTranslationY(imgHero.getTranslationY() - 5);
            heroY -= 5;
        }
        if(!heroKnockOut) {
            heroMoving = true;
        }
        drawHero();
    }//end moveUp()

    @Override
    public void moveDown() {
        if(imgHero.getY() < 0.70 * sceneHeight && !heroKnockOut){
            imgHero.setTranslationY(imgHero.getTranslationY() + 5);
            heroY += 5;
        }
        if(!heroKnockOut) {
            heroMoving = true;
        }
        drawHero();
    }//end moveDown()

    @Override
    public void moveLeft() {
        if(imgHero.getX() > -0.05 * sceneWidth && !heroKnockOut){
            imgHero.setTranslationX(imgHero.getTranslationX() - 5);
            heroX -= 5;
            heroLeft = true;
        }
        if(!heroKnockOut) {
            heroMoving = true;
        }
        drawHero();
    }//end moveLeft()

    @Override
    public void moveRight() {
        if(imgHero.getX() < 0.90 * sceneWidth && !heroKnockOut){
            imgHero.setTranslationX(imgHero.getTranslationX() + 5);
            heroX += 5;
            heroLeft = false;
        }
        if(!heroKnockOut) {
            heroMoving = true;
        }
        drawHero();
    }//end moveRight()

    @Override
    public void releaseUp() {
        heroMoving = false;
    }

    @Override
    public void releaseDown() {
        heroMoving = false;
    }

    @Override
    public void releaseLeft() {
        heroMoving = false;
    }

    @Override
    public void releaseRight() {
        heroMoving = false;
    }

    @Override
    public void clickRed() { }

    @Override
    public void longClickRed() { }

    @Override
    public void clickBlue() {
        heroWalk.stop();
        heroMoving = false;
        if(!heroKnockOut) {
            punch();
        }
    }//end clickBlue()

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
    }//end restartGame()




    //////////////////////////////////////////////  OTHER GAME METHODS  //////////////////////////////////////////////////


    public void drawHero(){
        if(!heroAttack) {
            if(heroKnockOut){
                if(!heroLeft){
                    heroMoving = false;
                    if(heroWalk.isRunning()) {
                        heroWalk.stop();
                    }
                    imgHero.setBackgroundResource(R.drawable.empty_animation);
                    imgHero.setImageResource(R.drawable.herodownright);
                }
                else{
                    heroMoving = false;
                    if(heroWalk.isRunning()) {
                        heroWalk.stop();
                    }
                    imgHero.setBackgroundResource(R.drawable.empty_animation);
                    imgHero.setImageResource(R.drawable.herodownleft);
                }
            }
            else if(!heroLeft) {
                if(!heroMoving){
                    if(heroWalk.isRunning()) {
                        heroWalk.stop();
                    }
                    imgHero.setBackgroundResource(R.drawable.empty_animation);
                    imgHero.setImageResource(R.drawable.heroright);
                }
                else {
                    imgHero.setImageResource(R.drawable.empty);
                    imgHero.setBackgroundResource(R.drawable.fight_street_hero_walk_right);
                    heroWalk = (AnimationDrawable) imgHero.getBackground();
                    heroWalk.start();
                }
            }
            else {
                if(!heroMoving){
                    if(heroWalk.isRunning()) {
                        heroWalk.stop();
                    }
                    imgHero.setBackgroundResource(R.drawable.empty_animation);
                    imgHero.setImageResource(R.drawable.heroleft);
                }
                else {
                    imgHero.setImageResource(R.drawable.empty);
                    imgHero.setBackgroundResource(R.drawable.fight_street_hero_walk_left);
                    heroWalk = (AnimationDrawable) imgHero.getBackground();
                    heroWalk.start();
                }
            }
        }
    }//end drawHero()

    public void punch(){
        heroAttack = true;
        heroMoving = false;
        if(!heroLeft) {
            if(heroWalk.isRunning()) {
                heroWalk.stop();
            }
            imgHero.setBackgroundResource(R.drawable.empty_animation);
            imgHero.setImageResource(R.drawable.herohitright);
        }
        else{
            if(heroWalk.isRunning()) {
                heroWalk.stop();
            }
            imgHero.setBackgroundResource(R.drawable.empty_animation);
            imgHero.setImageResource(R.drawable.herohitleft);
        }

        checkPunch();

        Timer hit = new Timer();
        hit.schedule(new TimerTask(){
            @Override
            public void run(){
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(!heroLeft) {
                            imgHero.setImageResource(R.drawable.heroright);
                        }
                        else{
                            imgHero.setImageResource(R.drawable.heroleft);
                        }
                        heroAttack = false;
                    }
                });
            }
        }, 250);
    }//end punch()

    public void checkPunch(){
        if(!heroLeft){
            float punchX = imgHero.getX() + 82;
            float punchY = imgHero.getY() + 45;
            float badTop = imgBadGuy.getY() + 40;
            float badBottom = imgBadGuy.getY() + 85;
            float badLeftSide = imgBadGuy.getX() + 44;
            float badRightSide = imgBadGuy.getX() + 82;
            if(!badLeft) {
                badLeftSide = imgBadGuy.getX() + 20;
                badRightSide = imgBadGuy.getX() + 53;
            }
            if(punchX > badLeftSide && punchX < badRightSide && punchY > badTop && punchY < badBottom){
                badKnockOut = true;
                badGuyMoving = false;
                if(badGuyWalk.isRunning()) {
                    badGuyWalk.stop();
                }
                imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                imgBadGuy.setImageResource(R.drawable.badguyleftdown);
                badGuyDamage += 0.34;
                imgBadGuy.setTranslationX(imgBadGuy.getTranslationX() + (float)(0.2 * sceneWidth));
                Timer victory = new Timer();
                victory.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                badGuyWounds();
                            }
                        });
                    }
                }, 500);
            }
        }
        else{
            float punchX = imgHero.getX() + 20;
            float punchY = imgHero.getY() + 45;
            float badTop = imgBadGuy.getY() + 40;
            float badBottom = imgBadGuy.getY() + 85;
            float badLeftSide = imgBadGuy.getX() + 20;
            float badRightSide = imgBadGuy.getX() + 53;
            if(badLeft) {
                badLeftSide = imgBadGuy.getX() + 44;
                badRightSide = imgBadGuy.getX() + 82;
            }
            if(punchX > badLeftSide && punchX < badRightSide && punchY > badTop && punchY < badBottom){
                badKnockOut = true;
                badGuyMoving = false;
                if(badGuyWalk.isRunning()) {
                    badGuyWalk.stop();
                }
                imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                imgBadGuy.setImageResource(R.drawable.badguyrightdown);
                badGuyDamage += 0.34;
                imgBadGuy.setTranslationX(imgBadGuy.getTranslationX() - (float)(0.2 * sceneWidth));
                Timer victory = new Timer();
                victory.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                badGuyWounds();
                            }
                        });
                    }
                }, 500);
            }
        }
    }//end checkPunch()


    public void generateOpponent(){
        Timer nextFight = new Timer();
        nextFight.schedule(new TimerTask(){
            @Override
            public void run(){
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(!conflict){
                            double chance = Math.random();
                            if(chance < 0.5){
                                conflict = true;
                                badMoves();
                                attempt();
                            }
                            else{
                                generateOpponent();
                            }
                        }
                    }
                });
            }
        }, 3000);
    }//end generateOpponent()

    public void drawBadGuy(){
        if(badKnockOut){
            if(!badLeft){
                badGuyMoving = false;
                if(badGuyWalk.isRunning()) {
                    badGuyWalk.stop();
                }
                imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                imgBadGuy.setImageResource(R.drawable.badguyrightdown);
            }
            else{
                badGuyMoving = false;
                if(badGuyWalk.isRunning()) {
                    badGuyWalk.stop();
                }
                imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                imgBadGuy.setImageResource(R.drawable.badguyrightdown);
            }
        }
        else if(assault){
            if(badGuyWalk.isRunning()) {
                badGuyWalk.stop();
            }
            imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
            badGuyMoving = false;
            if(!badLeft){
                imgBadGuy.setImageResource(R.drawable.badguyhitright);
            }
            else{
                imgBadGuy.setImageResource(R.drawable.badguyhitleft);
            }
        }
        else if(conflict){
            if(imgBadGuy.getX() > imgHero.getX()) {
                badLeft = true;
                if(!badGuyMoving){
                    if(badGuyWalk.isRunning()) {
                        badGuyWalk.stop();
                    }
                    imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                    imgBadGuy.setImageResource(R.drawable.badguyleft);
                }
                else {
                    imgBadGuy.setImageResource(R.drawable.empty);
                    imgBadGuy.setBackgroundResource(R.drawable.fight_street_bad_guy_walk_left);
                    badGuyWalk = (AnimationDrawable) imgBadGuy.getBackground();
                    badGuyWalk.start();
                }
            }
            else{
                badLeft = false;
                if(!badGuyMoving){
                    if(badGuyWalk.isRunning()) {
                        badGuyWalk.stop();
                    }
                    imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                    imgBadGuy.setImageResource(R.drawable.badguyright);
                }
                else {
                    imgBadGuy.setImageResource(R.drawable.empty);
                    imgBadGuy.setBackgroundResource(R.drawable.fight_street_bad_guy_walk_right);
                    badGuyWalk = (AnimationDrawable) imgBadGuy.getBackground();
                    badGuyWalk.start();
                }
            }
        }
        else{
            if(badGuyWalk.isRunning()) {
                badGuyWalk.stop();
            }
            imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
            imgBadGuy.setImageResource(R.drawable.empty);
        }
    }//end drawBadGuy()


    public void badMoves(){

        final Handler moving = new Handler();
        Runnable go = new Runnable(){
            @Override
            public void run(){
                moveBadGuy();
                moving.postDelayed(this, 100);
                if(!conflict){
                    moving.removeCallbacks(this);
                }
            }
        };
        moving.postDelayed(go, 100);

    }//end badMoves()

    public void moveBadGuy(){
        if(!assault) {
            double decide = Math.random();
            if(decide < 0.15) {
                direction = (int) (Math.random() * 4);
            }
            if(imgBadGuy.getX() < -0.05 * sceneWidth){
                direction = 3;
            }
            if(imgBadGuy.getX() > 0.90 * sceneWidth){
                direction = 1;
            }
            if(imgBadGuy.getY() >  0.70 * sceneHeight){
                direction = 2;
            }
            if(!badKnockOut) {
                if (direction == 0) {
                    badGuyMoving = true;
                    badMoveDown();
                } else if (direction == 1) {
                    badGuyMoving = true;
                    badMoveLeft();
                } else if (direction == 2) {
                    badGuyMoving = true;
                    badMoveUp();
                } else {
                    badGuyMoving = true;
                    badMoveRight();
                }
            }
        }
    }//end moveBadGuy()

    public void badMoveDown(){
        if(imgBadGuy.getY() <  0.70 * sceneHeight){
            imgBadGuy.setTranslationY(imgBadGuy.getTranslationY() + 2);
            badY += 2;
        }
        drawBadGuy();
    }//end badMoveDown()

    public void badMoveLeft(){
        if(imgBadGuy.getX() > -0.05 * sceneWidth){
            imgBadGuy.setTranslationX(imgBadGuy.getTranslationX() - 2);
            badX -= 2;
        }
        drawBadGuy();
    }//end badMoveLeft()

    public void badMoveUp(){
        if(imgBadGuy.getY() > 0.16 * sceneHeight){
            imgBadGuy.setTranslationY(imgBadGuy.getTranslationY() - 2);
            badY -= 2;
        }
        drawBadGuy();
    }//end badMoveUp()

    public void badMoveRight(){
        if(imgBadGuy.getX() < 0.90 * sceneWidth){
            imgBadGuy.setTranslationX(imgBadGuy.getTranslationX() + 2);
            badX += 2;
        }
        drawBadGuy();
    }//end badMoveRight()


    public void attempt(){
        final Handler assaultAttempt = new Handler();
        Runnable check = new Runnable(){
            @Override
            public void run(){
                advance();
                assaultAttempt.postDelayed(this, 350);
                if(!conflict){
                    assaultAttempt.removeCallbacks(this);
                }
            }
        };
        assaultAttempt.postDelayed(check, 350);
    }//end attempt()

    public void advance(){
        double strategy = Math.random();
        if(strategy < 0.5) {
            if (!badLeft) {
                float fistX = imgBadGuy.getX() + 82;
                float fistY = imgBadGuy.getY() + 45;
                float playerLeft = imgHero.getX() + 20;
                float playerRight = imgHero.getX() + 53;
                float playerTop = imgHero.getY() + 40;
                float playerBottom = imgHero.getY() + 85;
                if (!heroLeft) {
                    playerLeft = imgHero.getX() + 44;
                    playerRight = imgHero.getX() + 82;
                }

                if (fistX > playerLeft && fistX < playerRight && fistY > playerTop && fistY < playerBottom) {
                    assault = true;
                    heroKnockOut = true;
                    if(badGuyWalk.isRunning()) {
                        badGuyWalk.stop();
                    }
                    imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                    imgBadGuy.setImageResource(R.drawable.badguyhitright);
                    if(heroWalk.isRunning()) {
                        heroWalk.stop();
                    }
                    imgHero.setBackgroundResource(R.drawable.empty_animation);
                    imgHero.setImageResource(R.drawable.herodownleft);
                    heroDamage += 0.34;
                    imgHero.setTranslationX(imgHero.getTranslationX() + (float)(0.2 * sceneWidth));
                    Timer defeat = new Timer();
                    defeat.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    assault = false;
                                    imgBadGuy.setImageResource(R.drawable.badguyright);
                                    heroWounds();
                                }
                            });
                        }
                    }, 500);
                }
            } else {
                float fistX = imgBadGuy.getX() + 20;
                float fistY = imgBadGuy.getY() + 45;
                float playerLeft = imgHero.getX() + 44;
                float playerRight = imgHero.getX() + 82;
                float playerTop = imgHero.getY() + 40;
                float playerBottom = imgHero.getY() + 85;
                if (!heroLeft) {
                    playerLeft = imgHero.getX() + 20;
                    playerRight = imgHero.getX() + 53;
                }

                if (fistX > playerLeft && fistX < playerRight && fistY > playerTop && fistY < playerBottom) {
                    assault = true;
                    heroKnockOut = true;
                    if(badGuyWalk.isRunning()) {
                        badGuyWalk.stop();
                    }
                    imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
                    imgBadGuy.setImageResource(R.drawable.badguyhitleft);
                    if(heroWalk.isRunning()) {
                        heroWalk.stop();
                    }
                    imgHero.setBackgroundResource(R.drawable.empty_animation);
                    imgHero.setImageResource(R.drawable.herodownright);
                    heroDamage += 0.34;
                    imgHero.setTranslationX(imgHero.getTranslationX() - (float)(0.2 * sceneWidth));
                    Timer defeat = new Timer();
                    defeat.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    assault = false;
                                    imgBadGuy.setImageResource(R.drawable.badguyleft);
                                    heroWounds();
                                }
                            });
                        }
                    }, 500);
                }
            }
        }
    }//end advance()


    public void heroWounds(){
        if(heroDamage >= 1.0){
            loss++;
            heroDamage =0.0;
            badGuyDamage =0.0;
            heroX = 0;
            heroY = 0;
            heroLeft = false;
            badX = 0;
            badY = 0;
            badLeft = true;
            heroKnockOut = false;
            assault = false;
            conflict = false;
            imgHero.setTranslationX(0);
            imgHero.setTranslationY(0);
            imgBadGuy.setTranslationX(0);
            imgBadGuy.setTranslationY(0);
            imgHero.setImageResource(R.drawable.heroright);
            imgBadGuy.setImageResource(R.drawable.empty);
            getActivity().overridePendingTransition(0,0);
            hud_data[0] = win;
            hud_data[1] = loss;
            sceneToHUDInterface.passHUDData(hud_data);
            drawHero();
            drawBadGuy();
            generateOpponent();
            if(conflict){
                badMoves();
            }
            getActivity().overridePendingTransition(0,0);
        }
        else{
            heroKnockOut = false;
            imgHero.setImageResource(R.drawable.heroright);
        }
    }//end heroWounds()

    public void badGuyWounds(){
        if(badGuyDamage >= 1.0){
            win++;
            hud_data[0] = win;
            hud_data[1] = loss;
            sceneToHUDInterface.passHUDData(hud_data);
            heroDamage = 0.0;
            badGuyDamage = 0.0;
            if(badGuyWalk.isRunning()) {
                badGuyWalk.stop();
            }
            imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
            imgBadGuy.setImageResource(R.drawable.empty);
            badX = 0;
            badY = 0;
            imgBadGuy.setTranslationX(0);
            imgBadGuy.setTranslationY(0);
            badKnockOut = false;
            conflict = false;
            generateOpponent();
        }
        else{
            badKnockOut = false;
            if(badGuyWalk.isRunning()) {
                badGuyWalk.stop();
            }
            imgBadGuy.setBackgroundResource(R.drawable.empty_animation);
            imgBadGuy.setImageResource(R.drawable.badguyright);
        }
    }//end badGuyWounds()

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
        win = 0;
        loss = 0;
        heroX = 0;
        heroY = 0;
        heroLeft = false;
        heroAttack = false;
        heroDamage = 0.0;
        heroKnockOut = false;
        badX = 0;
        badY = 0;
        direction = 0;
        badLeft = true;
        badAttack = false;
        conflict = false;
        assault = false;
        badGuyDamage = 0.0;
        badKnockOut = false;
        hud_data[0] = win;
        hud_data[1] = loss;
        sceneToHUDInterface.passHUDData(hud_data);
        drawHero();
        drawBadGuy();
        if(conflict){
            badMoves();
        }
    }

}//end FightStreetScene
