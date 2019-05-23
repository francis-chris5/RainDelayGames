package projects.chrisfrancis.raindelaygames.ConcreteGameFragents.TestGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

import projects.chrisfrancis.raindelaygames.AbstractGameFragments.HUDFragment;
import projects.chrisfrancis.raindelaygames.R;

public class TestGameHUD extends HUDFragment {

    private TextView txtName;
    private TextView txtScore;

    private DecimalFormat df = new DecimalFormat("#.##");





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View hudDisplay = inflater.inflate(R.layout.fragment_hud, null);
        txtName = hudDisplay.findViewById(R.id.txtHighScoreName);
        txtScore = hudDisplay.findViewById(R.id.txtScore);
        return hudDisplay;
    }





    @Override
    public void updateHUDData(Object... objects) {
        clearHUD();
        fillOutName((String)objects[0]);
        fillOutScore((double)objects[1]);
    }


    public void clearHUD(){
        txtName.setText("");
        txtScore.setText("");
    }

    public void fillOutName(String name){
        txtName.setText(name);
    }

    public void fillOutScore(double score){
        txtScore.setText(df.format(score));
    }
}
