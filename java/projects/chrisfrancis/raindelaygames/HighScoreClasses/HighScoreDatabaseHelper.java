package projects.chrisfrancis.raindelaygames.HighScoreClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class HighScoreDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String HIGH_SCORE_DATA = "highScores.db";
    private String table = "highScoreTable";
    private String column1 = "_id";
    private String column2 = "name";
    private String column3 = "score";
    private String column4 = "game";
    private Context context;

    public HighScoreDatabaseHelper(Context context) {
        super(context, HIGH_SCORE_DATA, null, DB_VERSION);
        this.context = context;
    }//end 1-arg constructor


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + table + "(" +
                column1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column2 + " TEXT, " +
                column3 + " REAL, " +
                column4 + " TEXT " +
                ");";
        db.execSQL(createTable);
    }//end onCreate()



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }//end onUpgrade



    public void addHighScore(HighScore highScore){
        ContentValues score = new ContentValues();
        score.put(column2, highScore.getName());
        score.put(column3, (float)highScore.getScore());
        score.put(column4, highScore.getGame());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table, null, score);
        db.close();
    }//end addHighScore()



    public void removeHighScore(String game){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table + " WHERE " + column4 + " = \"" + game + "\"");
        db.close();
    }//end removeHighScore()



    public HighScore getCurrentScore(String game){
        HighScore score = new HighScore();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE " + column4 + " = \"" + game + "\"";
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()){
            score.setName(c.getString(1));
            score.setScore((double)c.getFloat(2));
            score.setGame(c.getString(3));
        }
        c.close();
        db.close();
        return score;
    }//end getCurrentScore()



    public Cursor queryHighScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query= "SELECT * FROM " + table + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }//end queryHighScores()



    public HighScore[] getHighScores(){
        ArrayList<HighScore> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            String tempName = c.getString(1);
            double tempScore = (double)c.getFloat(2);
            String tempGame = c.getString(3);
            HighScore temp = new HighScore(tempName, tempScore, tempGame);
            list.add(temp);
        }
        c.close();
        db.close();
        HighScore[] scores = new HighScore[list.size()];
        scores = list.toArray(scores);
        return scores;
    }//end getHighScores()


}//end HighScoreDatabaseHelper
