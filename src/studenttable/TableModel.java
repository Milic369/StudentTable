package studenttable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 05/03/16.
 */
public class TableModel {

    private int numberExaminations = 5;
    private List<Student> students;

    public TableModel() {
        students = new ArrayList<Student>();
    }

    public int getNumberExaminations() {
        return numberExaminations;
    }

    public void setNumberExaminations(int numberExaminations) {
        this.numberExaminations = numberExaminations;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
