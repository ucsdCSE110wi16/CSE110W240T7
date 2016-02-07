package androidstudio.edbud.com.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LunaLu on 2/3/16.
 */
public class Courses {
    Map<String, String> assignments;
    int unit;
    boolean letter;
    String courseId;
    int gpa;

    Courses(String id, int u, boolean l){
        this.courseId=id;
        this.unit=u;
        this.letter=l;
        this.assignments = new HashMap<>();;
    }

    void addAssignment(String hw, String weights){
        assignments.put(hw, weights);
    }

    ArrayList getAssignments(String weights){
        return new ArrayList();
    }
}
