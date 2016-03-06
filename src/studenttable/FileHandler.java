package studenttable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 26/02/16.
 */
public class FileHandler {

    private MainWindow mainWindow;
    private MainPanel mainPanel;
    private TableModel tableModel;

    public FileHandler(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.mainPanel = mainWindow.getMainPanel();
        this.tableModel = mainWindow.getMainPanel().getTableModel();
    }

    public void openFile(){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter( "Student table", "stable"));
        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                openXMLFile(fc.getSelectedFile().getPath());
        }
    }

    public void saveFile(){
        try {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                XMLOutputFactory output = XMLOutputFactory.newInstance();
                XMLStreamWriter writer = output.createXMLStreamWriter
                        (new FileWriter(fc.getSelectedFile() + ".stable"));
                writer.writeStartDocument("UTF-8", "1.0");
                writer.writeStartElement("text");
                writer.writeStartElement("numberExam");
                writer.writeCharacters(Integer.toString(tableModel.getNumberExaminations()));
                writer.writeEndElement();
                for (Student student: tableModel.getStudents()){
                    writer.writeStartElement("student");
                    writer.writeAttribute("lastName", student.getLastName());
                    writer.writeAttribute("firstName", student.getFirstName());
                    writer.writeAttribute("middleName", student.getMiddleName());
                    writer.writeAttribute("group", Integer.toString(student.getNumberGroup()));
                    for (Examination exam : student.getExaminations()) {
                        writer.writeStartElement("exam");
                        writer.writeAttribute("name", exam.getExaminationName());
                        writer.writeCharacters(exam.getExaminationMark());
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                }
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.flush();
            }
        } catch (Exception eSave) {
            JOptionPane.showMessageDialog
                    (null, "Can't save file", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void openXMLFile(String fileName) {
        try {
            tableModel.getStudents().clear();
            List<Examination> exams = new ArrayList<Examination>();
            String lastName = "";
            String firstName = "";
            String middleName = "";
            String group = "";
            String numberExam = "";
            String name;
            String mark;
            XMLStreamReader xmlr = XMLInputFactory.newInstance()
                    .createXMLStreamReader(fileName, new FileInputStream(fileName));
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    if (xmlr.getLocalName().equals("student")){
                        exams = new ArrayList<Examination>();
                        lastName = xmlr.getAttributeValue(null, "lastName");
                        firstName = xmlr.getAttributeValue(null, "firstName");
                        middleName = xmlr.getAttributeValue(null, "middleName");
                        group = xmlr.getAttributeValue(null, "group");
                    }
                    else if (xmlr.getLocalName().equals("exam")){
                        name = xmlr.getAttributeValue(null, "name");
                        xmlr.next();
                        mark = xmlr.getText();
                        exams.add(new Examination(name, Integer.parseInt(mark)));
                    } else if (xmlr.getLocalName().equals("numberExam")){
                        xmlr.next();
                        numberExam = xmlr.getText();
                    }
                } else if (xmlr.isEndElement()) {
                    if (xmlr.getLocalName().equals("student")){
                        tableModel.getStudents().add(new Student(lastName, firstName, middleName,
                                Integer.parseInt(group), exams));
                    }
                }
            }
            tableModel.setNumberExaminations(Integer.parseInt(numberExam));
            mainPanel.updateTable();
            mainWindow.updateWindow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't open file", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
