package projects.chrisfrancis.raindelaygames.ConcreteGameFragents.TestGame;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.chrisfrancis.raindelaygames.AbstractGameFragments.SceneFragment;
import projects.chrisfrancis.raindelaygames.R;


public class TestGameScene extends SceneFragment {



    //test data
    private double counter = 0.0;
    private Object[] stuff = {new String("test HUD display"), new Double(counter)};




    public TestGameScene() {
        gameName = "Test Game";
        gameScore = 0.0;
        gameInstructions = "Click the buttons to see test output strings sent to the HUD";
    }//end empty constructor



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scene, container, false);
    }



    @Override
    public Object[] getStartStuff() {
        //Object[] startStuff = {gameName, gameScore};
        Object[] startStuff = {300};
        return startStuff;
    }

    @Override
    public double getGameScore() {
        return 100.0;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public void moveUp() {
        stuff[0] = "touch up arrow";
        stuff[1] = counter + Math.random() * 5;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void moveDown() {
        stuff[0] = "touch down arrow";
        stuff[1] = counter + Math.random() * 5;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void moveLeft() {
        stuff[0] = "touch left arrow";
        stuff[1] = counter + Math.random() * 5;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void moveRight() {
        stuff[0] = "touch right arrow";
        stuff[1] = counter + Math.random() * 5;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void clickRed() {
        stuff[0] = "click red button";
        stuff[1] = counter - Math.random() * 3;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void longClickRed() {
        stuff[0] = "long red button";
        stuff[1] = counter + Math.random() * 7;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void clickBlue() {
        stuff[0] = "click blue button";
        stuff[1] = counter - Math.random() * 3;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void longClickBlue() {
        stuff[0] = "long blue button";
        stuff[1] = counter + Math.random() * 7;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void clickPurple() {
        stuff[0] = "click purple button";
        stuff[1] = counter - Math.random() * 3;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void longClickPurple() {
        stuff[0] = "long purple button";
        stuff[1] = counter + Math.random() * 7;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void clickYellow() {
        stuff[0] = "click yellow button";
        stuff[1] = counter - Math.random() * 3;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void longClickYellow() {
        stuff[0] = "long yellow button";
        stuff[1] = counter + Math.random() * 7;
        sceneToHUDInterface.passHUDData(stuff);
    }

    @Override
    public void restartGame() {
        stuff[0] = "restarted...";
        stuff[1] = 1.1;
        sceneToHUDInterface.passHUDData(stuff);
    }
}//end SceneFragment

