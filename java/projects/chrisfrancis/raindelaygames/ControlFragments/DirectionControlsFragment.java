package projects.chrisfrancis.raindelaygames.ControlFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import projects.chrisfrancis.raindelaygames.R;


public class DirectionControlsFragment extends Fragment {


    /*
    * The fragment containing the direction controls from the engine. The interface
    * ensures the game activity implements the methods and then calls those methods
    * from this fragment, where they will then be pushed on to the particular
    * game/HUD fragments
    */


    //////////////////////////////////////  DATA FIELDS  ///////////////////////////////////////////////////////////

    private DirectionInterface directionInterface;
        //in milliseconds, so FPS is 1000 divided by this cast to an int
    private int engineSpeed = 40;

    private ImageButton btnUp;
    private ImageButton btnDown;
    private ImageButton btnLeft;
    private ImageButton btnRight;






    //////////////////////////////////  INITIALIZE THE FRAGMENT  ///////////////////////////////////////////////////

    public interface DirectionInterface{
        void moveUp();
        void releaseUp();
        void moveDown();
        void releaseDown();
        void moveLeft();
        void releaseLeft();
        void moveRight();
        void releaseRight();
    }//end DirectionInterface

    public DirectionControlsFragment() {
    }//end empty constructor()



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//end onCreate()


        /*
         * Touches instead of clicks on these buttons so that they can be held
         * and then called repeatedly at the speed passed in by game activity
         * and originating from the particular game scene fragment
         */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View controls = inflater.inflate(R.layout.fragment_direction_controls, null);

        btnUp = controls.findViewById(R.id.btnUp);
        btnUp.setOnTouchListener(new View.OnTouchListener() {
            private Handler upHandle;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(upHandle != null){
                            return true;
                        }
                        upHandle = new Handler();
                        upHandle.postDelayed(goUp, engineSpeed);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(upHandle == null){
                            return true;
                        }
                        upHandle.removeCallbacks(goUp);
                        upHandle = null;
                        directionInterface.releaseUp();
                        break;
                }
                return false;
            }

            Runnable goUp = new Runnable(){
                @Override
                public void run(){
                    directionInterface.moveUp();
                    upHandle.postDelayed(this, engineSpeed);
                }
            };
        });

        btnDown = controls.findViewById(R.id.btnDown);
        btnDown.setOnTouchListener(new View.OnTouchListener() {
            private Handler downHandle;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(downHandle != null){
                            return true;
                        }
                        downHandle = new Handler();
                        downHandle.postDelayed(goDown, engineSpeed);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(downHandle == null){
                            return true;
                        }
                        downHandle.removeCallbacks(goDown);
                        downHandle = null;
                        directionInterface.releaseDown();
                        break;
                }
                return false;
            }

            Runnable goDown = new Runnable(){
                @Override
                public void run(){
                    directionInterface.moveDown();
                    downHandle.postDelayed(this, engineSpeed);
                }
            };
        });

        btnLeft = controls.findViewById(R.id.btnLeft);
        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            private Handler leftHandle;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(leftHandle != null){
                            return true;
                        }
                        leftHandle = new Handler();
                        leftHandle.postDelayed(goLeft, engineSpeed);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(leftHandle == null){
                            return true;
                        }
                        leftHandle.removeCallbacks(goLeft);
                        leftHandle = null;
                        directionInterface.releaseLeft();
                        break;
                }
                return false;
            }

            Runnable goLeft = new Runnable(){
                @Override
                public void run(){
                    directionInterface.moveLeft();
                    leftHandle.postDelayed(this, engineSpeed);
                }
            };
        });

        btnRight = controls.findViewById(R.id.btnRight);
        btnRight.setOnTouchListener(new View.OnTouchListener() {
            private Handler rightHandle;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(rightHandle != null){
                            return true;
                        }
                        rightHandle = new Handler();
                        rightHandle.postDelayed(goRight, engineSpeed);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(rightHandle == null){
                            return true;
                        }
                        rightHandle.removeCallbacks(goRight);
                        rightHandle = null;
                        directionInterface.releaseRight();
                        break;
                }
                return false;
            }

            Runnable goRight = new Runnable(){
                @Override
                public void run(){
                    directionInterface.moveRight();
                    rightHandle.postDelayed(this, engineSpeed);
                }
            };
        });

        return controls;
    }//end onCreateView()


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //pass the touches through the activity to the game fragment
            directionInterface = (DirectionInterface)context;
        }
        catch(Exception e){
            e.printStackTrace();
            //interface not implemented...
        }
    }//end onAttach()

    @Override
    public void onDetach() {
        super.onDetach();
        directionInterface = null;
    }//end onDetach()




    //////////////////////////////////////////////// OTHER METHODS  ////////////////////////////////////////////////////////

        /*
         * Gets the speed the game is running at to determine how quickly to recall the
         * touch events on the direction controls
         */
    public void setEngineSpeed(int speed){
        engineSpeed = speed;
    }//end setEngineSpeed()

}//end DirectionControlFragment
