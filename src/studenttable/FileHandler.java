package studenttable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.*;
import java.io.*;

/**
 * Created by andrey on 26/02/16.
 */
public class FileHandler {

    private MainWindow mainWindow;

    public FileHandler(MainWindow mainWindow){
        this.mainWindow = mainWindow;
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
                //TODO
                /*for (Line line : textPanel.getLines()) {
                    writer.writeStartElement("line");
                    for (Char ch : line.getChars()) {
                        writer.writeStartElement("char");
                        writer.writeAttribute("font", ch.getFontType());
                        writer.writeAttribute("style", Integer.toString(ch.getFontStyles()));
                        writer.writeAttribute("size", Integer.toString(ch.getFontSize()));
                        writer.writeCharacters(ch.getStringCh());
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                }*/
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
            //TODO
            /*Line newLine = new Line(mainWindow);
            textPanel.setLine(new ArrayList<Line>());
            caret.setCaretX(0);
            caret.setCaretY(0);
            XMLStreamReader xmlr = XMLInputFactory.newInstance()
                    .createXMLStreamReader(fileName, new FileInputStream(fileName));
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    if (xmlr.getLocalName().equals("line")){
                        newLine = new Line(mainWindow);
                    }
                    else if (xmlr.getLocalName().equals("char")){
                        String font = xmlr.getAttributeValue(null, "font");
                        String size = xmlr.getAttributeValue(null, "size");
                        String style = xmlr.getAttributeValue(null, "style");
                        xmlr.next();
                        newLine.add(xmlr.getText(), font, style, size);
                    }
                } else if (xmlr.isEndElement()) {
                    if (xmlr.getLocalName().equals("line")){
                        textPanel.getLines().add(newLine);
                    }
                }
            }*/
            mainWindow.updateWindow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't open file", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
