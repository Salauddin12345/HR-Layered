
package com.thinking.machines.hr.pl.ui;

import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;

public class DesignationUI extends JFrame
{
private JLabel designationLabel;
private JLabel searchLabel;
private JLabel errorLabel;
private JTextField searchBoxTextField;
private JButton searchBoxTextClearButton;
private Container container;
private JTable designationTable;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;

public DesignationUI()
{
initComponents();
setAppearence();
addListeners();
setViewMode();
controlPanel.setViewMode();
}

private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationTable.getRowCount()==0)
{
searchBoxTextField.setEnabled(false);
searchBoxTextClearButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchBoxTextField.setEnabled(true);
searchBoxTextField.requestFocus();
searchBoxTextClearButton.setEnabled(true);
designationTable.setEnabled(true);
}
}

private void setAddMode()
{
this.mode=MODE.ADD;
searchBoxTextField.setEnabled(false);
searchBoxTextClearButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setEditMode()
{
this.mode=MODE.EDIT;
searchBoxTextField.setEnabled(false);
searchBoxTextClearButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchBoxTextField.setEnabled(false);
searchBoxTextClearButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchBoxTextField.setEnabled(false);
searchBoxTextClearButton.setEnabled(false);
designationTable.setEnabled(false);
}

//inner class
class ControlPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonPanel;
private DesignationInterface designation;
ControlPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(110,110,110)));
initComponents();
setAppearence();
addListeners();
}

void setViewMode()
{
DesignationUI.this.setViewMode();
titleLabel.setVisible(true);
titleTextField.setVisible(false);
clearTitleTextFieldButton.setVisible(false);
addButton.setEnabled(true);
cancelButton.setEnabled(false);
if(designationTable.getRowCount()>0)
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
else
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
addButton.setIcon(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/add.png")));
editButton.setIcon(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/edit.png")));
}

void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleLabel.setVisible(false);
this.titleTextField.setText("");
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.addButton.setIcon(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/ok.png")));
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"select designation to Edit");
return; 
}
DesignationUI.this.setEditMode();
titleTextField.setText(designation.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
editButton.setIcon(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/ok.png")));
}
void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"select designation to delete");
return; 
}
DesignationUI.this.setDeleteMode();
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
removeDesignation();
this.setViewMode();
}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}

public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
this.titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
this.titleLabel.setText("");
}

private void initComponents()
{
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField(20);
clearTitleTextFieldButton=new JButton(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/cancel.png")));
addButton=new JButton(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/add.png")));
editButton=new JButton(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/edit.png")));
cancelButton=new JButton(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/cancel.png")));
deleteButton=new JButton(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/delete.png")));
exportToPDFButton=new JButton(new ImageIcon(DesignationUI.this.getClass().getResource("/icons/pdf.png")));
buttonPanel=new JPanel();
}
private void setAppearence()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm=0;
int tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+10+110+5,tm+20,140,30);
titleTextField.setBounds(lm+10+110+5,tm+20,330,30);

clearTitleTextFieldButton.setBounds(lm+10+110+140+10+190+5,tm+20,30,30);
buttonPanel.setBounds(lm+10,tm+20+80,485,70);
buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(130,130,130)));
addButton.setBounds(lm+35,tm+10,50,50);
editButton.setBounds(lm+35+50+40,tm+10,50,50);
cancelButton.setBounds(lm+35+50+40+50+40,tm+10,50,50); 
deleteButton.setBounds(lm+35+50+40+50+40+50+40,tm+10,50,50);
exportToPDFButton.setBounds(lm+35+50+40+50+40+50+40+50+40,tm+10,50,50);

buttonPanel.setLayout(null);
buttonPanel.add(addButton);
buttonPanel.add(editButton);
buttonPanel.add(cancelButton);
buttonPanel.add(deleteButton);
buttonPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(clearTitleTextFieldButton);
add(buttonPanel);
}

private boolean addDesignation()
{
String title=titleTextField.getText();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
return false;
}
else
{
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//no code
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rect=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rect);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
return false;
}
}
}

private boolean updateDesignation()
{
String title=titleTextField.getText();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
return false;
}
else
{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//no code
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rect=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rect);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
return false;
}
}
}

private void removeDesignation()
{
try
{
String title=designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete " +title+" ?","CONFORMATION",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
designationModel.remove(designation.getCode());
JOptionPane.showMessageDialog(this,"Designation : "+title+" deleted");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}

}

