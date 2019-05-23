package projects.chrisfrancis.raindelaygames.AbstractGameFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.chrisfrancis.raindelaygames.R;


public abstract class SceneFragment extends Fragment {

        /*
         * This class is the parent for all game scenes, all the initialization is
         * handled here so the actual game fragment just needs to implement
         * the methods connecting to the control buttons and passing data
         * to the HUD, and initialize the values for the name, score, and instructions
         * in its constructor
         */

    ///////////////////////////////////////  PROTECTED DATA FIELDS ///////////////////////////////////////////////////////

    protected SceneToHUDInterface sceneToHUDInterface;
    protected int engineSpeed = 100; //milliseconds so FPS is 1000 divided by this
    protected String gameName = "Abstract Game Class";
    protected double gameScore = 0.0;
    protected String gameInstructions = "Abstract: Instructions will be generated particular to the game";






    //////////////////////////////////////  FRAGMENT COMPONENTS  ////////////////////////////////////////////////////////

    public interface SceneToHUDInterface{
        void passHUDData(Object... objects);//make sure data can be passed from game to HUD
        Object[] getStartStuff(); //stuff to initialize the HUD
        void showHelp(String instructions);//game activity pops up a toast in context, pass string from fragment
        void getEngineSpeed();
    }



    public SceneFragment() {
        setRetainInstance(true);
    }//end empty constructor



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//end onCreate()




        /*
         * this will initialize the games in concrete versions
         */

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //to access the method to pass data to HUD and send "help" to toast
            sceneToHUDInterface = (SceneToHUDInterface) context;
        }
        catch(Exception e){
            //interface not implemented...
        }
    }//end onAttach()



        /*
         * Is this the best spot to automatically save game data???
         */
    @Override
    public void onDetach() {
        super.onDetach();
    }//end onDetach()






    ////////////////////////////////////////////  METHODS CALLED FROM CONTROL FRAGMENTS  /////////////////////////////////////////////


        /*
         * passes the speed the game moves at to the direction control
         * buttons for the speed the touch events occur at
         */
    public int getEngineSpeed(){
        return engineSpeed;
    }


        /*
         * initialization for data going to the HUD
         */
    public abstract Object[] getStartStuff();


        /*
         * get the name and score for high score database
         */
    public abstract double getGameScore();

    public abstract String getGameName();



        /*
         * Direction Controls:
         * activated on touch events at engine speed
         */

    public abstract void moveUp();

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void moveRight();

    public abstract void clickRed();

    public abstract void longClickRed();

    public abstract void clickBlue();

    public abstract void longClickBlue();

    public abstract void clickPurple();

    public abstract void longClickPurple();

    public abstract void clickYellow();

    public abstract void longClickYellow();





        /*
         * MENU BUTTONS:
         * restart and instructions need to come from the particular game
         */
    public abstract void restartGame();

    public void showHelp(){
            //pass the instructions to a toast generated in context of game activity
        sceneToHUDInterface.showHelp(gameInstructions);
    }




}//end SceneFragment
