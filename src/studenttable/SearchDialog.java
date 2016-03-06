package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by andrey on 06/03/16.
 */
public class SearchDialog {

    private static final Insets insets = new Insets(0, 0, 0, 0);
    private MainPanel mainPanel;
    private TableModel tableModel;
    private String mode;
    private JTextField lastName;
    private JTextField group;
    private JComboBox[] examinationsMark;
    private JFrame frame;
    private MainPanel searchPanel;
    private JScrollPane searchScrollPanel;

    public SearchDialog(MainPanel mainPanel, String mode) {
        this.mainPanel = mainPanel;
        this.mode = mode;
        tableModel = mainPanel.getTableModel();
        frame = createFrame();
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(mainPanel);
    }

    private JFrame createFrame(){
        JFrame frame;
        JLabel labelText;
        if (mode.equals("SEARCH_MODE")) {
            frame = new JFrame("Search student");
            labelText = new JLabel("Search student");
        }
        else {
            frame = new JFrame("Remove student");
            labelText = new JLabel("Remove Student");
        }
        JPanel jPanelID = new JPanel();
        jPanelID.setLayout(new GridBagLayout());
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 0, 0, 3, 1);
        String[] labelString = {"Last Name*:", "Group:"};
        labelText = new JLabel(labelString[0]);
        addComponent(jPanelID,labelText, 0, 1, 1, 1);
        lastName = new JTextField(30);
        addComponent(jPanelID, lastName, 1, 1, 3, 1);
        labelText = new JLabel(labelString[1]);
        addComponent(jPanelID, labelText, 0, 2, 1, 1);
        group = new JTextField(30);
        addComponent(jPanelID, group, 1, 2, 3, 1);
        labelText = new JLabel("Middle mark(less/great)");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 0, 3, 1, 1);
        labelText = new JLabel("Mark(less/great)");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 0, 4, 1, 1);
        String[] markString = {"-", "4", "5", "6", "7", "8", "9", "10"};
        examinationsMark = new JComboBox[tableModel.getNumberExaminations()];
        for (int i=0; i < 2;i++){
            JComboBox jcbfMark = new JComboBox(markString);
            examinationsMark[i] = jcbfMark;
            jcbfMark = new JComboBox(markString);
            examinationsMark[i+2] = jcbfMark;
            addComponent(jPanelID, examinationsMark[i], 1, i+3, 1, 1);
            addComponent(jPanelID, examinationsMark[i+2], 2, i+3, 1, 1);
        }
        frame.add(jPanelID, BorderLayout.NORTH);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mode.equals("SEARCH_MODE")) searchStudent();
                else removeStudent();
            }
        });
        frame.add(okButton, BorderLayout.SOUTH);
        return frame;
    }

    private static void addComponent(Container container, Component component,
                                     int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0);
        container.add(component, gbc);
    }

    private void searchStudent(){
        if (isAllCorrect()){
            if (searchScrollPanel != null) frame.remove(searchScrollPanel);
            searchPanel = new MainPanel();
            searchPanel.getTableModel().setNumberExaminations(mainPanel.getTableModel().getNumberExaminations());
            searchScrollPanel = new JScrollPane(searchPanel);
            for (Student student: tableModel.getStudents()) {
                if (compliesTemplate(student)) {
                    searchPanel.getTableModel().getStudents().add(student);
                }
            }
            searchPanel.updateTable();
            frame.add(searchScrollPanel, BorderLayout.CENTER);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog
                    (null, "Not correct student information", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeStudent(){
        if (isAllCorrect()){
            int counterStudent = 0;
            Iterator<Student> itr = tableModel.getStudents().iterator();
            while (itr.hasNext()) {
                Student student = itr.next();
                if (compliesTemplate(student)) {
                    itr.remove();
                    counterStudent++;
                }
            }
            tableModel.setNumberMaxExaminations();
            mainPanel.updateTable();
            if (counterStudent > 0) {
                JOptionPane.showMessageDialog
                        (null, "Delete " + counterStudent + " student", "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog
                        (null, "Student not find", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog
                    (null, "Not correct student information", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean compliesTemplate(Student student) {
        boolean complies = true;
        if (!lastName.getText().equals(student.getLastName())) complies = false;
        if (!group.getText().equals("") && !group.getText().equals(Integer.toString(student.getNumberGroup())))
            complies = false;
        if (!getExaminationsMark(0))
            if (!isaCompliesMiddleMark(student)) complies = false;
        if (!getExaminationsMark(1))
            if (!isaCompliesMark(student)) complies = false;
        return complies;
    }

    private boolean isaCompliesMiddleMark(Student student) {
        return  getExaminationsMarkInt(0) <= student.getMiddleMark() &&
                getExaminationsMarkInt(2) >= student.getMiddleMark();
    }

    private boolean isaCompliesMark(Student student) {
        boolean answer = true;
        if (student.getExaminations().size() == 0) return false;
        for (Examination exam : student.getExaminations()) {
            int examMark = exam.getExaminationMarkInt();
            if (!(getExaminationsMarkInt(1) <= examMark && getExaminationsMarkInt(3) >= examMark))
                answer = false;
        }
        return answer;
    }

    private boolean isAllCorrect() {
        boolean allCorrect = true;
        if (isNotCorrectLastName()) allCorrect = false;
        if (isNotCorrectGroup()) allCorrect = false;
        for (int i=0; i < 2;i++){
            if (isNotCorrectMarck(i)) allCorrect = false;
        }
        return allCorrect;
    }

    private boolean isNotCorrectGroup() {
        Pattern p = Pattern.compile("[0-9]+");
        return !p.matcher(group.getText()).matches() ^ group.getText().equals("");
    }

    private boolean isNotCorrectLastName() {
        return ((lastName.getText().equals("")) ||
                (lastName.getText().length() > 0 && lastName.getText().charAt(0) == ' '));
    }

    private boolean isNotCorrectMarck(int i) {
        return ((getExaminationsMark(i) && !getExaminationsMark(i+2)) ||
                (getExaminationsMark(i+2) && !getExaminationsMark(i)));
    }

    private boolean getExaminationsMark(int i){
        return examinationsMark[i].getSelectedItem().equals("-");
    }

    private int getExaminationsMarkInt(int i){
        return Integer.parseInt((String)examinationsMark[i].getSelectedItem());
    }

}
