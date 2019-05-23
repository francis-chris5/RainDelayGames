package projects.chrisfrancis.raindelaygames.AbstractGameFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class HUDFragment extends Fragment {

        /*
         * This class is to be the parent of the particular
         * Heads Up Display that goes with each game. Game activity
         * is set up to stream in data as an array of objects from
         * the game scene and controls components
         */


    //////////////////////////////////////////////  DATA FIELDS  /////////////////////////////////////////////////////////

    protected HUDInterface hudInterface;




    /////////////////////////////////////////////  INITIALIZE THE FRAGMENT  //////////////////////////////////////////////

    public interface HUDInterface{
            //make sure data can be passed in
        void passHUDData(Object ...objects);
    }

    public HUDFragment() {
        setRetainInstance(true);
    }//end empty constructor


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//end onCreate()

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
                //so HUD can control data stream too, usually from game though
            hudInterface = (HUDInterface) context;
        }
        catch(Exception e){
            e.printStackTrace();
            //interface not implemented...
        }
    }//end onAttach()

    @Override
    public void onDetach() {
        super.onDetach();
        hudInterface = null;
    }//end onDetach()


    ////////////////////////////////////////////////////////  HUD METHODS  ///////////////////////////////////////////////

        /*
         * An array of data coming in to display in the
         * particular HUD
         */
    public abstract void updateHUDData(Object ...objects);


}//end HUDFragment
