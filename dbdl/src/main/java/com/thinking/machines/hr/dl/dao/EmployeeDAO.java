package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;
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

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid designation code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("date of birth is null");
}
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("basic salary is null");
}
if(basicSalary.signum()==(-1)) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("basic salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("length of pan number can not be zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("aadhar card number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("length of aadhar number can not be zero");
}

try
{
boolean panNumberExists=false;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
panNumberExists=true;
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
boolean aadharCardNumberExists=false;
if(resultSet.next())
{
aadharCardNumberExists=true;
}
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number ("+panNumber+") and aadhar card number ("+aadharCardNumber+") already exists");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number ("+panNumber+") already exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("aadhar card number ("+aadharCardNumber+") already exists");
}
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
int generatedEmployeeId;
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(10000000+generatedEmployeeId));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("invalid employee id:"+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employeeId is null");
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOException("invalid employee id :"+employeeId);
}
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name can not be zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("invalid designation code : "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid designation code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("date of birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("gender not set to MALE/FEMALE");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("basic salary is null");
}
if(basicSalary.signum()==(-1)) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("basic salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("length of pan number can not be zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("aadhar card number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("length of aadhar number can not be zero");
}


try
{
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid employee Id : "+employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
boolean panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
boolean aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number("+panNumber+") and aadhar card number("+aadharCardNumber+") exists");
}

if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan Number : "+panNumber+" exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("aadhar card number : "+aadharCardNumber+" exists");
}

preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}

public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("invalid employee id:"+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employeeId is null");
int actualEmployeeId=0;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid employee Id : "+employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from employee");

java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
EmployeeDTOInterface employeeDTO;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return employees;
}
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();

java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
EmployeeDTOInterface employeeDTO;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid designation Code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
boolean designationAlloted;
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
designationAlloted=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return designationAlloted;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("invalid employeeId  : "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employeeId is zero");
int actualEmployeeId;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOException("invalid Employee Id :"+employeeId);
}
EmployeeDTOInterface employeeDTO=null;

try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid Employee Id :"+employeeId);
}
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("invalid pan number : "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("invalid pan number : "+panNumber);

EmployeeDTOInterface employeeDTO=null;
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid PAN Number :"+panNumber);
}
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("invalid aadhar card number : "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("invalid aadhar card number : "+aadharCardNumber);

EmployeeDTOInterface employeeDTO=null;
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();

if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid Aadaar Card Number :"+aadharCardNumber);
}
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(10000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender=resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}

public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("invalid employeeId  : "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employeeId is zero");
int actualEmployeeId;
try
{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
}catch(Exception exception)
{
throw new DAOException("invalid Employee Id :"+employeeId);
}
EmployeeDTOInterface employeeDTO=null;

try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean employeeIdExists;
employeeIdExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return employeeIdExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean panNumberExists;
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return panNumberExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
boolean aadharCardNumberExists;
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return aadharCardNumberExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public int getCount() throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) from employee");
resultSet.next();
int count=resultSet.getInt(1);
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}
public int getCountByDesignation(int designationCode) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid designation Code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select count(*) from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
resultSet.next();
int count=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}

}