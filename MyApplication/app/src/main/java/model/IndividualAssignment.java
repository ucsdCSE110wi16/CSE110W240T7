package model;



/**
 * Created by LunaLu on 2/9/16.
 */
public class IndividualAssignment {
    String assignmentName;
    double rawScore, scoreOutOf, percent;
    int year, month, day;
    boolean setScore;

    IndividualAssignment(String name, int y, int m, int d){
        this.assignmentName =name;
        this.year=y;
        this.month = m;
        this.day = d;
        this.rawScore = 0;
        this.setScore = false;
    }

    public String getAssignmentName(){
        return assignmentName;
    }

    public boolean isSetScore(){
        return setScore;
    }

    public void setScore(double s, double outof){
        setScore=true;
        this.rawScore = s;
        this.scoreOutOf = outof;
        this.percent = rawScore/scoreOutOf;
    }

    /**Return score to show in individual course page
     *
     * @return double score;
     */

    public double getRawScore(){
        return rawScore;
    }

    public double getScoreOutOf(){
        return scoreOutOf;
    }

    public int getDueDate(){
        return year*1000+month*100+day;
    }

    /**Return Assignment percentage obtained, used for calculation
     *
     * @return percentage calculated using score/score out of
     */
    public double getPercent(){ return percent;}

}
