package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";

public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name can not be zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("invalid designation code : "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false) throw new DAOException("invalid designation code : "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==(-1)) throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number can not be zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar number can not be zero");

try
{
int lastGeneratedEmployeeId=10000000;
String lastGeneratedEmployeeIdString="";
int recordCount=0;
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fPanNumber="";
String fAadharCardNumber="";
boolean isPanNumberExists=false;
boolean isAadharCardNumberExists=false;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(isPanNumberExists==false && fPanNumber.equalsIgnoreCase(panNumber)==true)
{
isPanNumberExists=true;
}
if(isAadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)==true)
{
isAadharCardNumberExists=true;
}
if(isPanNumberExists && isAadharCardNumberExists) break;
}
if(isPanNumberExists && isAadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number ("+panNumber+") and aadhar card number ("+aadharCardNumber+") already exists");
}
if(isPanNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number ("+panNumber+") already exists");
}
if(isAadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("aadhar card number ("+aadharCardNumber+") already exists");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
recordCount++;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");

randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString+"\n");

randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("invalid employee id:"+employeeId);
if(employeeId.length()==0) throw new DAOException("length of employeeId is null");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name can not be zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("invalid designation code : "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false) throw new DAOException("invalid designation code : "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("gender not set to MALE/FEMALE");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==(-1)) throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number can not be zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar number can not be zero");

try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("invalid employee id:"+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid employee id:"+employeeId);
}
String fEmployeeId;
String fPanNumber;
String fAadharCardNumber;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
long foundAt=0;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(int x=1;x<=6;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();

if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
}
if(panNumberFound==false && fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}

if(employeeIdFound && panNumberFound && aadharCardNumberFound)
{
break;
}

}

if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("invalid employee id : "+employeeId);
}
boolean panNumberExists=false;
if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists=true;
}
boolean aadharCardNumberExists=false;
if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists=true;
}

if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number("+panNumber+") and aadhar card number("+aadharCardNumber+") exists");
}

if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan Number : "+panNumber+" exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("aadhar card number : "+aadharCardNumber+" exists");
}

randomAccessFile.seek(foundAt);
for(int x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}

public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("invalid employee id:"+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employeeId is null");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("invalid employee id:"+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid employee id:"+employeeId);
}
String fEmployeeId;
boolean employeeIdFound=false;
long foundAt=0;
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(int x=1;x<=8;x++) randomAccessFile.readLine();

if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
break;
}

}

if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("invalid employee id : "+employeeId);
}

File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}


}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
EmployeeDTOInterface employeeDTO=null;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
String tmp=randomAccessFile.readLine();
if(tmp.charAt(0)=='M' || tmp.charAt(0)=='m') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);   
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
throw new DAOException("invalid desiganation Code : "+designationCode);
}

Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
EmployeeDTOInterface employeeDTO=null;
String fEmployeeId="";
String fName="";
int fDesignationCode=0;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode!=designationCode)
{
for(x=1;x<=6;x++) randomAccessFile.readLine();
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
String tmp=randomAccessFile.readLine();
if(tmp.charAt(0)=='M' || tmp.charAt(0)=='m') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);  
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
throw new DAOException("invlaid designation code : "+designationCode);
}
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)
{
randomAccessFile.close();
return true;
}
for(x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}

}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("invalid employeeId  : "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employeeId is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("invalid employeeId : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invlaid employeeId : "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
employeeDTO.setGender((randomAccessFile.readLine().charAt(0)=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(int x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("invalid employeeId : "+employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("invalid pan number : "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("invalid pan number : "+panNumber);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("invalid pan number : "+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid pan Number : "+panNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("invalid pan Number : "+panNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("invalid aadhar card number : "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("invalid aadhar card number : "+aadharCardNumber);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("invalid aadhar card number : "+aadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid aadhar card Number : "+aadharCardNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("invalid aadhar card Number : "+aadharCardNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
randomAccessFile.close();
return true;
}
for(int x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPanNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int x=1;x<=7;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fAadharCardNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int x=1;x<=8;x++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
try
{
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("invalid designation code : "+designationCode);
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
int count=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode) count++;
for(int x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

}