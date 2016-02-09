package model;

/**
 * Created by LunaLu on 2/9/16.
 */
public class IndividualAssignment {
    String assignmentName;
    int score;
    int year, month, day;

    IndividualAssignment(String name, int y, int m, int d){
        this.assignmentName =name;
        this.year=y;
        this.month = m;
        this.day = d;
    }

    public void setScore(int s){
        this.score = s;
    }


    public int getScore(){
        return score;
    }



}
