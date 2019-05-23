package projects.chrisfrancis.raindelaygames.ControlFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import projects.chrisfrancis.raindelaygames.R;


public class ActionControlsFragment extends Fragment {

    /*
     * this fragment controls the action buttons, the interface assures the activity is able to pass the
     * events on to the actual game/HUD fragments
     */


    ///////////////////////////////////  DATA FIELDS /////////////////////////////////////////////////////

    private ActionInterface actionInterface;

    private ImageButton btnRed;
    private ImageButton btnBlue;
    private ImageButton btnPurple;
    private ImageButton btnYellow;




    /////////////////////////////////  INITIALIZE THE FRAGENT //////////////////////////////////////////////
    public interface ActionInterface{
        void clickRed();
        void clickBlue();
        void clickPurple();
        void clickYellow();
        void longClickRed();
        void longClickBlue();
        void longClickPurple();
        void longClickYellow();
    }

    public ActionControlsFragment() {
    }//end empty constructor


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//end onCreate()


        /*
         * set view up with click and long-click listeners for each button, the events
         * are passed back through the interface to where the event is needed
         */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View controls = inflater.inflate(R.layout.fragment_action_controls, null);

        btnRed = controls.findViewById(R.id.btnRed);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionInterface.clickRed();
            }
        });
        btnRed.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                actionInterface.longClickRed();
                return true;
            }
        });

        btnBlue = controls.findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionInterface.clickBlue();
            }
        });
        btnBlue.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                actionInterface.longClickBlue();
                return true;
            }
        });

        btnPurple = controls.findViewById(R.id.btnPurple);
        btnPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionInterface.clickPurple();
            }
        });
        btnPurple.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                actionInterface.longClickPurple();
                return true;
            }
        });

        btnYellow = controls.findViewById(R.id.btnYellow);
        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionInterface.clickYellow();
            }
        });
        btnYellow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                actionInterface.longClickYellow();
                return true;
            }
        });

        return controls;
    }//end onCreateView()



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            actionInterface = (ActionInterface) context; //make sure the activity can pass the events on
        }
        catch(Exception e){
            e.printStackTrace();
            //interface not implemented...
        }
    }//end onAttach()

    @Override
    public void onDetach() {
        super.onDetach();
        actionInterface = null;
    }//end onDetach();


}//end ActionControlsFragment
