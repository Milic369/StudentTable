package studenttable;

import java.util.List;

/**
 * Created by andrey on 05/03/16.
 */
public class Student {

    private String lastName;
    private String firstName;
    private String middleName;
    private int numberGroup;
    private double middleMark;
    private List<Examination> examinations;

    public Student(String lastName, String firstName, String middleName,
                   int numberGroup, List<Examination> examinations) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.numberGroup = numberGroup;
        this.examinations = examinations;
        this.middleMark = solveMiddleMark();
    }

    private double solveMiddleMark() {
        int val = 0;
        if (examinations.size() == 0) return 0;
        for (Examination exam: examinations){
            val += Integer.parseInt(exam.getExaminationMark());
        }
        return val/examinations.size();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<Examination> examinations) {
        this.examinations = examinations;
    }

    public double getMiddleMark() {
        return middleMark;
    }

    public void setMiddleMark(double middleMark) {
        this.middleMark = middleMark;
    }


    public String getField(int i) {
        if (i == 0) return getLastName() + " " + getFirstName() + " " + getMiddleName();
        else if (i == 1) return Integer.toString(getNumberGroup());
        else {
            int numberExamination = (i - 2) / 2;
            if (i % 2 == 0) {
                if (numberExamination < getExaminations().size()) {
                    return getExaminations().get(numberExamination).getExaminationName();
                } else return " - ";
            } else {
                if (numberExamination < getExaminations().size()) {
                    return getExaminations().get(numberExamination).getExaminationMark();
                } else return " - ";
            }
        }
    }

}
