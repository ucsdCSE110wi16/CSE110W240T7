package model;



/**
 * Created by LunaLu on 2/9/16.
 */
public class IndividualAssignment {
    String assignmentName;
    double rawScore, scoreOutOf, percent;
    int year, month, day;
    boolean setScore;
    String belongsTo;

    IndividualAssignment(){

    }

    IndividualAssignment(String weight, String name, int y, int m, int d){
        this.assignmentName =name;
        this.year=y;
        this.month = m;
        this.day = d;
        this.rawScore = 0;
        this.setScore = false;
        this.belongsTo = weight;
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

    public void setBelongsTo(String weight){
        this.belongsTo = weight;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getBelongsTo(){
        return belongsTo;
    }


    /**Return Assignment percentage obtained, used for calculation
     *
     * @return percentage calculated using score/score out of
     */
    public double getPercent(){ return percent;}

}
