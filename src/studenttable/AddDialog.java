package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andrey on 05/03/16.
 */
public class AddDialog {

    private static final Insets insets = new Insets(0, 0, 0, 0);
    private MainPanel mainPanel;
    private TableModel tableModel;
    private JTextField[] fieldID;
    private JTextField[] examinationsName;
    private JComboBox[] examinationsMark;

    public AddDialog(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        tableModel = mainPanel.getTableModel();
        JFrame frame = createFrame();
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(mainPanel);
    }

    private JFrame createFrame(){
        JFrame frame = new JFrame("Add student");
        JPanel jPanelID = new JPanel();
        jPanelID.setLayout(new GridBagLayout());
        JLabel labelText = new JLabel("Add new student");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 0, 0, 2, 1);
        String[] labelString = {"Last Name*:", "First Name*:", "Middle Name*:", "Group*:"};
        fieldID = new JTextField[4];
        for (int i=0; i < 4;i++){
            labelText = new JLabel(labelString[i]);
            addComponent(jPanelID,labelText, 0, i+1, 1, 1);
            JTextField jtfField = new JTextField(30);
            fieldID[i] = jtfField;
            addComponent(jPanelID, fieldID[i], 1, i+1, 1, 1);
        }
        labelText = new JLabel("Examinations");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 0, 5, 2, 1);
        labelText = new JLabel("Name");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 0, 6, 1, 1);
        labelText = new JLabel("Mark");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        addComponent(jPanelID,labelText, 1, 6, 1, 1);
        String[] markString = {"-", "4", "5", "6", "7", "8", "9", "10"};
        examinationsName = new JTextField[tableModel.getNumberExaminations()];
        examinationsMark = new JComboBox[tableModel.getNumberExaminations()];
        for (int i=0; i < tableModel.getNumberExaminations();i++){
            JTextField jtfName = new JTextField(30);
            examinationsName[i] = jtfName;
            JComboBox jcbfMark = new JComboBox(markString);
            examinationsMark[i] = jcbfMark;
            addComponent(jPanelID, examinationsName[i], 0, i+7, 1, 1);
            addComponent(jPanelID, examinationsMark[i], 1, i+7, 2, 1);
        }
        frame.add(jPanelID, BorderLayout.NORTH);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewStudent();
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

    private void createNewStudent() {
        if (!isAllCorrect()){
            JOptionPane.showMessageDialog
                    (null, "Not correct student information", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else
        {
            List<Examination> examinations = new ArrayList<>();
            for (int i=0; i < tableModel.getNumberExaminations();i++){
                if (!examinationsName[i].getText().equals("")) {
                    examinations.add(new Examination(examinationsName[i].getText(),
                            Integer.parseInt((String) examinationsMark[i].getSelectedItem())));
                }
            }
            tableModel.getStudents().add(new Student(getTextID(0),
                                                     getTextID(1),
                                                     getTextID(2),
                                                     Integer.parseInt(getTextID(3)),
                                                     examinations));
            mainPanel.updateTable();
        }
    }

    private boolean isAllCorrect() {
        boolean allCorrect = true;
        for (int i=0; i < 3;i++){
            if (isNotCorrectID(i)) allCorrect = false;
        }
        if (isNotCorrectGroup()) allCorrect = false;
        for (int i=0; i < tableModel.getNumberExaminations();i++){
            if (isNotCorrectExamination(i)) allCorrect = false;
        }
        return allCorrect;
    }

    private boolean isNotCorrectGroup() {
        Pattern p = Pattern.compile("[0-9]+");
        return !p.matcher(fieldID[3].getText()).matches();
    }

    private String getTextID(int i) {
        return fieldID[i].getText();
    }

    private boolean isNotCorrectID(int i) {
        return ((fieldID[i].getText().equals("")) ||
                (fieldID[i].getText().length() > 0 && fieldID[i].getText().charAt(0) == ' '));
    }

    private boolean isNotCorrectExamination(int i) {
        return ((examinationsName[i].getText().equals("") && !examinationsMark[i].getSelectedItem().equals("-")) ||
                (examinationsMark[i].getSelectedItem().equals("-") && !examinationsName[i].getText().equals("")) ||
                (examinationsName[i].getText().length() > 0 && examinationsName[i].getText().charAt(0) == ' '));
    }

}
