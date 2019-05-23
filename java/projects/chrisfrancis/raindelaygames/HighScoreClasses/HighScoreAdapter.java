package projects.chrisfrancis.raindelaygames.HighScoreClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

import projects.chrisfrancis.raindelaygames.R;

public class HighScoreAdapter extends BaseAdapter {

    private LayoutInflater mHighScoreInflator;
    private HighScore[] winners;
    private DecimalFormat df = new DecimalFormat("#.##");

    public HighScoreAdapter(Context c, HighScore[] winners) {
        this.winners = winners;
        mHighScoreInflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }//end 2-arg constructor

    @Override
    public int getCount() {
        return winners.length;
    }//end getCount()

    @Override
    public Object getItem(int position) {
        return winners[position];
    }//end getItem()

    @Override
    public long getItemId(int position) {
        return position;
    }//end getItemId()

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View highScore = mHighScoreInflator.inflate(R.layout.high_score_entry, null);
        TextView txtHighScoreName = highScore.findViewById(R.id.txtHighScoreName);
        TextView txtHighScoreGame = highScore.findViewById(R.id.txtHighScoreGame);
        TextView txtHighScoreScore = highScore.findViewById(R.id.txtHighScoreScore);
        txtHighScoreName.setText(winners[i].getName());
        txtHighScoreGame.setText(winners[i].getGame());
        txtHighScoreScore.setText(df.format(winners[i].getScore()));
        return highScore;
    }//end getView()

}//end HighScoreAdapter