private void addListeners()
{

clearTitleTextFieldButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
titleTextField.setText("");
titleTextField.requestFocus();
}

});


addButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)
{
setAddMode();
titleTextField.requestFocus();
}
else
{
if(addDesignation())
{
setViewMode();
}

else titleTextField.requestFocus();
}
}

});

editButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)
{
setEditMode();
titleTextField.requestFocus();
}
else
{
if(updateDesignation())
{
setViewMode();
}
else titleTextField.requestFocus();

}
}

});

cancelButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
setViewMode();
}

});

deleteButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
setDeleteMode();
}

});

exportToPDFButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
JFileChooser jfc=new JFileChooser();
jfc.setAcceptAllFileFilterUsed(false);
jfc.setCurrentDirectory(new File("."));
jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){

public boolean accept(File file)
{
if(file.isDirectory()) return true;
if(file.getName().endsWith(".pdf")) return true;
return false;
}

public String getDescription()
{
return "pdf files";
}

});
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect Path : "+file.getAbsolutePath());
return;
}
if(file.exists()) 
{
selectedOption=JOptionPane.showConfirmDialog(DesignationUI.this,"Do You Want To Override("+file.getName()+")","CONFORMATION",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
file.delete();
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data Exported To : "+file.getAbsolutePath());
}
catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}
}
catch(Exception e)
{
System.out.println(e.getMessage());
}

}



}

});

}

}


ControlPanel controlPanel;

private void initComponents()
{
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
setIconImage(logoIcon.getImage());
designationLabel=new JLabel("Designation");
errorLabel=new JLabel("");
searchLabel=new JLabel("Search");
searchBoxTextField=new JTextField();
searchBoxTextClearButton=new JButton(new ImageIcon(this.getClass().getResource("/icons/cancel.png")));
controlPanel=new ControlPanel();
}

void setAppearence()
{
Font designationLabelFont=new Font("Verdana",Font.BOLD,20);
Font searchLabelFont=new Font("Verdana",Font.PLAIN,16);
Font errorLabelFont=new Font("Verdana",Font.BOLD,12);
Font searchBoxTextFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("verdana",Font.BOLD,16);

designationLabel.setFont(designationLabelFont);
searchLabel.setFont(searchLabelFont);
errorLabel.setFont(errorLabelFont);
searchBoxTextField.setFont(searchBoxTextFont);



designationModel=new DesignationModel();
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

designationTable.setRowHeight(30);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(5);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(330);
designationTable.setFont(new Font("Verdana",Font.PLAIN,16));
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

int lm=0;
int tm=0;
designationLabel.setBounds(lm+10,tm+10,200,30);
searchLabel.setBounds(lm+10,tm+10+30+10,100,20);
searchBoxTextField.setBounds(lm+10+100+5,tm+10+30+5,200,30);
errorLabel.setBounds(lm+10+200+40,tm+20,100,20);
errorLabel.setForeground(Color.red);
searchBoxTextClearButton.setBounds(lm+10+100+5+200+3,tm+10+30+5,30,30);
scrollPane.setBounds(lm+10,tm+10+30+5+30,505,320);
controlPanel.setBounds(lm+10,tm+10+30+5+30+323,505,190);
container=getContentPane();
container.setLayout(null);
container.add(scrollPane);
container.add(designationLabel);
container.add(errorLabel);
container.add(searchLabel);
container.add(searchBoxTextField);
container.add(searchBoxTextClearButton);
container.add(controlPanel);
int width=540;
int height=630;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setSize(width,height);
setLocation((d.width/2)-width/2,(d.height/2)-height/2);
}

void searchDesignation()
{
errorLabel.setText("");
String g=searchBoxTextField.getText().trim();
if(g.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(g,true);
}catch(BLException blException)
{
errorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rect=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rect);
}
private void addListeners()
{
searchBoxTextField.getDocument().addDocumentListener(new DocumentListener(){

public void changedUpdate(DocumentEvent de)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent de)
{
searchDesignation();
}
});

searchBoxTextClearButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
searchBoxTextField.setText("");
searchBoxTextField.requestFocus();
}

});

designationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

public void valueChanged(ListSelectionEvent lse)
{
try
{
int indexOfSelectedRow=designationTable.getSelectedRow();
DesignationInterface designation=designationModel.getDesignationAt(indexOfSelectedRow);
controlPanel.setDesignation(designation);
}catch(BLException blException)
{
controlPanel.clearDesignation();
}

}

});

}

}