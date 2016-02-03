package androidstudio.edbud.com.myapplication;

import java.util.ArrayList;

/**
 * Created by LunaLu on 2/3/16.
 */
public class courses {
    ArrayList assignments;
    int unit;
    boolean letter;
    String courseId;

    courses(String id, int u, boolean l){
        this.courseId=id;
        this.unit=u;
        this.letter=l;
        assignments = new ArrayList();
    }

    void addAssignment(String hw){
        assignments.add(hw);
    }

    ArrayList getAssignments(){
        return assignments;
    }
}
