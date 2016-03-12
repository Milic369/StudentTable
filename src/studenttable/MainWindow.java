package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by andrey on 18/02/16.
 */
public class MainWindow {

    private FileHandler fileHandler;
    private StudentTableWithPaging studentTableWithPaging;
    private JFrame frame;
    private JScrollPane scrollPanel;

    public MainWindow() {
        frame = new JFrame("Student Table");
        frame.setSize(850,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createFileMenu());
        frame.add(createToolBar(), BorderLayout.PAGE_START);
        studentTableWithPaging = new StudentTableWithPaging(this);
        scrollPanel = new JScrollPane(studentTableWithPaging);
        fileHandler = new FileHandler(this);
        frame.add(scrollPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resolveTableSize();
            }
        });
    }

    private void resolveTableSize() {
        int height = (int)frame.getSize().getHeight()-100;
        studentTableWithPaging.setHeightTable(height);
        studentTableWithPaging.getScrollTable().setPreferredSize(new Dimension(0, height));
    }

    private JMenuBar createFileMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open");
        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.openFile();
            }
        });
        fileMenu.add(openFile);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.saveFile();
            }
        });
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(AddComponent.makeButton(new JButton(), "SAVE.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.saveFile();
            }
        }));
        toolBar.add(AddComponent.makeButton(new JButton(), "OPEN.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.openFile();
            }
        }));
        toolBar.addSeparator();
        toolBar.add(AddComponent.makeButton(new JButton(), "SEARCH.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchDialog(MainWindow.this, "SEARCH_MODE");
            }
        }));
        toolBar.add(AddComponent.makeButton(new JButton(), "ADD.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddDialog(studentTableWithPaging);
            }
        }));
        toolBar.add(AddComponent.makeButton(new JButton(), "REMOVE.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchDialog(MainWindow.this, "REMOVE_MODE");;
            }
        }));
        return toolBar;
    }

    public void updateWindow() {
        scrollPanel.revalidate();
        scrollPanel.repaint();
        frame.requestFocus();
    }

    public StudentTableWithPaging getStudentTableWithPaging(){
        return studentTableWithPaging;
    }

    public JFrame getFrame(){
        return frame;
    }

    public JScrollPane getScrollPanel(){
        return scrollPanel;
    }

    public static void main(String[] args){
        new MainWindow();
    }

}
