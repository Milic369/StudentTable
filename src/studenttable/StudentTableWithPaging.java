package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andrey on 04/03/16.
 */
public class StudentTableWithPaging extends JComponent {

    private TableModel tableModel;
    private MainWindow mainWindow;
    private JScrollPane scrollTable;
    private int currentPage = 1;
    private int studentOnPage = 10;
    private int heightTable;

    public StudentTableWithPaging(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        tableModel = new TableModel();
        heightTable = (int)mainWindow.getFrame().getSize().getHeight()-100;
        makePanel();
    }

    public void makePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(makeTable(), BorderLayout.NORTH);
        scrollTable = new JScrollPane(tablePanel);
        scrollTable.setPreferredSize(new Dimension(0, heightTable));
        scrollTable.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateScrollTable();
            }
        });
        add(scrollTable, BorderLayout.NORTH);
        add(makeToolsPanel(), BorderLayout.SOUTH);
    }

    private JPanel makeTable(){
        JPanel table = new JPanel();
        table.setLayout(new GridBagLayout());
        int numberExaminations = tableModel.getNumberExaminations();
        List<Student> students = tableModel.getStudents();
        AddComponent.add(table, "Full Name", 0, 1, 1, 3);
        AddComponent.add(table, "Group", 1, 1, 1, 3);
        AddComponent.add(table, "Examinations", 2, 1, numberExaminations * 2, 1);
        for (int i = 0, x = 2; i < numberExaminations; i++, x += 2) {
            AddComponent.add(table, Integer.toString(i + 1), x, 2, 2, 1);
            AddComponent.add(table, "name", x, 3, 1, 1);
            AddComponent.add(table, "mark", x + 1, 3, 1, 1);
        }
        int firstStudentOnPage = studentOnPage * (currentPage - 1);
        for (int y = 4, student = firstStudentOnPage; y < studentOnPage + 4 && student < students.size(); y++, student++) {
            tableModel.setNumberMaxExaminations(students.get(student).getExaminations().size());
            for (int i = 0; i < numberExaminations * 2 + 2; i++) {
                String write = students.get(student).getField(i);
                AddComponent.add(table, write, i, y, 1, 1);
            }
        }
        return table;
    }

    private JPanel makeToolsPanel() {
        int numberExaminations = tableModel.getNumberExaminations();
        List<Student> students = tableModel.getStudents();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        String statusBar = "Page " + currentPage + "/" + getNumberMaxPage()
                + " Total records: " + students.size() + " ";
        panel.add(new JLabel(statusBar));
        panel.add(AddComponent.makeButton(new JButton(), "FIRST_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firstPage();
            }
        }));
        panel.add(AddComponent.makeButton(new JButton(), "PREVIOUS_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prevPage();
            }
        }));
        panel.add(AddComponent.makeButton(new JButton(), "NEXT_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextPage();
            }
        }));
        panel.add(AddComponent.makeButton(new JButton(), "LAST_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lastPage();
            }
        }));
        JLabel label = new JLabel(" Student on page: ");
        panel.add(label);
        String[] sizeStudent = {"10", "20", "30", "50", "100"};
        JComboBox sizeBox = new JComboBox(sizeStudent);
        sizeBox.setSelectedIndex(Arrays.asList(sizeStudent).indexOf(Integer.toString(studentOnPage)));
        sizeBox.setMaximumSize(sizeBox.getPreferredSize());
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeStudentOnPage(e);
            }
        });
        panel.add(sizeBox);
        label = new JLabel(" Number examinations: ");
        panel.add(label);
        String[] sizeExam = {"5", "6", "7", "8", "9", "10", "12", "15", "20"};
        JComboBox examBox = new JComboBox(sizeExam);
        examBox.setSelectedIndex(Arrays.asList(sizeExam).indexOf(Integer.toString(numberExaminations)));
        examBox.setMaximumSize(examBox.getPreferredSize());
        examBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeNumberExam(e);
            }
        });
        panel.add(examBox);
        return panel;
    }

    public void nextPage(){
        if (hasNextPage()) {
            currentPage++;
            updateComponent();
        }
    }

    private boolean hasNextPage() {
        return tableModel.getStudents().size() > studentOnPage * (currentPage - 1) + studentOnPage;
    }

    public void prevPage(){
        if (currentPage > 1) {
            currentPage--;
            updateComponent();
        }
    }

    public void firstPage(){
        if (currentPage > 1) {
            currentPage = 1;
            updateComponent();
        }
    }

    public void lastPage(){
        if (currentPage != getNumberMaxPage()){
            currentPage = getNumberMaxPage();
            updateComponent();
        }
    }

    private int getNumberMaxPage() {
        return (int)((tableModel.getStudents().size() - 1)/ studentOnPage) + 1;
    }

    public void changeStudentOnPage(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        if (studentOnPage != Integer.parseInt(change)){
            studentOnPage = Integer.parseInt(change);
            updateComponent();
        }
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public int getHeightTable() {
        return heightTable;
    }

    public void setHeightTable(int heightTable) {
        this.heightTable = heightTable;
    }

    public JScrollPane getScrollTable() {
        return scrollTable;
    }

    public void setScrollTable(JScrollPane scrollTable) {
        this.scrollTable = scrollTable;
    }

    public void updateComponent(){
        removeAll();
        makePanel();
        revalidate();
        repaint();
    }

    private void updateScrollTable() {
        scrollTable.revalidate();
        scrollTable.repaint();
    }

    public void changeNumberExam(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        if (canChangeNumberExam(change)){
            tableModel.setNumberExaminations(Integer.parseInt(change));
            updateComponent();
        } else {
            JOptionPane.showMessageDialog
                    (null, "Can't do number exam, small than " + tableModel.getNumberMaxExaminations(),
                            "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    private boolean canChangeNumberExam(String change) {
        return tableModel.getNumberMaxExaminations() <= Integer.parseInt(change);
    }
}

