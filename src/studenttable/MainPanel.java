package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by andrey on 04/03/16.
 */
public class MainPanel extends JComponent {

    private static final Insets insets = new Insets(0, 0, 0, 0);
    //private MainWindow mainWindow;
    private TableModel tableModel;
    private JPanel table;
    private int currentPage = 1;
    private int studentOnPage = 10;

    public MainPanel(MainWindow mainWindow){
        //this.mainWindow = mainWindow;
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
            for (int i = 0; i < numberExaminations * 2 + 2; i++) {
                String write = students.get(student).getField(i);
                addComponent(table, write, i, y, 1, 1);
            }
        }
        add(table, BorderLayout.NORTH);
        String statusBar = "Page " + currentPage + "/" + getNumberMaxPage()
                + " Total records: " + students.size();
        add(new JLabel(statusBar), BorderLayout.SOUTH);
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

}

