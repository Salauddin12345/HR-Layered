package com.thinking.machines.hr.pl.model;

import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;


public class DesignationModel extends AbstractTableModel
{
private String []columnTitle;
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
public DesignationModel()
{
this.populateDataStructure();
}

private void populateDataStructure()
{
columnTitle=new String[2];
columnTitle[0]="S.No.";
columnTitle[1]="Designation";
Set<DesignationInterface> blDesignations=null;
try
{
designationManager=DesignationManager.getDesignationManager();
blDesignations=designationManager.getDesignations();
}catch(BLException blException)
{
// ???????? what to do
}
designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}

public int getRowCount()
{
return designations.size();
}

public int getColumnCount()
{
return columnTitle.length;
}

public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}

public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;
return String.class;
}
public boolean isCellEditable()
{
return false;
}

public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator;
iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("invalid designaiton : "+designation.getTitle());
throw blException;
}

public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{

Iterator<DesignationInterface> iterator;
iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("invalid designaiton : "+title);
throw blException;
}

public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code)break;
index++;
}
if(index==this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("invalid code : "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}

public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=designations.size())
{
BLException blException;
blException=new BLException();
blException.setGenericException("invalid index : "+index);
throw blException;
}
return this.designations.get(index);
}

public void exportToPDF(File file) throws BLException
{
try
{
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);

Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);
Paragraph companyNamePara=new Paragraph("XYZ Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);

Paragraph reportTitlePara=new Paragraph("List Of Designation");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(16);
 
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
Paragraph columnTitle1=new  Paragraph("S.No");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(15);

Paragraph columnTitle2=new  Paragraph("Designation");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(15);

PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);



Paragraph pageNumberPara;
Paragraph dataPara;
float topTableColumnWidth[]={1,5};
float dataTableColumnWidth[]={1,5};

int sno,x,pageSize=7;
boolean newPage=true;
int pageNumber=0;
Cell cell;
int totalPage=this.designations.size()/pageSize;
if(this.designations.size()%pageSize!=0) totalPage++;
Table topTable;
Table dataTable=null;
Table pageNumberTable;

sno=0;
pageSize=7;
x=0;
DesignationInterface designation;

while(x<this.designations.size())
{
if(newPage==true)
{
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidth));
cell=new Cell();
cell.add(logoPara);
cell.setBorder(Border.NO_BORDER);
topTable.addCell(cell);

cell=new Cell();
cell.add(companyNamePara);
cell.setBorder(Border.NO_BORDER);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
document.add(topTable);

pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));

pageNumberPara=new Paragraph("Page:"+pageNumber+"/"+totalPage);
pageNumberPara.setFont(pageNumberFont);
pageNumberPara.setFontSize(13);

cell=new Cell();
cell.add(pageNumberPara);
cell.setBorder(Border.NO_BORDER);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
document.add(pageNumberTable);

dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidth));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}
sno++;
designation=this.designations.get(x);
dataPara=new Paragraph(String.valueOf(sno));
dataPara.setFont(dataFont);
dataPara.setFontSize(14);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

dataPara=new Paragraph(designation.getTitle());
dataPara.setFont(dataFont);
dataPara.setFontSize(14);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

x++;

if(sno%pageSize==0 || x==this.designations.size())
{
document.add(dataTable);
document.add(new Paragraph("Software By : Thinking Machines"));
if(x<this.designations.size())
{
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}

}

}

document.close();
}catch(Exception e)
{
BLException blException=new BLException();
blException.setGenericException(e.getMessage());
throw blException;
}
}


} 