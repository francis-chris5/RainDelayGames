package projects.chrisfrancis.raindelaygames.ConcreteGameFragents.SpaceInvasion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import projects.chrisfrancis.raindelaygames.AbstractGameFragments.HUDFragment;
import projects.chrisfrancis.raindelaygames.R;

public class SpaceInvasionHUD extends HUDFragment {

    private TextView txtSpaceInvasionScore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.space_invasion_hud, null);
        txtSpaceInvasionScore = content.findViewById(R.id.txtSpaceInvasionScore);
        return content;
    }//end onCreateView()

    @Override
    public void updateHUDData(Object... objects) {
            //one slot in array, should be an integer score
        clearHUD();
        txtSpaceInvasionScore.setText("Score: " + (int)objects[0]);
    }//end updateHUDData()

    public void clearHUD(){
        txtSpaceInvasionScore.setText("");
    }



}//end SpaceInvasionHUD
