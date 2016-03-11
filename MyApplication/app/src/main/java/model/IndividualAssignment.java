package model;



/**
 * Created by LunaLu on 2/9/16.
 */
public class IndividualAssignment {
    String assignmentName;
    double rawScore, scoreOutOf, percent;
    int year, month, day;
    boolean setScore;
    boolean canReachNextLevel;
    String nextLevel;
    String belongsToCourse, belongsToCategory;
    double percentToImprove;

    IndividualAssignment(){
    }

    IndividualAssignment(String course, String weight, String name, int y, int m, int d){
        this.assignmentName =name;
        this.year=y;
        this.month = m;
        this.day = d;
        this.rawScore = 0;
        this.setScore = false;
        this.belongsToCourse = course;
        this.belongsToCategory = weight;
        this.nextLevel = "A";
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
        if(rawScore == 0){
            this.percent = 0.0;
            return;
        }
        this.percent = rawScore/scoreOutOf;
    }

    public void setBelongsToCourse(String course){
        this.belongsToCourse = course;
    }

    public void setBelongsToCategory(String weight){
        this.belongsToCategory = weight;
    }

    public void setPercentToImprove(double percent){
        this.percentToImprove = percent;
    }

    public void setCanReachNextLevel(boolean canReachNextLevel) {
        this.canReachNextLevel = canReachNextLevel;
    }


    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
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

    public String getBelongsToCourse(){
        return belongsToCourse;
    }

    public String getBelongsToCategory(){
        return belongsToCategory;
    }

    public double getPercentToImprove(){
        return percentToImprove;
    }

    public boolean getCanReachNextLevel(){
        return canReachNextLevel;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    /**Return Assignment percentage obtained, used for calculation
     *
     * @return percentage calculated using score/score out of
     */
    public double getPercent(){ return percent;}

}
