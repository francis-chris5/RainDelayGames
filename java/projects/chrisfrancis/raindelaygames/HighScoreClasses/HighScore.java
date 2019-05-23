package projects.chrisfrancis.raindelaygames.HighScoreClasses;

public class HighScore {

    private String name;
    private double score;
    private String game;

    public HighScore() {
    }//end empty constructor

    public HighScore(String name, double score, String game) {
        this.name = name;
        this.score = score;
        this.game = game;
    }//end 3-arg constructor

    public String getName() {
        return name;
    }//end getName()

    public double getScore() {
        return score;
    }//end getScore()

    public String getGame() {
        return game;
    }//end getGame()

    public void setName(String name) {
        this.name = name;
    }//end setName()

    public void setScore(double score) {
        this.score = score;
    }//end setScore()

    public void setGame(String game) {
        this.game = game;
    }//end setGame()

}//end HighScore
