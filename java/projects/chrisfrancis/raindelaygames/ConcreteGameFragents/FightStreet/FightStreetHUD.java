package projects.chrisfrancis.raindelaygames.ConcreteGameFragents.FightStreet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import projects.chrisfrancis.raindelaygames.AbstractGameFragments.HUDFragment;
import projects.chrisfrancis.raindelaygames.R;

public class FightStreetHUD extends HUDFragment {

    private TextView txtWins, txtLosses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fight_street_hud, null);
        txtWins = content.findViewById(R.id.txtWins);
        txtLosses = content.findViewById(R.id.txtLosses);
        return content;
    }

    @Override
    public void updateHUDData(Object... objects) {
        //integers: 0 is wins, 1 is losses
        clearHUD();
        txtWins.setText("Wins: " + (int)objects[0]);
        txtLosses.setText("Losses: " + (int)objects[1]);
    }

    public void clearHUD(){
        txtWins.setText("");
        txtLosses.setText("");
    }
}
