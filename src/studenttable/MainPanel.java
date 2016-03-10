package studenttable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by andrey on 04/03/16.
 */
public class MainPanel extends JComponent {

    private static final Insets insets = new Insets(0, 0, 0, 0);
    private TableModel tableModel;
    private JPanel table;
    private int currentPage = 1;
    private int studentOnPage = 10;

    public MainPanel(){
        setLayout(new BorderLayout());
        tableModel = new TableModel();
        makePanel();
    }

    public void makePanel() {
        table = new JPanel();
        table.setLayout(new GridBagLayout());
        int numberExaminations = tableModel.getNumberExaminations();
        List<Student> students = tableModel.getStudents();
        addComponent(table, "Full Name", 0, 1, 1, 3);
        addComponent(table, "Group", 1, 1, 1, 3);
        addComponent(table, "Examinations", 2, 1, numberExaminations * 2, 1);
        for (int i = 0, x = 2; i < numberExaminations; i++, x += 2) {
            addComponent(table, Integer.toString(i + 1), x, 2, 2, 1);
            addComponent(table, "name", x, 3, 1, 1);
            addComponent(table, "mark", x + 1, 3, 1, 1);
        }
        int firstStudentOnPage = studentOnPage * (currentPage - 1);
        for (int y = 4, student = firstStudentOnPage; y < studentOnPage + 4 && student < students.size(); y++, student++) {
            tableModel.setNumberMaxExaminations(students.get(student).getExaminations().size());
            for (int i = 0; i < numberExaminations * 2 + 2; i++) {
                String write = students.get(student).getField(i);
                addComponent(table, write, i, y, 1, 1);
            }
        }
        add(table, BorderLayout.NORTH);
        String statusBar = "Page " + currentPage + "/" + getNumberMaxPage()
                + " Total records: " + students.size() + " ";
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(new JLabel(statusBar));
        panel.add(MainWindow.makeButton(new JButton(), "FIRST_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firstPage();
            }
        }));
        panel.add(MainWindow.makeButton(new JButton(), "PREVIOUS_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prevPage();
            }
        }));
        panel.add(MainWindow.makeButton(new JButton(), "NEXT_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextPage();
            }
        }));
        panel.add(MainWindow.makeButton(new JButton(), "LAST_12.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lastPage();
            }
        }));
        add(panel, BorderLayout.SOUTH);
    }

    private static void addComponent(Container container, String nameLabel,
                                     int gridx, int gridy, int gridwidth, int gridheight) {
        JLabel label = new JLabel(nameLabel);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label.setHorizontalAlignment(JLabel.CENTER);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0);
        container.add(label, gbc);
    }

    public void nextPage(){
        if (hasNextPage()) {
            currentPage++;
            updateTable();
        }
    }

    private boolean hasNextPage() {
        return tableModel.getStudents().size() > studentOnPage * (currentPage - 1) + studentOnPage;
    }

    public void prevPage(){
        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    }

    public void firstPage(){
        if (currentPage > 1) {
            currentPage = 1;
            updateTable();
        }
    }

    public void lastPage(){
        if (currentPage != getNumberMaxPage()){
            currentPage = getNumberMaxPage();
            updateTable();
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
            updateTable();
        }
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void updateTable(){
        removeAll();
        makePanel();
        revalidate();
        repaint();
    }

    public void changeNumberExam(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        if (canChangeNumberExam(change)){
            tableModel.setNumberExaminations(Integer.parseInt(change));
            updateTable();
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

