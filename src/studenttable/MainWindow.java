package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andrey on 18/02/16.
 */
public class MainWindow {

    private FileHandler fileHandler;
    private MainPanel mainPanel;
    private JFrame frame;
    private JScrollPane scrollPanel;

    public MainWindow() {
        frame = new JFrame("Student Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createFileMenu());
        frame.add(createToolBar(), BorderLayout.PAGE_START);
        mainPanel = new MainPanel();
        scrollPanel = new JScrollPane(mainPanel);
        fileHandler = new FileHandler(this);
        frame.add(scrollPanel, BorderLayout.CENTER);
        frame.setSize(850,350);
        frame.setVisible(true);
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
        toolBar.add(makeButton(new JButton(), "SAVE.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.saveFile();
            }
        }));
        toolBar.add(makeButton(new JButton(), "OPEN.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.openFile();
            }
        }));
        toolBar.addSeparator();
        toolBar.add(makeButton(new JButton(), "SEARCH.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchDialog(mainPanel, "SEARCH_MODE");
            }
        }));
        toolBar.add(makeButton(new JButton(), "ADD.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddDialog(mainPanel);
            }
        }));
        toolBar.add(makeButton(new JButton(), "REMOVE.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchDialog(mainPanel, "REMOVE_MODE");;
            }
        }));
        toolBar.addSeparator();
        toolBar.add(makeButton(new JButton(), "FIRST.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.firstPage();
            }
        }));
        toolBar.add(makeButton(new JButton(), "PREVIOUS.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.prevPage();
            }
        }));
        toolBar.add(makeButton(new JButton(), "NEXT.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.nextPage();
            }
        }));
        toolBar.add(makeButton(new JButton(), "LAST.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.lastPage();
            }
        }));
        toolBar.addSeparator();
        JLabel label = new JLabel("Student on page: ");
        toolBar.add(label);
        String[] sizeStudent = {"10", "20", "30", "50", "100"};
        JComboBox sizeBox = new JComboBox(sizeStudent);
        sizeBox.setMaximumSize(sizeBox.getPreferredSize());
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeStudentOnPage(e);
            }
        });
        toolBar.add(sizeBox);
        toolBar.addSeparator();
        label = new JLabel("Number examinations: ");
        toolBar.add(label);
        String[] sizeExam = {"5", "6", "7", "8", "9", "10", "12", "15", "20"};
        JComboBox examBox = new JComboBox(sizeExam);
        examBox.setMaximumSize(examBox.getPreferredSize());
        examBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeNumberExam(e);
            }
        });
        toolBar.add(examBox);
        return toolBar;
    }

    public static JButton makeButton(JButton button, String imgString, ActionListener action){
        button.addActionListener(action);
        String patch = "img/" + imgString;
        ImageIcon img = new ImageIcon(patch);
        button.setIcon(img);
        return button;
    }

    public void updateWindow() {
        scrollPanel.revalidate();
        scrollPanel.repaint();
        frame.requestFocus();
    }

    public MainPanel getMainPanel(){
        return mainPanel;
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
